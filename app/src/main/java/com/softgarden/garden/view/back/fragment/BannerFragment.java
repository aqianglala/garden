package com.softgarden.garden.view.back.fragment;

import android.os.Bundle;
import android.widget.GridView;

import com.softgarden.garden.global.BaseFragment;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.view.back.adapter.BannerGridAdapter;

import java.util.ArrayList;

/**
 * Created by qiang-pc on 2016/6/21.
 */
public class BannerFragment extends BaseFragment {

    private GridView mGridView;
    private ArrayList<String> mData;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_banner);
        Bundle arguments = getArguments();
        mData = arguments.getStringArrayList("tags");
        mGridView = getViewById(R.id.gridView);
        BannerGridAdapter bannerGridAdapter = new BannerGridAdapter(mData, getActivity());
        mGridView.setAdapter(bannerGridAdapter);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void onUserVisible() {

    }
}
