package com.softgarden.garden.dialog;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softgarden.garden.jiadun_android.R;


/**
 * Created by Administrator on 2015/6/16.
 */
public class LoadDialog extends DialogFragment {
    public LoadDialog() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_load_dialog, container);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable());
    }

    public static LoadDialog show(FragmentActivity activity) {
        LoadDialog dialog = new LoadDialog();
        dialog.setCancelable(false);
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        dialog.show(transaction, null);
        return dialog;
    }
}
