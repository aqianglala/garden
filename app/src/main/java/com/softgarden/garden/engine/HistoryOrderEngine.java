package com.softgarden.garden.engine;

import com.softgarden.garden.base.BaseEngine;
import com.softgarden.garden.base.ObjectCallBack;
import com.softgarden.garden.entity.HistoryDetailsEntity;
import com.softgarden.garden.entity.HistoryOrderEntity;
import com.softgarden.garden.helper.HttpHelper;
import com.softgarden.garden.interfaces.UrlsAndKeys;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qiang-pc on 2016/7/4.
 */
public class HistoryOrderEngine extends BaseEngine{

    public void historyOrder(String customerNo, ObjectCallBack<HistoryOrderEntity>
            callBack){

        JSONObject object=new JSONObject();
        try {
            object.put("CustomerNo",customerNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpHelper.post(UrlsAndKeys.historyOrder,object,callBack);
    }

    /**
     * 获取订单详情数据
     * @param orderNo
     * @param callBack
     */
    public void historyDetails(String orderNo, ObjectCallBack<HistoryDetailsEntity>
            callBack){

        JSONObject object=new JSONObject();
        try {
            object.put("OrderNo",orderNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpHelper.post(UrlsAndKeys.historyDetails,object,callBack);
    }

}
