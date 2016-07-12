package com.softgarden.garden.dialog;


import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.view.back.activity.BackDetailActivity;


/**
 * Created by Administrator on 2015/6/16.
 */
public class BackPromptDialog extends DialogFragment implements View.OnClickListener{
    private Context context;
    private TextView tv_count;
    private TextView tv_show_detail;
    private EditText et_pswd;

    public void setTv_count(TextView tv_count) {
        this.tv_count = tv_count;
    }

    public BackPromptDialog() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 去掉对话框标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View rootView = inflater.inflate(R.layout.dialog_back, container);
        tv_count = (TextView) rootView.findViewById(R.id.tv_count);
        tv_show_detail = (TextView) rootView.findViewById(R.id.tv_show_detail);
        tv_show_detail.setOnClickListener(this);
        et_pswd = (EditText) rootView.findViewById(R.id.et_pswd);
        rootView.findViewById(R.id.btn_yes).setOnClickListener(this);
        rootView.findViewById(R.id.iv_cancle).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_show_detail:
                Intent intent = new Intent(context, BackDetailActivity.class);
                context.startActivity(intent);
                dismiss();
                break;
            case R.id.btn_yes:
                String pswd = et_pswd.getText().toString().trim();
                if(TextUtils.isEmpty(pswd)){
                    Toast.makeText(context,"请输入密码！",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context,"退货成功！",Toast.LENGTH_LONG).show();
                    dismiss();
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

    public static BackPromptDialog show(FragmentActivity activity) {
        BackPromptDialog dialog = new BackPromptDialog();
        dialog.setCancelable(false);
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        dialog.show(transaction, null);
        return dialog;
    }
}
