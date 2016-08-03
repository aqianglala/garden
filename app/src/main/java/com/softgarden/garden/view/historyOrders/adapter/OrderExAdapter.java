package com.softgarden.garden.view.historyOrders.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softgarden.garden.base.BaseApplication;
import com.softgarden.garden.entity.HistoryOrderEntity;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.GlobalParams;
import com.softgarden.garden.utils.LogUtils;
import com.softgarden.garden.view.historyOrders.OrderDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 先判断是否开启支付，如果未开启，则历史订单不显示付款状态，
 * 否则，判断用户是否是现金用户还是记账用户
 * 如果是记账用户，则不显示付款状态
 * 如果是现金用户，则需根据是否已经付款显示付款状态
 * 进入详情页之后，如果订单是未付款的，则显示一个支付的按钮
 * Created by qiang-pc on 2016/6/29.
 */
public class OrderExAdapter extends BaseExpandableListAdapter{
    private final LayoutInflater inflater;
    private List<HistoryOrderEntity.DataBean> mData;
    private List<HistoryOrderEntity.DataBean> groups = new ArrayList<>();
    private List<HistoryOrderEntity.DataBean> children = new ArrayList<>();
    private Context context;
    private ExpandableListView exListView;

    private boolean isYYY;

    public void setYYY(boolean YYY) {
        isYYY = YYY;
    }

    public OrderExAdapter(List<HistoryOrderEntity.DataBean> data, Context context) {
        this.mData = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
        groupingData();
    }

    public void groupingData() {
        // 分成两组，第一组的组布局为列表项布局，第二组为显示收缩的布局
        groups.clear();
        if (mData.size()>1){
            for(int i=0;i<2;i++){
                if(i ==0){
                    groups.add(mData.get(i));
                }else{
                    groups.add(null);
                }
            }
        }else{
            if (mData.size() == 0){
                groups.add(null);
            }else{
                groups.add(mData.get(0));
            }
        }
        children.clear();
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
        if(groupPosition == 0 ){
            exListView =  (ExpandableListView) parent;
            if (getGroup(groupPosition)!=null){
                convertView = inflater.inflate(R.layout.item_list_orders, parent, false);
                TextView tv_number = (TextView) convertView.findViewById(R.id.tv_number);
                TextView tv_amount = (TextView) convertView.findViewById(R.id.tv_amount);
                TextView tv_price = (TextView) convertView.findViewById(R.id.tv_price);
                TextView tv_price_label = (TextView) convertView.findViewById(R.id.tv_price_label);
                final TextView tv_state = (TextView) convertView.findViewById(R.id.tv_state);
                ImageView iv_order_type = (ImageView) convertView.findViewById(R.id.iv_order_type);
                LinearLayout ll_state = (LinearLayout) convertView.findViewById(R.id.ll_state);
                final HistoryOrderEntity.DataBean item = (HistoryOrderEntity.DataBean) getGroup(groupPosition);
                tv_number.setText(item.getOrderNo());
                tv_amount.setText((item.getTgs()+item.getQty())+"件");
                tv_price.setText("￥"+item.getAmount());

                String type = item.getType();
                if ("1".equals(type)){ // 正常订单
                    iv_order_type.setImageResource(R.mipmap.dingdan);
                    tv_price_label.setText("订单金额：");
                }else if ("2".equals(type)){// 退货
                    iv_order_type.setImageResource(R.mipmap.tui);
                    tv_price_label.setText("退货金额：");
                }else if ("3".equals(type)){// 换货
                    iv_order_type.setImageResource(R.mipmap.huan);
                    tv_price_label.setText("换货金额：");
                }
//                先判断是否开启支付，如果未开启，则历史订单不显示付款状态，
//                否则，判断用户是否是现金用户还是记账用户
//                如果是记账用户，则不显示付款状态
//                如果是现金用户，则需根据是否已经付款显示付款状态
//                进入详情页之后，如果订单是未付款的，则显示一个支付的按钮
                if (isYYY || ("1".equals(BaseApplication.indexEntity.getData().getZhifu()) &&"现金"
                        .equals(BaseApplication.userInfo.getData().getJsfs()))){
                    ll_state.setVisibility(View.VISIBLE);
                    String is_pay = item.getIs_pay();
                    if ("0".equals(is_pay)){// 未付款
                        tv_state.setText("未付款");
                    }else if("1".equals(is_pay)){// 已付款
                        tv_state.setText("已付款");
                    }else{// 2,到付
                        tv_state.setText("到付");
                    }
                }else{
                    ll_state.setVisibility(View.GONE);
                }

                TextView tv_detail = (TextView) convertView.findViewById(R.id.tv_detail);
                tv_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 跳转到详情页,到时还需要传递数据过去
                        Intent intent = new Intent(context, OrderDetailActivity.class);
                        LogUtils.e("put:"+item.getOrderNo());
                        intent.putExtra(GlobalParams.ORDERNO,item.getOrderNo());
                        intent.putExtra(GlobalParams.ORDERTYPE,item.getType());
                        intent.putExtra(GlobalParams.ORDERSTATE,item.getIs_pay());
                        intent.putExtra(GlobalParams.ORDERDATE,item.getOrderDate());
                        intent.putExtra(GlobalParams.ISYYY,isYYY);
                        context.startActivity(intent);
                    }
                });
            }else{
                convertView = inflater.inflate(R.layout.item_list_orders_no, parent, false);
            }
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
            holder.tv_amount = (TextView) convertView.findViewById(R.id.tv_amount);
            holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            holder.tv_detail = (TextView) convertView.findViewById(R.id.tv_detail);
            holder.tv_state = (TextView) convertView.findViewById(R.id.tv_state);
            holder.tv_price_label = (TextView) convertView.findViewById(R.id.tv_price_label);
            holder.iv_order_type = (ImageView) convertView.findViewById(R.id.iv_order_type);
            holder.ll_state = (LinearLayout) convertView.findViewById(R.id.ll_state);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        final HistoryOrderEntity.DataBean item = children.get(childPosition);
        holder.tv_number.setText(item.getOrderNo());
        holder.tv_amount.setText((item.getTgs()+item.getQty())+"件");
        holder.tv_price.setText("￥"+item.getAmount());

        String type = item.getType();
        if ("1".equals(type)){ // 正常订单
            holder.iv_order_type.setImageResource(R.mipmap.dingdan);
            holder.tv_price_label.setText("订单金额：");
        }else if ("2".equals(type)){// 退货
            holder.iv_order_type.setImageResource(R.mipmap.tui);
            holder.tv_price_label.setText("退货金额：");
        }else if ("3".equals(type)){// 换货
            holder.iv_order_type.setImageResource(R.mipmap.huan);
            holder.tv_price_label.setText("换货金额：");
        }
