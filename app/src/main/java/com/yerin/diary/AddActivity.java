package com.yerin.diary;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class AddActivity extends Activity {
    private LinearLayout diarySetDate;
    private TextView diaryYear, diaryMonth, diaryDay, diaryDate;
    private RadioGroup emotionRadioGroup1, emotionRadioGroup2;
    private RadioButton btnSmile, btnSoso, btnAngry, btnHappy, btnRelax, btnSad;
    private ImageView diaryEmoji, diaryPhoto;
    private EditText diaryContent;
    private Button btnBack, btnSave;
    private DiaryAdapter diaryAdapter;

    private int currentYear;
    private int currentMonth;
    private int currentDay;

    private Calendar calendar;

    private boolean isEmotionChecking = true;
    private int emotionCheckedId;

    private static final int REQUEST_CODE = 0;
    private Uri uri;

//    private Diary diary;

    private String dYear, dMonth, dDay, dDate, dEmotion, dContent, dPhoto;
    private int dEmoji;

    private DbHelper DBHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        DBHelper = new DbHelper(getApplicationContext(), "diary", null, 1);

        // findViewById
        diarySetDate = findViewById(R.id.diarySetDate);
        diaryYear = findViewById(R.id.diaryYear);
        diaryMonth = findViewById(R.id.diaryMonth);
        diaryDay = findViewById(R.id.diaryDay);
        diaryDate = findViewById(R.id.diaryDate);
        diaryEmoji = findViewById(R.id.diaryEmoji);
        diaryContent = findViewById(R.id.diaryContent);
        diaryPhoto = findViewById(R.id.diaryPhoto);

        emotionRadioGroup1 = findViewById(R.id.emotionRadioGroup1);
        emotionRadioGroup2 = findViewById(R.id.emotionRadioGroup2);

        btnSmile = findViewById(R.id.btn_smile);
        btnSoso = findViewById(R.id.btn_soso);
        btnAngry = findViewById(R.id.btn_angry);
        btnHappy = findViewById(R.id.btn_happy);
        btnRelax = findViewById(R.id.btn_relax);
        btnSad = findViewById(R.id.btn_sad);

        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);

        // 현재 날짜 구하기
        calendar = Calendar.getInstance();

        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        diaryYear.setText((currentYear) + "");
        if ((currentMonth + 1) < 10) diaryMonth.setText("0" + (currentMonth + 1) + "");
        else diaryMonth.setText((currentMonth + 1) + "");
        if (currentDay < 10) diaryDay.setText("0" + (currentDay) + "");
        else diaryDay.setText((currentDay) + "");

        // 위의 날짜에 해당하는 요일
        String day = diaryYear.getText().toString() + diaryMonth.getText().toString() + diaryDay.getText().toString();
        String[] week = {"일", "월", "화", "수", "목", "금", "토"};
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        Calendar cal = Calendar.getInstance();
        Date getDate;
        try {
            getDate = dateFormat.parse(day);
            cal.setTime(getDate);
            int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
            diaryDate.setText(week[w] + "");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        diarySetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();

                int currentYear = calendar.get(Calendar.YEAR);
                int currentMonth = calendar.get(Calendar.MONTH);
                int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog.OnDateSetListener dDateSetListener =
                        new DatePickerDialog.OnDateSetListener() {
                            // onDateSet method
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                diaryYear.setText((year) + "");
                                if (monthOfYear < 10) {
                                    diaryMonth.setText("0" + (monthOfYear + 1) + "");
                                } else {
                                    diaryMonth.setText((monthOfYear + 1) + "");
                                }
                                if (dayOfMonth < 10) {
                                    diaryDay.setText("0" + (dayOfMonth) + "");
                                } else {
                                    diaryDay.setText((dayOfMonth) + "");
                                }

                                // 위의 날짜에 해당하는 요일
                                String day = diaryYear.getText().toString() + diaryMonth.getText().toString() + diaryDay.getText().toString();
                                String[] week = {"일", "월", "화", "수", "목", "금", "토"};
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

                                Calendar cal = Calendar.getInstance();
                                Date getDate;
                                try {
                                    getDate = dateFormat.parse(day);
                                    cal.setTime(getDate);
                                    int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
                                    diaryDate.setText(week[w] + "");
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        };
                DatePickerDialog dialog = new DatePickerDialog(AddActivity.this, dDateSetListener, currentYear, currentMonth, currentDay);
                dialog.show();
            }
        });


        emotionRadioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isEmotionChecking) {
                    isEmotionChecking = false;
                    emotionRadioGroup2.clearCheck();
                    emotionCheckedId = checkedId;

//                    if (emotionCheckedId == R.id.btn_smile) {
//                        btnSmile.setTextSize(20);
//                    } else if (emotionCheckedId == R.id.btn_soso) {
//                        btnSoso.setTextSize(20);
//                    } else if (emotionCheckedId == R.id.btn_angry) {
//                        btnAngry.setTextSize(20);
//                    }
                }
                isEmotionChecking = true;

                if (emotionCheckedId == R.id.btn_smile) {
                    btnSmile.setTextSize(20);
                    btnSoso.setTextSize(15);
                    btnAngry.setTextSize(15);
                    btnHappy.setTextSize(15);
                    btnRelax.setTextSize(15);
                    btnSad.setTextSize(15);
                } else if (emotionCheckedId == R.id.btn_soso) {
                    btnSmile.setTextSize(15);
                    btnSoso.setTextSize(20);
                    btnAngry.setTextSize(15);
                    btnHappy.setTextSize(15);
                    btnRelax.setTextSize(15);
                    btnSad.setTextSize(15);
                } else if (emotionCheckedId == R.id.btn_angry) {
                    btnSmile.setTextSize(15);
                    btnSoso.setTextSize(15);
                    btnAngry.setTextSize(20);
                    btnHappy.setTextSize(15);
                    btnRelax.setTextSize(15);
                    btnSad.setTextSize(15);
                }
            }
        });

        emotionRadioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isEmotionChecking) {
                    isEmotionChecking = false;
                    emotionRadioGroup1.clearCheck();
                    emotionCheckedId = checkedId;

//                    if (emotionCheckedId == R.id.btn_happy) {
//                        btnHappy.setTextSize(20);
//                    } else if (emotionCheckedId == R.id.btn_relax) {
//                        btnRelax.setTextSize(20);
//                    } else if (emotionCheckedId == R.id.btn_sad) {
//                        btnSad.setTextSize(20);
//                    }
                }
                isEmotionChecking = true;

                if (emotionCheckedId == R.id.btn_happy) {
                    btnSmile.setTextSize(15);
                    btnSoso.setTextSize(15);
                    btnAngry.setTextSize(15);
                    btnHappy.setTextSize(20);
                    btnRelax.setTextSize(15);
                    btnSad.setTextSize(15);
                } else if (emotionCheckedId == R.id.btn_relax) {
                    btnSmile.setTextSize(15);
                    btnSoso.setTextSize(15);
                    btnAngry.setTextSize(15);
                    btnHappy.setTextSize(15);
                    btnRelax.setTextSize(20);
                    btnSad.setTextSize(15);
                } else if (emotionCheckedId == R.id.btn_sad) {
                    btnSmile.setTextSize(15);
                    btnSoso.setTextSize(15);
                    btnAngry.setTextSize(15);
                    btnHappy.setTextSize(15);
                    btnRelax.setTextSize(15);
                    btnSad.setTextSize(20);
                }
            }
        });


        diaryEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rect displayRectangle = new Rect();

                Window window = AddActivity.this.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

                final AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this, R.style.CustomAlertDialog);

                ViewGroup viewGroup = findViewById(android.R.id.content);
                final View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.custom_dialog, viewGroup, false);
                dialogView.setMinimumWidth((int) (displayRectangle.width() * 1f - 50));
                dialogView.setMinimumHeight((int) (displayRectangle.height() * 1f - 50));

                builder.setView(dialogView);

                final AlertDialog alertDialog = builder.create();

                final ImageView bigEmoji = dialogView.findViewById(R.id.bigEmoji);
                Button btnOk = dialogView.findViewById(R.id.btnOk);
                final GridView emojiGridView = dialogView.findViewById(R.id.diaryEmojiGridView);

                final EmojiGridViewAdapter emojiGridViewAdapter = new EmojiGridViewAdapter(AddActivity.this, R.layout.row);
                emojiGridView.setAdapter(emojiGridViewAdapter);

                emojiGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int[] imgs = emojiGridViewAdapter.getEmojiImages();
                        bigEmoji.setImageResource(imgs[position]);
                        diaryEmoji.setImageResource(imgs[position]);

                        switch (position) {
                            case 0: dEmoji = 1; break;
                            case 1: dEmoji = 2; break;
                            case 2: dEmoji = 3; break;
                            case 3: dEmoji = 4; break;
                            case 4: dEmoji = 5; break;
                            case 5: dEmoji = 6; break;
                            case 6: dEmoji = 7; break;
                            case 7: dEmoji = 8; break;
                            case 8: dEmoji = 9; break;
                            case 9: dEmoji = 10; break;
                            case 10: dEmoji = 11; break;
                            case 11: dEmoji = 12; break;
                            case 12: dEmoji = 13; break;
                            case 13: dEmoji = 14; break;
                            case 14: dEmoji = 15; break;
                            case 15: dEmoji = 16; break;
                            case 16: dEmoji = 17; break;
                            case 17: dEmoji = 18; break;
                            case 18: dEmoji = 19; break;
                            case 19: dEmoji = 20; break;
                            case 20: dEmoji = 21; break;
                            case 21: dEmoji = 22; break;
                            case 22: dEmoji = 23; break;
                            case 23: dEmoji = 24; break;
                            case 24: dEmoji = 25; break;
                            case 25: dEmoji = 26; break;
                            case 26: dEmoji = 27; break;
                            case 27: dEmoji = 28; break;
                            case 28: dEmoji = 29; break;
                            case 29: dEmoji = 30; break;
                            case 30: dEmoji = 31; break;
                            case 31: dEmoji = 32; break;
                        }
                    }
                });

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

        diaryPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHome = new Intent(AddActivity.this, MainActivity.class);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intentHome);
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 데이터 추출
                dYear = diaryYear.getText().toString();
                dMonth = diaryMonth.getText().toString();
                dDay = diaryDay.getText().toString();
                dDate = diaryDate.getText().toString();

                try {
                    if (emotionCheckedId == R.id.btn_smile) {
                        dEmotion = "좋아";
                    } else if (emotionCheckedId == R.id.btn_soso) {
                        dEmotion = "그냥 그래";
                    } else if (emotionCheckedId == R.id.btn_angry) {
                        dEmotion = "화나";
                    } else if (emotionCheckedId == R.id.btn_happy) {
                        dEmotion = "행복해";
                    } else if (emotionCheckedId == R.id.btn_relax) {
                        dEmotion = "편안해";
                    } else if (emotionCheckedId == R.id.btn_sad) {
                        dEmotion = "슬퍼";
                    }
                } catch (Exception e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
                    builder.setTitle("있잖아요");
                    builder.setMessage("오늘 기분을 선택해주세요");

                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                }

                try {
                    dContent = diaryContent.getText().toString();
                } catch (Exception e) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
