package com.softgarden.garden.view.shopcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.softgarden.garden.global.BaseActivity;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.view.pay.PayActivity;

import java.util.ArrayList;

public class ConfirmOrderActivity extends BaseActivity {


    private TextView tv_address;
    private TextView tv_price;
    private Button btn_commit_order;
    private ListView listview;
    private ArrayList<String> mData;
    private ConfirmOrderAdapter adapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_confirm_order);

        tv_address = getViewById(R.id.tv_address);
        tv_price = getViewById(R.id.tv_price);
        btn_commit_order = getViewById(R.id.btn_commit_order);

        virtualData();
        listview = getViewById(R.id.listview);
        adapter = new ConfirmOrderAdapter(mData, this);
        listview.setAdapter(adapter);

        addFooter();
    }

    private void addFooter() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_footer_order_list,
                listview, false);
        listview.addFooterView(view);
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
