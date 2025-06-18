package com.example.smarthome_v3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity2 extends AppCompatActivity {

    private static final String TAG = "LoginActivity";  // TAG를 정의

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView registerButton;
    private SocketActivity socketActivity;
    private boolean isLoggingIn = false;  // 로그인 중 상태 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.registerButton);

        socketActivity = SocketActivity.getInstance();

        socketActivity.connectToServer(data -> {
            runOnUiThread(() -> {
                loginButton.setEnabled(true); // 서버 응답 후 버튼 다시 활성화
                handleServerResponse(data.trim()); // 응답 처리 메서드 호출
            });
        });

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // 로그인 버튼 클릭 시 입력 데이터 유효성 검사
            if (isInputValid(username, password) && !isLoggingIn) {
                isLoggingIn = true; // 로그인 시도 중 상태 설정
                loginButton.setEnabled(false); // 요청 중 버튼 비활성화
                attemptLogin(username, password); // 로그인 시도
            }
        });
    }

    // 입력 데이터 유효성 검사 메서드
    private boolean isInputValid(String username, String password) {
        if (username.isEmpty()) {
            Toast.makeText(this, "사용자 이름을 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void attemptLogin(String username, String password) {
        if (!socketActivity.isSocketConnected()) {
            socketActivity.connectToServer(data -> {
                if (data.equalsIgnoreCase("Connection failed")) {
                    runOnUiThread(() -> {
                        Toast.makeText(LoginActivity2.this, "서버 연결 실패. 네트워크 상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
                        loginButton.setEnabled(true);
                        isLoggingIn = false; // 로그인 시도 중 상태 해제
                    });
                } else {
                    // 연결 성공 시 로그인 데이터 전송
                    socketActivity.sendLoginData(username, password);
                }
            });
        } else {
            // 연결된 경우 바로 로그인 데이터 전송
            socketActivity.sendLoginData(username, password);
        }
    }

    // 서버 응답 처리 메서드
    private void handleServerResponse(String response) {
        runOnUiThread(() -> {
            if (isLoggingIn) {
                switch (response) {
                    case "LOGIN_SUCCESS":
                        isLoggingIn = false; // 로그인 성공 후 상태 해제
                        Log.d(TAG, "Login successful, processing success...");
                        Toast.makeText(LoginActivity2.this, "Login successful", Toast.LENGTH_SHORT).show();
                        // 로그인 성공 후 UI 갱신 및 MainActivity로 전환
                        Intent mainIntent = new Intent(LoginActivity2.this, MainActivity.class);
                        startActivity(mainIntent);
                        finish(); // 현재 LoginActivity 종료
                        break;

                    case "Login Failed":
                        isLoggingIn = false; // 로그인 실패 후 상태 해제
                        Log.d(TAG, "Login failed.");
                        Toast.makeText(LoginActivity2.this, "아이디 혹은 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                        loginButton.setEnabled(true); // 버튼 다시 활성화
                        break;

                    case "REGISTER_FAILURE: Invalid Registration Data":
                        Toast.makeText(LoginActivity2.this, "등록된 사용자 정보가 없습니다. 회원가입을 먼저 진행해 주세요.", Toast.LENGTH_SHORT).show();
                        break;

                    case "Connection failed":
                        Toast.makeText(LoginActivity2.this, "서버 연결 실패. 네트워크 상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        isLoggingIn = false; // 예기치 않은 응답 시 상태 해제
                        Log.d(TAG, "Unexpected response: " + response);
                        Toast.makeText(LoginActivity2.this, "Unexpected response from server: " + response, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        socketActivity.disconnect();
    }
}