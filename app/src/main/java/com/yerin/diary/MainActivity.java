package com.yerin.diary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity{
    private TextView firstMessage;
    private TextView diaryYear, diaryMonth, diaryDay;
    private LinearLayout diarySetDate;
    private FloatingActionButton btnAdd;
    private Button btnSetting, btnChart, btnAsc, btnDesc;
    private com.yerin.diary.DatePickerDialog datePickerDialog;

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
        DBHelper = new DbHelper(getApplicationContext(), "diary", null, 1);

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

        dList = DBHelper.dGetDiaryList(diaryYear.getText().toString(), diaryMonth.getText().toString());

        // 데이터가 존재하지 않을 경우 first message 출력
        if (dList.size() != 0) {
            firstMessage.setVisibility(View.GONE);
        } else if (dList.size() == 0) {
            firstMessage.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }

        // 리사이클러뷰 탑재
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        DBHelper = new DbHelper(getApplicationContext(), "diary", null, 1);
        db = DBHelper.getWritableDatabase();

        dAdapter = new DiaryAdapter(dList);
        recyclerView.setAdapter(dAdapter);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.scrollToPosition(0);
            }
        }, 200);

        // 요소별 동작 메소드
        diarySetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();

                int currentYear = calendar.get(Calendar.YEAR);
                int currentMonth = calendar.get(Calendar.MONTH);
                int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

                createDialogWithoutDateField().show();

//                DatePickerDialog.OnDateSetListener dDateSetListener =
//                        new DatePickerDialog.OnDateSetListener() {
//                            public void onDateSet(DatePicker view, int year, int monthOfYear, final int dayOfMonth) {
//                                diaryYear.setText((year) + "");
//                                if ((monthOfYear + 1) < 10)
//                                    diaryMonth.setText("0" + (monthOfYear + 1) + "");
//                                else diaryMonth.setText((dayOfMonth + 1) + "");
//
//                                dList = DBHelper.dGetDiaryList(diaryYear.getText().toString(), diaryMonth.getText().toString());
//
//                                dAdapter = new DiaryAdapter(dList);
//                                recyclerView.setAdapter(dAdapter);
//
////                                new Handler().postDelayed(new Runnable() {
////                                    @Override
////                                    public void run() {
////                                        recyclerView.scrollToPosition();
////                                    }
////                                }, 200);
//                            }
//                        };
//                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, dDateSetListener, currentYear, currentMonth, currentDay);
//                dialog.show();

//                datePickerDialog = (DatePicker) findViewById(R.id.datePicker);
//                try {
//                    DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, onDateSetListener, currentYear, currentMonth, currentDay);
//
//                    Field f[] = datePickerDialog.getClass().getDeclaredFields();
//                    for (Field field : f) {
//                        if (field.getName().equals("mDaySpinner") || field.getName().equals("mDayPicker")) {
//                            field.setAccessible(true);
//                            Object dayPicker = new Object();
//                            dayPicker = field.get(datePickerDialog);
//                            ((View) dayPicker).setVisibility(View.GONE);
//                        }
//                    }
//                } catch (SecurityException e) {
//                    Log.d("ERROR", e.getMessage());
//                } catch (IllegalArgumentException e) {
//                    Log.d("ERROR", e.getMessage());
//                } catch (IllegalAccessException e) {
//                    Log.d("ERROR", e.getMessage());
//                }

//                datePickerDialog = new DatePickerDialog();
//                datePickerDialog.setListener(this);
//                datePickerDialog.show(getFragmentManager(), "datePickerDialog");

            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
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
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
            }
        });
    }

//    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
//        @Override
//        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//
//        }
//    };

//    @Override
//    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//        diaryYear.setText((year) + "");
//        if ((month + 1) < 10)
//            diaryMonth.setText("0" + (month + 1) + "");
//        else diaryMonth.setText((dayOfMonth + 1) + "");
//    }

    private DatePickerDialog createDialogWithoutDateField() {
        calendar = Calendar.getInstance();

        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this, null, currentYear, currentMonth, currentDay);
        try {
            java.lang.reflect.Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
            for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker") || datePickerDialogField.getName().equals("mDateSpinner")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker = (DatePicker) datePickerDialogField.get(dpd);
                    java.lang.reflect.Field[] datePickerFields = datePickerDialogField.getType().getDeclaredFields();
                    for (java.lang.reflect.Field datePickerField : datePickerFields) {
                        Log.i("test", datePickerField.getName());
                        if ("mDaySpinner".equals(datePickerField.getName())) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = datePickerField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
        }
        return dpd;
    }
}
