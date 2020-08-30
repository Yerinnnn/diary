package com.yerin.diary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {
    private Button btnBack, btnSave;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

//        btnBack = findViewById(R.id.btnBack);
//
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentHome = new Intent(SettingActivity.this, MainActivity.class);
//                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intentHome.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                startActivity(intentHome);
//                finish();
//            }
//        });
    }
}
