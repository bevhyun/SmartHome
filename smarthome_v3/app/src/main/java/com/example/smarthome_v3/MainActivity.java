package com.example.smarthome_v3;
/*

package com.example.smarthome_v2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String SERVER_IP = "220.69.207.113"; // 서버 IP 주소
    private static final int SERVER_PORT = 12345; // 서버 포트 번호

    private TextView temperatureTextView;
    private TextView humidityTextView;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temperatureTextView = findViewById(R.id.out_temp);
        humidityTextView = findViewById(R.id.out_humid);
        handler = new Handler(Looper.getMainLooper());

        // 서버에서 센서 값을 받아와 UI에 표시하는 메서드 호출
        receiveSensorValueFromServer();
    }

    private void receiveSensorValueFromServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String serverIp = "220.69.207.113";  // 서버의 IP 주소로 변경
                    int serverPort = 12345;  // 서버의 포트 번호로 변경
                    Socket socket = new Socket(SERVER_IP, SERVER_PORT);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    while (true) {
                        // 서버로부터 센서 값 수신
                        final String sensorValue = reader.readLine();

                        // UI 업데이트
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                updateSensorValue(sensorValue);
                            }
                        });
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Error in receiving sensor value: " + e.getMessage());
                    showToast("Failed to receive sensor value from server");
                }
            }
        }).start();
    }

    private void updateSensorValue(String sensorValue) {
        try {
            // 받아온 센서 값을 쉼표를 기준으로 분리하여 온도와 습도로 나눔
            String[] values = sensorValue.split(",");
            if (values.length >= 2) {
                // 온도와 습도를 TextView에 설정
                temperatureTextView.setText(values[0] + "℃");
                humidityTextView.setText(values[1] + "%");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in parsing sensor value: " + e.getMessage());
            showToast("Failed to parse sensor value");
        }
    }

    private void showToast(final String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}


 */

// 되는 코드

// -------------------------------------------------------------------------------------------------

/*

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String SERVER_IP = "220.69.207.113"; // 서버 IP 주소
    private static final int SERVER_PORT = 12345; // 서버 포트 번호

    private TextView temperatureTextView;
    private TextView humidityTextView;
    private Handler handler;
    private LinearLayout homeLayout;
    private LinearLayout tipLayout;
    private LinearLayout graphLayout;
    private LinearLayout cctvLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temperatureTextView = findViewById(R.id.out_temp);
        humidityTextView = findViewById(R.id.out_humid);
        handler = new Handler(Looper.getMainLooper());

        homeLayout = findViewById(R.id.home_btn); // LinearLayout 초기화
        tipLayout = findViewById(R.id.tip_btn);   // LinearLayout 초기화
        graphLayout = findViewById(R.id.graph_btn);
        cctvLayout = findViewById(R.id.cctv_btn);

        // LinearLayout 클릭 리스너 설정
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        // tipLayout 클릭 리스너 설정
        tipLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TipActivity.class);
                startActivity(intent);
            }
        });

        // graphLayout 클릭 리스너 설정
        graphLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Graph button clicked");
                Intent intent = new Intent(MainActivity.this, GraphActivity.class);
                startActivity(intent);
            }
        });

        // cctvLayout 클릭 리스너 설정
        cctvLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CctvActivity.class);
                startActivity(intent);
            }
        });


        // 서버에서 센서 값을 받아와 UI에 표시하는 메서드 호출
        receiveSensorValueFromServer();
    }

    private void receiveSensorValueFromServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket(SERVER_IP, SERVER_PORT);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    while (true) {
                        // 서버로부터 센서 값 수신
                        final String sensorValue = reader.readLine();

                        // UI 업데이트
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                updateSensorValue(sensorValue);
                            }
                        });
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Error in receiving sensor value: " + e.getMessage());
                    showToast("Failed to receive sensor value from server");
                }
            }
        }).start();
    }

    private void updateSensorValue(String sensorValue) {
        try {
            // 받아온 센서 값을 쉼표를 기준으로 분리하여 온도와 습도로 나눔
            String[] values = sensorValue.split(",");
            if (values.length >= 2) {
                // 온도와 습도를 TextView에 설정
                temperatureTextView.setText(values[0] + "℃");
                humidityTextView.setText(values[1] + "%");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in parsing sensor value: " + e.getMessage());
            showToast("Failed to parse sensor value");
        }
    }

    private void showToast(final String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

 */

