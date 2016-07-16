package com.softgarden.garden.view.historyOrders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.softgarden.garden.base.BaseActivity;
import com.softgarden.garden.base.BaseApplication;
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
import com.softgarden.garden.utils.ScreenUtils;
import com.softgarden.garden.view.pay.PayActivity;
import com.softgarden.garden.view.shopcar.CommitOrderDialog;
import com.softgarden.garden.view.shopcar.OverTimePromptDialog;
import com.softgarden.garden.view.shopcar.adapter.OrderDetailExAdapter;
import com.softgarden.garden.view.start.entity.MessageBean;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.List;

public class OrderDetailActivity extends BaseActivity implements ModifyCountInterface{


    private List<HistoryDetailsEntity.DataBean.ShopBean> mData;
    private OrderDetailExAdapter adapter;
    private Button btn_modify;
    private RelativeLayout rl_confirm;
    private ExpandableListView expandableListView;
    private TextView tv_orderNumb;
    private TextView tv_totalAmount;
    private TextView tv_price;
    private EditText et_remarks;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_order_detail);

        // register the receiver object
        EventBus.getDefault().register(this);

        btn_modify = getViewById(R.id.btn_modify);
        rl_confirm = getViewById(R.id.rl_confirm);

        tv_orderNumb = getViewById(R.id.tv_orderNumb);
        tv_totalAmount = getViewById(R.id.tv_totalAmount);
        tv_price = getViewById(R.id.tv_price);

        expandableListView = getViewById(R.id.exListView);
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
        tv_orderNumb.setText(orderNo);
        LogUtils.e("orderNo:"+orderNo);
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
                tv_totalAmount.setText(getTotalNum()+"");
                tv_price.setText(getTotalPrice()+"");
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

    private int getTotalNum() {
        int totalNum = 0;
        for(HistoryDetailsEntity.DataBean.ShopBean item:mData){
            totalNum+=item.getTotal();
        }
        return totalNum;
    }

    private double getTotalPrice() {
        double totalPrice = 0;
        for(HistoryDetailsEntity.DataBean.ShopBean item:mData){
            double price = item.getIsSpecial().equals("0")?Double.parseDouble
                    (item.getBzj()): Double.parseDouble( item
                    .getPrice());
            totalPrice+=item.getTotal()*price;
        }
        return totalPrice;
    }

    boolean inputAgain;
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
                HttpHelper.post(UrlsAndKeys.payment, new JSONObject(), new BaseCallBack(this) {
                    @Override
                    public void onSuccess(JSONObject result) {
                        String data = result.optString("data");
                        if("1".equals(data)){// 开启支付,判断是否为记账还是现金，如果是记账则提交订单，否则进入支付页面
                            if(!inputAgain){
                                showCommitDialog(data);
                            }else{
                                commit();
                            }
                        }else if("0".equals(data)){// 关闭支付,都提交订单
                            if(!inputAgain){
                                showCommitDialog(data);
                            }else{
                                commitOrder();
                            }
                        }
                    }
                });
                break;
        }
    }

    private void commitOrder() {
        if(mData.size()>0){
            String orderDate = mData.get(0).getOrderDate();
            String orderNo = mData.get(0).getOrderNo();
            String remarks = et_remarks.getText().toString().trim();
            OrderEditEntity orderEditEntity = new OrderEditEntity();
            orderEditEntity.setOrderNo(orderNo);
            orderEditEntity.setOrderDate(orderDate);
            orderEditEntity.setRemarks(remarks);
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

                    @Override
                    public void onError(JSONObject result, String message1, int code) {
                        showOverTimeDialog();
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void commit() {
        if(!BaseApplication.userInfo.getData().getJsfs().equals("记账")){
            commitOrder();
        }else if(BaseApplication.userInfo.getData().getJsfs().equals("现金")){
            startActivity(new Intent(this, PayActivity.class));
        }
    }

    public void setInputAgain(boolean inputAgain) {
        this.inputAgain = inputAgain;
    }

    private void showCommitDialog(String data) {
        CommitOrderDialog dialog = new CommitOrderDialog(this, R.style.CustomDialog,data);
        dialog.show();
        // 设置宽，高可在xml布局中写上,但宽度默认是match_parent，所以需要在代码中设置
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        attributes.width = (int) (ScreenUtils.getScreenWidth(this)*0.9);
        attributes.height =(int) (ScreenUtils.getScreenWidth(this)*0.9);
        dialog.getWindow().setAttributes(attributes);
    }

    private void showOverTimeDialog() {
        OverTimePromptDialog dialog = new OverTimePromptDialog(this, R.style.CustomDialog);
        dialog.show();
        // 设置宽，高可在xml布局中写上,但宽度默认是match_parent，所以需要在代码中设置
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        attributes.width = (int) (ScreenUtils.getScreenWidth(this)*0.8);
        attributes.height = ScreenUtils.getScreenWidth(this);
        dialog.getWindow().setAttributes(attributes);
    }

    private void addFooter() {
        View footer_remark = LayoutInflater.from(this).inflate(R.layout.layout_footer_order_list,
                expandableListView, false);
        et_remarks = (EditText) footer_remark.findViewById(R.id.et_remarks);
        expandableListView.addFooterView(footer_remark);
    }



    @Override
    public void doIncrease(TextView textView, int position, String currentCount) {
        int total = mData.get(position).getTotal();
        mData.get(position).setTotal(++total);
        adapter.groupingData();
        adapter.notifyDataSetChanged();
        tv_totalAmount.setText(getTotalNum()+"");
        tv_price.setText(getTotalPrice()+"");
    }

    @Override
    public void doDecrease(TextView textView, int position, String currentCount) {
        int total = mData.get(position).getTotal();
        if(total>1){
            mData.get(position).setTotal(--total);
            adapter.groupingData();
            adapter.notifyDataSetChanged();
            tv_totalAmount.setText(getTotalNum()+"");
            tv_price.setText(getTotalPrice()+"");
        }
    }

    @Subscriber(tag = "commitOrder")
    private void commitOrder(MessageBean user) {
        Log.e("", "### update user with my_tag, name = " + user.message);
        if(user.message.equals("0")){// 关闭支付，直接提交
            commitOrder();
        }else if(user.message.equals("1")){
            commit();
        }
        setInputAgain(true);
    }

    @Override
    protected void onDestroy() {
        // Don’t forget to unregister !!
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
