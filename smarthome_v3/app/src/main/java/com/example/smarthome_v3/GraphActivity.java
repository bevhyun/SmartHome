package com.example.smarthome_v3;


/*

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class GraphActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
    }
}

*/

// -------------------------------------------------------------------------------------------------

/*

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {

    private LineChart temChart;
    private LineChart humChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv);  // XML 파일 이름이 activity_cctv 인지 확인하세요

        temChart = findViewById(R.id.tem_chart);
        humChart = findViewById(R.id.hum_chart);

        // 온도 데이터 설정
        ArrayList<Entry> temEntries = new ArrayList<>();
        temEntries.add(new Entry(0, 20));
        temEntries.add(new Entry(1, 24));
        temEntries.add(new Entry(2, 22));
        temEntries.add(new Entry(3, 23));

        LineDataSet temDataSet = new LineDataSet(temEntries, "Temperature");
        LineData temData = new LineData(temDataSet);
        temChart.setData(temData);
        temChart.invalidate();

        // 습도 데이터 설정
        ArrayList<Entry> humEntries = new ArrayList<>();
        humEntries.add(new Entry(0, 60));
        humEntries.add(new Entry(1, 65));
        humEntries.add(new Entry(2, 70));
        humEntries.add(new Entry(3, 68));

        LineDataSet humDataSet = new LineDataSet(humEntries, "Humidity");
        LineData humData = new LineData(humDataSet);
        humChart.setData(humData);
        humChart.invalidate();
    }
}

 */

// -------------------------------------------------------------------------------------------------

// 되는 코드!!!! SocketActivity로 연결하기 전에 되는 코드!!!!

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {

    private LineChart temChart;
    private LineChart humChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);  // XML 파일 이름이 activity_cctv 인지 확인하세요

        temChart = findViewById(R.id.tem_chart);
        humChart = findViewById(R.id.hum_chart);

        // 온도 데이터 설정
        ArrayList<Entry> temEntries = new ArrayList<>();
        temEntries.add(new Entry(0, 36));
        temEntries.add(new Entry(1, 36));
        temEntries.add(new Entry(2, 35));
        temEntries.add(new Entry(3, 34));
        temEntries.add(new Entry(4, 34));
        temEntries.add(new Entry(5, 32));
        temEntries.add(new Entry(6, 36));

        LineDataSet temDataSet = new LineDataSet(temEntries, "Temperature");
        temDataSet.setColor(Color.RED); // 선 색상 설정
        temDataSet.setValueTextColor(Color.BLACK); // 값 색상 설정
        temDataSet.setDrawFilled(true); // 배경 채우기
        temDataSet.setFillColor(Color.RED); // 채우기 색상
        LineData temData = new LineData(temDataSet);
        temChart.setData(temData);
        temChart.invalidate();

        // 습도 데이터 설정
        ArrayList<Entry> humEntries = new ArrayList<>();
        humEntries.add(new Entry(0, 63));
        humEntries.add(new Entry(1, 58));
        humEntries.add(new Entry(2, 59));
        humEntries.add(new Entry(3, 64));
        humEntries.add(new Entry(4, 68));
        humEntries.add(new Entry(5, 65));
        humEntries.add(new Entry(5, 63));
        humEntries.add(new Entry(5, 71));

        LineDataSet humDataSet = new LineDataSet(humEntries, "Humidity");
        humDataSet.setColor(Color.BLUE); // 선 색상 설정
        humDataSet.setValueTextColor(Color.BLACK); // 값 색상 설정
        humDataSet.setDrawFilled(true); // 배경 채우기
        humDataSet.setFillColor(Color.BLUE); // 채우기 색상
        LineData humData = new LineData(humDataSet);
        humChart.setData(humData);
        humChart.invalidate();

        // 차트 설정
        configureChart(temChart);
        configureChart(humChart);
    }

    private void configureChart(LineChart chart) {
        chart.getDescription().setEnabled(false); // 설명 텍스트 비활성화
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM); // X축 위치 설정
        chart.getAxisLeft().setAxisMinimum(0f); // Y축의 최소값 설정
        chart.getAxisRight().setEnabled(false); // 오른쪽 Y축 비활성화
        chart.setDrawGridBackground(false); // 배경 그리드 비활성화
    }
}