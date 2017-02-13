package com.anrikuwen.mydiary.diaryfragment;

import android.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anrikuwen.mydiary.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DiaryActivity extends AppCompatActivity {

    public static ViewPager viewPager;
    private TabLayout tabLayout;
    public static DiaryFragmentAdapter adapter;
    private DiaryProjectFragment projectFragment;
    private DiaryCalendarFragment calendarFragment;
    private DiaryDiaryFragment diaryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        initView();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.diary_view_pager);
        tabLayout = (TabLayout) findViewById(R.id.diary_tab_layout);
        adapter = new DiaryFragmentAdapter(getSupportFragmentManager());
        String[] titles = {"项目","日历","日记"};
        List<Fragment> fragments = new ArrayList<>();
        projectFragment = new DiaryProjectFragment();
        fragments.add(projectFragment);
        calendarFragment = new DiaryCalendarFragment();
        fragments.add(calendarFragment);
        diaryFragment = new DiaryDiaryFragment();
        fragments.add(diaryFragment);
        adapter.setTitles(titles);
        adapter.setFragments(fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        viewPager.setAdapter(adapter);
    }
}
