package com.softgarden.garden.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.LogUtils;
import com.softgarden.garden.utils.StatusBarUtils;
import com.softgarden.garden.utils.ToastUtil;


public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    public String TAG;
    protected Context mApp;
    private TextView tv_title;
    protected BaseActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        BaseApplication.addActivity(this);
        TAG = this.getClass().getSimpleName();
        mApp = BaseApplication.getContext();


        initView(savedInstanceState);
        if(!TAG.equals("MainActivity")&&!TAG.equals("SplashActivity")&& !TAG.equals("LoginActivity")){
            Toolbar toolbar = getViewById(R.id.toolBar);
            setSupportActionBar(toolbar);
            tv_title = getViewById(R.id.tv_title);
            // 设置状态栏为红色
            StatusBarUtils.setColor(this, getResources().getColor(R.color.colorPrimary));
            // 设置显示返回键
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        setListener();
        processLogic(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 查找View
     *
     * @param id   控件的id
     * @param <VT> View类型
     * @return
     */
    protected <VT extends View> VT getViewById(@IdRes int id) {
        return (VT) findViewById(id);
    }

    /**
     * 初始化布局以及View控件
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
     * 需要处理点击事件时，重写该方法
     *
     * @param v
     */
    public void onClick(View v) {
    }

    public void showToast(String text) {
        ToastUtil.show(text);
    }


    @Override
    protected void onDestroy() {
        LogUtils.i(TAG,"关闭请求");
        BaseApplication.removeActivity(this);
        super.onDestroy();
    }

    // 在Activity的onPostCreate与onTitleChanged中都会调用这个方法，可以在这个方法中设置toolbar的标题，标题根据menifest中指定的
    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if(tv_title!=null && !TAG.equals("ForgetPswdActivity")&& !TAG.equals("BackDetailActivity")){
            tv_title.setText(title);
        }
    }

    public void goActivity(Class clazz){
        startActivity(new Intent(this,clazz));
    }

}