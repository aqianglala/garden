package com.softgarden.garden.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.softgarden.garden.global.BaseActivity;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.view.main.activity.MainActivity;

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
                    showToast("登陆成功！");
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("isShowDiaog",false);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }
}
