package com.softgarden.garden.view.back.fragment;

import android.os.Bundle;
import android.widget.GridView;

import com.softgarden.garden.base.BaseFragment;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.GlobalParams;
import com.softgarden.garden.view.back.adapter.BannerGridAdapter;
import com.softgarden.garden.view.back.interfaces.OnItemClickPositionListener;
import com.softgarden.garden.view.start.entity.MessageBean;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;

/**
 * Created by qiang-pc on 2016/6/21.
 */
public class BannerFragment extends BaseFragment {

    private GridView mGridView;
    private ArrayList<String> mData;
    private int groupIndex;
    private BannerGridAdapter bannerGridAdapter;
    private String itemclassName;
    private boolean isBack;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_banner);
        // register the receiver object
        EventBus.getDefault().register(this);

        Bundle arguments = getArguments();
        groupIndex = arguments.getInt("groupIndex");
        isBack = arguments.getBoolean("isBack");
        itemclassName = arguments.getString(GlobalParams.itemclassname);
        mData = arguments.getStringArrayList("tags");
        mGridView = getViewById(R.id.gridView);
        bannerGridAdapter = new BannerGridAdapter(groupIndex,mData, getActivity
                ());
        if(listener!=null){
            bannerGridAdapter.setOnItemClickPositionListener(listener);
        }
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
    private OnItemClickPositionListener listener;
    public void setOnItemClickPositionListener(OnItemClickPositionListener listener){
        this.listener = listener;
    }

    @Override
    public void onDestroyView() {
        // Don’t forget to unregister !!
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscriber(tag = "clickIndex")
    private void clickIndex(MessageBean user) {
        if(user.itemclassName.equals(itemclassName)&& user.isBack == isBack){
            int clickIndex = Integer.parseInt(user.message);
            bannerGridAdapter.setClickIndex(clickIndex);
            bannerGridAdapter.notifyDataSetChanged();
        }
    }
}
