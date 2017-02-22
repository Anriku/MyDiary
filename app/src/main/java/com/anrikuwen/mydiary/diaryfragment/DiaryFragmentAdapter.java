package com.anrikuwen.mydiary.diaryfragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 10393 on 2017/2/9.
 */

public class DiaryFragmentAdapter extends FragmentPagerAdapter{

    private List<Fragment> fragments;

    public DiaryFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
