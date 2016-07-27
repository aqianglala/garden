package com.softgarden.garden.view.start.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.Gson;
import com.softgarden.garden.base.BaseActivity;
import com.softgarden.garden.base.BaseApplication;
import com.softgarden.garden.base.BaseCallBack;
import com.softgarden.garden.base.EngineFactory;
import com.softgarden.garden.engine.LaunchEngine;
import com.softgarden.garden.entity.UserEntity;
import com.softgarden.garden.helper.HttpHelper;
import com.softgarden.garden.helper.ImageLoaderHelper;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.GlobalParams;
import com.softgarden.garden.utils.SPUtils;
import com.softgarden.garden.view.login.LoginActivity;

import org.json.JSONObject;

public class SplashActivity extends BaseActivity {

    private String data;
    private NetworkImageView iv_qidongye;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        iv_qidongye = (NetworkImageView) findViewById(R.id.iv_qidongye);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        // TODO: 2016/7/1 从本地中获取账号密码信息，如果有的话则直接进入到mainActivity，否则进入登录页面
        LaunchEngine engine = (LaunchEngine) EngineFactory.getEngine(LaunchEngine.class);
        long begin = System.currentTimeMillis();
        engine.qidongye(new BaseCallBack(this,false) {
            @Override
            public void onSuccess(JSONObject result) {
                data = result.optString("data");
                iv_qidongye.setImageUrl(HttpHelper.HOST+data, ImageLoaderHelper.getInstance());
                iv_qidongye.setDefaultImageResId(R.mipmap.splash);
            }
        });
        final Intent intent;
        String userinfo = (String) SPUtils.get(this, GlobalParams.USERINFO, "");
        if(TextUtils.isEmpty(userinfo)){
            intent = new Intent(SplashActivity.this, LoginActivity.class);
        }else{
            BaseApplication.userInfo = new Gson().fromJson(userinfo, UserEntity.class);
            intent = new Intent(SplashActivity.this, MainActivity.class);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        },3000);
    }
}
