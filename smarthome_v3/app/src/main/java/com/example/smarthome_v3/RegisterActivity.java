package com.example.smarthome_v3;

/*

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button signUpButton;

    private Socket socket;
    private PrintWriter writer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.idText);
        nameEditText = findViewById(R.id.nameText);
        phoneEditText = findViewById(R.id.phoneText);
        passwordEditText = findViewById(R.id.passwordText);
        signUpButton = findViewById(R.id.registerButton);

        // 서버에 연결합니다.
        connectToServer();

        signUpButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            String name = nameEditText.getText().toString();
            String phone = phoneEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();

            if (username.isEmpty() || name.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                // 서버로 데이터를 전송하는 메서드를 호출합니다.
                sendSignupData(username, name, phone, password);
            }
        });
    }

    private void connectToServer() {
        new Thread(() -> {
            try {
                // 서버의 IP 주소와 포트 번호를 설정합니다.
                String serverIp = "192.168.0.180";  // 서버의 IP 주소로 변경
                int serverPort = 12345;  // 서버의 포트 번호로 변경
                socket = new Socket(serverIp, serverPort);
                writer = new PrintWriter(socket.getOutputStream(), true);
                Log.d("Socket", "Connected to server");

                // UI 스레드에서 토스트 메시지를 표시합니다.
                runOnUiThread(() -> Toast.makeText(RegisterActivity.this, "Connected to server", Toast.LENGTH_SHORT).show());
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(RegisterActivity.this, "Failed to connect to server: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("SocketError", "Exception: " + e.getMessage());
                });
            }
        }).start();
    }

    private void sendSignupData(String username, String name, String phone, String password) {
        new Thread(() -> {
            try {
                if (socket != null && socket.isConnected() && !socket.isClosed()) {
                    writer.println(username + "," + phone + "," + password + "," + name);
                    Log.d("Socket", "Data sent: " + username + "," + phone + "," + password + "," + name);

                    // UI 스레드에서 토스트 메시지를 표시합니다.
                    runOnUiThread(() -> Toast.makeText(RegisterActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show());
                } else {
                    runOnUiThread(() -> Toast.makeText(RegisterActivity.this, "Socket is closed or not initialized", Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(RegisterActivity.this, "Sign Up Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("SocketError", "Exception: " + e.getMessage());
                });
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (writer != null) {
                writer.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

*/


// -------------------------------------------------------------------------------------------------

// SocketActivity에 LogWeather 서버만 정보만 추가했을 때 동작되는 코드!!!!

