package com.yerin.diary;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MonthlyAdapter extends RecyclerView.Adapter<MonthlyAdapter.MonthlyViewHolder> {
    private Context mContext;
    private ArrayList<Monthly> mList;
    private ArrayList<Diary> dList;

    public void setmContext(Context context) {
        this.mContext = context;
    }

    MonthlyAdapter(ArrayList<Monthly> list) {
        mList = list;
    }

    public class MonthlyViewHolder extends RecyclerView.ViewHolder {
        TextView monthlyYear;
        TextView monthlyMonth;
        RecyclerView diaryRecyclerview;

        public MonthlyViewHolder(@NonNull View itemView) {
            super(itemView);

            monthlyYear = itemView.findViewById(R.id.monthlyYear);
            monthlyMonth = itemView.findViewById(R.id.monthlyMonth);
            diaryRecyclerview = itemView.findViewById(R.id.diaryRecyclerview);
        }
    }


    @NonNull
    @Override
    public MonthlyAdapter.MonthlyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.monthly_recyclerview, parent, false);

        MonthlyAdapter.MonthlyViewHolder vh = new MonthlyAdapter.MonthlyViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MonthlyAdapter.MonthlyViewHolder holder, int position) {

        // 중첩 리사이클러뷰
        DbHelper DBHelper = new DbHelper(mContext, "diary", null, 1);
        dList = DBHelper.dGetDiaryList(mList.get(position).getmYear(), mList.get(position).getmMonth());
        Log.d(TAG, "onBindViewHolder: dList.isEmpty(): " + mList.get(position).getmYear() + " " + mList.get(position).getmMonth() + " " + dList.isEmpty());

        DiaryAdapter dAdapter = new DiaryAdapter(dList);
        dAdapter.setdContext(mContext);

        if (dList.isEmpty() == true) {
            holder.monthlyYear.setVisibility(View.GONE);
            holder.monthlyMonth.setVisibility(View.GONE);
            holder.diaryRecyclerview.setVisibility(View.GONE);
        }

        // 리사이클러뷰에 어댑터 탑재
        holder.diaryRecyclerview.setHasFixedSize(true);
        holder.diaryRecyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        holder.diaryRecyclerview.setAdapter(dAdapter);

        holder.monthlyYear.setText(mList.get(position).getmYear());
        holder.monthlyMonth.setText(mList.get(position).getmMonth());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
