package com.softgarden.garden.view.back;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nineoldandroids.view.ViewPropertyAnimator;
import com.softgarden.garden.MainActivity;
import com.softgarden.garden.global.BaseFragment;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.view.shopcar.ShopcarActivity;

/**
 * Created by Hasee on 2016/6/6.
 */
public class BackFragment extends BaseFragment{

    private ImageView iv_me;
    private ImageView iv_shopcar;
    private MainActivity mActivity;
    private TextView tv_bread;
    private TextView tv_cake;
    private View bread_indicator;
    private View cake_indicator;

    private BreadCakeFragment breadFragment;
    private BreadCakeFragment cakeFragment;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_back);
        mActivity = (MainActivity)getActivity();
        iv_me = getViewById(R.id.iv_me);
        iv_shopcar = getViewById(R.id.iv_shopCar);

        tv_bread = getViewById(R.id.tv_bread);
        tv_cake = getViewById(R.id.tv_cake);
        bread_indicator = getViewById(R.id.bread_indicator);
        cake_indicator = getViewById(R.id.cake_indicator);
    }

    @Override
    protected void setListener() {
        iv_me.setOnClickListener(this);
        iv_shopcar.setOnClickListener(this);
        tv_bread.setOnClickListener(this);
        tv_cake.setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        changeTitleState(true);
        if (breadFragment == null) {
            FragmentTransaction ft = mActivity.getSupportFragmentManager()
                    .beginTransaction();
            breadFragment = new BreadCakeFragment();
            Bundle bundle1 = new Bundle();
            bundle1.putBoolean("isBread",true);
            breadFragment.setArguments(bundle1);
            ft.add(R.id.fl_container, breadFragment);
            ft.commit();
        }
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
                changeTitleState(true);
                FragmentTransaction ft1 = mActivity.getSupportFragmentManager()
                        .beginTransaction();
                hideFragments(ft1);//先隐藏掉所有的fragment
                ft1.show(breadFragment);
                ft1.commit();
                break;
            case R.id.tv_cake:
                changeTitleState(false);
                FragmentTransaction ft2 = mActivity.getSupportFragmentManager()
                        .beginTransaction();
                hideFragments(ft2);//先隐藏掉所有的fragment
                if (cakeFragment == null) {
                    cakeFragment = new BreadCakeFragment();
                    Bundle bundle2 = new Bundle();
                    bundle2.putBoolean("isBread",false);
                    cakeFragment.setArguments(bundle2);
                    ft2.add(R.id.fl_container, cakeFragment);
                } else {
                    ft2.show(cakeFragment);
                }
                ft2.commit();
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
        bread_indicator.setVisibility(isSelectBread?View.VISIBLE:View.INVISIBLE);
        cake_indicator.setVisibility(!isSelectBread?View.VISIBLE:View.INVISIBLE);
    }

    /**
     * 缩放标题
     * @param sclae 缩放比例
     * @param textview
     */
    private void scaleTitle(float sclae, TextView textview) {
        ViewPropertyAnimator.animate(textview).scaleX(sclae).scaleY(sclae);
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     * @param transaction
     *            用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (breadFragment != null) {
            transaction.hide(breadFragment);
        }
        if (cakeFragment != null) {
            transaction.hide(cakeFragment);
        }
    }
}
