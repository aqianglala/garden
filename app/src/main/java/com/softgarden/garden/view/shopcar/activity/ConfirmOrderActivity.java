package com.softgarden.garden.view.shopcar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.softgarden.garden.base.BaseActivity;
import com.softgarden.garden.base.BaseApplication;
import com.softgarden.garden.base.BaseCallBack;
import com.softgarden.garden.base.ObjectCallBack;
import com.softgarden.garden.dialog.CommitOrderDialog;
import com.softgarden.garden.dialog.OverTimeDialog;
import com.softgarden.garden.entity.CommitOrderResultEntity;
import com.softgarden.garden.entity.OrderCommitEntity;
import com.softgarden.garden.helper.HttpHelper;
import com.softgarden.garden.interfaces.UrlsAndKeys;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.other.ShoppingCart;
import com.softgarden.garden.utils.GlobalParams;
import com.softgarden.garden.utils.LogUtils;
import com.softgarden.garden.utils.ToastUtil;
import com.softgarden.garden.utils.Utils;
import com.softgarden.garden.view.historyOrders.OrderDetailActivity;
import com.softgarden.garden.view.pay.PayActivity;
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
    private OrderCommitEntity orderCommitEntity;

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
        et_remarks.setEnabled(true);
        et_remarks.setHint("请在这里添加备注（100字以内）");
        expandableListView.addFooterView(view);
    }


    @Override
    protected void setListener() {
        btn_commit_order.setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        // 设置总价
        float total = ShoppingCart.getInstance().getTotal();
        // 设置地址
        tv_address.setText(BaseApplication.userInfo.getData().getShipadd());
        tv_price.setText(Utils.formatFloat(total)+"");
        // 获取数据
        orderCommitEntity = (OrderCommitEntity) getIntent().getSerializableExtra("data");
        mData = orderCommitEntity.getZstail();
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
                String remark = et_remarks.getText().toString().trim();
                orderCommitEntity.setRemarks(remark);
                HttpHelper.post(UrlsAndKeys.payment, new JSONObject(), new BaseCallBack(this) {
                    @Override
                    public void onSuccess(JSONObject result) {
                        String data = result.optString("data");
                        if("1".equals(data)){// 开启支付,判断是否为记账还是现金，如果是记账则提交订单，否则进入支付页面
                            if(!inputAgain){
                                CommitOrderDialog.show(context,data);
                            }else{
                                commit();
                            }
                        }else if("0".equals(data)){// 关闭支付,都提交订单,跳转到订单详情页
                            if(!inputAgain){
                                CommitOrderDialog.show(context,data);
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
        if(BaseApplication.userInfo.getData().getJsfs().equals("记账")){
            commitOrder();
        }else if(BaseApplication.userInfo.getData().getJsfs().equals("现金")){
            Intent intent = new Intent(this, PayActivity.class);
            intent.putExtra("order",orderCommitEntity);
            startActivity(intent);
            finish();
        }
    }

    private void commitOrder() {
        try {
            String s = new Gson().toJson(orderCommitEntity);
            LogUtils.e(s);
            HttpHelper.post(UrlsAndKeys.order, new JSONObject(s), new ObjectCallBack<CommitOrderResultEntity>
                    (context) {
                @Override
                public void onSuccess(CommitOrderResultEntity data) {
                    BaseApplication.clearShopcart();
                    // 更新历史列表
                    EventBus.getDefault().post(new MessageBean("mr.simple"), "updateOrder");
                    ToastUtil.show("提交成功，请前往“历史订单”查看");
                    // 跳转到详情页,到时还需要传递数据过去
                    Intent intent = new Intent(context, OrderDetailActivity.class);
                    intent.putExtra(GlobalParams.ORDERNO,data.getData().getOrderNo());
                    intent.putExtra(GlobalParams.ORDERDATE,orderCommitEntity.getOrderDate());
                    intent.putExtra(GlobalParams.ORDERTYPE,"1");
                    intent.putExtra(GlobalParams.ORDERSTATE,"0");// 未付款
                    context.startActivity(intent);
                    finish();
                }

                @Override
                public void onError(JSONObject result, String message1, int code) {
                    showToast(message1);
                    if("时间超过了".equals(message1)){
                        // 需要验证时间,由后台判断
                        OverTimeDialog.show(context);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
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
