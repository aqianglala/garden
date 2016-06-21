package com.softgarden.garden.view.back;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.softgarden.garden.global.BaseFragment;

import java.util.ArrayList;

/**
 * Created by qiang-pc on 2016/6/21.
 */
public class ContainerPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<BaseFragment>fragments;
    public ContainerPagerAdapter(FragmentManager fm, ArrayList<BaseFragment>fragments) {
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
