package com.softgarden.garden.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softgarden.garden.utils.LogUtils;
import com.softgarden.garden.utils.ToastUtil;

public abstract class BaseFragment extends Fragment implements View.OnClickListener{
    public String TAG;
    protected Context mApp;
    public View mContentView;
    protected BaseActivity mActivity;


    public void onAttach(Activity activity) {
        super.onAttach(activity);
        TAG = this.getClass().getSimpleName();
        mApp = BaseApplication.getContext();
        mActivity = (BaseActivity) activity;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onUserVisible();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 避免多次从xml中加载布局文件
        if (mContentView == null) {
            initView(savedInstanceState);
            setListener();
            processLogic(savedInstanceState);
        } else {
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            if (parent != null) {
                parent.removeView(mContentView);
            }
        }
        return mContentView;
    }

    @Override
    public void onDestroy() {
        LogUtils.i(TAG,"关闭请求");
        super.onDestroy();
    }

    protected void setContentView(@LayoutRes int layoutResID) {
        mContentView = LayoutInflater.from(mActivity).inflate(layoutResID, null);
    }

    /**
     * 初始化View控件
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 给View控件添加事件监听器
     */
    protected abstract void setListener();

    /**
     * 处理业务逻辑，状态恢复等操作
     *
     * @param savedInstanceState
     */
    protected abstract void processLogic(Bundle savedInstanceState);

    /**
     * 当fragment对用户可见时，会调用该方法，可在该方法中懒加载网络数据
     */
    protected abstract void onUserVisible();

    /**
     * 查找View
     *
     * @param id   控件的id
     * @param <VT> View类型
     * @return
     */
    protected <VT extends View> VT getViewById(@IdRes int id) {
        return (VT) mContentView.findViewById(id);
    }

    public void showToast(String text) {
        ToastUtil.show(text);
    }

    @Override
    public void onClick(View v) {

    }

    public void goActivity(Class clazz){
        startActivity(new Intent(mActivity,clazz));
    }
}