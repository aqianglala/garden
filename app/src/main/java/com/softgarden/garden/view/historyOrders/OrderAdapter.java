package com.softgarden.garden.view.historyOrders;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softgarden.garden.jiadun_android.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    public View getView(int position, View convertView, final ViewGroup parent) {
        int itemViewType = getItemViewType(position);
        HolderView holderView = null;
        if(convertView == null){
            holderView = new HolderView();
            switch (itemViewType){
                case ITEM_ORDER:
                    convertView = inflater.inflate(R.layout.item_list_orders,parent,false);
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
                if(isCalendarClick){
                    if(selectedPosition !=-1){
                        data.clear();
                        data.add(tempData.get(selectedPosition));
                    }else{
                        data.clear();
                        OrderBeanTest orderBeanTest = new OrderBeanTest();
                        orderBeanTest.setBack("%0");
                        orderBeanTest.setPrice(0);
                        orderBeanTest.setAmount(0);
                        orderBeanTest.setNumber("0");
                        data.add(orderBeanTest);
                    }
                    selectedPosition = -1;
                    isCalendarClick = false;
                }
                OrderBeanTest item = data.get(position);
                holderView.tv_number.setText(item.getNumber());
                holderView.tv_amount.setText(item.getAmount()+"");
                holderView.tv_price.setText("￥"+item.getPrice());
                holderView.tv_back.setText(item.getBack());
                holderView.tv_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 跳转到详情页
                        context.startActivity(new Intent(context,OrderDetailActivity.class));
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
                            ((ListView)parent).setSelection(0);
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
    private int selectedPosition = -1;
    private boolean isCalendarClick;
    public void showDetail(Date date){
        isCalendarClick = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String seletedDate = format.format(date);
        for(int i=0;i<tempData.size();i++){
            if(seletedDate.equals(tempData.get(i).getDate())){
                selectedPosition = i;
                // 跳出循环
                break;
            }
        }
        isOpen = false;
        notifyDataSetChanged();
    }

    class HolderView{
        TextView tv_number;
        TextView tv_amount;
        TextView tv_price;
        TextView tv_back;
        TextView tv_detail;
        LinearLayout ll_more;
        ImageView imageView;
    }
}
