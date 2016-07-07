package com.softgarden.garden.view.buy.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.softgarden.garden.base.BaseFragment;
import com.softgarden.garden.base.EngineFactory;
import com.softgarden.garden.base.ObjectCallBack;
import com.softgarden.garden.engine.BuyEngine;
import com.softgarden.garden.entity.IndexEntity;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.view.buy.adapter.ContentAdapter;
import com.softgarden.garden.view.buy.adapter.TitleAdapter;

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
    private TextView tv_title;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_list);

        tv_title = getViewById(R.id.tv_title);
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
                    tv_title.setText(item.getTitle());
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
        yiji_id = arguments.getString("yiji_id");
        loadData();
    }

    private void loadData() {
        BuyEngine engine = (BuyEngine) EngineFactory.getEngine(BuyEngine.class);
        engine.getProducts(mActivity.getUserId(), yiji_id, null, new
                ObjectCallBack<IndexEntity>(mActivity) {
            @Override
            public void onSuccess(IndexEntity data) {
                setData(data.getData().getErji(),data.getData().getSanji());
            }
        });
    }

    @Override
    protected void onUserVisible() {

    }
    private void setData(List<IndexEntity.DataBean.ErjiBean> erji, List<IndexEntity.DataBean.SanjiBean> sanji) {

        mTitles.clear();
        mData.clear();
        if (sanji!=null)
            mData.addAll(sanji);
        if (erji!=null){
            String title = erji.get(0).getTitle();
            tv_title.setText(title);
            mTitles.addAll(erji);
        }
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

}
