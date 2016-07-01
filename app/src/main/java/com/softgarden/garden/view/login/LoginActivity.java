package com.softgarden.garden.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.softgarden.garden.global.BaseActivity;
import com.softgarden.garden.interfaces.UrlsAndKeys;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.MD5;
import com.softgarden.garden.utils.SPUtils;
import com.softgarden.garden.utils.volleyUtils.VolleyRequestUtil;
import com.softgarden.garden.view.login.entity.LoginBean;
import com.softgarden.garden.view.main.activity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

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
                    JsonObject data = new JsonObject();
                    data.addProperty("username",account);
                    data.addProperty("password",MD5.getMD5(password+ UrlsAndKeys.md5Str));// 密码加密

                    showLoadingDialog();
                    VolleyRequestUtil.RequestPost(UrlsAndKeys.login,TAG,data,new LoginListener());

                }
                break;
        }
    }

    private class LoginListener implements VolleyRequestUtil.VolleyListener{

        @Override
        public void onSuccess(String data) {
            try {
                JSONObject jsonObject = new JSONObject(data);
                String status = jsonObject.optString("status");
                if(status.equals("1")){
                    showToast("登录成功");
                    Gson gson = new Gson();
                    LoginBean loginBean = gson.fromJson(data, LoginBean.class);
                    // 保存用户名和密码
                    SPUtils.put(LoginActivity.this,UrlsAndKeys.USERNAME,loginBean.getData().getUsername());
                    SPUtils.put(LoginActivity.this,UrlsAndKeys.PASSWORD,loginBean.getData().getPassword
                            ());

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    if(loginBean.getErrorMsg().equals("强制改密")){
                        intent.putExtra("isShowDiaog",true);
                    }
                    startActivity(intent);
                    finish();
                }else {
                    String errorMsg = jsonObject.optString("errorMsg");
                    showToast(errorMsg);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            dismissDialog();
        }

        @Override
        public void onError(VolleyError volleyError) {
            Log.e(TAG,volleyError.getMessage());
            dismissDialog();
        }
    }

    private long mExitTime;
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
