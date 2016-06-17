package com.softgarden.garden;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.softgarden.garden.global.BaseActivity;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.L;
import com.softgarden.garden.utils.ScreenUtils;
import com.softgarden.garden.view.back.BackFragment;
import com.softgarden.garden.view.buy.BuyFragment;
import com.softgarden.garden.view.change.ChangeFragment;
import com.softgarden.garden.view.historyOrders.OrderFragment;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

public class MainActivity extends BaseActivity {

    private RadioGroup radioGroup;
    private BuyFragment buyFragment;
    private BackFragment backFragment;
    private ChangeFragment changeFragment;
    private OrderFragment orderFragment;
    private SlidingMenu menu;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            setViewHeight();
        }
        // register the receiver object
        EventBus.getDefault().register(this);

        menu = getViewById(R.id.slidingmenulayout);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
    }

    @Override
    protected void setListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction ft = getSupportFragmentManager()
                        .beginTransaction();
                hideFragments(ft);//先隐藏掉所有的fragment
                switch (checkedId){
                    case R.id.rb_buy:
                        L.e(TAG,"buy");
                        if (buyFragment == null) {
                            buyFragment = new BuyFragment();
                            ft.add(R.id.fl_content, buyFragment);
                        } else {
                            ft.show(buyFragment);
                        }
                        break;
                    case R.id.rb_back:
                        if (backFragment == null) {
                            backFragment = new BackFragment();
                            ft.add(R.id.fl_content, backFragment);
                        } else {
                            ft.show(backFragment);
                        }
                        break;
                    case R.id.rb_change:
                        if (changeFragment == null) {
                            changeFragment = new ChangeFragment();
                            ft.add(R.id.fl_content, changeFragment);
                        } else {
                            ft.show(changeFragment);
                        }
                        break;
                    case R.id.rb_orders:
                        if (orderFragment == null) {
                            orderFragment = new OrderFragment();
                            ft.add(R.id.fl_content, orderFragment);
                        } else {
                            ft.show(orderFragment);
                        }
                        break;
                }
                ft.commit();
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        radioGroup.check(R.id.rb_buy);
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     * @param transaction
     *            用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (buyFragment != null) {
            transaction.hide(buyFragment);
        }
        if (backFragment != null) {
            transaction.hide(backFragment);
        }
        if (changeFragment != null) {
            transaction.hide(changeFragment);
        }
        if (orderFragment != null) {
            transaction.hide(orderFragment);
        }
    }

    private long mExitTime;
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(menu.isMenuShowing()){
               menu.showContent();
            }else{
                if ((System.currentTimeMillis() - mExitTime) > 2000) {
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    mExitTime = System.currentTimeMillis();
                } else {
                    finish();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if(menu.isMenuShowing()){
            menu.showContent();
        }
    }

    @Override
    protected void onDestroy() {
        // Don’t forget to unregister !!
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscriber(tag = "my_tag")
    private void closeMenu(MessageBean user) {
        Log.e("", "### update user with my_tag, name = " + user.message);
        menu.showContent();
    }

    public void toggle(){
        menu.toggle();
    }

    private void setViewHeight() {
        View view_content = findViewById(R.id.view_content);
        view_content.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        ViewGroup.LayoutParams content_params = view_content.getLayoutParams();
        content_params.height = ScreenUtils.getStatusBarHeight(this);
        view_content.setLayoutParams(content_params);

        View view_menu = findViewById(R.id.view_menu);
        view_menu.setBackgroundColor(Color.WHITE);
        ViewGroup.LayoutParams menu_params = view_menu.getLayoutParams();
        menu_params.height = ScreenUtils.getStatusBarHeight(this);
        view_menu.setLayoutParams(menu_params);
    }

}
