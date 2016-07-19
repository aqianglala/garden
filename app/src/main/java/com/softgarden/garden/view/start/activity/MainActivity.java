package com.softgarden.garden.view.start.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.softgarden.garden.base.BaseActivity;
import com.softgarden.garden.base.BaseApplication;
import com.softgarden.garden.dialog.ModifyDialog;
import com.softgarden.garden.entity.TempData;
import com.softgarden.garden.interfaces.UrlsAndKeys;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.other.ShoppingCart;
import com.softgarden.garden.utils.GlobalParams;
import com.softgarden.garden.utils.SPUtils;
import com.softgarden.garden.utils.ScreenUtils;
import com.softgarden.garden.view.back.fragment.BackFragment;
import com.softgarden.garden.view.buy.fragment.BuyFragment;
import com.softgarden.garden.view.historyOrders.fragment.OrderFragment;
import com.softgarden.garden.view.start.entity.MessageBean;

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
    private RadioButton rb_back;
    private RadioButton rb_change;
    private RadioButton rb_buy;
    private RadioButton rb_orders;
    private int lastCheckId;
    private int returnType;
    private ModifyDialog dialog;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        // 由于有slidingmenu，因此需要
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            setViewHeight();
        }
        // register the receiver object
        EventBus.getDefault().register(this);
        returnType = Integer.parseInt(BaseApplication.userInfo.getData()
                .getReturnType());
        // 是否已经改过密码
        hasModify = (boolean) SPUtils.get(context, UrlsAndKeys.HASMODIFYPSWD,false);

        menu = getViewById(R.id.slidingmenulayout);
        rb_back = getViewById(R.id.rb_back);
        rb_change = getViewById(R.id.rb_change);
        rb_buy = getViewById(R.id.rb_buy);
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
                    case R.id.rb_buy:
                        lastCheckId = checkedId;
                        hideFragments(ft);//先隐藏掉所有的fragment
                        if (buyFragment == null) {
                            buyFragment = new BuyFragment();
                            ft.add(R.id.fl_content, buyFragment);
                        } else {
                            ft.show(buyFragment);
                            buyFragment.startTurning();
                        }
                        break;
                    case R.id.rb_back:
                        rb_back.setChecked(returnType == 1 || returnType ==3?true:false);
                        if(rb_back.isChecked()){
                            lastCheckId = checkedId;
                            hideFragments(ft);//先隐藏掉所有的fragment
                            if (backFragment == null) {
                                backFragment = new BackFragment();
                                Bundle backBundle = new Bundle();
                                backBundle.putBoolean("isBack",true);
                                backFragment.setArguments(backBundle);
                                ft.add(R.id.fl_content, backFragment);
                            } else {
                                ft.show(backFragment);
                            }
                        }else{
                            setcheck(lastCheckId);
                            // 显示对话框
                            showToast("您没有此类的权限！");
                        }
                        break;
                    case R.id.rb_change:
                        rb_change.setChecked(returnType == 2 || returnType ==3?true:false);
                        if(rb_change.isChecked()){
                            lastCheckId = checkedId;
                            hideFragments(ft);//先隐藏掉所有的fragment
                            if (changeFragment == null) {
                                changeFragment = new BackFragment();
                                Bundle changeBundle = new Bundle();
                                changeBundle.putBoolean("isBack",false);
                                changeFragment.setArguments(changeBundle);
                                ft.add(R.id.fl_content, changeFragment);
                            } else {
                                ft.show(changeFragment);
                            }
                        }else{
                            // 上一个要check
                            setcheck(lastCheckId);
                            // 显示对话框
                            showToast("您没有此类的权限！");
                        }
                        break;
                    case R.id.rb_orders:
                        lastCheckId = checkedId;
                        hideFragments(ft);//先隐藏掉所有的fragment
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

    private void setcheck(int lastCheckId) {
        switch (lastCheckId){
            case R.id.rb_buy:
                rb_buy.setChecked(true);
                break;
            case R.id.rb_back:
                rb_back.setChecked(true);
                break;
            case R.id.rb_change:
                rb_change.setChecked(true);
                break;
            case R.id.rb_orders:
                rb_orders.setChecked(true);
                break;
        }
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        radioGroup.check(R.id.rb_buy);
        if(!hasModify){
            dialog = ModifyDialog.show(this);
        }
    }

//    private void showDialog() {
//        ModifyPswdDialog dialog = new ModifyPswdDialog(this, R.style.CustomDialog);
//        dialog.setCancelable(false);
//        dialog.show();
//        // 设置宽，高可在xml布局中写上,但宽度默认是match_parent，所以需要在代码中设置
//        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
//        attributes.width = (int) (ScreenUtils.getScreenWidth(this)*0.8);
//        attributes.height = (int) (ScreenUtils.getScreenWidth(this)*0.9);
//        dialog.getWindow().setAttributes(attributes);
//    }

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
                    // 保存数据到本地
                    TempData tempData = new TempData();
                    tempData.setTempDataBeans(BaseApplication.tempDataBeans);
                    String shopcart_data = new Gson().toJson(tempData);
                    SPUtils.put(this, GlobalParams.SHOPCART_DATA,shopcart_data);
                    // 清空全局变量
                    BaseApplication.indexEntity = null;
                    BaseApplication.tempDataBeans.clear();
                    ShoppingCart instance = ShoppingCart.getInstance();
                    instance.clearCart();
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

    @Subscriber(tag = "close_dialog")
    private void closeDialog(MessageBean user) {
        Log.e("", "### update user with my_tag, name = " + user.message);
        if(dialog.isVisible()){
            dialog.dismiss();
        }
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
