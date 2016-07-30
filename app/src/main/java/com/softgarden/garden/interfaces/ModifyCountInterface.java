package com.softgarden.garden.interfaces;

import android.widget.TextView;

/**
 * 减少或增加数据的监听
 * Created by qiang-pc on 2016/6/23.
 */
public interface ModifyCountInterface {
    void doIncrease(TextView textView, int position, String currentCount);
    void doDecrease(TextView textView,int position, String currentCount);
}
