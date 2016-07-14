package com.softgarden.garden.helper;

import com.android.http.RequestManager;
import com.android.http.RequestMap;
import com.google.gson.Gson;
import com.softgarden.garden.base.BaseCallBack;
import com.softgarden.garden.utils.LogUtils;
import com.softgarden.garden.utils.StringUtils;

import org.json.JSONObject;


/**
 * Created by Administrator on 2015/6/14.
 */
public class HttpHelper {

    public static final String HOST = "http://wei.ruanjiekeji.com/jiadun/";
//    public static final String REGISTER = HOST+"/obspace/index.php?g=App&c=Login&a=register";

    //    http://112.124.48.189/obspace/index.php?g=App&c=Login&a=login
    public static void post(String url, RequestMap parameters, BaseCallBack callBack) {
//        if (!TextUtils.isEmpty(BaseApplication.uid)) {
//            if (parameters == null) parameters = new RequestMap();
//            parameters.put("uid", BaseApplication.uid);
//        }
        RequestManager instance = RequestManager.getInstance();
        instance.post(url, parameters, callBack, 0);
    }

    public static Gson gson = new Gson();

    public static String getUrl(String path) {
        return HttpHelper.HOST + path;
    }


    /**
     * 将参数拼到jsonobject中、调用此方法
     *
     * @param url
     * @param obc
     * @param callBack
     */
    public static void post(String url, JSONObject obc, BaseCallBack callBack) {
        if (obc != null) {
            LogUtils.e(obc.toString());
            RequestMap params = new RequestMap();
            String data = obc.toString();
            String sign = StringUtils.getMd5String("Ysljsd&sfli%87wirioew3^534rjkljl" + data);
            LogUtils.i("post_data:" + data);
            LogUtils.i("sign:"+sign);
            params.put("data", data);
            params.put("apisign", sign);
            post(HttpHelper.getUrl(url), params, callBack);
        }
    }

    /**
     * 发送图片
     *
     * @param url
     * @param pic
     * @param callBack
     */
    public static void post(String url, String pic, BaseCallBack callBack) {


        RequestMap params = new RequestMap();
        String sign = StringUtils.getMd5String("Ysljsd&sfli%87wirioew3^534rjkljl");
        params.put("pic", pic);
        params.put("apisign", sign);
        post(HttpHelper.getUrl(url), params, callBack);

    }

    /**
     * 将参数拼到传入对应实体类中、调用此方法，注意，不是参数的属性不要赋值
     *
     * @param url
     * @param obc
     * @param callBack
     */
    public static void post(String url, Object obc, BaseCallBack callBack) {

        if (obc != null) {

            RequestMap params = new RequestMap();
            String data = gson.toJson(obc);
            String sign = StringUtils.getMd5String("Ysljsd&sfli%87wirioew3^534rjkljl" + data);
            LogUtils.i("data:" + data);
            LogUtils.i("apisign:" + sign);
            params.put("data", data);
            params.put("apisign", sign);
            post(HttpHelper.getUrl(url), params, callBack);
        }
    }


}