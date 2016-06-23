package com.softgarden.garden.view.main.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.global.BaseActivity;

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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }
        },3000);
    }
}
