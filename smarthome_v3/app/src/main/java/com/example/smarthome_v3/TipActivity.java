package com.example.smarthome_v3;

/*

package com.example.smarthome_v1;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TipActivity extends AppCompatActivity {

    private TextView userTempTextView;
    private TextView outTempTextView1;
    private TextView outTempTextView2;
    private TextView livTempTextView1;
    private TextView livTempTextView2;
    private TextView weatherTextView;
    private TextView weatherPreTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        // Initialize TextViews
        userTempTextView = findViewById(R.id.user_temp);
        outTempTextView1 = findViewById(R.id.out_temp1);
        outTempTextView2 = findViewById(R.id.out_temp2);
        livTempTextView1 = findViewById(R.id.liv_temp1);
        livTempTextView2 = findViewById(R.id.liv_temp2);
        weatherTextView = findViewById(R.id.weather);
        weatherPreTextView = findViewById(R.id.weather_pre);

        // Get temperature and humidity from Intent
        String temperature = getIntent().getStringExtra("temperature");
        String humidity = getIntent().getStringExtra("humidity");

        // Display received temperature and humidity
        userTempTextView.setText("Current Temperature: " + temperature);
        outTempTextView1.setText("Temperature: " + temperature);
        outTempTextView2.setText("Humidity: " + humidity);

        // Generate and display clothing tip
        String tip = getClothingTip(temperature, humidity);
        livTempTextView1.setText("Clothing Tip: ");
        livTempTextView2.setText(tip);
    }

    private String getClothingTip(String temperature, String humidity) {
        double temp = Double.parseDouble(temperature.replace("℃", ""));
        double hum = Double.parseDouble(humidity.replace("%", ""));
        StringBuilder tip = new StringBuilder();

        if (temp > 30) {
            tip.append("날씨가 매우 덥습니다. 가볍고 통풍이 잘 되는 옷을 입으세요.");
            if (hum > 60) {
                tip.append(" 습도가 높아 불쾌지수가 높을 수 있습니다. 물을 충분히 섭취하세요.");
                tip.append(" 양산을 챙기세요.");
            }
        } else if (temp > 20) {
            tip.append("날씨가 따뜻합니다. 가벼운 옷을 입으세요.");
            if (hum > 60) {
                tip.append(" 습도가 높아 불쾌지수가 높을 수 있습니다. 땀 흡수가 잘 되는 옷을 입으세요.");
                tip.append(" 양산을 챙기세요.");
            }
        } else if (temp > 10) {
            tip.append("날씨가 쌀쌀합니다. 얇은 겉옷을 준비하세요.");
            if (hum > 60) {
                tip.append(" 습도가 높아 비가 올 수 있으니 창문을 닫으세요.");
                tip.append(" 우산을 챙기세요.");
            }
        } else if (temp > 0) {
            tip.append("날씨가 춥습니다. 따뜻한 옷을 입으세요.");
            if (hum > 60) {
                tip.append(" 습도가 높아 비나 눈이 올 수 있으니 창문을 닫으세요.");
                tip.append(" 우산을 챙기세요.");
            }
        } else {
            tip.append("날씨가 매우 춥습니다. 두꺼운 옷을 입고, 핫팩을 챙기세요.");
            if (hum > 60) {
                tip.append(" 습도가 높아 눈이 올 수 있으니 창문을 닫으세요.");
                tip.append(" 우산을 챙기세요.");
            }
        }

        return tip.toString();
    }
}

 */

// -------------------------------------------------------------------------------------------------

