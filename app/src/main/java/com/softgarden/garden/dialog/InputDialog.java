package com.softgarden.garden.dialog;


import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;

import com.softgarden.garden.interfaces.DialogInputListener;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.ToastUtil;


/**
 * Created by Administrator on 2015/6/16.
 */
public class InputDialog extends DialogFragment implements View.OnClickListener{
    private EditText et_count;
    private static Context context;

    public InputDialog() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 去掉对话框标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View rootView = inflater.inflate(R.layout.dialog_input, container);
        et_count = (EditText) rootView.findViewById(R.id.et_count);
        if(isTuangou){
            et_count.setText(tuangou+"");
        }else{
            et_count.setText(shuliang+"");
        }
        et_count.setSelection(et_count.getText().length());
        Button btn_add_car = (Button) rootView.findViewById(R.id.btn_add_car);
        btn_add_car.setText(btnLabel);
        btn_add_car.setOnClickListener(this);
        rootView.findViewById(R.id.btn_cancel).setOnClickListener(this);
        rootView.findViewById(R.id.iv_minus).setOnClickListener(this);
        rootView.findViewById(R.id.iv_plus).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Light_NoTitleBar);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable());
    }
    private static int tuangou;
    private static int shuliang;
    private static int max;
    private static boolean isTuangou;
    private static String btnLabel;
    public static InputDialog show(FragmentActivity activity,int group,int count,int
            maxCount,boolean isTuan,String Label) {
        tuangou = group;
        shuliang = count;
        max = maxCount;
        isTuangou = isTuan;
        btnLabel = Label;
        context = activity;
        InputDialog dialog = new InputDialog();
        dialog.setCancelable(true);
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        dialog.show(transaction, null);
        return dialog;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_minus:
                String count1 = et_count.getText().toString().trim();
                if(TextUtils.isEmpty(count1)){
                    ToastUtil.show("请输入数量！");
                }else{
                    int i = Integer.parseInt(count1);
                    if(i>0){
                        et_count.setText((--i)+"");
                    }
                }
                break;
            case R.id.iv_plus:
                String count2 = et_count.getText().toString().trim();
                if(TextUtils.isEmpty(count2)){
                    ToastUtil.show("请输入数量！");
                }else{
                    int i = Integer.parseInt(count2);
                    et_count.setText((++i)+"");
                }
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_add_car:
                String trim = et_count.getText().toString().trim();
                if (TextUtils.isEmpty(trim)){
                    ToastUtil.show("输入不能为空！");
                    return;
                }
                int count = Integer.parseInt(trim);
                if(max!=0){
                    if(!isTuangou){
                        if(count<max){
                            listener.inputNum(count+"");
                            dismiss();
                        }else{
                            ToastUtil.show("您输入的数量达到商品上限,请重新输入!");
                        }
                    }else{
                        listener.inputNum(count+"");
                        dismiss();
                    }
                }else{
                    listener.inputNum(count+"");
                    dismiss();
                }
                break;
        }
    }

    private DialogInputListener listener;
    public void setDialogInputListener(DialogInputListener listener){
        this.listener = listener;
    }
}
