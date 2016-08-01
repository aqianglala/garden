package com.softgarden.garden.view.YingYeYuan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.softgarden.garden.entity.YYYEntity;
import com.softgarden.garden.jiadun_android.R;

import me.yokeyword.indexablelistview.IndexableAdapter;

/**
 * Created by YoKeyword on 16/3/24.
 */
public class ContactAdapter extends IndexableAdapter<YYYEntity.DataBean> {
    private Context mContext;

    public ContactAdapter(Context context) {
        mContext = context;
    }

    @Override
    protected TextView onCreateTitleViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tv_title_contact, parent, false);
        return (TextView) view.findViewById(R.id.tv_title);
    }

    @Override
    protected ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, YYYEntity.DataBean entity) {
        ContactViewHolder myHolder = (ContactViewHolder) holder;
        myHolder.tvName.setText(entity.getName());
        myHolder.tvAddress.setText(entity.getShipadd());
    }

    class ContactViewHolder extends ViewHolder {
        private ImageView imgAvatar;
        private TextView tvAddress, tvName;

        public ContactViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvAddress = (TextView) view.findViewById(R.id.tv_address);
        }
    }
}