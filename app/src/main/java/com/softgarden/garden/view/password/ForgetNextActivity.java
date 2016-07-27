package com.softgarden.garden.view.password;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.softgarden.garden.base.BaseActivity;
import com.softgarden.garden.base.BaseCallBack;
import com.softgarden.garden.base.EngineFactory;
import com.softgarden.garden.engine.UserEngine;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.SPUtils;
import com.softgarden.garden.utils.ToastUtil;
import com.softgarden.garden.view.login.LoginActivity;

import org.json.JSONObject;

public class ForgetNextActivity extends BaseActivity {


    private EditText et_new_pswd;
    private EditText et_confirm_pswd;
    private String phone;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_forget_next);
        et_new_pswd = getViewById(R.id.et_new_pswd);
        et_confirm_pswd = getViewById(R.id.et_confirm_pswd);
        phone = getIntent().getStringExtra("phone");
    }

    @Override
    protected void setListener() {
        getViewById(R.id.btn_complete).setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_complete:
                String newPswd = et_new_pswd.getText().toString().trim();
                String confirmPswd = et_confirm_pswd.getText().toString().trim();
                if(TextUtils.isEmpty(newPswd)||TextUtils.isEmpty(confirmPswd)){
                    showToast("输入不能为空！");
                }else{
                    if(newPswd.equals(confirmPswd)){
                        if(newPswd.matches("^(?=.*\\d)(?=.*[a-z A-Z]).{6,12}$")){
                            modifyPswd(newPswd);
                        }else{
                            showToast("密码必须是6~12位的字母数字的组合");
                        }
                    }else{
                        showToast("两次密码不一致!");
                    }
                }
                break;
        }
    }

    /**
     * 修改密码
     * @param newPswd
     */
    private void modifyPswd(String newPswd){
        UserEngine engine = (UserEngine) EngineFactory.getEngine(UserEngine.class);
        engine.modifyPswd(phone, newPswd, new BaseCallBack(this) {
            @Override
            public void onSuccess(JSONObject result) {
                // 修改密码成功
                ToastUtil.show("修改密码成功！");
                // 跳转到登录页面
                SPUtils.clear(context);
                goActivity(LoginActivity.class);
                finish();
            }
        });
    }

}
