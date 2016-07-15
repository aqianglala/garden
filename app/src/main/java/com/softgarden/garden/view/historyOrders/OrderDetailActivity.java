package com.softgarden.garden.view.historyOrders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.softgarden.garden.base.BaseActivity;
import com.softgarden.garden.base.BaseCallBack;
import com.softgarden.garden.base.EngineFactory;
import com.softgarden.garden.base.ObjectCallBack;
import com.softgarden.garden.engine.HistoryOrderEngine;
import com.softgarden.garden.entity.HistoryDetailsEntity;
import com.softgarden.garden.entity.OrderEditEntity;
import com.softgarden.garden.helper.HttpHelper;
import com.softgarden.garden.interfaces.ModifyCountInterface;
import com.softgarden.garden.interfaces.UrlsAndKeys;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.GlobalParams;
import com.softgarden.garden.utils.LogUtils;
import com.softgarden.garden.view.shopcar.adapter.OrderDetailExAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class OrderDetailActivity extends BaseActivity implements ModifyCountInterface{


    private List<HistoryDetailsEntity.DataBean.ShopBean> mData;
    private OrderDetailExAdapter adapter;
    private Button btn_modify;
    private RelativeLayout rl_confirm;
    private ExpandableListView expandableListView;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_order_detail);

        btn_modify = getViewById(R.id.btn_modify);
        rl_confirm = getViewById(R.id.rl_confirm);

        expandableListView = getViewById(R.id.exListView);
        adapter = new OrderDetailExAdapter(mData, this);
        adapter.setModifyCountInterface(this);
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

    @Override
    protected void setListener() {
        btn_modify.setOnClickListener(this);
        getViewById(R.id.btn_cancel).setOnClickListener(this);
        getViewById(R.id.btn_confirm).setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        String orderNo = getIntent().getStringExtra(GlobalParams.ORDERNO);
        HistoryOrderEngine engine = (HistoryOrderEngine) EngineFactory.getEngine(HistoryOrderEngine.class);
        engine.historyDetails(orderNo, new ObjectCallBack<HistoryDetailsEntity>(this) {
            @Override
            public void onSuccess(HistoryDetailsEntity data) {
                // 为每一项计算总数和总价
                List<HistoryDetailsEntity.DataBean.ShopBean> shop = data.getData().getShop();
                for(HistoryDetailsEntity.DataBean.ShopBean item:shop){
                    int qty = Integer.parseInt(item.getQty());
                    int tgs = Integer.parseInt(item.getTgs());
                    item.setTotal(qty+tgs);
                }
                mData = shop ;
                adapter = new OrderDetailExAdapter(mData, context);
                adapter.setModifyCountInterface(OrderDetailActivity.this);
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
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_modify:
                rl_confirm.setVisibility(View.VISIBLE);
                btn_modify.setVisibility(View.GONE);
                adapter.setEditable(true);
                adapter.notifyDataSetChanged();
                break;
            case R.id.btn_cancel:
                rl_confirm.setVisibility(View.GONE);
                btn_modify.setVisibility(View.VISIBLE);
                adapter.setEditable(false);
                adapter.notifyDataSetChanged();
                break;
            case R.id.btn_confirm:
                // 重新提交订单
                // 获取订单日期和订单编号
                if(mData.size()>0){
                    String orderDate = mData.get(0).getOrderDate();
                    String orderNo = mData.get(0).getOrderNo();
                    OrderEditEntity orderEditEntity = new OrderEditEntity();
                    orderEditEntity.setOrderNo(orderNo);
                    orderEditEntity.setOrderDate(orderDate);
                    orderEditEntity.setZstail(mData);

                    try {
                        String s = new Gson().toJson(orderEditEntity);
                        LogUtils.e(s);
                        HttpHelper.post(UrlsAndKeys.orderEdit, new JSONObject(s), new BaseCallBack
                                (context) {
                            @Override
                            public void onSuccess(JSONObject result) {
                                showToast("提交订单成功！");
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private void addFooter() {
        View footer_remark = LayoutInflater.from(this).inflate(R.layout.layout_footer_order_list,
                expandableListView, false);
        expandableListView.addFooterView(footer_remark);
    }



    @Override
    public void doIncrease(TextView textView, int position, String currentCount) {
        HistoryDetailsEntity.DataBean.ShopBean shopBean = mData.get(position);
        int total = shopBean.getTotal();
        shopBean.setTotal(++total);
        textView.setText(total);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void doDecrease(TextView textView, int position, String currentCount) {
        HistoryDetailsEntity.DataBean.ShopBean shopBean = mData.get(position);
        int total = shopBean.getTotal();
        if(total>1){
            shopBean.setTotal(--total);
            textView.setText(total);
            adapter.notifyDataSetChanged();
        }
    }
}
