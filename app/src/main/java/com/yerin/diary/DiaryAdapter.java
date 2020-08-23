package com.yerin.diary;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {
    private ArrayList<Diary> dList;

    public class DiaryViewHolder extends RecyclerView.ViewHolder {
        TextView dYear;
        TextView dMonth;
        TextView dDay;
        TextView dDate;
        TextView dEmotion;
        ImageView dEmoji;
        TextView dContent;
        ImageView dPhoto;
        TextView dPhotoWarning;

        public DiaryViewHolder(@NonNull final View itemView) {
            super(itemView);

            dYear = itemView.findViewById(R.id.diaryYear);
            dMonth = itemView.findViewById(R.id.diaryMonth);
            dDay = itemView.findViewById(R.id.diaryDay);
            dDate = itemView.findViewById(R.id.diaryDate);
            dEmotion = itemView.findViewById(R.id.diaryEmotion);
            dEmoji = itemView.findViewById(R.id.diaryEmoji);
            dContent = itemView.findViewById(R.id.diaryContent);
            dPhoto = itemView.findViewById(R.id.diaryPhoto);
            dPhotoWarning = itemView.findViewById(R.id.diaryPhotoWarning);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        try {
                            dPhoto.setVisibility(View.VISIBLE);
//                        dPhotoWarning.setVisibility(View.VISIBLE);

                            itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int position = getAdapterPosition();
                                    if (position != RecyclerView.NO_POSITION) {
                                        dPhoto.setVisibility(GONE);
                                        dPhotoWarning.setVisibility(GONE);
                                    }
                                }
                            });
                        } catch (Exception e) {

                        }
                    }
                }

            });
        }
    }

    DiaryAdapter(ArrayList<Diary> list) {
        dList = list;
    }

    @Override
    public DiaryAdapter.DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.card_recyclerview, parent, false);

        DiaryAdapter.DiaryViewHolder vh = new DiaryAdapter.DiaryViewHolder(view);

        return vh;
    }

    public Bitmap getDiaryPhoto(byte[] photoByte) {
        Log.d(TAG, "getDiaryPhoto: 65 line photoByte: " + photoByte);
        byte[] list = photoByte;
        Bitmap bitmap = BitmapFactory.decodeByteArray(list, 0, list.length);

        if (list == null) {
            bitmap = null;
        } else {
            bitmap = BitmapFactory.decodeByteArray(list, 0, list.length);
        }
        return bitmap;
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryAdapter.DiaryViewHolder holder, int position) {
        dList.get(position).getdYear();

        holder.dYear.setText(dList.get(position).getdYear());
        holder.dMonth.setText((dList.get(position).getdMonth()) + "");
        holder.dDay.setText((dList.get(position).getdDay()) + "");
        holder.dDate.setText((dList.get(position).getdDate()) + "");
        holder.dEmotion.setText((dList.get(position).getdEmotion()) + "");

        switch (dList.get(position).getdEmoji()) {
            case 1: holder.dEmoji.setImageResource(R.drawable.smile); break;
            case 2: holder.dEmoji.setImageResource(R.drawable.soso); break;
            case 3: holder.dEmoji.setImageResource(R.drawable.angry); break;
            case 4: holder.dEmoji.setImageResource(R.drawable.happy); break;
            case 5: holder.dEmoji.setImageResource(R.drawable.relax); break;
            case 6: holder.dEmoji.setImageResource(R.drawable.sad); break;
        }

        holder.dContent.setText((dList.get(position).getdContent()) + "");
        try {
            holder.dPhoto.setImageURI(Uri.parse(dList.get(position).getdPhoto()));
        } catch (Exception e) {
            holder.dPhoto.setVisibility(GONE);
//            holder.dPhotoWarning.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return dList.size();
    }
}