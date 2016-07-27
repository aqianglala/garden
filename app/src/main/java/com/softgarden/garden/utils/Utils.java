package com.softgarden.garden.utils;

import com.softgarden.garden.entity.IndexEntity;

import java.math.BigDecimal;

/**
 * Created by qiang-pc on 2016/7/25.
 */
public class Utils {
    public static float formatFloat(float f){
        BigDecimal b = new BigDecimal(f);
        float f1 =  b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        return f1;
    }

    public static float formatFloat(IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean goodsBean){
        float price = goodsBean.getIsSpecial() == 0?Float.parseFloat(goodsBean
                .getBzj()): Float.parseFloat(goodsBean.getPrice());
        return price;
    }
}
