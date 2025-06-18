package com.example.smarthome_v3;

/*

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }
}

 */

// -------------------------------------------------------------------------------------------------

/*

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class HomeActivity extends AppCompatActivity {

    private TextView skyTextView, tempTextView, ptyTextView, popTextView;
    private Handler uiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // skyTextView = findViewById(R.id.skyTextView);
        tempTextView = findViewById(R.id.out_temp);
        ptyTextView = findViewById(R.id.microdust);
        popTextView = findViewById(R.id.out_humid);

        uiHandler = new Handler(Looper.getMainLooper());

        new SocketTask().execute();
    }

    private class SocketTask extends AsyncTask<Void, String, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String serverAddress = "192.168.0.180";
            int serverPort = 12345;

            try (Socket socket = new Socket(serverAddress, serverPort);
                 PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                String message = "REQUEST_DATA";
                out.println(message);
                Log.d("SocketTest", "Sent: " + message);

                String line;
                while ((line = in.readLine()) != null) {
                    Log.d("SocketTest", "Received line from server: " + line);
                    publishProgress(line);
                }

            } catch (UnknownHostException e) {
                Log.e("SocketTest", "Unknown host", e);
            } catch (SocketTimeoutException e) {
                Log.e("SocketTest", "Socket timeout", e);
            } catch (Exception e) {
                Log.e("SocketTest", "Error occurred", e);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            // values[0] contains the received line of data
            if (values.length > 0) {
                handleDataReceived(values[0]);
            }
        }
    }

    private void handleDataReceived(String data) {
        if (data.startsWith("TEMP: ")) {
            String temp = data.substring("TEMP: ".length());
            uiHandler.post(() -> tempTextView.setText(temp + " ℃"));
            //} else if (data.startsWith("SKY: ")) {
            //String sky = data.substring("SKY: ".length());
            //uiHandler.post(() -> skyTextView.setText(sky));
        } else if (data.startsWith("PTY: ")) {
            String pty = data.substring("PTY: ".length());
            uiHandler.post(() -> ptyTextView.setText(pty));
        } else if (data.startsWith("POP: ")) {
            String pop = data.substring("POP: ".length());
            uiHandler.post(() -> popTextView.setText(pop + " %"));
        }
    }
}

 */

// -------------------------------------------------------------------------------------------------

/*

// 되는 코드!!!! SocketActivity로 연결하기 전에 되는 코드!!!!

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private static final String SERVER_IP = "192.168.0.180"; // 서버 IP 주소
    private static final int SERVER_PORT = 12345; // 서버 포트 번호

    private TextView tempTextView, rehTextView, microTextView;
    private Handler uiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // UI 요소 초기화
        tempTextView = findViewById(R.id.out_temp); // 실외 온도
        rehTextView = findViewById(R.id.out_humid); // 실외 습도
        microTextView = findViewById(R.id.microdust); // 실외 미세먼지

        uiHandler = new Handler(Looper.getMainLooper());

        // 서버에서 센서 값을 받아와 UI에 표시
        receiveSensorValueFromServer();
    }

    private void receiveSensorValueFromServer() {
        new SocketTask().execute();
    }

    private class SocketTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
                 PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"))) {

                // 서버에 데이터 요청
                out.println("REQUEST_DATA");
                out.flush();
                Log.d(TAG, "Sent: REQUEST_DATA");

                // 서버로부터 응답 수신
                String line;
                while ((line = in.readLine()) != null) {
                    Log.d(TAG, "Received: " + line);
                    // UI 업데이트를 위한 데이터 처리
                    parseAndSetData(line);
                }

            } catch (Exception e) {
                Log.e(TAG, "Error occurred", e);
            }
            return null;
        }

        protected void parseAndSetData(String data) {
            Log.d(TAG, "Parsing Data: " + data); // 전체 데이터 확인

            if (data.startsWith("TEMP: ")) {
                String temp = data.substring("TEMP: ".length());
                Log.d(TAG, "Parsed Temperature: " + temp); // 온도 로그 추가
                uiHandler.post(() -> tempTextView.setText(temp + " °C"));
            } else if (data.startsWith("REH: ")) {
                String reh = data.substring("REH: ".length());
                uiHandler.post(() -> rehTextView.setText(reh + " %"));  // 습도 상태 업데이트
            } else if (data.startsWith("MICRO: ")) {
                String micro = data.substring("MICRO: ".length());
                Log.d(TAG, "Parsed Micro Dust: " + micro); // 미세먼지 로그 추가
                uiHandler.post(() -> updateMicroStatus(micro));
            }
        }
    }

    private void updateMicroStatus(String microValue) {
        try {
            int value = Integer.parseInt(microValue); // 문자열을 숫자로 변환 시도
            String status;
            if (value <= 30) {
                status = "좋음";
            } else if (value <= 80) {
                status = "보통";
            } else if (value <= 150) {
                status = "나쁨";
            } else {
                status = "매우 나쁨";
            }
            microTextView.setText(status);  // 미세먼지 상태를 TextView에 설정
        } catch (NumberFormatException e) {
            Log.e(TAG, "Invalid micro dust value: " + microValue); // 오류 로그 추가
            microTextView.setText("데이터 없음"); // 기본 텍스트 설정
        }
    }
}

*/

