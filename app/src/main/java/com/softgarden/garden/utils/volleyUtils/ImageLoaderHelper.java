package com.softgarden.garden.utils.volleyUtils;

import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;


/**
 * Created by Administrator on 2015/6/17.
 */
public class ImageLoaderHelper extends ImageLoader {
    private static ImageLoaderHelper imageLoader = null;

    private ImageLoaderHelper(RequestQueue queue, ImageCache imageCache) {
        super(queue, imageCache);
    }

    public static void init(RequestQueue queue) {
        if (imageLoader == null) {
            imageLoader = new ImageLoaderHelper(queue,new BitmapCache());
        }
    }

    public static ImageLoader getInstance(){
        return imageLoader;
    }

    public static void setImage(String requestUrl, ImageView imageView, int defaultImageResId,
                                int errorImageResId){
        ImageListener imageListener = ImageLoader.getImageListener(imageView, defaultImageResId, errorImageResId);
        imageLoader.get(requestUrl,imageListener);
    }
}