//                    builder.setTitle("있잖아요");
//                    builder.setMessage("일기를 적어주세요!");
//
//                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    });
                    dContent = "";
                }

                try {
                    dPhoto = uri.toString();
                } catch (Exception e) {
                    dPhoto = null;
                }

                // 해당 날짜에 이미 일기가 있다면
                Diary diary = DBHelper.dGetDiary(dYear, dMonth, dDay);
                Log.d(TAG, "onClick: ObjectUtils.isEmpty(DBHelper.dGetDiary(dYear, dMonth, dDay) " + ObjectUtils.isEmpty(diary));
                if (ObjectUtils.isEmpty(DBHelper.dGetDiary(dYear, dMonth, dDay)) == false) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
                    builder.setTitle("있잖아요").setMessage("이 날 일기 이미 있는데 바꿀래요?");

                    // 확인 버튼
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DBHelper.dUpdate(dYear, dMonth, dDay, dDate, dEmotion, dEmoji, dContent, dPhoto);

                            Toast.makeText(getApplicationContext(), "바꿨어요!", Toast.LENGTH_SHORT).show();

                            Intent intentHome = new Intent(AddActivity.this, MainActivity.class);
                            intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intentHome);

                            dialog.dismiss();

                            finish();
                        }
                    });

                    // 취소 버튼
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intentHome = new Intent(AddActivity.this, MainActivity.class);
                            intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intentHome);

                            dialog.dismiss();

                            finish();
                        }
                    });

                    builder.show();

                } else {
                    DBHelper.dInsert(dYear, dMonth, dDay, dDate, dEmotion, dEmoji, dContent, dPhoto);

                    Intent intentHome = new Intent(AddActivity.this, MainActivity.class);
                    intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intentHome);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    uri = data.getData();

                    InputStream in = getContentResolver().openInputStream(data.getData());

                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    diaryPhoto.setImageBitmap(img);
                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static class ObjectUtils {
        public static boolean isEmpty(Object s) {
            if (s == null) {
                return true;
            }
            if ((s instanceof String) && (((String) s).trim().length() == 0)) {
                return true;
            }
            if (s instanceof Map) {
                return ((Map<?, ?>) s).isEmpty();
            }
            if (s instanceof List) {
                return ((List<?>) s).isEmpty();
            }
            if (s instanceof Object[]) {
                return (((Object[]) s).length == 0);
            }
            return false;
        }
    }

}
