package com.softgarden.garden.view.pay;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.softgarden.garden.global.BaseActivity;
import com.softgarden.garden.jiadun_android.R;

public class PayActivity extends BaseActivity {


    private LinearLayout ll_alipay;
    private LinearLayout ll_yinlian;
    private LinearLayout ll_weixin;
    private CheckBox cb_alipay;
    private CheckBox cb_weixin;
    private CheckBox cb_yinlian;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pay);
        ll_alipay = getViewById(R.id.ll_alipay);
        ll_weixin = getViewById(R.id.ll_weixin);
        ll_yinlian = getViewById(R.id.ll_yinlian);

        cb_alipay = getViewById(R.id.cb_alipay);
        cb_weixin = getViewById(R.id.cb_weixin);
        cb_yinlian = getViewById(R.id.cb_yinlian);

        changeBackground(R.id.ll_alipay);
    }

    @Override
    protected void setListener() {
        ll_alipay.setOnClickListener(this);
        ll_weixin.setOnClickListener(this);
        ll_yinlian.setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.ll_alipay:
                changeBackground(R.id.ll_alipay);
                break;
            case R.id.ll_weixin:
                changeBackground(R.id.ll_weixin);
                break;
            case R.id.ll_yinlian:
                changeBackground(R.id.ll_yinlian);
                break;
        }
    }

    // 把所有layout的背景置为normal
    // 把当前的背景设置pressed
    // 把所有checkbox置为false
    // 把当前置为true
    private void changeBackground(int resId) {
        ll_alipay.setBackgroundResource(resId != R.id.ll_alipay?R.drawable
                .shape_modify_order_normal:R.drawable.shape_modify_order_pressed);
        ll_weixin.setBackgroundResource(resId != R.id.ll_weixin?R.drawable
                .shape_modify_order_normal:R.drawable.shape_modify_order_pressed);
        ll_yinlian.setBackgroundResource(resId != R.id.ll_yinlian?R.drawable
                .shape_modify_order_normal:R.drawable.shape_modify_order_pressed);

        cb_alipay.setChecked(resId == R.id.ll_alipay?true:false);
        cb_weixin.setChecked(resId == R.id.ll_weixin?true:false);
        cb_yinlian.setChecked(resId == R.id.ll_yinlian?true:false);
    }
}