// -------------------------------------------------------------------------------------------------

/*

// 이거 되는거임 외부날씨 API 받기 전까지 다 되던거임!!!!!!!!!!!!!!


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String SERVER_IP = "220.69.207.113"; // 서버 IP 주소
    private static final int SERVER_PORT = 12345; // 서버 포트 번호

    private TextView temperatureTextView;
    private TextView humidityTextView;
    private TextView ptyTextView; // 강수형태
    private Handler handler;
    private LinearLayout homeLayout;
    private LinearLayout tipLayout;
    private LinearLayout graphLayout;
    private LinearLayout cctvLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temperatureTextView = findViewById(R.id.out_temp);
        // humidityTextView = findViewById(R.id.out_humid); // 습도
        // ptyTextView = findViewById(R.id.in_gas); // 강수형태
        handler = new Handler(Looper.getMainLooper());

        homeLayout = findViewById(R.id.home_btn); // LinearLayout 초기화
        tipLayout = findViewById(R.id.tip_btn);   // LinearLayout 초기화
        graphLayout = findViewById(R.id.graph_btn);
        cctvLayout = findViewById(R.id.cctv_btn);

        // Home영역을 누르면 HomeActivity로 이동
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        // Tip영역을 누르면 TipActivity로 이동
        tipLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TipActivity.class);
                startActivity(intent);
            }
        });

        // GRAPH영역을 누르면 GraphActivity로 이동
        graphLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Graph button clicked");
                Intent intent = new Intent(MainActivity.this, GraphActivity.class);
                startActivity(intent);
            }
        });

        // CCTV영역을 누르면 CctvActivity로 이동
        cctvLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CctvActivity.class);
                startActivity(intent);
            }
        });

        // 서버에서 센서 값을 받아와 UI에 표시하는 메서드 호출
        receiveSensorValueFromServer();
    }

    private void receiveSensorValueFromServer() {
        new SocketTask().execute();
    }

    private class SocketTask extends AsyncTask<Void, String, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
                 PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                String message = "REQUEST_DATA";
                out.println(message);
                Log.d(TAG, "Sent: " + message);

                String line;
                while ((line = in.readLine()) != null) {
                    Log.d(TAG, "Received line from server: " + line);
                    publishProgress(line);
                }

            } catch (UnknownHostException e) {
                Log.e(TAG, "Unknown host", e);
            } catch (SocketTimeoutException e) {
                Log.e(TAG, "Socket timeout", e);
            } catch (Exception e) {
                Log.e(TAG, "Error occurred", e);
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
            handler.post(() -> temperatureTextView.setText(temp + " ℃"));
        } else if (data.startsWith("PTY: ")) {
            String pty = data.substring("PTY: ".length());
            handler.post(() -> ptyTextView.setText(pty));
        } else if (data.startsWith("POP: ")) {
            String pop = data.substring("POP: ".length());
            handler.post(() -> humidityTextView.setText(pop + " %"));
        }
    }

    private void showToast(final String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

 */

// -------------------------------------------------------------------------------------------------

