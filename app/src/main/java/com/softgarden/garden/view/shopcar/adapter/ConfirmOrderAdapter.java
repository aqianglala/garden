package com.softgarden.garden.view.shopcar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softgarden.garden.jiadun_android.R;

import java.util.ArrayList;

/**
 * Created by qiang-pc on 2016/6/17.
 */
public class ConfirmOrderAdapter extends BaseAdapter {

    private final int ITEM_ORDER = 0;
    private final int ITEM_STRETCH = 1;

    private LayoutInflater inflater;
    private ArrayList<String>data;
    private ArrayList<String>tempData = new ArrayList<>();
    private Context context;

    private boolean isOpen;

    public ConfirmOrderAdapter(ArrayList<String> data, Context context) {
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
        if(!isOpen){// 收缩,默认显示三条
            data.clear();
            for(int i=0;i<3;i++){
                data.add(tempData.get(i));
            }
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
                    convertView = inflater.inflate(R.layout.item_order_detail,parent,false);
                    holderView.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
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
                String item = data.get(position);
                holderView.tv_name.setText(item);
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
                            for(int i=0;i<3;i++){
                                data.add(tempData.get(i));
                            }
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

    class HolderView{
        TextView tv_name;
//        TextView tv_amount;
//        TextView tv_price;
//        TextView tv_back;
//        TextView tv_detail;
        LinearLayout ll_more;
        ImageView imageView;
    }
}
