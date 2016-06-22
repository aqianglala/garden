package com.softgarden.garden.view.back;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.ListView;

import com.softgarden.garden.global.BaseFragment;
import com.softgarden.garden.jiadun_android.R;

import java.util.ArrayList;

/**
 * Created by qiang on 2016/6/14.
 */
public class BreadCakeFragment extends BaseFragment {

    private boolean isBread;
    private ViewPager vp_banner;
    private ViewPager vp_content;
    private ListView lv_content;

    private ArrayList<String> mData;
    private CheckProductAdapter adapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_bread_cake);
        Bundle arguments = getArguments();
        boolean isBack = arguments.getBoolean("isBack");
        Button btn_confirm = getViewById(R.id.btn_confirm);
        btn_confirm.setText(isBack?"确认退货":"确认换货");

        virtualData();

        vp_banner = getViewById(R.id.vp_banner);
        vp_content = getViewById(R.id.vp_content);

        ArrayList<BaseFragment> fragments = new ArrayList<>();
        BannerFragment bannerFragment1 = new BannerFragment();
        BannerFragment bannerFragment2 = new BannerFragment();
        fragments.add(bannerFragment1);
        fragments.add(bannerFragment2);

        BannerPagerAdapter bannerPagerAdapter = new BannerPagerAdapter(getChildFragmentManager(),
                fragments);
        vp_banner.setAdapter(bannerPagerAdapter);

        lv_content = getViewById(R.id.lv_content);
        adapter = new CheckProductAdapter(mActivity, R.layout.item_list_check_content);
        adapter.setDatas(mData);
        lv_content.setAdapter(adapter);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        isBread = getArguments().getBoolean("isBread");
    }

    @Override
    protected void onUserVisible() {

    }

    private void virtualData() {
        mData = new ArrayList<>();
        for(int i=0;i<20;i++){
            mData.add("面包"+(i+1));
        }
    }
}
