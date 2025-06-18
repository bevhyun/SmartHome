package com.example.smarthome_v3;

/*

// SocketActivity에 LogWeather 서버만 정보만 추가했을 때 동작되는 코드!!!!
// 정상적으로 서버에서의 데이터를 받아오는 로그캣 출력

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.IOException;

public class SocketActivity {

    private static final String TAG = "SocketActivity";
    private static final String SERVER_IP = "192.168.0.180";
    private static final int SERVER_PORT = 12345;

    private static SocketActivity instance;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private OnDataReceivedListener dataListener;

    // Private constructor for Singleton
    private SocketActivity() { }

    public static synchronized SocketActivity getInstance() {
        if (instance == null) {
            instance = new SocketActivity();
        }
        return instance;
    }

    // 서버에 연결하고 요청을 보내는 메서드
    public void connectToServer(OnDataReceivedListener listener) {
        dataListener = listener;
        new Thread(() -> {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                writer = new PrintWriter(socket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Log.d(TAG, "Connected to server");

                // 서버로 데이터 요청
                requestDataFromServer();

                // 데이터 수신
                receiveDataFromServer();

            } catch (IOException e) {
                Log.e(TAG, "Failed to connect to server: " + e.getMessage());
            }
        }).start();
    }

    // 서버에 데이터 요청
    private void requestDataFromServer() {
        if (writer != null) {
            String message = "REQUEST_DATA";
            writer.println(message);
            writer.flush();
            Log.d(TAG, "Sent request to server: " + message);
        } else {
            Log.e(TAG, "Writer is null. Cannot send request.");
        }
    }

    // 서버로부터 데이터를 수신하는 메서드
    private void receiveDataFromServer() {
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                Log.d(TAG, "Received from server: " + line);
                if (dataListener != null) {
                    dataListener.onDataReceived(line);
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error reading from server: " + e.getMessage());
        }
    }

    // 서버와 연결을 종료하는 메서드
    public void disconnect() {
        try {
            if (writer != null) writer.close();
            if (reader != null) reader.close();
            if (socket != null) socket.close();
            Log.d(TAG, "Disconnected from server");
        } catch (IOException e) {
            Log.e(TAG, "Failed to disconnect: " + e.getMessage());
        }
    }

    // 데이터 수신 리스너 인터페이스 정의
    public interface OnDataReceivedListener {
        void onDataReceived(String data);
    }
}

*/

// -------------------------------------------------------------------------------------------------

// 회원가입 코드까지 추가했을 때!! (로그인은 안됨)
// [남은 코드] 로그인, 아두이노, ght api

/*

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.io.IOException;

public class SocketActivity {

    private static final String TAG = "SocketActivity";
    private static final String SERVER_IP = "192.168.0.180";
    private static final int SERVER_PORT = 12345;

    private static SocketActivity instance;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private OnDataReceivedListener dataListener;
    private boolean isConnecting = false;

    private SocketActivity() { }

    public static synchronized SocketActivity getInstance() {
        if (instance == null) {
            instance = new SocketActivity();
        }
        return instance;
    }

    public void connectToServer(OnDataReceivedListener listener) {
        if (isConnecting) return;
        isConnecting = true;
        dataListener = listener;

        new Thread(() -> {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                socket.setSoTimeout(30000); // 타임아웃 30초 설정
                writer = new PrintWriter(socket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Log.d(TAG, "Connected to server");

                // 서버로부터 데이터 수신
                receiveDataFromServer();

            } catch (IOException e) {
                Log.e(TAG, "Failed to connect to server: " + e.getMessage());
                if (dataListener != null) {
                    dataListener.onDataReceived("Connection failed: " + e.getMessage());
                }
            } finally {
                isConnecting = false;
            }
        }).start();
    }

    private boolean isSocketConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }

    public void sendSignupData(String username, String name, String phone, String password) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "REGISTER," + username + "," + name + "," + phone + "," + password;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "Sent signup data to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send signup data.");
        }
    }

    public void sendUsernameValidationRequest(String username) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "VALIDATE_USER," + username;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "Sent username validation request to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send validation request.");
        }
    }

    private void receiveDataFromServer() {
        try {
            String line;
            while (isSocketConnected() && (line = reader.readLine()) != null) {
                Log.d(TAG, "Received from server: " + line);
                if (dataListener != null) {
                    dataListener.onDataReceived(line);
                }
            }
        } catch (SocketTimeoutException e) {
            Log.e(TAG, "Socket read timed out: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "Error reading from server: " + e.getMessage());
            if (isSocketConnected()) {
                disconnect();
                connectToServer(dataListener);  // 재연결 시도
            }
        }
    }

    public void disconnect() {
        try {
            if (writer != null) writer.close();
            if (reader != null) reader.close();
            if (socket != null) socket.close();
            Log.d(TAG, "Disconnected from server");
        } catch (IOException e) {
            Log.e(TAG, "Failed to disconnect: " + e.getMessage());
        }
    }

    public interface OnDataReceivedListener {
        void onDataReceived(String data);
    }
}

*/

// -------------------------------------------------------------------------------------------------

/*

// 로그인 기능 구현 완료(회원가입은 안됨)
// [남은 코드] 아두이노, ght api

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.io.IOException;

public class SocketActivity {

    private static final String TAG = "SocketActivity";
    private static final String SERVER_IP = "192.168.0.180";
    private static final int SERVER_PORT = 12345;

    private static SocketActivity instance;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private OnDataReceivedListener dataListener;
    private boolean isConnecting = false;

    private SocketActivity() { }

    public static synchronized SocketActivity getInstance() {
        if (instance == null) {
            instance = new SocketActivity();
        }
        return instance;
    }

    public void connectToServer(OnDataReceivedListener listener) {
        if (isConnecting) return;
        isConnecting = true;
        dataListener = listener;

        new Thread(() -> {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                socket.setSoTimeout(30000); // 타임아웃 30초 설정
                writer = new PrintWriter(socket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Log.d(TAG, "Connected to server");

                // 서버로부터 데이터 수신
                receiveDataFromServer();

            } catch (IOException e) {
                Log.e(TAG, "Failed to connect to server: " + e.getMessage());
                if (dataListener != null) {
                    // 연결 실패 시 간단한 메시지만 전송
                    dataListener.onDataReceived("Connection failed");
                }
            } finally {
                isConnecting = false;
            }
        }).start();
    }

    private boolean isSocketConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }

    public void sendSignupData(String username, String name, String phone, String password) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "REGISTER," + username + "," + name + "," + phone + "," + password;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "Sent signup data to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send signup data.");
        }
    }

    public void sendUsernameValidationRequest(String username) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "VALIDATE_USER," + username;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "Sent username validation request to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send validation request.");
        }
    }

    // 로그인 데이터를 서버로 전송하는 메소드 추가
    public void sendLoginData(String username, String password) {
        if (isSocketConnected()) {
            new Thread(() -> {
                // 서버 형식에 맞춰 "Login_Data" 전송 후 로그인 데이터를 따로 전송
                writer.println("Login_Data");
                writer.flush();
                String message = username + "," + password;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "Sent login data to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send login data.");
        }
    }

    private void receiveDataFromServer() {
        try {
            String line;
            while (isSocketConnected() && (line = reader.readLine()) != null) {
                Log.d(TAG, "Received from server: " + line);
                if (dataListener != null) {
                    dataListener.onDataReceived(line);
                }
            }
        } catch (SocketTimeoutException e) {
            Log.e(TAG, "Socket read timed out: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "Error reading from server: " + e.getMessage());
            if (isSocketConnected()) {
                disconnect();
                connectToServer(dataListener);  // 재연결 시도
            }
        }
    }

    public void disconnect() {
        try {
            if (writer != null) writer.close();
            if (reader != null) reader.close();
            if (socket != null) socket.close();
            Log.d(TAG, "Disconnected from server");
        } catch (IOException e) {
            Log.e(TAG, "Failed to disconnect: " + e.getMessage());
        }
    }

    public interface OnDataReceivedListener {
        void onDataReceived(String data);
    }
}

*/

// -------------------------------------------------------------------------------------------------

// 이상하게 연결된 회원가입과 로그인..

