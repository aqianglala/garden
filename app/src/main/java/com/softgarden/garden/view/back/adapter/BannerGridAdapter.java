package com.softgarden.garden.view.back.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.softgarden.garden.jiadun_android.R;

import java.util.ArrayList;

/**
 * Created by qiang-pc on 2016/6/28.
 */
public class BannerGridAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private ArrayList<String>mData;
    private Context context;

    public BannerGridAdapter(ArrayList<String> mData, Context context) {
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
        RadioButton rb_tag = (RadioButton) view.findViewById(R.id.rb_tag);
        rb_tag.setText(mData.get(position));
        rb_tag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(context,mData.get(position),Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
