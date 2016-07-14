package com.softgarden.garden.view.shopcar.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.softgarden.garden.base.BaseActivity;
import com.softgarden.garden.base.BaseApplication;
import com.softgarden.garden.dialog.InputCountDialog;
import com.softgarden.garden.entity.OrderCommitEntity;
import com.softgarden.garden.entity.TempDataBean;
import com.softgarden.garden.helper.HttpHelper;
import com.softgarden.garden.helper.ImageLoaderHelper;
import com.softgarden.garden.interfaces.DialogInputListener;
import com.softgarden.garden.interfaces.ModifyCountInterface;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.other.ShoppingCart;
import com.softgarden.garden.utils.ToastUtil;
import com.softgarden.garden.view.shopcar.entity.GroupInfo;
import com.softgarden.garden.view.start.entity.MessageBean;

import org.simple.eventbus.EventBus;

import java.util.List;
import java.util.Map;

/**
 * Created by qiang-pc on 2016/6/22.
 */
public class ShopcartExpandableListViewAdapter extends BaseExpandableListAdapter {

    private final LayoutInflater inflater;
    private List<GroupInfo> groups;
    private Map<String, List<OrderCommitEntity.ZstailBean>> children;
    private Context context;

    private CheckInterface checkInterface;
    private ModifyCountInterface modifyCountInterface;

