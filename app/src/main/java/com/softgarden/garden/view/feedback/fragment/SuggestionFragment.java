package com.softgarden.garden.view.feedback.fragment;

import android.os.Bundle;
import android.widget.EditText;

import com.softgarden.garden.base.BaseFragment;
import com.softgarden.garden.jiadun_android.R;

/**
 * Created by qiang-pc on 2016/6/21.
 */
public class SuggestionFragment extends BaseFragment {
    private EditText et_content;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_suggestion);
        et_content = getViewById(R.id.et_content);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void onUserVisible() {

    }

    public String getText(){
        return et_content.getText().toString().trim();
    }
}
