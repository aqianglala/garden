package com.softgarden.garden.view.start.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.softgarden.garden.base.BaseApplication;
import com.softgarden.garden.base.BaseFragment;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.SPUtils;
import com.softgarden.garden.utils.ScreenUtils;
import com.softgarden.garden.view.feedback.activity.SuggestionActivity;
import com.softgarden.garden.view.login.LoginActivity;
import com.softgarden.garden.view.start.entity.MessageBean;
import com.softgarden.garden.view.password.ForgetPswdActivity;

import org.simple.eventbus.EventBus;

/**
 * Created by qiang on 2016/6/14.
 */
public class MenuFragment extends BaseFragment {

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_menu);
        TextView tv_name = getViewById(R.id.tv_name);
        tv_name.setText(BaseApplication.userInfo.getData().getCustomerName());
    }

    @Override
    protected void setListener() {
        getViewById(R.id.rl_feedback).setOnClickListener(this);
        getViewById(R.id.rl_modify_pswd).setOnClickListener(this);
        getViewById(R.id.rl_contact).setOnClickListener(this);
        getViewById(R.id.tv_logout).setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_feedback:
                mActivity.startActivity(new Intent(mActivity,SuggestionActivity.class));
                break;
            case R.id.rl_modify_pswd:
                Intent intent = new Intent(mActivity, ForgetPswdActivity.class);
                intent.putExtra("title","修改密码");
                mActivity.startActivity(intent);
                break;
            case R.id.rl_contact:
                // 弹出提示框
                showContactDialog();
                break;
            case R.id.tv_logout:
                // 弹出对话框提示是否退出
                new AlertDialog.Builder(mActivity)
                        .setTitle("退出当前账号")
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO: 2016/6/30 清除本地账号信息，跳转到登录界面
                                SPUtils.clear(mActivity);
                                mActivity.startActivity(new Intent(mActivity, LoginActivity.class));
                                mActivity.finish();
                            }
                        }).create()
                        .show();
                break;
        }
        // 关闭侧滑菜单
        // post a event with tag, the tag is like broadcast's action
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new MessageBean("mr.simple"), "my_tag");
            }
        },300);
    }

    private void showContactDialog() {
        final String mobile = BaseApplication.indexEntity.getData().getKfdh();
        View view = LayoutInflater.from(mActivity).inflate(R.layout.dialog_call, null);
        TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone);
        tv_phone.setText(mobile);
        final AlertDialog alertDialog = new AlertDialog.Builder(mActivity).setView(view)
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
                // 使用系统的电话拨号服务，必须去声明权限，在AndroidManifest.xml中进行声明
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
                        + mobile));
                if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission
                        .CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    showToast("没有拨打电话的权限");
                    return;
                }
                mActivity.startActivity(intent);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

        Window window = alertDialog.getWindow();
        // 设置背景透明，以实现圆角
        window.setBackgroundDrawableResource(android.R.color.transparent);
        // 设置宽，高可在xml布局中写上,但宽度默认是match_parent，所以需要在代码中设置
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = (int) (ScreenUtils.getScreenWidth(mActivity)*0.8);
        window.setAttributes(attributes);
    }
}
