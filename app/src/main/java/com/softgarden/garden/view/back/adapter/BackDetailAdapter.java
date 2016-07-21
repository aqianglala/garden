package com.softgarden.garden.view.back.adapter;

import android.content.Context;

import com.android.volley.toolbox.NetworkImageView;
import com.softgarden.garden.entity.IndexEntity;
import com.softgarden.garden.helper.HttpHelper;
import com.softgarden.garden.helper.ImageLoaderHelper;
import com.softgarden.garden.jiadun_android.R;

import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by qiang-pc on 2016/6/30.
 */
public class BackDetailAdapter extends BGAAdapterViewAdapter<IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean> {
    public BackDetailAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @Override
    protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, int i, IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean item) {
        bgaViewHolderHelper.setText(R.id.tv_name,item.getItemName())
                .setText(R.id.tv_number,item.getItemNo())
                .setText(R.id.tv_prediction,item.getProQty())
                .setText(R.id.tv_weight,item.getSpec())
                .setText(R.id.tv_back,item.getReturnrate());
        bgaViewHolderHelper.setText(R.id.tv_numb,"x"+item.getQty());
        // 判断单价
        float price;
        if (item.getBzj()!=null){
            price = item.getIsSpecial() == 0?Float.parseFloat
                    (item.getBzj()): Float.parseFloat( item.getPrice());
        }else{
            price = Float.parseFloat( item.getPrice());
        }
        bgaViewHolderHelper.setText(R.id.tv_price,price+"");

        NetworkImageView iv_product = bgaViewHolderHelper.getView(R.id.iv_product);
        iv_product.setImageUrl(HttpHelper.HOST+item.getPicture(), ImageLoaderHelper.getInstance());
    }
}
