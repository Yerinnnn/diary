package com.yerin.diary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerDialog extends DialogFragment {
    private static final int MAX_YEAR = 2099;
    private static final int MIN_YEAR = 1980;

    private android.app.DatePickerDialog.OnDateSetListener listener;
    private Calendar cal = Calendar.getInstance();

    private Button btnConfirm, btnCancel;
    private NumberPicker yearPicker, monthPicker, dayPicker;

    public void setListener(android.app.DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialog = inflater.inflate(R.layout.dialog_datepicker, null);

        btnConfirm = dialog.findViewById(R.id.btnConfirm);
        btnCancel = dialog.findViewById(R.id.btnCancel);

        yearPicker = dialog.findViewById(R.id.pickerYear);
        monthPicker = dialog.findViewById(R.id.pickerMonth);
        dayPicker = dialog.findViewById(R.id.pickerDay);

        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DatePickerDialog.this.getDialog().cancel();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                listener.onDateSet(null, yearPicker.getValue(), monthPicker.getValue(), dayPicker.getValue());
                DatePickerDialog.this.getDialog().cancel();

            }
        });

        int year = cal.get(Calendar.YEAR);
        yearPicker.setMinValue(MIN_YEAR);
        yearPicker.setMaxValue(MAX_YEAR);
        yearPicker.setValue(year);

        int month = cal.get(Calendar.MONTH);
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(cal.get(Calendar.MONTH) + 1);

        int day = cal.get(Calendar.DAY_OF_MONTH);
        dayPicker.setMinValue(1);
        dayPicker.setMaxValue(31);
        monthPicker.setValue(day);

        builder.setView(dialog);

        return builder.create();
    }
}
