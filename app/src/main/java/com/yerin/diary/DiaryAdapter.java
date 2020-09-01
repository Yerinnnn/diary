package com.yerin.diary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {
    private ArrayList<Diary> dList;
    private Context dContext;

    public void setdContext(Context context) {
        this.dContext = context;
    }

    DiaryAdapter(ArrayList<Diary> list) {
        dList = list;
    }

    public class DiaryViewHolder extends RecyclerView.ViewHolder {
        TextView diaryYear;
        TextView diaryMonth;
        TextView diaryDay;
        TextView diaryDate;
        TextView diaryEmotion;
        ImageView diaryEmoji;
        TextView diaryContent;
        ImageView diaryPhoto;
        TextView diaryPhotoWarning;

        public DiaryViewHolder(@NonNull final View itemView) {
            super(itemView);

            diaryYear = itemView.findViewById(R.id.diaryYear);
            diaryMonth = itemView.findViewById(R.id.diaryMonth);
            diaryDay = itemView.findViewById(R.id.diaryDay);
            diaryDate = itemView.findViewById(R.id.diaryDate);
            diaryEmotion = itemView.findViewById(R.id.diaryEmotion);
            diaryEmoji = itemView.findViewById(R.id.diaryEmoji);
            diaryContent = itemView.findViewById(R.id.diaryContent);
            diaryPhoto = itemView.findViewById(R.id.diaryPhoto);
            diaryPhotoWarning = itemView.findViewById(R.id.diaryPhotoWarning);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    // 아이템 클릭 시 ContentActivity로 이동
                    if (position != RecyclerView.NO_POSITION) {
                            Intent intent = new Intent(dContext, ContentActivity.class);

                            String dYear = dList.get(position).getdYear();
                            String dMonth = dList.get(position).getdMonth();
                            String dDay = dList.get(position).getdDay();
                            String dDate = dList.get(position).getdDate();

                            intent.putExtra("dYear", dYear);
                            intent.putExtra("dMonth", dMonth);
                            intent.putExtra("dDay", dDay);
                            intent.putExtra("dDate", dDate);

                            dContext.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                }

            });

            // 아이템 롱 클릭시 메뉴 다이얼로그
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final String[] items = {"편집하기", "삭제하기", "공유하기"};
                    final int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
//                        MenuDialog menuDialog = new MenuDialog(dList.get(position).getdYear(), dList.get(position).getdMonth(), dList.get(position).getdDate(), dList.get(position).getdContent());
//                        menuDialog.show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(dContext);
                        builder.setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item


                            }
                        });
                        AlertDialog alertDialog = builder.create();
