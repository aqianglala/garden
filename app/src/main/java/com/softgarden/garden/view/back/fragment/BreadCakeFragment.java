package com.softgarden.garden.view.back.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softgarden.garden.base.BaseFragment;
import com.softgarden.garden.entity.IndexEntity;
import com.softgarden.garden.interfaces.CheckInterface;
import com.softgarden.garden.interfaces.ModifyCountInterface;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.GlobalParams;
import com.softgarden.garden.utils.UIUtils;
import com.softgarden.garden.view.back.adapter.BannerPagerAdapter;
import com.softgarden.garden.view.back.adapter.CheckProductAdapter;
import com.softgarden.garden.view.back.interfaces.OnItemClickPositionListener;
import com.softgarden.garden.view.start.entity.MessageBean;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by qiang on 2016/6/14.
 */
public class BreadCakeFragment extends BaseFragment implements CheckInterface,
        ModifyCountInterface,OnItemClickPositionListener,CheckProductAdapter.InputNumListener{

    private ViewPager vp_banner;
    private ListView lv_content;

    private ArrayList<IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean> mData = new ArrayList<>();
    private CheckProductAdapter adapter;

    private List<ImageView> dots = new ArrayList<ImageView>();
    private ArrayList<String> tags;
    private ArrayList<BaseFragment> bannerFragments;
    private IndexEntity.DataBean.ShopBean data;
    private int currentPageIndex = 0;
    private boolean isBack;
    private int groups;
    private LinearLayout ll_dots;

    public HashMap<String, List<IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean>> getMap() {
        return map;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_bread_cake);

        vp_banner = getViewById(R.id.vp_banner);
        lv_content = getViewById(R.id.lv_content);
        ll_dots = getViewById(R.id.ll_dots);
    }

    private void setData() {
        Bundle arguments = getArguments();
        // 一级分类的全部数据
        data = (IndexEntity.DataBean.ShopBean) arguments.getSerializable("data");
        isBack = arguments.getBoolean("isBack");
        // 获取到的banner数据
        initBannerData();
        // 分组，每组6个
        grouping();

        BannerPagerAdapter bannerPagerAdapter = new BannerPagerAdapter(getChildFragmentManager(),
                bannerFragments);
        vp_banner.setAdapter(bannerPagerAdapter);

        adapter = new CheckProductAdapter(mActivity, R.layout.item_list_check_content,
                isBack?"确认退货":"确认换货");
        adapter.setCheckInterface(this);
        adapter.setModifyCountInterface(this);
        adapter.setInputNumListener(this);

        lv_content.setAdapter(adapter);

        if(tags.size()>6){
            ll_dots.setVisibility(View.VISIBLE);
            addDots(bannerFragments);
            dots.get(0).setSelected(true);
        }else{
            ll_dots.setVisibility(View.GONE);
        }
        if (tags.size()>0){
            mData.clear();
            mData.addAll(map.get(tags.get(0)));
            adapter.setDatas(mData);
        }
    }

    private void grouping() {
        bannerFragments = new ArrayList<>();
        int size = tags.size();
        groups = (int)Math.ceil(size/6.0);
        // 设置viewpager的缓存页数
        vp_banner.setOffscreenPageLimit(groups);
        for (int i = 0; i< groups; i++){
            ArrayList<String> group = new ArrayList<>();
            if(i == groups -1){// 如果是最后一组
                int lastSize = size % 6;
                for(int k = i*6;k<(i*6+lastSize);k++){
                    group.add(tags.get(k));
                }
            }else{
                for(int j=i*6;j<(i+1)*6;j++){
                    group.add(tags.get(j));
                }
            }
            BannerFragment bannerFragment = new BannerFragment();
            bannerFragment.setOnItemClickPositionListener(this);
            Bundle bundle = new Bundle();
            // 传递数据及组的坐标，以此来获得被点击的item在原集合的位置
            bundle.putInt("groupIndex",i);
            bundle.putBoolean("isBack",isBack);
            bundle.putString(GlobalParams.itemclassname,data.getItemclassName());
            bundle.putStringArrayList("tags",group);
            bannerFragment.setArguments(bundle);
            bannerFragments.add(bannerFragment);
        }
    }

    private HashMap<String,List<IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean>> map = new
            HashMap<>();
    private void initBannerData() {
        tags = new ArrayList<>();
        for(IndexEntity.DataBean.ShopBean.ChildBean erji:data.getChild()){
            tags.add(erji.getItemGroupName());
            map.put(erji.getItemGroupName(),erji.getGoods());
        }
    }

    /**
     * 添加banner的指示器
     * @param fragments
     */
    private void addDots(ArrayList<BaseFragment> fragments) {
        for (int i = 0; i < fragments.size(); i++) {
            ImageView img = new ImageView(getContext());
            img.setBackgroundResource(R.drawable.dot_selector);
            img.setSelected(false);// 灰色
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                    UIUtils.dip2px(7), UIUtils.dip2px(7));
            if(i<fragments.size()-1){
                p.rightMargin = UIUtils.dip2px(7);
            }
            img.setLayoutParams(p);
            ll_dots.addView(img);
            dots.add(img);
        }
    }

    @Override
    protected void setListener() {
        vp_banner.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                setVpBannerHeight();
                vp_banner.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        vp_banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dots.get(currentPageIndex).setSelected(false);
                currentPageIndex = position;
                dots.get(currentPageIndex).setSelected(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        setData();
    }

    private void setVpBannerHeight() {
        // 设置viewpager的高度
        int measuredHeight = vp_banner.getMeasuredHeight();
        ViewGroup.LayoutParams layoutParams = vp_banner.getLayoutParams();
        if(tags.size()<=3){
            layoutParams.height = UIUtils.dip2px(53);
        }else{
            layoutParams.height = UIUtils.dip2px(107);
        }
        vp_banner.setLayoutParams(layoutParams);
    }

    @Override
    public void checkChild(int position, boolean isChecked) {
        map.get(mData.get(position).getItemGroupName()).get(position).setChoosed(isChecked);
        mData.get(position).setChoosed(isChecked);
    }

    @Override
    public void doIncrease(TextView tv_total, int position, String currentCount) {
        int qty = Integer.parseInt(currentCount);
        IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean goodsBean = map.get(mData.get(position)
                .getItemGroupName()).get(position);
        goodsBean.setQty(++qty);
        float price;
        if (goodsBean.getBzj()!=null){
            price = goodsBean.getIsSpecial() == 0?Float.parseFloat
                    (goodsBean.getBzj()): Float.parseFloat( goodsBean.getPrice());
        }else{
            price = Float.parseFloat( goodsBean.getPrice());
        }
        goodsBean.setAmount(price*qty);
        mData.get(position).setQty(qty);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void doDecrease(TextView tv_total,int position, String currentCount) {
        int qty = Integer.parseInt(currentCount);
        if(qty>0){
            IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean goodsBean = map.get(mData.get(position)
                    .getItemGroupName()).get(position);
            goodsBean.setQty(--qty);
            float price;
            if (goodsBean.getBzj()!=null){
                price = goodsBean.getIsSpecial() == 0?Float.parseFloat
                        (goodsBean.getBzj()): Float.parseFloat( goodsBean.getPrice());
            }else{
                price = Float.parseFloat( goodsBean.getPrice());
            }
            goodsBean.setAmount(price*qty);
            mData.get(position).setQty(qty);
            adapter.notifyDataSetChanged();
        }
    }
    /**
     * bannerFragment中的item被点击后会触发此回调
     * 然后再把选中的位置通知给所有的bannerFragment，让他们更新界面
     * 同时可以在此方法中实现点击事件,更新下面列表
      */

    @Override
    public void onClickPosition(int position,String tag) {
        List<IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean> goodsBeens = map.get(tags.get
                (position));
        mData.clear();
        mData.addAll(goodsBeens);
        adapter.notifyDataSetChanged();

        EventBus.getDefault().post(new MessageBean(position+"", data.getItemclassName(),isBack),
                "clickIndex");
    }

    @Override
    public void inputNum(int position,String num) {
        int qty = Integer.parseInt(num);
        IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean goodsBean = map.get(mData.get(position)
                .getItemGroupName()).get(position);
        goodsBean.setQty(qty);
        float price;
        if (goodsBean.getBzj()!=null){
            price = goodsBean.getIsSpecial() == 0?Float.parseFloat
                    (goodsBean.getBzj()): Float.parseFloat( goodsBean.getPrice());
        }else{
            price = Float.parseFloat( goodsBean.getPrice());
        }
        goodsBean.setAmount(price*qty);
        mData.get(position).setQty(qty);
        adapter.notifyDataSetChanged();
    }
}
