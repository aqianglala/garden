package com.softgarden.garden.utils;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import com.softgarden.garden.base.BaseApplication;


/**
 * 周钇冲
 * 获取资源的工具类
 */
public class UIUtils {
	
	/**
	 * 获取上下文
	 * @return
	 */
	public static Context getContext(){
		return BaseApplication.getContext();
	}
	
	/**
	 * 获取资源
	 * @return
	 */
	public static Resources getResources(){
		return getContext().getResources();
	}
	
	/**
	 * 获取资源string
	 * @param id
	 * @return
	 */
	public static String getString(int id){
		return getContext().getResources().getString(id);
	}
	
	/**
	 * 获取资源stringarray
	 * @param id
	 * @return
	 */
	public static String[] getStringArray(int id){
		return getContext().getResources().getStringArray(id);
	}
	

	/**
	 * 获取资源stringarray
	 * @param id
	 * @return
	 */
	public static int getColor(int id){
		return getContext().getResources().getColor(id);
	}
	

	/**
	 * 获取应用包名
	 * @return
	 */
	public static String getPackageName() {
		// TODO Auto-generated method stub
		return getContext().getPackageName();
	}
	
	/**
	 * 获取全局handler
	 * @return
	 */
	public static Handler getMainHandler(){
		return BaseApplication.getMainHandler();
	}
	
	/**
	 * 在主线程执行的方法
	 * 
	 */
	
	public static void runOnUiThread(Runnable nRunnable){
		
		
		long currentThreadId=android.os.Process.myTid();
		if(currentThreadId==BaseApplication.getMainThreadId()){
			//相等则当前是主线程。直接运行即可
			nRunnable.run();
		}else{
			//不相等则到handler中运行
			getMainHandler().post(nRunnable);
		}
	}
	
	/**
	 * 执行延时任务
	 * @param nRunnable
	 * @param delayMillis
	 */
	public static void runOnUiDelayed(Runnable nRunnable,long delayMillis){
		getMainHandler().postDelayed(nRunnable, delayMillis);
		
	}
	
	/**
	 * 移除任务
	 * @param nRunnable
	 */
	public static void UiRemoveCallbacks(Runnable nRunnable){
		
		getMainHandler().removeCallbacks(nRunnable);
	}
	
	/**
	 * 
	 * @param dip
	 * @return
	 */
	public static int dip2px(int dip)
	{
		// 公式 1: px = dp * (dpi / 160)
		// 公式 2: dp = px / denistity;
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		float density = metrics.density;
		// metrics.densityDpi
		return (int) (dip * density + 0.5f);
	}

	public static int px2dip(int px)
	{
		// 公式 1: px = dp * (dpi / 160)
		// 公式 2: dp = px / denistity;
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		float density = metrics.density;
		// metrics.densityDpi
		return (int) (px / density + 0.5f);
	}
	
	public static void Toastshow(String mess)
	{
		Toast.makeText(getContext(), mess,Toast.LENGTH_SHORT).show();
	}
	public static void startActivity(Class<?> myactivity)
	{
		Intent intent = new Intent(UIUtils.getContext(), myactivity);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getContext().startActivity(intent);
	}
	public static void startActivity(Class<?> myactivity,Bundle bundle)
	{
		Intent intent = new Intent(UIUtils.getContext(), myactivity);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtras(bundle);
		getContext().startActivity(intent);
	}
	public static void startActivity(Intent intent)
	{
		getContext().startActivity(intent);
	}

	public static String getString(int id,Object...formatArgs) {
		// TODO Auto-generated method stub
		return getResources().getString(id, formatArgs);
	}
	public static String getString(int id,String args) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(args)){
			args="";
		}
		return getResources().getString(id, args);
	}

	public static View inflate(int layout) {
		// TODO Auto-generated method stub
		View view=View.inflate(UIUtils.getContext(),layout,null);
		return view;
	}

	/**
	 * 从SharedPreferences中读取数据
	 *
	 * @return
	 */
	public static SharedPreferences getPreferences() {
		if (preferences == null) preferences = getContext().getSharedPreferences("brearly.xml", getContext().MODE_PRIVATE);
		return preferences;
	}
	private static  SharedPreferences preferences;


}