/*

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.io.IOException;


public class SocketActivity {

    private static final String TAG = "SocketActivity";
    private static final String SERVER_IP = "192.168.0.180";
    private static final int SERVER_PORT = 12345;

    private static SocketActivity instance;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private OnDataReceivedListener dataListener;
    private boolean isConnecting = false;

    private SocketActivity() { }

    public static synchronized SocketActivity getInstance() {
        if (instance == null) {
            instance = new SocketActivity();
        }
        return instance;
    }

    public void connectToServer(OnDataReceivedListener listener) {
        if (isConnecting) return;

        // 기존 소켓이 열려 있다면 닫음
        if (socket != null && !socket.isClosed()) {
            disconnect();
        }

        isConnecting = true;
        dataListener = listener;

        new Thread(() -> {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                socket.setSoTimeout(30000); // 타임아웃 30초 설정
                writer = new PrintWriter(socket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Log.d(TAG, "Connected to server");

                // 서버로부터 데이터 수신
                receiveDataFromServer();
                // 서버로 데이터 요청
                requestDataFromServer();

                // 데이터 수신
                receiveDataFromServer();

            } catch (IOException e) {
                Log.e(TAG, "Failed to connect to server: " + e.getMessage());
                if (dataListener != null) {
                    dataListener.onDataReceived("Connection failed");
                }
            } finally {
                isConnecting = false;
            }
        }).start();
    }

    private boolean isSocketConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }

    // 회원가입 데이터 전송 메서드
    public void sendSignupData(String username, String name, String phone, String password) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "REGISTER," + username + "," + name + "," + phone + "," + password;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "Sent signup data to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send signup data.");
        }
    }

    // 로그인 데이터 전송 메서드
    public void sendLoginData(String username, String password) {
        if (isSocketConnected()) {
            new Thread(() -> {
                // 서버 형식에 맞춰 "Login_Data" 전송 후 로그인 데이터를 따로 전송
                writer.println("Login_Data");
                writer.flush();
                String message = username + "," + password;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "Sent login data to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send login data.");
        }
    }

    // 아이디 중복 확인 요청 메서드 추가
    public void sendUsernameValidationRequest(String username) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "VALIDATE_USER," + username;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "Sent username validation request to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send username validation request.");
        }
    }

    // 서버에 데이터 요청
    private void requestDataFromServer() {
        if (writer != null) {
            String message = "REQUEST_DATA";
            writer.println(message);
            writer.flush();
            Log.d(TAG, "Sent request to server: " + message);
        } else {
            Log.e(TAG, "Writer is null. Cannot send request.");
        }
    }

    private void receiveDataFromServer() {

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                Log.d(TAG, "Received from server: " + line);
                if (dataListener != null) {
                    dataListener.onDataReceived(line);
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error reading from server: " + e.getMessage());
        }

        try {
            String line;
            while (isSocketConnected() && (line = reader.readLine()) != null) {
                line = line.trim().replaceAll("[\\s\\u0000-\\u001F\\u007F]", ""); // 모든 공백 및 특수 문자 제거
                Log.d(TAG, "Raw data from server: '" + line + "'");
                if (dataListener != null) {
                    dataListener.onDataReceived(line);  // onDataReceived 호출
                }
            }
        } catch (SocketTimeoutException e) {
            Log.e(TAG, "Socket read timed out: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "Error reading from server: " + e.getMessage());
            if (isSocketConnected()) {
                disconnect();
                connectToServer(dataListener);  // 재연결 시도
            }
        }
    }

    public void disconnect() {
        try {
            if (writer != null) writer.close();
            if (reader != null) reader.close();
            if (socket != null) socket.close();
            Log.d(TAG, "Disconnected from server");
        } catch (IOException e) {
            Log.e(TAG, "Failed to disconnect: " + e.getMessage());
        }
    }

    public interface OnDataReceivedListener {
        void onDataReceived(String data);
    }
}

*/

// -------------------------------------------------------------------------------------------------

// TestSocket 02
// 나름 가능성 있는 코드........ 나의 한줄기의 빛..... 이거 망가지면 강 자퇴.... ☆

/*

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.io.IOException;


public class SocketActivity {

    private static final String TAG = "SocketActivity";
    private static final String SERVER_IP = "192.168.0.180";
    private static final int SERVER_PORT = 12345;

    private static SocketActivity instance;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private OnDataReceivedListener dataListener;
    private boolean isConnecting = false;

    private SocketActivity() { }

    public static synchronized SocketActivity getInstance() {
        if (instance == null) {
            instance = new SocketActivity();
        }
        return instance;
    }

    public void connectToServer(OnDataReceivedListener listener) {
        if (isConnecting) return;

        // 기존 소켓이 열려 있다면 닫음
        if (socket != null && !socket.isClosed()) {
            disconnect();
        }

        isConnecting = true;
        dataListener = listener;

        new Thread(() -> {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                socket.setSoTimeout(30000); // 타임아웃 30초 설정
                writer = new PrintWriter(socket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Log.d(TAG, "Connected to server");

                // 서버로부터 데이터 수신
                receiveDataFromServer();

                // 서버로 데이터 요청
                requestDataFromServer();

                // 데이터 수신
                receiveDataFromServer();

            } catch (IOException e) {
                Log.e(TAG, "Failed to connect to server: " + e.getMessage());
                if (dataListener != null) {
                    dataListener.onDataReceived("Connection failed");
                }
            } finally {
                isConnecting = false;
            }
        }).start();
    }

    private boolean isSocketConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }

    // 회원가입 데이터 전송 메서드
    public void sendSignupData(String username, String name, String phone, String password) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "REGISTER," + username + "," + name + "," + phone + "," + password;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "Sent signup data to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send signup data.");
        }
    }

    // 로그인 데이터 전송 메서드
    public void sendLoginData(String username, String password) {
        if (isSocketConnected()) {
            new Thread(() -> {
                // 서버 형식에 맞춰 "Login_Data" 전송 후 로그인 데이터를 따로 전송
                writer.println("Login_Data");
                writer.flush();
                String message = username + "," + password;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "Sent login data to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send login data.");
        }
    }

    // 아이디 중복 확인 요청 메서드 추가
    public void sendUsernameValidationRequest(String username) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "VALIDATE_USER," + username;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "Sent username validation request to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send username validation request.");
        }
    }

    // 서버에 데이터 요청
    private void requestDataFromServer() {
        if (writer != null) {
            String message = "REQUEST_DATA";
            writer.println(message);
            writer.flush();
            Log.d(TAG, "Sent request to server: " + message);
        } else {
            Log.e(TAG, "Writer is null. Cannot send request.");
        }
    }

    private void receiveDataFromServer() {

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                Log.d(TAG, "Received from server: " + line);
                if (dataListener != null) {
                    dataListener.onDataReceived(line);
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error reading from server: " + e.getMessage());
        }

        try {
            String line;
            while (isSocketConnected() && (line = reader.readLine()) != null) {
                line = line.trim().replaceAll("[\\s\\u0000-\\u001F\\u007F]", ""); // 모든 공백 및 특수 문자 제거
                Log.d(TAG, "Raw data from server: '" + line + "'");
                if (dataListener != null) {
                    dataListener.onDataReceived(line);  // onDataReceived 호출
                }
            }
        } catch (SocketTimeoutException e) {
            Log.e(TAG, "Socket read timed out: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "Error reading from server: " + e.getMessage());
            if (isSocketConnected()) {
                disconnect();
                connectToServer(dataListener);  // 재연결 시도
            }
        }
    }

    public void disconnect() {
        try {
            if (writer != null) writer.close();
            if (reader != null) reader.close();
            if (socket != null) socket.close();
            Log.d(TAG, "Disconnected from server");
        } catch (IOException e) {
            Log.e(TAG, "Failed to disconnect: " + e.getMessage());
        }
    }

    public interface OnDataReceivedListener {
        void onDataReceived(String data);
    }
}

*/

// -------------------------------------------------------------------------------------------------

// 회원가입 + 날씨 API 구현 됨

/*

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.io.IOException;

public class SocketActivity {

    private static final String TAG = "SocketActivity";
    private static final String SERVER_IP = "192.168.0.180";
    private static final int SERVER_PORT = 12345;

    private static SocketActivity instance;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private OnDataReceivedListener dataListener;
    private boolean isConnecting = false;

    private SocketActivity() { }

    public static synchronized SocketActivity getInstance() {
        if (instance == null) {
            instance = new SocketActivity();
        }
        return instance;
    }

    public void connectToServer(OnDataReceivedListener listener) {
        if (isConnecting) return;

        if (socket != null && !socket.isClosed()) {
            disconnect();
        }

        isConnecting = true;
        dataListener = listener;

        new Thread(() -> {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                socket.setSoTimeout(60000); // 타임아웃을 60초로 설정
                writer = new PrintWriter(socket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                Log.d(TAG, "Connected to server");

                requestDataFromServer();
                receiveDataFromServer(); // 데이터 수신을 한 번만 호출

            } catch (IOException e) {
                Log.e(TAG, "Failed to connect to server: " + e.getMessage());
                if (dataListener != null) {
                    dataListener.onDataReceived("Connection failed");
                }
            } finally {
                isConnecting = false;
            }
        }).start();
    }

    private boolean isSocketConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }


    // 회원가입 데이터
    public void sendSignupData(String username, String name, String phone, String password) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "REGISTER," + username + "," + name + "," + phone + "," + password;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "회원가입 :Sent signup data to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send signup data.");
        }
    }

    // 로그인 데이터
    public void sendLoginData(String username, String password) {
        if (isSocketConnected()) {
            new Thread(() -> {
                writer.println("Login_Data");
                writer.flush();
                String message = username + "," + password;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "로그인: Sent login data to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send login data.");
        }
    }

    // 중복 확인
    public void sendUsernameValidationRequest(String username) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "VALIDATE_USER," + username;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "Sent username validation request to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send username validation request.");
        }
    }

    private void requestDataFromServer() {
        if (writer != null) {
            String message = "REQUEST_DATA";
            writer.println(message);
            writer.flush();
            Log.d(TAG, "Sent request to server: " + message);
        } else {
            Log.e(TAG, "Writer is null. Cannot send request.");
        }
    }

    private void receiveDataFromServer() {
        new Thread(() -> {
            try {
                String line;
                while (isSocketConnected() && (line = reader.readLine()) != null) {
                    line = line.trim().replaceAll("[\\s\\u0000-\\u001F\\u007F]", ""); // 특수 문자 제거
                    Log.d(TAG, "Processed data received from server: '" + line + "'");
                    if (dataListener != null) {
                        dataListener.onDataReceived(line);
                    }
                }
            } catch (SocketTimeoutException e) {
                Log.e(TAG, "Socket read timed out: " + e.getMessage());
                attemptReconnect();
            } catch (IOException e) {
                Log.e(TAG, "Error reading from server: " + e.getMessage());
                if (isSocketConnected()) {
                    disconnect();
                    attemptReconnect();
                }
            }
        }).start();
    }

    private void attemptReconnect() {
        disconnect();
        try {
            Thread.sleep(5000); // 5초 대기 후 재연결 시도
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        connectToServer(dataListener);
    }

    public void disconnect() {
        try {
            if (writer != null) writer.close();
            if (reader != null) reader.close();
            if (socket != null) socket.close();
            Log.d(TAG, "Disconnected from server");
        } catch (IOException e) {
            Log.e(TAG, "Failed to disconnect: " + e.getMessage());
        }
    }

    public interface OnDataReceivedListener {
        void onDataReceived(String data);
    }
}

*/