/*

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String SERVER_IP = "192.168.0.180"; // 서버 IP 주소
    private static final int SERVER_PORT = 12345; // 서버 포트 번호

    private Handler handler;
    private LinearLayout homeLayout, tipLayout, graphLayout, cctvLayout;
    private TextView tempTextView, rehTextView, uvTextView, microTextView;
    private Handler uiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI 요소 초기화
        handler = new Handler(Looper.getMainLooper());
        homeLayout = findViewById(R.id.home_btn);
        tipLayout = findViewById(R.id.tip_btn);
        graphLayout = findViewById(R.id.graph_btn);
        cctvLayout = findViewById(R.id.cctv_btn);
        tempTextView = findViewById(R.id.tempTextView);
        rehTextView = findViewById(R.id.rehTextView);
        uvTextView = findViewById(R.id.uvTextView);
        microTextView = findViewById(R.id.microTextView);

        uiHandler = new Handler(Looper.getMainLooper());

        // 버튼 클릭 리스너 설정
        setButtonListeners();

        // 서버에서 센서 값을 받아와 UI에 표시
        receiveSensorValueFromServer();
    }

    // 버튼 클릭 리스너 설정 메서드
    private void setButtonListeners() {
        homeLayout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, HomeActivity.class)));
        tipLayout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TipActivity.class)));
        graphLayout.setOnClickListener(v -> {
            Log.d(TAG, "Graph button clicked");
            startActivity(new Intent(MainActivity.this, GraphActivity.class));
        });
        cctvLayout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CctvActivity.class)));
    }

    // 서버에서 센서 데이터를 받아오는 메서드
    private void receiveSensorValueFromServer() {
        // 비동기 작업 실행
        new SocketTask().execute();
    }

    // AsyncTask 클래스: 서버와의 통신을 처리
    private class SocketTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder receivedData = new StringBuilder();

            try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
                 PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"))) {

                // 서버에 데이터 요청
                String message = "REQUEST_SENSORDATA";
                out.println(message);
                out.flush();
                Log.d("SocketTest", "Sent: " + message);

                // 서버로부터 응답 수신
                String line;
                while ((line = in.readLine()) != null) {
                    Log.d("SocketTest", "Received: " + line);
                    receivedData.append(line).append("\n");

                    // UI 업데이트를 위한 데이터 처리
                    parseAndSetData(line);
                }

            } catch (Exception e) {
                Log.e("SocketTest", "Error occurred", e);
            }

            return receivedData.toString();
        }

        // 데이터를 파싱하고 UI에 업데이트하는 메서드
        private void parseAndSetData(String data) {
            if (data.startsWith("TEMP: ")) {
                String temp = data.substring("TEMP: ".length());
                uiHandler.post(() -> tempTextView.setText(temp));
            } else if (data.startsWith("REH: ")) {
                String reh = data.substring("REH: ".length());
                uiHandler.post(() -> rehTextView.setText(reh));
            } else if (data.startsWith("UV: ")) {
                String uv = data.substring("UV: ".length());
                uiHandler.post(() -> uvTextView.setText(uv));
            } else if (data.startsWith("MICRO: ")) {
                String micro = data.substring("MICRO: ".length());
                uiHandler.post(() -> microTextView.setText(micro));
            }
        }
    }
}

*/

// -------------------------------------------------------------------------------------------------

// 미세먼지 좋음 나쁨 보이게 고치기 전까지 되는 코드 !!!!!!!!!

/*ㄴㄴ

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String SERVER_IP = "192.168.0.180" +
            ""; // 서버 IP 주소
    private static final int SERVER_PORT = 12345; // 서버 포트 번호

    private Handler handler;
    private LinearLayout homeLayout, tipLayout, graphLayout, cctvLayout;
    private TextView tempTextView, rehTextView, uvTextView, microTextView;
    private Handler uiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI 요소 초기화
        handler = new Handler(Looper.getMainLooper());
        homeLayout = findViewById(R.id.home_btn);
        tipLayout = findViewById(R.id.tip_btn);
        graphLayout = findViewById(R.id.graph_btn);
        cctvLayout = findViewById(R.id.cctv_btn);
        tempTextView = findViewById(R.id.tempTextView);
        rehTextView = findViewById(R.id.rehTextView);
        uvTextView = findViewById(R.id.uvTextView);
        microTextView = findViewById(R.id.microTextView);

        uiHandler = new Handler(Looper.getMainLooper());

        // 버튼 클릭 리스너 설정
        setButtonListeners();

        // 서버에서 센서 값을 받아와 UI에 표시
        receiveSensorValueFromServer();
    }

    // 버튼 클릭 리스너 설정 메서드
    private void setButtonListeners() {
        homeLayout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, HomeActivity.class)));
        tipLayout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TipActivity.class)));
        graphLayout.setOnClickListener(v -> {
            Log.d(TAG, "Graph button clicked");
            startActivity(new Intent(MainActivity.this, GraphActivity.class));
        });
        cctvLayout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CctvActivity.class)));
    }

    // 서버에서 센서 데이터를 받아오는 메서드
    private void receiveSensorValueFromServer() {
        // 비동기 작업 실행
        new SocketTask().execute();
    }

    // AsyncTask 클래스: 서버와의 통신을 처리
    private class SocketTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder receivedData = new StringBuilder();

            try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
                 PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"))) {

                // 서버에 데이터 요청
                String message = "REQUEST_DATA";
                out.println(message);
                out.flush();
                Log.d("SocketTest", "Sent: " + message);

                // 서버로부터 응답 수신
                String line;
                while ((line = in.readLine()) != null) {
                    Log.d("SocketTest", "Received: " + line);
                    receivedData.append(line).append("\n");

                    // UI 업데이트를 위한 데이터 처리
                    parseAndSetData(line);
                }

            } catch (Exception e) {
                Log.e("SocketTest", "Error occurred", e);
            }

            return receivedData.toString();
        }

        // 데이터를 파싱하고 UI에 업데이트하는 메서드
        private void parseAndSetData(String data) {
            if (data.startsWith("TEMP: ")) {
                String temp = data.substring("TEMP: ".length());
                uiHandler.post(() -> tempTextView.setText(temp + " °C"));
            } else if (data.startsWith("REH: ")) {
                String reh = data.substring("REH: ".length());
                uiHandler.post(() -> rehTextView.setText(reh + " %"));
            } else if (data.startsWith("UV: ")) {
                String uv = data.substring("UV: ".length());
                uiHandler.post(() -> uvTextView.setText(uv));
            } else if (data.startsWith("MICRO: ")) {
                String micro = data.substring("MICRO: ".length());
                uiHandler.post(() -> microTextView.setText(micro + " ug/m3"));
            }
        }
    }
}

*/

