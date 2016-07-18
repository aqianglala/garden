package com.softgarden.garden.view.back.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.softgarden.garden.base.BaseApplication;
import com.softgarden.garden.base.BaseFragment;
import com.softgarden.garden.dialog.BackPromptDialog;
import com.softgarden.garden.entity.BackCommitEntity;
import com.softgarden.garden.entity.IndexEntity;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.ScreenUtils;
import com.softgarden.garden.utils.UIUtils;
import com.softgarden.garden.view.back.adapter.ContainerPagerAdapter;
import com.softgarden.garden.view.change.ChangePromptDialog;
import com.softgarden.garden.view.start.activity.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hasee on 2016/6/6.
 */
public class BackFragment extends BaseFragment{

    private ImageView iv_me;
    private MainActivity mActivity;

    private RelativeLayout rl_indicator;
    private LinearLayout ll_tab;
    private LinearLayout ll_tab_container;

    private int tabCount;
    private int tabWidth;
    private ViewPager vp_content;
    private ArrayList<TextView> tabViews =  new ArrayList<TextView>();;

    private ArrayList<BaseFragment> fragments = new ArrayList<>();
    private boolean isBack;
    private Button btn_confirm;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_back);

        mActivity = (MainActivity)getActivity();

        btn_confirm = getViewById(R.id.btn_confirm);
        iv_me = getViewById(R.id.iv_me);

        rl_indicator = getViewById(R.id.rl_indicator);
        ll_tab = getViewById(R.id.ll_tab);
        ll_tab_container = getViewById(R.id.ll_tab_container);

        vp_content = getViewById(R.id.vp_content);
    }

    @Override
    protected void setListener() {
        btn_confirm.setOnClickListener(this);
        iv_me.setOnClickListener(this);
        vp_content.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                float i = (float)ScreenUtils.getScreenWidth(mActivity) / tabWidth;
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
        // 由于退货和换货用的是同个fragment的，所以创建的时候要传入个参数用来识别是退货还是换货
        Bundle arguments = getArguments();
        isBack = arguments.getBoolean("isBack");
        btn_confirm.setText(isBack?"确认退货":"确认换货");
        // 如果数据不为空
        if(BaseApplication.indexEntity!= null){
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
            changeTitleState(0);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.iv_me:
                mActivity.toggle();
                break;

            case R.id.btn_confirm:
                String btn_text = btn_confirm.getText().toString();
                if(btn_text.equals("确认换货")){
                    ChangePromptDialog changePromptDialog = new ChangePromptDialog(mActivity, R
                            .style.CustomDialog);
                    showDialog(changePromptDialog);
                }else if(btn_text.equals("确认退货")){
                    /**
                     * 判断是否有登录过，登录过则直接提交，否则直接
                     */
                    BackCommitEntity backCommitEntity = getBackCommitEntity();
                    BackPromptDialog.show(mActivity,backCommitEntity);
                }
                break;
        }
    }

    @NonNull
    private BackCommitEntity getBackCommitEntity() {
        // 遍历所有数据
        ArrayList<IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean> goodsBeens = new
                ArrayList<>();
        for (BaseFragment fragment: fragments){
            HashMap<String, List<IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean>>
                    map = ((BreadCakeFragment) fragment).getMap();
            for (Map.Entry<String,List<IndexEntity.DataBean.ShopBean.ChildBean
                    .GoodsBean>> entry: map.entrySet()){
                for (IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean item: entry.getValue()){
                    // 数量不为0，且有选中
                    if(item.isChoosed()&&item.getQty()!=0){
                        goodsBeens.add(item);
                    }
                }
            }
        }
        return new BackCommitEntity(goodsBeens,
                BaseApplication.userInfo.getData()
                        .getCustomerNo());
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
        rl_indicator.setVisibility(View.VISIBLE);
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

    private void showDialog(Dialog dialog) {
        dialog.show();
        // 设置宽，高可在xml布局中写上,但宽度默认是match_parent，所以需要在代码中设置
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        attributes.width = (int) (ScreenUtils.getScreenWidth(mActivity)*0.9);
        attributes.height =(int) (ScreenUtils.getScreenWidth(mActivity)*0.9);
        dialog.getWindow().setAttributes(attributes);
    }

}
