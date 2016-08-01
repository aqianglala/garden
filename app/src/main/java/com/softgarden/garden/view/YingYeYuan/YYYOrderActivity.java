package com.softgarden.garden.view.YingYeYuan;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.softgarden.garden.base.BaseActivity;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.view.historyOrders.fragment.OrderFragment;

import org.simple.eventbus.EventBus;

public class YYYOrderActivity extends BaseActivity {

    private RadioGroup radioGroup;
    private OrderFragment orderFragment;
    private RadioButton rb_orders;
    private String name;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_yyy_order);
        // register the receiver object
        EventBus.getDefault().register(this);

        name = getIntent().getStringExtra("name");

        rb_orders = getViewById(R.id.rb_orders);
        radioGroup = getViewById(R.id.radioGroup);
    }

    @Override
    protected void setListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction ft = getSupportFragmentManager()
                        .beginTransaction();
                switch (checkedId){
                    case R.id.rb_orders:
                        hideFragments(ft);//先隐藏掉所有的fragment
                        if (orderFragment == null) {
                            orderFragment = new OrderFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("name",name);
                            orderFragment.setArguments(bundle);
                            ft.add(R.id.fl_content, orderFragment);
                        } else {
                            ft.show(orderFragment);
                        }
                        break;
                }
                ft.commitAllowingStateLoss();
            }
        });
    }


    @Override
    protected void processLogic(Bundle savedInstanceState) {
        radioGroup.check(R.id.rb_orders);
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     * @param transaction
     *            用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (orderFragment != null) {
            transaction.hide(orderFragment);
        }
    }

    @Override
    protected void onDestroy() {
        // Don’t forget to unregister !!
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
