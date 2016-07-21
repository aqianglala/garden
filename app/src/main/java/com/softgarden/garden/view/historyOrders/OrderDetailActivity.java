package com.softgarden.garden.view.historyOrders;

import android.graphics.Color;
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

import com.softgarden.garden.base.BaseActivity;
import com.softgarden.garden.base.BaseApplication;
import com.softgarden.garden.base.BaseCallBack;
import com.softgarden.garden.base.EngineFactory;
import com.softgarden.garden.base.ObjectCallBack;
import com.softgarden.garden.dialog.OverTimeDialog;
import com.softgarden.garden.engine.HistoryOrderEngine;
import com.softgarden.garden.entity.HistoryDetailsEntity;
import com.softgarden.garden.entity.OrderEditEntity;
import com.softgarden.garden.interfaces.ModifyCountInterface;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.GlobalParams;
import com.softgarden.garden.utils.LogUtils;
import com.softgarden.garden.utils.ScreenUtils;
import com.softgarden.garden.utils.StringUtils;
import com.softgarden.garden.view.shopcar.CommitOrderDialog;
import com.softgarden.garden.view.shopcar.adapter.OrderDetailExAdapter;
import com.softgarden.garden.view.start.entity.MessageBean;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrderDetailActivity extends BaseActivity implements ModifyCountInterface,DatePickerDialog.OnDateSetListener{


    private List<HistoryDetailsEntity.DataBean.ShopBean> mData;
    private OrderDetailExAdapter adapter;
    private Button btn_pay;

    private RelativeLayout rl_confirm;
    private ExpandableListView expandableListView;
    private TextView tv_orderNumb;
    private TextView tv_totalAmount;
    private TextView tv_price;
    private EditText et_remarks;
    private String orderNo;
    private String orderDate;

    private RelativeLayout rl_date;
    private TextView tv_date;
    private TextView tv_right;
    private int state;
    private String zffs;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_order_detail);

        // register the receiver object
        EventBus.getDefault().register(this);

        tv_right = getViewById(R.id.tv_right);
        tv_right.setVisibility(View.VISIBLE);

        btn_pay = getViewById(R.id.btn_pay);
        rl_confirm = getViewById(R.id.rl_confirm);

        tv_orderNumb = getViewById(R.id.tv_orderNumb);
        tv_totalAmount = getViewById(R.id.tv_totalAmount);
        tv_price = getViewById(R.id.tv_price);

        rl_date = getViewById(R.id.rl_date);
        tv_date = getViewById(R.id.tv_date);

        expandableListView = getViewById(R.id.exListView);
        addFooter();
    }

    @Override
    protected void setListener() {
        tv_right.setOnClickListener(this);
        rl_date.setOnClickListener(this);
        btn_pay.setOnClickListener(this);
        getViewById(R.id.btn_cancel).setOnClickListener(this);
        getViewById(R.id.btn_confirm).setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        state = getIntent().getIntExtra(GlobalParams.ORDERSTATE, -1);
        orderNo = getIntent().getStringExtra(GlobalParams.ORDERNO);
        orderDate = getIntent().getStringExtra(GlobalParams.ORDERDATE);
        // 设置底部界面
        initBottomUI();
        initTimePicker();
        tv_orderNumb.setText(orderNo);
        LogUtils.e("orderNo:"+ orderNo);
        HistoryOrderEngine engine = (HistoryOrderEngine) EngineFactory.getEngine(HistoryOrderEngine.class);
        engine.historyDetails(orderNo, new ObjectCallBack<HistoryDetailsEntity>(this) {
            @Override
            public void onSuccess(HistoryDetailsEntity data) {
                // 设置备注
                et_remarks.setText(data.getData().getHead().getRemarks());
                zffs = data.getData().getHead().getZffs();
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

    private void initBottomUI() {
        if ("1".equals(BaseApplication.indexEntity.getData().getZhifu()) &&"现金".equals(
                BaseApplication.userInfo.getData().getJsfs())){
            if (state == 0){// 未付款
                btn_pay.setVisibility(View.VISIBLE);
                rl_confirm.setVisibility(View.GONE);
            }else{
                btn_pay.setVisibility(View.GONE);
                rl_confirm.setVisibility(View.VISIBLE);
            }
        }else{
            btn_pay.setVisibility(View.GONE);
            rl_confirm.setVisibility(View.VISIBLE);
        }
    }

    private DatePickerDialog dpd;
    private void initTimePicker() {
        Calendar now = Calendar.getInstance();
        Date dateFromStr = StringUtils.getDateFromStr(orderDate);
        now.setTime(dateFromStr);
        tv_date.setText(StringUtils.formatDate(now.getTime()));
        dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setThemeDark(false);
        dpd.vibrate(false);//设置是否震动
        dpd.dismissOnPause(true);//activity暂停的时候是否销毁对话框
        dpd.showYearPickerFirst(false);//先展示年份选择
        dpd.setAccentColor(Color.parseColor("#E6003E"));
        dpd.setMinDate(now);
    }

    private int getTotalNum() {
        int totalNum = 0;
        for(HistoryDetailsEntity.DataBean.ShopBean item:mData){
            totalNum+=item.getTotal();
        }
        return totalNum;
    }

    private float getTotalPrice() {
        float totalPrice = 0;
        for(HistoryDetailsEntity.DataBean.ShopBean item:mData){
            float price = item.getIsSpecial().equals("0")?Float.parseFloat
                    (item.getBzj()): Float.parseFloat( item
                    .getPrice());
            totalPrice+=item.getTotal()*price;
        }
        BigDecimal b = new BigDecimal(totalPrice);
        float f1 =  b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        return f1;
    }

    boolean inputAgain;
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_right:
                rl_confirm.setVisibility(View.VISIBLE);
                btn_pay.setVisibility(View.GONE);
                adapter.setEditable(true);
                adapter.notifyDataSetChanged();
                break;
            case R.id.rl_date:
                dpd.show(getFragmentManager(), "Datepickerdialog");
                break;
            case R.id.btn_cancel:
                rl_confirm.setVisibility(View.GONE);
                btn_pay.setVisibility(View.VISIBLE);
                adapter.setEditable(false);
                adapter.notifyDataSetChanged();
                break;
            case R.id.btn_pay:  // 支付

                break;
            case R.id.btn_confirm:
                // 重新提交订单
                // 获取订单日期和订单编号
                if(!inputAgain){
                    showCommitDialog("0");
                }else{
                    commitOrder();
                }
                break;
        }
    }

    private void commitOrder() {
        if(mData.size()>0){
            String remarks = et_remarks.getText().toString().trim();
            OrderEditEntity orderEditEntity = new OrderEditEntity();
            orderEditEntity.setOrderNo(orderNo);
            orderEditEntity.setOrderDate(tv_date.getText().toString());
            orderEditEntity.setRemarks(remarks);
            orderEditEntity.setZffs(Integer.parseInt(zffs));
            orderEditEntity.setZstail(mData);

            HistoryOrderEngine engine = (HistoryOrderEngine) EngineFactory.getEngine(HistoryOrderEngine.class);
            engine.orderEdit(orderEditEntity,new BaseCallBack(context) {
                @Override
                public void onSuccess(JSONObject result) {
                    // 更新历史列表
                    showToast("提交订单成功！");
                    EventBus.getDefault().post(new MessageBean("mr.simple"), "updateOrder");
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
        }
        setInputAgain(true);
    }

    @Override
    protected void onDestroy() {
        // Don’t forget to unregister !!
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar instance = Calendar.getInstance();
        instance.set(year,monthOfYear,dayOfMonth);
        String formatDate = StringUtils.formatDate(instance.getTime());
        tv_date.setText(formatDate);
    }
}
