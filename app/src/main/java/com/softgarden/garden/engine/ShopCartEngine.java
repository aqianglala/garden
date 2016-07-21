package com.softgarden.garden.engine;

import com.google.gson.Gson;
import com.softgarden.garden.base.BaseCallBack;
import com.softgarden.garden.base.BaseEngine;
import com.softgarden.garden.base.ObjectCallBack;
import com.softgarden.garden.entity.OrderCommitEntity;
import com.softgarden.garden.entity.PayEntity;
import com.softgarden.garden.helper.HttpHelper;
import com.softgarden.garden.interfaces.UrlsAndKeys;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qiang-pc on 2016/7/4.
 */
public class ShopCartEngine extends BaseEngine{

    public void onOrder(OrderCommitEntity data, ObjectCallBack<PayEntity>
            callBack){
        String s = new Gson().toJson(data);
        JSONObject object = null;
        try {
            object = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpHelper.post(UrlsAndKeys.onOrder,object,callBack);
    }

    /**
     * 提交到付订单
     * @param data
     * @param callBack
     */
    public void dfOrder(OrderCommitEntity data, BaseCallBack callBack){
        String s = new Gson().toJson(data);
        JSONObject object = null;
        try {
            object = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpHelper.post(UrlsAndKeys.dfOrder,object,callBack);
    }


}
