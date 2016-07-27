package com.softgarden.garden.view.back.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.softgarden.garden.dialog.ChangePromptDialog;
import com.softgarden.garden.entity.BackCommitEntity;
import com.softgarden.garden.entity.IndexEntity;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.ScreenUtils;
import com.softgarden.garden.utils.StringUtils;
import com.softgarden.garden.utils.UIUtils;
import com.softgarden.garden.view.back.adapter.ContainerPagerAdapter;
import com.softgarden.garden.view.start.activity.MainActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hasee on 2016/6/6.
 */
public class BackFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener{

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
    private RelativeLayout rl_date;
    private TextView tv_date;
    private TextView tv_date_label;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_back);

        mActivity = (MainActivity)getActivity();

        btn_confirm = getViewById(R.id.btn_confirm);
        iv_me = getViewById(R.id.iv_me);

        rl_indicator = getViewById(R.id.rl_indicator);
        ll_tab = getViewById(R.id.ll_tab);
        ll_tab_container = getViewById(R.id.ll_tab_container);

        rl_date = getViewById(R.id.rl_date);
        tv_date = getViewById(R.id.tv_date);
        tv_date_label = getViewById(R.id.tv_date_label);

        vp_content = getViewById(R.id.vp_content);
    }

    @Override
    protected void setListener() {
        rl_date.setOnClickListener(this);
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
        initTimePicker();
        // 由于退货和换货用的是同个fragment的，所以创建的时候要传入个参数用来识别是退货还是换货
        Bundle arguments = getArguments();
        isBack = arguments.getBoolean("isBack");
        btn_confirm.setText(isBack?"确认退货":"确认换货");
        tv_date_label.setText(isBack?"退货日期":"换货日期");
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

    private DatePickerDialog dpd;
    private void initTimePicker() {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_MONTH,1);
        tv_date.setText(StringUtils.formatDate(now.getTime()));
        dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setThemeDark(false);
        dpd.vibrate(false);//设置是否震动
        dpd.dismissOnPause(true);//activity暂停的时候是否销毁对话框
        dpd.showYearPickerFirst(false);//先展示年份选择
        dpd.setAccentColor(Color.parseColor("#E6003E"));
        dpd.setMinDate(now);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.rl_date:
                dpd.show(mActivity.getFragmentManager(), "Datepickerdialog");
                break;
            case R.id.iv_me:
                mActivity.toggle();
                break;

            case R.id.btn_confirm:
                String btn_text = btn_confirm.getText().toString();
                BackCommitEntity backCommitEntity = getBackCommitEntity();
                if(btn_text.equals("确认换货")){
                   ChangePromptDialog.show(mActivity,backCommitEntity);
                }else if(btn_text.equals("确认退货")){
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
                        .getCustomerNo(),tv_date.getText().toString());
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
        try {
            IndexEntity.DataBean.ShopBean clone = (IndexEntity.DataBean.ShopBean) shopBean.clone();
            Bundle bundle = new Bundle();
            bundle.putBoolean("isBack",isBack);
            bundle.putSerializable("data",clone );
            fragmentProduct.setArguments(bundle);
            fragments.add(fragmentProduct);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar instance = Calendar.getInstance();
        instance.set(year,monthOfYear,dayOfMonth);
        String formatDate = StringUtils.formatDate(instance.getTime());
        tv_date.setText(formatDate);
    }

}
