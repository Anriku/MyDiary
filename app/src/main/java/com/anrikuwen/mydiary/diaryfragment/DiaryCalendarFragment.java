package com.anrikuwen.mydiary.diaryfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anrikuwen.mydiary.R;

/**
 * Created by 10393 on 2017/2/9.
 */

public class DiaryCalendarFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.diary_calendar_fragment,null);
    }
}
