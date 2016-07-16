package com.softgarden.garden.view.historyOrders.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.softgarden.garden.entity.HistoryDetailsEntity;
import com.softgarden.garden.helper.ImageLoaderHelper;
import com.softgarden.garden.jiadun_android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiang-pc on 2016/6/29.
 */
public class OrderDetailExAdapter extends BaseExpandableListAdapter{
    private final LayoutInflater inflater;
    private List<HistoryDetailsEntity.DataBean.ShopBean> mData;
    private List<HistoryDetailsEntity.DataBean.ShopBean> groups;
    private List<HistoryDetailsEntity.DataBean.ShopBean> children;
    private Context context;
    private ExpandableListView exListView;

    public OrderDetailExAdapter(List<HistoryDetailsEntity.DataBean.ShopBean> data, Context context) {
        this.mData = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
        groupingData();
    }
    private boolean isEditable;
    public void setEditable(boolean editable) {
        isEditable = editable;
    }
    /**
     * 分成4组，第1~2，4组没有child，第三组有child
      */
    private void groupingData() {
        groups = new ArrayList<>();
        for(int i=0;i<4;i++){
            if(i <3){
                groups.add(mData.get(i));
            }else{
                groups.add(null);
            }
        }
        children = new ArrayList<>();
        for(int i = 3;i<mData.size();i++){
            children.add(mData.get(i));
        }
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(groupPosition == 2){
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
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, final ViewGroup
            parent) {
        if(groupPosition < 3){
            exListView =  (ExpandableListView) parent;
            convertView = inflater.inflate(R.layout.item_order_detail, parent, false);
            TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            TextView tv_numb = (TextView) convertView.findViewById(R.id.tv_numb);
            TextView tv_minus = (TextView) convertView.findViewById(R.id.tv_minus);
            TextView tv_plus = (TextView) convertView.findViewById(R.id.tv_plus);
            final EditText et_total = (EditText) convertView.findViewById(R.id.et_total);
            RelativeLayout rl_modify_numb = (RelativeLayout) convertView.findViewById(R.id.rl_modify_numb);
            String item = (String) getGroup(groupPosition);
            tv_name.setText(item);
            rl_modify_numb.setVisibility(isEditable?View.VISIBLE:View.GONE);
            tv_numb.setVisibility(isEditable?View.GONE:View.VISIBLE);

            et_total.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus){
                        String trim = et_total.getText().toString().trim();
                        if(TextUtils.isEmpty(trim)){
                            et_total.setText("1");
                        }
                    }
                }
            });
            tv_plus.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    modifyCountInterface.doIncrease(et_total,groupPosition,
                            et_total.getText().toString().trim());
                }
            });
            tv_minus.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    modifyCountInterface.doDecrease(et_total,groupPosition,et_total.getText()
                            .toString().trim());
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
                        exListView.collapseGroup(2);
                        // 向上移
                        exListView.setSelection(0);
                        isOpen = !isOpen;
                    }else{
                        exListView.expandGroup(2);
                        isOpen = !isOpen;
                    }
                }
            });
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View
            convertView, ViewGroup parent) {
        HolderView holderView;
        if(convertView == null){
            holderView = new HolderView();
            convertView = inflater.inflate(R.layout.item_order_detail, parent, false);
            holderView.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            // 数量
            holderView.tv_numb = (TextView) convertView.findViewById(R.id.tv_numb);

            holderView.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
            holderView.tv_prediction = (TextView) convertView.findViewById(R.id.tv_prediction);
            holderView.tv_weight = (TextView) convertView.findViewById(R.id.tv_weight);
            holderView.tv_back = (TextView) convertView.findViewById(R.id.tv_back);
            holderView.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            holderView.tv_total = (TextView) convertView.findViewById(R.id.tv_total);
            holderView.iv_product = (NetworkImageView) convertView.findViewById(R.id.iv_product);
            holderView.tv_minus = (TextView) convertView.findViewById(R.id.tv_minus);
            holderView.tv_plus = (TextView) convertView.findViewById(R.id.tv_plus);
            holderView.rl_modify_numb = (RelativeLayout) convertView.findViewById(R.id.rl_modify_numb);
            convertView.setTag(holderView);
        }else{
            holderView = (HolderView) convertView.getTag();
        }
        HistoryDetailsEntity.DataBean.ShopBean item = children.get(childPosition);
        String numb = (Integer.parseInt(item.getQty()) + Integer.parseInt(item.getTgs())) + "";
        holderView.tv_name.setText(item.getItemName());
        holderView.tv_numb.setText(numb);
        holderView.tv_number.setText(item.getItemNo());
        holderView.tv_prediction.setText(item.getProQty());
        holderView.tv_weight.setText(item.getSpec());
        holderView.tv_back.setText(item.getReturnrate());
        holderView.tv_price.setText(item.getAmount());
        holderView.tv_total.setText(numb);
        holderView.iv_product.setImageUrl(item.getPicture(), ImageLoaderHelper.getInstance());
        holderView.rl_modify_numb.setVisibility(isEditable?View.VISIBLE:View.GONE);
        holderView.tv_numb.setVisibility(isEditable?View.GONE:View.VISIBLE);
        final HolderView finalHolderView = holderView;

        holderView.tv_plus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                modifyCountInterface.doIncrease(finalHolderView.tv_total,childPosition+3,
                        finalHolderView
                        .tv_total.getText().toString().trim());
            }
        });
        holderView.tv_minus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                modifyCountInterface.doDecrease(finalHolderView.tv_total,childPosition+3,finalHolderView.tv_total.getText().toString().trim());
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    class HolderView{
        TextView tv_name;
        TextView tv_numb;
        TextView tv_number;
        TextView tv_prediction;
        TextView tv_weight;
        TextView tv_back;
        TextView tv_price;
        TextView tv_minus;
        TextView tv_plus;
        TextView tv_total;
        NetworkImageView iv_product;
        RelativeLayout rl_modify_numb;
    }

    private com.softgarden.garden.interfaces.ModifyCountInterface modifyCountInterface;
    public void setModifyCountInterface(com.softgarden.garden.interfaces.ModifyCountInterface modifyCountInterface)
    {
        this.modifyCountInterface = modifyCountInterface;
    }

}
