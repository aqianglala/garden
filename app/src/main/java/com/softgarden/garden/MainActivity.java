package com.softgarden.garden;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.softgarden.garden.global.BaseActivity;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.L;
import com.softgarden.garden.view.back.BackFragment;
import com.softgarden.garden.view.buy.BuyFragment;
import com.softgarden.garden.view.change.ChangeFragment;
import com.softgarden.garden.view.historyOrders.OrderFragment;

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
        Window window = getWindow();
        //4.4版本及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        //5.0版本及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        // 设置侧滑菜单
        initMenu();
    }

    private void initMenu() {
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.layout_menu);
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
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
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

    public void toggle(){
        menu.toggle();
    }

}
