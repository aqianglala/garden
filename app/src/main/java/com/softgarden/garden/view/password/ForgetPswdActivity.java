package com.softgarden.garden.view.password;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.softgarden.garden.global.BaseActivity;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.ScreenUtils;

public class ForgetPswdActivity extends BaseActivity {


    private EditText et_phone_number;
    private EditText et_verification_code;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_forget_pswd);

        et_phone_number = getViewById(R.id.et_phone_number);
        et_verification_code = getViewById(R.id.et_verification_code);
    }

    @Override
    protected void setListener() {
        getViewById(R.id.btn_next).setOnClickListener(this);
        getViewById(R.id.ll_call).setOnClickListener(this);
        getViewById(R.id.btn_get_code).setOnClickListener(this);

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_next:
                startActivity(new Intent(this,ForgetNextActivity.class));
                break;
            case R.id.ll_call:
                showContactDialog();
                break;
            case R.id.btn_get_code:
                String phoneNumber = et_phone_number.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNumber)) {
                    showToast("手机号不能为空!");
                }
                break;
        }
    }

    private void showContactDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_call, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view)
                .setCancelable(true).create();
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        view.findViewById(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = getResources().getString(R.string.phone);
                // 使用系统的电话拨号服务，必须去声明权限，在AndroidManifest.xml中进行声明
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
                        + mobile));
                if (ActivityCompat.checkSelfPermission(ForgetPswdActivity.this, Manifest.permission
                        .CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    showToast("没有拨打电话的权限");
                    return;
                }
                startActivity(intent);
            }
        });
        alertDialog.show();

        Window window = alertDialog.getWindow();
        // 设置背景透明，以实现圆角
        window.setBackgroundDrawableResource(android.R.color.transparent);
        // 设置宽，高可在xml布局中写上,但宽度默认是match_parent，所以需要在代码中设置
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = (int) (ScreenUtils.getScreenWidth(this)*0.8);
        window.setAttributes(attributes);
    }
}