// -------------------------------------------------------------------------------------------------

/*

// 되는 코드!!!! SocketActivity로 연결하기 전에 되는 코드!!!!

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String SERVER_IP = "192.168.0.180"; // 서버 IP 주소
    private static final int SERVER_PORT = 12345; // 서버 포트 번호

    private Handler handler;
    private LinearLayout homeLayout, tipLayout, graphLayout, cctvLayout;
    private TextView tempTextView, rehTextView, uvTextView, microTextView;
    private Handler uiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI 요소 초기화
        handler = new Handler(Looper.getMainLooper());
        homeLayout = findViewById(R.id.home_btn);
        tipLayout = findViewById(R.id.tip_btn);
        graphLayout = findViewById(R.id.graph_btn);
        cctvLayout = findViewById(R.id.cctv_btn);
        tempTextView = findViewById(R.id.tempTextView);
        rehTextView = findViewById(R.id.rehTextView);
        uvTextView = findViewById(R.id.uvTextView);
        microTextView = findViewById(R.id.microTextView); // 미세먼지 수치 또는 상태를 표시할 TextView

        uiHandler = new Handler(Looper.getMainLooper());

        // 버튼 클릭 리스너 설정
        setButtonListeners();

        // 서버에서 센서 값을 받아와 UI에 표시
        receiveSensorValueFromServer();
    }

    // 버튼 클릭 리스너 설정 메서드
    private void setButtonListeners() {
        homeLayout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, HomeActivity.class)));
        tipLayout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TipActivity.class)));
        graphLayout.setOnClickListener(v -> {
            Log.d(TAG, "Graph button clicked");
            startActivity(new Intent(MainActivity.this, GraphActivity.class));
        });
        cctvLayout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CctvActivity.class)));
    }

    // 서버에서 센서 데이터를 받아오는 메서드
    private void receiveSensorValueFromServer() {
        // 비동기 작업 실행
        new SocketTask().execute();
    }

    // AsyncTask 클래스: 서버와의 통신을 처리
    private class SocketTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder receivedData = new StringBuilder();

            try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
                 PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"))) {

                // 서버에 데이터 요청
                String message = "REQUEST_DATA";
                out.println(message);
                out.flush();
                Log.d("SocketTest", "Sent: " + message);

                // 서버로부터 응답 수신
                String line;
                while ((line = in.readLine()) != null) {
                    Log.d("SocketTest", "Received: " + line);
                    receivedData.append(line).append("\n");

                    // UI 업데이트를 위한 데이터 처리
                    parseAndSetData(line);
                }

            } catch (Exception e) {
                Log.e("SocketTest", "Error occurred", e);
            }

            return receivedData.toString();
        }

        // 데이터를 파싱하고 UI에 업데이트하는 메서드
        private void parseAndSetData(String data) {
            if (data.startsWith("TEMP: ")) {
                String temp = data.substring("TEMP: ".length());
                uiHandler.post(() -> tempTextView.setText(temp + " °C"));  // 온도 상태 업데이트
            } else if (data.startsWith("REH: ")) {
                String reh = data.substring("REH: ".length());
                uiHandler.post(() -> rehTextView.setText(reh + " %"));  // 습도 상태 업데이트
            } else if (data.startsWith("UV: ")) {
                String uv = data.substring("UV: ".length());
                uiHandler.post(() -> uvTextView.setText(uv));  // 자외선 상태 업데이트
            } else if (data.startsWith("MICRO: ")) {
                String micro = data.substring("MICRO: ".length());
                uiHandler.post(() -> {
                    updateMicroStatus(Integer.parseInt(micro)); // 미세먼지 상태 업데이트
                });
            }
        }
    }

    // 미세먼지 상태를 업데이트하는 메서드
    private void updateMicroStatus(int microValue) {
        String status;
        if (microValue <= 30) {
            status = "좋음";
        } else if (microValue <= 80) {
            status = "보통";
        } else if (microValue <= 150) {
            status = "나쁨";
        } else {
            status = "매우 나쁨";
        }
        microTextView.setText(status);  // 미세먼지 상태를 TextView에 설정
    }
}

 */

