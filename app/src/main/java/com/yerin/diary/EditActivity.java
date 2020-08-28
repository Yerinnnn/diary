package com.yerin.diary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;
import static java.lang.System.in;
import static java.util.Collections.rotate;

public class EditActivity extends Activity {
    private LinearLayout diarySetDate;
    private TextView diaryYear, diaryMonth, diaryDay, diaryDate;
    private RadioGroup emotionRadioGroup1, emotionRadioGroup2;
    private RadioButton btnSmile, btnSoso, btnAngry, btnHappy, btnRelax, btnSad;
    private ImageView diaryEmoji, diaryPhoto;
    private EditText diaryContent;
    private Button btnBack, btnSave;

    private String dYear, dMonth, dDay, dDate, dEmotion, dContent, dPhoto;
    private int dEmoji;

    private DbHelper DBHelper;
    private Diary diary;

    private boolean isEmotionChecking = true;
    private int emotionCheckedId;

    private static final int REQUEST_CODE = 0;
    private Uri uri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        DBHelper = new DbHelper(getApplicationContext(), "diary", null, 1);

        // xml 요소 연결
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

        Intent intent = getIntent();
        dYear = intent.getStringExtra("dYear");
        dMonth = intent.getStringExtra("dMonth");
        dDay = intent.getStringExtra("dDay");
        dDate = intent.getStringExtra("dDate");

        diaryYear.setText(dYear);
        diaryMonth.setText(dMonth);
        diaryDay.setText(dDay);
        diaryDate.setText(dDate);

        Log.d(TAG, "onCreate: EditActivity getStringExtra dYear dMonth: " + dYear + dMonth + dDay + dDate);
        Log.d(TAG, "onCreate: EditActivity dGetDiary: " + DBHelper.dGetDiary(dYear, dMonth, dDay));

        diary = DBHelper.dGetDiary(dYear, dMonth, dDay);

        // 디비 데이터를 사용해 라디오버튼 셋팅
        if (diary.getdEmotion() == "좋아") {
            emotionCheckedId = R.id.btn_smile;
            isEmotionChecking = true;
            btnSmile.setTextSize(23);
            btnSoso.setTextSize(18);
            btnAngry.setTextSize(18);
            btnHappy.setTextSize(18);
            btnRelax.setTextSize(18);
            btnSad.setTextSize(18);
        } else if (diary.getdEmotion() == "그냥 그래") {
            emotionCheckedId = R.id.btn_soso;
            isEmotionChecking = true;
            btnSmile.setTextSize(18);
            btnSoso.setTextSize(23);
            btnAngry.setTextSize(18);
            btnHappy.setTextSize(18);
            btnRelax.setTextSize(18);
            btnSad.setTextSize(18);
        } else if (diary.getdEmotion() == "화나") {
            emotionCheckedId = R.id.btn_angry;
            isEmotionChecking = true;
            btnSmile.setTextSize(18);
            btnSoso.setTextSize(18);
            btnAngry.setTextSize(23);
            btnHappy.setTextSize(18);
            btnRelax.setTextSize(18);
            btnSad.setTextSize(18);
        } else if (diary.getdEmotion() == "행복해") {
            emotionCheckedId = R.id.btn_happy;
            isEmotionChecking = true;
            btnSmile.setTextSize(18);
            btnSoso.setTextSize(18);
            btnAngry.setTextSize(18);
            btnHappy.setTextSize(23);
            btnRelax.setTextSize(18);
            btnSad.setTextSize(18);
        } else if (diary.getdEmotion() == "편안해") {
            emotionCheckedId = R.id.btn_relax;
            isEmotionChecking = true;
            btnSmile.setTextSize(18);
            btnSoso.setTextSize(18);
            btnAngry.setTextSize(18);
            btnHappy.setTextSize(18);
            btnRelax.setTextSize(23);
            btnSad.setTextSize(18);
        } else if (diary.getdEmotion() == "슬퍼") {
            emotionCheckedId = R.id.btn_sad;
            isEmotionChecking = true;
            btnSmile.setTextSize(18);
            btnSoso.setTextSize(18);
            btnAngry.setTextSize(18);
            btnHappy.setTextSize(18);
            btnRelax.setTextSize(18);
            btnSad.setTextSize(23);
        }

