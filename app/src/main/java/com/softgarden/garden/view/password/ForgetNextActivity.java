package com.softgarden.garden.view.password;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.google.gson.JsonObject;
import com.softgarden.garden.global.BaseActivity;
import com.softgarden.garden.interfaces.UrlsAndKeys;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.volleyUtils.VolleyRequestUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgetNextActivity extends BaseActivity {


    private Button btn_complete;
    private EditText et_new_pswd;
    private EditText et_confirm_pswd;
    private String phone;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_forget_next);
        et_new_pswd = getViewById(R.id.et_new_pswd);
        et_confirm_pswd = getViewById(R.id.et_confirm_pswd);
        phone = getIntent().getStringExtra(UrlsAndKeys.USERNAME);
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
                        JsonObject data = new JsonObject();
                        data.addProperty("phone",phone);
                        data.addProperty("newpwd",newPswd);
                        showLoadingDialog();
                        VolleyRequestUtil.RequestPost(UrlsAndKeys.modifyPswd,TAG,data,new
                                NewPswdListener());
                    }else{
                        showToast("两次密码不一致!");
                    }
                }
                break;
        }
    }

    private class NewPswdListener implements VolleyRequestUtil.VolleyListener{

        @Override
        public void onSuccess(String data) {
            try {
                JSONObject jsonObject = new JSONObject(data);
                String status = jsonObject.optString("status");
                if(status.equals("1")){
                    showToast("验证码已发送！");
                    JSONObject object = new JSONObject(data);
                    String code = object.optString("data");
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

}
