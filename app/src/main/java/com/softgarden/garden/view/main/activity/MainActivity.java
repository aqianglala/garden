package com.softgarden.garden.view.main.activity;

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
import com.softgarden.garden.base.BaseActivity;
import com.softgarden.garden.interfaces.UrlsAndKeys;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.LogUtils;
import com.softgarden.garden.utils.SPUtils;
import com.softgarden.garden.utils.ScreenUtils;
import com.softgarden.garden.view.back.fragment.BackFragment;
import com.softgarden.garden.view.buy.fragment.BuyFragment;
import com.softgarden.garden.view.historyOrders.fragment.OrderFragment;
import com.softgarden.garden.view.main.entity.MessageBean;
import com.softgarden.garden.view.main.ModifyPswdDialog;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

public class MainActivity extends BaseActivity {

    private RadioGroup radioGroup;
    private BuyFragment buyFragment;
    private BackFragment backFragment;
    private BackFragment changeFragment;
    private OrderFragment orderFragment;
    private SlidingMenu menu;
    private boolean hasModify;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            setViewHeight();
        }
        // register the receiver object
        EventBus.getDefault().register(this);
        hasModify = (boolean) SPUtils.get(context, UrlsAndKeys.HASMODIFYPSWD,false);
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
                        LogUtils.e(TAG,"buy");
                        if (buyFragment == null) {
                            buyFragment = new BuyFragment();
                            ft.add(R.id.fl_content, buyFragment);
                        } else {
                            ft.show(buyFragment);
                        }
                        buyFragment.startTurning();
                        break;
                    case R.id.rb_back:
                        if (backFragment == null) {
                            backFragment = new BackFragment();
                            Bundle backBundle = new Bundle();
                            backBundle.putBoolean("isBack",true);
                            backFragment.setArguments(backBundle);
                            ft.add(R.id.fl_content, backFragment);
                        } else {
                            ft.show(backFragment);
                        }
                        buyFragment.stopTurning();
                        break;
                    case R.id.rb_change:
                        if (changeFragment == null) {
                            changeFragment = new BackFragment();
                            Bundle changeBundle = new Bundle();
                            changeBundle.putBoolean("isBack",false);
                            changeFragment.setArguments(changeBundle);
                            ft.add(R.id.fl_content, changeFragment);
                        } else {
                            ft.show(changeFragment);
                        }
                        buyFragment.stopTurning();
                        break;
                    case R.id.rb_orders:
                        if (orderFragment == null) {
                            orderFragment = new OrderFragment();
                            ft.add(R.id.fl_content, orderFragment);
                        } else {
                            ft.show(orderFragment);
                        }
                        buyFragment.stopTurning();
                        break;
                }
                ft.commit();
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        radioGroup.check(R.id.rb_buy);
        if(!hasModify){
            showDialog();
        }
    }

    private void showDialog() {
        ModifyPswdDialog dialog = new ModifyPswdDialog(this, R.style.CustomDialog);
        dialog.setCancelable(false);
        dialog.show();
        // 设置宽，高可在xml布局中写上,但宽度默认是match_parent，所以需要在代码中设置
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        attributes.width = (int) (ScreenUtils.getScreenWidth(this)*0.8);
        attributes.height = (int) (ScreenUtils.getScreenWidth(this)*0.9);
        dialog.getWindow().setAttributes(attributes);
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

    // 开始自动翻页
    @Override
    protected void onResume() {
        super.onResume();
        //开始自动翻页
        if(buyFragment!=null){
            buyFragment.startTurning();
        }
    }

    // 停止自动翻页
    @Override
    protected void onPause() {
        super.onPause();
        //停止翻页
        if(buyFragment!=null){
            buyFragment.stopTurning();
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
