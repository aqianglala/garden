package com.softgarden.garden.utils;

import android.support.annotation.StringRes;
import android.widget.Toast;

import com.softgarden.garden.base.BaseApplication;


/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/7/2 10:17
 * 描述:
 */
public class ToastUtil {

    private ToastUtil() {
    }

    public static void show(CharSequence text) {
        if (text.length() < 10) {
            Toast.makeText(BaseApplication.getContext(), text, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(BaseApplication.getContext(), text, Toast.LENGTH_LONG).show();
        }
    }

    public static void show(@StringRes int resId) {
        show(BaseApplication.getContext().getString(resId));
    }

}