package com.softgarden.garden.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.softgarden.garden.jiadun_android.R;

/**
 * Created by qiang-pc on 2016/7/1.
 */
public class LoadingDialog extends Dialog {
    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_dialog_loading_layout);
    }
}
