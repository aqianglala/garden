package com.softgarden.garden.view.shopcar;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.ScreenUtils;

/**
 * Created by qiang-pc on 2016/6/23.
 */
public class OverTimePromptDialog extends Dialog {
    private Context context;
    public OverTimePromptDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    public OverTimePromptDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_overtime);
        findViewById(R.id.ll_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContactDialog();
                dismiss();
            }
        });
        findViewById(R.id.iv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void showContactDialog() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_call, null);
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
                String mobile = context.getResources().getString(R.string.phone);
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
