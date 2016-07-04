package com.softgarden.garden.view.main.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.text.TextUtils;

import com.softgarden.garden.interfaces.UrlsAndKeys;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.base.BaseActivity;
import com.softgarden.garden.utils.SPUtils;
import com.softgarden.garden.view.login.LoginActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        // TODO: 2016/7/1 从本地中获取账号密码信息，如果有的话则直接进入到mainActivity，否则进入登录页面
        final Intent intent;
        String token = (String) SPUtils.get(this, UrlsAndKeys.TOKEN, "");
        if(TextUtils.isEmpty(token)){
            intent = new Intent(SplashActivity.this, LoginActivity.class);
        }else{
            intent = new Intent(SplashActivity.this, MainActivity.class);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        },2000);
    }
}
