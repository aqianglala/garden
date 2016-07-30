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

import com.google.gson.Gson;
import com.softgarden.garden.base.BaseActivity;
import com.softgarden.garden.base.BaseApplication;
import com.softgarden.garden.base.BaseCallBack;
import com.softgarden.garden.base.EngineFactory;
import com.softgarden.garden.base.ObjectCallBack;
import com.softgarden.garden.engine.UserEngine;
import com.softgarden.garden.entity.BackCommitEntity;
import com.softgarden.garden.entity.IndexEntity;
import com.softgarden.garden.entity.UserEntity;
import com.softgarden.garden.helper.HttpHelper;
import com.softgarden.garden.interfaces.UrlsAndKeys;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.ToastUtil;
import com.softgarden.garden.view.back.activity.BackDetailActivity;

import org.json.JSONException;
import org.json.JSONObject;


public class ChangePromptDialog extends DialogFragment implements View.OnClickListener{
    private static Context context;
    private TextView tv_count;
    private TextView tv_show_detail;
    private EditText et_pswd;

    private static BackCommitEntity mData;
    private int total;

    public ChangePromptDialog() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 去掉对话框标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View rootView = inflater.inflate(R.layout.dialog_chang1, container);
        tv_count = (TextView) rootView.findViewById(R.id.tv_count);
        tv_show_detail = (TextView) rootView.findViewById(R.id.tv_show_detail);
        tv_show_detail.setOnClickListener(this);
        et_pswd = (EditText) rootView.findViewById(R.id.et_pswd);
        rootView.findViewById(R.id.btn_yes).setOnClickListener(this);
        rootView.findViewById(R.id.iv_cancle).setOnClickListener(this);
        // 设置数量
        for(IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean item: mData.getZstail()){
            total +=item.getQty();
        }
        tv_count.setText(total +"");

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_show_detail:
                Intent intent = new Intent(context, BackDetailActivity.class);
                intent.putExtra("detail",mData.getZstail());
                intent.putExtra("total",total);
                intent.putExtra("title","换货详情");
                context.startActivity(intent);
                dismiss();
                break;
            case R.id.btn_yes:
                String pswd = et_pswd.getText().toString().trim();
                if (mData.getZstail().size()>0){
                    if(TextUtils.isEmpty(pswd)){
                        Toast.makeText(context,"请输入密码！",Toast.LENGTH_LONG).show();
                    }else{
                        sendLogin(BaseApplication.userInfo.getData().getCustomerNo(),pswd);
                    }
                }else{
                    Toast.makeText(context,"换货数量不能为0！",Toast.LENGTH_LONG).show();
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

    public static ChangePromptDialog show(FragmentActivity activity, BackCommitEntity backCommitEntity) {
        context = activity;
        mData = backCommitEntity;
        ChangePromptDialog dialog = new ChangePromptDialog();
        dialog.setCancelable(false);
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        dialog.show(transaction, null);
        return dialog;
    }

    private void sendLogin(final String name, String password) {
        UserEngine engine = (UserEngine) EngineFactory.getEngine(UserEngine.class);
        engine.login(name, password, new ObjectCallBack<UserEntity>((BaseActivity) context) {
            @Override
            public void onSuccess(UserEntity data) {
                BaseApplication.userInfo = data;
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(new Gson().toJson(mData));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // 登录成功，则提交退货订单
                HttpHelper.post(UrlsAndKeys.hhGoods,jsonObject , new BaseCallBack((BaseActivity)
                        context) {
                    @Override
                    public void onSuccess(JSONObject result) {
                        ToastUtil.show("退货成功");
                        dismiss();
                    }
                });
            }
        });
    }
}
