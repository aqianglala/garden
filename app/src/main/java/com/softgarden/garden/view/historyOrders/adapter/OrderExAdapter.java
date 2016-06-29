package com.softgarden.garden.view.historyOrders.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.view.historyOrders.OrderDetailActivity;
import com.softgarden.garden.view.historyOrders.entity.OrderBeanTest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by qiang-pc on 2016/6/29.
 */
public class OrderExAdapter extends BaseExpandableListAdapter{
    private final LayoutInflater inflater;
    private ArrayList<OrderBeanTest> mData;
    private ArrayList<OrderBeanTest> groups;
    private ArrayList<OrderBeanTest> children;
    private Context context;
    private ExpandableListView exListView;

    public OrderExAdapter(ArrayList<OrderBeanTest> data, Context context) {
        this.mData = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
        groupingData();
    }

    private void groupingData() {
        // 分成两组，第一组的组布局为列表项布局，第二组为显示收缩的布局
        groups = new ArrayList<>();
        for(int i=0;i<2;i++){
            if(i ==0){
                groups.add(mData.get(i));
            }else{
                groups.add(null);
            }
        }
        children = new ArrayList<>();
        for(int i = 1;i<mData.size();i++){
            children.add(mData.get(i));
        }
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(groupPosition == 0){
            return children.size();
        }else{
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return children.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private boolean isOpen;
    @Override
    public View getGroupView(int groupPosition, final boolean isExpanded, View convertView, final ViewGroup
            parent) {
        if(groupPosition == 0){
            exListView =  (ExpandableListView) parent;
            convertView = inflater.inflate(R.layout.item_list_orders, parent, false);
            TextView tv_number = (TextView) convertView.findViewById(R.id.tv_number);
            OrderBeanTest item = (OrderBeanTest) getGroup(groupPosition);
            tv_number.setText(item.getNumber());

            TextView tv_detail = (TextView) convertView.findViewById(R.id.tv_detail);
            tv_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 跳转到详情页,到时还需要传递数据过去
                    Intent intent = new Intent(context, OrderDetailActivity.class);
                    context.startActivity(intent);
                }
            });
        }else{
            convertView = inflater.inflate(R.layout.item_more, parent, false);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
            if(isOpen){
                imageView.setBackgroundResource(R.mipmap.xiangxiashang);
            }else{
                imageView.setBackgroundResource(R.mipmap.xiangxia);
            }
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 展开就收缩，收缩就展开
                    if(isOpen){
                        // 收缩
                        exListView.collapseGroup(0);
                        // 向上移
                        exListView.setSelection(0);
                        isOpen = !isOpen;
                    }else{
                        exListView.expandGroup(0);
                        isOpen = !isOpen;
                    }
                }
            });
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View
            convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_list_orders, parent, false);
            holder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
            holder.tv_detail = (TextView) convertView.findViewById(R.id.tv_detail);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        OrderBeanTest item = children.get(childPosition);
        holder.tv_number.setText(item.getNumber());

        holder.tv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到详情页,到时还需要传递数据过去
                Intent intent = new Intent(context, OrderDetailActivity.class);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class ViewHolder{
        TextView tv_number;
        TextView tv_detail;
    }

    public void showDetail(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String seletedDate = format.format(date);
        for(int i=0;i<mData.size();i++){
            if(seletedDate.equals(mData.get(i).getDate())){
                // 跳出循环
                groups.clear();
                groups.add(mData.get(i));
                groups.add(null);
                break;
            }
        }
        isOpen = false;
        exListView.collapseGroup(0);
        notifyDataSetChanged();
    }
}
