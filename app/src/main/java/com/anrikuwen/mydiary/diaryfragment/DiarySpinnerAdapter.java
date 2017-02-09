package com.anrikuwen.mydiary.diaryfragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.anrikuwen.mydiary.R;
/**
 * Created by 10393 on 2017/2/9.
 */

public class DiarySpinnerAdapter extends BaseAdapter {

    private int[] imagesResource;
    private Context context;

    public DiarySpinnerAdapter(int[] imagesResource, Context context) {
        this.imagesResource = imagesResource;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imagesResource.length;
    }

    @Override
    public Object getItem(int position) {
        return imagesResource[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.diary_spinner_item,null);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.diary_spinner_image);
        imageView.setImageResource(imagesResource[position]);
        return convertView;
    }
}