// -------------------------------------------------------------------------------------------------

// SocketActivity에 LogWeather 서버만 정보만 추가했을 때 동작되는 코드!!!!

/*

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity implements SocketActivity.OnDataReceivedListener {

    private static final String TAG = "HomeActivity";
    private TextView tempTextView, rehTextView, microTextView;
    private Handler uiHandler;
    private SocketActivity socketActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // UI 요소 초기화
        tempTextView = findViewById(R.id.out_temp); // 실외 온도
        rehTextView = findViewById(R.id.out_humid); // 실외 습도
        microTextView = findViewById(R.id.microdust); // 실외 미세먼지

        uiHandler = new Handler(Looper.getMainLooper());

        // SocketActivity 인스턴스를 가져와서 서버 연결 시작
        socketActivity = SocketActivity.getInstance();
        socketActivity.connectToServer(this); // 서버 연결 및 데이터 수신
    }

    // 서버에서 데이터 수신 시 호출되는 메서드
    @Override
    public void onDataReceived(String data) {
        Log.d(TAG, "Received: " + data); // 수신 데이터 로그 출력
        parseAndSetData(data);
    }

    // 데이터를 파싱하고 UI에 설정하는 메서드
    private void parseAndSetData(String data) {
        Log.d(TAG, "Parsing Data: " + data); // 전체 데이터 확인

        if (data.startsWith("TEMP:")) {  // 온도 데이터
            String temp = data.substring("TEMP:".length()).trim();
            tempTextView.setText(temp + " °C"); // 온도 설정
        } else if (data.startsWith("REH:")) {  // 습도 데이터
            String reh = data.substring("REH:".length()).trim();
            rehTextView.setText(reh + " %"); // 습도 설정
        } else if (data.startsWith("MICRO:")) {  // 미세먼지 데이터
            String micro = data.substring("MICRO:".length()).trim();
            try {
                int microValue = Integer.parseInt(micro);
                updateMicroStatus(microValue); // 미세먼지 상태 설정
            } catch (NumberFormatException e) {
                Log.e(TAG, "Invalid micro data: " + micro);
            }
        }
    }

    // 미세먼지 상태를 업데이트하는 메서드
    private void updateMicroStatus(int microValue) {
        String status;
        if (microValue <= 30) {
            status = "좋음"; // 미세먼지 상태가 좋음
        } else if (microValue <= 80) {
            status = "보통"; // 미세먼지 상태가 보통
        } else if (microValue <= 150) {
            status = "나쁨"; // 미세먼지 상태가 나쁨
        } else {
            status = "매우 나쁨"; // 미세먼지 상태가 매우 나쁨
        }
        microTextView.setText(status); // 텍스트 뷰에 설정
    }

    // 미세먼지 상태를 업데이트하는 메서드
    private void updateMicroStatus(String microValue) {
        try {
            int value = Integer.parseInt(microValue); // 문자열을 숫자로 변환 시도
            String status;
            if (value <= 30) {
                status = "좋음";
            } else if (value <= 80) {
                status = "보통";
            } else if (value <= 150) {
                status = "나쁨";
            } else {
                status = "매우 나쁨";
            }
            microTextView.setText(status);  // 미세먼지 상태를 TextView에 설정
        } catch (NumberFormatException e) {
            Log.e(TAG, "Invalid micro dust value: " + microValue); // 오류 로그 추가
            microTextView.setText("데이터 없음"); // 기본 텍스트 설정
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 액티비티 종료 시 서버 연결 해제
        if (socketActivity != null) {
            socketActivity.disconnect();
        }
    }
}

*/

//--------------------------------------------------------------------------------------------------

// 아두이노 연결 완!!료!!

