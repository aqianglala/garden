package com.softgarden.garden.view.historyOrders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softgarden.garden.jiadun_android.R;

import java.util.ArrayList;

/**
 * Created by qiang-pc on 2016/6/17.
 */
public class OrderAdapter extends BaseAdapter {

    private final int ITEM_ORDER = 0;
    private final int ITEM_STRETCH = 1;

    private LayoutInflater inflater;
    private ArrayList<OrderBeanTest>data;
    private ArrayList<OrderBeanTest>tempData = new ArrayList<>();
    private Context context;

    private boolean isOpen;

    public OrderAdapter(ArrayList<OrderBeanTest> data, Context context) {
        this.data = data;
        tempData.addAll(data);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        if(position == data.size()){// 如果是最后一项
            return ITEM_STRETCH;
        }else{
            return ITEM_ORDER;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        if(!isOpen){// 收缩
            data.clear();
            data.add(tempData.get(0));
        }else{// 展开
            data.clear();
            data.addAll(tempData);
        }
        return data.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemViewType = getItemViewType(position);
        HolderView holderView = null;
        if(convertView == null){
            holderView = new HolderView();
            switch (itemViewType){
                case ITEM_ORDER:
                    convertView = inflater.inflate(R.layout.item_list_orders,parent,false);
                    holderView.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
                    holderView.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
                    holderView.tv_amount = (TextView) convertView.findViewById(R.id.tv_amount);
                    holderView.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
                    holderView.tv_back = (TextView) convertView.findViewById(R.id.tv_back);
                    holderView.tv_detail = (TextView) convertView.findViewById(R.id.tv_detail);
                    break;
                case ITEM_STRETCH:
                    convertView = inflater.inflate(R.layout.item_more,parent,false);
                    holderView.ll_more = (LinearLayout) convertView.findViewById(R.id.ll_more);
                    holderView.imageView = (ImageView) convertView.findViewById(R.id.imageview);
                    break;
            }
            convertView.setTag(holderView);
        }else{
            holderView = (HolderView) convertView.getTag();
        }

        switch (itemViewType){
            case ITEM_ORDER:
                // 设置数据
                OrderBeanTest item = (OrderBeanTest) getItem(position);
                holderView.tv_date.setText(item.getDate());
                holderView.tv_number.setText(item.getNumber());
                holderView.tv_amount.setText(item.getAmount()+"");
                holderView.tv_price.setText("￥"+item.getPrice());
                holderView.tv_back.setText(item.getBack());
                holderView.tv_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 更新第一项数据，并收缩列表
                    }
                });
                break;
            case ITEM_STRETCH:
                if(isOpen){
                    holderView.imageView.setBackgroundResource(R.mipmap.ic_launcher);
                }else{
                    holderView.imageView.setBackgroundResource(R.mipmap.xiangxia);
                }
                holderView.ll_more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isOpen){// 收缩
                            data.clear();
                            data.add(tempData.get(0));
                            isOpen = !isOpen;
                            notifyDataSetChanged();
                        }else{// 展开
                            data.clear();
                            data.addAll(tempData);
                            isOpen = !isOpen;
                            notifyDataSetChanged();
                        }
                    }
                });
                break;
        }
        return convertView;
    }

    class HolderView{
        TextView tv_date;
        TextView tv_number;
        TextView tv_amount;
        TextView tv_price;
        TextView tv_back;
        TextView tv_detail;
        LinearLayout ll_more;
        ImageView imageView;
    }
}
