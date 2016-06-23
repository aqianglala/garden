package com.softgarden.garden.view.buy.adapter;

import android.content.Context;
import android.view.View;

import com.softgarden.garden.jiadun_android.R;

import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by qiang-pc on 2016/6/14.
 */
public class TitleAdapter extends BGAAdapterViewAdapter<String>{
    private int currentPosition;
    private Context mContext;
    public TitleAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
        mContext = context;
    }

    @Override
    protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, int i, String s) {
        bgaViewHolderHelper.setText(R.id.tv_title, s);
        View view = bgaViewHolderHelper.getView(R.id.tv_title);
        if(i == currentPosition){
            view.setBackgroundColor(mContext.getResources().getColor(R.color.bg_gray));
        }else{
            view.setBackgroundColor(mContext.getResources().getColor(R.color.bg_white));
        }
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }
}
