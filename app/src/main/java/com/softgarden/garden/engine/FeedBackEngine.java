package com.softgarden.garden.engine;

import com.softgarden.garden.base.BaseCallBack;
import com.softgarden.garden.base.BaseEngine;
import com.softgarden.garden.base.ObjectCallBack;
import com.softgarden.garden.entity.UploadImgEntity;
import com.softgarden.garden.helper.HttpHelper;
import com.softgarden.garden.interfaces.UrlsAndKeys;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qiang-pc on 2016/7/4.
 */
public class FeedBackEngine extends BaseEngine{

    public void complaint(String customerNo, String content, String pic, BaseCallBack
            callBack){

        JSONObject object=new JSONObject();
        try {
            object.put("CustomerNo",customerNo);
            object.put("content",content);
            object.put("pic",pic);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpHelper.post(UrlsAndKeys.complaint,object,callBack);
    }

    public void proposal(String customerNo, String content, BaseCallBack
            callBack){

        JSONObject object=new JSONObject();
        try {
            object.put("CustomerNo",customerNo);
            object.put("content",content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpHelper.post(UrlsAndKeys.proposal,object,callBack);
    }

    public void upload_img(String pic, ObjectCallBack<UploadImgEntity>
            callBack){

        JSONObject object=new JSONObject();
        try {
            object.put("pic",pic);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpHelper.post(UrlsAndKeys.upload_img,object,callBack);
    }
}
