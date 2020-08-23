package com.yerin.diary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;

public class EmojiActivity extends Activity {
    private ImageView bigEmoji;
    private RadioGroup radioGroup1;
    private RadioGroup radioGroup2;
    private Button btnSave;

    private boolean isChecking = true;
    private int rCheckedId = R.id.btn_smile;

    private int dEmoji;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emoji);

        bigEmoji = findViewById(R.id.bigEmoji);

        radioGroup1 = findViewById(R.id.radioGroup1);
        radioGroup2 = findViewById(R.id.radioGroup2);

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    radioGroup2.clearCheck();
                    rCheckedId = checkedId;
                }
                isChecking = true;
            }
        });

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    radioGroup1.clearCheck();
                    rCheckedId = checkedId;
                }
                isChecking = true;
            }
        });

        if (rCheckedId == R.id.btn_smile) {
            dEmoji = 1;
//            bigEmoji.setImageDrawable();
        } else if (rCheckedId == R.id.btn_soso) {
            dEmoji = 2;
//            bigEmoji.setImageDrawable();
        } else if (rCheckedId == R.id.btn_angry) {
            dEmoji = 3;
//            bigEmoji.setImageDrawable();
        } else if (rCheckedId == R.id.btn_happy) {
            dEmoji = 4;
//            bigEmoji.setImageDrawable();
        } else if (rCheckedId == R.id.btn_relax) {
            dEmoji = 5;
//            bigEmoji.setImageDrawable();
        } else if (rCheckedId == R.id.btn_sad) {
            dEmoji = 6;
//            bigEmoji.setImageDrawable();
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContentActivity.class);

                intent.putExtra("dEmoji", dEmoji);

                startActivity(intent);
            }
        });
    }
}