// -------------------------------------------------------------------------------------------------

// 회원가입 + 날씨 API 구현 됨

/*

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.io.IOException;

public class SocketActivity {

    private static final String TAG = "SocketActivity";
    private static final String SERVER_IP = "192.168.0.180";
    private static final int SERVER_PORT = 12345;

    private static SocketActivity instance;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private OnDataReceivedListener dataListener;
    private boolean isConnecting = false;

    private SocketActivity() { }

    public static synchronized SocketActivity getInstance() {
        if (instance == null) {
            instance = new SocketActivity();
        }
        return instance;
    }

    public void connectToServer(OnDataReceivedListener listener) {
        if (isConnecting) return;

        if (socket != null && !socket.isClosed()) {
            disconnect();
        }

        isConnecting = true;
        dataListener = listener;

        new Thread(() -> {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                socket.setSoTimeout(60000); // 타임아웃을 60초로 설정
                writer = new PrintWriter(socket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                Log.d(TAG, "Connected to server");

                requestDataFromServer();
                receiveDataFromServer(); // 데이터 수신을 한 번만 호출

            } catch (IOException e) {
                Log.e(TAG, "Failed to connect to server: " + e.getMessage());
                if (dataListener != null) {
                    dataListener.onDataReceived("Connection failed");
                }
            } finally {
                isConnecting = false;
            }
        }).start();
    }

    private boolean isSocketConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }


    // 회원가입 데이터
    public void sendSignupData(String username, String name, String phone, String password) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "REGISTER," + username + "," + name + "," + phone + "," + password;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "회원가입 :Sent signup data to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send signup data.");
        }
    }

    // 로그인 데이터
    public void sendLoginData(String username, String password) {
        if (isSocketConnected()) {
            new Thread(() -> {
                writer.println("Login_Data");
                writer.flush();
                String message = username + "," + password;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "로그인: Sent login data to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send login data.");
        }
    }

    // 중복 확인
    public void sendUsernameValidationRequest(String username) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "VALIDATE_USER," + username;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "Sent username validation request to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send username validation request.");
        }
    }

    private void requestDataFromServer() {
        if (writer != null) {
            String message = "REQUEST_DATA";
            writer.println(message);
            writer.flush();
            Log.d(TAG, "Sent request to server: " + message);
        } else {
            Log.e(TAG, "Writer is null. Cannot send request.");
        }
    }

    private void receiveDataFromServer() {
        new Thread(() -> {
            try {
                String line;
                while (isSocketConnected() && (line = reader.readLine()) != null) {
                    line = line.trim().replaceAll("[\\s\\u0000-\\u001F\\u007F]", ""); // 특수 문자 제거
                    Log.d(TAG, "Processed data received from server: '" + line + "'");
                    if (dataListener != null) {
                        dataListener.onDataReceived(line);
                    }
                }
            } catch (SocketTimeoutException e) {
                Log.e(TAG, "Socket read timed out: " + e.getMessage());
                attemptReconnect();
            } catch (IOException e) {
                Log.e(TAG, "Error reading from server: " + e.getMessage());
                if (isSocketConnected()) {
                    disconnect();
                    attemptReconnect();
                }
            }
        }).start();
    }

    private void attemptReconnect() {
        disconnect();
        try {
            Thread.sleep(5000); // 5초 대기 후 재연결 시도
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        connectToServer(dataListener);
    }

    public void disconnect() {
        try {
            if (writer != null) writer.close();
            if (reader != null) reader.close();
            if (socket != null) socket.close();
            Log.d(TAG, "Disconnected from server");
        } catch (IOException e) {
            Log.e(TAG, "Failed to disconnect: " + e.getMessage());
        }
    }

    public interface OnDataReceivedListener {
        void onDataReceived(String data);
    }
}

 */

// -------------------------------------------------------------------------------------------------

// 이상한 메세지가 뜨지만...? 동작은 되는 코드.. ㅋ (회원가입+로그인+메인화면)
/*

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.io.IOException;

public class SocketActivity {

    private static final String TAG = "SocketActivity";
    private static final String SERVER_IP = "192.168.1.105";
    private static final int SERVER_PORT = 12345;

    private static SocketActivity instance;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private OnDataReceivedListener dataListener;
    private boolean isConnecting = false;

    private SocketActivity() { }

    public static synchronized SocketActivity getInstance() {
        if (instance == null) {
            instance = new SocketActivity();
        }
        return instance;
    }

    public void connectToServer(OnDataReceivedListener listener) {
        if (isConnecting) return;

        if (socket != null && !socket.isClosed()) {
            disconnect();
        }

        isConnecting = true;
        dataListener = listener;

        new Thread(() -> {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                socket.setSoTimeout(60000); // 타임아웃을 60초로 설정
                writer = new PrintWriter(socket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                Log.d(TAG, "Connected to server");

                requestDataFromServer();
                receiveDataFromServer(); // 데이터 수신을 한 번만 호출

            } catch (IOException e) {
                Log.e(TAG, "Failed to connect to server: " + e.getMessage());
                if (dataListener != null) {
                    dataListener.onDataReceived("Connection failed");
                }
            } finally {
                isConnecting = false;
            }
        }).start();
    }

    boolean isSocketConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }


    // 회원가입 데이터
    public void sendSignupData(String username, String name, String phone, String password) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "REGISTER," + username + "," + name + "," + phone + "," + password;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "회원가입 :Sent signup data to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send signup data.");
        }
    }

    // 로그인 데이터
    public void sendLoginData(String username, String password) {
        if (isSocketConnected()) {
            new Thread(() -> {
                writer.println("Login_Data");
                writer.flush();
                String message = username + "," + password;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "로그인: Sent login data to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Attempting to reconnect and resend login data.");
            connectToServer(data -> {
                // 재연결 후 로그인 데이터 전송
                sendLoginData(username, password);
            });
        }
    }

    // 중복 확인
    public void sendUsernameValidationRequest(String username) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "VALIDATE_USER," + username;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "Sent username validation request to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send username validation request.");
        }
    }


    // 날씨를 받아오는 코드
    private void requestDataFromServer() {
        if (writer != null) {
            String message = "REQUEST_DATA";
            writer.println(message);
            writer.flush();
            Log.d(TAG, "Sent request to server: " + message);
        } else {
            Log.e(TAG, "Writer is null. Cannot send request.");
        }
    }

    private void receiveDataFromServer() {
        new Thread(() -> {
            try {
                String line;
                while (isSocketConnected() && (line = reader.readLine()) != null) {
                    line = line.trim().replaceAll("[\\s\\u0000-\\u001F\\u007F]", ""); // 특수 문자 제거
                    Log.d(TAG, "Processed data received from server: '" + line + "'");
                    if (dataListener != null) {
                        dataListener.onDataReceived(line);
                    }
                }
            } catch (SocketTimeoutException e) {
                Log.e(TAG, "Socket read timed out: " + e.getMessage());
                attemptReconnect();
            } catch (IOException e) {
                Log.e(TAG, "Error reading from server: " + e.getMessage());
                if (isSocketConnected()) {
                    disconnect();
                    attemptReconnect();
                }
            }
        }).start();
    }

    private void attemptReconnect() {
        disconnect();
        try {
            Thread.sleep(5000); // 5초 대기 후 재연결 시도
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        connectToServer(dataListener);
    }

    public void disconnect() {
        try {
            if (writer != null) writer.close();
            if (reader != null) reader.close();
            if (socket != null) socket.close();
            Log.d(TAG, "Disconnected from server");
        } catch (IOException e) {
            Log.e(TAG, "Failed to disconnect: " + e.getMessage());
        }
    }

    public interface OnDataReceivedListener {
        void onDataReceived(String data);
    }
}

 */

// -------------------------------------------------------------------------------------------------

// 이상한 메세지가 뜨는거 수정하려고 노력 중... 이상한 메세지 뜨는거 말고 다 괜찬음

