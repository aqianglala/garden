package com.softgarden.garden.interfaces;

import android.widget.TextView;

/**
 * Created by qiang-pc on 2016/6/23.
 */
public interface ModifyCountInterface {
    void doIncrease(TextView textView, int position, String currentCount);
    void doDecrease(TextView textView,int position, String currentCount);
}
