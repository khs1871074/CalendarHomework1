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

    public static int year = 0; //년월 정보가 액티비티 실행마다 초기화되지않게 전역변수로 선언
    public static int month = 0;
    //public static int day = 0; //사용하지 않는 코드 주석처리하였음

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar c = Calendar.getInstance();  //Calendar 클래스의 객체 생성

        //이전 액티비티로부터 인텐트 받아오는 코드
        //참조코드 https://kwanulee.github.io/AndroidProgramming/activity-intent/activity-communication.html
        // https://kwanulee.github.io/AndroidProgramming/activity-intent/exercise5.html
        Intent gotintent = getIntent();
        if(gotintent.getIntExtra("nowmonth",0)==0) { //받아온 값이 없다면 디폴트값 0을 반환
            month = c.get(Calendar.MONTH) + 1;
            //디폴드값 0이 반환되면 현재 월의 정보를 Calendar 클래스로부터 받아옴
        }
        else {
            month = gotintent.getIntExtra("nowmonth", 0);
            //받아온 값이 있다면 인텐트값을 적용
        }

        if(gotintent.getIntExtra("nowyear",-1)==-1) {
            year = c.get(Calendar.YEAR);
        }
        else if (gotintent.getIntExtra("nowyear",-1)==0){
            //받아온 해당 년의 값이 0이 된다면 0년 12월이 아닌 1년 1월로 초기화
            year = 1;
            month = 1;
            Toast.makeText(getApplicationContext(), "마지막 페이지입니다",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            year = gotintent.getIntExtra("nowyear", 0);
        }


        // 달력 표시를 위한 배열 정의와 날짜 계산
        ArrayList<String> items= new ArrayList<String>(); //.add() 메소드를 사용하기 위해 ArrayList를 사용하였음

        String cday = "";
        int start_day = getDays(year, month) % 7; //사용자정의 메소드 getDays()에서 총 일수를 반환받은 후
                                            // 7로 나눈 나머지를 이용하여 해당 달의 시작 요일을 구하는 코드

        //구한 start_day가 0(일요일)이 아닐 경우 start_day-1만큼 ArrayList에 공백을 추가하는 코드
        if(start_day!=0) {
            for (int i = 0; i < start_day; i++) {
                cday = "";
                items.add(cday);
            }
        }
        //2월은 28,29일, 4,6,9,11월은 30일, 그 외에는 31일인것을 이용하여 해당 월의 날짜값들을 ArrayList에 추가하는 코드
        //코드는 getDays()메소드와 같이 1871074김혁순이 과거 Java프로그래밍 과목에서 프로젝트 과제로 제출했던 코드를 일부 변형하여 재사용하였음
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
        //참조코드 https://kwanulee.github.io/AndroidProgramming/adapter-view/gridview.html
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
        //String d = Integer.toString(day); //사용하지 않는 코드 주석처리하였음

        //참조코드 https://kwanulee.github.io/AndroidProgramming/activity-intent/exercise5.html
        TextView mon = (TextView) findViewById(R.id.titlenowmonth);
        mon.setText(y+"년 "+m+"월");

        // 버튼 클릭 이벤트(다음버튼)
        //참조코드 https://kwanulee.github.io/AndroidProgramming/activity-intent/exercise5.html
        //https://kwanulee.github.io/AndroidProgramming/activity-intent/exercise2.html
        Button btnAfter = findViewById(R.id.after);
        btnAfter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                month += 1; //버튼 클릭시 해당 월에서 1 증가
                if(month==13) { //만약 12월에서 다음 버튼을 클릭한다면 월을 1로 초기화하고 년도 값을 1 증가
                    month = 1;
                    year += 1;
                }
                Toast.makeText(getApplicationContext(),
                        Integer.toString(year)+"년 "+Integer.toString(month)+"월",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MonthViewActivity.class); //MonthViewActivity인텐트 선언
                intent.putExtra("nowmonth", month); //증가한 month 값 인텐트로 전달
                intent.putExtra("nowyear", year); //해당 year 혹은 증가한 year 값 인텐트로 전달
                startActivity(intent); //새로운 MonthViewActivity 실행

                finish(); //이전 MonthViewActivity 종료
                //onDestroy()는 액티비티 종료후 작동이 중지되는 버그가 발생해 finish()를 사용하였음
            }
        });

        // 버튼 클릭 이벤트(이전버튼)
        Button btnBefore = findViewById(R.id.before);
        btnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                month -= 1; //버튼 클릭시 해당 월에서 1 감소
                if(month==0) { //만약 1월에서 이전 버튼을 클릭한다면 월을 12로 초기화하고 년도 값을 1 감소
                    month = 12;
                    year -= 1;
                }
                Toast.makeText(getApplicationContext(),
                        Integer.toString(year)+"년 "+Integer.toString(month)+"월",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MonthViewActivity.class);
                intent.putExtra("nowmonth", month); //감소한 month 값 인텐트로 전달
                intent.putExtra("nowyear", year); //해당 year 혹은 감소소 year 값 인텐트로 전달
                startActivity(intent); //새로운 MonthViewActivity 실행

                finish(); //이전 MonthViewActivity 종료
            }
        });

        // 그리드뷰 클릭 이벤트(달력 그리드뷰)
        //참조코드 https://kwanulee.github.io/AndroidProgramming/adapter-view/gridview.html
        cal.setOnItemClickListener(new GridView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if(position-start_day<0){ //클릭한 포지션의 위치가 공백일 경우 아무것도 출력하지않음
                }
                else { //클릭한 포지션의 위치가 공백이 아닐 경우 해당 날짜를 계산하여 토스트메시지 출력
                    Toast.makeText(MonthViewActivity.this,
                            y + "." + m + "." + (position - start_day + 1),
                            Toast.LENGTH_SHORT).show();
                    //일 수는 1일부터 시작이지만 position값은 0부터 시작이므로 1을 더해줌
                }
            }
        });
    }

    // 현재 달의 시작 날짜를 계산하기 위해 총 날짜를 알아내는 함수 정의
    //코드는 1871074김혁순이 과거 Java프로그래밍 과목에서 프로젝트 과제로 제출했던 코드를 재사용하였음
    public static int getDays(int year, int month) { // 총 날짜수를 구하는 메소드
        int total = 0;

        total += (year - 1) * 365;
        total += (int)(year / 4) - (int)(year / 100) + (int)(year / 400); //윤년 계산
        if (year % 400 == 0) {
            if (month < 3)
                total -= 1;
        } else if (year % 4 == 0 && year % 100 != 0) {
            if (month < 3)
                total -= 1;
        }

        switch (month) {  //그 해의 1월 1일부터 그 달의 이전 달의 마지막날까지를 구하는 코드
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

        return total + 1; // 그 달의 1일까지의 총 일수를 반환
    }
}