// -------------------------------------------------------------------------------------------------

// SocketActivity에 LogWeather 서버만 정보만 추가했을 때 동작되는 코드!!!!
// 원래 됐지만 ...이젠 안되는 코드....ㅆ

/*

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SocketActivity.OnDataReceivedListener {

    private static final String TAG = "MainActivity";
    private LinearLayout homeLayout, tipLayout, graphLayout, cctvLayout;
    private TextView tempTextView, rehTextView, uvTextView, microTextView;
    private SocketActivity socketActivity;
    private Handler uiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI 요소 초기화
        homeLayout = findViewById(R.id.home_btn);
        tipLayout = findViewById(R.id.tip_btn);
        graphLayout = findViewById(R.id.graph_btn);
        cctvLayout = findViewById(R.id.cctv_btn);
        tempTextView = findViewById(R.id.tempTextView);
        rehTextView = findViewById(R.id.rehTextView);
        uvTextView = findViewById(R.id.uvTextView);
        microTextView = findViewById(R.id.microTextView);

        uiHandler = new Handler(Looper.getMainLooper());

        // 버튼 클릭 리스너 설정
        setButtonListeners();

        // SocketActivity 인스턴스 가져오기 및 서버 연결 및 데이터 수신
        socketActivity = SocketActivity.getInstance();
        socketActivity.connectToServer(this);  // 서버 연결 및 데이터 수신 시작
        Log.d(TAG, "Attempting to connect to server...");
    }

    // 서버에서 데이터 수신 시 호출되는 메서드
    @Override
    public void onDataReceived(String data) {
        Log.d(TAG, "Data received: " + data);
        uiHandler.post(() -> parseAndSetData(data));
    }

    private void parseAndSetData(String data) {
        Log.d(TAG, "Parsing data: " + data); // 데이터 파싱 로그 추가
        if (data.startsWith("TEMP: ")) {
            String temp = data.substring("TEMP: ".length());
            tempTextView.setText(temp + " °C");
        } else if (data.startsWith("REH: ")) {
            String reh = data.substring("REH: ".length());
            rehTextView.setText(reh + " %");
        } else if (data.startsWith("UV: ")) {
            String uv = data.substring("UV: ".length());
            uvTextView.setText(uv);
        } else if (data.startsWith("MICRO: ")) {
            String micro = data.substring("MICRO: ".length());
            updateMicroStatus(Integer.parseInt(micro));
        }
    }

    // 미세먼지 상태를 업데이트하는 메서드
    private void updateMicroStatus(int microValue) {
        String status;
        if (microValue <= 30) {
            status = "좋음";
        } else if (microValue <= 80) {
            status = "보통";
        } else if (microValue <= 150) {
            status = "나쁨";
        } else {
            status = "매우 나쁨";
        }
        microTextView.setText(status);
    }

    // 버튼 클릭 리스너 설정 메서드
    private void setButtonListeners() {
        homeLayout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, HomeActivity.class)));
        tipLayout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TipActivity.class)));
        graphLayout.setOnClickListener(v -> {
            Log.d(TAG, "Graph button clicked");
            startActivity(new Intent(MainActivity.this, GraphActivity.class));
        });
        cctvLayout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CctvActivity.class)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // MainActivity 종료 시 서버 연결 해제
        if (socketActivity != null) {
            socketActivity.disconnect();
        }
    }
}

*/

