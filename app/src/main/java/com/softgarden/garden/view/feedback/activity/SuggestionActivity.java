package com.softgarden.garden.view.feedback.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.softgarden.garden.global.BaseActivity;
import com.softgarden.garden.global.BaseFragment;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.ScreenUtils;
import com.softgarden.garden.view.buy.adapter.MyPagerAdapter;
import com.softgarden.garden.view.feedback.fragment.ComplaintFragment;
import com.softgarden.garden.view.feedback.fragment.SuggestionFragment;

import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelector;

public class SuggestionActivity extends BaseActivity {

    private TextView tv_complaint; 
    private TextView tv_suggestion;
    private int tabWidth;
    private RelativeLayout rl_indicator;
    private ViewPager viewPager;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_suggestion);

        tv_complaint = getViewById(R.id.tv_complaint);
        tv_suggestion = getViewById(R.id.tv_suggestion);
        // 设置tab的宽度
        tabWidth = ScreenUtils.getScreenWidth(this)/2;
        rl_indicator = getViewById(R.id.rl_indicator);
        ViewGroup.LayoutParams layoutParams = rl_indicator
                .getLayoutParams();
        layoutParams.width = tabWidth;
        rl_indicator.setLayoutParams(layoutParams);

        viewPager = getViewById(R.id.viewpager);

        ArrayList<BaseFragment> fragments = new ArrayList<>();
        ComplaintFragment complaintFragment = new ComplaintFragment();
        SuggestionFragment suggestionFragment = new SuggestionFragment();
        fragments.add(complaintFragment);
        fragments.add(suggestionFragment);

        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(myPagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 修改文字颜色
                int pink = SuggestionActivity.this.getResources().getColor(R.color.colorAccent);
                int black =  SuggestionActivity.this.getResources().getColor(R.color.black_text);
                tv_complaint.setTextColor(position ==0?pink:black);
                tv_suggestion.setTextColor(!(position ==0)?pink:black);
                float translationx = position * tabWidth + positionOffsetPixels/2;
                ViewHelper.setTranslationX(rl_indicator,translationx);
            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    public static ArrayList<String> mSelectPath;
    public static final int REQUEST_IMAGE = 2;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE){
            if(resultCode == RESULT_OK){
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
            }
        }
    }
}
