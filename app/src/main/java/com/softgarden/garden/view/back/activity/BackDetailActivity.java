package com.softgarden.garden.view.back.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.softgarden.garden.base.BaseActivity;
import com.softgarden.garden.entity.IndexEntity;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.view.back.adapter.BackDetailAdapter;

import java.util.ArrayList;

public class BackDetailActivity extends BaseActivity {


    private BackDetailAdapter adapter;
    private ListView listView;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_back_detail);

        ArrayList<IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean> detail = (ArrayList
                <IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean>) getIntent().getSerializableExtra("detail");

        // 计算总数量
        int total = 0;
        for(IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean item: detail){
            total+=item.getQty();
        }
        TextView tv_totalAmount = (TextView) findViewById(R.id.tv_totalAmount);
        tv_totalAmount.setText(total+"");
        adapter = new BackDetailAdapter(this, R.layout.item_order_detail);
        listView = getViewById(R.id.listView);
        adapter.setDatas(detail);
        listView.setAdapter(adapter);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        String title = getIntent().getStringExtra("title");
        ((TextView)getViewById(R.id.tv_title)).setText(title);
    }
}
