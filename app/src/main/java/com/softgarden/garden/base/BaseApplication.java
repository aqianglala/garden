package com.softgarden.garden.base;

import android.app.Application;

import com.android.http.RequestManager;
import com.android.volley.RequestQueue;
import com.softgarden.garden.helper.ImageLoaderHelper;

import java.util.ArrayList;

public class BaseApplication extends Application{

    private static BaseApplication sInstance;
    private static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        ImageLoaderHelper.init(this);
        RequestManager.getInstance().init(this);
    }

    public static BaseApplication getInstance() {
        return sInstance;
    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }

    private static ArrayList<BaseActivity>activities = new ArrayList<>();
    public static void addActivity(BaseActivity activity){
        activities.add(activity);
    }

    public static void removeActivity(BaseActivity activity){
        activities.remove(activity);
    }

    public static void finishAllActivity() {
        for (int i = 0; i < activities.size(); i++) {
            if (activities.get(i) != null && !activities.get(i).isFinishing()) {
                activities.get(i).finish();
            }
        }
        activities.clear();
    }

}
