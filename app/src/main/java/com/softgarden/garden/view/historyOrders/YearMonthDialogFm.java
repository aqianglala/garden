package com.softgarden.garden.view.historyOrders;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softgarden.garden.jiadun_android.R;

import java.util.Calendar;

/**
 * Created by qiang-pc on 2016/6/17.
 */
public class YearMonthDialogFm extends DialogFragment implements View.OnClickListener{

    private View rootView;
    private PickerListAdapter adapter;
    private LinearLayout ll_tv;
    private TextView tv_year;
    private TextView tv_month;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // 去掉对话框标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        rootView = inflater.inflate(R.layout.dialog_picker, container, false);
        // 设置顶部年月的背景颜色
        ll_tv = (LinearLayout) rootView.findViewById(R.id.ll_tv);
        if(color!=0){
            ll_tv.setBackgroundColor(color);
        }

        tv_year = (TextView) rootView.findViewById(R.id.tv_year);
        tv_month = (TextView) rootView.findViewById(R.id.tv_month);

        tv_year.setOnClickListener(this);
        tv_month.setOnClickListener(this);

        listView = (ListView) rootView.findViewById(R.id.listview);
        // 隐藏分割线
        listView.setDividerHeight(0);
        adapter = new PickerListAdapter(inflater);
        // 设置时间选择监听
        if(listener!=null){
            adapter.setOnItemSelectedListener(listener);
        }
        // 如果没有设置时间，则使用当前时间
        if(year==0 && month==0){
            Calendar instance = Calendar.getInstance();
            this.year = instance.get(Calendar.YEAR);
            this.month = instance.get(Calendar.MONTH);
        }
        // year，month是为了设置选中项的背景
        adapter.setTime(year,month);
        tv_month.setText(month +"月");
        tv_year.setText(year +"年");

        listView.setAdapter(adapter);
        listView.setSelection(year - 1900-1);
        // 更新title的颜色
        changeTvColor(false);
        return rootView;
    }
    public String darkWhite = "#ffffff";
    public String lightWhite = "#88ffffff";
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_year:
                // 修改颜色
                changeTvColor(false);
                adapter.setIsMonth(false);
                adapter.notifyDataSetChanged();
                listView.setSelection(year - 1900-1);
                break;
            case R.id.tv_month:
                // 修改颜色
                changeTvColor(true);
                adapter.setIsMonth(true);
                adapter.notifyDataSetChanged();
                listView.setSelection(month-2);
                break;
        }
    }

    private void changeTvColor(boolean isMonthClick) {
        tv_year.setTextColor(isMonthClick? Color.parseColor(lightWhite):Color.parseColor
            (darkWhite));
        tv_month.setTextColor(!isMonthClick?Color.parseColor(lightWhite):Color.parseColor
            (darkWhite));
    }

    /**
     * 用来修改对话框的大小
     */
    public void onResume() {
        super.onResume();
//        Window window = getDialog().getWindow();
//        window.setLayout(window.getAttributes().width, 800);//Here!
    }

    /**
     * 允许修改标题的背景颜色
     * @param color
     */
    private int color;
    public void setTitleBackgroundColor(int color){
        if(color!=0){
            this.color = color;
        }
    }

    private int year = 0;
    private int month = 0;
    public void setTime(int year, int month){
        this.year = year;
        this.month = month;
    }
    // 设置时间选择的监听
    PickerListAdapter.onTimePickListener listener;
    public void setOnItemSelectedListener(PickerListAdapter.onTimePickListener listener){
        this.listener = listener;
    }
}
