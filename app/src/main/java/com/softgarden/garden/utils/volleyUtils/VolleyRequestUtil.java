package com.softgarden.garden.utils.volleyUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.softgarden.garden.global.BaseApplication;
import com.softgarden.garden.interfaces.UrlsAndKeys;
import com.softgarden.garden.utils.MD5;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qiang on 2016/6/12.
 */
public class VolleyRequestUtil {

    public static StringRequest stringRequest;
    /*
    * 获取GET请求内容
    * 参数：
    * context：当前上下文；
    * url：请求的url地址；
    * tag：当前请求的标签；
    * volleyListenerInterface：VolleyListenerInterface接口；
    * */
    public static void RequestGet(String url, String tag, final VolleyListener volleyListener) {
        // 清除请求队列中的tag标记请求
        BaseApplication.getRequestQueue().cancelAll(tag);
        // 创建当前的请求，获取字符串内容
        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                volleyListener.onSuccess(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyListener.onError(volleyError);
            }
        });
        // 为当前请求添加标记
        stringRequest.setTag(tag);
        // 将当前请求添加到请求队列中
        BaseApplication.getRequestQueue().add(stringRequest);
        // 重启当前请求队列
        BaseApplication.getRequestQueue().start();
    }

    /*
    * 获取POST请求内容（请求的代码为Map）
    * 参数：
    * context：当前上下文；
    * url：请求的url地址；
    * tag：当前请求的标签；
    * params：POST请求内容；
    * volleyListenerInterface：VolleyListenerInterface接口；
    * */
    public static void RequestPost(String url, String tag, final Map<String, String> params,
                                   final VolleyListener volleyListener) {
        // 清除请求队列中的tag标记请求
        BaseApplication.getRequestQueue().cancelAll(tag);
        // 创建当前的POST请求，并将请求内容写入Map中
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>
                () {
            @Override
            public void onResponse(String s) {
                volleyListener.onSuccess(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyListener.onError(volleyError);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        // 为当前请求添加标记
        stringRequest.setTag(tag);
        // 将当前请求添加到请求队列中
        BaseApplication.getRequestQueue().add(stringRequest);
        // 重启当前请求队列
        BaseApplication.getRequestQueue().start();
    }

    /*
    * 获取POST请求内容（请求的代码为Map）
    * 参数：
    * url：请求的url地址；
    * tag：当前请求的标签；
    * data：POST请求内容；
    * volleyListenerInterface：VolleyListenerInterface接口；
    * */
    public static void RequestPost(String url, String tag, JsonObject data,
                                   final VolleyListener volleyListener) {

        final Map<String, String> params = new HashMap<>();
        params.put("data",data.toString());
        params.put("apisign", MD5.getMD5(UrlsAndKeys.API_SIGN_KEY+data.toString()));
        // 清除请求队列中的tag标记请求
        BaseApplication.getRequestQueue().cancelAll(tag);
        // 创建当前的POST请求，并将请求内容写入Map中
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>
                () {
            @Override
            public void onResponse(String s) {
                volleyListener.onSuccess(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyListener.onError(volleyError);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<>();
//                headers.put("Accept", "application/json");
//                headers.put("Content-Type", "application/json; charset=UTF-8");
//                return headers;
//            }
        };
        // 为当前请求添加标记
        stringRequest.setTag(tag);
        // 将当前请求添加到请求队列中
        BaseApplication.getRequestQueue().add(stringRequest);
        // 重启当前请求队列
        BaseApplication.getRequestQueue().start();
    }

    public interface VolleyListener {
        void onSuccess(String data);

        void onError(VolleyError volleyError);
    }
}
