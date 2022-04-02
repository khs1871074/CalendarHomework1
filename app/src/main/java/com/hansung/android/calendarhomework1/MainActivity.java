package com.hansung.android.calendarhomework1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 데이터 원본 준비
        String[] items = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

        //어댑터 준비 (배열 객체 이용, simple_list_item_1 리소스 사용
        ArrayAdapter<String> adapt
                = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                items);

        // id를 바탕으로 화면 레이아웃에 정의된 GridView 객체 로딩
        GridView calendar = (GridView) findViewById(R.id.calendar);
        // 어댑터를 GridView 객체에 연결
        calendar.setAdapter(adapt);

    }
}