package com.softgarden.garden.view.password;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.softgarden.garden.base.BaseActivity;
import com.softgarden.garden.base.BaseApplication;
import com.softgarden.garden.base.BaseCallBack;
import com.softgarden.garden.base.EngineFactory;
import com.softgarden.garden.engine.UserEngine;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.ScreenUtils;
import com.softgarden.garden.utils.ToastUtil;

import org.json.JSONObject;

public class ForgetPswdActivity extends BaseActivity {


    private EditText et_phone_number;
    private EditText et_verification_code;
    private Button btn_get_code;
    private String phone;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_forget_pswd);

        et_phone_number = getViewById(R.id.et_phone_number);
        et_verification_code = getViewById(R.id.et_verification_code);
        btn_get_code = getViewById(R.id.btn_get_code);
    }

    @Override
    protected void setListener() {
        getViewById(R.id.btn_next).setOnClickListener(this);
        getViewById(R.id.ll_call).setOnClickListener(this);
        btn_get_code.setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        String title = getIntent().getStringExtra("title");
        if(title.equals("忘记密码")){
            et_phone_number.setEnabled(true);
        }else{
            phone = BaseApplication.userInfo.getData().getPhone();
            et_phone_number.setEnabled(false);
            et_phone_number.setText(phone);
        }
        ((TextView)getViewById(R.id.tv_title)).setText(title);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_next:
                String code = et_verification_code.getText().toString().trim();
                if(TextUtils.isEmpty(code)){
                    showToast("验证码不能为空！");
                }else{
                    checkVerifyCode(code);
                }
                break;
            case R.id.ll_call:
                showContactDialog();
                break;
            case R.id.btn_get_code:
                String phone = et_phone_number.getText().toString().trim();
                if(TextUtils.isEmpty(phone)){
                    showToast("手机号码不能为空！");
                }else{
                    getCode();
                }
                break;
        }
    }

    /**
     * 校验验证码
     * @param code
     */
    private void checkVerifyCode(String code) {
        UserEngine engine = (UserEngine) EngineFactory.getEngine(UserEngine.class);
        engine.checkCode(phone,code, new BaseCallBack(this) {
            @Override
            public void onSuccess(JSONObject result) {
                // 跳转到下一页
                goActivity(ForgetNextActivity.class);
                finish();
            }
        });
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        // 倒计时开始
        mc.start();
        btn_get_code.setEnabled(false);
        UserEngine engine = (UserEngine) EngineFactory.getEngine(UserEngine.class);
        engine.getCode(phone, new BaseCallBack(this) {
            @Override
            public void onSuccess(JSONObject result) {
                ToastUtil.show("获取验证码成功！");
                String data = String.valueOf(result.optInt("data"));
                et_verification_code.setText(data);
            }
        });
    }

    /**
     * 显示拨打电话的弹窗
     */
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

    private MyCountDownTimer mc = new MyCountDownTimer(12000,1000); //倒计时线程
    /**
     * 继承 CountDownTimer 防范
     *
     * 重写 父类的方法 onTick() 、 onFinish()
     */

    class MyCountDownTimer extends CountDownTimer {
        /**
         *
         * @param millisInFuture
         *      表示以毫秒为单位 倒计时的总数
         *
         *      例如 millisInFuture=1000 表示1秒
         *
         * @param countDownInterval
         *      表示 间隔 多少微秒 调用一次 onTick 方法
         *
         *      例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         *
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            mc.cancel();
            btn_get_code.setText("获取验证码");
            btn_get_code.setEnabled(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btn_get_code.setText(millisUntilFinished/1000 + "秒");
        }
    }

    @Override
    protected void onDestroy() {
        mc.cancel();
        super.onDestroy();
    }
}
