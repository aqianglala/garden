package com.softgarden.garden.view.back;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.view.back.activity.BackDetailActivity;

/**
 * Created by qiang-pc on 2016/6/23.
 */
public class BackPromptDialog extends Dialog implements View.OnClickListener{
    private Context context;
    private TextView tv_count;
    private TextView tv_show_detail;
    private EditText et_pswd;

    public void setTv_count(TextView tv_count) {
        this.tv_count = tv_count;
    }

    public BackPromptDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    public BackPromptDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_back);
        tv_count = (TextView) findViewById(R.id.tv_count);
        tv_show_detail = (TextView) findViewById(R.id.tv_show_detail);
        tv_show_detail.setOnClickListener(this);
        et_pswd = (EditText) findViewById(R.id.et_pswd);
        findViewById(R.id.btn_yes).setOnClickListener(this);
        findViewById(R.id.iv_cancle).setOnClickListener(this);
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
}
