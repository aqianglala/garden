package com.softgarden.garden.view.start;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.view.password.ForgetPswdActivity;

/**
 * Created by qiang-pc on 2016/6/23.
 */
public class ModifyPswdDialog extends Dialog {
    private Context context;
    public ModifyPswdDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    public ModifyPswdDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_modify);
        findViewById(R.id.btn_modify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ForgetPswdActivity.class);
                intent.putExtra("title","修改密码");
                context.startActivity(intent);
                dismiss();
            }
        });
    }
}