/*

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.io.IOException;

public class SocketActivity {

    private static final String TAG = "SocketActivity";
    private static final String SERVER_IP = "192.168.0.180";
    private static final int SERVER_PORT = 12345;

    private static SocketActivity instance;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private OnDataReceivedListener dataListener;
    private boolean isConnecting = false;

    private SocketActivity() { }

    public static synchronized SocketActivity getInstance() {
        if (instance == null) {
            instance = new SocketActivity();
        }
        return instance;
    }

    public void connectToServer(OnDataReceivedListener listener) {
        if (isConnecting) return;

        if (socket != null && !socket.isClosed()) {
            disconnect();
        }

        isConnecting = true;
        dataListener = listener;

        new Thread(() -> {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                socket.setSoTimeout(60000); // 타임아웃을 60초로 설정
                writer = new PrintWriter(socket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                Log.d(TAG, "Connected to server");
//'']
                requestDataFromServer();
                receiveDataFromServer(); // 데이터 수신을 한 번만 호출

            } catch (IOException e) {
                Log.e(TAG, "Failed to connect to se                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               rver: " + e.getMessage());
                if (dataListener != null) {
                    dataListener.onDataReceived("Connection failed");
                }
            } finally {
                isConnecting = false;
            }
        }).start();
    }


    boolean isSocketConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }


    // 회원가입 데이터
    public void sendSignupData(String username, String name, String phone, String password) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "REGISTER," + username + "," + name + "," + phone + "," + password;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "회원가입 :Sent signup data to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send signup data.");
        }
    }

    // 로그인 데이터
    public void sendLoginData(String username, String password) {
        if (isSocketConnected()) {
            new Thread(() -> {
                writer.println("Login_Data");
                writer.flush();
                String message = username + "," + password;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "로그인: Sent login data to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Attempting to reconnect and resend login data.");
            connectToServer(data -> {
                // 재연결 후 로그인 데이터 전송
                sendLoginData(username, password);
            });
        }
    }

    // 중복 확인
    public void sendUsernameValidationRequest(String username) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "VALIDATE_USER," + username;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "Sent username validation request to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send username validation request.");
        }
    }


    // 날씨를 받아오는 코드
    void requestDataFromServer() {
        if (writer != null) {
            String message = "REQUEST_DATA";
            writer.println(message);
            writer.flush();
            Log.d(TAG, "Sent request to server: " + message);
        } else {
            Log.e(TAG, "Writer is null. Cannot send request.");
        }
    }

    private void receiveDataFromServer() {
        new Thread(() -> {
            try {
                String line;
                while (isSocketConnected() && (line = reader.readLine()) != null) {
                    line = line.trim().replaceAll("[\\s\\u0000-\\u001F\\u007F]", ""); // 특수 문자 제거
                    Log.d(TAG, "Processed data received from server: '" + line + "'");
                    if (dataListener != null) {
                        dataListener.onDataReceived(line);
                    }
                }
            } catch (SocketTimeoutException e) {
                Log.e(TAG, "Socket read timed out: " + e.getMessage());
                attemptReconnect();
            } catch (IOException e) {
                Log.e(TAG, "Error reading from server: " + e.getMessage());
                if (isSocketConnected()) {
                    disconnect();
                    attemptReconnect();
                }
            }
        }).start();
    }

    private void attemptReconnect() {
        disconnect();
        try {
            Thread.sleep(5000); // 5초 대기 후 재연결 시도
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        connectToServer(dataListener);
    }

    public void disconnect() {
        try {
            if (writer != null) writer.close();
            if (reader != null) reader.close();
            if (socket != null) socket.close();
            Log.d(TAG, "Disconnected from server");
        } catch (IOException e) {
            Log.e(TAG, "Failed to disconnect: " + e.getMessage());
        }
    }

    public interface OnDataReceivedListener {
        void onDataReceived(String data);
    }
}

 */

// -------------------------------------------------------------------------------------------------

// 아두이노 연결 완료!!

/*

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.io.IOException;

public class SocketActivity {

    private static final String TAG = "SocketActivity";
    private static final String SERVER_IP = "192.168.0.180";
    private static final int SERVER_PORT = 12345;

    private static SocketActivity instance;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private OnDataReceivedListener dataListener;
    private boolean isConnecting = false;

    private SocketActivity() { }

    public static synchronized SocketActivity getInstance() {
        if (instance == null) {
            instance = new SocketActivity();
        }
        return instance;
    }

    public void connectToServer(OnDataReceivedListener listener) {
        if (isConnecting) return;

        if (socket != null && !socket.isClosed()) {
            disconnect();
        }

        isConnecting = true;
        dataListener = listener;

        new Thread(() -> {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                socket.setSoTimeout(60000); // 타임아웃을 60초로 설정
                writer = new PrintWriter(socket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                Log.d(TAG, "Connected to server");
//'']
                requestDataFromServer();
                receiveDataFromServer(); // 데이터 수신을 한 번만 호출
                sendRequestSensorData();

            } catch (IOException e) {
                Log.e(TAG, "Failed to connect to se                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               rver: " + e.getMessage());
                if (dataListener != null) {
                    dataListener.onDataReceived("Connection failed");
                }
            } finally {
                isConnecting = false;
            }
        }).start();
    }


    boolean isSocketConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }


    // 회원가입 데이터
    public void sendSignupData(String username, String name, String phone, String password) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "REGISTER," + username + "," + name + "," + phone + "," + password;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "회원가입 :Sent signup data to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send signup data.");
        }
    }

    // 로그인 데이터
    public void sendLoginData(String username, String password) {
        if (isSocketConnected()) {
            new Thread(() -> {
                writer.println("Login_Data");
                writer.flush();
                String message = username + "," + password;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "로그인: Sent login data to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Attempting to reconnect and resend login data.");
            connectToServer(data -> {
                // 재연결 후 로그인 데이터 전송
                sendLoginData(username, password);
            });
        }
    }

    // 중복 확인
    public void sendUsernameValidationRequest(String username) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "VALIDATE_USER," + username;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "Sent username validation request to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send username validation request.");
        }
    }


    // 날씨를 받아오는 코드 (main)
    void requestDataFromServer() {
        if (writer != null) {
            String message = "REQUEST_DATA";
            writer.println(message);
            writer.flush();
            Log.d(TAG, "Sent request to server: " + message);
        } else {
            Log.e(TAG, "Writer is null. Cannot send request.");
        }
    }


    // 날씨 데이터 + 센서 데이터 받아오는 코드 (home)
    void sendRequestSensorData() {
        if (writer != null) {
            String message = "MAIN_DATA";
            writer.println(message);
            writer.flush();
            Log.d(TAG, "Sent request to server: " + message);
        } else {
            Log.e(TAG, "Writer is null. Cannot send request.");
        }
    }



    private void receiveDataFromServer() {
        new Thread(() -> {
            try {
                String line;
                while (isSocketConnected() && (line = reader.readLine()) != null) {
                    line = line.trim().replaceAll("[\\s\\u0000-\\u001F\\u007F]", ""); // 특수 문자 제거
                    Log.d(TAG, "Processed data received from server: '" + line + "'");

                    // 센서 데이터인지 확인하여 별도 처리
                    if (line.startsWith("SENSOR_DATA")) {
                        String sensorData = line.substring("SENSOR_DATA".length());
                        Log.d(TAG, "Received sensor data: " + sensorData);
                        if (dataListener != null) {
                            dataListener.onDataReceived(sensorData); // 센서 데이터 전달
                        }
                    } else {
                        // 그 외 데이터 처리
                        if (dataListener != null) {
                            dataListener.onDataReceived(line);
                        }
                    }
                }
            } catch (SocketTimeoutException e) {
                Log.e(TAG, "Socket read timed out: " + e.getMessage());
                attemptReconnect();
            } catch (IOException e) {
                Log.e(TAG, "Error reading from server: " + e.getMessage());
                if (isSocketConnected()) {
                    disconnect();
                    attemptReconnect();
                }
            }
        }).start();
    }

    private void attemptReconnect() {
        disconnect();
        try {
            Thread.sleep(5000); // 5초 대기 후 재연결 시도
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        connectToServer(dataListener);
    }

    public void disconnect() {
        try {
            if (writer != null) writer.close();
            if (reader != null) reader.close();
            if (socket != null) socket.close();
            Log.d(TAG, "Disconnected from server");
        } catch (IOException e) {
            Log.e(TAG, "Failed to disconnect: " + e.getMessage());
        }
    }

    public interface OnDataReceivedListener {
        void onDataReceived(String data);
    }
}

*/

// -------------------------------------------------------------------------------------------------

// gpt 고치기 완료!! 팬 동작 전까지 다 동작됨!

