package com.softgarden.garden.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.android.http.RequestManager;
import com.softgarden.garden.entity.IndexEntity;
import com.softgarden.garden.entity.TempDataBean;
import com.softgarden.garden.entity.UserEntity;
import com.softgarden.garden.helper.ImageLoaderHelper;
import com.softgarden.garden.other.ShoppingCart;
import com.softgarden.garden.utils.GlobalParams;
import com.softgarden.garden.view.start.entity.MessageBean;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class BaseApplication extends Application{

    private static Context context;
    private static Thread mainThread;
    private static long mainThreadId;
    private static Handler mainHandler;
    private static Looper mainlooper;
    public static IndexEntity indexEntity ;
    public static List<TempDataBean> tempDataBeans;
    public static UserEntity userInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        tempDataBeans = new ArrayList<>();
        //获取主线程
        mainThread=Thread.currentThread();
        //获取当前的线程id
        mainThreadId=android.os.Process.myTid();
        //在主线程初始化一个全局的handler。
        mainHandler=new Handler();

        mainlooper=getMainLooper();
        ImageLoaderHelper.init(this);
        RequestManager.getInstance().init(this);

        //获取当前设备的宽高。保存起来
        WindowManager windowManager=(WindowManager) getSystemService(WINDOW_SERVICE);
        Display display=windowManager.getDefaultDisplay();
        DisplayMetrics outMetrics=new DisplayMetrics();
        display.getMetrics(outMetrics);
        GlobalParams.widthPixels=outMetrics.widthPixels;
        GlobalParams.heightPixels=outMetrics.heightPixels;
    }

    public static Context getContext() {
        return context;
    }

    public static Thread getMainThread() {
        return mainThread;
    }

    public static long getMainThreadId() {
        return mainThreadId;
    }

    public static Handler getMainHandler() {
        return mainHandler;
    }

    public static Looper getMainlooper() {
        return mainlooper;
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

    public static void clearShopcart(){
        tempDataBeans.clear();
        ShoppingCart.getInstance().clearCart();
        EventBus.getDefault().post(new MessageBean("mr.simple"), "notifyDataSetChange");
    }

}