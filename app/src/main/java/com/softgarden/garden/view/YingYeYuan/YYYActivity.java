package com.softgarden.garden.view.YingYeYuan;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.softgarden.garden.base.BaseActivity;
import com.softgarden.garden.base.BaseApplication;
import com.softgarden.garden.base.EngineFactory;
import com.softgarden.garden.base.ObjectCallBack;
import com.softgarden.garden.engine.YYYEngine;
import com.softgarden.garden.entity.YYYEntity;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.ScreenUtils;

import me.yokeyword.indexablelistview.IndexEntity;
import me.yokeyword.indexablelistview.IndexableStickyListView;

public class YYYActivity extends BaseActivity {

    private IndexableStickyListView mIndexableStickyListView;
    private SlidingMenu menu;
    private ImageView iv_me;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_yyy_main);
        // 由于有slidingmenu，因此需要
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            setViewHeight();
        }

        iv_me = getViewById(R.id.iv_me);
        menu = getViewById(R.id.slidingmenulayout);
        mIndexableStickyListView = (IndexableStickyListView) findViewById(R.id.indexListView);
        mIndexableStickyListView.showCenterOverlayView(true);
        mIndexableStickyListView.showRightOverlayView(false, Color.RED);
    }

    @Override
    protected void setListener() {
        iv_me.setOnClickListener(this);
        mIndexableStickyListView.setOnItemContentClickListener(new IndexableStickyListView.OnItemContentClickListener() {
            @Override
            public void onItemClick(View v, IndexEntity indexEntity) {
                YYYEntity.DataBean entity = (YYYEntity.DataBean) indexEntity;
                Intent intent = new Intent(context, YYYOrderActivity.class);
                intent.putExtra("name",entity.getCustomerNo());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

        ContactAdapter adapter = new ContactAdapter(this);
        mIndexableStickyListView.setAdapter(adapter);

        YYYEngine engine = (YYYEngine) EngineFactory.getEngine(YYYEngine.class);
        String yyy = BaseApplication.userInfo.getData().getYyy();
        engine.yyy(yyy, new ObjectCallBack<YYYEntity>(this) {
            @Override
            public void onSuccess(YYYEntity data) {
                for (YYYEntity.DataBean item: data.getData()){
                    item.setName(item.getCustomerName());
                }
                mIndexableStickyListView.bindDatas(data.getData());
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.iv_me:
                toggle();
                break;
        }
    }

    public void toggle(){
        menu.toggle();
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
