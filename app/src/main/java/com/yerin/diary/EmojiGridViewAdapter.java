package com.yerin.diary;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import static android.content.ContentValues.TAG;

public class EmojiGridViewAdapter extends BaseAdapter {
    private int[] emojiImages = { R.drawable.good_ubu_1, R.drawable.slaver_ubu_2, R.drawable.suprised_ubu_3,
            R.drawable.study_ubu_4, R.drawable.drawing_ubu_5, R.drawable.singing_ubu_6, R.drawable.yes_ubu_7,
            R.drawable.no_ubu_8, R.drawable.dont_know_ubu_9, R.drawable.music_ubu_10, R.drawable.fish_ubu_11,
            R.drawable.ubu_fish_12, R.drawable.muffin_ubu_13, R.drawable.hungry_ubu_14, R.drawable.cooking_ubu_15,
            R.drawable.sorry_ubu_16, R.drawable.shy_ubu_17, R.drawable.sad_ubu_18, R.drawable.exercise_ubu_19,
            R.drawable.skateboard_ubu_20, R.drawable.aerobics_ubu_21, R.drawable.ringring_ubu_22, R.drawable.watch_ubu_23,
            R.drawable.angry_ubu_24, R.drawable.congrats_ubu_25, R.drawable.love_ubu_26, R.drawable.gift_ubu_27,
            R.drawable.wash_ubu_28, R.drawable.brush_teeth_ubu_29, R.drawable.tired_ubu_30, R.drawable.phone_ubu_31,
            R.drawable.good_night_ubu_32
    };
    private Context eContext;
    private int eLayout;
    LayoutInflater inf;

    public int[] getEmojiImages() {
        return emojiImages;
    }

    public EmojiGridViewAdapter(Context c, int layout) {
        eContext = c;
        eLayout = layout;
        inf = (LayoutInflater) c.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return emojiImages.length;
    }

    @Override
    public Object getItem(int position) {
        return emojiImages[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null) convertView = inf.inflate(eLayout, null);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.emojiItem);
        imageView.setImageResource(emojiImages[position]);

        return imageView;
    }
}