/*

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity implements SocketActivity.OnDataReceivedListener {

    private static final String TAG = "HomeActivity";
    private TextView tempTextView, rehTextView, microTextView, livingtempView, livinghumView;
    private Handler uiHandler;
    private SocketActivity socketActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // UI 요소 초기화
        tempTextView = findViewById(R.id.out_temp); // 실외 온도
        rehTextView = findViewById(R.id.out_humid); // 실외 습도
        microTextView = findViewById(R.id.microdust); // 실외 미세먼지
        livingtempView = findViewById(R.id.living_temp);
        livinghumView = findViewById(R.id.living_hum);

        uiHandler = new Handler(Looper.getMainLooper());

        // SocketActivity 인스턴스를 가져와서 서버 연결 시작
        socketActivity = SocketActivity.getInstance();
        socketActivity.connectToServer(this); // 서버 연결 및 데이터 수신
    }

    // 서버에서 데이터 수신 시 호출되는 메서드
    @Override
    public void onDataReceived(String data) {
        Log.d(TAG, "Received: " + data); // 수신 데이터 로그 출력
        parseAndSetData(data);
    }

    // 데이터를 파싱하고 UI에 설정하는 메서드
    private void parseAndSetData(String data) {
        Log.d(TAG, "Parsing Data: " + data); // 전체 데이터 확인

        if (data.startsWith("OUTTEMP:") && data.length() > "OUTTEMP:".length()) { // 실외 온도 데이터
            String OUTTEMP = data.substring("OUTTEMP:".length()).trim();
            tempTextView.setText(OUTTEMP + " °C"); // 온도 설정
        } else if (data.startsWith("REH:") && data.length() > "REH:".length()) { // 실외 습도 데이터
            String reh = data.substring("REH:".length()).trim();
            rehTextView.setText(reh + " %"); // 습도 설정
        } else if (data.startsWith("MICRO:") && data.length() > "MICRO:".length()) { // 미세먼지 데이터
            String micro = data.substring("MICRO:".length()).trim();
            try {
                int microValue = Integer.parseInt(micro);
                updateMicroStatus(microValue); // 미세먼지 상태 설정
            } catch (NumberFormatException e) {
                Log.e(TAG, "Invalid micro data: " + micro);
                microTextView.setText("데이터 없음");
            }
        } else if (data.startsWith("INTEMP:") && data.length() > "INTEMP:".length()) { // 실내 온도 데이터
            String INTEMP = data.substring("INTEMP:".length()).trim();
            livingtempView.setText(INTEMP + " °C"); // 실내 온도 설정
        } else if (data.startsWith("HUM:") && data.length() > "HUM:".length()) { // 실내 습도 데이터
            String HUM = data.substring("HUM:".length()).trim();
            livinghumView.setText(HUM + " %"); // 실내 습도 설정
        }
    }

    // 미세먼지 상태를 업데이트하는 메서드
    private void updateMicroStatus(int microValue) {
        String status;
        if (microValue <= 30) {
            status = "좋음"; // 미세먼지 상태가 좋음
        } else if (microValue <= 80) {
            status = "보통"; // 미세먼지 상태가 보통
        } else if (microValue <= 150) {
            status = "나쁨"; // 미세먼지 상태가 나쁨
        } else {
            status = "매우 나쁨"; // 미세먼지 상태가 매우 나쁨
        }
        microTextView.setText(status); // 텍스트 뷰에 설정
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 액티비티 종료 시 서버 연결 해제
        if (socketActivity != null) {
            socketActivity.disconnect();
        }
    }
}

*/

// -------------------------------------------------------------------------------------------------

// 팬 돌아가는 신호 보내는 코드 만들려고 노력~! (HFAN만 안되지만 다른건 다 돌아감)