/*

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class TipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);
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

public class TipActivity extends AppCompatActivity {

    private static final String TAG = "TipActivity";
    private static final String SERVER_IP = "192.168.0.180"; // 서버 IP 주소
    private static final int SERVER_PORT = 12345; // 서버 포트 번호

    private TextView userTempTextView, rainTextView;
    private Handler uiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        // UI 요소 초기화
        userTempTextView = findViewById(R.id.user_temp); // 서버에서 받아올 온도
        rainTextView = findViewById(R.id.out_rain); // 서버에서 받아올 강수확률

        uiHandler = new Handler(Looper.getMainLooper());

        // 서버에서 데이터를 받아와 UI에 표시
        receiveDataFromServer();
    }

    private void receiveDataFromServer() {
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
                    parseAndSetData(line);
                }

            } catch (Exception e) {
                Log.e(TAG, "Error occurred", e);
            }
            return null;
        }

        private void parseAndSetData(String data) {
            Log.d(TAG, "Parsing Data: " + data); // 전체 데이터 확인

            if (data.startsWith("TEMP: ")) {
                String temp = data.substring("TEMP: ".length());
                Log.d(TAG, "Parsed Temperature: " + temp); // 온도 로그 추가
                uiHandler.post(() -> userTempTextView.setText(" 현재 실외온도는 " + temp + " °C 입니다."));
            } else if (data.startsWith("POP: ")) {
                String pop = data.substring("POP: ".length());
                Log.d(TAG, "Parsed Rain Probability: " + pop); // 강수확률 로그 추가
                uiHandler.post(() -> rainTextView.setText("현재 강수확률은 " + pop + " % 입니다."));
            }
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

public class TipActivity extends AppCompatActivity implements SocketActivity.OnDataReceivedListener {

    private static final String TAG = "TipActivity";
    private TextView userTempTextView, rainTextView;
    private Handler uiHandler;
    private SocketActivity socketActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        // UI 요소 초기화
        userTempTextView = findViewById(R.id.user_temp); // 서버에서 받아올 온도
        rainTextView = findViewById(R.id.out_rain); // 서버에서 받아올 강수확률

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

        if (data.startsWith("TEMP: ")) {
            String temp = data.substring("TEMP: ".length());
            Log.d(TAG, "Parsed Temperature: " + temp); // 온도 로그 추가
            uiHandler.post(() -> userTempTextView.setText(" 현재 실외온도는 " + temp + " °C 입니다."));
        } else if (data.startsWith("POP: ")) {
            String pop = data.substring("POP: ".length());
            Log.d(TAG, "Parsed Rain Probability: " + pop); // 강수확률 로그 추가
            uiHandler.post(() -> rainTextView.setText("현재 강수확률은 " + pop + " % 입니다."));
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

// -------------------------------------------------------------------------------------------------

// GPT 연결하기 전 코드

/*

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TipActivity extends AppCompatActivity implements SocketActivity.OnDataReceivedListener {

    private static final String TAG = "TipActivity";
    private TextView userTempTextView, rainTextView;
    private Handler uiHandler;
    private SocketActivity socketActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        // UI 요소 초기화
        userTempTextView = findViewById(R.id.user_temp); // 서버에서 받아올 온도
        rainTextView = findViewById(R.id.out_rain); // 서버에서 받아올 강수확률

        uiHandler = new Handler(Looper.getMainLooper());

        // SocketActivity 인스턴스를 가져와서 서버 연결 시작
        socketActivity = SocketActivity.getInstance();
        socketActivity.connectToServer(this); // 서버 연결 및 데이터 수신
    }

    // 서버에서 데이터 수신 시 호출되는 메서드
    @Override
    public void onDataReceived(String data) {
        Log.d(TAG, "Data received: " + data);
        uiHandler.post(() -> parseAndSetData(data));
    }

    // 데이터를 파싱하고 UI에 설정하는 메서드
    private void parseAndSetData(String data) {
        Log.d(TAG, "Parsing data: " + data); // 데이터 파싱 로그 추가

        if (data.startsWith("TEMP:")) {  // 온도 데이터
            String temp = data.substring("TEMP:".length()).trim();
            userTempTextView.setText("오늘의 기온은 " + temp + " °C 도입니다."); // 온도 설정
        } else if (data.startsWith("POP:")) {  // 강수확률 데이터
            String POP = data.substring("POP:".length()).trim();
            rainTextView.setText("오늘의 강수확률은 "+ POP + " % 입니다."); // 강수확률 설정

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

// -------------------------------------------------------------------------------------------------


// 날씨는 안 뜨지만 gpt는 잘 연결됨

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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TipActivity extends AppCompatActivity {

    private TextView textView;
    private TextView textView2;
    private TextView textView3;
    private Handler uiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        // TextView 연결
        textView = findViewById(R.id.textview);
        textView2 = findViewById(R.id.textview2);
        textView3 = findViewById(R.id.textview3);
        uiHandler = new Handler(Looper.getMainLooper());

        // 소켓 연결 및 데이터 수신
        new SocketTask().execute();
    }

    private class SocketTask extends AsyncTask<Void, Void, Void> {

        private int messageIndex = 0;  // 클래스 멤버 변수로 선언하여 인덱스를 관리

        @Override
        protected Void doInBackground(Void... voids) {
            String serverAddress = "192.168.0.180";  // 서버 주소
            int serverPort = 12345;  // 서버 포트

            try (Socket socket = new Socket(serverAddress, serverPort);
                 PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"))) {


                // 서버로 보낼 메시지
                String message = "Tip_Data";
                out.println(message);  // 메시지 전송
                out.flush();

                Log.d("SocketTest", "Sent: " + message);

                // 서버로부터의 응답을 수신
                String line;

                while ((line = in.readLine()) != null) {
                    Log.d("SocketTest", "Received line from server: " + line);

                    // 정규 표현식을 사용해 "숫자 + 마침표" 제거
                    String cleanedLine = line.replaceFirst("^\\d+\\.\\s*", "").trim();

                    // 메시지를 개별 줄로 분리하여 TextView에 배치
                    final String finalLine = cleanedLine;
                    uiHandler.post(() -> {
                        if (messageIndex == 0) {
                            textView.setText(finalLine);
                        } else if (messageIndex == 1) {
                            textView2.setText(finalLine);
                        } else if (messageIndex == 2) {
                            textView3.setText(finalLine);
                        }
                        messageIndex++;
                    });

                    // 3개 이상의 메시지는 처리하지 않도록 종료
                    if (messageIndex >= 3) break;
                }
                Log.d("SocketTest", "End of stream reached");

            } catch (UnknownHostException e) {
                Log.e("SocketTest", "Unknown host", e);
            } catch (IOException e) {
                Log.e("SocketTest", "Error occurred", e);
            }

            return null;
        }
    }
}

 */

