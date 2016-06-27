package com.softgarden.garden.view.feedback.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
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
import com.softgarden.garden.view.feedback.utils.Bimp;
import com.softgarden.garden.view.feedback.utils.FileUtils;

import java.io.IOException;
import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelector;

public class SuggestionActivity extends BaseActivity {

    private TextView tv_complaint;
    private TextView tv_suggestion;
    private int tabWidth;
    private RelativeLayout rl_indicator;
    private ViewPager viewPager;
    private MyPagerAdapter myPagerAdapter;
    private ComplaintFragment f;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_suggestion);

        tv_complaint = getViewById(R.id.tv_complaint);
        tv_suggestion = getViewById(R.id.tv_suggestion);
        // 设置tab的宽度
        tabWidth = ScreenUtils.getScreenWidth(this) / 2;
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

        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(myPagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
                // 修改文字颜色
                int pink = SuggestionActivity.this.getResources().getColor(R.color.colorAccent);
                int black = SuggestionActivity.this.getResources().getColor(R.color.black_text);
                tv_complaint.setTextColor(position == 0 ? pink : black);
                tv_suggestion.setTextColor(!(position == 0) ? pink : black);
                float translationx = position * tabWidth + positionOffsetPixels / 2;
                ViewHelper.setTranslationX(rl_indicator, translationx);
            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        f = (ComplaintFragment) myPagerAdapter.getItem(0);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    public static final int REQUEST_IMAGE = 2;
    private ArrayList<Bitmap>bitmaps = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                // 获取到高清图片存放的地址  /storage/emulated/0/formats/da_1466694126631.JPEG
                final ArrayList<String> selectPath = data.getStringArrayListExtra(MultiImageSelector
                        .EXTRA_RESULT);
                // 将数据保存到map中和本地中
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        for (String path : selectPath) {
                            Log.e("path", path);
                            try {
                                Bitmap bm = Bimp.revitionImageSize(path);
                                if(Bimp.bmp.size()<9){
                                    Bimp.bmp.add(bm);
                                }
                                String newStr = path.substring(
                                        path.lastIndexOf("/") + 1,
                                        path.lastIndexOf("."));
                                FileUtils.saveBitmap(bm, "" + newStr);
                                Message message = Message.obtain();
                                message.what = 1;
                                handler.sendMessage(message);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();
            }
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    f.notifyData();
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Bimp.bmp.clear();
        FileUtils.deleteDir();
    }
}
