package com.softgarden.garden.view.shopcar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.softgarden.garden.jiadun_android.R;

import java.util.List;
import java.util.Map;

/**
 * Created by qiang-pc on 2016/6/22.
 */
public class ShopcartExpandableListViewAdapter extends BaseExpandableListAdapter {

    private final LayoutInflater inflater;
    private List<GroupInfo> groups;
    private Map<String, List<ProductInfo>> children;
    private Context context;

    private CheckInterface checkInterface;
    private ModifyCountInterface modifyCountInterface;

    public ShopcartExpandableListViewAdapter(List<GroupInfo> groups, Map<String,
            List<ProductInfo>> children, Context context) {
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
        String groupId = groups.get(groupPosition).getId();
        return children.get(groupId).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String groupId = groups.get(groupPosition).getId();
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
        holder.tv_group.setText(group.getName());
        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 改变children的isChecked属性，然后notifyDataSetChange
                group.setChoosed(((CheckBox) v).isChecked());
                checkInterface.checkGroup(groupPosition, ((CheckBox) v).isChecked());// 暴露组选接口
            }
        });
        holder.checkbox.setChecked(group.isChoosed());
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View
            convertView, ViewGroup parent) {
        final ChildHolder cholder;
        if (convertView == null)
        {
            cholder = new ChildHolder();
            convertView = inflater.inflate(R.layout.item_list_check_content,parent,false);
            cholder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
            cholder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            cholder.tv_minus = (TextView) convertView.findViewById(R.id.tv_minus);
            cholder.tv_plus = (TextView) convertView.findViewById(R.id.tv_plus);
            cholder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            cholder.et_total = (EditText) convertView.findViewById(R.id.et_total);
            // childrenMap.put(groupPosition, convertView);
            convertView.setTag(cholder);
        } else
        {
            // convertView = childrenMap.get(groupPosition);
            cholder = (ChildHolder) convertView.getTag();
        }
        final ProductInfo product = (ProductInfo) getChild(groupPosition, childPosition);

        if (product != null)
        {
            cholder.et_total.setText(product.getCount()+"");
            cholder.tv_price.setText(product.getPrice()+"");
            cholder.tv_name.setText(product.getName());
            cholder.checkbox.setChecked(product.isChoosed());
            cholder.checkbox.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    product.setChoosed(((CheckBox) v).isChecked());
                    cholder.checkbox.setChecked(((CheckBox) v).isChecked());
                    checkInterface.checkChild(groupPosition, childPosition, ((CheckBox) v).isChecked());// 暴露子选接口
                }
            });
            cholder.tv_plus.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    modifyCountInterface.doIncrease(groupPosition, childPosition, cholder.et_total,
                            cholder.checkbox.isChecked());// 暴露增加接口
                }
            });
            cholder.tv_minus.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    modifyCountInterface.doDecrease(groupPosition, childPosition, cholder
                            .et_total, cholder.checkbox.isChecked());// 暴露删减接口
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
        TextView tv_price;
        TextView tv_minus;
        TextView tv_plus;
        EditText et_total;
    }

    public void setCheckInterface(CheckInterface checkInterface)
    {
        this.checkInterface = checkInterface;
    }

    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface)
    {
        this.modifyCountInterface = modifyCountInterface;
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

    /**
     * 改变数量的接口
     *
     *
     */
    public interface ModifyCountInterface
    {
        /**
         * 增加操作
         *
         * @param groupPosition
         *            组元素位置
         * @param childPosition
         *            子元素位置
         * @param showCountView
         *            用于展示变化后数量的View
         * @param isChecked
         *            子元素选中与否
         */
        public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

        /**
         * 删减操作
         *
         * @param groupPosition
         *            组元素位置
         * @param childPosition
         *            子元素位置
         * @param showCountView
         *            用于展示变化后数量的View
         * @param isChecked
         *            子元素选中与否
         */
        public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);
    }
}