//                        alertDialog.setBackgroundDrawable(new ColorDrawable(Color.argb(255,62,79,92)));
                    }


                    return false;
                }
            });
        }
    }

    @Override
    public DiaryAdapter.DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.card_recyclerview, parent, false);

        DiaryAdapter.DiaryViewHolder vh = new DiaryAdapter.DiaryViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryAdapter.DiaryViewHolder holder, int position) {
        holder.diaryYear.setText(dList.get(position).getdYear());
        holder.diaryMonth.setText((dList.get(position).getdMonth()) + "");
        holder.diaryDay.setText((dList.get(position).getdDay()) + "");
        holder.diaryDate.setText((dList.get(position).getdDate()) + "");
        holder.diaryEmotion.setText((dList.get(position).getdEmotion()) + "");

        // 감정에 따라 글씨 색 변경
        switch (dList.get(position).getdEmotion()) {
            case "좋아":
                holder.diaryEmotion.setTextColor(Color.parseColor("#FFC107"));
                break;
            case "그냥 그래":
                holder.diaryEmotion.setTextColor(Color.parseColor("#69A621"));
                break;
            case "화나":
                holder.diaryEmotion.setTextColor(Color.parseColor("#D53226"));
                break;
            case "행복해":
                holder.diaryEmotion.setTextColor(Color.parseColor("#F15626"));
                break;
            case "편안해":
                holder.diaryEmotion.setTextColor(Color.parseColor("#E68BBF"));
                break;
            case "슬퍼":
                holder.diaryEmotion.setTextColor(Color.parseColor("#4888BC"));
                break;
        }

        switch (dList.get(position).getdEmoji()) {
            case 1:
                holder.diaryEmoji.setImageResource(R.drawable.good_ubu_1);
                break;
            case 2:
                holder.diaryEmoji.setImageResource(R.drawable.slaver_ubu_2);
                break;
            case 3:
                holder.diaryEmoji.setImageResource(R.drawable.suprised_ubu_3);
                break;
            case 4:
                holder.diaryEmoji.setImageResource(R.drawable.study_ubu_4);
                break;
            case 5:
                holder.diaryEmoji.setImageResource(R.drawable.drawing_ubu_5);
                break;
            case 6:
                holder.diaryEmoji.setImageResource(R.drawable.singing_ubu_6);
                break;
            case 7:
                holder.diaryEmoji.setImageResource(R.drawable.yes_ubu_7);
                break;
            case 8:
                holder.diaryEmoji.setImageResource(R.drawable.no_ubu_8);
                break;
            case 9:
                holder.diaryEmoji.setImageResource(R.drawable.dont_know_ubu_9);
                break;
            case 10:
                holder.diaryEmoji.setImageResource(R.drawable.music_ubu_10);
                break;
            case 11:
                holder.diaryEmoji.setImageResource(R.drawable.fish_ubu_11);
                break;
            case 12:
                holder.diaryEmoji.setImageResource(R.drawable.ubu_fish_12);
                break;
            case 13:
                holder.diaryEmoji.setImageResource(R.drawable.muffin_ubu_13);
                break;
            case 14:
                holder.diaryEmoji.setImageResource(R.drawable.hungry_ubu_14);
                break;
            case 15:
                holder.diaryEmoji.setImageResource(R.drawable.cooking_ubu_15);
                break;
            case 16:
                holder.diaryEmoji.setImageResource(R.drawable.sorry_ubu_16);
                break;
            case 17:
                holder.diaryEmoji.setImageResource(R.drawable.shy_ubu_17);
                break;
            case 18:
                holder.diaryEmoji.setImageResource(R.drawable.sad_ubu_18);
                break;
            case 19:
                holder.diaryEmoji.setImageResource(R.drawable.exercise_ubu_19);
                break;
            case 20:
                holder.diaryEmoji.setImageResource(R.drawable.skateboard_ubu_20);
                break;
            case 21:
                holder.diaryEmoji.setImageResource(R.drawable.aerobics_ubu_21);
                break;
            case 22:
                holder.diaryEmoji.setImageResource(R.drawable.ringring_ubu_22);
                break;
            case 23:
                holder.diaryEmoji.setImageResource(R.drawable.watch_ubu_23);
                break;
            case 24:
                holder.diaryEmoji.setImageResource(R.drawable.angry_ubu_24);
                break;
            case 25:
                holder.diaryEmoji.setImageResource(R.drawable.congrats_ubu_25);
                break;
            case 26:
                holder.diaryEmoji.setImageResource(R.drawable.love_ubu_26);
                break;
            case 27:
                holder.diaryEmoji.setImageResource(R.drawable.gift_ubu_27);
                break;
            case 28:
                holder.diaryEmoji.setImageResource(R.drawable.wash_ubu_28);
                break;
            case 29:
                holder.diaryEmoji.setImageResource(R.drawable.brush_teeth_ubu_29);
                break;
            case 30:
                holder.diaryEmoji.setImageResource(R.drawable.tired_ubu_30);
                break;
            case 31:
                holder.diaryEmoji.setImageResource(R.drawable.phone_ubu_31);
                break;
        }

        holder.diaryContent.setText((dList.get(position).getdContent()) + "");
        try {
            holder.diaryPhoto.setImageURI(Uri.parse(dList.get(position).getdPhoto()));
        } catch (Exception e) {
            holder.diaryPhoto.setVisibility(GONE);
//            holder.dPhotoWarning.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return dList.size();
    }
}