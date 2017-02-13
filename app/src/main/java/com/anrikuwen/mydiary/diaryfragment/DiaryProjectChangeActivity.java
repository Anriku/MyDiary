package com.anrikuwen.mydiary.diaryfragment;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.anrikuwen.mydiary.R;
import com.anrikuwen.mydiary.database.DiaryData;

import org.litepal.crud.DataSupport;

public class DiaryProjectChangeActivity extends AppCompatActivity {

    private ImageView weatherImage;
    private ImageView moodImage;
    private EditText titleEdit;
    private EditText contentEdit;
    private DiaryData diaryData;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_project_change);
        weatherImage = (ImageView) findViewById(R.id.diary_project_rec_item_change_weather);
        moodImage = (ImageView) findViewById(R.id.diary_project_rec_item_change_mood);
        titleEdit = (EditText) findViewById(R.id.diary_project_rec_item_change_title);
        contentEdit = (EditText) findViewById(R.id.diary_project_rec_item_change_content);
        diaryData = (DiaryData) getIntent().getSerializableExtra("DiaryItemData");
        initView();

        floatingActionButton = (FloatingActionButton) findViewById(R.id.diary_project_rec_change_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaryData.setDiaryTitle(String.valueOf(titleEdit.getText()));
                diaryData.setDiaryContent(String.valueOf(contentEdit.getText()));
                diaryData.updateAll("diaryYear = ? and diaryMonth = ?" +
                        "and diaryDay = ? and diaryTime = ?",diaryData.getDiaryYear(),
                        diaryData.getDiaryMonth(),diaryData.getDiaryDay(),diaryData.getDiaryTime());
                Toast.makeText(DiaryProjectChangeActivity.this,"修改完成",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void initView() {

        titleEdit.setText(diaryData.getDiaryTitle());
        contentEdit.setText(diaryData.getDiaryContent());


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
