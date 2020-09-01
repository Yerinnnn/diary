package com.yerin.diary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private TextView firstMessage;
    private TextView diaryYear, diaryMonth, diaryDay;
    private LinearLayout diarySetDate;
    private FloatingActionButton btnAdd;
    private Button btnSetting, btnChart, btnAsc, btnDesc;
    private YearMonthDatePickerDialog yearMonthDatePickerDialog;

    private DiaryAdapter dAdapter;
    private ArrayList<Diary> dList;

    private RecyclerView recyclerView;

    private Calendar calendar;

    private String currentYear, currentMonth, currentDay, currentDate;
    private int dayOfWeek;

    private DbHelper DBHelper;
    private SQLiteDatabase db;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHelper = new DbHelper(MainActivity.this, "diary", null, 1);

        // 요소 연결
        firstMessage = findViewById(R.id.firstMessage);
        diarySetDate = findViewById(R.id.diarySetDate);
        diaryYear = findViewById(R.id.diaryYear);
        diaryMonth = findViewById(R.id.diaryMonth);
        btnAdd = findViewById(R.id.btnAdd);
        btnSetting = findViewById(R.id.btnSetting);
//        btnChart = findViewById(R.id.btnChart);
        btnAsc = findViewById(R.id.btnAsc);
        btnDesc = findViewById(R.id.btnDesc);
        recyclerView = findViewById(R.id.diaryRecyclerview);

        // 현재 시간 저장
        calendar = Calendar.getInstance();

        currentYear = Integer.toString(calendar.get(Calendar.YEAR));
        currentMonth = Integer.toString(calendar.get(Calendar.MONTH) + 1);
        currentDay = Integer.toString(calendar.get(Calendar.DATE));
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        switch (dayOfWeek) {
            case 1:
                currentDate = "일";
                break;
            case 2:
                currentDate = "월";
                break;
            case 3:
                currentDate = "화";
                break;
            case 4:
                currentDate = "수";
                break;
            case 5:
                currentDate = "목";
                break;
            case 6:
                currentDate = "금";
                break;
            case 7:
                currentDate = "토";
                break;
        }

        diaryYear.setText(currentYear);
        diaryMonth.setText(currentMonth);
        if (Integer.parseInt(currentMonth) < 10) diaryMonth.setText("0" + currentMonth);
        else diaryMonth.setText(currentMonth);

        dList = DBHelper.dGetDiaryListAll();

        // 데이터가 존재하지 않을 경우 first message 출력
        if (dList.size() != 0) {
            firstMessage.setVisibility(View.GONE);
        } else if (dList.size() == 0) {
            firstMessage.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }

        // 리사이클러뷰 탑재
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setNestedScrollingEnabled(false);

        dAdapter = new DiaryAdapter(dList);
        dAdapter.setdContext(MainActivity.this);
        recyclerView.setAdapter(dAdapter);
//        recyclerView.scrollToPosition();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.scrollToPosition(0);
            }
        }, 200);

        // 요소별 동작 메소드
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

//        btnChart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        btnAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, true));
            }
        });

        btnDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
                ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(0, 0);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        recyclerView.scrollToPosition(dAdapter.getItemCount());
//                    }
//                }, 200);
            }
        });
    }

    public void diarySetDateListener(View v) {
        YearMonthDatePickerDialog yearMonthDatePickerDialog = new YearMonthDatePickerDialog();
        yearMonthDatePickerDialog.setListener(datePickerListener);
        yearMonthDatePickerDialog.show(getSupportFragmentManager(), "date picker");
    }

    android.app.DatePickerDialog.OnDateSetListener datePickerListener = new android.app.DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Log.d("YearMonthPickerTest", "year = " + year + ", month = " + monthOfYear + ", day = " + dayOfMonth);

            diaryYear.setText((year) + "");

            if ((monthOfYear) < 10)
                diaryMonth.setText("0" + (monthOfYear));
            else diaryMonth.setText((dayOfMonth) + "");

            dList = DBHelper.dGetDiaryList(diaryYear.getText().toString(), diaryMonth.getText().toString());

            dAdapter = new DiaryAdapter(dList);
            recyclerView.setAdapter(dAdapter);
        }
    };

}
