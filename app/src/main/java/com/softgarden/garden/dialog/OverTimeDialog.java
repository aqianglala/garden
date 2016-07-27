package com.softgarden.garden.dialog;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.softgarden.garden.base.BaseApplication;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.ScreenUtils;


/**
 * Created by Administrator on 2015/6/16.
 */
public class OverTimeDialog extends DialogFragment {
    private static Context context;

    public OverTimeDialog() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 去掉对话框标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View rootView = inflater.inflate(R.layout.dialog_over, container);
        rootView.findViewById(R.id.ll_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContactDialog();
                dismiss();
            }
        });
        rootView.findViewById(R.id.iv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 去除对话框背景
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable());
    }

    public static OverTimeDialog show(FragmentActivity activity) {
        context = activity;
        OverTimeDialog dialog = new OverTimeDialog();
        dialog.setCancelable(false);
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        dialog.show(transaction, null);
        return dialog;
    }

    private void showContactDialog() {
        final String mobile = BaseApplication.indexEntity.getData().getKfdh();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_call, null);
        TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone);
        tv_phone.setText(mobile);
        final AlertDialog alertDialog = new AlertDialog.Builder(context).setView(view)
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
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission
                        .CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(context,"没有拨打电话的权限",Toast.LENGTH_LONG).show();
                    return;
                }
                context.startActivity(intent);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

        Window window = alertDialog.getWindow();
        // 设置背景透明，以实现圆角
        window.setBackgroundDrawableResource(android.R.color.transparent);
        // 设置宽，高可在xml布局中写上,但宽度默认是match_parent，所以需要在代码中设置
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = (int) (ScreenUtils.getScreenWidth(context)*0.8);
        window.setAttributes(attributes);
    }
}
