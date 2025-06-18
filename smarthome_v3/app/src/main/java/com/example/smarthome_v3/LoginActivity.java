package com.example.smarthome_v3;


/*


package com.example.smarthome_v1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView registerButton; // 회원가입 TextView

    private Socket socket;
    private PrintWriter writer;
    private BufferedInputStream inputStream;
    private boolean isConnected = false; // 연결 상태를 추적하기 위한 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.registerButton); // TextView 연결

        // 앱이 시작될 때 서버에 연결합니다.
        connectToServer();

        // 회원가입 버튼 클릭 시 RegisterActivity로 이동
        TextView registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    sendLoginData(username, password); // 로그인 데이터 전송
                }
            }
        });



    }

    private void connectToServer() {
        new Thread(() -> {
            try {
                // 서버의 IP 주소와 포트 번호를 설정합니다.
                String serverIp = "220.69.207.113";  // 서버의 IP 주소로 변경
                int serverPort = 12345;  // 서버의 포트 번호로 변경
                socket = new Socket(serverIp, serverPort);
                writer = new PrintWriter(socket.getOutputStream(), true);
                inputStream = new BufferedInputStream(socket.getInputStream());
                Log.d("Socket", "Connected to server");

                // UI 스레드에서 토스트 메시지를 표시합니다.
                runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Connected to server", Toast.LENGTH_SHORT).show());
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(LoginActivity.this, "Failed to connect to server: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("SocketError", "Exception: " + e.getMessage());
                });
            }
        }).start();
    }

    private void sendLoginData(String username, String password) {
        new Thread(() -> {
            try {
                if (socket != null && socket.isConnected() && !socket.isClosed()) {
                    String dataToSend = username + "," + password;
                    writer.println(dataToSend);
                    Log.d("Socket", "Data sent: " + dataToSend);

                    // 서버로부터 응답을 읽습니다.
                    byte[] buffer = new byte[1024];
                    int bytesRead = inputStream.read(buffer);
                    if (bytesRead != -1) {
                        String response = new String(buffer, 0, bytesRead);
                        Log.d("Socket", "Response received: " + response);

                        // UI 스레드에서 응답에 따라 토스트 메시지를 표시합니다.
                        runOnUiThread(() -> {
                            if ("Login Successful".equals(response.trim())) {
                                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Log.e("SocketError", "Failed to read response from server.");
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Socket is closed or not initialized", Toast.LENGTH_SHORT).show());
                }
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(LoginActivity.this, "Login Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("SocketError", "Exception: " + e.getMessage());
                });
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disconnectFromServer();
    }

    private void disconnectFromServer() {
        new Thread(() -> {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

*/


// -------------------------------------------------------------------------------------------------

/*

package com.example.smarthome_v1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView registerButton;

    private Socket socket;
    private PrintWriter writer;
    private BufferedInputStream inputStream;
    private boolean isConnected = false; // 연결 상태를 추적하기 위한 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.registerButton);

        // 앱이 시작될 때 서버에 연결합니다.
        connectToServer();

        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    sendLoginData(username, password); // 로그인 데이터 전송
                }
            }
        });
    }

    private void connectToServer() {
        new Thread(() -> {
            try {
                String serverIp = "220.69.207.113";  // 서버의 IP 주소로 변경
                int serverPort = 12345;  // 서버의 포트 번호로 변경
                socket = new Socket(serverIp, serverPort);
                writer = new PrintWriter(socket.getOutputStream(), true);
                inputStream = new BufferedInputStream(socket.getInputStream());
                isConnected = true; // 연결 상태를 true로 설정
                Log.d("Socket", "Connected to server");

                runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Connected to server", Toast.LENGTH_SHORT).show());
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(LoginActivity.this, "Failed to connect to server: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("SocketError", "Exception: " + e.getMessage());
                });
            }
        }).start();
    }

    private void sendLoginData(String username, String password) {
        new Thread(() -> {
            try {
                if (isConnected && socket != null && socket.isConnected() && !socket.isClosed()) {
                    String dataToSend = username + "," + password;
                    writer.println(dataToSend);
                    Log.d("Socket", "Data sent: " + dataToSend);

                    byte[] buffer = new byte[1024];
                    int bytesRead = inputStream.read(buffer);
                    if (bytesRead != -1) {
                        String response = new String(buffer, 0, bytesRead);
                        Log.d("Socket", "Response received: " + response);

                        runOnUiThread(() -> {
                            if ("Login Successful".equals(response.trim())) {
                                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Log.e("SocketError", "Failed to read response from server.");
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Socket is closed or not initialized", Toast.LENGTH_SHORT).show());
                }
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(LoginActivity.this, "Login Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("SocketError", "Exception: " + e.getMessage());
                });
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disconnectFromServer();
    }

    private void disconnectFromServer() {
        new Thread(() -> {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
                isConnected = false; // 연결 상태를 false로 설정
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

 */

