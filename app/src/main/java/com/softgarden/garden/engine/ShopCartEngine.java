package com.softgarden.garden.engine;

import com.softgarden.garden.base.BaseCallBack;
import com.softgarden.garden.base.BaseEngine;
import com.softgarden.garden.helper.HttpHelper;
import com.softgarden.garden.interfaces.UrlsAndKeys;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qiang-pc on 2016/7/4.
 */
public class ShopCartEngine extends BaseEngine{

    public void commitOrder(String OrderDate,String CustomerNo,String zstail,
                        BaseCallBack callBack){

        JSONObject object=new JSONObject();
        try {
            object.put("OrderDate",OrderDate);
            object.put("CustomerNo",CustomerNo);
            object.put("zstail",zstail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpHelper.post(UrlsAndKeys.order,object,callBack);
    }
}