    public ShopcartExpandableListViewAdapter(List<GroupInfo> groups, Map<String, List<OrderCommitEntity.ZstailBean>>
            children, Context context) {
        this.groups = groups;
        this.children = children;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String groupId = groups.get(groupPosition).getGroupId();
        return children.get(groupId).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String groupId = groups.get(groupPosition).getGroupId();
        return children.get(groupId).get(childPosition);
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

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup
            parent) {
        GroupHolder holder = null;
        if(convertView==null){
            holder = new GroupHolder();
            convertView = inflater.inflate(R.layout.item_shopcargrouplist,parent,false);
            holder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
            holder.tv_group = (TextView) convertView.findViewById(R.id.tv_group);
            convertView.setTag(holder);
        }else{
            holder = (GroupHolder) convertView.getTag();
        }
        final GroupInfo group = (GroupInfo) getGroup(groupPosition);
        holder.tv_group.setText(group.getGroupName());
        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 改变children的isChecked属性，然后notifyDataSetChange
                group.setChoosed(((CheckBox) v).isChecked());
                checkInterface.checkGroup(groupPosition, ((CheckBox) v).isChecked());// 暴露组选接口
            }
        });
        holder.checkbox.setChecked(group.isChoosed());
        holder.checkbox.setVisibility(isEditMode?View.VISIBLE:View.GONE);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View
            convertView, ViewGroup parent) {
        final ChildHolder cholder;
        if (convertView == null)
        {
            cholder = new ChildHolder();
            convertView = inflater.inflate(R.layout.item_list_content,parent,false);
            cholder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
            cholder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            cholder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
            cholder.tv_prediction = (TextView) convertView.findViewById(R.id.tv_prediction);
            cholder.tv_weight = (TextView) convertView.findViewById(R.id.tv_weight);
            cholder.tv_back = (TextView) convertView.findViewById(R.id.tv_back);
            cholder.tv_minus = (TextView) convertView.findViewById(R.id.tv_minus);
            cholder.tv_plus = (TextView) convertView.findViewById(R.id.tv_plus);
            cholder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            cholder.tv_total = (TextView) convertView.findViewById(R.id.tv_total);
            cholder.tv_group = (TextView) convertView.findViewById(R.id.tv_group);
            cholder.tv_special = (TextView) convertView.findViewById(R.id.tv_special);
            cholder.iv_tejia = (ImageView) convertView.findViewById(R.id.iv_tejia);
            cholder.iv_product = (NetworkImageView) convertView.findViewById(R.id.iv_product);
            // childrenMap.put(groupPosition, convertView);
            convertView.setTag(cholder);
        } else
        {
            // convertView = childrenMap.get(groupPosition);
            cholder = (ChildHolder) convertView.getTag();
        }
        final OrderCommitEntity.ZstailBean product = (OrderCommitEntity.ZstailBean) getChild(groupPosition, childPosition);

        if (product != null)
        {
            // 设置网络图片
            cholder.iv_product.setImageUrl(HttpHelper.HOST+product.getPicture(), ImageLoaderHelper
                    .getInstance());
            // 设置价格
            int price = product.getPrice();
            if(price == 0 || product.getIsSpecial() == 0){// 没有特价，使用标准价
                cholder.tv_special.setText(product.getBzj());
                cholder.tv_price.setVisibility(View.GONE);
                cholder.iv_tejia.setVisibility(View.GONE);
            }else{// 特价
                cholder.tv_special.setText(product.getPrice()+"");
                cholder.tv_price.setText(product.getBzj());
                cholder.tv_price.setVisibility(View.VISIBLE);
                cholder.tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                cholder.iv_tejia.setVisibility(View.VISIBLE);
            }

            cholder.tv_name.setText(product.getItemName());
            cholder.tv_number.setText(product.getItemNo());
            cholder.tv_prediction.setText(product.getProQty()+"");
            cholder.tv_weight.setText(product.getSpec());
            cholder.tv_back.setText(product.getReturnrate()+"");
            cholder.tv_total.setText(product.getQty()+"");
            cholder.tv_group.setText(product.getTgs()+"");

            cholder.checkbox.setChecked(product.isIsChoosed());
            cholder.checkbox.setVisibility(isEditMode?View.VISIBLE:View.GONE);
            // 商品数量上限
            final int maxCount = product.getProQty() * Integer.parseInt(BaseApplication.userInfo
                    .getData().getKxd
                    ());
            cholder.tv_minus.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int count = Integer.parseInt(cholder.tv_total.getText().toString().trim());
                    int tuangou = Integer.parseInt(cholder.tv_group.getText().toString().trim());
                    if(count>1){
                        cholder.tv_total.setText(--count+"");
                        TempDataBean item = new TempDataBean(tuangou, count, product.getItemNo());
                        ShoppingCart shoppingcart = ShoppingCart.getInstance();
                        shoppingcart.changeItem(item);
                        // 更新首页数据
                        EventBus.getDefault().post(new MessageBean("mr.simple"), "notifyDataSetChange");
                    }
                }
            });
            cholder.tv_plus.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int count = Integer.parseInt(cholder.tv_total.getText().toString().trim());
                    int tuangou = Integer.parseInt(cholder.tv_group.getText().toString().trim());
                    if(maxCount != 0){
                        if(count+tuangou<maxCount){
                            cholder.tv_total.setText(++count+"");
                            TempDataBean item = new TempDataBean(tuangou, count, product.getItemNo());
                            ShoppingCart shoppingcart = ShoppingCart.getInstance();
                            shoppingcart.changeItem(item);
                            // 更新首页数据
                            EventBus.getDefault().post(new MessageBean("mr.simple"), "notifyDataSetChange");
                        }else{
                            ToastUtil.show("数量已达上限！");
                        }
                    }else{
                        cholder.tv_total.setText(++count+"");
                        TempDataBean item = new TempDataBean(tuangou, count, product.getItemNo());
                        ShoppingCart shoppingcart = ShoppingCart.getInstance();
                        shoppingcart.changeItem(item);
                        // 更新首页数据
                        EventBus.getDefault().post(new MessageBean("mr.simple"), "notifyDataSetChange");

                    }
                }
            });


            cholder.tv_total.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tuangou = Integer.parseInt(cholder.tv_group.getText().toString().trim());
                    int shuliang = Integer.parseInt(cholder.tv_total.getText().toString().trim());
                    InputCountDialog dialog = InputCountDialog.show((BaseActivity) context,
                            tuangou,shuliang,maxCount,false);
                    dialog.setDialogInputListener(new DialogInputListener() {
                        @Override
                        public void inputNum(String num) {
                            cholder.tv_total.setText(num);
                            ShoppingCart shoppingCart = ShoppingCart.getInstance();
                            int tuangou = Integer.parseInt(cholder.tv_group.getText().toString().trim());
                            shoppingCart.changeItem(new TempDataBean(tuangou,Integer.parseInt(num),
                                    product.getItemNo()));
                            // 更新首页数据
                            EventBus.getDefault().post(new MessageBean("mr.simple"), "notifyDataSetChange");
                        }
                    });
                }
            });
            cholder.tv_group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tuangou = Integer.parseInt(cholder.tv_group.getText().toString().trim());
                    int shuliang = Integer.parseInt(cholder.tv_total.getText().toString().trim());
                    InputCountDialog dialog = InputCountDialog.show((BaseActivity) context,
                            tuangou,shuliang,maxCount,true);
                    dialog.setDialogInputListener(new DialogInputListener() {
                        @Override
                        public void inputNum(String num) {
                            cholder.tv_group.setText(num);
                            ShoppingCart shoppingCart = ShoppingCart.getInstance();
                            int count = Integer.parseInt(cholder.tv_total.getText().toString().trim());
                            shoppingCart.changeItem(new TempDataBean(Integer.parseInt(num),count,
                                    product.getItemNo()));
                            // 更新首页数据
                            EventBus.getDefault().post(new MessageBean("mr.simple"), "notifyDataSetChange");
                        }
                    });
                }
            });
            cholder.checkbox.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    product.setIsChoosed(((CheckBox) v).isChecked());
                    cholder.checkbox.setChecked(((CheckBox) v).isChecked());
                    checkInterface.checkChild(groupPosition, childPosition, ((CheckBox) v).isChecked());// 暴露子选接口
                }
            });
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    /**
     * 组元素绑定器
     *
     *
     */
    private class GroupHolder
    {
        CheckBox checkbox;
        TextView tv_group;
    }

    /**
     * 子元素绑定器
     *
     *
     */
    private class ChildHolder
    {
        CheckBox checkbox;

        TextView tv_name;
        TextView tv_number;
        TextView tv_prediction;
        TextView tv_weight;
        TextView tv_back;
        TextView tv_price;
        TextView tv_minus;
        TextView tv_plus;
        TextView tv_total;
        TextView tv_group;
        TextView tv_special;
        ImageView iv_tejia;
        NetworkImageView iv_product;
    }

    private boolean isEditMode;
    public void setIsEditMode(boolean isEditMode){
        this.isEditMode = isEditMode;
    }

    public void setCheckInterface(CheckInterface checkInterface)
    {
        this.checkInterface = checkInterface;
    }

    /**
     * 复选框接口
     *
     *
     */
    public interface CheckInterface
    {
        /**
         * 组选框状态改变触发的事件
         *
         * @param groupPosition
         *            组元素位置
         * @param isChecked
         *            组元素选中与否
         */
        public void checkGroup(int groupPosition, boolean isChecked);

        /**
         * 子选框状态改变时触发的事件
         *
         * @param groupPosition
         *            组元素位置
         * @param childPosition
         *            子元素位置
         * @param isChecked
         *            子元素选中与否
         */
        public void checkChild(int groupPosition, int childPosition, boolean isChecked);
    }

}