// 연결 성공한 코드 근데 로그인 버튼 누르면 activity_main으로 안 넘어감

// -------------------------------------------------ㅁ------------------------------------------------

// SocketActivity에 LogWeather 서버만 정보만 추가했을 때 동작되는 코드!!!!
// L1

/*

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView registerButton;

    private Socket socket;
    private PrintWriter writer;
    private BufferedInputStream inputStream;
    private boolean isConnected = false; // 연결 상태를 추적하기 위한 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.registerButton);

        // 앱이 시작될 때 서버에 연결합니다.
        connectToServer();

        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    sendLoginData(username, password); // 로그인 데이터 전송
                }
            }
        });
    }

    private void connectToServer() {
        new Thread(() -> {
            try {
                String serverIp = "192.168.0.180";  // 서버의 IP 주소로 변경
                int serverPort = 12345;  // 서버의 포트 번호로 변경
                socket = new Socket(serverIp, serverPort);
                writer = new PrintWriter(socket.getOutputStream(), true);
                inputStream = new BufferedInputStream(socket.getInputStream());
                isConnected = true; // 연결 상태를 true로 설정
                Log.d("Socket", "Connected to server");

                runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Connected to server", Toast.LENGTH_SHORT).show());
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(LoginActivity.this, "Failed to connect to server: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("SocketError", "Exception: " + e.getMessage());
                });
            }
        }).start();
    }

    private void sendLoginData(String username, String password) {
        new Thread(() -> {
            try {
                if (isConnected && socket != null && socket.isConnected() && !socket.isClosed()) {
                    // 데이터를 전송하기 전에 줄바꿈 문자 제거
                    String dataToSend = username.trim() + "," + password.trim();
                    writer.println(dataToSend);
                    Log.d("Socket", "Data sent: " + dataToSend);

                    byte[] buffer = new byte[1024];
                    int bytesRead = inputStream.read(buffer);
                    if (bytesRead != -1) {
                        String response = new String(buffer, 0, bytesRead).trim();
                        Log.d("Socket", "Response received: " + response);

                        runOnUiThread(() -> {
                            if ("Login Successful".equals(response)) {
                                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(mainIntent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Log.e("SocketError", "Failed to read response from server.");
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Socket is closed or not initialized", Toast.LENGTH_SHORT).show());
                }
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(LoginActivity.this, "Login Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("SocketError", "Exception: " + e.getMessage());
                });
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disconnectFromServer();
    }

    private void disconnectFromServer() {
        new Thread(() -> {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
                isConnected = false; // 연결 상태를 false로 설정
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

*/

// -------------------------------------------------------------------------------------------------


// L2
// SocketActivity랑 분리했을 때 로그인 기능 구현 완료

/*

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView registerButton;
    private SocketActivity socketActivity;

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
                String response = data.trim();

                switch (response) {
                    case "LOGIN_SUCCESS":
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                        finish();
                        break;

                    case "Login Failed":
                        Toast.makeText(LoginActivity.this, "아이디 혹은 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                        break;

                    case "Connection failed":
                        Toast.makeText(LoginActivity.this, "서버 연결 실패. 네트워크 상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        Toast.makeText(LoginActivity.this, "Unexpected response from server: " + response, Toast.LENGTH_SHORT).show();
                        break;
                }
            });
        });

        registerButton.setOnClickListener(view -> {
            Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
        });

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                // 로그인 요청 시도
                loginButton.setEnabled(false); // 요청 중 버튼 비활성화
                socketActivity.sendLoginData(username, password); // 로그인 데이터 전송
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socketActivity.disconnect();
    }
}

*/


// -------------------------------------------------------------------------------------------------

// 이상한 메세지가 뜨지만...? 동작은 되는 코드.. ㅋ (회원가입+로그인+메인화면)
// 회원가입 갔다가 로그인 안되는 현상 고치기 전 코드

/*

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView registerButton;
    private SocketActivity socketActivity;

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
                String response = data.trim();

                switch (response) {
                    case "LOGIN_SUCCESS":
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                        finish();
                        break;

                    case "Login Failed":
                        Toast.makeText(LoginActivity.this, "아이디 혹은 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                        break;

                    case "Connection failed":
                        Toast.makeText(LoginActivity.this, "서버 연결 실패. 네트워크 상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        Toast.makeText(LoginActivity.this, "Unexpected response from server: " + response, Toast.LENGTH_SHORT).show();
                        break;
                }
            });
        });

        registerButton.setOnClickListener(view -> {
            Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
        });

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                // 로그인 요청 시도
                loginButton.setEnabled(false); // 요청 중 버튼 비활성화

                // 연결이 되어 있지 않다면 연결 후 로그인 데이터 전송
                if (!socketActivity.isSocketConnected                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ()) {
                    socketActivity.connectToServer(data -> {
                        if (data.equalsIgnoreCase("Connection failed")) {
                            runOnUiThread(() -> {
                                Toast.makeText(LoginActivity.this, "서버 연결 실패. 네트워크 상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
                                loginButton.setEnabled(true);
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
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socketActivity.disconnect();
    }
}

 */

