package com.softgarden.garden.engine;

import android.text.TextUtils;

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

    public void getProducts(String uid, String yiji_id, String erji_id, ObjectCallBack<IndexEntity>
            callBack){

        JSONObject object=new JSONObject();
        try {
            object.put("uid",uid);
            if(!TextUtils.isEmpty(yiji_id))
            object.put("yiji_id",yiji_id);
            if(!TextUtils.isEmpty(erji_id))
            object.put("erji_id",erji_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpHelper.post(UrlsAndKeys.index,object,callBack);
    }
}
