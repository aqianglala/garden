package com.softgarden.garden.base;

import android.support.v4.app.FragmentActivity;

import com.android.http.RequestManager;
import com.softgarden.garden.dialog.LoadDialog;
import com.softgarden.garden.dialog.ToastDialog;
import com.softgarden.garden.jiadun_android.BuildConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Administrator on 2015/6/15.
 */
public abstract class BaseCallBack implements RequestManager.RequestListener {
    private FragmentActivity activity;
    public int success;
    public String message;
    private LoadDialog dialog;
    private int errcode;

    public BaseCallBack(FragmentActivity activity) {
        this(activity, true);
    }

    public BaseCallBack(FragmentActivity activity, boolean isShowLoadDialog) {
        this.activity = activity;
        if (isShowLoadDialog) dialog = LoadDialog.show(activity);
    }

    @Override
    public void onRequest() {
        System.out.println("BaseCallBack.onRequest");
    }

    @Override
    public void onSuccess(String result, Map<String, String> header, String url, int actionId) {
        if (BuildConfig.DEBUG) System.out.println("result = [" + result + "]");
        if (dialog != null && !dialog.isCancelable() && !activity.isFinishing()) dialog.dismiss();
        try {
            JSONObject jsonObject = new JSONObject(result);
            this.message = jsonObject.optString("errorMsg");
            this.success = jsonObject.optInt("status");
            this.errcode = jsonObject.optInt("errorCode");
            if (success != 1) {
                if (success == -1) {
                    message = "服务器繁忙";
                    onBusy(jsonObject, message);
                } else {
                    onError(jsonObject, message, errcode);
                }
            } else {
                if (BuildConfig.DEBUG) System.out.println("url = " + url);
                onSuccess(jsonObject);
            }
        } catch (JSONException e) {
            onError(result, url, actionId);
        }
    }

    public FragmentActivity getActivity() {
        return activity;
    }

    public abstract void onSuccess(JSONObject result);

    @Override
    public void onError(String s, String s1, int i) {
        if (dialog != null && !dialog.isCancelable()) dialog.dismiss();
        ToastDialog.showError(activity, "请求异常", null);
    }

    public void onError(JSONObject result, String message1, int code) {
//        返回的msgd都是英语。所有将其改为中文的显示出来

        switch (message1) {
            case "phone_already_register":
                message1 = "电话号码已注册!";
                break;
            case "verify_send_faile":
                message1 = "验证码发送失败!";
                break;

            case "verify_error":
                message1 = "验证码错误!";
                break;
            case "verify_timeout":
                message1 = "验证码超时!";
                break;
            case "register_fail":
                message1 = "注册失败!";
                break;
            case "password_error":
                message1 = "密码错误!";
                break;
            case "change_password_fail":
                message1 = "修改密码失败";
                break;
            case "phone_not_regverify":
                message1 = "电话号码未注册";
                break;
            case "wait_for_audit":
                message1 = "账号审核中";
                break;
            case "data_is_null":
                message1 = "暂无数据！";
                break;
        }
        ToastDialog.showError(getActivity(), message1);
    }

    public void onBusy(JSONObject result, String message) {
        ToastDialog.showError(getActivity(), message);
    }


    ;
}