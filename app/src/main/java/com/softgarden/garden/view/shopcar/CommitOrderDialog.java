package com.softgarden.garden.view.shopcar;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.softgarden.garden.base.BaseActivity;
import com.softgarden.garden.base.BaseApplication;
import com.softgarden.garden.base.EngineFactory;
import com.softgarden.garden.base.ObjectCallBack;
import com.softgarden.garden.engine.UserEngine;
import com.softgarden.garden.entity.UserEntity;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.GlobalParams;
import com.softgarden.garden.utils.SPUtils;
import com.softgarden.garden.view.start.entity.MessageBean;

import org.simple.eventbus.EventBus;

/**
 * Created by qiang-pc on 2016/6/23.
 */
public class CommitOrderDialog extends Dialog implements View.OnClickListener{
    private Context context;
    private EditText et_pswd;
    private String switchPayment;

    public CommitOrderDialog(Context context, int themeResId, String switchPayment) {
        super(context, themeResId);
        this.context = context;
        this.switchPayment = switchPayment;
    }

    public CommitOrderDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_commit_order);
        et_pswd = (EditText) findViewById(R.id.et_pswd);
        findViewById(R.id.btn_yes).setOnClickListener(this);
        findViewById(R.id.iv_cancle).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_yes:
                String pswd = et_pswd.getText().toString().trim();
                if(TextUtils.isEmpty(pswd)){
                    Toast.makeText(context,"请输入密码！",Toast.LENGTH_LONG).show();
                }else{
                    sendLogin(BaseApplication.userInfo.getData().getCustomerNo(),pswd);
                }
                break;
            case R.id.iv_cancle:
                dismiss();
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
        engine.login(name, password, new ObjectCallBack<UserEntity>((BaseActivity) context) {
            @Override
            public void onSuccess(UserEntity data) {
                BaseApplication.userInfo = data;
                // 保存用户名和密码
                SPUtils.put(context, GlobalParams.USERINFO,new Gson().toJson(data));
                SPUtils.put(context,GlobalParams.HASMODIFYPSWD,data.getErrorMsg().equals("强制改密")
                        ?false:true);
                dismiss();
                EventBus.getDefault().post(new MessageBean(switchPayment), "commitOrder");
            }
        });
    }
}
