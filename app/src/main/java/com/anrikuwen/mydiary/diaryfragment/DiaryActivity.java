package com.anrikuwen.mydiary.diaryfragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anrikuwen.mydiary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Diary版块的主Activity
 */
public class DiaryActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private DiaryProjectFragment projectFragment;
    private DiaryCalendarFragment calendarFragment;
    private DiaryDiaryFragment diaryFragment;
    public static ViewPager viewPager;
    public static DiaryFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        initView();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.diary_view_pager);
        tabLayout = (TabLayout) findViewById(R.id.diary_tab_layout);
        String[] titles = {"项目", "日历", "日记"};
        List<Fragment> fragments = new ArrayList<>();
        adapter = new DiaryFragmentAdapter(getSupportFragmentManager(),titles,fragments);
        projectFragment = new DiaryProjectFragment();
        fragments.add(projectFragment);
        calendarFragment = new DiaryCalendarFragment();
        fragments.add(calendarFragment);
        diaryFragment = new DiaryDiaryFragment();
        fragments.add(diaryFragment);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    /**
     * 此方法用于删除对应的日记后更新DiaryDiaryFragment的界面
     */
    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        viewPager.setAdapter(adapter);
    }
}
