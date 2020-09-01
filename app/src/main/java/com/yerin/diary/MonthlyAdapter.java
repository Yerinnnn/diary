package com.yerin.diary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MonthlyAdapter extends RecyclerView.Adapter<MonthlyAdapter.MonthlyViewHolder> {
    private Context mContext;
    private ArrayList<Monthly> mList;

    public void setmContext(Context context) {
        this.mContext = context;
    }

    MonthlyAdapter(ArrayList<Monthly> list) {
        mList = list;
    }

    public class MonthlyViewHolder extends RecyclerView.ViewHolder {
        private TextView monthlyYear;
        private TextView monthlyMonth;
        private RecyclerView diaryRecyclerview;


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
        final ArrayList<Diary> dList;
        final DbHelper DBHelper;

        DBHelper = new DbHelper(mContext, "diary", null, 1);

        dList = DBHelper.dGetDiaryList(mList.get(position).getmYear(), mList.get(position).getmMonth());

        DiaryAdapter dAdapter = new DiaryAdapter(dList);

        // 리사이클러뷰 탑재
        MonthlyViewHolder.diaryRecyclerview.setHasFixedSize(true);
        MonthlyViewHolder.diaryRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        diaryRecyclerView.setNestedScrollingEnabled(false);

        dAdapter = new DiaryAdapter(dList);
        dAdapter.setdContext(mContext);
        diaryRecyclerView.setAdapter(dAdapter);

        holder.monthlyYear.setText(mList.get(position).getmYear());
        holder.monthlyMonth.setText(mList.get(position).getmMonth());
        holder.diaryRecyclerview.setAdapter(dAdapter);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
