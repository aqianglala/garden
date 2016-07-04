package com.softgarden.garden.engine;

import com.softgarden.garden.base.BaseCallBack;
import com.softgarden.garden.base.BaseEngine;
import com.softgarden.garden.base.ObjectCallBack;
import com.softgarden.garden.entity.UserEntity;
import com.softgarden.garden.helper.HttpHelper;
import com.softgarden.garden.interfaces.UrlsAndKeys;
import com.softgarden.garden.utils.MD5;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qiang-pc on 2016/7/4.
 */
public class UserEngine extends BaseEngine{

    /**
     * 登录
     * @param phone
     * @param password
     * @param callBack
     */
    public void login(String phone,String password,ObjectCallBack<UserEntity> callBack){

        JSONObject object=new JSONObject();
        try {
            object.put("username",phone);
            object.put("password", MD5.getMD5(password+ UrlsAndKeys.md5Str));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpHelper.post("index.php/App/Api/login",object,callBack);
    }

    /**
     * 修改密码，获取验证码
     * @param phone
     * @param callBack
     */
    public void getCode(String phone, BaseCallBack callBack){
        JSONObject object=new JSONObject();
        try {
            object.put("phone",phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpHelper.post("index.php/App/Api/getregVerify",object,callBack);
    }

    /**
     * 校验验证码
     * @param phone
     * @param code
     * @param callBack
     */
    public void checkCode(String phone, String code, BaseCallBack callBack){
        JSONObject object=new JSONObject();
        try {
            object.put("phone",phone);
            object.put("code",code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpHelper.post("index.php/App/Api/checkfindverify",object,callBack);
    }

    /**
     * 修改密码
     * @param phone
     * @param newpswd
     * @param callBack
     */
    public void modifyPswd(String phone, String newpswd, BaseCallBack callBack){
        JSONObject object=new JSONObject();
        try {
            object.put("phone",phone);
            object.put("newpwd",MD5.getMD5(newpswd+ UrlsAndKeys.md5Str));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpHelper.post("index.php/App/Api/modifyPassword",object,callBack);
    }
}
