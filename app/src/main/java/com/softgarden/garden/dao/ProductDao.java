package com.softgarden.garden.dao;

import android.content.Context;

import com.softgarden.garden.entity.ProductItem;
import com.softgarden.garden.utils.UIUtils;


public class ProductDao extends DaoSuper<ProductItem>{
    public ProductDao(Context context) {
        super(context);
    }


    private  static ProductDao dao;
    public static ProductDao getDao(){
        if(dao==null){
            dao=new ProductDao(UIUtils.getContext());
        }
        return dao;
    }
}
