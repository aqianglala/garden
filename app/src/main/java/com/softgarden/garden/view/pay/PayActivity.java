package com.softgarden.garden.view.pay;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.softgarden.garden.base.BaseActivity;
import com.softgarden.garden.base.BaseApplication;
import com.softgarden.garden.base.EngineFactory;
import com.softgarden.garden.base.ObjectCallBack;
import com.softgarden.garden.dialog.OverTimeDialog;
import com.softgarden.garden.engine.HistoryOrderEngine;
import com.softgarden.garden.engine.PayResult;
import com.softgarden.garden.engine.ShopCartEngine;
import com.softgarden.garden.entity.CommitOrderResultEntity;
import com.softgarden.garden.entity.OrderCommitEntity;
import com.softgarden.garden.entity.PayEntity;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.other.ShoppingCart;
import com.softgarden.garden.utils.GlobalParams;
import com.softgarden.garden.utils.LogUtils;
import com.softgarden.garden.utils.Utils;
import com.softgarden.garden.view.historyOrders.OrderDetailActivity;
import com.softgarden.garden.view.start.entity.MessageBean;

import org.json.JSONObject;
import org.simple.eventbus.EventBus;

public class PayActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener{


    private LinearLayout ll_alipay;
    private LinearLayout ll_weixin;
    private LinearLayout ll_daofu;

    private CheckBox cb_alipay;
    private CheckBox cb_weixin;
    private CheckBox cb_daofu;
    private OrderCommitEntity data;

