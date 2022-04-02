package com.hansung.android.calendarhomework1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthViewActivity extends AppCompatActivity {

    public static int year = 0;
    public static int month = 0;
    public static int day = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar c = Calendar.getInstance();

        Intent gotintent = getIntent();
        ///////
        if(gotintent.getIntExtra("nowmonth",0)==0) {
            month = c.get(Calendar.MONTH) + 1;
        }
        else {
            month = gotintent.getIntExtra("nowmonth", 0);
        }

        if(gotintent.getIntExtra("nowyear",-1)==-1) {
            year = c.get(Calendar.YEAR);
        }
        else if (gotintent.getIntExtra("nowyear",-1)==0){
            year = 1;
            month = 1;
            Toast.makeText(getApplicationContext(), "마지막 페이지입니다",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            year = gotintent.getIntExtra("nowyear", 0);
        }
        /////////
        String y = Integer.toString(year);
        String m = Integer.toString(month);
        String d = Integer.toString(day);
        String cday = "";
        // 데이터 원본 준비
        ArrayList<String> items= new ArrayList<String>();

        int start_day = getDays(year, month) % 7;

        if(start_day!=0) {
            for (int i = 0; i < start_day; i++) {
                items.add("");
            }
        }
        if (month == 2) {
            if (year % 400 == 0) {
                for (int i = 1; i <= 29; i++) {
                    cday = Integer.toString(i);
                    items.add(cday);
                }
            } else if (year % 4 == 0 && year % 100 != 0) {
                for (int i = 1; i <= 29; i++) {
                    cday = Integer.toString(i);
                    items.add(cday);
                }
            } else {
                for (int i = 1; i <= 28; i++) {
                    cday = Integer.toString(i);
                    items.add(cday);
                }
            }
        }
        else if (month == 4 || month == 6 || month == 9 || month == 11) {
            for (int i = 1; i <= 30; i++) {
                cday = Integer.toString(i);
                items.add(cday);
            }

        } else {
            for (int i = 1; i <= 31; i++) {
                cday = Integer.toString(i);
                items.add(cday);
            }
        }

        //어댑터 준비 (배열 객체 이용, simple_list_item_1 리소스 사용
        ArrayAdapter<String> adapt
                = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1,items);

        // id를 바탕으로 화면 레이아웃에 정의된 GridView 객체 로딩
        GridView cal = (GridView) findViewById(R.id.calendarGridView);
        // 어댑터를 GridView 객체에 연결
        cal.setAdapter(adapt);

        TextView mon = (TextView) findViewById(R.id.nowmonth);
        mon.setText(y+"년"+" "+m+"월");

        Button btnAfter = findViewById(R.id.after);
        btnAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                month += 1;
                if(month==13) {
                    month = 1;
                    year += 1;
                }
                Intent intent = new Intent(getApplicationContext(), MonthViewActivity.class);
                intent.putExtra("nowmonth", month);
                intent.putExtra("nowyear", year);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "After Submitted Successfully",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        Button btnBefore = findViewById(R.id.before);
        btnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                month -= 1;
                if(month==0) {
                    month = 12;
                    year -= 1;
                }
                Intent intent = new Intent(getApplicationContext(), MonthViewActivity.class);
                intent.putExtra("nowmonth", month);
                intent.putExtra("nowyear", year);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Before Submitted Successfully",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public static int getDays(int year, int month) { // 총 날짜수를 구하는 메소드
        int total = 0;

        total += (year - 1) * 365;
        total += (year / 4) - (year / 100) + (year / 400);
        if (year % 400 == 0) {
            if (month < 3)
                total -= 1;
        } else if (year % 4 == 0 || year % 100 != 0) {
            if (month < 3)
                total -= 1;
        }

        switch (month) {
            case 1:
                break;
            case 2:
                total += 31;
                break;
            case 3:
                total += 31 + 28;
                break;
            case 4:
                total += 31 + 28 + 31;
                break;
            case 5:
                total += 31 + 28 + 31 + 30;
                break;
            case 6:
                total += 31 + 28 + 31 + 30 + 31;
                break;
            case 7:
                total += 31 + 28 + 31 + 30 + 31 + 30;
                break;
            case 8:
                total += 31 + 28 + 31 + 30 + 31 + 30 + 31;
                break;
            case 9:
                total += 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31;
                break;
            case 10:
                total += 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30;
                break;
            case 11:
                total += 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31;
                break;
            case 12:
                total += 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31 + 30;
                break;
        }

        return total + 1;
    }
}

