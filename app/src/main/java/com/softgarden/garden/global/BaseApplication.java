package com.softgarden.garden.global;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.softgarden.garden.utils.volleyUtils.ImageLoaderHelper;

public class BaseApplication extends Application{

    private static BaseApplication sInstance;
    private static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        // 初始化volley
        requestQueue = Volley.newRequestQueue(this);
        ImageLoaderHelper.init(requestQueue);
    }

    public static BaseApplication getInstance() {
        return sInstance;
    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
