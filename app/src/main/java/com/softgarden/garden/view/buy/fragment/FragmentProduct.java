package com.softgarden.garden.view.buy.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.softgarden.garden.base.BaseFragment;
import com.softgarden.garden.base.EngineFactory;
import com.softgarden.garden.base.ObjectCallBack;
import com.softgarden.garden.engine.BuyEngine;
import com.softgarden.garden.entity.IndexEntity;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.view.buy.adapter.ContentAdapter;
import com.softgarden.garden.view.buy.adapter.TitleAdapter;
import com.softgarden.garden.view.main.entity.MessageBean;

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
    private List<IndexEntity.DataBean.SanjiBean> mData = new ArrayList<>();
    private List<IndexEntity.DataBean.ErjiBean> mTitles = new ArrayList<>();
    private int mCurrentPosition = 0;

    private TitleAdapter titleAdapter;
    private ContentAdapter contentAdapter;
    private String yiji_id;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_list);

        // register the receiver object
        EventBus.getDefault().register(this);

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
                    IndexEntity.DataBean.ErjiBean item = (IndexEntity.DataBean.ErjiBean) parent.getAdapter().getItem(position);
                    BuyEngine engine = (BuyEngine) EngineFactory.getEngine(BuyEngine.class);
                    engine.getProducts(mActivity.getUserId(), yiji_id, item.getId(), new
                            ObjectCallBack<IndexEntity>(mActivity) {
                                @Override
                                public void onSuccess(IndexEntity data) {
                                    mData.addAll(data.getData().getSanji());
                                    contentAdapter.notifyDataSetChanged();
                                }
                            });
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
        IndexEntity indexEntity = (IndexEntity) arguments.getSerializable("defaultData");
        yiji_id = arguments.getString("yiji_id");
        if(indexEntity == null){
            // TODO: 2016/7/6 从网络中获取
            loadData();
        }else{
            // 设置数据
            getData(indexEntity.getData().getErji(),indexEntity.getData().getSanji());
        }
    }

    private void loadData() {
        BuyEngine engine = (BuyEngine) EngineFactory.getEngine(BuyEngine.class);
        engine.getProducts(mActivity.getUserId(), yiji_id, null, new
                ObjectCallBack<IndexEntity>(mActivity) {
            @Override
            public void onSuccess(IndexEntity data) {
                getData(data.getData().getErji(),data.getData().getSanji());
            }
        });
    }

    @Override
    protected void onUserVisible() {

    }

    private void getData(List<IndexEntity.DataBean.ErjiBean> erji, List<IndexEntity.DataBean.SanjiBean> sanji) {
        mTitles.clear();
        mData.clear();
        if (sanji!=null)
        mData.addAll(sanji);
        if (erji!=null)
        mTitles.addAll(erji);
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

    @Override
    public void onDestroyView() {
        // Don’t forget to unregister !!
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscriber(tag = "refresh")
    private void refresh(MessageBean user) {
        Log.e("", "### update user with my_tag, name = " + user.message);
        loadData();
    }
}