    private static final int SDK_PAY_FLAG = 1;
    private TextView tv_price;
    private TextView tv_total;
    private Button btn_commit;
    private String orderNo;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pay);
        ll_alipay = getViewById(R.id.ll_alipay);
        ll_weixin = getViewById(R.id.ll_weixin);
        ll_daofu = getViewById(R.id.ll_daofu);

        cb_alipay = getViewById(R.id.cb_alipay);
        cb_weixin = getViewById(R.id.cb_weixin);
        cb_daofu = getViewById(R.id.cb_daofu);

        tv_price = getViewById(R.id.tv_price);
        tv_total = getViewById(R.id.tv_total);

        btn_commit = getViewById(R.id.btn_commit);

        changeBackground(R.id.ll_alipay);
    }

    @Override
    protected void setListener() {
        ll_alipay.setOnClickListener(this);
        ll_weixin.setOnClickListener(this);
        ll_daofu.setOnClickListener(this);
        // 点击checkBox也能切换
        cb_alipay.setOnCheckedChangeListener(this);
        cb_weixin.setOnCheckedChangeListener(this);
        cb_daofu.setOnCheckedChangeListener(this);
        btn_commit.setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        data = (OrderCommitEntity) getIntent().getSerializableExtra("order");
        orderNo = getIntent().getStringExtra(GlobalParams.ORDERNO);
        float total;
        if (TextUtils.isEmpty(orderNo)){// 从购物车中点进来
            total = ShoppingCart.getInstance().getTotal();
        }else{// 从订单详情页点进来
            total = Float.parseFloat(getIntent().getStringExtra(GlobalParams.TOTALPRICE));
        }
        float f = Utils.formatFloat(total);
        tv_price.setText("¥"+f);
        tv_total.setText("合计：¥"+f);
    }
    private int leibie = 1;
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.ll_alipay:
                leibie = 1;
                changeBackground(R.id.ll_alipay);
                // 修改btn文字
                btn_commit.setText("确认订单");
                break;
            case R.id.ll_weixin:
                leibie = 2;
                changeBackground(R.id.ll_weixin);
                btn_commit.setText("确认订单");
                break;
            case R.id.ll_daofu:
                leibie = 3;
                changeBackground(R.id.ll_daofu);
                btn_commit.setText("确认");
                break;
            case R.id.btn_commit:
                if (leibie == 1){
                    if (TextUtils.isEmpty(orderNo)){// 从购物车进去
                        data.setLeibie(1);
                        ShopCartEngine engine = (ShopCartEngine) EngineFactory.getEngine(ShopCartEngine.class);
                        engine.onOrder(data, new ObjectCallBack<PayEntity>(this) {
                            @Override
                            public void onSuccess(final PayEntity data) {
                                // 取出订单号
                                orderNo = data.getData().getOrderNo();
                                EventBus.getDefault().post(new MessageBean("mr.simple"), "updateOrder");
                                pay(data);
                            }
                        });
                    }else{// 从订单详情来支付
                        HistoryOrderEngine engine = (HistoryOrderEngine) EngineFactory.getEngine(HistoryOrderEngine.class);
                        engine.pay(orderNo, leibie, new ObjectCallBack<PayEntity>(context) {
                            @Override
                            public void onSuccess(PayEntity data) {
                                EventBus.getDefault().post(new MessageBean("mr.simple"), "updateOrder");
                                pay(data);
                            }
                        });
                    }
                }else if (leibie == 3){
                    data.setLeibie(3);
                    ShopCartEngine engine = (ShopCartEngine) EngineFactory.getEngine(ShopCartEngine.class);
                    engine.dfOrder(data, new ObjectCallBack<CommitOrderResultEntity>(context) {
                        @Override
                        public void onSuccess(CommitOrderResultEntity entity) {
                            // 清空购物车
                            BaseApplication.clearShopcart();
                            // 更新历史列表
                            showToast("提交订单成功！");
                            EventBus.getDefault().post(new MessageBean("mr.simple"), "updateOrder");
                            // 跳转到详情页,到时还需要传递数据过去
                            Intent intent = new Intent(context, OrderDetailActivity.class);
                            LogUtils.e("put:"+entity.getData().getOrderNo());
                            intent.putExtra(GlobalParams.ORDERNO,entity.getData().getOrderNo());
                            intent.putExtra(GlobalParams.ORDERDATE,data.getOrderDate());
                            intent.putExtra(GlobalParams.ORDERTYPE,"1");
                            intent.putExtra(GlobalParams.ORDERSTATE,"2");
                            context.startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onError(JSONObject result, String message1, int code) {
                            showToast(message1);
                            // 清空购物车
                            BaseApplication.clearShopcart();
                            if("时间超过了".equals(message1)){
                                // 需要验证时间,由后台判断
                                OverTimeDialog.show(context);
                            }
                        }
                    });
                }
                break;
        }
    }

    private void pay(final PayEntity data) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(context);
                String param = data.getData().getParam();
                String result = alipay.pay(param,true);
                LogUtils.e(result);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
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
        ll_daofu.setBackgroundResource(resId != R.id.ll_daofu?R.drawable
                .shape_modify_order_normal:R.drawable.shape_modify_order_pressed);

        cb_alipay.setChecked(resId == R.id.ll_alipay?true:false);
        cb_weixin.setChecked(resId == R.id.ll_weixin?true:false);
        cb_daofu.setChecked(resId == R.id.ll_daofu?true:false);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.cb_alipay:
                if (isChecked)
                changeBackground(R.id.ll_alipay);
                break;
            case R.id.cb_weixin:
                if (isChecked)
                changeBackground(R.id.ll_weixin);
                break;
            case R.id.cb_daofu:
                if (isChecked)
                changeBackground(R.id.ll_daofu);
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
                        // 跳转到详情页,到时还需要传递数据过去
                        Intent intent = new Intent(context, OrderDetailActivity.class);
                        intent.putExtra(GlobalParams.ORDERNO,orderNo);
                        intent.putExtra(GlobalParams.ORDERDATE,data.getOrderDate());
                        intent.putExtra(GlobalParams.ORDERTYPE,"1");
                        intent.putExtra(GlobalParams.ORDERSTATE,"1");
                        context.startActivity(intent);
                        EventBus.getDefault().post(new MessageBean("mr.simple"), "updateOrderDetail");
                        finish();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(context, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    // 清空购物车
                    BaseApplication.clearShopcart();
                    break;
                }
                default:
                    break;
            }
        };
    };
}
