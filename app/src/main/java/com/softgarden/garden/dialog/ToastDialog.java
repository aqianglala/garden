package com.softgarden.garden.dialog;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.softgarden.garden.jiadun_android.R;


/**
 * Created by Administrator on 2015/6/16.
 */
public class ToastDialog extends DialogFragment {
    private int imageResId;
    private String message;
    private OnCloseListener listener;
    private int delay = 1500;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_toast_dialog, container);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable());
        ImageView image_icon = (ImageView) getView().findViewById(R.id.image_icon);
        TextView text_message = (TextView) getView().findViewById(R.id.text_message);
        image_icon.setImageResource(imageResId);
        text_message.setText(message);

        handler.sendEmptyMessageDelayed(0, delay);
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (getDialog() != null) getDialog().cancel();
            if (listener != null) listener.onClose();
        }
    };

    public static void show(FragmentActivity activity, int imageResId, int messageResId, final OnCloseListener
            listener) {
        show(activity, imageResId, activity.getString(messageResId), listener);
    }

    public static void show(FragmentActivity activity, int imageResId, String message, final OnCloseListener listener) {
        final ToastDialog toastDialog = new ToastDialog();
        toastDialog.imageResId = imageResId;
        toastDialog.message = message;
        toastDialog.listener = listener;

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        toastDialog.show(fragmentTransaction, null);
    }

    public static void showSuccess(FragmentActivity activity, String message, OnCloseListener listener) {
        show(activity, R.mipmap.success, message, listener);
    }

    public static void showSuccess(FragmentActivity activity, int messageResId, OnCloseListener listener) {
        show(activity, R.mipmap.success, activity.getString(messageResId), listener);
    }

    public static void showError(FragmentActivity activity, String message, OnCloseListener listener) {
        show(activity, R.mipmap.error, message, listener);
    }

    public static void showError(FragmentActivity activity, int messageResId, OnCloseListener listener) {
        show(activity, R.mipmap.error, activity.getString(messageResId), listener);
    }

    public static void showSuccess(FragmentActivity activity, String message) {
        show(activity, R.mipmap.success, message, null);
    }

    public static void showSuccess(FragmentActivity activity, int messageResId) {
        show(activity, R.mipmap.success, activity.getString(messageResId), null);
    }

    public static void showError(FragmentActivity activity, String message) {
        show(activity, R.mipmap.error, message, null);
    }

    public static void showError(FragmentActivity activity, int messageResId) {
        show(activity, R.mipmap.error, activity.getString(messageResId), null);
    }

    public static interface OnCloseListener {
        void onClose();
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        transaction.add(this, tag);
        return transaction.commitAllowingStateLoss();
    }
}