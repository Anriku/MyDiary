package com.anrikuwen.mydiary.diaryfragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anrikuwen.mydiary.BaseActivity;
import com.anrikuwen.mydiary.R;
import com.anrikuwen.mydiary.database.DiaryData;

import org.litepal.crud.DataSupport;

import java.util.List;

public class DiaryProjectRecItemActivity extends BaseActivity {
    private TextView monthText;
    private TextView dayText;
    private TextView weekDayText;
    private TextView timeText;
    private ImageView weatherImage;
    private ImageView moodImage;
    private TextView titleText;
    private TextView contentText;
    private DiaryData diaryData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_diary_rec_item);
        monthText = (TextView) findViewById(R.id.diary_project_rec_item_month);
        dayText = (TextView) findViewById(R.id.diary_project_rec_item_day);
        weekDayText = (TextView) findViewById(R.id.diary_project_rec_item_weekday);
        timeText = (TextView) findViewById(R.id.diary_project_rec_item_time);
        weatherImage = (ImageView) findViewById(R.id.diary_project_rec_item_weather);
        moodImage = (ImageView) findViewById(R.id.diary_project_rec_item_mood);
        titleText = (TextView) findViewById(R.id.diary_project_rec_item_title);
        contentText = (TextView) findViewById(R.id.diary_project_rec_item_content);
        diaryData = (DiaryData) getIntent().getSerializableExtra("DiaryItemData");
        setResource();
    }


    private void setResource() {
        monthText.setText(diaryData.getDiaryMonth());
        dayText.setText(diaryData.getDiaryDay());
        weekDayText.setText(diaryData.getDiaryWeekDay());
        timeText.setText(diaryData.getDiaryTime());
        titleText.setText(diaryData.getDiaryTitle());
        contentText.setText(diaryData.getDiaryContent());

        switch (diaryData.getWeather()){
            case "晴":
                weatherImage.setImageResource(R.mipmap.ic_weather_sunny);
                break;
            case "雨":
                weatherImage.setImageResource(R.mipmap.ic_weather_rainy);
                break;
            case "云":
                weatherImage.setImageResource(R.mipmap.ic_weather_cloud);
                break;
            case "风":
                weatherImage.setImageResource(R.mipmap.ic_weather_windy);
                break;
            case "雾":
                weatherImage.setImageResource(R.mipmap.ic_weather_foggy);
                break;
            case "雪":
                weatherImage.setImageResource(R.mipmap.ic_weather_snowy);
                break;
            default:
                break;
        }

        switch (diaryData.getMood()){
            case "高兴":
                moodImage.setImageResource(R.mipmap.ic_mood_happy);
                break;
            case "一般":
                moodImage.setImageResource(R.mipmap.ic_mood_soso);
                break;
            case "不高兴":
                moodImage.setImageResource(R.mipmap.ic_mood_unhappy);
                break;
            default:
                break;
        }
    }
}