/*

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import android.content.Intent;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button signUpButton;
    private Button validateButton;

    private Socket socket;
    private PrintWriter writer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.idText);
        nameEditText = findViewById(R.id.nameText);
        phoneEditText = findViewById(R.id.phoneText);
        passwordEditText = findViewById(R.id.passwordText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordText); // XML 레이아웃에 이 필드를 추가하세요.
        signUpButton = findViewById(R.id.registerButton);
        validateButton = findViewById(R.id.validateButton); // XML 레이아웃에 이 필드를 추가하세요.

        connectToServer();

        validateButton.setOnClickListener(view -> {
            String userID = usernameEditText.getText().toString().trim(); // 아이디 입력값 가져오기
            if (!userID.isEmpty()) {
                // 중복 확인 요청을 서버에 보냄
                sendValidationRequest(userID);
            } else {
                Toast.makeText(RegisterActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
            }
        });

        signUpButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            String name = nameEditText.getText().toString();
            String phone = phoneEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();

            if (username.isEmpty() || name.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                sendSignupData(username, name, phone, password);
            }
        });
    }

    private void connectToServer() {
        new Thread(() -> {
            try {
                String serverIp = "192.168.0.180";  // 서버의 IP 주소
                int serverPort = 12345;  // 서버의 포트 번호
                socket = new Socket(serverIp, serverPort);
                writer = new PrintWriter(socket.getOutputStream(), true);
                Log.d("Socket", "Connected to server");
                runOnUiThread(() -> Toast.makeText(RegisterActivity.this, "Connected to server", Toast.LENGTH_SHORT).show());
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(RegisterActivity.this, "Failed to connect to server: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("SocketError", "Exception: " + e.getMessage());
                });
            }
        }).start();
    }

    private void sendSignupData(String username, String name, String phone, String password) {
        new Thread(() -> {
            try {
                if (socket != null && socket.isConnected() && !socket.isClosed()) {
                    writer.println(username + "," + phone + "," + password + "," + name);
                    Log.d("Socket", "Data sent: " + username + "," + phone + "," + password + "," + name);
                    runOnUiThread(() -> Toast.makeText(RegisterActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show());

                    // 로그인 화면으로 이동
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); // 현재 액티비티를 종료하여 뒤로 가기 버튼을 눌러도 이전 화면으로 돌아가지 않게 함

                } else {
                    runOnUiThread(() -> Toast.makeText(RegisterActivity.this, "Socket is closed or not initialized", Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(RegisterActivity.this, "Sign Up Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("SocketError", "Exception: " + e.getMessage());
                });
            }
        }).start();
    }

    private void sendValidationRequest(String userID) {
        new Thread(() -> {
            Socket socket = null;
            try {
                socket = new Socket("220.69.207.113", 12345);
                socket.setSoTimeout(5000); // 서버 응답 대기 타임아웃 설정

                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                out.println("VALIDATE_USER " + userID);

                final String response = in.readLine();

                runOnUiThread(() -> {
                    if (response != null && response.equals("VALID")) {
                        // 사용 가능한 아이디인 경우
                        Toast.makeText(RegisterActivity.this, "Username is available", Toast.LENGTH_SHORT).show();
                        validateButton.setBackgroundColor(Color.parseColor("#8C8C8C")); // 버튼 색상 변경
                    } else if (response != null && response.equals("INVALID")) {
                        // 사용 불가능한 아이디인 경우
                        Toast.makeText(RegisterActivity.this, "Username is not available", Toast.LENGTH_SHORT).show();
                    } else {
                        // 예상치 못한 서버 응답
                        Toast.makeText(RegisterActivity.this, "Unexpected response from server", Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (UnknownHostException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(RegisterActivity.this, "Invalid host name", Toast.LENGTH_SHORT).show());
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(RegisterActivity.this, "Timeout while waiting for server response", Toast.LENGTH_SHORT).show());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (writer != null) {
                writer.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

*/

// -------------------------------------------------------------------------------------------------

// SocketActivity 분리 후 회원가입까지 다 되는 코드 (약간의 오류가 있지만)
// 그 오류는.... 회원가입을 누르면 알 수 없는 서버의 응답 이라고는 뜨지만? 회원가입은 됨

/*

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity implements SocketActivity.OnDataReceivedListener {

    private EditText usernameEditText, nameEditText, phoneEditText, passwordEditText;
    private Button registerButton, validateButton;
    private SocketActivity socketActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.idText);
        nameEditText = findViewById(R.id.nameText);
        phoneEditText = findViewById(R.id.phoneText);
        passwordEditText = findViewById(R.id.passwordText);
        registerButton = findViewById(R.id.registerButton);
        validateButton = findViewById(R.id.validateButton);

        socketActivity = SocketActivity.getInstance();
        socketActivity.connectToServer(this);

        validateButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            if (!username.isEmpty()) {
                socketActivity.sendUsernameValidationRequest(username);
                validateButton.setEnabled(false);
            } else {
                Toast.makeText(this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
            }
        });

        registerButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            String name = nameEditText.getText().toString();
            String phone = phoneEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (!username.isEmpty() && !name.isEmpty() && !phone.isEmpty() && !password.isEmpty()) {
                socketActivity.sendSignupData(username, name, phone, password);
                registerButton.setEnabled(false); // 회원가입 버튼 비활성화
            } else {
                Toast.makeText(this, "모든 필드를 입력하세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socketActivity.disconnect();
    }

    public void onDataReceived(String data) {
        runOnUiThread(() -> {
            Log.d("RegisterActivity", "Data received from server: " + data); // 서버 응답 확인 로그 추가
            // 서버 응답을 바탕으로 사용자에게 메시지 표시
            if (data.startsWith("VALIDATE")) {
                if (data.contains("SUCCESS")) {
                    Toast.makeText(RegisterActivity.this, "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                } else if (data.contains("FAILURE")) {
                    Toast.makeText(RegisterActivity.this, "이미 사용 중인 아이디입니다.", Toast.LENGTH_SHORT).show();
                }

                // 중복 확인 후 버튼 다시 활성화
                validateButton.setEnabled(true);

        } else if (data.startsWith("REGISTER")) {
            if (data.contains("SUCCESS")) {
                Toast.makeText(RegisterActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
            } else if (data.contains("FAILURE")) {
                Toast.makeText(RegisterActivity.this, "회원가입에 실패했습니다. 다시 시도하세요.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(RegisterActivity.this, "알 수 없는 서버 응답: " + data, Toast.LENGTH_LONG).show();
        }

        });
    }
}

*/

