package com.softgarden.garden.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.softgarden.garden.holder.BaseHolder;
import com.softgarden.garden.holder.MoreHolder;
import com.softgarden.garden.manager.ThreadManager;
import com.softgarden.garden.utils.UIUtils;

import java.util.ArrayList;


/**
 * listview加载多种布局类型: 1. 重写getViewTypeCount, 返回布局个数 2.
 * 重写getItemViewType,根据位置,返回布局类型 3. 在getView中根据类型加载不同布局
 * 
 * 注意: 布局类型必须从0开始定义
 * 
 * @author Kevin
 * @date 2015-9-25
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {

	public static final int TYPE_MORE = 0;// 加载更多布局样式
	public static final int TYPE_NORMAL = 1;// 普通布局样式, 刚开始要从0开始定义

	private ArrayList<T> list;

	public MyBaseAdapter(ArrayList<T> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size() + 1;// 要增加"加载更多"的布局
	}

	// 返回布局样式的个数
	@Override
	public int getViewTypeCount() {
		return 2;// 普通布局+加载更多布局两种样式
	}

	// 根据当前位置,返回对应布局样式
	@Override
	public int getItemViewType(int position) {
		if (position == list.size()) {
			// 最后一个item, 加载更多类型
			return TYPE_MORE;
		} else {
			return getInnerType(position);
		}
	}

	/**
	 * 返回普通布局类型, 子类可以重写此方法来返回多种普通布局类型
	 * 
	 * @return
	 */
	public int getInnerType(int position) {
		return TYPE_NORMAL;
	}

	@Override
	public T getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BaseHolder holder;
		if (convertView == null) {
			// 1. 加载item的布局对象
			// 2. findviewbyid,初始化各个控件
			// 3. 设置tag
			// 根据当前布局类型来加载不同的布局
			if (getItemViewType(position) != TYPE_MORE) {
				holder = getHolder(position);// 普通布局类型
			} else {
				// 更多布局类型
				holder = new MoreHolder(hasMore());
			}
		} else {
			holder = (BaseHolder) convertView.getTag();
		}

		// 4. 刷新控件数据
		if (getItemViewType(position) != TYPE_MORE) {
			holder.setData(getItem(position));// 不是更多,才设置数据
		} else {
			// 加载下一页数据
			MoreHolder moreHolder = (MoreHolder) holder;
			if (moreHolder.getData() == MoreHolder.TYPE_HAS_MORE) {// 有更多数据,才加载更多数据
				loadMore(moreHolder);
			}
		}

		return holder.getRootView();
	}

	/**
	 * 是否可以加载更多,子类可以重写此方法
	 * 
	 * @return
	 */
	public boolean hasMore() {
		return true;
	}

	/**
	 * 返回holder对象, 必须由子类实现
	 * 
	 * @return
	 */
	public abstract BaseHolder<T> getHolder(int position);

	private boolean isLoadMore = false;// 标记当前是否正在加载更多

	public void loadMore(final MoreHolder holder) {
		if (!isLoadMore) {// 判断当前是否没有加载更多, 避免重复加载更多数据
			isLoadMore = true;
			ThreadManager.getThreadPool().execute(new Runnable() {
				@Override
				public void run() {
					// 加载下一页数据
					final ArrayList<T> moreData = onLoadMore();

					UIUtils.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							if (moreData != null) {
								// 每页数据有20条
								// 如果返回的数据条数少于20条,说明没有更多数据了
								if (moreData.size() < 20) {
									holder.setData(MoreHolder.TYPE_NO_MORE);
								} else {
									holder.setData(MoreHolder.TYPE_HAS_MORE);
								}

								// 将更多数据追加给当前集合
								list.addAll(moreData);
								// 刷新界面
								notifyDataSetChanged();
							} else {
								// 加载更多失败,刷新加载更多的布局
								holder.setData(MoreHolder.TYPE_MORE_ERROR);
							}

							isLoadMore = false;
						}
					});
				}
			});
		}
	}

	/**
	 * 加载下一页数据,必须子类实现
	 * 
	 * @return
	 */
	public abstract ArrayList<T> onLoadMore();

	/**
	 * 返回当前集合大小
	 * 
	 * @return
	 */
	public int getListSize() {
		return list.size();
	}

}
