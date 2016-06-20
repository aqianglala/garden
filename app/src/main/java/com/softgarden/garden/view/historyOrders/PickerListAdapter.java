package com.softgarden.garden.view.historyOrders;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by qiang-pc on 2016/6/17.
 */
public class PickerListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<Integer>years = new ArrayList<>();
    private ArrayList<Integer>monthes = new ArrayList<>();
    private boolean isMonth;

    public void setIsMonth(boolean isMonth) {
        this.isMonth = isMonth;
    }

    public PickerListAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
        // 初始化数据
        for(int i = 1900;i<2100;i++){
            years.add(i);
        }
        for (int i = 1;i<=12;i++){
            monthes.add(i);
        }
    }

    @Override
    public int getCount() {
        if(isMonth){
            return monthes.size();
        }else{
            return years.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if(isMonth){
            return monthes.get(position);
        }else{
            return years.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_list, parent, false);
            holder.textView = (TextView) convertView.findViewById(R.id.textview);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        final int item = (int) getItem(position);
        holder.textView.setText(item+"");
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onItemSelected(item, isMonth);
                }
            }
        });

        if(!isMonth){// 年
            if(position == year-1900){
                holder.textView.setBackgroundResource(R.drawable.shape_item_selected);
            }else{
                holder.textView.setBackgroundColor(Color.TRANSPARENT);
            }
        }else{// 月
            if(position == month-1){
                holder.textView.setBackgroundResource(R.drawable.shape_item_selected);
            }else{
                holder.textView.setBackgroundColor(Color.TRANSPARENT);
            }
        }
        return convertView;
    }

    class ViewHolder{
        TextView textView;
    }

    private onTimePickListener listener;
    public interface onTimePickListener {
        void onItemSelected(int time, boolean isMonth);
    }

    public void setOnItemSelectedListener(onTimePickListener listener){
        this.listener = listener;
    }
    private int year;
    private int month;
    public void setTime(int year, int month){
        this.year = year;
        this.month = month;
    }

}
