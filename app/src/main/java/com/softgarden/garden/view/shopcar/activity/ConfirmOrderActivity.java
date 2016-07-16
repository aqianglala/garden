package com.softgarden.garden.view.shopcar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.softgarden.garden.base.BaseActivity;
import com.softgarden.garden.base.BaseApplication;
import com.softgarden.garden.base.BaseCallBack;
import com.softgarden.garden.entity.OrderCommitEntity;
import com.softgarden.garden.helper.HttpHelper;
import com.softgarden.garden.interfaces.UrlsAndKeys;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.other.ShoppingCart;
import com.softgarden.garden.utils.LogUtils;
import com.softgarden.garden.utils.ScreenUtils;
import com.softgarden.garden.view.pay.PayActivity;
import com.softgarden.garden.view.shopcar.CommitOrderDialog;
import com.softgarden.garden.view.shopcar.OverTimePromptDialog;
import com.softgarden.garden.view.shopcar.adapter.CartDetailExAdapter;
import com.softgarden.garden.view.start.entity.MessageBean;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.List;

public class ConfirmOrderActivity extends BaseActivity {


    private TextView tv_address;
    private TextView tv_price;
    private Button btn_commit_order;
    private List<OrderCommitEntity.ZstailBean> mData;
    private CartDetailExAdapter adapter;
    private ExpandableListView expandableListView;
    private EditText et_remarks;
    private OrderCommitEntity data;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_confirm_order);
        // register the receiver object
        EventBus.getDefault().register(this);

        tv_address = getViewById(R.id.tv_address);
        tv_price = getViewById(R.id.tv_price);
        btn_commit_order = getViewById(R.id.btn_commit_order);

        expandableListView = getViewById(R.id.exListView);

        addFooter();
    }

    private void addFooter() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_footer_order_list,
                expandableListView, false);
        et_remarks = (EditText) view.findViewById(R.id.et_remarks);
        expandableListView.addFooterView(view);
    }


    @Override
    protected void setListener() {
        btn_commit_order.setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        // 设置总价
        double total = ShoppingCart.getInstance().getTotal();
        // 设置地址
        tv_address.setText(BaseApplication.userInfo.getData().getShipadd());

        tv_price.setText(total+"");
        // 获取数据
        data = (OrderCommitEntity) getIntent().getSerializableExtra("data");
        mData = data.getZstail();
        adapter = new CartDetailExAdapter(mData, this);
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
    boolean inputAgain;
    public void setInputAgain(boolean inputAgain) {
        this.inputAgain = inputAgain;
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_commit_order:
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

    private void commit() {
        if(!BaseApplication.userInfo.getData().getJsfs().equals("记账")){
            commitOrder();
        }else if(BaseApplication.userInfo.getData().getJsfs().equals("现金")){
            startActivity(new Intent(this, PayActivity.class));
        }
    }

    private void commitOrder() {
        String remark = et_remarks.getText().toString().trim();
        data.setRemarks(remark);
        // 跳转到支付页面
        try {
            String s = new Gson().toJson(data);
            LogUtils.e(s);
            HttpHelper.post(UrlsAndKeys.order, new JSONObject(s), new BaseCallBack(context) {
                @Override
                public void onSuccess(JSONObject result) {
                    showToast("提交订单成功！");
                }

                @Override
                public void onError(JSONObject result, String message1, int code) {
                    showToast(message1);
                    if("时间超过了".equals(message1)){
                        // 需要验证时间,由后台判断
                        showOverTimeDialog();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showCommitDialog(String data) {
        CommitOrderDialog dialog = new CommitOrderDialog(this, R.style.CustomDialog,
                data);
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