// -------------------------------------------------------------------------------------------------

/*

// 날씨도 뜨고 gpt도 뜨는데..!?
// gpt가 뜨기 전에는 다른게 나옴..

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TipActivity extends AppCompatActivity implements SocketActivity.OnDataReceivedListener {

    private static final String TAG = "TipActivity";
    private TextView userTempTextView, rainTextView, textView, textView2, textView3;
    private Handler uiHandler;
    private SocketActivity socketActivity;
    private int messageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        // UI 요소 초기화
        userTempTextView = findViewById(R.id.user_temp);
        rainTextView = findViewById(R.id.out_rain);
        textView = findViewById(R.id.textview);
        textView2 = findViewById(R.id.textview2);
        textView3 = findViewById(R.id.textview3);

        uiHandler = new Handler(Looper.getMainLooper());

        // SocketActivity 인스턴스를 가져와서 서버 연결 시작
        socketActivity = SocketActivity.getInstance();
        socketActivity.connectToServer(this); // 서버 연결 및 데이터 수신
    }


    // REH, HUM 이 뜰 때
    // 서버에서 데이터 수신 시 호출되는 메서드
    @Override
    public void onDataReceived(String data) {
        Log.d(TAG, "Data received: " + data);
        uiHandler.post(() -> parseAndSetData(data));
    }


    // 데이터를 파싱하고 UI에 설정하는 메서드
    private void parseAndSetData(String data) {
        Log.d(TAG, "Parsing data: " + data);

        if (data.startsWith("TEMP:")) {  // 온도 데이터
            String temp = data.substring("TEMP:".length()).trim();
            userTempTextView.setText("오늘의 기온은 " + temp + " °C 도입니다.");
        } else if (data.startsWith("POP:")) {  // 강수확률 데이터
            String pop = data.substring("POP:".length()).trim();
            rainTextView.setText("오늘의 강수확률은 " + pop + " % 입니다.");
        } else {
            displayGeneralMessage(data);
        }
    }

    // 자꾸 데이터에 Tip_DATA 말고 다른 데이터가 출력됨ㅁ
    // 일반 메시지를 순서대로 3개의 TextView에 출력하는 메서드
    private void displayGeneralMessage(String message) {
        String cleanedMessage = message.replaceFirst("^\\d+\\.\\s*", "").trim();

        if (messageIndex == 0) {
            textView.setText(cleanedMessage);
        } else if (messageIndex == 1) {
            textView2.setText(cleanedMessage);
        } else if (messageIndex == 2) {
            textView3.setText(cleanedMessage);
        }
        messageIndex++;

        if (messageIndex >= 3) {
            messageIndex = 0;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (socketActivity != null) {
            socketActivity.disconnect();
        }
    }
}

*/

