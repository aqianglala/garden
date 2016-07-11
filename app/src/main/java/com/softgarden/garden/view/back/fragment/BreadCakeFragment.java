package com.softgarden.garden.view.back.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.softgarden.garden.base.BaseFragment;
import com.softgarden.garden.interfaces.CheckInterface;
import com.softgarden.garden.interfaces.ModifyCountInterface;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.ScreenUtils;
import com.softgarden.garden.view.back.BackPromptDialog;
import com.softgarden.garden.view.back.adapter.BannerPagerAdapter;
import com.softgarden.garden.view.back.adapter.CheckProductAdapter;
import com.softgarden.garden.view.back.interfaces.OnItemClickPositionListener;
import com.softgarden.garden.view.change.ChangePromptDialog;
import com.softgarden.garden.view.start.entity.MessageBean;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiang on 2016/6/14.
 */
public class BreadCakeFragment extends BaseFragment implements CheckInterface,
        ModifyCountInterface,OnItemClickPositionListener{

    private boolean isBread;
    private ViewPager vp_banner;
    private ViewPager vp_content;
    private ListView lv_content;

    private ArrayList<String> mData;
    private CheckProductAdapter adapter;
    private Button btn_confirm;

    private List<ImageView> dots = new ArrayList<ImageView>();
    private ArrayList<String> tags;
    private ArrayList<BaseFragment> bannerFragments;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_bread_cake);
        Bundle arguments = getArguments();
        boolean isBack = arguments.getBoolean("isBack");
        btn_confirm = getViewById(R.id.btn_confirm);
        btn_confirm.setText(isBack?"确认退货":"确认换货");

        virtualData();

        vp_banner = getViewById(R.id.vp_banner);
        vp_content = getViewById(R.id.vp_content);
        // 模擬获取到的banner数据
        initBannerData();
        // 分组，每组6个
        grouping();

        BannerPagerAdapter bannerPagerAdapter = new BannerPagerAdapter(getChildFragmentManager(),
                bannerFragments);
        vp_banner.setAdapter(bannerPagerAdapter);

        lv_content = getViewById(R.id.lv_content);
        adapter = new CheckProductAdapter(mActivity, R.layout.item_list_check_content);
        adapter.setCheckInterface(this);
        adapter.setModifyCountInterface(this);
        adapter.setDatas(mData);
        lv_content.setAdapter(adapter);

        addDots(bannerFragments);
        dots.get(0).setSelected(true);
    }

    private void grouping() {
        bannerFragments = new ArrayList<>();
        int size = tags.size();
        int groups = (int)Math.ceil(size/6.0);
        // 设置viewpager的缓存页数
        vp_banner.setOffscreenPageLimit(groups);
        for (int i = 0;i<groups;i++){
            ArrayList<String> group = new ArrayList<>();
            if(i == groups-1){// 如果是最后一组
                int lastSize = size % 6;
                for(int k = i*6;k<(i*6+lastSize);k++){
                    group.add(tags.get(k));
                }
            }else{
                for(int j=i*6;j<(i+1)*6;j++){
                    group.add(tags.get(j));
                }
            }
            BannerFragment bannerFragment = new BannerFragment();
            bannerFragment.setOnItemClickPositionListener(this);
            Bundle bundle = new Bundle();
            // 传递数据及组的坐标，以此来获得被点击的item在原集合的位置
            bundle.putInt("groupIndex",i);
            bundle.putStringArrayList("tags",group);
            bannerFragment.setArguments(bundle);
            bannerFragments.add(bannerFragment);
        }
    }

    private void initBannerData() {
        tags = new ArrayList<>();
        for(int i=0;i<20;i++){
            tags.add("生命包"+i);
        }
    }

    private void addDots(ArrayList<BaseFragment> fragments) {
        LinearLayout ll_dots = getViewById(R.id.ll_dots);
        for (int i = 0; i < fragments.size(); i++) {
            ImageView img = new ImageView(getContext());
            img.setBackgroundResource(R.drawable.dot_selector);
            img.setSelected(false);// 灰色
            // .xml layout_width layout_height
            // .java LinearLayout.LayoutParams 布局参数
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                    //
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            // .xml marginRight
            // .java rightMargin
            if(i<fragments.size()-1){
                p.rightMargin = 15;
            }
            img.setLayoutParams(p);
            ll_dots.addView(img);
            dots.add(img);
        }
    }

    private int currentPageIndex = 0;
    @Override
    protected void setListener() {
        btn_confirm.setOnClickListener(this);
        vp_banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dots.get(currentPageIndex).setSelected(false);
                currentPageIndex = position;
                dots.get(currentPageIndex).setSelected(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        isBread = getArguments().getBoolean("isBread");
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_confirm:
                String btn_text = btn_confirm.getText().toString();
                if(btn_text.equals("确认换货")){
                    ChangePromptDialog changePromptDialog = new ChangePromptDialog(mActivity, R
                            .style.CustomDialog);
                    showDialog(changePromptDialog);
                }else if(btn_text.equals("确认退货")){
                    BackPromptDialog backPromptDialog = new BackPromptDialog(mActivity, R
                            .style.CustomDialog);
                    showDialog(backPromptDialog);
                }
                break;
        }
    }

    private void virtualData() {
        mData = new ArrayList<>();
        for(int i=0;i<20;i++){
            mData.add("面包"+(i+1));
        }
    }

    private void showDialog(Dialog dialog) {
        dialog.show();
        // 设置宽，高可在xml布局中写上,但宽度默认是match_parent，所以需要在代码中设置
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        attributes.width = (int) (ScreenUtils.getScreenWidth(mActivity)*0.9);
        attributes.height =(int) (ScreenUtils.getScreenWidth(mActivity)*0.9);
        dialog.getWindow().setAttributes(attributes);
    }

    @Override
    public void checkChild(int position, boolean isChecked) {
        showToast(isChecked?"选中了":"未选中");
    }

    @Override
    public void doIncrease(EditText et_total,int position, String currentCount) {
        if(TextUtils.isEmpty(currentCount)){
            et_total.setText("1");
        }else{
            int total = Integer.parseInt(currentCount);
            et_total.setText((++total)+"");
        }
    }

    @Override
    public void doDecrease(EditText et_total,int position, String currentCount) {

        if(TextUtils.isEmpty(currentCount)){
            Toast.makeText(mActivity,"宝贝不能再少了哦！",Toast.LENGTH_SHORT).show();
        }else{
            int total = Integer.parseInt(currentCount);
            if(total>1){
                total--;
                et_total.setText(total+"");
            }
        }
    }
    /**
     * bannerFragment中的item被点击后会触发此回调
     * 然后再把选中的位置通知给所有的bannerFragment，让他们更新界面
     * 同时可以在此方法中实现点击事件,更新下面列表
      */

    @Override
    public void onClickPosition(int position,String tag) {
        EventBus.getDefault().post(new MessageBean(position+""), "clickIndex");
    }
}