// -------------------------------------------------------------------------------------------------

// SocketActivity 분리 후 회원가입까지 완전 다 되는 코드
// R1

/*

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class  RegisterActivity extends AppCompatActivity implements SocketActivity.OnDataReceivedListener {

    private EditText usernameEditText, nameEditText, phoneEditText, passwordEditText;
    private Button registerButton, validateButton;
    private SocketActivity socketActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.idText);
        nameEditText = findViewById(R.id.nameText);
        phoneEditText = findViewById(R.id.phoneText);
        passwordEditText = findViewById(R.id.passwordText);
        registerButton = findViewById(R.id.registerButton);
        validateButton = findViewById(R.id.validateButton);

        socketActivity = SocketActivity.getInstance();
        socketActivity.connectToServer(this);

        validateButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            if (!username.isEmpty()) {
                socketActivity.sendUsernameValidationRequest(username);
                validateButton.setEnabled(false);
            } else {
                Toast.makeText(this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
            }
        });

        registerButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            String name = nameEditText.getText().toString();
            String phone = phoneEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (!username.isEmpty() && !name.isEmpty() && !phone.isEmpty() && !password.isEmpty()) {
                socketActivity.sendSignupData(username, name, phone, password);
                registerButton.setEnabled(false); // 회원가입 버튼 비활성화
            } else {
                Toast.makeText(this, "모든 필드를 입력하세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socketActivity.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        socketActivity.connectToServer(this);
    }

    public void onDataReceived(String data) {
        // 원본 데이터를 로깅하여 수신 데이터의 형식을 확인
        Log.d("RegisterActivity", "Raw data from server: '" + data + "'");

        // 공백과 특수 문자를 모두 제거한 후 로그 출력
        data = data.trim();
        Log.d("RegisterActivity", "Processed data received from server: '" + data + "' (length: " + data.length() + ")");

        String finalData = data;
        runOnUiThread(() -> {
            Log.d("RegisterActivity", "Comparing data: '" + finalData + "'");

            if (finalData.equalsIgnoreCase("VALIDATE SUCCESS")) {
                Toast.makeText(RegisterActivity.this, "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                validateButton.setEnabled(true);
            } else if (finalData.equalsIgnoreCase("VALIDATE FAILURE")) {
                Toast.makeText(RegisterActivity.this, "이미 사용 중인 아이디입니다.", Toast.LENGTH_SHORT).show();
                validateButton.setEnabled(true);
            } else if (finalData.equalsIgnoreCase("REGISTER SUCCESS")) {
                Toast.makeText(RegisterActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else if (finalData.equalsIgnoreCase("REGISTER FAILURE")) {
                Toast.makeText(RegisterActivity.this, "회원가입에 실패했습니다. 다시 시도하세요.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RegisterActivity.this, "알 수 없는 서버 응답: " + finalData, Toast.LENGTH_LONG).show();
            }
        });
    }
}

*/

// --------------------------

// 엉성한 회원가입과 날씨API 연결

