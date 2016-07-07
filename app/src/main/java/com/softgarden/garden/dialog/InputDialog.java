package com.softgarden.garden.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.softgarden.garden.jiadun_android.R;

/**
 * Created by qiang-pc on 2016/6/23.
 */
public class InputDialog extends Dialog implements View.OnClickListener{
    private Context context;
    private EditText et_count;


    public InputDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    public InputDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_input);
        et_count = (EditText) findViewById(R.id.et_count);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        findViewById(R.id.btn_add_car).setOnClickListener(this);
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
