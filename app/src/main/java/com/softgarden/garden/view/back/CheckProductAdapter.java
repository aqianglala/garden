package com.softgarden.garden.view.back;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

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
    protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, int i, String s) {
        bgaViewHolderHelper.setText(R.id.tv_name,s);
        final EditText et_total = bgaViewHolderHelper.getView(R.id.et_total);
        bgaViewHolderHelper.getView(R.id.tv_minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(et_total.getText().toString().trim());
                if(count>1){
                    et_total.setText((--count)+"");
                }
            }
        });
        bgaViewHolderHelper.getView(R.id.tv_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(et_total.getText().toString().trim());
                et_total.setText((++count)+"");
            }
        });
    }
}