/*

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class HomeActivity extends AppCompatActivity implements SocketActivity.OnDataReceivedListener {

    private static final String TAG = "HomeActivity";
    private TextView tempTextView, rehTextView, microTextView, livingtempView, livinghumView;
    private Handler uiHandler;
    private SwitchCompat ledSwitch,acSwitch, airSwitch, heaterSwitch; // LED 스위치 추가
    private SocketActivity socketActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // UI 요소 초기화
        tempTextView = findViewById(R.id.out_temp); // 실외 온도
        rehTextView = findViewById(R.id.out_humid); // 실외 습도
        microTextView = findViewById(R.id.microdust); // 실외 미세먼지
        livingtempView = findViewById(R.id.living_temp);
        livinghumView = findViewById(R.id.living_hum);
        ledSwitch = findViewById(R.id.LED_switch); // LED 스위치 초기화
        acSwitch = findViewById(R.id.AC_switch); // AC 스위치 초기화
        airSwitch = findViewById(R.id.AIR_switch); // LED 스위치 초기화
        heaterSwitch = findViewById(R.id.HEATER_switch); // AC 스위치 초기화

        uiHandler = new Handler(Looper.getMainLooper());

        // SocketActivity 인스턴스를 가져와서 서버 연결 시작
        socketActivity = SocketActivity.getInstance();
        socketActivity.connectToServer(this); // 서버 연결 및 데이터 수신

        // LED 스위치 리스너 설정
        ledSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                sendLedSignal("LED_ON");
            } else {
                sendLedSignal("LED_OFF");
            }
        });

        // 에어컨 스위치 리스너 설정
        acSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                sendLedSignal("AC_ON");
            } else {
                sendLedSignal("AC_OFF");
            }
        });

        // 공기청정기 스위치 리스너 설정
        airSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                sendLedSignal("PFAN_ON");
            } else {
                sendLedSignal("PFAN_OFF");
            }
        });

        // 히터 스위치 리스너 설정
        heaterSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                sendLedSignal("HFAN_ON");
            } else {
                sendLedSignal("HFAN_OFF");
            }
        });
    }

    // 서버에 LED 신호를 전송하는 메서드
    private void sendLedSignal(String signal) {
        if (socketActivity != null) {
            socketActivity.sendData(signal);
            Log.d(TAG, "Sent LED signal: " + signal); // 신호 전송 시 로그 작성
        } else {
            Log.e(TAG, "SocketActivity is null, cannot send signal.");
        }
    }



    // 서버에서 데이터 수신 시 호출되는 메서드
    @Override
    public void onDataReceived(String data) {
        Log.d(TAG, "Received: " + data); // 수신 데이터 로그 출력
        parseAndSetData(data);
    }

    // 데이터를 파싱하고 UI에 설정하는 메서드
    private void parseAndSetData(String data) {
        Log.d(TAG, "Parsing Data: " + data); // 전체 데이터 확인

        if (data.startsWith("OUTTEMP:") && data.length() > "OUTTEMP:".length()) { // 실외 온도 데이터
            String OUTTEMP = data.substring("OUTTEMP:".length()).trim();
            tempTextView.setText(OUTTEMP + " °C"); // 온도 설정
        } else if (data.startsWith("REH:") && data.length() > "REH:".length()) { // 실외 습도 데이터
            String reh = data.substring("REH:".length()).trim();
            rehTextView.setText(reh + " %"); // 습도 설정
        } else if (data.startsWith("MICRO:") && data.length() > "MICRO:".length()) { // 미세먼지 데이터
            String micro = data.substring("MICRO:".length()).trim();
            try {
                int microValue = Integer.parseInt(micro);
                updateMicroStatus(microValue); // 미세먼지 상태 설정
            } catch (NumberFormatException e) {
                Log.e(TAG, "Invalid micro data: " + micro);
                microTextView.setText("데이터 없음");
            }
        } else if (data.startsWith("INTEMP:") && data.length() > "INTEMP:".length()) { // 실내 온도 데이터
            String INTEMP = data.substring("INTEMP:".length()).trim();
            livingtempView.setText(INTEMP + " °C"); // 실내 온도 설정
        } else if (data.startsWith("HUM:") && data.length() > "HUM:".length()) { // 실내 습도 데이터
            String HUM = data.substring("HUM:".length()).trim();
            livinghumView.setText(HUM + " %"); // 실내 습도 설정
        }
    }

    // 미세먼지 상태를 업데이트하는 메서드
    private void updateMicroStatus(int microValue) {
        String status;
        if (microValue <= 30) {
            status = "좋음"; // 미세먼지 상태가 좋음
        } else if (microValue <= 80) {
            status = "보통"; // 미세먼지 상태가 보통
        } else if (microValue <= 150) {
            status = "나쁨"; // 미세먼지 상태가 나쁨
        } else {
            status = "매우 나쁨"; // 미세먼지 상태가 매우 나쁨
        }
        microTextView.setText(status); // 텍스트 뷰에 설정
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 액티비티 종료 시 서버 연결 해제
        if (socketActivity != null) {
            socketActivity.disconnect();
        }
    }
}

*/

// -------------------------------------------------------------------------------------------------

// 온도 추천 만들기 완료 !!

