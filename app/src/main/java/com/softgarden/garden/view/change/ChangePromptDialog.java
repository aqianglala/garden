package com.softgarden.garden.view.change;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.softgarden.garden.jiadun_android.R;

/**
 * Created by qiang-pc on 2016/6/23.
 */
public class ChangePromptDialog extends Dialog {
    private Context context;
    public ChangePromptDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    public ChangePromptDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_change);
        findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"换货成功！",Toast.LENGTH_LONG).show();
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
}
