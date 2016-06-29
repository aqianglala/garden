package com.softgarden.garden.view.buy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.nineoldandroids.view.ViewHelper;
import com.softgarden.garden.global.BaseFragment;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.ScreenUtils;
import com.softgarden.garden.view.buy.LocalImageHolderView;
import com.softgarden.garden.view.buy.NetworkImageHolderView;
import com.softgarden.garden.view.buy.adapter.MyPagerAdapter;
import com.softgarden.garden.view.main.activity.MainActivity;
import com.softgarden.garden.view.shopcar.activity.ShopcarActivity;

import java.util.ArrayList;
import java.util.Arrays;
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
    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    private ViewPager viewPager;
    private int tabWidth;
    private RelativeLayout rl_indicator;
    private TextView tv_bread;
    private TextView tv_cake;

    private List<String> networkImages;
    private String[] images = {"http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg",
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://d.3987.com/sqmy_131219/001.jpg",
            "http://img2.3lian.com/2014/f2/37/d/39.jpg",
            "http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg",
            "http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg"
    };


    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_buy);
        iv_me = getViewById(R.id.iv_me);
        mActivity = (MainActivity)getActivity();
        iv_shopcar = getViewById(R.id.iv_shopCar);

        convenientBanner = getViewById(R.id.convenientBanner);
        tv_bread = getViewById(R.id.tv_bread);
        tv_cake = getViewById(R.id.tv_cake);
        // 设置tab的宽度
        setTabWidth();

        viewPager = getViewById(R.id.viewPager);
        // TODO: 2016/6/29 当获取到数据之后，需要将其传递给对应的fragment 
        ArrayList<BaseFragment> fragments = new ArrayList<>();
        FragmentProduct fragmentProduct1 = new FragmentProduct();
        FragmentProduct fragmentProduct2 = new FragmentProduct();
        fragments.add(fragmentProduct1);
        fragments.add(fragmentProduct2);

        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(myPagerAdapter);

        initRefreshLayout();
//        mRefreshLayout.beginRefreshing();
    }

    private void setTabWidth() {
        tabWidth = ScreenUtils.getScreenWidth(mActivity)/2;
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
        tv_bread.setOnClickListener(this);
        tv_cake.setOnClickListener(this);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 修改文字颜色
                int pink = mActivity.getResources().getColor(R.color.colorAccent);
                int black = mActivity.getResources().getColor(R.color.black_text);
                tv_bread.setTextColor(position ==0?pink:black);
                tv_cake.setTextColor(!(position ==0)?pink:black);
                float translationx = position * tabWidth + positionOffsetPixels/2;
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
        //本地图片例子
//        initData();
//        localBanner();
        //网络加载例子
        networkBanner();
        startTurning();
    }

    private void networkBanner() {
        networkImages= Arrays.asList(images);
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        },networkImages);
        convenientBanner.setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap
                .ic_page_indicator_focused});
    }

    private void localBanner() {
        convenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap
                        .ic_page_indicator_focused});
                //设置指示器的方向
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
//                .setOnPageChangeListener(this)//监听翻页事件
//                .setOnItemClickListener(this);
    }

    private void initData() {
        localImages.add(R.mipmap.banner);
        localImages.add(R.mipmap.banner);
        localImages.add(R.mipmap.banner);
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
            case R.id.tv_bread:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_cake:
                viewPager.setCurrentItem(1);
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
                SystemClock.sleep(3000);
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        getData();
                        refreshLayout.endRefreshing();
                    }
                });
            }
        }.start();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}
