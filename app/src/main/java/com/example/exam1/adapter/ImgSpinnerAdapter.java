package com.example.exam1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.exam1.R;

public class ImgSpinnerAdapter extends BaseAdapter {

    private int[] listImg;
    private Context context;

    public ImgSpinnerAdapter(int[] listImg, Context context) {
        this.listImg = listImg;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listImg.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.img_holder, parent, false);
        ImageView img = view.findViewById(R.id.img_holder);
        img.setImageResource(listImg[position]);
        return view;
    }
}
