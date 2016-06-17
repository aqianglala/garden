package com.softgarden.garden.view.historyOrders;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.softgarden.garden.MainActivity;
import com.softgarden.garden.global.BaseFragment;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.view.shopcar.ShopcarActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Hasee on 2016/6/6.
 */
public class OrderFragment extends BaseFragment{

    private ImageView iv_me;
    private ImageView iv_shopcar;
    private MainActivity mActivity;
    private ListView lv_orders;
    private ArrayList<OrderBeanTest> mData;
    private OrderAdapter orderAdapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_orders);
        iv_me = getViewById(R.id.iv_me);
        iv_shopcar = getViewById(R.id.iv_shopCar);
        mActivity = (MainActivity)getActivity();

        getData();
        lv_orders = getViewById(R.id.lv_orders);

        addFooter();
        orderAdapter = new OrderAdapter(mData, mActivity);
        lv_orders.setAdapter(orderAdapter);
    }

    private void addFooter() {
        View calenderLayout = LayoutInflater.from(mActivity).inflate(R.layout.calendarview, lv_orders,
                false);
        MaterialCalendarView widget = (MaterialCalendarView) calenderLayout.findViewById(R.id.calendarView);
        // 模拟历史订单日期
        ArrayList<CalendarDay> oldDates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 3; i++) {
            CalendarDay day = CalendarDay.from(calendar);
            oldDates.add(day);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        Drawable drawable = getResources().getDrawable(R.drawable.layer_green);
        widget.addDecorator(new EventDecorator(drawable, oldDates));

        lv_orders.addFooterView(calenderLayout);
    }

    private void getData() {
        mData = new ArrayList<>();
        for(int i = 0;i<20;i++){
            OrderBeanTest orderBeanTest = new OrderBeanTest();
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.DAY_OF_MONTH,i);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            orderBeanTest.setDate(format.format(instance.getTime()));

            orderBeanTest.setNumber("2020202"+i);
            orderBeanTest.setAmount(550+i);
            orderBeanTest.setPrice(550+i);
            orderBeanTest.setBack("8.8%");
            mData.add(orderBeanTest);
        }
    }

    @Override
    protected void setListener() {
        iv_me.setOnClickListener(this);
        iv_shopcar.setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.iv_me:
                mActivity.toggle();
                break;
            case R.id.iv_shopCar:
                startActivity(new Intent(mActivity, ShopcarActivity.class));
                break;
        }
    }
}
