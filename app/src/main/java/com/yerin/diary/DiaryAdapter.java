package com.yerin.diary;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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

        // 감정에 따라 글씨 색 변경
        switch (dList.get(position).getdEmotion()) {
            case "좋아": holder.dEmotion.setTextColor(Color.parseColor("#FFC107")); break;
            case "그냥 그래": holder.dEmotion.setTextColor(Color.parseColor("#69A621")); break;
            case "화나": holder.dEmotion.setTextColor(Color.parseColor("#D53226")); break;
            case "행복해": holder.dEmotion.setTextColor(Color.parseColor("#F15626")); break;
            case "편안해": holder.dEmotion.setTextColor(Color.parseColor("#E68BBF")); break;
            case "슬퍼": holder.dEmotion.setTextColor(Color.parseColor("#4888BC")); break;
        }

        switch (dList.get(position).getdEmoji()) {
            case 1: holder.dEmoji.setImageResource(R.drawable.good_ubu_1); break;
            case 2: holder.dEmoji.setImageResource(R.drawable.slaver_ubu_2); break;
            case 3: holder.dEmoji.setImageResource(R.drawable.suprised_ubu_3); break;
            case 4: holder.dEmoji.setImageResource(R.drawable.study_ubu_4); break;
            case 5: holder.dEmoji.setImageResource(R.drawable.drawing_ubu_5); break;
            case 6: holder.dEmoji.setImageResource(R.drawable.singing_ubu_6); break;
            case 7: holder.dEmoji.setImageResource(R.drawable.yes_ubu_7); break;
            case 8: holder.dEmoji.setImageResource(R.drawable.no_ubu_8); break;
            case 9: holder.dEmoji.setImageResource(R.drawable.dont_know_ubu_9); break;
            case 10: holder.dEmoji.setImageResource(R.drawable.music_ubu_10); break;
            case 11: holder.dEmoji.setImageResource(R.drawable.fish_ubu_11); break;
            case 12: holder.dEmoji.setImageResource(R.drawable.ubu_fish_12); break;
            case 13: holder.dEmoji.setImageResource(R.drawable.muffin_ubu_13); break;
            case 14: holder.dEmoji.setImageResource(R.drawable.hungry_ubu_14); break;
            case 15: holder.dEmoji.setImageResource(R.drawable.cooking_ubu_15); break;
            case 16: holder.dEmoji.setImageResource(R.drawable.sorry_ubu_16); break;
            case 17: holder.dEmoji.setImageResource(R.drawable.shy_ubu_17); break;
            case 18: holder.dEmoji.setImageResource(R.drawable.sad_ubu_18); break;
            case 19: holder.dEmoji.setImageResource(R.drawable.exercise_ubu_19); break;
            case 20: holder.dEmoji.setImageResource(R.drawable.skateboard_ubu_20); break;
            case 21: holder.dEmoji.setImageResource(R.drawable.aerobics_ubu_21); break;
            case 22: holder.dEmoji.setImageResource(R.drawable.ringring_ubu_22); break;
            case 23: holder.dEmoji.setImageResource(R.drawable.watch_ubu_23); break;
            case 24: holder.dEmoji.setImageResource(R.drawable.angry_ubu_24); break;
            case 25: holder.dEmoji.setImageResource(R.drawable.congrats_ubu_25); break;
            case 26: holder.dEmoji.setImageResource(R.drawable.love_ubu_26); break;
            case 27: holder.dEmoji.setImageResource(R.drawable.gift_ubu_27); break;
            case 28: holder.dEmoji.setImageResource(R.drawable.wash_ubu_28); break;
            case 29: holder.dEmoji.setImageResource(R.drawable.brush_teeth_ubu_29); break;
            case 30: holder.dEmoji.setImageResource(R.drawable.tired_ubu_30); break;
            case 31: holder.dEmoji.setImageResource(R.drawable.phone_ubu_31); break;
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