// -------------------------------------------------------------------------------------------------

// Tip_DATA에서 받는게 안니 값은 textview,textview2, textview3에서 뜨지 않는데
// Tip_DATA 값을 받았는데도 textview,textview2, textview3에 뜨지 않음

/*

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TipActivity extends AppCompatActivity implements SocketActivity.OnDataReceivedListener {

    private static final String TAG = "TipActivity";
    private TextView userTempTextView, rainTextView, textView, textView2, textView3;
    private Handler uiHandler;
    private SocketActivity socketActivity;
    private int messageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        // UI 요소 초기화
        userTempTextView = findViewById(R.id.user_temp);
        rainTextView = findViewById(R.id.out_rain);
        textView = findViewById(R.id.textview);
        textView2 = findViewById(R.id.textview2);
        textView3 = findViewById(R.id.textview3);

        // 텍스트뷰 초기값 설정
        textView.setText("");
        textView2.setText("");
        textView3.setText("");

        uiHandler = new Handler(Looper.getMainLooper());

        // SocketActivity 인스턴스를 가져와서 서버 연결 시작
        socketActivity = SocketActivity.getInstance();
        socketActivity.connectToServer(this); // 서버 연결 및 데이터 수신
    }

    // 서버에서 데이터 수신 시 호출되는 메서드
    @Override
    public void onDataReceived(String data) {
        Log.d(TAG, "Data received: " + data);
        uiHandler.post(() -> parseAndSetData(data));
    }

    // 데이터를 파싱하고 UI에 설정하는 메서드
    private void parseAndSetData(String data) {
        Log.d(TAG, "Parsing data: " + data);

        if (data.startsWith("TEMP:")) {  // 온도 데이터
            String temp = data.substring("TEMP:".length()).trim();
            userTempTextView.setText("오늘의 기온은 " + temp + " °C 도입니다.");
        } else if (data.startsWith("POP:")) {  // 강수확률 데이터
            String pop = data.substring("POP:".length()).trim();
            rainTextView.setText("오늘의 강수확률은 " + pop + " % 입니다.");
        } else if (data.startsWith("Tip_Data:")) {  // Tip_Data로 시작하는 일반 메시지
            Log.d(TAG, "Tip_Data message received. Passing to displayGeneralMessage.");
            displayGeneralMessage(data);
        } else {
            Log.d(TAG, "Ignored non-Tip_Data message: " + data);
        }
    }

    // Tip_Data가 수신될 때에만 일반 메시지를 순서대로 3개의 TextView에 출력하는 메서드
    private void displayGeneralMessage(String message) {
        // Tip_Data에서 실제 메시지만 추출
        String cleanedMessage = message.replaceFirst("^Tip_Data:\\s*", "").trim();

        Log.d(TAG, "Displaying Tip_Data message: " + cleanedMessage);

        if (messageIndex == 0) {
            textView.setText(cleanedMessage);
        } else if (messageIndex == 1) {
            textView2.setText(cleanedMessage);
        } else if (messageIndex == 2) {
            textView3.setText(cleanedMessage);
        }
        messageIndex++;

        if (messageIndex >= 3) {
            messageIndex = 0;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (socketActivity != null) {
            socketActivity.disconnect();
        }
    }
}

 */

// -------------------------------------------------------------------------------------------------

// Tip 기능 구현 완료. 아싸뵤~ ^^
// 긴급 팝업 기능까지!!

