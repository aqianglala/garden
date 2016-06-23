package com.softgarden.garden.view.back.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.softgarden.garden.view.main.activity.MainActivity;
import com.softgarden.garden.global.BaseFragment;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.ScreenUtils;
import com.softgarden.garden.view.back.adapter.ContainerPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Hasee on 2016/6/6.
 */
public class BackFragment extends BaseFragment{

    private ImageView iv_me;
    private MainActivity mActivity;
    private TextView tv_bread;
    private TextView tv_cake;

    private RelativeLayout rl_indicator;
    private LinearLayout ll_tab;

    private int tabWidth;
    private ViewPager vp_content;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_back);
        Bundle arguments = getArguments();
        boolean isBack = arguments.getBoolean("isBack");

        mActivity = (MainActivity)getActivity();
        iv_me = getViewById(R.id.iv_me);

        tv_bread = getViewById(R.id.tv_bread);
        tv_cake = getViewById(R.id.tv_cake);

        rl_indicator = getViewById(R.id.rl_indicator);
        ll_tab = getViewById(R.id.ll_tab);

        vp_content = getViewById(R.id.vp_content);

        ArrayList<BaseFragment> fragments = new ArrayList<>();
        BreadCakeFragment breadFragment = new BreadCakeFragment();
        Bundle breadBundle = new Bundle();
        breadBundle.putBoolean("isBread",true);
        breadBundle.putBoolean("isBack",isBack);
        breadFragment.setArguments(breadBundle);

        BreadCakeFragment cakeFragment = new BreadCakeFragment();
        Bundle cakeBundle = new Bundle();
        cakeBundle.putBoolean("isBread",false);
        cakeBundle.putBoolean("isBack",isBack);
        cakeFragment.setArguments(cakeBundle);

        fragments.add(breadFragment);
        fragments.add(cakeFragment);
        ContainerPagerAdapter contentPagerAdapter = new ContainerPagerAdapter(getChildFragmentManager(),
                fragments);
        vp_content.setAdapter(contentPagerAdapter);
    }

    @Override
    protected void setListener() {
        iv_me.setOnClickListener(this);
        tv_bread.setOnClickListener(this);
        tv_cake.setOnClickListener(this);
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
                changeTitleState(position==0?true:false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        changeTitleState(true);
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
            case R.id.tv_bread:
                vp_content.setCurrentItem(0);
                break;
            case R.id.tv_cake:
                vp_content.setCurrentItem(1);
                break;
        }
    }

    /**
     * 改变标题状态
     * @param isSelectBread true代表选择的是视频
     */
    private void changeTitleState(boolean isSelectBread) {
        // 缩放标题
        scaleTitle(isSelectBread ? 1.2f : 1.0f, tv_bread);
        scaleTitle(!isSelectBread ? 1.2f : 1.0f, tv_cake);
    }

    /**
     * 缩放标题
     * @param sclae 缩放比例
     * @param textview
     */
    private void scaleTitle(float sclae, TextView textview) {
        ViewPropertyAnimator.animate(textview).scaleX(sclae).scaleY(sclae);
    }

}
