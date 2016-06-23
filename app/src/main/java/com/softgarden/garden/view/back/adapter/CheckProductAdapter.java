package com.softgarden.garden.view.back.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.softgarden.garden.interfaces.CheckInterface;
import com.softgarden.garden.interfaces.ModifyCountInterface;
import com.softgarden.garden.jiadun_android.R;

import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by qiang-pc on 2016/6/22.
 */
public class CheckProductAdapter extends BGAAdapterViewAdapter<String> {
    public CheckProductAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @Override
    protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, final int position, String s) {
        bgaViewHolderHelper.setText(R.id.tv_name,s);
        final EditText et_total = bgaViewHolderHelper.getView(R.id.et_total);
        final CheckBox checkbox = bgaViewHolderHelper.getView(R.id.checkbox);

        et_total.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String trim = et_total.getText().toString().trim();
                    if(TextUtils.isEmpty(trim)){
                        et_total.setText("1");
                    }
                }
            }
        });
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInterface.checkChild(position,((CheckBox)v).isChecked());
            }
        });
        bgaViewHolderHelper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkbox.setChecked(!checkbox.isChecked());
                checkInterface.checkChild(position,checkbox.isChecked());
            }
        });
        bgaViewHolderHelper.getView(R.id.tv_minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCountInterface.doDecrease(et_total,position,et_total.getText().toString().trim());
            }
        });
        bgaViewHolderHelper.getView(R.id.tv_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCountInterface.doIncrease(et_total,position,et_total.getText().toString().trim());
            }
        });
    }

    private CheckInterface checkInterface;
    private ModifyCountInterface modifyCountInterface;
    public void setCheckInterface(CheckInterface checkInterface)
    {
        this.checkInterface = checkInterface;
    }

    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface)
    {
        this.modifyCountInterface = modifyCountInterface;
    }
}
