package com.softgarden.garden.view.buy.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softgarden.garden.base.BaseFragment;
import com.softgarden.garden.entity.IndexEntity;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.view.buy.adapter.ContentAdapter;
import com.softgarden.garden.view.buy.adapter.TitleAdapter;
import com.softgarden.garden.view.start.entity.MessageBean;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiang-pc on 2016/6/15.
 */
public class FragmentProduct extends BaseFragment{

    private ListView lv_titles;
    private ListView lv_content;
    private int mCurrentPosition = 0;

    private TitleAdapter titleAdapter;
    private ContentAdapter contentAdapter;
    private TextView tv_title;
    private RelativeLayout layout_empty;
    private IndexEntity.DataBean.ShopBean mData;
    private List<IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean> goods = new ArrayList<>();

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_list);
        // 注册监听
        EventBus.getDefault().register(this);

        tv_title = getViewById(R.id.tv_title);
        layout_empty = getViewById(R.id.layout_empty);
        lv_titles = getViewById(R.id.lv_titles);
        lv_content = getViewById(R.id.lv_content);
    }

    @Override
    protected void setListener() {
        lv_titles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position != mCurrentPosition){
                    goods.clear();
                    tv_title.setText(mData.getChild().get(position).getItemGroupName());
                    goods.addAll(mData.getChild().get(position).getGoods());
                    contentAdapter.notifyDataSetChanged();
                    titleAdapter.setCurrentPosition(position);
                    titleAdapter.notifyDataSetChanged();
                    mCurrentPosition = position;
                }
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        mData = (IndexEntity.DataBean.ShopBean) arguments.getSerializable("data");
        if (mData.getChild().size() == 0){
            layout_empty.setVisibility(View.VISIBLE);
        }

        setData();
        // 设置右边列表标题
        if (mData.getChild()!=null && mData.getChild().size()>0)
        tv_title.setText(mData.getChild().get(0).getItemGroupName());
    }


    @Override
    protected void onUserVisible() {

    }
    private void setData() {
        titleAdapter = new TitleAdapter(mActivity,R.layout.item_list_title);
        titleAdapter.setDatas(mData.getChild());
        lv_titles.setAdapter(titleAdapter);

        contentAdapter = new ContentAdapter(mActivity,R.layout.item_list_content);
        if (mData.getChild().size()>0){
            goods.addAll(mData.getChild().get(0).getGoods());
            contentAdapter.setDatas(goods);
            lv_content.setAdapter(contentAdapter);
        }
    }

    @Subscriber(tag = "notifyDataSetChange")
    private void notifyDataSetChange(MessageBean user) {
        Log.e("", "### update user with my_tag, name = " + user.message);
        contentAdapter.setHasClear(true);
        contentAdapter.notifyDataSetChanged();
    }

}
