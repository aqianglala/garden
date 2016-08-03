package com.softgarden.garden.dialog;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.softgarden.garden.base.BaseActivity;
import com.softgarden.garden.base.BaseApplication;
import com.softgarden.garden.base.EngineFactory;
import com.softgarden.garden.base.ObjectCallBack;
import com.softgarden.garden.engine.UserEngine;
import com.softgarden.garden.entity.UserEntity;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.view.start.entity.MessageBean;

import org.simple.eventbus.EventBus;


/**
 * Created by Administrator on 2015/6/16.
 */
public class CommitOrderDialog extends DialogFragment implements View.OnClickListener{
    private static Context context;
    private EditText et_pswd;
    private static String switchPayment;

    public CommitOrderDialog() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 去掉对话框标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View rootView = inflater.inflate(R.layout.dialog_commit, container);
        et_pswd = (EditText) rootView.findViewById(R.id.et_pswd);
        rootView.findViewById(R.id.btn_yes).setOnClickListener(this);
        rootView.findViewById(R.id.iv_cancle).setOnClickListener(this);
        return rootView;
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


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 去除对话框背景
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable());
    }

    public static CommitOrderDialog show(FragmentActivity activity,String switchPay) {
        switchPayment = switchPay;
        context = activity;
        CommitOrderDialog dialog = new CommitOrderDialog();
        dialog.setCancelable(false);
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        dialog.show(transaction, null);
        return dialog;
    }

    private void sendLogin(final String name, String password) {

        UserEngine engine = (UserEngine) EngineFactory.getEngine(UserEngine.class);
        engine.login(name, password, new ObjectCallBack<UserEntity>((BaseActivity) context) {
            @Override
            public void onSuccess(UserEntity data) {
                BaseApplication.userInfo = data;
                // 已经验证过了就不用重新验证，以下方法将在activity中添加已经验证过的标记，并调用提交订单的方法
                EventBus.getDefault().post(new MessageBean(switchPayment), "commitOrder");
                dismiss();
            }
        });
    }
}
