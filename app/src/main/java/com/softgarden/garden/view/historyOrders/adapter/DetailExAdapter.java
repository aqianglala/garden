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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softgarden.garden.jiadun_android.R;

import java.util.ArrayList;

/**
 * Created by qiang-pc on 2016/6/29.
 */
public class DetailExAdapter extends BaseExpandableListAdapter{
    private final LayoutInflater inflater;
    private ArrayList<String> mData;
    private ArrayList<String> groups;
    private ArrayList<String> children;
    private Context context;
    private ExpandableListView exListView;

    public DetailExAdapter(ArrayList<String> data, Context context) {
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
            holderView.tv_numb = (TextView) convertView.findViewById(R.id.tv_numb);
            holderView.tv_minus = (TextView) convertView.findViewById(R.id.tv_minus);
            holderView.tv_plus = (TextView) convertView.findViewById(R.id.tv_plus);
            holderView.et_total = (EditText) convertView.findViewById(R.id.et_total);
            holderView.rl_modify_numb = (RelativeLayout) convertView.findViewById(R.id.rl_modify_numb);
            convertView.setTag(holderView);
        }else{
            holderView = (HolderView) convertView.getTag();
        }
        String item = children.get(childPosition);
        holderView.tv_name.setText(item);
        holderView.rl_modify_numb.setVisibility(isEditable?View.VISIBLE:View.GONE);
        holderView.tv_numb.setVisibility(isEditable?View.GONE:View.VISIBLE);
        final HolderView finalHolderView = holderView;

        finalHolderView.et_total.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String trim = finalHolderView.et_total.getText().toString().trim();
                    if(TextUtils.isEmpty(trim)){
                        finalHolderView.et_total.setText("1");
                    }
                }
            }
        });
        holderView.tv_plus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                modifyCountInterface.doIncrease(finalHolderView.et_total,childPosition+3,
                        finalHolderView
                        .et_total.getText().toString().trim());
            }
        });
        holderView.tv_minus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                modifyCountInterface.doDecrease(finalHolderView.et_total,childPosition+3,finalHolderView.et_total.getText().toString().trim());
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
        TextView tv_minus;
        EditText et_total;
        TextView tv_plus;
        //        TextView tv_detail;
        LinearLayout ll_more;
        ImageView imageView;
        RelativeLayout rl_modify_numb;
    }

    private com.softgarden.garden.interfaces.ModifyCountInterface modifyCountInterface;
    public void setModifyCountInterface(com.softgarden.garden.interfaces.ModifyCountInterface modifyCountInterface)
    {
        this.modifyCountInterface = modifyCountInterface;
    }

}
