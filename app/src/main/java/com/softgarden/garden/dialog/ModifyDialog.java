package com.softgarden.garden.dialog;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.view.password.ForgetPswdActivity;


/**
 * Created by Administrator on 2015/6/16.
 */
public class ModifyDialog extends DialogFragment {
    private static Context context;

    public ModifyDialog() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 去掉对话框标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View rootView = inflater.inflate(R.layout.dialog_modify1, container);
        rootView.findViewById(R.id.btn_modify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ForgetPswdActivity.class);
                intent.putExtra("title","修改密码");
                context.startActivity(intent);
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

    public static ModifyDialog show(FragmentActivity activity) {
        context = activity;
        ModifyDialog dialog = new ModifyDialog();
        dialog.setCancelable(false);
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        dialog.show(transaction, null);
        return dialog;
    }

}
