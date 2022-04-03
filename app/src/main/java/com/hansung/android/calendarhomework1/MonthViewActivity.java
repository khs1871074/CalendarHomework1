package com.hansung.android.calendarhomework1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
    //public static int day = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar c = Calendar.getInstance();

        //이전 액티비티로부터 인텐트 받아오는 코드
        Intent gotintent = getIntent();
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


        // 달력 표시를 위한 배열 정의와 날짜 계산
        ArrayList<String> items= new ArrayList<String>();

        String cday = "";
        int start_day = getDays(year, month) % 7;

        if(start_day!=0) {
            for (int i = 0; i < start_day; i++) {
                cday = "";
                items.add(cday);
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


        // 현재 출력 중인 년, 월 정보를 TextView에 전달
        String y = Integer.toString(year);
        String m = Integer.toString(month);
        //String d = Integer.toString(day);

        TextView mon = (TextView) findViewById(R.id.titlenowmonth);
        mon.setText(y+"년 "+m+"월");

        // 버튼 클릭 이벤트(다음버튼)
        Button btnAfter = findViewById(R.id.after);
        btnAfter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                month += 1;
                if(month==13) {
                    month = 1;
                    year += 1;
                }
                Toast.makeText(getApplicationContext(),
                        Integer.toString(year)+"년 "+Integer.toString(month)+"월",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MonthViewActivity.class);
                intent.putExtra("nowmonth", month);
                intent.putExtra("nowyear", year);
                startActivity(intent);

                finish();
            }
        });

        // 버튼 클릭 이벤트(이전버튼)
        Button btnBefore = findViewById(R.id.before);
        btnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                month -= 1;
                if(month==0) {
                    month = 12;
                    year -= 1;
                }
                Toast.makeText(getApplicationContext(),
                        Integer.toString(year)+"년 "+Integer.toString(month)+"월",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MonthViewActivity.class);
                intent.putExtra("nowmonth", month);
                intent.putExtra("nowyear", year);
                startActivity(intent);

                finish();
            }
        });

        // 그리드뷰 클릭 이벤트(달력 그리드뷰)
        cal.setOnItemClickListener(new GridView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if(position-start_day<0){
                }
                else {
                    Toast.makeText(MonthViewActivity.this,
                            y + "." + m + "." + (position - start_day + 1),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // 현재 달의 시작 날짜를 계산하기 위해 총 날짜를 알아내는 함수 정의
    public static int getDays(int year, int month) { // 총 날짜수를 구하는 메소드
        int total = 0;

        total += (year - 1) * 365;
        total += (int)(year / 4) - (int)(year / 100) + (int)(year / 400);
        if (year % 400 == 0) {
            if (month < 3)
                total -= 1;
        } else if (year % 4 == 0 && year % 100 != 0) {
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