// ---------------------------------------------------------

// Unexpected response from server: REGISTER_FAILURE: Invalid Registration Data 라고는 안뜨고
// 회원정보가 없다고만 뜸

/*
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView registerButton;
    private SocketActivity socketActivity;

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

        registerButton.setOnClickListener(view -> {
            Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
        });

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // 로그인 버튼 클릭 시 입력 데이터 유효성 검사
            if (isInputValid(username, password)) {
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
                        Toast.makeText(LoginActivity.this, "서버 연결 실패. 네트워크 상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
                        loginButton.setEnabled(true);
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
        switch (response) {
            case "LOGIN_SUCCESS":
                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
                break;

            case "Login Failed":
                Toast.makeText(LoginActivity.this, "아이디 혹은 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                break;

            case "REGISTER_FAILURE: Invalid Registration Data":
                Toast.makeText(LoginActivity.this, "등록된 사용자 정보가 없습니다. 회원가입을 먼저 진행해 주세요.", Toast.LENGTH_SHORT).show();
                break;

            case "Connection failed":
                Toast.makeText(LoginActivity.this, "서버 연결 실패. 네트워크 상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
                break;

            default:
                Toast.makeText(LoginActivity.this, "Unexpected response from server: " + response, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socketActivity.disconnect();
    }
}
*/

// ----------------------------

// 회원가입 갔다가 로그인 안되는 현상 고치기 노력~
// 앱 키자마자 메세지박스 뜨는건 고쳐짐 그치만 회원가입 갔다가 로그인은 여전히 안됨.

/*

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

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

        registerButton.setOnClickListener(view -> {
            Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
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
                        Toast.makeText(LoginActivity.this, "서버 연결 실패. 네트워크 상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
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
            if (!isLoggingIn) {
                // 이미 로그인 요청이 완료된 상태라면 추가 처리를 하지 않음
                return;
            }
            switch (response) {
                case "LOGIN_SUCCESS":
                    isLoggingIn = false; // 로그인 성공 후 상태 해제
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                    break;

                case "Login Failed":
                    isLoggingIn = false; // 로그인 성공 후 상태 해제
                    Toast.makeText(LoginActivity.this, "아이디 혹은 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                    break;

                case "REGISTER_FAILURE: Invalid Registration Data":
                    Toast.makeText(LoginActivity.this, "등록된 사용자 정보가 없습니다. 회원가입을 먼저 진행해 주세요.", Toast.LENGTH_SHORT).show();
                    break;

                case "Connection failed":
                    Toast.makeText(LoginActivity.this, "서버 연결 실패. 네트워크 상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    isLoggingIn = false; // 로그인 성공 후 상태 해제
                    Toast.makeText(LoginActivity.this, "Unexpected response from server: " + response, Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        socketActivity.disconnect();
    }
}

 */

// -------------------------------------------------------------------------------------------------

// 회원가입 후 로그인 제발 고쳐져라

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

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

        registerButton.setOnClickListener(view -> {
            Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
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
                        Toast.makeText(LoginActivity.this, "서버 연결 실패. 네트워크 상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        // 로그인 성공 후 UI 갱신 및 MainActivity로 전환
                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                        finish(); // 현재 LoginActivity 종료
                        break;

                    case "Login Failed":
                        isLoggingIn = false; // 로그인 실패 후 상태 해제
                        Log.d(TAG, "Login failed.");
                        Toast.makeText(LoginActivity.this, "아이디 혹은 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                        loginButton.setEnabled(true); // 버튼 다시 활성화
                        break;

                    case "REGISTER_FAILURE: Invalid Registration Data":
                        Toast.makeText(LoginActivity.this, "등록된 사용자 정보가 없습니다. 회원가입을 먼저 진행해 주세요.", Toast.LENGTH_SHORT).show();
                        break;

                    case "Connection failed":
                        Toast.makeText(LoginActivity.this, "서버 연결 실패. 네트워크 상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        isLoggingIn = false; // 예기치 않은 응답 시 상태 해제
                        Log.d(TAG, "Unexpected response: " + response);
                        Toast.makeText(LoginActivity.this, "Unexpected response from server: " + response, Toast.LENGTH_SHORT).show();
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