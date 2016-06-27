package com.softgarden.garden.view.feedback.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.softgarden.garden.global.BaseFragment;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.view.feedback.activity.PhotoActivity;
import com.softgarden.garden.view.feedback.activity.SuggestionActivity;
import com.softgarden.garden.view.feedback.adapter.GridAdapter;
import com.softgarden.garden.view.feedback.utils.Bimp;
import com.softgarden.garden.view.main.entity.MessageBean;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import me.nereo.multi_image_selector.MultiImageSelector;

/**
 * Created by qiang-pc on 2016/6/21.
 */
public class ComplaintFragment extends BaseFragment{

    private EditText et_content;
    private GridView gridView;
    private GridAdapter adapter;
    private MultiImageSelector multiImageSelector;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_complaint);

        // register the receiver object
        EventBus.getDefault().register(this);

        et_content = getViewById(R.id.et_content);
        gridView = getViewById(R.id.gridView);

        adapter = new GridAdapter(mActivity);
        gridView.setAdapter(adapter);
        // 这里展示的是压缩后的图片
    }

    @Override
    protected void setListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == Bimp.bmp.size()){
                    pick();
                }else{
                    // 将当前位置和数据列表传过去
                    Intent intent = new Intent(mActivity, PhotoActivity.class);
                    intent.putExtra("currentPostion",position);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void onUserVisible() {

    }

    public void pick() {
        multiImageSelector = MultiImageSelector.create(mActivity)
                .showCamera(true) // 是否显示相机. 默认为显示
                .count(9 - Bimp.bmp.size()) // 最大选择图片数量, 默认为9. 只有在选择模式为多选时有效
                .multi(); // 多选模式, 默认模式;
        multiImageSelector.start(mActivity, ((SuggestionActivity)mActivity).REQUEST_IMAGE);
    }

    public void notifyData(){
        adapter.notifyDataSetChanged();
    }

    @Subscriber(tag = "notify")
    private void notify(MessageBean user) {
        Log.e("", "### update user with my_tag, name = " + user.message);
        notifyData();
    }
}