// -------------------------------------------------------------------------------------------------

// 이상한 메세지가 뜨지만...? 동작은 되는 코드.. ㅋ (회원가입+로그인+메인화면)

/*

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SocketActivity.OnDataReceivedListener {

    private static final String TAG = "MainActivity";
    private LinearLayout homeLayout, tipLayout, graphLayout, cctvLayout;
    private TextView tempTextView, rehTextView, uvTextView, microTextView;
    private SocketActivity socketActivity;
    private Handler uiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI 요소 초기화
        homeLayout = findViewById(R.id.home_btn);
        tipLayout = findViewById(R.id.tip_btn);
        graphLayout = findViewById(R.id.graph_btn);
        cctvLayout = findViewById(R.id.cctv_btn);
        tempTextView = findViewById(R.id.tempTextView);
        rehTextView = findViewById(R.id.rehTextView);
        uvTextView = findViewById(R.id.uvTextView);
        microTextView = findViewById(R.id.microTextView);

        uiHandler = new Handler(Looper.getMainLooper());

        // 버튼 클릭 리스너 설정
        setButtonListeners();

        // SocketActivity 인스턴스 가져오기 및 서버 연결 및 데이터 수신
        socketActivity = SocketActivity.getInstance();
        socketActivity.connectToServer(this);  // 서버 연결 및 데이터 수신 시작
        Log.d(TAG, "Attempting to connect to server...");
    }

    // 서버에서 데이터 수신 시 호출되는 메서드
    @Override
    public void onDataReceived(String data) {
        Log.d(TAG, "Data received: " + data);
        uiHandler.post(() -> parseAndSetData(data));
    }

    private void parseAndSetData(String data) {
        Log.d(TAG, "Parsing data: " + data); // 데이터 파싱 로그 추가

        if (data.startsWith("TEMP:")) {  // 온도 데이터
            String temp = data.substring("TEMP:".length()).trim();
            tempTextView.setText(temp + " °C"); // 온도 설정
        } else if (data.startsWith("REH:")) {  // 습도 데이터
            String reh = data.substring("REH:".length()).trim();
            rehTextView.setText(reh + " %"); // 습도 설정
        } else if (data.startsWith("UV:")) {  // 자외선 데이터
            String uv = data.substring("UV:".length()).trim();
            uvTextView.setText(uv); // 자외선 설정
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

    // 버튼 클릭 리스너 설정 메서드
    private void setButtonListeners() {
        homeLayout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, HomeActivity.class)));
        tipLayout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TipActivity.class)));
        graphLayout.setOnClickListener(v -> {
            Log.d(TAG, "Graph button clicked");
            startActivity(new Intent(MainActivity.this, GraphActivity.class));
        });
        cctvLayout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CctvActivity.class)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // MainActivity 종료 시 서버 연결 해제
        if (socketActivity != null) {
            socketActivity.disconnect();
        }
    }
}

*/

// -------------------------------------------------------------------------------------------------

// 24.10.27 외부날씨 api까지 되는데 홈이 미세먼지인 코드 ( 강수확률로 바꾸기 전 코드 )

