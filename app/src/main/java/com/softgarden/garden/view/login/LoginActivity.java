package com.softgarden.garden.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.softgarden.garden.base.BaseActivity;
import com.softgarden.garden.base.BaseApplication;
import com.softgarden.garden.base.EngineFactory;
import com.softgarden.garden.base.ObjectCallBack;
import com.softgarden.garden.dialog.ToastDialog;
import com.softgarden.garden.engine.UserEngine;
import com.softgarden.garden.entity.UserEntity;
import com.softgarden.garden.interfaces.UrlsAndKeys;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.SPUtils;
import com.softgarden.garden.view.start.activity.MainActivity;
import com.softgarden.garden.view.password.ForgetPswdActivity;

public class LoginActivity extends BaseActivity {


    private EditText et_account;
    private EditText et_password;
    private Button btn_login;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        
        et_account = getViewById(R.id.et_account);
        et_password = getViewById(R.id.et_password);
        btn_login = getViewById(R.id.btn_login);
    }

    @Override
    protected void setListener() {
        btn_login.setOnClickListener(this);
        getViewById(R.id.tv_forget_pswd).setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_login:
                String account = et_account.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                if(TextUtils.isEmpty(account)||TextUtils.isEmpty(password)){
                    showToast("用户名或密码为空！");
                }else{
                    // TODO: 2016/6/30 请求网络
                    sendLogin(account,password);
                }
                break;

            case R.id.tv_forget_pswd:
                Intent intent = new Intent(this, ForgetPswdActivity.class);
                intent.putExtra("title","忘记密码");
                startActivity(intent);
                break;
        }
    }

    /**
     * 发送登录请求。登录成功时保存账号密码，并跳转到主页
     *
     * @param name
     * @param password
     */
    private void sendLogin(final String name, String password) {

        UserEngine engine = (UserEngine) EngineFactory.getEngine(UserEngine.class);
        engine.login(name, password, new ObjectCallBack<UserEntity>(this) {
            @Override
            public void onSuccess(UserEntity data) {
                ToastDialog.showSuccess(LoginActivity.this, "登录成功！");

                // 保存用户名和密码
                SPUtils.put(LoginActivity.this, UrlsAndKeys.USERNAME,data.getData()
                        .getUsername());
                SPUtils.put(LoginActivity.this,UrlsAndKeys.PHONE,data.getData()
                        .getPhone());
                SPUtils.put(LoginActivity.this,UrlsAndKeys.USERID,data.getData()
                        .getId());
                SPUtils.put(LoginActivity.this,UrlsAndKeys.HASMODIFYPSWD,data.getErrorMsg().equals("强制改密")
                        ?false:true);

                goActivity(MainActivity.class);
                finish();
            }

        });
    }

    private long mExitTime;
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                BaseApplication.finishAllActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
