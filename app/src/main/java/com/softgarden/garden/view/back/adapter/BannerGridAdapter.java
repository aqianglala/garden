package com.softgarden.garden.view.back.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.Toast;

import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.view.back.interfaces.OnItemClickPositionListener;

import java.util.ArrayList;

/**
 * Created by qiang-pc on 2016/6/28.
 */
public class BannerGridAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private ArrayList<String>mData;
    private Context context;
    private int groupIndex;
    private boolean hasClick;

    public BannerGridAdapter(int groupIndex,ArrayList<String> mData, Context context) {
        this.groupIndex = groupIndex;
        this.mData = mData;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_grid_banner, parent, false);
        final RadioButton rb_tag = (RadioButton) view.findViewById(R.id.rb_tag);
        final String tag = mData.get(position);
        rb_tag.setText(tag);
        rb_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasClick = true;
                if(clickIndex != groupIndex*6+position&& rb_tag.isChecked()){
                    Toast.makeText(context,tag,Toast.LENGTH_SHORT).show();
                    if(listener!=null){
                        listener.onClickPosition(groupIndex*6+position,tag);
                    }
                }
            }
        });
        if (!hasClick && position ==0){
            rb_tag.setChecked(true);
        }else{
            if(groupIndex*6+position == clickIndex){
                rb_tag.setChecked(true);
            }else{
                rb_tag.setChecked(false);
            }
        }

        return view;
    }

    private OnItemClickPositionListener listener;
    public void setOnItemClickPositionListener(OnItemClickPositionListener listener){
        this.listener = listener;
    }
    private int clickIndex = -1;
    public void setClickIndex(int clickIndex){
        this.clickIndex = clickIndex;
    }
}