/*

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SocketActivity.OnDataReceivedListener {

    private static final String TAG = "MainActivity";
    private LinearLayout homeLayout, tipLayout, graphLayout, cctvLayout;
    private TextView tempTextView, rehTextView, uvTextView, microTextView;
    private SocketActivity socketActivity;
    private Handler uiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI 요소 초기화
        homeLayout = findViewById(R.id.home_btn);
        tipLayout = findViewById(R.id.tip_btn);
        graphLayout = findViewById(R.id.graph_btn);
        cctvLayout = findViewById(R.id.cctv_btn);
        tempTextView = findViewById(R.id.tempTextView);
        rehTextView = findViewById(R.id.rehTextView);
        uvTextView = findViewById(R.id.uvTextView);
        microTextView = findViewById(R.id.microTextView);

        uiHandler = new Handler(Looper.getMainLooper());

        // 버튼 클릭 리스너 설정
        setButtonListeners();

        // SocketActivity 인스턴스 가져오기 및 서버 연결 및 데이터 수신
        socketActivity = SocketActivity.getInstance();
        socketActivity.connectToServer(this);  // 서버 연결 및 데이터 수신 시작
        Log.d(TAG, "Attempting to connect to server...");
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
            tempTextView.setText(temp + " °C"); // 온도 설정
        } else if (data.startsWith("REH:")) {  // 습도 데이터
            String reh = data.substring("REH:".length()).trim();
            rehTextView.setText(reh + " %"); // 습도 설정
        } else if (data.startsWith("UV:")) {  // 자외선 데이터
            String uv = data.substring("UV:".length()).trim();
            uvTextView.setText(uv); // 자외선 설정
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

    // 버튼 클릭 리스너 설정 메서드
    private void setButtonListeners() {
        homeLayout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, HomeActivity.class)));
        tipLayout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TipActivity.class)));
        graphLayout.setOnClickListener(v -> {
            Log.d(TAG, "Graph button clicked");
            startActivity(new Intent(MainActivity.this, GraphActivity.class));
        });
        cctvLayout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CctvActivity.class)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // MainActivity 종료 시 서버 연결 해제
        if (socketActivity != null) {
            socketActivity.disconnect();
        }
    }
}

*/

// -------------------------------------------------------------------------------------------------

// main화면에 미세먼지가 아니라 강수확률로 바꾸고 돌아가는 코드
// [ 오늘의 날씨 ] 만들기 전에 돌아가던 코드

/*

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SocketActivity.OnDataReceivedListener {

    private static final String TAG = "MainActivity";
    private LinearLayout homeLayout, tipLayout, graphLayout, cctvLayout;
    private TextView tempTextView;
    private TextView rehTextView;
    private TextView uvTextView;
    private TextView popTextView;
    private SocketActivity socketActivity;
    private Handler uiHandler;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI 요소 초기화
        homeLayout = findViewById(R.id.home_btn);
        tipLayout = findViewById(R.id.tip_btn);
        graphLayout = findViewById(R.id.graph_btn);
        cctvLayout = findViewById(R.id.cctv_btn);
        tempTextView = findViewById(R.id.tempTextView);
        rehTextView = findViewById(R.id.rehTextView);
        uvTextView = findViewById(R.id.uvTextView);
        popTextView = findViewById(R.id.popTextView);

        uiHandler = new Handler(Looper.getMainLooper());

        // 버튼 클릭 리스너 설정
        setButtonListeners();

        // SocketActivity 인스턴스 가져오기 및 서버 연결 및 데이터 수신
        socketActivity = SocketActivity.getInstance();
        socketActivity.connectToServer(this);  // 서버 연결 및 데이터 수신 시작
        Log.d(TAG, "Attempting to connect to server...");
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

        runOnUiThread(() -> {
            if (data.startsWith("TEMP:")) {  // 온도 데이터
                String TEMP = data.substring("TEMP:".length()).trim();
                tempTextView.setText(TEMP + " °C"); // 온도 설정
            } else if (data.startsWith("REH:")) {  // 습도 데이터
                String reh = data.substring("REH:".length()).trim();
                rehTextView.setText(reh + " %"); // 습도 설정
            } else if (data.startsWith("UV:")) {  // 자외선 데이터
                String uv = data.substring("UV:".length()).trim();
                uvTextView.setText(uv); // 자외선 설정
            } else if (data.startsWith("POP:")) {  // 미세먼지 데이터
                String POP = data.substring("POP:".length()).trim();
                popTextView.setText(POP + " %"); // 자외선 설정
            }
        });
    }


    // 버튼 클릭 리스너 설정 메서드
    private void setButtonListeners() {
        homeLayout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, HomeActivity.class)));
        tipLayout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TipActivity.class)));
        graphLayout.setOnClickListener(v -> {
            Log.d(TAG, "Graph button clicked");
            startActivity(new Intent(MainActivity.this, GraphActivity.class));
        });
        cctvLayout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CctvActivity.class)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // MainActivity 종료 시 서버 연결 해제
        if (socketActivity != null) {
            socketActivity.disconnect();
        }
    }
}

 */

// -------------------------------------------------