/*

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class  RegisterActivity extends AppCompatActivity implements SocketActivity.OnDataReceivedListener {

    private EditText usernameEditText, nameEditText, phoneEditText, passwordEditText;
    private Button registerButton, validateButton;
    private SocketActivity socketActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.idText);
        nameEditText = findViewById(R.id.nameText);
        phoneEditText = findViewById(R.id.phoneText);
        passwordEditText = findViewById(R.id.passwordText);
        registerButton = findViewById(R.id.registerButton);
        validateButton = findViewById(R.id.validateButton);

        socketActivity = SocketActivity.getInstance();
        socketActivity.connectToServer(this);

        validateButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            if (!username.isEmpty()) {
                socketActivity.sendUsernameValidationRequest(username);
                validateButton.setEnabled(false);
            } else {
                Toast.makeText(this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
            }
        });

        registerButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            String name = nameEditText.getText().toString();
            String phone = phoneEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (!username.isEmpty() && !name.isEmpty() && !phone.isEmpty() && !password.isEmpty()) {
                socketActivity.sendSignupData(username, name, phone, password);
                registerButton.setEnabled(false); // 회원가입 버튼 비활성화
            } else {
                Toast.makeText(this, "모든 필드를 입력하세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socketActivity.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        socketActivity.connectToServer(this);
    }

    public void onDataReceived(String data) {
        // 원본 데이터를 로깅하여 수신 데이터의 형식을 확인
        Log.d("RegisterActivity", "Raw data from server: '" + data + "'");

        // 공백과 특수 문자를 모두 제거한 후 로그 출력
        data = data.trim();
        Log.d("RegisterActivity", "Processed data received from server: '" + data + "' (length: " + data.length() + ")");

        String finalData = data;
        runOnUiThread(() -> {
            Log.d("RegisterActivity", "Comparing data: '" + finalData + "'");

            if (finalData.equalsIgnoreCase("VALIDATE SUCCESS")) {
                Toast.makeText(RegisterActivity.this, "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                validateButton.setEnabled(true);
            } else if (finalData.equalsIgnoreCase("VALIDATE FAILURE")) {
                Toast.makeText(RegisterActivity.this, "이미 사용 중인 아이디입니다.", Toast.LENGTH_SHORT).show();
                validateButton.setEnabled(true);
            } else if (finalData.equalsIgnoreCase("REGISTERSUCCESS")) { // 여기 조건 수정
                Toast.makeText(RegisterActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else if (finalData.equalsIgnoreCase("REGISTER FAILURE")) {
                Toast.makeText(RegisterActivity.this, "회원가입에 실패했습니다. 다시 시도하세요.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RegisterActivity.this, "알 수 없는 서버 응답: " + finalData, Toast.LENGTH_LONG).show();
            }
        });
    }
}

*/

// -------------------------------------------------------------------------------------------------

// 이상한 메세지가 뜨지만...? 동작은 되는 코드.. ㅋ (회원가입+로그인+메인화면)
// 뒤로라기 만들기 전 코드
/*

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class  RegisterActivity extends AppCompatActivity implements SocketActivity.OnDataReceivedListener {

    private EditText usernameEditText, nameEditText, phoneEditText, passwordEditText;
    private Button registerButton, validateButton;
    private SocketActivity socketActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.idText);
        nameEditText = findViewById(R.id.nameText);
        phoneEditText = findViewById(R.id.phoneText);
        passwordEditText = findViewById(R.id.passwordText);
        registerButton = findViewById(R.id.registerButton);
        validateButton = findViewById(R.id.validateButton);

        socketActivity = SocketActivity.getInstance();
        socketActivity.connectToServer(this);

        validateButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            if (!username.isEmpty()) {
                socketActivity.sendUsernameValidationRequest(username);
                validateButton.setEnabled(false);
            } else {
                Toast.makeText(this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
            }
        });

        registerButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            String name = nameEditText.getText().toString();
            String phone = phoneEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (!username.isEmpty() && !name.isEmpty() && !phone.isEmpty() && !password.isEmpty()) {
                socketActivity.sendSignupData(username, name, phone, password);
                registerButton.setEnabled(false); // 회원가입 버튼 비활성화
            } else {
                Toast.makeText(this, "모든 필드를 입력하세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socketActivity.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        socketActivity.connectToServer(this);
    }

    public void onDataReceived(String data) {
        Log.d("RegisterActivity", "Raw data from server: '" + data + "'");

        // 데이터를 표준화 (서버에서 공백 없이 보내온 경우 처리)
        data = data.trim().replaceAll("(?i)registersuccess", "REGISTER SUCCESS");
        Log.d("RegisterActivity", "Processed data received from server: '" + data + "' (length: " + data.length() + ")");

        String finalData = data;
        runOnUiThread(() -> {
            Log.d("RegisterActivity", "Comparing data: '" + finalData + "'");

            if (finalData.equalsIgnoreCase("VALIDATE SUCCESS")) {
                Toast.makeText(RegisterActivity.this, "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                validateButton.setEnabled(true);
            } else if (finalData.equalsIgnoreCase("VALIDATE FAILURE")) {
                Toast.makeText(RegisterActivity.this, "이미 사용 중인 아이디입니다.", Toast.LENGTH_SHORT).show();
                validateButton.setEnabled(true);
            } else if (finalData.equalsIgnoreCase("REGISTER SUCCESS")) {
                Toast.makeText(RegisterActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else if (finalData.equalsIgnoreCase("REGISTER FAILURE")) {
                Toast.makeText(RegisterActivity.this, "회원가입에 실패했습니다. 다시 시도하세요.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RegisterActivity.this, "알 수 없는 서버 응답: " + finalData, Toast.LENGTH_LONG).show();
            }
        });
    }
}

 */

