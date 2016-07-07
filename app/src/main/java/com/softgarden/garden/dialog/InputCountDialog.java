package com.softgarden.garden.dialog;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.softgarden.garden.jiadun_android.R;


/**
 * Created by Administrator on 2015/6/16.
 */
public class InputCountDialog extends DialogFragment implements View.OnClickListener{
    private EditText et_count;
    private static Context context;

    public InputCountDialog() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_input, container);
        et_count = (EditText) rootView.findViewById(R.id.et_count);

        rootView.findViewById(R.id.btn_cancel).setOnClickListener(this);
        rootView.findViewById(R.id.btn_add_car).setOnClickListener(this);
        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Light_NoTitleBar);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable());
    }

    public static InputCountDialog show(FragmentActivity activity) {
        context = activity;
        InputCountDialog dialog = new InputCountDialog();
        dialog.setCancelable(true);
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        dialog.show(transaction, null);
        return dialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_add_car:
                String count = et_count.getText().toString().trim();
                Toast.makeText(context,count,Toast.LENGTH_SHORT).show();
                dismiss();
                break;
        }
    }
}
