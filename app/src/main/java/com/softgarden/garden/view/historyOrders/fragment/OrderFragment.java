package com.softgarden.garden.view.historyOrders.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.softgarden.garden.base.BaseActivity;
import com.softgarden.garden.base.BaseApplication;
import com.softgarden.garden.base.BaseFragment;
import com.softgarden.garden.base.ObjectCallBack;
import com.softgarden.garden.engine.HistoryOrderEngine;
import com.softgarden.garden.entity.HistoryOrderEntity;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.StringUtils;
import com.softgarden.garden.view.historyOrders.adapter.OrderExAdapter;
import com.softgarden.garden.view.historyOrders.widget.DisableDecorator;
import com.softgarden.garden.view.historyOrders.widget.MySelectorDecorator;
import com.softgarden.garden.view.historyOrders.widget.OrderDecorator;
import com.softgarden.garden.view.start.activity.MainActivity;
import com.softgarden.garden.view.start.entity.MessageBean;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

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
public class OrderFragment extends BaseFragment implements OnDateSelectedListener,SwipeRefreshLayout.OnRefreshListener {

    private ImageView iv_me;
    private MainActivity mActivity;
    private List<HistoryOrderEntity.DataBean> mData = new ArrayList<>();
    private MaterialCalendarView widget;
    private ExpandableListView expandableListView;
    private OrderExAdapter myExAdapter;
    private HashMap<String, List<HistoryOrderEntity.DataBean>> map;
    private MyObjectCallBack myObjectCallBack;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout layout_empty;
    private boolean isSwitchMonth;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_orders);
        // register the receiver object
        EventBus.getDefault().register(this);

        iv_me = getViewById(R.id.iv_me);
        mActivity = (MainActivity)getActivity();

        layout_empty = getViewById(R.id.layout_empty);

        swipeRefreshLayout = getViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);

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

        widget.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                isSwitchMonth = true;
                String date1 = formatDate(date);
                getHistoryOrder(date1);
            }
        });
        // 设置点击事件
        calenderLayout.findViewById(R.id.view_choose).setOnClickListener(this);
        widget.setOnDateChangedListener(this);

        // 设置不能滑动
        widget.setPagingEnabled(false);
        // 设置默认点击效果
        widget.addDecorator(new MySelectorDecorator(mActivity));

        expandableListView.addFooterView(calenderLayout);
    }

    /**
     * 如果当前没有选中日期，则取当月
     * 如果选中了日期，则取出日期的年月
     * @param date
     * @return
     */
    @NonNull
    private String formatDate(CalendarDay date) {
        String year;
        String month;
        if (date == null){
            CalendarDay today = CalendarDay.today();
            year = String.valueOf(today.getYear());
            month = String.valueOf(today.getMonth()+1);
        }else{
            month = String.valueOf(date.getMonth()+1);
            year = String.valueOf(date.getYear());
        }
        if (month.length() == 1){
            month =  "0"+month;
        }
        return year+"-"+month;
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
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        myObjectCallBack = new MyObjectCallBack(mActivity);
        swipeRefreshLayout.post(new Runnable(){
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        onRefresh();
    }

    private void getHistoryOrder(String date) {
        HistoryOrderEngine engine = new HistoryOrderEngine();
        String customerNo = BaseApplication.userInfo.getData().getCustomerNo();
        engine.historyOrder(customerNo,date, myObjectCallBack);
    }

    @Override
    protected void onUserVisible() {

    }

    /**
     * 下拉刷新，如果当前有选中日期，则刷新当前日期，如果没有，则刷新本月
     */
    @Override
    public void onRefresh() {
        CalendarDay selectedDate = widget.getSelectedDate();
        String s = formatDate(selectedDate);
        isSwitchMonth = false;
        getHistoryOrder(s);
    }

    class MyObjectCallBack extends ObjectCallBack<HistoryOrderEntity>{

        public MyObjectCallBack(BaseActivity activity) {
            super(activity,false);
        }

        @Override
        public void onSuccess(HistoryOrderEntity data) {
            // 获取历史订单日期
            map = data.getData();
            ArrayList<CalendarDay> nowDates = new ArrayList<>();
            ArrayList<CalendarDay> oldDates = new ArrayList<>();
            for(Map.Entry<String,List<HistoryOrderEntity.DataBean>> entry : map.entrySet()){
                // 当前天有订单
                if(StringUtils.getCurrDay().equals(entry.getKey())){
                    nowDates.add(StringUtils.stringToCalendarDay(entry.getKey()));
                }else{
                    oldDates.add(StringUtils.stringToCalendarDay(entry.getKey()));
                }
            }
            // 标记
            addDecorators(nowDates, oldDates);
            // 刷新当前天的订单
            if (isSwitchMonth){// 如果是切换月份，则订单不刷新
                return;
            }
            CalendarDay calendarDay = widget.getSelectedDate();
            if (calendarDay == null) {
                calendarDay = CalendarDay.today();
            }
            Date date = calendarDay.getDate();
            String seletedDate = StringUtils.formatDate(date);
            // 收缩列表,将数据更新在第一项
            mData.clear();
            List<HistoryOrderEntity.DataBean> list = map.get(seletedDate);
            if (list !=null && list.size()>0) {
                mData.addAll(list);
            }
            if (myExAdapter == null){
                myExAdapter = new OrderExAdapter(mData, mActivity);
                expandableListView.setAdapter(myExAdapter);
            }else{
                myExAdapter.groupingData();
                myExAdapter.setOpen(false);
                myExAdapter.notifyDataSetChanged();
                expandableListView.setSelection(0);
            }
            // 默认展示收缩第一组
            expandableListView.collapseGroup(0);
            // false，刷新完成，因此停止UI的刷新表现样式。
            swipeRefreshLayout.setRefreshing(false);
        }

        @Override
        public void onError(String s, String s1, int i) {
            super.onError(s, s1, i);

            expandableListView.setEmptyView(layout_empty);
            // false，刷新完成，因此停止UI的刷新表现样式。
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void addDecorators(ArrayList<CalendarDay> nowDates, ArrayList<CalendarDay> oldDates) {
        // 清除颜色标记
        widget.removeDecorators();
        ArrayList<CalendarDay> calendarDays = new ArrayList<>();
        calendarDays.addAll(nowDates);
        calendarDays.addAll(oldDates);
        Drawable disableDrawable = getResources().getDrawable(R.drawable
                .selector_calendar_order_disable);
        // 设置日期的文字样式，如果不设置，则会有点击时日期的文字变白色的效果
        widget.setDateTextAppearance(android.R.attr.dateTextAppearance);
        widget.addDecorator(new DisableDecorator(disableDrawable, calendarDays));
        Drawable redDrawable = getResources().getDrawable(R.drawable.selector_calendar_order_current);
        widget.addDecorator(new OrderDecorator(redDrawable,nowDates));
        Drawable greenDrawable = getResources().getDrawable(R.drawable.selector_calendar_order);
        widget.addDecorator(new OrderDecorator(greenDrawable, oldDates));
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
            public void onItemSelected(int year, int month, int day) {
                currentYear = year;
                currentMonth = month;
                Calendar instance = Calendar.getInstance();
                instance.set(year,month-2,day);
                CalendarDay currentDate = CalendarDay.from(instance);
                widget.setCurrentDate(currentDate,true);
                // 上面那个方法有时候不会更新显示的年月，因此，我把设置的时间往前一个月，然后手动往前一个月，这样就会更新标题了
                widget.goToNext();
                dialogFm.dismiss();
            }

        });
        dialogFm.show(getFragmentManager(),"dialogFm");
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {
        Date date = calendarDay.getDate();
        String seletedDate = StringUtils.formatDate(date);
        List<HistoryOrderEntity.DataBean> dataBeen = map.get(seletedDate);
        if (dataBeen != null){
            // 收缩列表,将数据更新在第一项
            mData.clear();
            mData.addAll(dataBeen);
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
        CalendarDay selectedDate = widget.getSelectedDate();
        String s = formatDate(selectedDate);
        isSwitchMonth = false;
        getHistoryOrder(s);
    }
}
