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
    private int[] emojiImages = { R.drawable.smile, R.drawable.soso, R.drawable.angry,
            R.drawable.happy, R.drawable.relax, R.drawable.sad };
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
