package com.softgarden.garden.view.buy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.softgarden.garden.base.BaseFragment;

import java.util.ArrayList;

/**
 * Created by qiang-pc on 2016/6/15.
 */
public class MyPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<BaseFragment>fragments;
    public MyPagerAdapter(FragmentManager fm, ArrayList<BaseFragment>fragments) {
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
