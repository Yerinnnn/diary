package com.yerin.diary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;

    // 생성자
    public DbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (!db.isReadOnly()) {

            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");

        }

        // 새로운 테이블 생성
        // 이름: memo, 자동으로 값 증가하는 _id 정수형 기본키, item 문자열 컬럼, price 정수형 컬럼, create_at 문자열 컬럼으로 구성된 테이블을 생성
        db.execSQL("CREATE TABLE diary (_id INTEGER PRIMARY KEY AUTOINCREMENT, dYear TEXT not null, dMonth TEXT not null," +
                "dDay TEXT not null, dDate TEXT not null, dEmotion TEXT not null, dEmoji INT not null, dContent TEXT not null, dPhoto TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == DB_VERSION) {
            db.execSQL("DROP TABLE diary");
            onCreate(db);
        }
    }

    public void dInsert(String dYear, String dMonth, String dDay, String dDate, String dEmotion, int dEmoji, String dContent, String dPhoto) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "INSERT INTO diary VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement sqLiteStatement = db.compileStatement(query);

        sqLiteStatement.bindString(2, dYear);
        sqLiteStatement.bindString(3, dMonth);
        sqLiteStatement.bindString(4, dDay);
        sqLiteStatement.bindString(5, dDate);
        sqLiteStatement.bindString(6, String.valueOf(dEmotion));
        sqLiteStatement.bindLong(7, dEmoji);
        sqLiteStatement.bindString(8, dContent);

        try {
            sqLiteStatement.bindString(9, dPhoto);
        } catch (Exception e) {
            sqLiteStatement.bindNull(9);
        }

        sqLiteStatement.execute();

        db.close();
    }

    public void dUpdate(String dYear, String dMonth, String dDay, String dDate, String dEmotion, int dEmoji, String dContent, String dPhoto) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM diary WHERE dYear = '" + dYear + "' AND dMonth = '" + dMonth + "' AND dDay = '" + dDay + "';");
//        String queryDelete = "DELETE FROM diary WHERE dYear = '" + dYear + "', dMonth = '" + dMonth + "', dDay = '" + dDay + "';";

        String query = "INSERT INTO diary VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
        SQLiteStatement sqLiteStatement = db.compileStatement(query);

        sqLiteStatement.bindString(2, dYear);
        sqLiteStatement.bindString(3, dMonth);
        sqLiteStatement.bindString(4, dDay);
        sqLiteStatement.bindString(5, dDate);
        sqLiteStatement.bindString(6, String.valueOf(dEmotion));
        sqLiteStatement.bindLong(7, dEmoji);
        sqLiteStatement.bindString(8, dContent);

        try {
            sqLiteStatement.bindString(9, dPhoto);
        } catch (Exception e) {
            sqLiteStatement.bindNull(9);
        }

        sqLiteStatement.execute();

        db.close();
    }

    public void dDelete(String dYear, String dMonth, String dDay) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM diary WHERE dYear = '" + dYear + "' AND dMonth = '" + dMonth + "' AND dDay = '" + dDay + "';");

//        String query = "DELETE FROM diary WHERE dYear=(?) AND dMonth=(?) AND dDay=(?)";
//        SQLiteStatement sqLiteStatement = db.compileStatement(query);

//        sqLiteStatement.bindString(1, String.valueOf(dYear));
//        sqLiteStatement.bindString(2, String.valueOf(dMonth));
//        sqLiteStatement.bindString(3, String.valueOf(dDay));

        db.close();
    }

    public void dInitialization(String dTitle) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM diary;");

        db.close();
    }

    public Diary dGetDiary(String dYear, String dMonth, String dDay) {
        String dDate = null;
        String dEmotion = null;
        int dEmoji;
        String dContent = null;
        String dPhoto = null;
        Diary result = null;

        Log.d(TAG, "dGetDiary: dYear: " + dYear);

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM diary WHERE dYear='" + dYear + "'AND dMonth='" +
                dMonth + "'AND dDay='" + dDay + "';", null);

        while (cursor.moveToNext()) {
            dYear = cursor.getString(1);
            dMonth = cursor.getString(2);
            dDay = cursor.getString(3);
            dDate = cursor.getString(4);
            dEmotion = cursor.getString(5);
            dEmoji = cursor.getInt(6);
            dContent = cursor.getString(7);
            dPhoto = cursor.getString(8);

            if (dPhoto != null) {
                dPhoto = cursor.getString(8);
            } else if (dPhoto == null) {
                dPhoto = null;
            }

            result = new Diary(dYear, dMonth, dDay, dDate, dEmotion, dEmoji, dContent, dPhoto);

            Log.d(TAG, "dGetDiary: result dYear: " + result.getdYear());
        }
        return result;
    }

    public ArrayList<Diary> dGetDiaryList(String dYear, String dMonth) {
        String dDay = null;
        String dDate = null;
        String dEmotion = null;
        int dEmoji;
        String dContent = null;
        String dPhoto = null;
        ArrayList<Diary> list = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM diary WHERE dYear='" + dYear + "'AND dMonth='" +
                dMonth + "' ORDER BY dDay DESC;", null);

        while (cursor.moveToNext()) {
            dYear = cursor.getString(1);
            dMonth = cursor.getString(2);
            dDay = cursor.getString(3);
            dDate = cursor.getString(4);
            dEmotion = cursor.getString(5);
            dEmoji = cursor.getInt(6);
            dContent = cursor.getString(7);
//            dPhoto = cursor.getString(8);

            if (dPhoto != null) {
                dPhoto = cursor.getString(8);
            } else if (dPhoto == null) {
                dPhoto = null;
            }
//
            // 모든 데이터 객체에 저장
            Diary results = new Diary(dYear, dMonth, dDay, dDate, dEmotion, dEmoji, dContent, dPhoto);
            list.add(results);
        }
        return list;
    }

    public ArrayList<Diary> dGetDiaryListAll() {
        String dYear = null;
        String dMonth = null;
        String dDay = null;
        String dDate = null;
        String dEmotion = null;
        int dEmoji;
        String dContent = null;
        String dPhoto = null;
        ArrayList<Diary> list = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM diary ORDER BY dMonth DESC, dDay DESC;", null);

        while (cursor.moveToNext()) {
            dYear = cursor.getString(1);
            dMonth = cursor.getString(2);
            dDay = cursor.getString(3);
            dDate = cursor.getString(4);
            dEmotion = cursor.getString(5);
            dEmoji = cursor.getInt(6);
            dContent = cursor.getString(7);
//            dPhoto = cursor.getString(8);

            if (dPhoto != null) {
                dPhoto = cursor.getString(8);
            } else if (dPhoto == null) {
                dPhoto = null;
            }
//
            // 모든 데이터 객체에 저장
            Diary results = new Diary(dYear, dMonth, dDay, dDate, dEmotion, dEmoji, dContent, dPhoto);
            list.add(results);
        }
        return list;
    }

    public String dGetFirstYear() {
        String firstYear = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM diary ORDER BY dMonth DESC;", null);

        while (cursor.moveToNext()) {
            firstYear = cursor.getString(1);
        }

        return firstYear;
    }
}

