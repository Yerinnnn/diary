package com.yerin.diary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class MenuDialog extends DialogFragment {
    private android.app.DatePickerDialog.OnDateSetListener listener;

    private Button btnEdit, btnDelete, btnShare;
    private DbHelper DBHelper;
    private String dYear, dMonth, dDate, dContent;

    public MenuDialog(String year, String month, String date, String content) {
        this.dYear = year;
        this.dMonth = month;
        this.dDate = date;
        this.dContent = content;
    }

    public void setListener(android.app.DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialog = inflater.inflate(R.layout.diary_menu_dialog, null);
        DBHelper = new DbHelper(getContext(), "diary", null, 1);


        builder.setView(dialog);

        return builder.create();
    }
}
