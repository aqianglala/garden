package com.softgarden.garden.view.buy.adapter;

import android.content.Context;
import android.widget.TextView;

import com.softgarden.garden.entity.IndexEntity;
import com.softgarden.garden.jiadun_android.R;

import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by qiang-pc on 2016/6/14.
 */
public class TitleAdapter extends BGAAdapterViewAdapter<IndexEntity.DataBean.ShopBean.ChildBean>{
    private int currentPosition;
    private Context mContext;
    public TitleAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
        mContext = context;
    }

    @Override
    protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, int i, IndexEntity.DataBean
            .ShopBean.ChildBean bean) {
        bgaViewHolderHelper.setText(R.id.tv_title, bean.getItemGroupName());
        TextView view = bgaViewHolderHelper.getView(R.id.tv_title);
        if(i == currentPosition){
            view.setTextColor(mContext.getResources().getColor(R.color.pink_text));
            view.setBackgroundColor(mContext.getResources().getColor(R.color.bg_gray));
        }else{
            view.setTextColor(mContext.getResources().getColor(R.color.black_text));
            view.setBackgroundColor(mContext.getResources().getColor(R.color.bg_white));
        }
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }
}