/*

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class HomeActivity extends AppCompatActivity implements SocketActivity.OnDataReceivedListener {

    private static final String TAG = "HomeActivity";
    private TextView tempTextView, rehTextView, microTextView, livingtempView;
    private Handler uiHandler;
    private SwitchCompat ledSwitch,acSwitch, airSwitch, heaterSwitch, tempSwitch; // LED 스위치 추가
    private SocketActivity socketActivity;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // UI 요소 초기화
        tempTextView = findViewById(R.id.out_temp); // 실외 온도
        rehTextView = findViewById(R.id.out_humid); // 실외 습도
        microTextView = findViewById(R.id.microdust); // 실외 미세먼지
        livingtempView = findViewById(R.id.living_temp);
        ledSwitch = findViewById(R.id.LED_switch); // LED 스위치 초기화
        acSwitch = findViewById(R.id.AC_switch); // AC 스위치 초기화
        airSwitch = findViewById(R.id.AIR_switch); // LED 스위치 초기화
        heaterSwitch = findViewById(R.id.HEATER_switch); // AC 스위치 초기화
        tempSwitch = findViewById(R.id.TEMP_switch); // 자동 온도 추천

        uiHandler = new Handler(Looper.getMainLooper());

        // SocketActivity 인스턴스를 가져와서 서버 연결 시작
        socketActivity = SocketActivity.getInstance();
        socketActivity.connectToServer(this); // 서버 연결 및 데이터 수신

        // LED 스위치 리스너 설정
        ledSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                sendLedSignal("LED_ON");
            } else {
                sendLedSignal("LED_OFF");
            }
        });

        // 에어컨 스위치 리스너 설정
        acSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                sendLedSignal("AC_ON");
            } else {
                sendLedSignal("AC_OFF");
            }
        });

        // 공기청정기 스위치 리스너 설정
        airSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                sendLedSignal("PFAN_ON");
            } else {
                sendLedSignal("PFAN_OFF");
            }
        });

        // 히터 스위치 리스너 설정
        heaterSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                sendLedSignal("HFAN_ON");
            } else {
                sendLedSignal("HFAN_OFF");
            }
        });

        // 온도 추천 스위치 리스너 설정
        tempSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                sendLedSignal("Recommend_ON");
            } else {
                sendLedSignal("Recommend_OFF");
            }
        });

    }

    // 서버에 LED 신호를 전송하는 메서드
    private void sendLedSignal(String signal) {
        if (socketActivity != null) {
            socketActivity.sendData(signal);
            Log.d(TAG, "Sent LED signal: " + signal); // 신호 전송 시 로그 작성
        } else {
            Log.e(TAG, "SocketActivity is null, cannot send signal.");
        }
    }


    // 서버에서 데이터 수신 시 호출되는 메서드
    @Override
    public void onDataReceived(String data) {
        Log.d(TAG, "Received: " + data); // 수신 데이터 로그 출력
        parseAndSetData(data);
    }



    // 데이터를 파싱하고 UI에 설정하는 메서드
    private void parseAndSetData(String data) {
        Log.d(TAG, "Parsing Data: " + data); // 전체 데이터 확인

        if (data.startsWith("OUTTEMP:") && data.length() > "OUTTEMP:".length()) { // 실외 온도 데이터
            String OUTTEMP = data.substring("OUTTEMP:".length()).trim();
            tempTextView.setText(OUTTEMP + " °C"); // 온도 설정
        } else if (data.startsWith("REH:") && data.length() > "REH:".length()) { // 실외 습도 데이터
            String reh = data.substring("REH:".length()).trim();
            rehTextView.setText(reh + " %"); // 습도 설정
        } else if (data.startsWith("MICRO:") && data.length() > "MICRO:".length()) { // 미세먼지 데이터
            String micro = data.substring("MICRO:".length()).trim();
            try {
                int microValue = Integer.parseInt(micro);
                updateMicroStatus(microValue); // 미세먼지 상태 설정
            } catch (NumberFormatException e) {
                Log.e(TAG, "Invalid micro data: " + micro);
                microTextView.setText("데이터 없음");
            }

        } else if (data.startsWith("INTEMP:") && data.length() > "INTEMP:".length()) { // 실내 온도 데이터
            String INTEMP = data.substring("INTEMP:".length()).trim();
            livingtempView.setText(INTEMP + " °C"); // 실내 온도 설정

        }
    }

    // 미세먼지 상태를 업데이트하는 메서드
    private void updateMicroStatus(int microValue) {
        String status;
        if (microValue <= 30) {
            status = "좋음"; // 미세먼지 상태가 좋음
        } else if (microValue <= 80) {
            status = "보통"; // 미세먼지 상태가 보통
        } else if (microValue <= 150) {
            status = "나쁨"; // 미세먼지 상태가 나쁨
        } else {
            status = "매우 나쁨"; // 미세먼지 상태가 매우 나쁨
        }
        microTextView.setText(status); // 텍스트 뷰에 설정
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 액티비티 종료 시 서버 연결 해제
        if (socketActivity != null) {
            socketActivity.disconnect();
        }
    }
}

*/

// -------------------------------------------------------------------------------------------------

