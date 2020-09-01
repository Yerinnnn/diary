package com.yerin.diary;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import android.app.Fragment;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    private int currentYear;
    private int currentMonth;
    private int currentDay;

    private Calendar calendar;

    private boolean isEmotionChecking = true;
    private int emotionCheckedId;

    private static final int REQUEST_CODE = 0;
    private Uri uri;
    private Uri savingUri;

    private Boolean isCamera = false;
    private Boolean isPermission = true;

    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 2;

    private File tempFile;

    private String dYear, dMonth, dDay, dDate, dEmotion, dContent, dPhoto;
    private int dEmoji;

    private DbHelper DBHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

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
                int currentMonth = calendar.get(Calendar.MONTH + 1);
                int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

//                DatePickerDialog datePickerDialog = new DatePickerDialog(AddActivity.this, currentYear, currentMonth, currentDay);
//                datePickerDialog.setListener(datePickerListener);
//                datePickerDialog.show();

                final Dialog dialog = new Dialog(AddActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_datepicker);

                WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;

                dialog.getWindow().setAttributes(params);

                dialog.show();

                final Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
                final Button btnCancel = dialog.findViewById(R.id.btnCancel);

                final NumberPicker yearPicker = dialog.findViewById(R.id.pickerYear);
                final NumberPicker monthPicker = dialog.findViewById(R.id.pickerMonth);
                final NumberPicker dayPicker = dialog.findViewById(R.id.pickerDay);

                final Calendar cal = Calendar.getInstance();

                final int MAX_YEAR = 2099;
                final int MIN_YEAR = 1980;

//                int year = cal.get(Calendar.YEAR);
                yearPicker.setMinValue(MIN_YEAR);
                yearPicker.setMaxValue(MAX_YEAR);
                yearPicker.setValue(currentYear);

//                int month = cal.get(Calendar.MONTH) + 1;
                monthPicker.setMinValue(1);
                monthPicker.setMaxValue(12);
                monthPicker.setValue(currentMonth);
                Log.d(TAG, "onCreate: currentMonth: " + currentMonth);

//                int day = cal.get(Calendar.DAY_OF_MONTH);
                dayPicker.setMinValue(1);
                dayPicker.setMaxValue(31);
                monthPicker.setValue(currentDay);
                Log.d(TAG, "onCreate: currentDay: " + currentDay);

                btnCancel.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                btnConfirm.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        datePickerListener.onDateSet(null, yearPicker.getValue(), monthPicker.getValue(), dayPicker.getValue());
                        dialog.dismiss();
                    }
                });

            }
        });


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
//                    btnSmile.setTextSize(23);
//                    btnSoso.setTextSize(18);
//                    btnAngry.setTextSize(18);
//                    btnHappy.setTextSize(18);
//                    btnRelax.setTextSize(18);
//                    btnSad.setTextSize(18);
                    btnSmile.setTextColor(Color.parseColor("#000000"));
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
                final Dialog dialog = new Dialog(AddActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_emoji);

                WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;

                dialog.getWindow().setAttributes(params);

                dialog.show();

                final ImageView bigEmoji = dialog.findViewById(R.id.bigEmoji);
                final Button btnOk = dialog.findViewById(R.id.btnOk);
                final GridView emojiGridView = dialog.findViewById(R.id.diaryEmojiGridView);
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
                        dialog.dismiss();
                    }
                });
            }
        });

        diaryPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tedPermission();

                if (isPermission) goToAlbum();
                else
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
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
                    dPhoto = savingUri.toString();
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
                            dialog.dismiss();
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

    android.app.DatePickerDialog.OnDateSetListener datePickerListener = new android.app.DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Log.d("YearMonthPickerTest", "year = " + year + ", month = " + monthOfYear + ", day = " + dayOfMonth);

            diaryYear.setText((year) + "");

            if ((monthOfYear) < 10)
                diaryMonth.setText("0" + (monthOfYear));
            else diaryMonth.setText((dayOfMonth) + "");

            if ((dayOfMonth) < 10)
                diaryDay.setText("0" + (dayOfMonth));
            else diaryDay.setText((dayOfMonth) + "");

            // 위의 날짜에 해당하는 요일
            String day = String.valueOf(year) + String.valueOf(monthOfYear) + String.valueOf(dayOfMonth);
            String[] week = {"일", "월", "화", "수", "목", "금", "토"};
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMd");
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

    // 앨범에서 이미지 가져오기
    private void goToAlbum() {
        isCamera = false;

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();

            if (tempFile != null) {
                if (tempFile.exists()) {

                    if (tempFile.delete()) {
                        Log.e(TAG, tempFile.getAbsolutePath() + " 삭제 성공");
                        tempFile = null;
                    }
                }
            }
            return;
        }

        switch (requestCode) {
            case PICK_FROM_ALBUM: {
                Uri photoUri = data.getData();
                Log.d(TAG, "PICK_FROM_ALBUM photoUri : " + photoUri);

                cropImage(photoUri);

                break;
            }
            case Crop.REQUEST_CROP: {
                // File cropFile = new File(Crop.getOutput(data).getPath());
                setImage();
            }
        }
    }

    // 이미지 crop
    private void cropImage(Uri photoUri) {
        // 갤러리에서 선택한 경우에는 tempFile 이 없으므로 새로 생성해야함
        try {
            tempFile = createImageFile();
        } catch (IOException e) {
            Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            finish();
            e.printStackTrace();
        }

        //크롭 후 저장할 Uri
        savingUri = Uri.fromFile(tempFile);

        Crop.of(photoUri, savingUri).start(this);
    }

    // 폴더 및 파일 만들기
    private File createImageFile() throws IOException {

        // 이미지 파일 이름 ( diary_{시간}_ )
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "diary_" + timeStamp + "_";
        Log.d(TAG, "createImageFile: imageFileName: " + imageFileName);

        // 이미지가 저장될 폴더 이름 ( Diary )
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + File.separator + "/Diary/");
        if (!storageDir.exists()) storageDir.mkdirs();
        Log.d(TAG, "createImageFile: storageDir: " + storageDir);

        // 파일 생성
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        Log.d(TAG, "createImageFile : " + image.getAbsolutePath());

        return image;
    }

    // tempfile을 bitmap으로 변환 후 imageview에 setImage
    private void setImage() {
        diaryPhoto.setImageURI(savingUri);

        // tempFile 사용 후 null 처리가 필요
        // (resultCode != RESULT_OK) 일 때 tempFile 을 삭제하기 때문에
        // 기존에 데이터가 남아있게 되면 원치 않은 삭제가 이루어질 수 있음
        tempFile = null;

    }
    // 권한 설정
    private void tedPermission() {

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공
                isPermission = true;
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
                isPermission = false;
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }
}