/*

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.io.IOException;

public class SocketActivity {

    private static final String TAG = "SocketActivity";
    private static final String SERVER_IP = "192.168.0.180";
    private static final int SERVER_PORT = 12345;

    private static SocketActivity instance;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private OnDataReceivedListener dataListener;
    private boolean isConnecting = false;

    private SocketActivity() { }

    public static synchronized SocketActivity getInstance() {
        if (instance == null) {
            instance = new SocketActivity();
        }
        return instance;
    }

    public void connectToServer(OnDataReceivedListener listener) {
        if (isConnecting) return;

        if (socket != null && !socket.isClosed()) {
            disconnect();
        }

        isConnecting = true;
        dataListener = listener;

        new Thread(() -> {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                socket.setSoTimeout(60000); // 타임아웃을 60초로 설정
                writer = new PrintWriter(socket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                Log.d(TAG, "Connected to server");
//'']
                requestDataFromServer();
                receiveDataFromServer(); // 데이터 수신을 한 번만 호출
                sendRequestSensorData();
                RequestTipData();

            } catch (IOException e) {
                Log.e(TAG, "Failed to connect to se                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               rver: " + e.getMessage());
                if (dataListener != null) {
                    dataListener.onDataReceived("Connection failed");
                }
            } finally {
                isConnecting = false;
            }
        }).start();
    }


    boolean isSocketConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }


    // 회원가입 데이터
    public void sendSignupData(String username, String name, String phone, String password) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "REGISTER," + username + "," + name + "," + phone + "," + password;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "회원가입 :Sent signup data to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send signup data.");
        }
    }

    // 로그인 데이터
    public void sendLoginData(String username, String password) {
        if (isSocketConnected()) {
            new Thread(() -> {
                writer.println("Login_Data");
                writer.flush();
                String message = username + "," + password;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "로그인: Sent login data to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Attempting to reconnect and resend login data.");
            connectToServer(data -> {
                // 재연결 후 로그인 데이터 전송
                sendLoginData(username, password);
            });
        }
    }

    // 중복 확인
    public void sendUsernameValidationRequest(String username) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "VALIDATE_USER," + username;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "Sent username validation request to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send username validation request.");
        }
    }


    // 날씨를 받아오는 코드 (main)
    void requestDataFromServer() {
        if (writer != null) {
            String message = "REQUEST_DATA";
            writer.println(message);
            writer.flush();
            Log.d(TAG, "Sent request to server: " + message);
        } else {
            Log.e(TAG, "Writer is null. Cannot send request.");
        }
    }


    // 날씨 데이터 + 센서 데이터 받아오는 코드 (home)
    void sendRequestSensorData() {
        if (writer != null) {
            String message = "MAIN_DATA";
            writer.println(message);
            writer.flush();
            Log.d(TAG, "Sent request to server: " + message);
        } else {
            Log.e(TAG, "Writer is null. Cannot send request.");
        }
    }

    // 날씨 데이터 + 센서 데이터 받아오는 코드 (home)
    void RequestTipData() {
        if (writer != null) {
            String message = "Tip_DATA";
            writer.println(message);
            writer.flush();
            Log.d(TAG, "Sent request to server: " + message);
        } else {
            Log.e(TAG, "Writer is null. Cannot send request.");
        }
    }



    private void receiveDataFromServer() {
        new Thread(() -> {
            try {
                String line;
                while (isSocketConnected() && (line = reader.readLine()) != null) {
                    line = line.trim().replaceAll("[\\s\\u0000-\\u001F\\u007F]", ""); // 특수 문자 제거
                    Log.d(TAG, "Processed data received from server: '" + line + "'");

                    // 센서 데이터인지 확인하여 별도 처리
                    if (line.startsWith("SENSOR_DATA")) {
                        String sensorData = line.substring("SENSOR_DATA".length());
                        Log.d(TAG, "Received sensor data: " + sensorData);
                        if (dataListener != null) {
                            dataListener.onDataReceived(sensorData); // 센서 데이터 전달
                        }
                    } else {
                        // 그 외 데이터 처리
                        if (dataListener != null) {
                            dataListener.onDataReceived(line);
                        }
                    }
                }
            } catch (SocketTimeoutException e) {
                Log.e(TAG, "Socket read timed out: " + e.getMessage());
                attemptReconnect();
            } catch (IOException e) {
                Log.e(TAG, "Error reading from server: " + e.getMessage());
                if (isSocketConnected()) {
                    disconnect();
                    attemptReconnect();
                }
            }
        }).start();
    }

    private void attemptReconnect() {
        disconnect();
        try {
            Thread.sleep(5000); // 5초 대기 후 재연결 시도
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        connectToServer(dataListener);
    }

    public void disconnect() {
        try {
            if (writer != null) writer.close();
            if (reader != null) reader.close();
            if (socket != null) socket.close();
            Log.d(TAG, "Disconnected from server");
        } catch (IOException e) {
            Log.e(TAG, "Failed to disconnect: " + e.getMessage());
        }
    }

    public interface OnDataReceivedListener {
        void onDataReceived(String data);
    }
}

*/

// -------------------------------------------------------------------------------------------------

// 팬모터 등등등 들 동작 완료~!

/*

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.io.IOException;

public class SocketActivity {

    private static final String TAG = "SocketActivity";
    private static final String SERVER_IP = "192.168.0.180";
    private static final int SERVER_PORT = 12345;

    private static SocketActivity instance;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private OnDataReceivedListener dataListener;
    private boolean isConnecting = false;

    private SocketActivity() { }

    public static synchronized SocketActivity getInstance() {
        if (instance == null) {
            instance = new SocketActivity();
        }
        return instance;
    }

    public void connectToServer(OnDataReceivedListener listener) {
        if (isConnecting) return;

        if (socket != null && !socket.isClosed()) {
            disconnect();
        }

        isConnecting = true;
        dataListener = listener;

        new Thread(() -> {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                socket.setSoTimeout(60000); // 타임아웃을 60초로 설정
                writer = new PrintWriter(socket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                Log.d(TAG, "Connected to server");
//'']
                requestDataFromServer();
                receiveDataFromServer(); // 데이터 수신을 한 번만 호출
                sendRequestSensorData();
                RequestTipData();

            } catch (IOException e) {
                Log.e(TAG, "Failed to connect to se                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               rver: " + e.getMessage());
                if (dataListener != null) {
                    dataListener.onDataReceived("Connection failed");
                }
            } finally {
                isConnecting = false;
            }
        }).start();
    }

    public void sendData(String data) {
        if (isSocketConnected()) {
            new Thread(() -> {
                writer.println(data);
                writer.flush();
                Log.d(TAG, "Data sent to server: " + data);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send data.");
        }
    }

    boolean isSocketConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }


    // 회원가입 데이터
    public void sendSignupData(String username, String name, String phone, String password) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "REGISTER," + username + "," + name + "," + phone + "," + password;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "회원가입 :Sent signup data to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send signup data.");
        }
    }

    // 로그인 데이터
    public void sendLoginData(String username, String password) {
        if (isSocketConnected()) {
            new Thread(() -> {
                writer.println("Login_Data");
                writer.flush();
                String message = username + "," + password;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "로그인: Sent login data to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Attempting to reconnect and resend login data.");
            connectToServer(data -> {
                // 재연결 후 로그인 데이터 전송
                sendLoginData(username, password);
            });
        }
    }

    // 중복 확인
    public void sendUsernameValidationRequest(String username) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "VALIDATE_USER," + username;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "Sent username validation request to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send username validation request.");
        }
    }


    // 날씨를 받아오는 코드 (main)
    void requestDataFromServer() {
        if (writer != null) {
            String message = "REQUEST_DATA";
            writer.println(message);
            writer.flush();
            Log.d(TAG, "Sent request to server: " + message);
        } else {
            Log.e(TAG, "Writer is null. Cannot send request.");
        }
    }


    // 날씨 데이터 + 센서 데이터 받아오는 코드 (home)
    void sendRequestSensorData() {
        if (writer != null) {
            String message = "MAIN_DATA";
            writer.println(message);
            writer.flush();
            Log.d(TAG, "Sent request to server: " + message);
        } else {
            Log.e(TAG, "Writer is null. Cannot send request.");
        }
    }

    // 날씨 데이터 + 센서 데이터 받아오는 코드 (home)
    void RequestTipData() {
        if (writer != null) {
            String message = "Tip_DATA";
            writer.println(message);
            writer.flush();
            Log.d(TAG, "Sent request to server: " + message);
        } else {
            Log.e(TAG, "Writer is null. Cannot send request.");
        }
    }



    private void receiveDataFromServer() {
        new Thread(() -> {
            try {
                String line;
                while (isSocketConnected() && (line = reader.readLine()) != null) {
                    line = line.trim().replaceAll("[\\s\\u0000-\\u001F\\u007F]", ""); // 특수 문자 제거
                    Log.d(TAG, "Processed data received from server: '" + line + "'");

                    // 센서 데이터인지 확인하여 별도 처리
                    if (line.startsWith("SENSOR_DATA")) {
                        String sensorData = line.substring("SENSOR_DATA".length());
                        Log.d(TAG, "Received sensor data: " + sensorData);
                        if (dataListener != null) {
                            dataListener.onDataReceived(sensorData); // 센서 데이터 전달
                        }
                    } else {
                        // 그 외 데이터 처리
                        if (dataListener != null) {
                            dataListener.onDataReceived(line);
                        }
                    }
                }
            } catch (SocketTimeoutException e) {
                Log.e(TAG, "Socket read timed out: " + e.getMessage());
                attemptReconnect();
            } catch (IOException e) {
                Log.e(TAG, "Error reading from server: " + e.getMessage());
                if (isSocketConnected()) {
                    disconnect();
                    attemptReconnect();
                }
            }
        }).start();
    }

    private void attemptReconnect() {
        disconnect();
        try {
            Thread.sleep(5000); // 5초 대기 후 재연결 시도
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        connectToServer(dataListener);
    }

    public void disconnect() {
        try {
            if (writer != null) writer.close();
            if (reader != null) reader.close();
            if (socket != null) socket.close();
            Log.d(TAG, "Disconnected from server");
        } catch (IOException e) {
            Log.e(TAG, "Failed to disconnect: " + e.getMessage());
        }
    }

    public interface OnDataReceivedListener {
        void onDataReceived(String data);
    }
}

*/

// -------------------------------------------------------------------------------------------------

// 자동 온도 추천 기능 구현 완료 !!!!

