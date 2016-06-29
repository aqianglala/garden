package com.softgarden.garden.view.shopcar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.softgarden.garden.global.BaseActivity;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.view.historyOrders.adapter.DetailExAdapter;
import com.softgarden.garden.view.pay.PayActivity;

import java.util.ArrayList;

public class ConfirmOrderActivity extends BaseActivity {


    private TextView tv_address;
    private TextView tv_price;
    private Button btn_commit_order;
    private ArrayList<String> mData;
    private DetailExAdapter adapter;
    private ExpandableListView expandableListView;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_confirm_order);

        tv_address = getViewById(R.id.tv_address);
        tv_price = getViewById(R.id.tv_price);
        btn_commit_order = getViewById(R.id.btn_commit_order);

        virtualData();

        expandableListView = getViewById(R.id.exListView);
        adapter = new DetailExAdapter(mData, this);
        expandableListView.setAdapter(adapter);
        // 默认展示收缩第一组
        expandableListView.collapseGroup(2);
        // 屏蔽组的点击事件
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition,
                                        long id) {
                return true;
            }
        });

        addFooter();
    }

    private void addFooter() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_footer_order_list,
                expandableListView, false);
        expandableListView.addFooterView(view);
    }

    private void virtualData() {
        mData = new ArrayList<>();
        for(int i=0;i<20;i++){
            mData.add("面包"+(i+1));
        }
    }

    @Override
    protected void setListener() {
        btn_commit_order.setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_commit_order:
                // 跳转到支付页面
                startActivity(new Intent(this, PayActivity.class));
                break;
        }
    }
}
