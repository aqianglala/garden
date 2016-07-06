package com.softgarden.garden.view.buy;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.toolbox.NetworkImageView;
import com.bigkoo.convenientbanner.holder.Holder;
import com.softgarden.garden.helper.HttpHelper;
import com.softgarden.garden.helper.ImageLoaderHelper;

/**
 * Created by qiang-pc on 2016/6/29.
 */
public class NetworkImageHolderView implements Holder<String> {
    private NetworkImageView imageView;
    @Override
    public View createView(Context context) {
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        imageView = new NetworkImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context,int position, String data) {
        imageView.setImageUrl(HttpHelper.HOST+data,ImageLoaderHelper.getInstance());
    }
}
