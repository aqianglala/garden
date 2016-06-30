package com.softgarden.garden.view.feedback.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.softgarden.garden.global.BaseActivity;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.StatusBarUtils;
import com.softgarden.garden.view.feedback.utils.Bimp;
import com.softgarden.garden.view.feedback.utils.FileUtils;
import com.softgarden.garden.view.main.entity.MessageBean;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;

public class PhotoActivity extends BaseActivity {

	private ArrayList<View> listViews = null;
	private ViewPager pager;
	private MyPageAdapter adapter;
	private int count;
	private TextView tv_right;
	private int toolBarHeight;
	private TextView tv_left;

	@Override
	protected void initView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_photo);
		StatusBarUtils.setColor(this,Color.parseColor("#2E3432"));
		initToolBar();

		pager = (ViewPager) findViewById(R.id.viewpager);
		for (int i = 0; i < Bimp.bmp.size(); i++) {
			initListViews(Bimp.bmp.get(i));//
		}

		adapter = new MyPageAdapter(listViews);// 构造adapter
		pager.setAdapter(adapter);// 设置适配器
		Intent intent = getIntent();
		count = intent.getIntExtra("currentPostion", -1);
		pager.setCurrentItem(count);
		tv_left.setText((count+1)+"/"+Bimp.bmp.size());
	}

	@Override
	protected void setListener() {
		pager.setOnPageChangeListener(pageChangeListener);
		tv_right.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 弹窗提示删除
				new AlertDialog.Builder(PhotoActivity.this)
						.setTitle("确认删除？")
						.setNegativeButton("取消",null)
						.setPositiveButton("确认", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								if (listViews.size() == 1) {
									Bimp.bmp.clear();
									Bimp.drr.clear();
									FileUtils.deleteDir();
									finish();
								} else {
									String newStr = Bimp.drr.get(count).substring(
											Bimp.drr.get(count).lastIndexOf("/") + 1,
											Bimp.drr.get(count).lastIndexOf("."));
									FileUtils.delFile(newStr+ ".JPEG");
									Bimp.bmp.remove(count);
									Bimp.drr.remove(count);
									tv_left.setText((count+1)+"/"+Bimp.bmp.size());
									pager.removeAllViews();
									listViews.remove(count);
									adapter.setListViews(listViews);
									adapter.notifyDataSetChanged();
								}
								EventBus.getDefault().post(new MessageBean("notify"), "notify");
							}
						}).create()
						.show();
			}
		});
		// 获取toolbar的高度
		toolbar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				toolBarHeight = toolbar.getHeight();
				toolbar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
			}
		});
	}

	@Override
	protected void processLogic(Bundle savedInstanceState) {

	}

	private Toolbar toolbar;
	private void initToolBar() {
		toolbar = getViewById(R.id.toolBar);
		toolbar.setBackgroundColor(Color.parseColor("#2E3432"));
		findViewById(R.id.tv_title).setVisibility(View.GONE);
		tv_left = getViewById(R.id.tv_left);
		tv_left.setVisibility(View.VISIBLE);
		tv_right = getViewById(R.id.tv_right);
		tv_right.setVisibility(View.VISIBLE);
		tv_right.setText("");
		tv_right.setBackgroundResource(R.mipmap.delete);
	}

	private boolean isToolBarHide;
	private void initListViews(Bitmap bm) {
		if (listViews == null)
			listViews = new ArrayList<View>();
		ImageView img = new ImageView(this);// 构造textView对象
		img.setBackgroundColor(0xff000000);
		img.setImageBitmap(bm);
		img.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		listViews.add(img);// 添加view
		img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if(isToolBarHide){// 显示动画
					TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, -toolBarHeight,
							0);
					translateAnimation.setFillAfter(true);
					translateAnimation.setDuration(300);
					toolbar.startAnimation(translateAnimation);
				}else{// 隐藏动画
					TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0,
							-toolBarHeight);
					translateAnimation.setFillAfter(true);
					translateAnimation.setDuration(300);
					toolbar.startAnimation(translateAnimation);
				}
				isToolBarHide = !isToolBarHide;
			}
		});
	}

	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

		public void onPageSelected(int arg0) {// 页面选择响应函数
			count = arg0;
			tv_left.setText((count+1)+"/"+Bimp.bmp.size());
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {// 滑动中。。。

		}

		public void onPageScrollStateChanged(int arg0) {// 滑动状态改变

		}
	};

	class MyPageAdapter extends PagerAdapter {

		private ArrayList<View> listViews;// content

		private int size;// 页数

		public MyPageAdapter(ArrayList<View> listViews) {// 构造函数
															// 初始化viewpager的时候给的一个页面
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		public void setListViews(ArrayList<View> listViews) {// 自己写的一个方法用来添加数据
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		public int getCount() {// 返回数量
			return size;
		}

		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		public void destroyItem(View arg0, int arg1, Object arg2) {// 销毁view对象
			((ViewPager) arg0).removeView(listViews.get(arg1 % size));
		}

		public void finishUpdate(View arg0) {
		}

		public Object instantiateItem(View arg0, int arg1) {// 返回view对象
			try {
				((ViewPager) arg0).addView(listViews.get(arg1 % size), 0);

			} catch (Exception e) {
			}
			return listViews.get(arg1 % size);
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}
}