// -------------------------------------------------------------------------------------------------

// 회원가입 후 로그이 안 되는 부분 수정 하려고 노오력

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class  RegisterActivity extends AppCompatActivity implements SocketActivity.OnDataReceivedListener {

    private EditText usernameEditText, nameEditText, phoneEditText, passwordEditText;
    private Button registerButton, validateButton;
    private TextView backButton;
    private SocketActivity socketActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.idText);
        nameEditText = findViewById(R.id.nameText);
        phoneEditText = findViewById(R.id.phoneText);
        passwordEditText = findViewById(R.id.passwordText);
        registerButton = findViewById(R.id.registerButton);
        validateButton = findViewById(R.id.validateButton);
        backButton = findViewById(R.id.backButton); // backButton 추가

        socketActivity = SocketActivity.getInstance();
        socketActivity.connectToServer(this);

        // "처음화면으로 돌아가기" 버튼 클릭 시 LoginActivity로 이동
        backButton.setOnClickListener(view -> {
            // 소켓 연결 해제
            SocketActivity.getInstance().disconnect();

            // 로그인 화면을 초기 상태로 열기 위한 Intent 생성
            Intent intent = new Intent(RegisterActivity.this, LoginActivity2.class);
            // 기존 액티비티 스택을 비우고 새 작업으로 시작
            startActivity(intent);
            finish(); // 회원가입 액티비티 종료
        });

        validateButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            if (!username.isEmpty()) {
                socketActivity.sendUsernameValidationRequest(username);
                validateButton.setEnabled(false);
            } else {
                Toast.makeText(this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
            }
        });

        registerButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            String name = nameEditText.getText().toString();
            String phone = phoneEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // 유효성 검사: 빈 값이 있는지 체크
            if (username.isEmpty() || name.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "모든 필드를 입력하세요.", Toast.LENGTH_SHORT).show();
            } else {
                // 유효성 검사 통과 시 서버로 데이터 전송
                socketActivity.sendSignupData(username, name, phone, password);
                registerButton.setEnabled(false); // 회원가입 버튼 비활성화
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socketActivity.disconnect();
    }
/*
    @Override
    protected void onResume() {
        super.onResume();
        socketActivity.connectToServer(this);
    }
 */
@Override
protected void onResume() {
    super.onResume();
    // onResume에서 불필요한 소켓 연결을 방지
    if (!socketActivity.isSocketConnected()) {
        socketActivity.connectToServer(this);
    }
}
/*
    public void onDataReceived(String data) {
        Log.d("RegisterActivity", "Raw data from server: '" + data + "'");

        // 데이터를 표준화 (서버에서 공백 없이 보내온 경우 처리)
        data = data.trim().replaceAll("(?i)registersuccess", "REGISTER SUCCESS");
        Log.d("RegisterActivity", "Processed data received from server: '" + data + "' (length: " + data.length() + ")");

        String finalData = data;
        runOnUiThread(() -> {
            Log.d("RegisterActivity", "Comparing data: '" + finalData + "'");

            if (finalData.equalsIgnoreCase("VALIDATE SUCCESS")) {
                Toast.makeText(RegisterActivity.this, "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                validateButton.setEnabled(true);
            } else if (finalData.equalsIgnoreCase("VALIDATE FAILURE")) {
                Toast.makeText(RegisterActivity.this, "이미 사용 중인 아이디입니다.", Toast.LENGTH_SHORT).show();
                validateButton.setEnabled(true);
            } else if (finalData.equalsIgnoreCase("REGISTER SUCCESS")) {
                Toast.makeText(RegisterActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity2.class);
                startActivity(intent);
                finish();
            } else if (finalData.equalsIgnoreCase("REGISTER FAILURE")) {
                Toast.makeText(RegisterActivity.this, "회원가입에 실패했습니다. 다시 시도하세요.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RegisterActivity.this, "알 수 없는 서버 응답: " + finalData, Toast.LENGTH_LONG).show();
            }
        });
    }

 */