/*

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.io.IOException;

public class SocketActivity {

    private static final String TAG = "SocketActivity";
    private static final String SERVER_IP = "192.168.0.180";
    // 아산 자취방 ip            192.168.1.105

    private static final int SERVER_PORT = 12345;

    private static SocketActivity instance;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private OnDataReceivedListener dataListener;
    private boolean isConnecting = false;
    private boolean isLoginInProgress = false; // 로그인 진행 상태 플래그

    private SocketActivity() { }

    public static synchronized SocketActivity getInstance() {
        if (instance == null) {
            instance = new SocketActivity();
        }
        return instance;
    }

    public void connectToServer(OnDataReceivedListener listener) {

        if (isConnecting) return;

        isConnecting = true;
        dataListener = listener;

        new Thread(() -> {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                socket.setSoTimeout(60000); // 타임아웃을 60초로 설정
                writer = new PrintWriter(socket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                Log.d(TAG, "Connected to server");
//'']
                requestDataFromServer();
                receiveDataFromServer(); // 데이터 수신을 한 번만 호출
                sendRequestSensorData();
                RequestTipData();

            } catch (IOException e) {
                Log.e(TAG, "Failed to connect to se                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               rver: " + e.getMessage());
                if (dataListener != null) {
                    dataListener.onDataReceived("Connection failed");
                }
            } finally {
                isConnecting = false;
            }
        }).start();
    }

    public void sendData(String data) {
        if (isSocketConnected()) {
            new Thread(() -> {
                writer.println(data);
                writer.flush();
                Log.d(TAG, "Data sent to server: " + data);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send data.");
        }
    }

    boolean isSocketConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }


    // 회원가입 데이터
    public void sendSignupData(String username, String name, String phone, String password) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "REGISTER," + username + "," + name + "," + phone + "," + password;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "회원가입 :Sent signup data to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send signup data.");
        }
    }

    // 로그인 데이터
    public void sendLoginData(String username, String password) {

        if (isLoginInProgress) {
            Log.d(TAG, "Login request is already in progress.");
            return; // 중복 요청 방지
        }

        if (isSocketConnected()) {

            new Thread(() -> {
                writer.println("Login_Data");
                writer.flush();
                String message = username + "," + password;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "로그인: Sent login data to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Attempting to reconnect and resend login data.");
            connectToServer(data -> {
                // 재연결 후 로그인 데이터 전송
                sendLoginData(username, password);
            });
        }
    }

    // 중복 확인
    public void sendUsernameValidationRequest(String username) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "VALIDATE_USER," + username;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "Sent username validation request to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send username validation request.");
        }
    }


    // 날씨를 받아오는 코드 (main)
    void requestDataFromServer() {
        if (writer != null) {
            String message = "REQUEST_DATA";
            writer.println(message);
            writer.flush();
            Log.d(TAG, "Sent request to server: " + message);
        } else {
            Log.e(TAG, "Writer is null. Cannot send request.");
        }
    }


    // 날씨 데이터 + 센서 데이터 받아오는 코드 (home)
    void sendRequestSensorData() {
        if (writer != null) {
            String message = "MAIN_DATA";
            writer.println(message);
            writer.flush();
            Log.d(TAG, "Sent request to server: " + message);
        } else {
            Log.e(TAG, "Writer is null. Cannot send request.");
        }
    }

    // 날씨 데이터 + 센서 데이터 받아오는 코드 (home)
    void RequestTipData() {
        if (writer != null) {
            String message = "Tip_DATA";
            writer.println(message);
            writer.flush();
            Log.d(TAG, "Sent request to server: " + message);
        } else {
            Log.e(TAG, "Writer is null. Cannot send request.");
        }
    }



    private void receiveDataFromServer() {
        new Thread(() -> {
            try {
                String line;
                while (isSocketConnected() && (line = reader.readLine()) != null) {
                    line = line.trim().replaceAll("[\\s\\u0000-\\u001F\\u007F]", ""); // 특수 문자 제거
                    Log.d(TAG, "Processed data received from server: '" + line + "'");

                    // 센서 데이터인지 확인하여 별도 처리
                    if (line.startsWith("SENSOR_DATA")) {
                        String sensorData = line.substring("SENSOR_DATA".length());
                        Log.d(TAG, "Received sensor data: " + sensorData);
                        if (dataListener != null) {
                            dataListener.onDataReceived(sensorData); // 센서 데이터 전달
                        }
                    } else {
                        // 그 외 데이터 처리
                        if (dataListener != null) {
                            dataListener.onDataReceived(line);
                        }
                    }
                }
            } catch (SocketTimeoutException e) {
                Log.e(TAG, "Socket read timed out: " + e.getMessage());
                attemptReconnect();
            } catch (IOException e) {
                Log.e(TAG, "Error reading from server: " + e.getMessage());
                if (isSocketConnected()) {
                    disconnect();
                    attemptReconnect();
                }
            }
        }).start();
    }

    private void attemptReconnect() {
        disconnect();
        try {
            Thread.sleep(5000); // 5초 대기 후 재연결 시도
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        connectToServer(dataListener);
    }

    public void disconnect() {
        try {
            if (writer != null) writer.close();
            if (reader != null) reader.close();
            if (socket != null) socket.close();
            Log.d(TAG, "Disconnected from server");
        } catch (IOException e) {
            Log.e(TAG, "Failed to disconnect: " + e.getMessage());
        }
    }

    public interface OnDataReceivedListener {
        void onDataReceived(String data);
    }
}

*/

// ------------

// tip 기능 GPT Api 공백 추가 완료

/*

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.io.IOException;

public class SocketActivity {

    private static final String TAG = "SocketActivity";
    private static final String SERVER_IP = "192.168.0.180";
    // 아산 자취방 ip            192.168.1.105

    private static final int SERVER_PORT = 12345;

    private static SocketActivity instance;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private OnDataReceivedListener dataListener;
    private boolean isConnecting = false;
    private boolean isLoginInProgress = false; // 로그인 진행 상태 플래그

    private SocketActivity() { }

    public static synchronized SocketActivity getInstance() {
        if (instance == null) {
            instance = new SocketActivity();
        }
        return instance;
    }

    public void connectToServer(OnDataReceivedListener listener) {

        if (isConnecting) return;

        isConnecting = true;
        dataListener = listener;

        new Thread(() -> {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                socket.setSoTimeout(60000); // 타임아웃을 60초로 설정
                writer = new PrintWriter(socket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                Log.d(TAG, "Connected to server");
//'']
                requestDataFromServer();
                receiveDataFromServer(); // 데이터 수신을 한 번만 호출
                sendRequestSensorData();
                RequestTipData();

            } catch (IOException e) {
                Log.e(TAG, "Failed to connect to se                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               rver: " + e.getMessage());
                if (dataListener != null) {
                    dataListener.onDataReceived("Connection failed");
                }
            } finally {
                isConnecting = false;
            }
        }).start();
    }

    public void sendData(String data) {
        if (isSocketConnected()) {
            new Thread(() -> {
                writer.println(data);
                writer.flush();
                Log.d(TAG, "Data sent to server: " + data);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send data.");
        }
    }

    boolean isSocketConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }


    // 회원가입 데이터
    public void sendSignupData(String username, String name, String phone, String password) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "REGISTER," + username + "," + name + "," + phone + "," + password;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "회원가입 :Sent signup data to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send signup data.");
        }
    }

    // 로그인 데이터
    public void sendLoginData(String username, String password) {

        if (isLoginInProgress) {
            Log.d(TAG, "Login request is already in progress.");
            return; // 중복 요청 방지
        }

        if (isSocketConnected()) {

            new Thread(() -> {
                writer.println("Login_Data");
                writer.flush();
                String message = username + "," + password;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "로그인: Sent login data to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Attempting to reconnect and resend login data.");
            connectToServer(data -> {
                // 재연결 후 로그인 데이터 전송
                sendLoginData(username, password);
            });
        }
    }

    // 중복 확인
    public void sendUsernameValidationRequest(String username) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "VALIDATE_USER," + username;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "Sent username validation request to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send username validation request.");
        }
    }


    // 날씨를 받아오는 코드 (main)
    void requestDataFromServer() {
        if (writer != null) {
            String message = "REQUEST_DATA";
            writer.println(message);
            writer.flush();
            Log.d(TAG, "Sent request to server: " + message);
        } else {
            Log.e(TAG, "Writer is null. Cannot send request.");
        }
    }


    // 날씨 데이터 + 센서 데이터 받아오는 코드 (home)
    void sendRequestSensorData() {
        if (writer != null) {
            String message = "MAIN_DATA";
            writer.println(message);
            writer.flush();
            Log.d(TAG, "Sent request to server: " + message);
        } else {
            Log.e(TAG, "Writer is null. Cannot send request.");
        }
    }

    // GPT Api 받아오는 코드
    void RequestTipData() {
        if (writer != null) {
            String message = "Tip_DATA";
            writer.println(message);
            writer.flush();
            Log.d(TAG, "Sent request to server: " + message);
        } else {
            Log.e(TAG, "Writer is null. Cannot send request.");
        }
    }



    private void receiveDataFromServer() {
        new Thread(() -> {
            try {
                String line;
                while (isSocketConnected() && (line = reader.readLine()) != null) {
                    //line = line.trim().replaceAll("[\\s\\u0000-\\u001F\\u007F]", ""); // 특수 문자 제거
                    line = line.replaceAll("[\\u0000-\\u001F\\u007F]", "");
                    Log.d(TAG, "Processed data received from server: '" + line + "'");

                    // 센서 데이터인지 확인하여 별도 처리
                    if (line.startsWith("SENSOR_DATA")) {
                        String sensorData = line.substring("SENSOR_DATA".length());
                        Log.d(TAG, "Received sensor data: " + sensorData);
                        if (dataListener != null) {
                            dataListener.onDataReceived(sensorData); // 센서 데이터 전달
                        }
                    } else {
                        // 그 외 데이터 처리
                        if (dataListener != null) {
                            dataListener.onDataReceived(line);
                        }
                    }
                }
            } catch (SocketTimeoutException e) {
                Log.e(TAG, "Socket read timed out: " + e.getMessage());
                attemptReconnect();
            } catch (IOException e) {
                Log.e(TAG, "Error reading from server: " + e.getMessage());
                if (isSocketConnected()) {
                    disconnect();
                    attemptReconnect();
                }
            }
        }).start();
    }

    private void attemptReconnect() {
        disconnect();
        try {
            Thread.sleep(5000); // 5초 대기 후 재연결 시도
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        connectToServer(dataListener);
    }

    public void disconnect() {
        try {
            if (writer != null) writer.close();
            if (reader != null) reader.close();
            if (socket != null) socket.close();
            Log.d(TAG, "Disconnected from server");
        } catch (IOException e) {
            Log.e(TAG, "Failed to disconnect: " + e.getMessage());
        }
    }

    public interface OnDataReceivedListener {
        void onDataReceived(String data);
    }
}

 */

