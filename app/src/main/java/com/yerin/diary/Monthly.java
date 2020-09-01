package com.yerin.diary;

public class Monthly {
    private String mYear;
    private String mMonth;

    public Monthly(String mYear, String mMonth) {
        this.mYear = mYear;
        this.mMonth = mMonth;

    }

    public String getmYear() {
        return mYear;
    }

    public void setmYear(String mYear) {
        this.mYear = mYear;
    }

    public String getmMonth() {
        return mMonth;
    }

    public void setmMonth(String mMonth) {
        this.mMonth = mMonth;
    }
}
