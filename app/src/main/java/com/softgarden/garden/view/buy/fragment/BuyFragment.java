package com.softgarden.garden.view.buy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.google.gson.Gson;
import com.nineoldandroids.view.ViewHelper;
import com.softgarden.garden.base.BaseApplication;
import com.softgarden.garden.base.BaseFragment;
import com.softgarden.garden.base.EngineFactory;
import com.softgarden.garden.base.ObjectCallBack;
import com.softgarden.garden.dialog.LoadDialog;
import com.softgarden.garden.engine.BuyEngine;
import com.softgarden.garden.entity.IndexEntity;
import com.softgarden.garden.entity.TempData;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.other.ShoppingCart;
import com.softgarden.garden.utils.GlobalParams;
import com.softgarden.garden.utils.SPUtils;
import com.softgarden.garden.utils.ScreenUtils;
import com.softgarden.garden.utils.StringUtils;
import com.softgarden.garden.utils.UIUtils;
import com.softgarden.garden.view.buy.NetworkImageHolderView;
import com.softgarden.garden.view.buy.adapter.MyPagerAdapter;
import com.softgarden.garden.view.shopcar.activity.ShopcarActivity;
import com.softgarden.garden.view.start.activity.MainActivity;
import com.softgarden.garden.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by Hasee on 2016/6/6.
 */