//                先判断是否开启支付，如果未开启，则历史订单不显示付款状态，
//                否则，判断用户是否是现金用户还是记账用户
//                如果是记账用户，则不显示付款状态
//                如果是现金用户，则需根据是否已经付款显示付款状态
//                进入详情页之后，如果订单是未付款的，则显示一个支付的按钮
        if (isYYY || ("1".equals(BaseApplication.indexEntity.getData().getZhifu()) &&"现金"
                .equals(BaseApplication.userInfo.getData().getJsfs()))){
            holder.ll_state.setVisibility(View.VISIBLE);
            String is_pay = item.getIs_pay();
            if ("0".equals(is_pay)){// 未付款
                holder.tv_state.setText("未付款");
            }else if("1".equals(is_pay)){// 已付款
                holder.tv_state.setText("已付款");
            }else{// 2,到付
                holder.tv_state.setText("到付");
            }
        }else{
            holder.ll_state.setVisibility(View.GONE);
        }
        holder.tv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到详情页,到时还需要传递数据过去
                Intent intent = new Intent(context, OrderDetailActivity.class);
                LogUtils.e("put:"+item.getOrderNo());
                intent.putExtra(GlobalParams.ORDERNO,item.getOrderNo());
                intent.putExtra(GlobalParams.ORDERTYPE,item.getType());
                intent.putExtra(GlobalParams.ORDERDATE,item.getOrderDate());
                intent.putExtra(GlobalParams.ORDERSTATE,item.getIs_pay());
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
        TextView tv_amount;
        TextView tv_price;
        TextView tv_detail;
        TextView tv_state;
        TextView tv_price_label;
        ImageView iv_order_type;
        LinearLayout ll_state;

    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