// -------------------------------------------------------------------------------------------------

// 회원가입 후 로그인 안되는거 고치기 위해서 노오력

/*

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.io.IOException;

public class SocketActivity {

    private static final String TAG = "SocketActivity";
    private static final String SERVER_IP = "192.168.0.180";
    // 아산 자취방 ip            192.168.1.105
    // 학교 연구실 IP            192.168.0.180

    private static final int SERVER_PORT = 12345;

    private static SocketActivity instance;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private OnDataReceivedListener dataListener;
    private boolean isConnecting = false;
    private boolean isLoginInProgress = false; // 로그인 진행 상태 플래그
    private boolean isConnected = false; // 재연결을 위한 상태 플래그

    private SocketActivity() { }

    public static synchronized SocketActivity getInstance() {
        if (instance == null) {
            instance = new SocketActivity();
        }
        return instance;
    }

    public void setDataListener(OnDataReceivedListener listener) {
        this.dataListener = listener; // 전달받은 리스너를 설정
    }

    public void connectToServer(OnDataReceivedListener listener) {

        if (isConnecting) return;

        isConnecting = true;
        dataListener = listener;

        new Thread(() -> {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                socket.setSoTimeout(60000); // 타임아웃을 60초로 설정
                writer = new PrintWriter(socket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                Log.d(TAG, "Connected to server");
//'']
                isConnected = true;

                requestDataFromServer();
                receiveDataFromServer(); // 데이터 수신을 한 번만 호출
                sendRequestSensorData();
                RequestTipData();

            } catch (IOException e) {
                Log.e(TAG, "Failed to connect to server: " + e.getMessage());
                if (dataListener != null) {
                    dataListener.onDataReceived("Connection failed");
                }
            } finally {
                isConnecting = false;
            }
        }).start();
    }

    public void sendData(String data) {
        if (isSocketConnected()) {
            new Thread(() -> {
                writer.println(data);
                writer.flush();
                Log.d(TAG, "Data sent to server: " + data);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send data.");
        }
    }

    boolean isSocketConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }


    // 회원가입 데이터
    public void sendSignupData(String username, String name, String phone, String password) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "REGISTER," + username + "," + name + "," + phone + "," + password;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "회원가입 :Sent signup data to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send signup data.");
        }
    }

    // 로그인 데이터
    public void sendLoginData(String username, String password) {

        if (isLoginInProgress) {
            Log.d(TAG, "Login request is already in progress.");
            return; // 중복 요청 방지
        }

        isLoginInProgress = true;

        if (isSocketConnected()) {

            new Thread(() -> {
                writer.println("Login_Data");
                writer.flush();
                String message = username + "," + password;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "로그인: Sent login data to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Attempting to reconnect and resend login data.");
            connectToServer(data -> {
                // 재연결 후 로그인 데이터 전송
                sendLoginData(username, password);
            });
        }
    }


    // 중복 확인
    public void sendUsernameValidationRequest(String username) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "VALIDATE_USER," + username;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "Sent username validation request to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send username validation request.");
        }
    }


    // 날씨를 받아오는 코드 (main)
    void requestDataFromServer() {
        if (writer != null) {
            String message = "REQUEST_DATA";
            writer.println(message);
            writer.flush();
            Log.d(TAG, "Sent request to server: " + message);
        } else {
            Log.e(TAG, "Writer is null. Cannot send request.");
        }
    }


    // 날씨 데이터 + 센서 데이터 받아오는 코드 (home)
    void sendRequestSensorData() {
        if (writer != null) {
            String message = "MAIN_DATA";
            writer.println(message);
            writer.flush();
            Log.d(TAG, "Sent request to server: " + message);
        } else {
            Log.e(TAG, "Writer is null. Cannot send request.");
        }
    }

    // GPT Api 받아오는 코드
    void RequestTipData() {
        if (writer != null) {
            String message = "Tip_DATA";
            writer.println(message);
            writer.flush();
            Log.d(TAG, "Sent request to server: " + message);
        } else {
            Log.e(TAG, "Writer is null. Cannot send request.");
        }
    }



    private void receiveDataFromServer() {
        new Thread(() -> {
            try {
                String line;
                while (isSocketConnected() && (line = reader.readLine()) != null) {
                    //line = line.trim().replaceAll("[\\s\\u0000-\\u001F\\u007F]", ""); // 특수 문자 제거
                    line = line.replaceAll("[\\u0000-\\u001F\\u007F]", "");
                    Log.d(TAG, "Processed data received from server: '" + line + "'");

                    // 센서 데이터인지 확인하여 별도 처리
                    if (line.startsWith("SENSOR_DATA")) {
                        String sensorData = line.substring("SENSOR_DATA".length());
                        Log.d(TAG, "Received sensor data: " + sensorData);
                        if (dataListener != null) {
                            dataListener.onDataReceived(sensorData); // 센서 데이터 전달
                            dataListener.onDataReceived(line); // 응답을 클라이언트로 전달
                        }
                    } else {
                        // 그 외 데이터 처리
                        if (dataListener != null) {
                            dataListener.onDataReceived(line);
                        }
                    }
                }
            } catch (SocketTimeoutException e) {
                Log.e(TAG, "Socket read timed out: " + e.getMessage());
                attemptReconnect();
            } catch (IOException e) {
                Log.e(TAG, "Error reading from server: " + e.getMessage());
                disconnect();
            }
        }).start();
    }

    private void attemptReconnect() {
        disconnect();
        try {
            Thread.sleep(5000); // 5초 대기 후 재연결 시도
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        connectToServer(dataListener);
    }

    public void disconnect() {
        try {
            if (writer != null) writer.close();
            if (reader != null) reader.close();
            if (socket != null) socket.close();
            Log.d(TAG, "Disconnected from server");
        } catch (IOException e) {
            Log.e(TAG, "Failed to disconnect: " + e.getMessage());
        }
    }

    public interface OnDataReceivedListener {
        void onDataReceived(String data);
    }
}

 */

// -------------------------------------------------------------------------------------------------

// 앱 처음 키고, 회원가입 켰을 때 메세지 박스 안 뜸
// 회원가입 갔다가 로그인 은 안 고쳐짐

