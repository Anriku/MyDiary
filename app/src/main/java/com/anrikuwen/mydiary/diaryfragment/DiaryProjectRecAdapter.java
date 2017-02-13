package com.anrikuwen.mydiary.diaryfragment;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anrikuwen.mydiary.R;
import com.anrikuwen.mydiary.database.DiaryData;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by 10393 on 2017/2/10.
 */

public class DiaryProjectRecAdapter extends RecyclerView.Adapter<DiaryProjectRecAdapter.ViewHolder> {

    private List<DiaryData> diaryDatas;
    private Context context;

    public DiaryProjectRecAdapter(Context context, List<DiaryData> diaryDatas) {
        this.context = context;
        this.diaryDatas = diaryDatas;
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

        holder.RecView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Dialog dialog = new Dialog(context);
                Window dialogWindow = dialog.getWindow();
                WindowManager.LayoutParams params = dialogWindow.getAttributes();
                params.gravity = Gravity.CENTER;
                dialogWindow.setAttributes(params);
                dialog.setContentView(R.layout.diary_project_rec_item_long_dialog);
                dialog.show();
                Button changeButton = (Button) dialog.findViewById(R.id.diary_project_rec_item_chang);
                Button deleteButton = (Button) dialog.findViewById(R.id.diary_project_rec_item_delete);
                changeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context,DiaryProjectChangeActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("DiaryItemData",diaryData);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                        dialog.cancel();
                    }
                });

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DataSupport.deleteAll(DiaryData.class,"diaryYear = ? and diaryMonth = ?" +
                                "and diaryDay = ? and diaryTime = ?",diaryData.getDiaryYear(),diaryData.getDiaryMonth()
                        ,diaryData.getDiaryDay(),diaryData.getDiaryTime());
                        Toast.makeText(context,"已删除",Toast.LENGTH_SHORT).show();

                        DiaryActivity.adapter.notifyDataSetChanged();
                        DiaryActivity.viewPager.setAdapter(DiaryActivity.adapter);
                        dialog.cancel();
                    }
                });
                return true;
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
