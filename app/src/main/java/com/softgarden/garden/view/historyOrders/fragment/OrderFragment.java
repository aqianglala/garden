package com.softgarden.garden.view.historyOrders.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.softgarden.garden.base.BaseApplication;
import com.softgarden.garden.base.BaseFragment;
import com.softgarden.garden.base.EngineFactory;
import com.softgarden.garden.base.ObjectCallBack;
import com.softgarden.garden.engine.HistoryOrderEngine;
import com.softgarden.garden.entity.HistoryOrderEntity;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.StringUtils;
import com.softgarden.garden.view.historyOrders.adapter.OrderExAdapter;
import com.softgarden.garden.view.historyOrders.widget.EventDecorator;
import com.softgarden.garden.view.historyOrders.widget.MySelectorDecorator;
import com.softgarden.garden.view.historyOrders.widget.OrderDecorator;
import com.softgarden.garden.view.start.activity.MainActivity;
import com.softgarden.garden.view.start.entity.MessageBean;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 先判断是否开启支付，如果未开启，则历史订单不显示付款状态，
 * 否则，判断用户是否是现金用户还是记账用户
 * 如果是记账用户，则不显示付款状态
 * 如果是现金用户，则需根据是否已经付款显示付款状态
 * 进入详情页之后，如果订单是未付款的，则显示一个支付的按钮
 * Created by Hasee on 2016/6/6.
 */
public class OrderFragment extends BaseFragment implements OnDateSelectedListener{

    private ImageView iv_me;
    private MainActivity mActivity;
    private List<HistoryOrderEntity.DataBean> mData = new ArrayList<>();
    private MaterialCalendarView widget;
    private ExpandableListView expandableListView;
    private OrderExAdapter myExAdapter;
    private HashMap<String, List<HistoryOrderEntity.DataBean>> map;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_orders);
        // register the receiver object
        EventBus.getDefault().register(this);

        iv_me = getViewById(R.id.iv_me);
        mActivity = (MainActivity)getActivity();

        expandableListView = getViewById(R.id.exListView);
        addFooter();
    }

    /**
     * 初始化日期view
     */
    private void addFooter() {
        View calenderLayout = LayoutInflater.from(mActivity).inflate(R.layout.calendarview, expandableListView,
                false);
        widget = (MaterialCalendarView) calenderLayout.findViewById(R.id.calendarView);
        // 设置中文
        String[] monthArr = {"一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"};
        widget.setTitleMonths(monthArr);
        String[] weekArr = {"周日","周一","周二","周三","周四","周五","周六"};
        widget.setWeekDayLabels(weekArr);

        // 设置点击事件
        calenderLayout.findViewById(R.id.view_choose).setOnClickListener(this);
        widget.setOnDateChangedListener(this);

        // 设置不能滑动
        widget.setPagingEnabled(false);
        // 设置默认点击效果
        widget.addDecorator(new MySelectorDecorator(mActivity));

        expandableListView.addFooterView(calenderLayout);
    }

    @Override
    protected void setListener() {
        iv_me.setOnClickListener(this);
        // 屏蔽组的点击事件
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition,
                                        long id) {
                return true;
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        getHistoryOrder();
    }

    private void getHistoryOrder() {
        HistoryOrderEngine engine = (HistoryOrderEngine) EngineFactory.getEngine(HistoryOrderEngine.class);
        engine.historyOrder(BaseApplication.userInfo.getData().getCustomerNo(), new
                ObjectCallBack<HistoryOrderEntity>(mActivity) {

            @Override
            public void onSuccess(HistoryOrderEntity data) {
                // 获取历史订单日期
                map = data.getData();

                ArrayList<CalendarDay> nowDates = new ArrayList<>();
                ArrayList<CalendarDay> oldDates = new ArrayList<>();
                boolean hasOrderOnCurrentDay = false;
                for(Map.Entry<String,List<HistoryOrderEntity.DataBean>> entry : map.entrySet()){
                    // 当前天
                    if(StringUtils.getCurrDay().equals(entry.getKey())){
                        hasOrderOnCurrentDay = true;
                        nowDates.add(StringUtils.stringToCalendarDay(entry.getKey()));
                        // 取出当天的订单显示
                        List<HistoryOrderEntity.DataBean> value = entry.getValue();
                        mData.clear();
                        mData.addAll(value);
                    }else{
                        oldDates.add(StringUtils.stringToCalendarDay(entry.getKey()));
                    }
                }
                myExAdapter = new OrderExAdapter(mData, mActivity);
                expandableListView.setAdapter(myExAdapter);
                // 默认展示收缩第一组
                expandableListView.collapseGroup(0);
                Drawable redDrawable = getResources().getDrawable(R.drawable.layer_red);
                widget.addDecorator(new EventDecorator(redDrawable,nowDates));
                Drawable greenDrawable = getResources().getDrawable(R.drawable.selector_calendar_order);
                widget.addDecorator(new OrderDecorator(greenDrawable, oldDates));
            }
        });
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
            case R.id.view_choose:
                // 弹出时间选择框
                showDialog();
                break;
        }
    }

    private int currentYear;
    private int currentMonth;
    public void showDialog(){
        // 弹出自定义年月选择框
        final YearMonthDialogFm dialogFm = new YearMonthDialogFm();
        // 更新对话框的时间
        dialogFm.setTime(currentYear,currentMonth);
        dialogFm.setTitleBackgroundColor(getResources().getColor(R.color.colorPrimary));
        dialogFm.setOnItemSelectedListener(new YearMonthDialogFm.onTimePickListener() {
            @Override
            public void onItemSelected(int time, boolean isMonth) {
                Calendar instance = Calendar.getInstance();
                if(isMonth){
                    currentMonth = time;
                }else{
                    currentYear = time;
                }
                instance.set(currentYear,currentMonth-2,1);
                CalendarDay day = CalendarDay.from(instance);
                widget.setCurrentDate(day,true);
                // 上面那个方法有时候不会更新显示的年月，因此，我把设置的时间往前一个月，然后手动往前一个月，这样就会更新标题了
                widget.goToNext();
                Toast.makeText(mActivity,(isMonth?"月份：":"年份：")+time,Toast.LENGTH_SHORT)
                        .show();
                dialogFm.dismiss();
            }
        });
        dialogFm.show(getFragmentManager(),"dialogFm");
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {
        Date date = calendarDay.getDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String seletedDate = format.format(date);
        boolean hasOrder = false;
        for(Map.Entry<String,List<HistoryOrderEntity.DataBean>> entry : map.entrySet()){
            if (seletedDate.equals(entry.getKey())){
                hasOrder = true;
                break;
            }
        }
        if(hasOrder){
            // 收缩列表,将数据更新在第一项
            mData.clear();
            mData.addAll(map.get(seletedDate));
            myExAdapter.groupingData();
            myExAdapter.setOpen(false);
            expandableListView.collapseGroup(0);
            myExAdapter.notifyDataSetChanged();
            expandableListView.setSelection(0);
        }else{
            // 当天没有订单
            showToast("当天没有订单!");
        }

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscriber(tag = "updateOrder")
    private void updateOrder(MessageBean user) {
        Log.e("", "### update user with my_tag, name = " + user.message);
        getHistoryOrder();
    }
}