/*

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.io.IOException;

public class SocketActivity {

    private static final String TAG = "SocketActivity";
    private static final String SERVER_IP = "192.168.1.105";
    // 아산 자취방 ip            192.168.1.105
    // 학교 연구실 IP            192.168.0.180

    private static final int SERVER_PORT = 12345;

    private static SocketActivity instance;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private OnDataReceivedListener dataListener;
    private boolean isConnecting = false;
    private boolean isLoginInProgress = false; // 로그인 진행 상태 플래그
    private boolean isConnected = false; // 재연결을 위한 상태 플래그

    private SocketActivity() { }

    public static synchronized SocketActivity getInstance() {
        if (instance == null) {
            instance = new SocketActivity();
        }
        return instance;
    }

    public void setDataListener(OnDataReceivedListener listener) {
        this.dataListener = listener; // 전달받은 리스너를 설정
    }

    public void connectToServer(OnDataReceivedListener listener) {

        if (isConnecting) return;

        isConnecting = true;
        dataListener = listener;

        new Thread(() -> {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                socket.setSoTimeout(60000); // 타임아웃을 60초로 설정
                writer = new PrintWriter(socket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                Log.d(TAG, "Connected to server");
//'']
                isConnected = true;

                requestDataFromServer();
                receiveDataFromServer(); // 데이터 수신을 한 번만 호출
                sendRequestSensorData();
                RequestTipData();

            } catch (IOException e) {
                Log.e(TAG, "Failed to connect to server: " + e.getMessage());
                if (dataListener != null) {
                    dataListener.onDataReceived("Connection failed");
                }
            } finally {
                isConnecting = false;
            }
        }).start();
    }

    public void sendData(String data) {
        if (isSocketConnected()) {
            new Thread(() -> {
                writer.println(data);
                writer.flush();
                Log.d(TAG, "Data sent to server: " + data);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send data.");
        }
    }

    boolean isSocketConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }


    // 회원가입 데이터
    public void sendSignupData(String username, String name, String phone, String password) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "REGISTER," + username + "," + name + "," + phone + "," + password;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "회원가입 :Sent signup data to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send signup data.");
        }
    }

    // 로그인 데이터
    public void sendLoginData(String username, String password) {
        if (isLoginInProgress) {
            Log.d(TAG, "Login request is already in progress.");
            return; // 중복 요청 방지
        }

        isLoginInProgress = true; // 로그인 진행 중 상태 설정

        if (isSocketConnected()) {
            new Thread(() -> {
                writer.println("Login_Data");
                writer.flush();
                String message = username + "," + password;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "로그인: Sent login data to server: " + message);
                isLoginInProgress = false; // 요청 완료 후 초기화
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Attempting to reconnect and resend login data.");
            connectToServer(data -> {
                sendLoginData(username, password); // 재연결 후 로그인 데이터 전송
                isLoginInProgress = false; // 요청 완료 후 초기화
            });
        }
    }


    // 중복 확인
    public void sendUsernameValidationRequest(String username) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "VALIDATE_USER," + username;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "Sent username validation request to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send username validation request.");
        }
    }


    // 날씨를 받아오는 코드 (main)
    void requestDataFromServer() {
        if (writer != null) {
            String message = "REQUEST_DATA";
            writer.println(message);
            writer.flush();
            Log.d(TAG, "Sent request to server: " + message);
        } else {
            Log.e(TAG, "Writer is null. Cannot send request.");
        }
    }


    // 날씨 데이터 + 센서 데이터 받아오는 코드 (home)
    void sendRequestSensorData() {
        if (writer != null) {
            String message = "MAIN_DATA";
            writer.println(message);
            writer.flush();
            Log.d(TAG, "Sent request to server: " + message);
        } else {
            Log.e(TAG, "Writer is null. Cannot send request.");
        }
    }

    // GPT Api 받아오는 코드
    void RequestTipData() {
        if (writer != null) {
            String message = "Tip_DATA";
            writer.println(message);
            writer.flush();
            Log.d(TAG, "Sent request to server: " + message);
        } else {
            Log.e(TAG, "Writer is null. Cannot send request.");
        }
    }



    private void receiveDataFromServer() {
        new Thread(() -> {
            try {
                String line;
                while (isSocketConnected() && (line = reader.readLine()) != null) {
                    //line = line.trim().replaceAll("[\\s\\u0000-\\u001F\\u007F]", ""); // 특수 문자 제거
                    line = line.replaceAll("[\\u0000-\\u001F\\u007F]", "");
                    Log.d(TAG, "Processed data received from server: '" + line + "'");

                    // 센서 데이터인지 확인하여 별도 처리
                    if (line.startsWith("SENSOR_DATA")) {
                        String sensorData = line.substring("SENSOR_DATA".length());
                        Log.d(TAG, "Received sensor data: " + sensorData);
                        if (dataListener != null) {
                            dataListener.onDataReceived(sensorData); // 센서 데이터 전달
                            dataListener.onDataReceived(line); // 응답을 클라이언트로 전달
                        }
                    } else {
                        // 그 외 데이터 처리
                        if (dataListener != null) {
                            dataListener.onDataReceived(line);
                        }
                    }
                }
            } catch (SocketTimeoutException e) {
                Log.e(TAG, "Socket read timed out: " + e.getMessage());
                attemptReconnect();
            } catch (IOException e) {
                Log.e(TAG, "Error reading from server: " + e.getMessage());
                disconnect();
            }
        }).start();
    }

    private void attemptReconnect() {
        disconnect();
        try {
            Thread.sleep(5000); // 5초 대기 후 재연결 시도
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        connectToServer(dataListener);
    }

    public void disconnect() {
        try {
            if (writer != null) writer.close();
            if (reader != null) reader.close();
            if (socket != null) socket.close();
            Log.d(TAG, "Disconnected from server");
        } catch (IOException e) {
            Log.e(TAG, "Failed to disconnect: " + e.getMessage());
        } finally {
            isLoginInProgress = false; // 연결이 끊어졌을 때 초기화
            isConnecting = false;
        }
    }

    public interface OnDataReceivedListener {
        void onDataReceived(String data);
    }
}

 */

// -----------------------------


import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.io.IOException;

public class SocketActivity {

    private static final String TAG = "SocketActivity";
    private static final String SERVER_IP = "192.168.0.180";
    // 아산 자취방 ip            192.168.1.105
    // 학교 연구실 IP            192.168.0.180

    private static final int SERVER_PORT = 12345;

    private static SocketActivity instance;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private OnDataReceivedListener dataListener;
    private boolean isConnecting = false;
    private boolean isLoginInProgress = false; // 로그인 진행 상태 플래그
    private boolean isConnected = false; // 재연결을 위한 상태 플래그

    private SocketActivity() { }

    public static synchronized SocketActivity getInstance() {
        if (instance == null) {
            instance = new SocketActivity();
        }
        return instance;
    }

    public void setDataListener(OnDataReceivedListener listener) {
        this.dataListener = listener; // 전달받은 리스너를 설정
    }

    public void connectToServer(OnDataReceivedListener listener) {

        if (isConnecting) return;

        isConnecting = true;
        dataListener = listener;

        new Thread(() -> {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                socket.setSoTimeout(60000); // 타임아웃을 60초로 설정
                writer = new PrintWriter(socket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                Log.d(TAG, "Connected to server");
//'']
                isConnected = true;

                requestDataFromServer();
                receiveDataFromServer(); // 데이터 수신을 한 번만 호출
                sendRequestSensorData();
                RequestTipData();

            } catch (IOException e) {
                Log.e(TAG, "Failed to connect to server: " + e.getMessage());
                if (dataListener != null) {
                    dataListener.onDataReceived("Connection failed");
                }
            } finally {
                isConnecting = false;
            }
        }).start();
    }

    public void sendData(String data) {
        if (isSocketConnected()) {
            new Thread(() -> {
                writer.println(data);
                writer.flush();
                Log.d(TAG, "Data sent to server: " + data);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send data.");
        }
    }

    boolean isSocketConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }


    // 회원가입 데이터
    public void sendSignupData(String username, String name, String phone, String password) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "REGISTER," + username + "," + name + "," + phone + "," + password;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "회원가입 :Sent signup data to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send signup data.");
        }
    }

    // 로그인 데이터
    public void sendLoginData(String username, String password) {
        if (isLoginInProgress) {
            Log.d(TAG, "Login request is already in progress.");
            return; // 중복 요청 방지
        }

        isLoginInProgress = true; // 로그인 진행 중 상태 설정

        if (isSocketConnected()) {
            new Thread(() -> {
                writer.println("Login_Data");
                writer.flush();
                String message = username + "," + password;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "로그인: Sent login data to server: " + message);
                isLoginInProgress = false; // 요청 완료 후 초기화
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Attempting to reconnect and resend login data.");
            connectToServer(data -> {
                sendLoginData(username, password); // 재연결 후 로그인 데이터 전송
                isLoginInProgress = false; // 요청 완료 후 초기화
            });
        }
    }


    // 중복 확인
    public void sendUsernameValidationRequest(String username) {
        if (isSocketConnected()) {
            new Thread(() -> {
                String message = "VALIDATE_USER," + username;
                writer.println(message);
                writer.flush();
                Log.d(TAG, "Sent username validation request to server: " + message);
            }).start();
        } else {
            Log.e(TAG, "Socket is not connected. Cannot send username validation request.");
        }
    }


    // 날씨를 받아오는 코드 (main)
    void requestDataFromServer() {
        if (writer != null) {
            String message = "REQUEST_DATA";
            writer.println(message);
            writer.flush();
            Log.d(TAG, "Sent request to server: " + message);
        } else {
            Log.e(TAG, "Writer is null. Cannot send request.");
        }
    }


    // 날씨 데이터 + 센서 데이터 받아오는 코드 (home)
    void sendRequestSensorData() {
        if (writer != null) {
            String message = "MAIN_DATA";
            writer.println(message);
            writer.flush();
            Log.d(TAG, "Sent request to server: " + message);
        } else {
            Log.e(TAG, "Writer is null. Cannot send request.");
        }
    }

    // GPT Api 받아오는 코드
    void RequestTipData() {
        if (writer != null) {
            String message = "Tip_DATA";
            writer.println(message);
            writer.flush();
            Log.d(TAG, "Sent request to server: " + message);
        } else {
            Log.e(TAG, "Writer is null. Cannot send request.");
        }
    }



    private void receiveDataFromServer() {
        new Thread(() -> {
            try {
                String line;
                while (isSocketConnected() && (line = reader.readLine()) != null) {
                    //line = line.trim().replaceAll("[\\s\\u0000-\\u001F\\u007F]", ""); // 특수 문자 제거
                    line = line.replaceAll("[\\u0000-\\u001F\\u007F]", "");
                    Log.d(TAG, "Processed data received from server: '" + line + "'");

                    // 센서 데이터인지 확인하여 별도 처리
                    if (line.startsWith("SENSOR_DATA")) {
                        String sensorData = line.substring("SENSOR_DATA".length());
                        Log.d(TAG, "Received sensor data: " + sensorData);
                        if (dataListener != null) {
                            dataListener.onDataReceived(sensorData); // 센서 데이터 전달
                            dataListener.onDataReceived(line); // 응답을 클라이언트로 전달
                        }
                    } else {
                        // 그 외 데이터 처리
                        if (dataListener != null) {
                            dataListener.onDataReceived(line);
                        }
                    }
                }
            } catch (SocketTimeoutException e) {
                Log.e(TAG, "Socket read timed out: " + e.getMessage());
                attemptReconnect();
            } catch (IOException e) {
                Log.e(TAG, "Error reading from server: " + e.getMessage());
                disconnect();
            }
        }).start();
    }

    private void attemptReconnect() {
        disconnect();
        try {
            Thread.sleep(5000); // 5초 대기 후 재연결 시도
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        connectToServer(dataListener);
    }

    public void disconnect() {
        try {
            if (writer != null) writer.close();
            if (reader != null) reader.close();
            if (socket != null) socket.close();
            Log.d(TAG, "Disconnected from server");
        } catch (IOException e) {
            Log.e(TAG, "Failed to disconnect: " + e.getMessage());
        } finally {
            isLoginInProgress = false; // 연결이 끊어졌을 때 초기화
            isConnecting = false;
        }
    }

    public interface OnDataReceivedListener {
        void onDataReceived(String data);
    }
}