// 긴급 팝업 기능까지 구현 완료 !!

/*

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class HomeActivity extends AppCompatActivity implements SocketActivity.OnDataReceivedListener {

    private static final String TAG = "HomeActivity";
    private TextView tempTextView, rehTextView, microTextView, livingtempView;
    private Handler uiHandler;
    private SwitchCompat ledSwitch,acSwitch, airSwitch, heaterSwitch, tempSwitch; // LED 스위치 추가
    private SocketActivity socketActivity;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // UI 요소 초기화
        tempTextView = findViewById(R.id.out_temp); // 실외 온도
        rehTextView = findViewById(R.id.out_humid); // 실외 습도
        microTextView = findViewById(R.id.microdust); // 실외 미세먼지
        livingtempView = findViewById(R.id.living_temp);
        ledSwitch = findViewById(R.id.LED_switch); // LED 스위치 초기화
        acSwitch = findViewById(R.id.AC_switch); // AC 스위치 초기화
        airSwitch = findViewById(R.id.AIR_switch); // LED 스위치 초기화
        heaterSwitch = findViewById(R.id.HEATER_switch); // AC 스위치 초기화
        tempSwitch = findViewById(R.id.TEMP_switch); // 자동 온도 추천

        uiHandler = new Handler(Looper.getMainLooper());

        // SocketActivity 인스턴스를 가져와서 서버 연결 시작
        socketActivity = SocketActivity.getInstance();
        socketActivity.connectToServer(this); // 서버 연결 및 데이터 수신

        // LED 스위치 리스너 설정
        ledSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                sendLedSignal("LED_ON");
            } else {
                sendLedSignal("LED_OFF");
            }
        });

        // 에어컨 스위치 리스너 설정
        acSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                sendLedSignal("AC_ON");
            } else {
                sendLedSignal("AC_OFF");
            }
        });

        // 공기청정기 스위치 리스너 설정
        airSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                sendLedSignal("PFAN_ON");
            } else {
                sendLedSignal("PFAN_OFF");
            }
        });

        // 히터 스위치 리스너 설정
        heaterSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                sendLedSignal("HFAN_ON");
            } else {
                sendLedSignal("HFAN_OFF");
            }
        });

        // 온도 추천 스위치 리스너 설정
        tempSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                sendLedSignal("Recommend_ON");
            } else {
                sendLedSignal("Recommend_OFF");
            }
        });

    }

    // 서버에 LED 신호를 전송하는 메서드
    private void sendLedSignal(String signal) {
        if (socketActivity != null) {
            socketActivity.sendData(signal);
            Log.d(TAG, "Sent LED signal: " + signal); // 신호 전송 시 로그 작성
        } else {
            Log.e(TAG, "SocketActivity is null, cannot send signal.");
        }
    }


    // 서버에서 데이터 수신 시 호출되는 메서드
    @Override
    public void onDataReceived(String data) {
        Log.d(TAG, "Received: " + data); // 수신 데이터 로그 출력
        parseAndSetData(data);

        if (data.equals("BTN_ON")) {
            runOnUiThread(this::showAlertForBtnOn); // UI 스레드에서 팝업 실행
        }

    }

    // 비상버튼 팝업창
    private void showAlertForBtnOn() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 커스텀 레이아웃 인플레이션
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.emergency, null);

        // AlertDialog에 커스텀 레이아웃 설정
        builder.setView(dialogView)
                .setTitle("알림")
                .setPositiveButton("확인", (dialog, which) -> dialog.dismiss())
                .show();
    }

    // 데이터를 파싱하고 UI에 설정하는 메서드
    private void parseAndSetData(String data) {
        Log.d(TAG, "Parsing Data: " + data); // 전체 데이터 확인

        if (data.startsWith("OUTTEMP:") && data.length() > "OUTTEMP:".length()) { // 실외 온도 데이터
            String OUTTEMP = data.substring("OUTTEMP:".length()).trim();
            tempTextView.setText(OUTTEMP + " °C"); // 온도 설정
        } else if (data.startsWith("REH:") && data.length() > "REH:".length()) { // 실외 습도 데이터
            String reh = data.substring("REH:".length()).trim();
            rehTextView.setText(reh + " %"); // 습도 설정
        } else if (data.startsWith("MICRO:") && data.length() > "MICRO:".length()) { // 미세먼지 데이터
            String micro = data.substring("MICRO:".length()).trim();
            try {
                int microValue = Integer.parseInt(micro);
                updateMicroStatus(microValue); // 미세먼지 상태 설정
            } catch (NumberFormatException e) {
                Log.e(TAG, "Invalid micro data: " + micro);
                microTextView.setText("데이터 없음");
            }

        } else if (data.startsWith("INTEMP:") && data.length() > "INTEMP:".length()) { // 실내 온도 데이터
            String INTEMP = data.substring("INTEMP:".length()).trim();
            livingtempView.setText(INTEMP + " °C"); // 실내 온도 설정

        }
    }

    // 미세먼지 상태를 업데이트하는 메서드
    private void updateMicroStatus(int microValue) {
        String status;
        if (microValue <= 30) {
            status = "좋음"; // 미세먼지 상태가 좋음
        } else if (microValue <= 80) {
            status = "보통"; // 미세먼지 상태가 보통
        } else if (microValue <= 150) {
            status = "나쁨"; // 미세먼지 상태가 나쁨
        } else {
            status = "매우 나쁨"; // 미세먼지 상태가 매우 나쁨
        }
        microTextView.setText(status); // 텍스트 뷰에 설정
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 액티비티 종료 시 서버 연결 해제
        if (socketActivity != null) {
            socketActivity.disconnect();
        }
    }
}

 */