/*

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class TipActivity extends AppCompatActivity implements SocketActivity.OnDataReceivedListener {

    private static final String TAG = "TipActivity";
    private TextView userTempTextView, rainTextView, textView, textView2, textView3;
    private Handler uiHandler;
    private SocketActivity socketActivity;
    private int messageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        // UI 요소 초기화
        userTempTextView = findViewById(R.id.user_temp);
        rainTextView = findViewById(R.id.out_rain);
        textView = findViewById(R.id.textview);
        textView2 = findViewById(R.id.textview2);
        textView3 = findViewById(R.id.textview3);

        // 텍스트뷰 초기값 설정
        textView.setText("");
        textView2.setText("");
        textView3.setText("");

        uiHandler = new Handler(Looper.getMainLooper());

        // SocketActivity 인스턴스를 가져와서 서버 연결 시작
        socketActivity = SocketActivity.getInstance();
        socketActivity.connectToServer(this); // 서버 연결 및 데이터 수신
    }

    // 서버에서 데이터 수신 시 호출되는 메서드
    @Override
    public void onDataReceived(String data) {
        Log.d(TAG, "Data received: " + data);
        uiHandler.post(() -> parseAndSetData(data));

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
        Log.d(TAG, "Parsing data: " + data);

        if (data.startsWith("TEMP:")) {  // 온도 데이터
            String temp = data.substring("TEMP:".length()).trim();
            userTempTextView.setText("오늘의 기온은 " + temp + " °C 도입니다.");
        } else if (data.startsWith("POP:")) {  // 강수확률 데이터
            String pop = data.substring("POP:".length()).trim();
            rainTextView.setText("오늘의 강수확률은 " + pop + " % 입니다.");
        } else if (data.matches("^[1-3]\\.\\s*.*")) {  // 일반 메시지 (1. , 2. , 3. 으로 시작)
            Log.d(TAG, "General message received. Passing to displayGeneralMessage.");
            displayGeneralMessage(data);
        } else {
            Log.d(TAG, "Ignored non-Tip_Data message: " + data);
        }
    }

    // Tip_Data가 수신될 때에만 일반 메시지를 순서대로 3개의 TextView에 출력하는 메서드
    private void displayGeneralMessage(String message) {
        // 메시지에서 앞의 번호 제거
        // String cleanedMessage = message.replaceFirst("^[1-3]\\.\\s*", "");
        String cleanedMessage = message.replaceFirst("^[1-3]\\.", "");

        Log.d(TAG, "Displaying Tip_Data message: " + cleanedMessage);

        if (messageIndex == 0) {
            textView.setText(cleanedMessage);
        } else if (messageIndex == 1) {
            textView2.setText(cleanedMessage);
        } else if (messageIndex == 2) {
            textView3.setText(cleanedMessage);
        }
        messageIndex++;

        if (messageIndex >= 3) {
            messageIndex = 0;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (socketActivity != null) {
            socketActivity.disconnect();
        }
    }
}

 */

// -------------------------------------------------------------------------------------------------

// gpt 부분 공백 살리기까지 완료!!!!
// TIP 졸업!!!!!!!!!!!!!!!!!!!!!!!

/*

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class TipActivity extends AppCompatActivity implements SocketActivity.OnDataReceivedListener {

    private static final String TAG = "TipActivity";
    private TextView userTempTextView, rainTextView, textView, textView2, textView3;
    private Handler uiHandler;
    private SocketActivity socketActivity;
    private int messageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        // UI 요소 초기화
        userTempTextView = findViewById(R.id.user_temp);
        rainTextView = findViewById(R.id.out_rain);
        textView = findViewById(R.id.textview);
        textView2 = findViewById(R.id.textview2);
        textView3 = findViewById(R.id.textview3);

        // 텍스트뷰 초기값 설정
        textView.setText("");
        textView2.setText("");
        textView3.setText("");

        uiHandler = new Handler(Looper.getMainLooper());

        // SocketActivity 인스턴스를 가져와서 서버 연결 시작
        socketActivity = SocketActivity.getInstance();
        socketActivity.connectToServer(this); // 서버 연결 및 데이터 수신
    }

    // 서버에서 데이터 수신 시 호출되는 메서드
    @Override
    public void onDataReceived(String data) {
        Log.d(TAG, "Data received: " + data);
        uiHandler.post(() -> parseAndSetData(data));

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
        Log.d(TAG, "Parsing data: " + data);

        if (data.startsWith("TEMP:")) {  // 온도 데이터
            String temp = data.substring("TEMP:".length()).trim();
            userTempTextView.setText("오늘의 기온은 " + temp + " °C 도입니다.");
        } else if (data.startsWith("POP:")) {  // 강수확률 데이터
            String pop = data.substring("POP:".length()).trim();
            rainTextView.setText("오늘의 강수확률은 " + pop + " % 입니다.");
        } else if (data.matches("^[1-3]\\.\\s*.*")) {  // 일반 메시지 (1. , 2. , 3. 으로 시작)
            Log.d(TAG, "General message received. Passing to displayGeneralMessage.");
            displayGeneralMessage(data);
        } else {
            Log.d(TAG, "Ignored non-Tip_Data message: " + data);
        }
    }


    private void displayGeneralMessage(String message) {
        // 번호와 점만 제거하고 공백은 그대로 유지
        String cleanedMessage = message.replaceFirst("^\\d+\\.", "");

        uiHandler.post(() -> {
            Log.d(TAG, "Displaying Tip_Data message: " + cleanedMessage);

            // 메시지를 가공한 후 TextView에 설정
            if (messageIndex == 0) {
                textView.setText(cleanedMessage);
            } else if (messageIndex == 1) {
                textView2.setText(cleanedMessage);
            } else if (messageIndex == 2) {
                textView3.setText(cleanedMessage);
            }
            messageIndex++;

            // 3개 이상의 메시지를 처리하지 않도록 순환 설정
            if (messageIndex >= 3) {
                messageIndex = 0;
            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (socketActivity != null) {
            socketActivity.disconnect();
        }
    }
}

 */

