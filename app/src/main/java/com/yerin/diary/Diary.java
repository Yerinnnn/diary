package com.yerin.diary;

public class Diary {
    private String dYear;
    private String dMonth;
    private String dDay;
    private String dDate;
    private String dEmotion;
    private int dEmoji;
    private String dContent;
    private String dPhoto;

    public Diary(String year, String month, String day, String date, String emotion, int emoji, String content, String photo) {
        dYear = year;
        dMonth = month;
        dDay = day;
        dDate = date;
        dEmotion = emotion;
        dEmoji = emoji;
        dContent = content;
        dPhoto = photo;
    }

    public String getdYear() {
        return dYear;
    }

    public void setdYear(String dYear) {
        this.dYear = dYear;
    }

    public String getdMonth() {
        return dMonth;
    }

    public void setdMonth(String dMonth) {
        this.dMonth = dMonth;
    }

    public String getdDay() {
        return dDay;
    }

    public void setdDay(String dDay) {
        this.dDay = dDay;
    }

    public String getdDate() {
        return dDate;
    }

    public void setdDate(String dDate) {
        this.dDate = dDate;
    }

    public String getdEmotion() {
        return dEmotion;
    }

    public void setdEmotion(String dEmotion) {
        this.dEmotion = dEmotion;
    }

    public int getdEmoji() {
        return dEmoji;
    }

    public void setdEmoji(int dEmoji) {
        this.dEmoji = dEmoji;
    }

    public String getdContent() {
        return dContent;
    }

    public void setdContent(String dContent) {
        this.dContent = dContent;
    }

    public String getdPhoto() {
        return dPhoto;
    }

    public void setdPhoto(String dPhoto) {
        this.dPhoto = dPhoto;
    }
}
