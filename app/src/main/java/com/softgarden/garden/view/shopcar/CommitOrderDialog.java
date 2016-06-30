package com.softgarden.garden.view.shopcar;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.view.shopcar.activity.ConfirmOrderActivity;

/**
 * Created by qiang-pc on 2016/6/23.
 */
public class CommitOrderDialog extends Dialog implements View.OnClickListener{
    private Context context;
    private EditText et_pswd;

    public CommitOrderDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
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
                    Toast.makeText(context,"密码正确！",Toast.LENGTH_LONG).show();
                    context.startActivity(new Intent(context,ConfirmOrderActivity.class));
                    dismiss();
                }
                break;
            case R.id.iv_cancle:
                dismiss();
                break;
        }
    }
}
