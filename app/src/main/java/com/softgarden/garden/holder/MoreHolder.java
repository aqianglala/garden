package com.softgarden.garden.holder;


import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.UIUtils;

/**
 * 加载更多的holder
 * 
 * 加载更多的几种类型: 1. 可以加载更多 2. 不能加载更多 3. 加载更多失败
 * 
 * @author Kevin
 * @date 2015-9-25
 */
public class MoreHolder extends BaseHolder<Integer> {

	public static final int TYPE_HAS_MORE = 0;// 可以加载更多
	public static final int TYPE_NO_MORE = 1;// 不能加载更多
	public static final int TYPE_MORE_ERROR = 2;// 加载更多失败

	private LinearLayout llLoadMore;
	private TextView tvLoadError;

	public MoreHolder(boolean hasMore) {
		// 根据是否可以加载更多,来设置当前的加载类型, 一旦设置数据,马上会refreshView
		setData(hasMore ? TYPE_HAS_MORE : TYPE_NO_MORE);
	}

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.list_item_more);
		llLoadMore = (LinearLayout) view.findViewById(R.id.ll_load_more);
		tvLoadError = (TextView) view.findViewById(R.id.tv_load_error);
		return view;
	}

	@Override
	public void refreshView(Integer data) {
		switch (data) {
		case TYPE_HAS_MORE:
			// 可以加载更多, 展示加载更多的界面
			llLoadMore.setVisibility(View.VISIBLE);
			tvLoadError.setVisibility(View.GONE);
			break;
		case TYPE_NO_MORE:
			// 不能加载更多, 隐藏加载更多界面
			llLoadMore.setVisibility(View.GONE);
			tvLoadError.setVisibility(View.GONE);
			break;
		case TYPE_MORE_ERROR:
			// 加载更多失败, 展示加载失败界面
			llLoadMore.setVisibility(View.GONE);
			tvLoadError.setVisibility(View.VISIBLE);
			break;

		default:
			break;
		}
	}

}