// [ 오늘의 날씨 ] 기능 구현

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SocketActivity.OnDataReceivedListener {

    private static final String TAG = "MainActivity";
    private LinearLayout homeLayout, tipLayout, graphLayout, cctvLayout;
    private ImageView weatherSkyImageView, weatherPtyImageView;
    private TextView tempTextView, rehTextView, uvTextView, popTextView;
    private SocketActivity socketActivity;
    private Handler uiHandler;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // UI 요소 초기화
        homeLayout = findViewById(R.id.home_btn);
        tipLayout = findViewById(R.id.tip_btn);
        graphLayout = findViewById(R.id.graph_btn);
        cctvLayout = findViewById(R.id.cctv_btn);
        tempTextView = findViewById(R.id.tempTextView);
        rehTextView = findViewById(R.id.rehTextView);
        uvTextView = findViewById(R.id.uvTextView);
        popTextView = findViewById(R.id.popTextView);

        weatherSkyImageView = findViewById(R.id.weather_sky); // SKY 데이터 ImageView
        weatherPtyImageView = findViewById(R.id.weather_pty); // PTY 데이터 ImageView

        uiHandler = new Handler(Looper.getMainLooper());

        // 버튼 클릭 리스너 설정
        setButtonListeners();

        // SocketActivity 인스턴스 가져오기 및 서버 연결 및 데이터 수신
        socketActivity = SocketActivity.getInstance();
        socketActivity.connectToServer(this);  // 서버 연결 및 데이터 수신 시작
        Log.d(TAG, "Attempting to connect to server...");
    }

    // 서버에서 데이터 수신 시 호출되는 메서드
    @Override
    public void onDataReceived(String data) {
        Log.d(TAG, "Data received: " + data);

        uiHandler.post(() -> parseAndSetData(data));


    }

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
        Log.d(TAG, "Parsing data: " + data); // 데이터 파싱 로그 추가

        runOnUiThread(() -> {
            if (data.startsWith("TEMP:")) {  // 온도 데이터
                String TEMP = data.substring("TEMP:".length()).trim();
                tempTextView.setText(TEMP + " °C"); // 온도 설정
            } else if (data.startsWith("REH:")) {  // 습도 데이터
                String reh = data.substring("REH:".length()).trim();
                rehTextView.setText(reh + " %"); // 습도 설정
            } else if (data.startsWith("UV:")) {  // 자외선 데이터
                String uv = data.substring("UV:".length()).trim();
                uvTextView.setText(uv); // 자외선 설정
            } else if (data.startsWith("POP:")) {  // 미세먼지 데이터
                String POP = data.substring("POP:".length()).trim();
                popTextView.setText(POP + " %"); // 자외선 설정
            } else if (data.startsWith("SKY:")) {
                String sky = data.substring("SKY:".length()).trim();
                updateSkyImage(sky);
            } else if (data.startsWith("PTY:")) {
                String pty = data.substring("PTY:".length()).trim();
                updatePtyImage(pty);
            }
        });
    }

    private void updateSkyImage(String sky) {
        int imageResId = R.drawable.nope;
        if (sky != null) {
            switch (sky) {
                case "맑음":
                    imageResId = R.drawable.weather_sun;
                    break;
                case "구름많음":
                    imageResId = R.drawable.weather_cloudy;
                    break;
                case "흐림":
                    imageResId = R.drawable.weather_mcloudy;
                    break;
            }
        }
        weatherSkyImageView.setImageResource(imageResId);
    }

    private void updatePtyImage(String pty) {

        int imageResId = R.drawable.nope; // 기본값을 nope 이미지로 설정

        if (pty != null) {
            switch (pty) {
                case "비":
                case "소나기":
                    imageResId = R.drawable.weather_rain;
                    break;
                case "눈":
                case "진눈깨비":
                    imageResId = R.drawable.weather_snow;
                    break;
            }
        }
        weatherPtyImageView.setImageResource(imageResId);
    }

    // 버튼 클릭 리스너 설정 메서드
    private void setButtonListeners() {
        homeLayout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, HomeActivity.class)));
        tipLayout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TipActivity.class)));
        graphLayout.setOnClickListener(v -> {
            Log.d(TAG, "Graph button clicked");
            startActivity(new Intent(MainActivity.this, GraphActivity.class));
        });
        cctvLayout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CctvActivity.class)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // MainActivity 종료 시 서버 연결 해제
        if (socketActivity != null) {
            socketActivity.disconnect();
        }
    }
}
