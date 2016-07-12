package com.softgarden.garden.engine;

import com.softgarden.garden.base.BaseEngine;
import com.softgarden.garden.base.ObjectCallBack;
import com.softgarden.garden.entity.IndexEntity;
import com.softgarden.garden.helper.HttpHelper;
import com.softgarden.garden.interfaces.UrlsAndKeys;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qiang-pc on 2016/7/4.
 */
public class BuyEngine extends BaseEngine{

    public void getProducts(String username, ObjectCallBack<IndexEntity>
            callBack){

        JSONObject object=new JSONObject();
        try {
            object.put("CustomerNo",username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpHelper.post(UrlsAndKeys.index,object,callBack);
    }
}
