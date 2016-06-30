package com.softgarden.garden.view.back.adapter;

import android.content.Context;

import com.softgarden.garden.jiadun_android.R;

import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by qiang-pc on 2016/6/30.
 */
public class BackDetailAdapter extends BGAAdapterViewAdapter<String> {
    public BackDetailAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @Override
    protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, int i, String s) {
        bgaViewHolderHelper.setText(R.id.tv_name,s);
    }
}
