package com.softgarden.garden.view.back.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.softgarden.garden.base.BaseActivity;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.view.back.adapter.BackDetailAdapter;

import java.util.ArrayList;

public class BackDetailActivity extends BaseActivity {


    private ArrayList<String> mData;
    private BackDetailAdapter adapter;
    private ListView listView;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_back_detail);
        initData();
        adapter = new BackDetailAdapter(this, R.layout.item_order_detail);
        listView = getViewById(R.id.listView);
        adapter.setDatas(mData);
        listView.setAdapter(adapter);
    }

    private void initData() {
        mData = new ArrayList<>();
        for(int i=1;i<=20;i++){
            mData.add("面包"+i);
        }
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
