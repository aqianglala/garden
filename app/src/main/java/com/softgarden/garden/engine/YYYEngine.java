package com.softgarden.garden.engine;

import com.softgarden.garden.base.BaseEngine;
import com.softgarden.garden.base.ObjectCallBack;
import com.softgarden.garden.entity.YYYEntity;
import com.softgarden.garden.helper.HttpHelper;
import com.softgarden.garden.interfaces.UrlsAndKeys;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qiang-pc on 2016/7/4.
 */
public class YYYEngine extends BaseEngine{

    /**
     * 获取营业员列表
     * @param uid
     * @param callBack
     */
    public void yyy(String uid, ObjectCallBack<YYYEntity>
            callBack){

        JSONObject object=new JSONObject();
        try {
            object.put("uid",uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpHelper.post(UrlsAndKeys.yyy,object,callBack);
    }
}
