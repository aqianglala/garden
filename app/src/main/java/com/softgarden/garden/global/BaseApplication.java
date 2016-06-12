package com.softgarden.garden.global;

import android.app.Application;

import com.softgarden.garden.helper.ImageLoaderHelper;

public class BaseApplication extends Application{

    private static BaseApplication sInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        // 初始化imageLoader
        ImageLoaderHelper.init(this);
    }

    public static BaseApplication getInstance() {
        return sInstance;
    }
}
