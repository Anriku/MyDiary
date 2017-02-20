package com.anrikuwen.mydiary.diaryfragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.anrikuwen.mydiary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Diary版块的主Activity
 */
public class DiaryActivity extends AppCompatActivity {

    private DiaryProjectFragment projectFragment;
    private DiaryCalendarFragment calendarFragment;
    private DiaryDiaryFragment diaryFragment;
    public static ViewPager viewPager;
    public static DiaryFragmentAdapter adapter;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        initView();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.diary_view_pager);
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
        radioGroup = (RadioGroup) findViewById(R.id.diary_radio_group);
        radioGroup.check(R.id.diary_radio_button_0);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.diary_radio_button_0:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.diary_radio_button_1:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.diary_radio_button_2:
                        viewPager.setCurrentItem(2);
                        break;
                    default:
                        break;
                }
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        radioGroup.check(R.id.diary_radio_button_0);
                        break;
                    case 1:
                        radioGroup.check(R.id.diary_radio_button_1);
                        break;
                    case 2:
                        radioGroup.check(R.id.diary_radio_button_2);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
