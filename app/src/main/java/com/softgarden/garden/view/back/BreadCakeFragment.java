package com.softgarden.garden.view.back;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.softgarden.garden.global.BaseFragment;
import com.softgarden.garden.jiadun_android.R;

/**
 * Created by qiang on 2016/6/14.
 */
public class BreadCakeFragment extends BaseFragment {

    private TextView tv_content;
    private boolean isBread;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_bread_cake);
        tv_content = getViewById(R.id.tv_content);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        isBread = getArguments().getBoolean("isBread");
        tv_content.setBackgroundColor(isBread? Color.YELLOW:Color.GREEN);
    }

    @Override
    protected void onUserVisible() {

    }
}
