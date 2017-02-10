package com.anrikuwen.mydiary.diaryfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anrikuwen.mydiary.R;
import com.anrikuwen.mydiary.database.DiaryData;

import java.util.List;

/**
 * Created by 10393 on 2017/2/10.
 */

public class DiaryProjectRecAdapter extends RecyclerView.Adapter<DiaryProjectRecAdapter.ViewHolder> {

    private List<DiaryData> diaryDatas;
    private Context context;

    public DiaryProjectRecAdapter(List<DiaryData> diaryDatas, Context context) {
        this.diaryDatas = diaryDatas;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.diary_project_recy_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DiaryData diaryData = diaryDatas.get(position);

        holder.yearText.setText(diaryData.getDiaryYear());
        holder.monthText.setText(diaryData.getDiaryMonth());
        holder.dayText.setText(diaryData.getDiaryDay());
        holder.weekDayText.setText(diaryData.getDiaryWeekDay());
        holder.timeText.setText(diaryData.getDiaryTime());
        holder.titleText.setText(diaryData.getDiaryTitle());

        holder.RecView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DiaryProjectRecItemActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("DiaryItemData",diaryData);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        switch (diaryData.getWeather()){
            case "晴":
                holder.weatherImage.setImageResource(R.mipmap.ic_weather_sunny);
                break;
            case "雨":
                holder.weatherImage.setImageResource(R.mipmap.ic_weather_rainy);
                break;
            case "云":
                holder.weatherImage.setImageResource(R.mipmap.ic_weather_cloud);
                break;
            case "风":
                holder.weatherImage.setImageResource(R.mipmap.ic_weather_windy);
                break;
            case "雾":
                holder.weatherImage.setImageResource(R.mipmap.ic_weather_foggy);
                break;
            case "雪":
                holder.weatherImage.setImageResource(R.mipmap.ic_weather_snowy);
                break;
            default:
                break;
        }

        switch (diaryData.getMood()){
            case "高兴":
                holder.moodImage.setImageResource(R.mipmap.ic_mood_happy);
                break;
            case "一般":
                holder.moodImage.setImageResource(R.mipmap.ic_mood_soso);
                break;
            case "不高兴":
                holder.moodImage.setImageResource(R.mipmap.ic_mood_unhappy);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return diaryDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView yearText;
        TextView monthText;
        TextView dayText;
        TextView weekDayText;
        TextView timeText;
        TextView titleText;
        View RecView;

        ImageView weatherImage;
        ImageView moodImage;
        public ViewHolder(View itemView) {
            super(itemView);
            RecView = itemView;
            yearText = (TextView) itemView.findViewById(R.id.diary_project_rec_year);
            monthText = (TextView) itemView.findViewById(R.id.diary_project_rec_month);
            dayText = (TextView) itemView.findViewById(R.id.diary_project_rec_day);
            weekDayText = (TextView) itemView.findViewById(R.id.diary_project_rec_week_day);
            timeText = (TextView) itemView.findViewById(R.id.diary_project_rec_time);
            titleText = (TextView) itemView.findViewById(R.id.diary_project_rec_title);
            weatherImage = (ImageView) itemView.findViewById(R.id.diary_project_rec_weather);
            moodImage = (ImageView) itemView.findViewById(R.id.diary_project_rec_mood);
        }
    }
}
