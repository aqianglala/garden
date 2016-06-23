package com.softgarden.garden.view.feedback.fragment;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.GridView;

import com.softgarden.garden.global.BaseFragment;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.view.feedback.activity.SuggestionActivity;
import com.softgarden.garden.view.feedback.adapter.GridAdapter;

import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelector;

/**
 * Created by qiang-pc on 2016/6/21.
 */
public class ComplaintFragment extends BaseFragment implements GridAdapter.PickPictureInterface{

    private EditText et_content;
    private GridView gridView;
    private ArrayList<String> mData;
    private GridAdapter adapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_complaint);

        et_content = getViewById(R.id.et_content);
        gridView = getViewById(R.id.gridView);
        mData = new ArrayList<>();
        adapter = new GridAdapter(mActivity, mData);
        adapter.setPickPictureInterface(this);
        gridView.setAdapter(adapter);
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

    @Override
    public void pick() {
        MultiImageSelector.create(mActivity)
                .showCamera(true) // 是否显示相机. 默认为显示
                .count(9) // 最大选择图片数量, 默认为9. 只有在选择模式为多选时有效
                .multi() // 多选模式, 默认模式;
                .origin(((SuggestionActivity)mActivity).mSelectPath) // 默认已选择图片. 只有在选择模式为多选时有效
                .start(mActivity, ((SuggestionActivity)mActivity).REQUEST_IMAGE);
    }
}
