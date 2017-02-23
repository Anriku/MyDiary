package com.anrikuwen.mydiary.diaryfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.anrikuwen.mydiary.R;
import com.anrikuwen.mydiary.database.DiaryData;
import com.anrikuwen.mydiary.mainactivity.MainActivity;

import org.litepal.tablemanager.Connector;

/**
 * Created by 10393 on 2017/2/9.
 */

public class DiaryDiaryFragment extends Fragment {

    private int[] weatherImages;
    private int[] moodImages;
    private DiarySpinnerAdapter weatherAdapter;
    private DiarySpinnerAdapter moodAdapter;
    private Spinner weatherSpinner;
    private Spinner moodSpinner;
    private View view;
    private DiaryData diaryData;
    private String weatherSelected;
    private String moodSelected;
    private EditText titleEdit;
    private EditText contentEdit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.diary_diary_fragment, null);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        weatherSpinner = (Spinner) view.findViewById(R.id.weather_spinner);
        moodSpinner = (Spinner) view.findViewById(R.id.mood_spinner);
        initWeather();
        initMood();

        titleEdit = (EditText) view.findViewById(R.id.diary_diary_title_edit);
        contentEdit = (EditText) view.findViewById(R.id.diary_diary_content_edit);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.diary_diary_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeData();
            }
        });
    }

    private void storeData() {
        String month = DiaryTime.getMonth();
        String day = DiaryTime.getDay();
        String weekDay = DiaryTime.getWeekDay();
        String time = DiaryTime.getTime();
        String year = DiaryTime.getYear();
        Connector.getDatabase();
        String title = String.valueOf(titleEdit.getText());
        String content = String.valueOf(contentEdit.getText());
        diaryData = new DiaryData();
        diaryData.setDiaryTitle(title);
        diaryData.setDiaryContent(content);
        diaryData.setWeather(weatherSelected);
        diaryData.setMood(moodSelected);
        diaryData.setDiaryMonth(month);
        diaryData.setDiaryDay(day);
        diaryData.setDiaryWeekDay(weekDay);
        diaryData.setDiaryYear(year);
        diaryData.setDiaryTime(time);
        diaryData.save();
        Toast.makeText(view.getContext(), "保存成功", Toast.LENGTH_SHORT).show();
        titleEdit.setText("");
        contentEdit.setText("");
        DiaryActivity.viewPager.setCurrentItem(0);
    }

    private void initWeather() {
        weatherImages = new int[]{R.mipmap.ic_weather_sunny, R.mipmap.ic_weather_cloud
                , R.mipmap.ic_weather_foggy
                , R.mipmap.ic_weather_rainy, R.mipmap.ic_weather_snowy
                , R.mipmap.ic_weather_windy};
        weatherAdapter = new DiarySpinnerAdapter(weatherImages, view.getContext());
        weatherSpinner.setAdapter(weatherAdapter);
        weatherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        weatherSelected = "晴";
                        break;
                    case 1:
                        weatherSelected = "云";
                        break;
                    case 2:
                        weatherSelected = "雾";
                        break;
                    case 3:
                        weatherSelected = "雨";
                        break;
                    case 4:
                        weatherSelected = "雪";
                        break;
                    case 5:
                        weatherSelected = "风";
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void initMood() {
        moodImages = new int[]{R.mipmap.ic_mood_happy, R.mipmap.ic_mood_soso
                , R.mipmap.ic_mood_unhappy};
        moodAdapter = new DiarySpinnerAdapter(moodImages, view.getContext());
        moodSpinner.setAdapter(moodAdapter);
        moodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        moodSelected = "高兴";
                        break;
                    case 1:
                        moodSelected = "一般";
                        break;
                    case 2:
                        moodSelected = "不高兴";
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
