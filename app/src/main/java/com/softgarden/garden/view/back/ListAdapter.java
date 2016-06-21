package com.softgarden.garden.view.back;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.view.buy.TestDataBean;

import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by qiang-pc on 2016/6/14.
 */
public class ListAdapter extends BGAAdapterViewAdapter<TestDataBean.DataBean.ArrBean> {

    public ListAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @Override
    protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, int i, TestDataBean.DataBean
            .ArrBean arrBean) {
        bgaViewHolderHelper.setText(R.id.tv_name,arrBean.getName()).setText(R.id.tv_number,
                arrBean.getNumber()).setText(R.id.tv_prediction,arrBean.getPrediction()+"").setText
                (R.id.tv_weight,arrBean.getWeight()+"").setText(R.id.tv_back,arrBean.getBack())
                .setText(R.id.tv_price,arrBean.getPrice()+"");
        final EditText et_total = bgaViewHolderHelper.getView(R.id.et_total);
        bgaViewHolderHelper.getView(R.id.tv_minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int total = Integer.parseInt(et_total.getText().toString().trim());
                if(total>0){
                    total--;
                    et_total.setText(total+"");
                }
            }
        });
        bgaViewHolderHelper.getView(R.id.tv_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int total = Integer.parseInt(et_total.getText().toString().trim());
                total++;
                et_total.setText(total+"");
            }
        });
    }
}
