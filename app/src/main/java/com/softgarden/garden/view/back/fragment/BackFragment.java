package com.softgarden.garden.view.back.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.softgarden.garden.base.BaseApplication;
import com.softgarden.garden.base.BaseFragment;
import com.softgarden.garden.entity.IndexEntity;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.ScreenUtils;
import com.softgarden.garden.utils.UIUtils;
import com.softgarden.garden.view.back.adapter.ContainerPagerAdapter;
import com.softgarden.garden.view.start.activity.MainActivity;

import java.util.ArrayList;

/**
 * Created by Hasee on 2016/6/6.
 */
public class BackFragment extends BaseFragment{

    private ImageView iv_me;
    private MainActivity mActivity;
    private TextView tv_bread;

    private RelativeLayout rl_indicator;
    private LinearLayout ll_tab;
    private LinearLayout ll_tab_container;

    private int tabCount;
    private int tabWidth;
    private ViewPager vp_content;
    private ArrayList<TextView> tabViews =  new ArrayList<TextView>();;

    private ArrayList<BaseFragment> fragments = new ArrayList<>();
    private boolean isBack;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_back);
        Bundle arguments = getArguments();
        isBack = arguments.getBoolean("isBack");

        mActivity = (MainActivity)getActivity();
        iv_me = getViewById(R.id.iv_me);

        rl_indicator = getViewById(R.id.rl_indicator);
        ll_tab = getViewById(R.id.ll_tab);
        ll_tab_container = getViewById(R.id.ll_tab_container);

        vp_content = getViewById(R.id.vp_content);

        // 动态添加tab和fragment,ll_tab的最大宽度为240dp
        tabCount = BaseApplication.indexEntity.getData().getShop().size();
        setTabWidth();

        // 动态生成fragment，并设置其一级id，让对应的fragment请求自己的数据

        fragments.clear();
        ll_tab_container.removeAllViews();
        for(int i=0;i<tabCount;i++){
            // 动态生成tab
            addTab(i);
            // 动态生成fragment
            addFragment(i);
        }
        ContainerPagerAdapter contentPagerAdapter = new ContainerPagerAdapter(getChildFragmentManager(),
                fragments);
        vp_content.setAdapter(contentPagerAdapter);
    }

    @Override
    protected void setListener() {
        iv_me.setOnClickListener(this);
        ll_tab.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tabWidth = ll_tab.getWidth()/2;
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rl_indicator
                        .getLayoutParams();
                layoutParams.width = tabWidth;
                rl_indicator.setLayoutParams(layoutParams);
                ll_tab.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        vp_content.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int screenWidth = ScreenUtils.getScreenWidth(mActivity);
                float i = (float)screenWidth / tabWidth;
                float translationx = position * tabWidth + positionOffsetPixels/i;
                ViewHelper.setTranslationX(rl_indicator,translationx);
            }

            @Override
            public void onPageSelected(int position) {
                changeTitleState(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        changeTitleState(0);
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
        }
    }

    /**
     * 改变标题状态
     */
    private void changeTitleState(int position) {
        // 缩放标题
        for (int i=0;i<tabViews.size();i++){
            if(i == position){
                scaleTitle(true ? 1.2f : 1.0f, tabViews.get(i));
            }else{
                scaleTitle(false ? 1.2f : 1.0f, tabViews.get(i));
            }
        }
    }

    /**
     * 缩放标题
     * @param sclae 缩放比例
     * @param textview
     */
    private void scaleTitle(float sclae, TextView textview) {
        ViewPropertyAnimator.animate(textview).scaleX(sclae).scaleY(sclae);
    }

    private void addTab(final int i) {
        TextView tabView = (TextView) LayoutInflater.from(mActivity).inflate(R.layout.layout_item_tab_thh,
                ll_tab_container, false);
        tabView.setText(BaseApplication.indexEntity.getData().getShop().get(i).getItemclassName());
        tabView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_content.setCurrentItem(i);
            }
        });
        tabViews.add(tabView);
        ll_tab_container.addView(tabView);
    }

    /**
     * 计算每个tab的宽度
     */
    private void setTabWidth() {
        tabWidth = UIUtils.dip2px(60);
        ViewGroup.LayoutParams layoutParams_ll_tab = ll_tab.getLayoutParams();
        layoutParams_ll_tab.width = tabWidth * tabCount;
        ll_tab.setLayoutParams(layoutParams_ll_tab);

        rl_indicator = getViewById(R.id.rl_indicator);
        ViewGroup.LayoutParams layoutParams = rl_indicator
                .getLayoutParams();
        layoutParams.width = tabWidth;
        rl_indicator.setLayoutParams(layoutParams);
    }

    /**
     * 动态添加fragment
     * @param i
     */
    private void addFragment(int i) {
        BreadCakeFragment fragmentProduct = new BreadCakeFragment();
        IndexEntity.DataBean.ShopBean shopBean = BaseApplication.indexEntity.getData().getShop().get(i);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isBack",isBack);
        bundle.putSerializable("data",shopBean );
        fragmentProduct.setArguments(bundle);
        fragments.add(fragmentProduct);
    }

}
