package com.softgarden.garden.view.buy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.nineoldandroids.view.ViewHelper;
import com.softgarden.garden.base.BaseFragment;
import com.softgarden.garden.base.EngineFactory;
import com.softgarden.garden.base.ObjectCallBack;
import com.softgarden.garden.engine.BuyEngine;
import com.softgarden.garden.entity.IndexEntity;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.ScreenUtils;
import com.softgarden.garden.view.buy.NetworkImageHolderView;
import com.softgarden.garden.view.buy.adapter.MyPagerAdapter;
import com.softgarden.garden.view.main.activity.MainActivity;
import com.softgarden.garden.view.shopcar.activity.ShopcarActivity;
import com.softgarden.garden.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by Hasee on 2016/6/6.
 */
public class BuyFragment extends BaseFragment implements BGARefreshLayout
        .BGARefreshLayoutDelegate{

    private ImageView iv_me;
    private ImageView iv_shopcar;
    private MainActivity mActivity;

    private ConvenientBanner convenientBanner;//顶部广告栏控件
    private CustomViewPager viewPager;
    private int tabWidth;
    private RelativeLayout rl_indicator;

    private List<String> networkImages;
    private LinearLayout ll_tab_container;
    private ArrayList<TextView> tabViews =  new ArrayList<TextView>();;
    private int tabCount;
    private ArrayList<BaseFragment> fragments = new ArrayList<>();
    private MyPagerAdapter myPagerAdapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_buy);
        mActivity = (MainActivity)getActivity();

        ll_tab_container = getViewById(R.id.ll_tab_container);
        iv_me = getViewById(R.id.iv_me);
        iv_shopcar = getViewById(R.id.iv_shopCar);
        convenientBanner = getViewById(R.id.convenientBanner);
        viewPager = getViewById(R.id.viewPager);
        initRefreshLayout();
//        mRefreshLayout.beginRefreshing();
    }

    /**
     * 计算每个tab的宽度
     */
    private void setTabWidth() {
        tabWidth = ScreenUtils.getScreenWidth(mActivity)/tabCount;
        rl_indicator = getViewById(R.id.rl_indicator);
        ViewGroup.LayoutParams layoutParams = rl_indicator
                .getLayoutParams();
        layoutParams.width = tabWidth;
        rl_indicator.setLayoutParams(layoutParams);
    }


    @Override
    protected void setListener() {
        iv_me.setOnClickListener(this);
        iv_shopcar.setOnClickListener(this);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 修改文字颜色
                int pink = mActivity.getResources().getColor(R.color.colorAccent);
                int black = mActivity.getResources().getColor(R.color.black_text);
                int size = tabViews.size();
                for(int i=0;i<size;i++){
                    tabViews.get(i).setTextColor(i==position?pink:black);
                }
                float translationx = position * tabWidth + positionOffsetPixels/tabCount;
                ViewHelper.setTranslationX(rl_indicator,translationx);
            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        // 访问网络
        loadData();
    }

    /**
     * 访问网络
     */
    private void loadData() {
        BuyEngine engine = (BuyEngine) EngineFactory.getEngine(BuyEngine.class);
        String userId = mActivity.getUserId();
        engine.getProducts(userId, null, null, new ObjectCallBack<IndexEntity>(mActivity) {
            @Override
            public void onSuccess(IndexEntity indexEntity) {
                // 是否显示退换货tab
                String thh_tui = indexEntity.getData().getThh().getThh_tui();
                String thh_huan = indexEntity.getData().getThh().getThh_huan();
                // 设置是否显示退换货按钮
                mActivity.isShowThh(thh_tui, thh_huan);
                //设置banner
                networkImages = indexEntity.getData().getBanner();
                networkBanner();
                // 设置tab的宽度
                tabCount = indexEntity.getData().getYiji().size();
                setTabWidth();
                // 动态生成fragment，并设置其一级id，让对应的fragment请求自己的数据
                fragments.clear();
                tabViews.clear();
                ll_tab_container.removeAllViews();
                for(int i=0;i<tabCount;i++){
                    // 动态生成tab
                    addTab(indexEntity, tabViews, i);
                    // 动态生成fragment
                    addFragment(indexEntity, fragments, i);
                }

                // 设置能否滚动
                if (myPagerAdapter == null){
                    myPagerAdapter = new MyPagerAdapter(getChildFragmentManager(), fragments);
                    viewPager.setAdapter(myPagerAdapter);
                }else{
                    myPagerAdapter.notifyDataSetChanged();
                }
                mRefreshLayout.endRefreshing();
            }

            @Override
            public void onError(String s, String s1, int i) {
                super.onError(s, s1, i);
                mRefreshLayout.endRefreshing();
            }
        });
    }

    /**
     * 动态添加fragment
     * @param indexEntity
     * @param fragments
     * @param i
     */
    private void addFragment(IndexEntity indexEntity, ArrayList<BaseFragment> fragments, int i) {
        FragmentProduct fragmentProduct = new FragmentProduct();
        String yiji_id = indexEntity.getData().getYiji().get(i).getId();
        Bundle bundle = new Bundle();
        bundle.putString("yiji_id",yiji_id );
        fragmentProduct.setArguments(bundle);
        fragments.add(fragmentProduct);
    }

    /**
     * 动态添加tab
     * @param indexEntity
     * @param tabViews
     * @param i
     */
    private void addTab(final IndexEntity indexEntity, ArrayList<TextView> tabViews, final int i) {
        TextView tabView = (TextView) LayoutInflater.from(mActivity).inflate(R.layout.layout_tab,
                ll_tab_container, false);
        tabView.setText(indexEntity.getData().getYiji().get(i).getTitle());
        tabView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(i);
            }
        });
        tabViews.add(tabView);
        ll_tab_container.addView(tabView);
    }

    /**
     * 设置banner
     */
    private void networkBanner() {
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        },networkImages);
        convenientBanner.setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap
                .ic_page_indicator_focused});
        startTurning();
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.iv_me:
                mActivity.toggle();
                break;
            case R.id.iv_shopCar:
                startActivity(new Intent(mActivity, ShopcarActivity.class));
                break;
        }
    }

    public void startTurning(){
        if(convenientBanner!=null){
            convenientBanner.startTurning(5000);
        }
    }

    public void stopTurning(){
        if(convenientBanner!=null){
            convenientBanner.stopTurning();
        }
    }
    private BGARefreshLayout mRefreshLayout;
    private void initRefreshLayout() {
        mRefreshLayout = getViewById(R.id.rl_modulename_refresh);
        mRefreshLayout.setIsShowLoadingMoreView(false);
        // 为BGARefreshLayout设置代理
        mRefreshLayout.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(mActivity, true);
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(final BGARefreshLayout refreshLayout) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                loadData();
            }
        }.start();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}
