package com.anrikuwen.mydiary.diaryfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.anrikuwen.mydiary.R;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.diary_diary_fragment, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        weatherSpinner = (Spinner) view.findViewById(R.id.weather_spinner);
        moodSpinner = (Spinner) view.findViewById(R.id.mood_spinner);
        initWeather();
        initMood();
    }

    private void initWeather() {
        weatherImages = new int[]{R.mipmap.ic_weather_sunny, R.mipmap.ic_weather_cloud
                , R.mipmap.ic_weather_foggy
                , R.mipmap.ic_weather_rainy, R.mipmap.ic_weather_snowy
                , R.mipmap.ic_weather_windy};
        weatherAdapter = new DiarySpinnerAdapter(weatherImages, view.getContext());
        weatherSpinner.setAdapter(weatherAdapter);
    }


    private void initMood() {
        moodImages = new int[]{R.mipmap.ic_mood_happy, R.mipmap.ic_mood_soso
                , R.mipmap.ic_mood_unhappy};
        moodAdapter = new DiarySpinnerAdapter(moodImages, view.getContext());
        moodSpinner.setAdapter(moodAdapter);
    }

}
