package com.yerin.diary;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;
import static java.lang.System.out;

public class ContentActivity extends Activity {
    private String dYear, dMonth, dDay, dDate, dEmotion, dContent, dPhoto;
    private int dEmoji;

    private Diary diary;
    private DbHelper DBHelper;
    private ArrayList<Diary> dList;

    private TextView diaryYear, diaryMonth, diaryDay, diaryDate, diaryEmotion, diaryContent;
    private ImageView diaryEmoji, diaryPhoto;
    private Button btnBack, btnEdit, btnDelete, btnShare;
    private LinearLayout diaryContentActivityLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        DBHelper = new DbHelper(ContentActivity.this, "diary", null, 1);

        // xml 요소 연결
        btnBack = findViewById(R.id.btnBack);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        btnShare = findViewById(R.id.btnShare);
        diaryYear = findViewById(R.id.diaryYear);
        diaryMonth = findViewById(R.id.diaryMonth);
        diaryDay = findViewById(R.id.diaryDay);
        diaryDate = findViewById(R.id.diaryDate);
        diaryEmotion = findViewById(R.id.diaryEmotion);
        diaryEmoji = findViewById(R.id.diaryEmoji);
        diaryContent = findViewById(R.id.diaryContent);
        diaryPhoto = findViewById(R.id.diaryPhoto);
        diaryContentActivityLayout = findViewById(R.id.contentActivityLayout);


        // DiaryAdapter에서 intent로 보낸 데이터 받기
        final Intent intent = getIntent();

        dYear = intent.getStringExtra("dYear");
        dMonth = intent.getStringExtra("dMonth");
        dDay = intent.getStringExtra("dDay");
        dDate = intent.getStringExtra("dDate");
        Log.d(TAG, "ContentActivity onCreate: getStringExtra dYear: " + dYear + dMonth + dDay + dDate);

//        Log.d(TAG, "onCreate: DBHelper.dGetDiary" + DBHelper.dGetDiary("2020", "08", "16"));

        // intent로 받은 데이터를 이용해 디비에서 데이터 가져오기
        diary = DBHelper.dGetDiary(dYear, dMonth, dDay);

        dEmotion = diary.getdEmotion();
        dEmoji = diary.getdEmoji();
        dContent = diary.getdContent();
        dPhoto = diary.getdPhoto();

        // xml 요소에 데이터 setting
        diaryYear.setText(dYear);
        diaryMonth.setText(dMonth);
        diaryDay.setText(dDay);
        diaryDate.setText(dDate);
        diaryEmotion.setText(dEmotion);

        // 감정에 따라 글씨 색 변경
        switch (dEmotion) {
            case "좋아":
                diaryEmotion.setTextColor(Color.parseColor("#FFC107"));
                break;
            case "그냥 그래":
                diaryEmotion.setTextColor(Color.parseColor("#69A621"));
                break;
            case "화나":
                diaryEmotion.setTextColor(Color.parseColor("#D53226"));
                break;
            case "행복해":
                diaryEmotion.setTextColor(Color.parseColor("#F15626"));
                break;
            case "편안해":
                diaryEmotion.setTextColor(Color.parseColor("#E68BBF"));
                break;
            case "슬퍼":
                diaryEmotion.setTextColor(Color.parseColor("#4888BC"));
                break;
        }

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

        try {
            diaryPhoto.setImageURI(Uri.parse(diary.getdPhoto()));
            Log.d(TAG, "onCreate: getdPhoto: " + diary.getdPhoto());
        } catch (Exception e) {
            diaryPhoto.setVisibility(GONE);
            e.getMessage();
//            holder.dPhotoWarning.setVisibility(View.VISIBLE);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHome = new Intent(getApplicationContext(), MainActivity.class);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intentHome);
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEdit = new Intent(getApplicationContext(), EditActivity.class);
                intentEdit.putExtra("dYear", dYear);
                intentEdit.putExtra("dMonth", dMonth);
                intentEdit.putExtra("dDay", dDay);
                intentEdit.putExtra("dDate", dDate);
                intentEdit.putExtra("dEmotion", dEmotion);
                intentEdit.putExtra("dEmoji", dEmoji);
                intentEdit.putExtra("dContent", dContent);
                intentEdit.putExtra("dPhoto", dPhoto);
                startActivity(intentEdit);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DeleteDialog deleteDialog = new DeleteDialog(ContentActivity.this, dYear, dMonth, dDay);
//                deleteDialog.show();

                final Dialog dialog = new Dialog(ContentActivity.this);

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_warning);

                WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;

                dialog.getWindow().setAttributes(params);

                dialog.show();

                final TextView dialogType = (TextView) dialog.findViewById(R.id.dialogType);
                final TextView dialogMessage = (TextView) dialog.findViewById(R.id.dialogMessage);
                final Button btnCancle = (Button) dialog.findViewById(R.id.btnCancel);
                final Button btnConfirm = (Button) dialog.findViewById(R.id.btnConfirm);

                dialogType.setText("삭제");
                dialogMessage.setText("삭제 후에는 복구가 불가능합니다.\n정말 삭제 하시겠습니까?");
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DbHelper DBHelper = new DbHelper(ContentActivity.this, "diary", null, 1);
                        DBHelper.dDelete(dYear, dMonth, dDay);

                        Intent intentHome = new Intent(ContentActivity.this, MainActivity.class);
                        intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ContentActivity.this.startActivity(intentHome);
                    }
                });
                btnCancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaryContentActivityLayout.setDrawingCacheEnabled(true);

                Bitmap bm = diaryContentActivityLayout.getDrawingCache();

                try {
                    File image = takeScreenshot(bm);
                    Log.d(TAG, "onClick: bm: " + bm.toString());
                    Intent intentShare = new Intent();
                    intentShare.setAction(Intent.ACTION_SEND);
                    intentShare.setType("image/jpeg");
                    intentShare.putExtra(Intent.EXTRA_STREAM, Uri.parse(String.valueOf(image)));

                    try {
                        startActivity(Intent.createChooser(intentShare, "Share Screenshot"));
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(ContentActivity.this, "No App Available", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    bm.recycle();
                    e.printStackTrace();
                }
            }
        });
    }

    private File takeScreenshot(Bitmap bm) throws Exception {
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "diary_" + timeStamp + "_" + "screenshot_";
        Log.d(TAG, "createImageFile: imageFileName: " + imageFileName);

        // 이미지가 저장될 폴더 이름 ( Diary )
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + File.separator + "/Diary/");
        if (!storageDir.exists()) storageDir.mkdirs();
        Log.d(TAG, "createImageFile: storageDir: " + storageDir);

        // 파일 생성
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        Log.d(TAG, "createImageFile : " + image.getAbsolutePath());

        // 파일 압축
        FileOutputStream fos;
        fos = new FileOutputStream(image);
        bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);

        return image;
    }
}