// --------------------------------------------------------------------

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class TipActivity extends AppCompatActivity implements SocketActivity.OnDataReceivedListener {

    private static final String TAG = "TipActivity";
    private TextView userTempTextView, rainTextView, textView, textView2, textView3;
    private Handler uiHandler;
    private SocketActivity socketActivity;
    private int messageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        // UI 요소 초기화
        userTempTextView = findViewById(R.id.user_temp);
        rainTextView = findViewById(R.id.out_rain);
        textView = findViewById(R.id.textview);
        textView2 = findViewById(R.id.textview2);
        textView3 = findViewById(R.id.textview3);

        // 텍스트뷰 초기값 설정
        textView.setText("");
        textView2.setText("");
        textView3.setText("");

        uiHandler = new Handler(Looper.getMainLooper());

        // SocketActivity 인스턴스를 가져와서 서버 연결 시작
        socketActivity = SocketActivity.getInstance();

        // SocketActivity의 활성화된 액티비티 설정
        // socketActivity.getInstance().setActiveActivity("TipActivity");

        socketActivity.connectToServer(this); // 서버 연결 및 데이터 수신
    }

    // 서버에서 데이터 수신 시 호출되는 메서드
    @Override
    public void onDataReceived(String data) {
        Log.d(TAG, "Data received: " + data);
        uiHandler.post(() -> parseAndSetData(data));

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
        Log.d(TAG, "Parsing data: " + data);

        if (data.startsWith("TEMP:")) {  // 온도 데이터
            String temp = data.substring("TEMP:".length()).trim();
            userTempTextView.setText("오늘의 기온은 " + temp + " °C 도입니다.");
        } else if (data.startsWith("POP:")) {  // 강수확률 데이터
            String pop = data.substring("POP:".length()).trim();
            rainTextView.setText("오늘의 강수확률은 " + pop + " % 입니다.");
        } else if (data.matches("^[1-3]\\.\\s*.*")) {  // 일반 메시지 (1. , 2. , 3. 으로 시작)
            Log.d(TAG, "General message received. Passing to displayGeneralMessage.");
            displayGeneralMessage(data);
        } else {
            Log.d(TAG, "Ignored non-Tip_Data message: " + data);
        }
    }


    private void displayGeneralMessage(String message) {
        // 번호와 점만 제거하고 공백은 그대로 유지
        String cleanedMessage = message.replaceFirst("^\\d+\\.", "");

        uiHandler.post(() -> {
            Log.d(TAG, "Displaying Tip_Data message: " + cleanedMessage);

            // 메시지를 가공한 후 TextView에 설정
            if (messageIndex == 0) {
                textView.setText(cleanedMessage);
            } else if (messageIndex == 1) {
                textView2.setText(cleanedMessage);
            } else if (messageIndex == 2) {
                textView3.setText(cleanedMessage);
            }
            messageIndex++;

            // 3개 이상의 메시지를 처리하지 않도록 순환 설정
            if (messageIndex >= 3) {
                messageIndex = 0;
            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (socketActivity != null) {
            socketActivity.disconnect();
        }
    }
}