public void onDataReceived(String data) {
    Log.d("RegisterActivity", "Raw data from server: '" + data + "'");

    // 데이터를 표준화 (서버에서 공백 없이 보내온 경우 처리)
    data = data.trim().replaceAll("(?i)registersuccess", "REGISTER SUCCESS");
    Log.d("RegisterActivity", "Processed data received from server: '" + data + "' (length: " + data.length() + ")");

    String finalData = data;
    runOnUiThread(() -> {
        Log.d("RegisterActivity", "Comparing data: '" + finalData + "'");

        if (finalData.equalsIgnoreCase("VALIDATE SUCCESS")) {
            Toast.makeText(RegisterActivity.this, "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
            validateButton.setEnabled(true);
        } else if (finalData.equalsIgnoreCase("VALIDATE FAILURE")) {
            Toast.makeText(RegisterActivity.this, "이미 사용 중인 아이디입니다.", Toast.LENGTH_SHORT).show();
            validateButton.setEnabled(true);
        } else if (finalData.equalsIgnoreCase("REGISTER SUCCESS")) {
            Toast.makeText(RegisterActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this, LoginActivity2.class);
            startActivity(intent);
            finish();
        } else if (finalData.equalsIgnoreCase("REGISTER FAILURE: Invalid Registration Data")) {
            Toast.makeText(RegisterActivity.this, "회원가입에 실패했습니다. 입력한 정보를 확인하세요.", Toast.LENGTH_SHORT).show();
            // 추가적인 오류 메시지 또는 처리 로직을 여기에 추가할 수 있습니다.
        } else {
            //Toast.makeText(RegisterActivity.this, "알 수 없는 서버 응답: " + finalData, Toast.LENGTH_LONG).show();
            Log.d("RegisterActivity", "알 수 없는 서버 응답: " + finalData);
        }
    });
}
}

/*

// 아예 아무 로그도 안 뜨게 함.

public void onDataReceived(String data) {
    Log.d("RegisterActivity", "Raw data from server: '" + data + "'");

    // 데이터를 표준화 (서버에서 공백 없이 보내온 경우 처리)
    data = data.trim().replaceAll("(?i)registersuccess", "REGISTER SUCCESS");
    Log.d("RegisterActivity", "Processed data received from server: '" + data + "' (length: " + data.length() + ")");

    String finalData = data;
    runOnUiThread(() -> {
        Log.d("RegisterActivity", "Comparing data: '" + finalData + "'");

        // 서버 응답 처리 (Toast 대신 Log를 사용하여 로그만 찍음)
        if (finalData.equalsIgnoreCase("VALIDATE SUCCESS")) {
            // 사용 가능한 아이디
            Log.d("RegisterActivity", "사용 가능한 아이디입니다.");
            validateButton.setEnabled(true);
        } else if (finalData.equalsIgnoreCase("VALIDATE FAILURE")) {
            // 이미 사용 중인 아이디
            Log.d("RegisterActivity", "이미 사용 중인 아이디입니다.");
            validateButton.setEnabled(true);
        } else if (finalData.equalsIgnoreCase("REGISTER SUCCESS")) {
            // 회원가입 성공
            Log.d("RegisterActivity", "회원가입이 완료되었습니다.");
            Intent intent = new Intent(RegisterActivity.this, LoginActivity2.class);
            startActivity(intent);
            finish();
        } else if (finalData.equalsIgnoreCase("REGISTER FAILURE: Invalid Registration Data")) {
            // 회원가입 실패
            Log.d("RegisterActivity", "회원가입에 실패했습니다. 입력한 정보를 확인하세요.");
        } else {
            // 알 수 없는 응답
            Log.d("RegisterActivity", "알 수 없는 서버 응답: " + finalData);
        }
    });
}
 */