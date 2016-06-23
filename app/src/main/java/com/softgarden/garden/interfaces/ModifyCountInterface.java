package com.softgarden.garden.interfaces;

import android.widget.EditText;

/**
 * Created by qiang-pc on 2016/6/23.
 */
public interface ModifyCountInterface {
    void doIncrease(EditText editText,int position, String currentCount);
    void doDecrease(EditText editText,int position, String currentCount);
}