        // 디비 데이터를 사용해 이모지 셋팅
        switch (diary.getdEmoji()) {
            case 1:
                diaryEmoji.setImageResource(R.drawable.good_ubu_1);
                break;
            case 2:
                diaryEmoji.setImageResource(R.drawable.slaver_ubu_2);
                break;
            case 3:
                diaryEmoji.setImageResource(R.drawable.suprised_ubu_3);
                break;
            case 4:
                diaryEmoji.setImageResource(R.drawable.study_ubu_4);
                break;
            case 5:
                diaryEmoji.setImageResource(R.drawable.drawing_ubu_5);
                break;
            case 6:
                diaryEmoji.setImageResource(R.drawable.singing_ubu_6);
                break;
            case 7:
                diaryEmoji.setImageResource(R.drawable.yes_ubu_7);
                break;
            case 8:
                diaryEmoji.setImageResource(R.drawable.no_ubu_8);
                break;
            case 9:
                diaryEmoji.setImageResource(R.drawable.dont_know_ubu_9);
                break;
            case 10:
                diaryEmoji.setImageResource(R.drawable.music_ubu_10);
                break;
            case 11:
                diaryEmoji.setImageResource(R.drawable.fish_ubu_11);
                break;
            case 12:
                diaryEmoji.setImageResource(R.drawable.ubu_fish_12);
                break;
            case 13:
                diaryEmoji.setImageResource(R.drawable.muffin_ubu_13);
                break;
            case 14:
                diaryEmoji.setImageResource(R.drawable.hungry_ubu_14);
                break;
            case 15:
                diaryEmoji.setImageResource(R.drawable.cooking_ubu_15);
                break;
            case 16:
                diaryEmoji.setImageResource(R.drawable.sorry_ubu_16);
                break;
            case 17:
                diaryEmoji.setImageResource(R.drawable.shy_ubu_17);
                break;
            case 18:
                diaryEmoji.setImageResource(R.drawable.sad_ubu_18);
                break;
            case 19:
                diaryEmoji.setImageResource(R.drawable.exercise_ubu_19);
                break;
            case 20:
                diaryEmoji.setImageResource(R.drawable.skateboard_ubu_20);
                break;
            case 21:
                diaryEmoji.setImageResource(R.drawable.aerobics_ubu_21);
                break;
            case 22:
                diaryEmoji.setImageResource(R.drawable.ringring_ubu_22);
                break;
            case 23:
                diaryEmoji.setImageResource(R.drawable.watch_ubu_23);
                break;
            case 24:
                diaryEmoji.setImageResource(R.drawable.angry_ubu_24);
                break;
            case 25:
                diaryEmoji.setImageResource(R.drawable.congrats_ubu_25);
                break;
            case 26:
                diaryEmoji.setImageResource(R.drawable.love_ubu_26);
                break;
            case 27:
                diaryEmoji.setImageResource(R.drawable.gift_ubu_27);
                break;
            case 28:
                diaryEmoji.setImageResource(R.drawable.wash_ubu_28);
                break;
            case 29:
                diaryEmoji.setImageResource(R.drawable.brush_teeth_ubu_29);
                break;
            case 30:
                diaryEmoji.setImageResource(R.drawable.tired_ubu_30);
                break;
            case 31:
                diaryEmoji.setImageResource(R.drawable.phone_ubu_31);
                break;
        }

        diaryContent.setText(diary.getdContent());

        // 디비 데이터를 이용해 사진 셋팅
        try {
            diaryPhoto.setImageURI(Uri.parse(diary.getdPhoto()));
        } catch (Exception e) {
            diaryPhoto.setVisibility(GONE);
//            holder.dPhotoWarning.setVisibility(View.VISIBLE);
        }