// -------------------------------------------------------------------------------------------------

// 사용자 온도 설정 기능 구현하려고 노오력 !!

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class HomeActivity extends AppCompatActivity implements SocketActivity.OnDataReceivedListener {

    private static final String TAG = "HomeActivity";
    private TextView tempTextView, rehTextView, microTextView, livingtempView;
    private Handler uiHandler;
    private EditText livConTemp;
    private SwitchCompat ledSwitch,acSwitch, airSwitch, heaterSwitch, tempSwitch, setSwitch; // LED 스위치 추가
    private SocketActivity socketActivity;
    private Button setBut;

    private boolean isSetSwitchOn = false; // 스위치 상태를 저장하는 변수

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // UI 요소 초기화
        tempTextView = findViewById(R.id.out_temp); // 실외 온도
        rehTextView = findViewById(R.id.out_humid); // 실외 습도
        microTextView = findViewById(R.id.microdust); // 실외 미세먼지
        livingtempView = findViewById(R.id.living_temp);
        livConTemp = findViewById(R.id.liv_con_temp);
        ledSwitch = findViewById(R.id.LED_switch); // LED 스위치 초기화
        acSwitch = findViewById(R.id.AC_switch); // AC 스위치 초기화
        airSwitch = findViewById(R.id.AIR_switch); // LED 스위치 초기화
        heaterSwitch = findViewById(R.id.HEATER_switch); // AC 스위치 초기화
        tempSwitch = findViewById(R.id.TEMP_switch); // 자동 온도 추천

        setSwitch = findViewById(R.id.set_switch);
        setBut = findViewById(R.id.set_but); // 설정온도입력 완료 버튼

        uiHandler = new Handler(Looper.getMainLooper());

        // SocketActivity 인스턴스를 가져와서 서버 연결 시작
        socketActivity = SocketActivity.getInstance();
        socketActivity.connectToServer(this); // 서버 연결 및 데이터 수신

        // LED 스위치 리스너 설정
        ledSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                sendLedSignal("LED_ON");
            } else {
                sendLedSignal("LED_OFF");
            }
        });

        // 에어컨 스위치 리스너 설정
        acSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                sendLedSignal("AC_ON");
            } else {
                sendLedSignal("AC_OFF");
            }
        });

        // 공기청정기 스위치 리스너 설정
        airSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                sendLedSignal("PFAN_ON");
            } else {
                sendLedSignal("PFAN_OFF");
            }
        });

        // 히터 스위치 리스너 설정
        heaterSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                sendLedSignal("HFAN_ON");
            } else {
                sendLedSignal("HFAN_OFF");
            }
        });

        // 온도 추천 스위치 리스너 설정
        tempSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                sendLedSignal("Recommend_ON");
            } else {
                sendLedSignal("Recommend_OFF");
            }
        });

        /*
        // set_switch 스위치 리스너 설정
        setSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // 스위치가 오른쪽으로 갔을 때 compareAndSendSignal 호출
                compareAndSendSignal();
            }
            // 스위치가 왼쪽으로 갔을 때는 아무 동작도 하지 않음
        });

         */

        // set_switch 스위치 리스너 설정
        setSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isSetSwitchOn = isChecked; // 스위치 상태 저장

            if (!isChecked) {
                // 스위치가 왼쪽으로 갔을 때 No_Signal, HFAN_OFF, AC_OFF 전송
                sendLedSignal("No_Signal");
                sendLedSignal("HFAN_OFF");
                sendLedSignal("AC_OFF");
            }
        });

        // set_but 버튼 클릭 리스너 설정
        setBut.setOnClickListener(v -> {
            if (isSetSwitchOn) {
                // 스위치가 오른쪽으로 켜져 있을 때만 compareAndSendSignal 호출
                compareAndSendSignal();
            }
        });
    }

    // 서버에 LED 신호를 전송하는 메서드
    private void sendLedSignal(String signal) {
        if (socketActivity != null) {
            socketActivity.sendData(signal);
            Log.d(TAG, "Sent LED signal: " + signal); // 신호 전송 시 로그 작성
        } else {
            Log.e(TAG, "SocketActivity is null, cannot send signal.");
        }
    }


    // 서버에서 데이터 수신 시 호출되는 메서드
    @Override
    public void onDataReceived(String data) {
        Log.d(TAG, "Received: " + data); // 수신 데이터 로그 출력
        parseAndSetData(data);

        if (data.equals("BTN_ON")) {
            runOnUiThread(this::showAlertForBtnOn); // UI 스레드에서 팝업 실행
        }

    }

    // 비상버튼 팝업창
    private void showAlertForBtnOn() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 커스텀 레이아웃 인플레이션
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.emergency, null);

        // AlertDialog에 커스텀 레이아웃 설정
        builder.setView(dialogView)
                .setTitle("알림")
                .setPositiveButton("확인", (dialog, which) -> dialog.dismiss())
                .show();
    }

    // 데이터를 파싱하고 UI에 설정하는 메서드
    private void parseAndSetData(String data) {
        Log.d(TAG, "Parsing Data: " + data); // 전체 데이터 확인

        if (data.startsWith("OUTTEMP:") && data.length() > "OUTTEMP:".length()) { // 실외 온도 데이터
            String OUTTEMP = data.substring("OUTTEMP:".length()).trim();
            tempTextView.setText(OUTTEMP + " °C"); // 온도 설정
        } else if (data.startsWith("REH:") && data.length() > "REH:".length()) { // 실외 습도 데이터
            String reh = data.substring("REH:".length()).trim();
            rehTextView.setText(reh + " %"); // 습도 설정
        } else if (data.startsWith("MICRO:") && data.length() > "MICRO:".length()) { // 미세먼지 데이터
            String micro = data.substring("MICRO:".length()).trim();
            try {
                int microValue = Integer.parseInt(micro);
                updateMicroStatus(microValue); // 미세먼지 상태 설정
            } catch (NumberFormatException e) {
                Log.e(TAG, "Invalid micro data: " + micro);
                microTextView.setText("데이터 없음");
            }

        } else if (data.startsWith("INTEMP:") && data.length() > "INTEMP:".length()) { // 실내 온도 데이터
            String INTEMP = data.substring("INTEMP:".length()).trim();
            livingtempView.setText(INTEMP + " °C"); // 실내 온도 설정

        }
    }

    // 온도 비교 후 신호 전송하는 메서드
    private void compareAndSendSignal() {
        try {
            String livingTempText = livingtempView.getText().toString().replace(" °C", "").trim();
            String targetTempText = livConTemp.getText().toString().trim();

            if (!livingTempText.isEmpty() && !targetTempText.isEmpty()) {
                float livingTemp = Float.parseFloat(livingTempText);
                float targetTemp = Float.parseFloat(targetTempText);

                if (livingTemp > targetTemp) {
                    sendLedSignal("AC_ON");
                    sendLedSignal("HFAN_OFF");
                    sendLedSignal("usersettemp, " + targetTemp);
                } else if (livingTemp < targetTemp) {
                    sendLedSignal("HFAN_ON");
                    sendLedSignal("AC_OFF");
                    sendLedSignal("usersettemp, " + targetTemp);
                } else {
                    sendLedSignal("No_Signal");
                }
            }
        } catch (NumberFormatException e) {
            Log.e(TAG, "Invalid temperature input", e);
        }
    }

    // 미세먼지 상태를 업데이트하는 메서드
    private void updateMicroStatus(int microValue) {
        String status;
        if (microValue <= 30) {
            status = "좋음"; // 미세먼지 상태가 좋음
        } else if (microValue <= 80) {
            status = "보통"; // 미세먼지 상태가 보통
        } else if (microValue <= 150) {
            status = "나쁨"; // 미세먼지 상태가 나쁨
        } else {
            status = "매우 나쁨"; // 미세먼지 상태가 매우 나쁨
        }
        microTextView.setText(status); // 텍스트 뷰에 설정
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 액티비티 종료 시 서버 연결 해제
        if (socketActivity != null) {
            socketActivity.disconnect();
        }
    }
}