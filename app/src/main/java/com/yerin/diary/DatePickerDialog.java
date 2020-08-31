package com.yerin.diary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

import static android.content.ContentValues.TAG;

public class DatePickerDialog extends Dialog {
    private static final int MAX_YEAR = 2099;
    private static final int MIN_YEAR = 1980;

    private android.app.DatePickerDialog.OnDateSetListener listener;
    private Calendar cal = Calendar.getInstance();

    private Context context;

    private Button btnConfirm, btnCancel;
    private NumberPicker yearPicker, monthPicker, dayPicker;

    private int currentYear, currentMonth, currentDay;

    public void setListener(android.app.DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    public DatePickerDialog(@NonNull Context context, int year, int month, int day) {
        super(context);
        this.context = context;
        this.currentYear = year;
        this.currentMonth = month;
        this.currentDay = day;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_datepicker);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(params);

        dialog.show();

        btnConfirm = dialog.findViewById(R.id.btnConfirm);
        btnCancel = dialog.findViewById(R.id.btnCancel);

        yearPicker = dialog.findViewById(R.id.pickerYear);
        monthPicker = dialog.findViewById(R.id.pickerMonth);
        dayPicker = dialog.findViewById(R.id.pickerDay);

        int year = cal.get(Calendar.YEAR);
        yearPicker.setMinValue(MIN_YEAR);
        yearPicker.setMaxValue(MAX_YEAR);
        yearPicker.setValue(year);

        int month = cal.get(Calendar.MONTH) + 1;
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(month);
        Log.d(TAG, "onCreate: currentMonth: " + currentMonth);

        int day = cal.get(Calendar.DAY_OF_MONTH);
        dayPicker.setMinValue(1);
        dayPicker.setMaxValue(31);
        monthPicker.setValue(day);
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
                listener.onDateSet(null, yearPicker.getValue(), monthPicker.getValue(), dayPicker.getValue());
                dialog.dismiss();
            }
        });

    }
}