        // 기능
       emotionRadioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isEmotionChecking) {
                    isEmotionChecking = false;
                    emotionRadioGroup2.clearCheck();
                    emotionCheckedId = checkedId;
                }
                isEmotionChecking = true;

                if (emotionCheckedId == R.id.btn_smile) {
                    btnSmile.setTextSize(23);
                    btnSoso.setTextSize(18);
                    btnAngry.setTextSize(18);
                    btnHappy.setTextSize(18);
                    btnRelax.setTextSize(18);
                    btnSad.setTextSize(18);
                } else if (emotionCheckedId == R.id.btn_soso) {
                    btnSmile.setTextSize(18);
                    btnSoso.setTextSize(23);
                    btnAngry.setTextSize(18);
                    btnHappy.setTextSize(18);
                    btnRelax.setTextSize(18);
                    btnSad.setTextSize(18);
                } else if (emotionCheckedId == R.id.btn_angry) {
                    btnSmile.setTextSize(18);
                    btnSoso.setTextSize(18);
                    btnAngry.setTextSize(23);
                    btnHappy.setTextSize(18);
                    btnRelax.setTextSize(18);
                    btnSad.setTextSize(18);
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
                }
                isEmotionChecking = true;

                if (emotionCheckedId == R.id.btn_happy) {
                    btnSmile.setTextSize(18);
                    btnSoso.setTextSize(18);
                    btnAngry.setTextSize(18);
                    btnHappy.setTextSize(23);
                    btnRelax.setTextSize(18);
                    btnSad.setTextSize(18);
                } else if (emotionCheckedId == R.id.btn_relax) {
                    btnSmile.setTextSize(18);
                    btnSoso.setTextSize(18);
                    btnAngry.setTextSize(18);
                    btnHappy.setTextSize(18);
                    btnRelax.setTextSize(23);
                    btnSad.setTextSize(18);
                } else if (emotionCheckedId == R.id.btn_sad) {
                    btnSmile.setTextSize(18);
                    btnSoso.setTextSize(18);
                    btnAngry.setTextSize(18);
                    btnHappy.setTextSize(18);
                    btnRelax.setTextSize(18);
                    btnSad.setTextSize(23);
                }
            }
        });

        diaryEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rect displayRectangle = new Rect();

                Window window = EditActivity.this.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

                final AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this, R.style.CustomAlertDialog);

                ViewGroup viewGroup = findViewById(android.R.id.content);
                final View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.custom_dialog, viewGroup, false);
                dialogView.setMinimumWidth((int) (displayRectangle.width() * 1f - 50));
                dialogView.setMinimumHeight((int) (displayRectangle.height() * 1f - 50));

                builder.setView(dialogView);

                final AlertDialog alertDialog = builder.create();

                final ImageView bigEmoji = dialogView.findViewById(R.id.bigEmoji);
                Button btnOk = dialogView.findViewById(R.id.btnOk);
                final GridView emojiGridView = dialogView.findViewById(R.id.diaryEmojiGridView);

                final EmojiGridViewAdapter emojiGridViewAdapter = new EmojiGridViewAdapter(EditActivity.this, R.layout.row);
                emojiGridView.setAdapter(emojiGridViewAdapter);

                emojiGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int[] imgs = emojiGridViewAdapter.getEmojiImages();
                        bigEmoji.setImageResource(imgs[position]);
                        diaryEmoji.setImageResource(imgs[position]);

                        switch (position) {
                            case 0:
                                dEmoji = 1;
                                break;
                            case 1:
                                dEmoji = 2;
                                break;
                            case 2:
                                dEmoji = 3;
                                break;
                            case 3:
                                dEmoji = 4;
                                break;
                            case 4:
                                dEmoji = 5;
                                break;
                            case 5:
                                dEmoji = 6;
                                break;
                            case 6:
                                dEmoji = 7;
                                break;
                            case 7:
                                dEmoji = 8;
                                break;
                            case 8:
                                dEmoji = 9;
                                break;
                            case 9:
                                dEmoji = 10;
                                break;
                            case 10:
                                dEmoji = 11;
                                break;
                            case 11:
                                dEmoji = 12;
                                break;
                            case 12:
                                dEmoji = 13;
                                break;
                            case 13:
                                dEmoji = 14;
                                break;
                            case 14:
                                dEmoji = 15;
                                break;
                            case 15:
                                dEmoji = 16;
                                break;
                            case 16:
                                dEmoji = 17;
                                break;
                            case 17:
                                dEmoji = 18;
                                break;
                            case 18:
                                dEmoji = 19;
                                break;
                            case 19:
                                dEmoji = 20;
                                break;
                            case 20:
                                dEmoji = 21;
                                break;
                            case 21:
                                dEmoji = 22;
                                break;
                            case 22:
                                dEmoji = 23;
                                break;
                            case 23:
                                dEmoji = 24;
                                break;
                            case 24:
                                dEmoji = 25;
                                break;
                            case 25:
                                dEmoji = 26;
                                break;
                            case 26:
                                dEmoji = 27;
                                break;
                            case 27:
                                dEmoji = 28;
                                break;
                            case 28:
                                dEmoji = 29;
                                break;
                            case 29:
                                dEmoji = 30;
                                break;
                            case 30:
                                dEmoji = 31;
                                break;
                            case 31:
                                dEmoji = 32;
                                break;
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
                Intent intentHome = new Intent(EditActivity.this, MainActivity.class);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
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

                DBHelper.dUpdate(dYear, dMonth, dDay, dDate, dEmotion, dEmoji, dContent, dPhoto);

                Intent intentHome = new Intent(EditActivity.this, MainActivity.class);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentHome);
                finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    uri = data.getData();

                    InputStream in = getContentResolver().openInputStream(data.getData());

                    Bitmap img = BitmapFactory.decodeStream(in);

                    ExifInterface exif = new ExifInterface(getContentResolver().openInputStream(uri));

                    in.close();

                    int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                    Matrix matrix = new Matrix();
                    if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                        matrix.postRotate(90);
                    } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                        matrix.postRotate(180);
                    } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                        matrix.postRotate(270);
                    }
//                    ExifInterface exif = new ExifInterface(uri.getPath());
//                    int exifOrientation = exif.getAttributeInt(
//                            ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//                    int exifDegree = exifOrientationToDegrees(exifOrientation);
//
//                    img = rotate(img, exifDegree);
//                    diaryPhoto.setImageBitmap(img);
//                } catch(Exception e) {}

                    diaryPhoto.setImageBitmap(img);
                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }

    public int exifOrientationToDegrees(int exifOrientation){
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {

            return 270;
        } return 0;
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
