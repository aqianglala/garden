package com.softgarden.garden.view.buy.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.softgarden.garden.base.BaseFragment;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.view.buy.adapter.ContentAdapter;
import com.softgarden.garden.view.buy.entity.TestDataBean;
import com.softgarden.garden.view.buy.adapter.TitleAdapter;

import java.util.ArrayList;

/**
 * Created by qiang-pc on 2016/6/15.
 */
public class FragmentProduct extends BaseFragment{

    private ListView lv_titles;
    private ListView lv_content;
    private ArrayList<TestDataBean.DataBean> dataBeens;
    private ArrayList<TestDataBean.DataBean.ArrBean> mData = new ArrayList<>();
    private ArrayList<String> mTitles = new ArrayList<>();
    private int mCurrentPosition = 0;

    private TitleAdapter titleAdapter;
    private ContentAdapter contentAdapter;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_list);
        lv_titles = getViewById(R.id.lv_titles);
        lv_content = getViewById(R.id.lv_content);
    }

    @Override
    protected void setListener() {
        lv_titles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position != mCurrentPosition){
                    mData.clear();
                    mData.addAll(dataBeens.get(position).getArr());
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
        getData();
    }

    @Override
    protected void onUserVisible() {

    }

    private void getData() {
        mTitles.clear();
        mData.clear();
        // 模拟全部数据
        dataBeens = new ArrayList<>();
        for(int i=0;i<20;i++){
            TestDataBean.DataBean dataBean = new TestDataBean.DataBean();
            dataBean.setNum(10);
            dataBean.setType("第"+i+"种");
            ArrayList<TestDataBean.DataBean.ArrBean> arrBeens = new ArrayList<>();
            for(int j = 0;j<10;j++){
                TestDataBean.DataBean.ArrBean arrBean = new TestDataBean.DataBean.ArrBean();
                arrBean.setName("蛋糕"+i);
                arrBean.setPrediction(80);
                arrBean.setBack("80%");
                arrBean.setWeight(80);
                arrBean.setNumber("100");
                arrBeens.add(arrBean);
            }
            dataBean.setArr(arrBeens);
            dataBeens.add(dataBean);
        }
        mData.addAll(dataBeens.get(mCurrentPosition).getArr());
        ArrayList<String>titles = new ArrayList<>();
        // 取出标题数据
        for(TestDataBean.DataBean bean :dataBeens){
            titles.add(bean.getType());
        }
        mTitles.addAll(titles);
        if(titleAdapter==null){
            titleAdapter = new TitleAdapter(mActivity,R.layout.item_list_title);
            titleAdapter.setDatas(mTitles);
            lv_titles.setAdapter(titleAdapter);
        }else{
            titleAdapter.notifyDataSetChanged();
        }
        if(contentAdapter==null){
            contentAdapter = new ContentAdapter(mActivity,R.layout.item_list_content);
            contentAdapter.setDatas(mData);
            lv_content.setAdapter(contentAdapter);
        }else{
            contentAdapter.notifyDataSetChanged();
        }
    }

    public void setSelection(int position){
        lv_content.setSelection(position);
    }

}