public class BuyFragment extends BaseFragment implements BGARefreshLayout
        .BGARefreshLayoutDelegate,Observer{

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
    private TextView tv_count;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_buy);
        mActivity = (MainActivity)getActivity();

        tv_count = getViewById(R.id.tv_count);
        ll_tab_container = getViewById(R.id.ll_tab_container);
        iv_me = getViewById(R.id.iv_me);
        iv_shopcar = getViewById(R.id.iv_shopCar);
        convenientBanner = getViewById(R.id.convenientBanner);
        viewPager = getViewById(R.id.viewPager);
        // 设置viewpager不能滑动
        viewPager.setPagingEnabled(false);
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
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        // 访问网络
        // 当天不重复请求，隔天才请求新的数据
        String time = (String) SPUtils.get(mActivity, GlobalParams.LAST_UPDATE_TIME, "");
        String data = (String) SPUtils.get(mActivity, GlobalParams.DATA, "");
        if(StringUtils.getCurrDay().equals(time) && !TextUtils.isEmpty(data)){// 当天,从数据库中获取
            IndexEntity indexEntity = new Gson().fromJson(data, IndexEntity.class);
            // 获取购物车数据
            String shopcart_data = (String) SPUtils.get(mActivity, GlobalParams.SHOPCART_DATA, "");
            TempData tempData = new Gson().fromJson(shopcart_data, TempData.class);
            if(tempData!=null){
                BaseApplication.tempDataBeans = tempData.getTempDataBeans();
            }
            BaseApplication.indexEntity = indexEntity;
            setData(indexEntity);
            final LoadDialog loadDialog = LoadDialog.show(mActivity);
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    final ShoppingCart shoppingCart = ShoppingCart.getInstance();
                    // 添加监听
                    shoppingCart.addObserver(BuyFragment.this);
                    shoppingCart.initCartList();
                    UIUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadDialog.dismiss();
                            showToast(shoppingCart.getTotalNum()+"");
                            int totalNum = ShoppingCart.getInstance().getTotalNum();
                            if(totalNum>99){
                                tv_count.setText("99+");
                            }else{
                                tv_count.setText(totalNum+"");
                            }
                        }
                    });
                }
            }.start();
        }else{// 访问网络
            loadData();
        }
        int totalNum = ShoppingCart.getInstance().getTotalNum();
        if(totalNum>99){
            tv_count.setText("99+");
        }else{
            tv_count.setText(totalNum+"");
        }
    }

    /**
     * 访问网络
     */
    private void loadData() {
        BuyEngine engine = (BuyEngine) EngineFactory.getEngine(BuyEngine.class);
        engine.getProducts("GZ_0001", new ObjectCallBack<IndexEntity>(mActivity) {
            @Override
            public void onSuccess(IndexEntity indexEntity) {
                if(shouldClearCart()){//隔天了，清掉了购物车
                    SPUtils.put(mActivity,GlobalParams.SHOPCART_DATA,"");
                    SPUtils.put(mActivity,GlobalParams.DATA,"");
                    SPUtils.put(mActivity,GlobalParams.LAST_UPDATE_TIME,"");
                }
                // 更新数据
                SPUtils.put(mActivity,GlobalParams.DATA,new Gson().toJson(indexEntity));
                // 将此数据做为全局变量，为了减少对数据库的操作
                SPUtils.put(mActivity, GlobalParams.LAST_UPDATE_TIME,StringUtils.getCurrDay());

                BaseApplication.indexEntity = indexEntity;
                setData(indexEntity);
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        final ShoppingCart shoppingCart = ShoppingCart.getInstance();
                        // 添加监听
                        shoppingCart.addObserver(BuyFragment.this);
                        shoppingCart.initCartList();
                        UIUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int totalNum = shoppingCart.getTotalNum();
                                if(totalNum>99){
                                    tv_count.setText("99+");
                                }else{
                                    tv_count.setText(totalNum+"");
                                }
                                showToast(shoppingCart.getTotalNum()+"");
                            }
                        });
                    }
                }.start();
            }

            @Override
            public void onError(String s, String s1, int i) {
                super.onError(s, s1, i);
                mRefreshLayout.endRefreshing();
            }
        });
    }

    private void setData(IndexEntity indexEntity) {
        //设置banner
        networkImages = indexEntity.getData().getBanner();
        networkBanner();
        // 设置tab的宽度
        tabCount = indexEntity.getData().getShop().size();
        setTabWidth();
        // 动态生成fragment，并设置其一级id，让对应的fragment请求自己的数据
        fragments.clear();
        tabViews.clear();
        ll_tab_container.removeAllViews();
        for(int i=0;i<tabCount;i++){
            // 动态生成tab
            addTab(indexEntity, i);
            // 动态生成fragment
            addFragment(indexEntity, fragments, i);
        }
        // 设置能否滚动
        if (myPagerAdapter == null){
            myPagerAdapter = new MyPagerAdapter(getChildFragmentManager(), fragments);
            viewPager.setAdapter(myPagerAdapter);
        }else{
            // 暂时无效
            myPagerAdapter.notifyDataSetChanged();
        }
        mRefreshLayout.endRefreshing();
    }

    /**
     * 动态添加fragment
     * @param indexEntity
     * @param fragments
     * @param i
     */
    private void addFragment(IndexEntity indexEntity, ArrayList<BaseFragment> fragments, int i) {
        FragmentProduct fragmentProduct = new FragmentProduct();
        IndexEntity.DataBean.ShopBean shopBean = indexEntity.getData().getShop().get(i);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",shopBean );
        fragmentProduct.setArguments(bundle);
        fragments.add(fragmentProduct);
    }

    /**
     * 动态添加tab
     * @param indexEntity
     * @param i
     */
    private void addTab(final IndexEntity indexEntity, final int i) {
        TextView tabView = (TextView) LayoutInflater.from(mActivity).inflate(R.layout.layout_tab,
                ll_tab_container, false);
        tabView.setText(indexEntity.getData().getShop().get(i).getItemclassName());
        if (i == 0){
            tabView.setTextColor(mActivity.getResources().getColor(R.color.colorAccent));
        }
        tabView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 判断能否滑动，为空提示：您没有权限购买此类产品
                int size = indexEntity.getData().getShop().get(i).getChild().size();
                if(size>0){
                    viewPager.setCurrentItem(i);
                    // 修改文字颜色
                    int pink = mActivity.getResources().getColor(R.color.colorAccent);
                    int black = mActivity.getResources().getColor(R.color.black_text);
                    int count = tabViews.size();
                    for(int j=0;j<count;j++){
                        tabViews.get(j).setTextColor(j==i?pink:black);
                    }
                    float translationx = i * tabWidth;
                    ViewHelper.setTranslationX(rl_indicator,translationx);
                }else{
                    showToast("您没有权限购买此类产品！");
                }
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
                if(tv_count.getText().toString().trim().equals("0")){
                    showToast("购物车空空如也~~");
                }else{
                    startActivity(new Intent(mActivity, ShopcarActivity.class));
                }
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

    public boolean shouldClearCart(){
        String last_update_time = (String) SPUtils.get(mActivity, GlobalParams.LAST_UPDATE_TIME, "");
        if(StringUtils.getCurrDay().equals(last_update_time)){
            return false;
        }else{
            return true;
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

    @Override
    public void update(Observable observable, Object data) {
        int totalNum = ShoppingCart.getInstance().getTotalNum();
        if(totalNum>99){
            tv_count.setText("99+");
        }else{
            tv_count.setText(totalNum+"");
        }
    }
}