package com.softgarden.garden.view.buy.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.softgarden.garden.interfaces.CheckInterface;
import com.softgarden.garden.interfaces.ModifyCountInterface;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.view.buy.entity.TestDataBean;

import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by qiang-pc on 2016/6/14.
 */
public class ContentAdapter extends BGAAdapterViewAdapter<TestDataBean.DataBean.ArrBean> {

    private Context context;
    public ContentAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
        this.context = context;
    }

    @Override
    protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, final int position, TestDataBean.DataBean
            .ArrBean arrBean) {
        bgaViewHolderHelper.setText(R.id.tv_name,arrBean.getName()).setText(R.id.tv_number,
                arrBean.getNumber()).setText(R.id.tv_prediction,arrBean.getPrediction()+"").setText
                (R.id.tv_weight,arrBean.getWeight()+"").setText(R.id.tv_back,arrBean.getBack())
                .setText(R.id.tv_price,arrBean.getPrice()+"");
        final EditText et_total = bgaViewHolderHelper.getView(R.id.et_total);
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
        bgaViewHolderHelper.getView(R.id.tv_minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                int count = Integer.parseInt(et_total.getText().toString().trim());
//                modifyCountInterface.doDecrease(et_total,position,count);

                String trim = et_total.getText().toString().trim();
                if(TextUtils.isEmpty(trim)){
                    Toast.makeText(context,"宝贝不能再少了哦！",Toast.LENGTH_SHORT).show();
                }else{
                    int total = Integer.parseInt(trim);
                    if(total>1){
                        total--;
                        et_total.setText(total+"");
                    }
                }
            }
        });
        bgaViewHolderHelper.getView(R.id.tv_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                int count = Integer.parseInt(et_total.getText().toString().trim());
//                modifyCountInterface.doIncrease(et_total,position,count);
                String trim = et_total.getText().toString().trim();
                if(TextUtils.isEmpty(trim)){
                    et_total.setText("1");
                }else{
                    int total = Integer.parseInt(trim);
                    et_total.setText((++total)+"");
                }
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
