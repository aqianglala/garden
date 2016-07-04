package com.softgarden.garden.helper;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.softgarden.garden.utils.BitmapCache;


/**
 * Created by Administrator on 2015/6/17.
 */
public class ImageLoaderHelper extends ImageLoader {
    private static ImageLoaderHelper imageLoader = null;

    private ImageLoaderHelper(RequestQueue queue, ImageCache imageCache) {
        super(queue, imageCache);
    }

    public static void init(Context context) {
        if (imageLoader == null) {
            RequestQueue mQueue = Volley.newRequestQueue(context);
            imageLoader = new ImageLoaderHelper(mQueue,new BitmapCache());
        }
    }

    public static ImageLoader getInstance(){
        return imageLoader;
    }
}
