package com.yerin.diary;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class DeleteDialog extends Dialog {
    private Context context;

    private TextView dialogType;
    private TextView dialogMessage;
    private Button btnCancle;
    private Button btnConfirm;

    private String dYear;
    private String dMonth;
    private String dDay;

    public DeleteDialog(@NonNull Context context, String dYear, String dMonth, String dDay) {
        super(context);
        this.context = context;
        this.dYear = dYear;
        this.dMonth = dMonth;
        this.dDay = dDay;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_warning);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(params);

        dialog.show();

        dialogType = (TextView) dialog.findViewById(R.id.dialogType);
        dialogMessage = (TextView) dialog.findViewById(R.id.dialogMessage);
        btnCancle = (Button) dialog.findViewById(R.id.btnCancel);
        btnConfirm = (Button) dialog.findViewById(R.id.btnConfirm);

//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//        layoutParams.dimAmount = 0.8f;
//        getWindow().setAttributes(layoutParams);

        dialogType.setText("삭제");
        dialogMessage.setText("삭제 후에는 복구가 불가능합니다.\n정말 삭제 하시겠습니까?");
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper DBHelper = new DbHelper(context, "diary", null, 1);
                DBHelper.dDelete(dYear, dMonth, dDay);

                Intent intentHome = new Intent(context, MainActivity.class);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intentHome);
            }
        });
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

}
