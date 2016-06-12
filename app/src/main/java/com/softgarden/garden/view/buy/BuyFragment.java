package com.softgarden.garden.view.buy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.softgarden.garden.MainActivity;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.global.BaseFragment;
import com.softgarden.garden.view.shopcar.ShopcarActivity;

/**
 * Created by Hasee on 2016/6/6.
 */
public class BuyFragment extends BaseFragment {

    private ImageView iv_me;
    private ImageView iv_shopcar;
    private MainActivity mActivity;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_buy);
        iv_me = getViewById(R.id.iv_me);
        mActivity = (MainActivity)getActivity();
        iv_shopcar = getViewById(R.id.iv_shopCar);
    }

    @Override
    protected void setListener() {
        iv_me.setOnClickListener(this);
        iv_shopcar.setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

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
}
