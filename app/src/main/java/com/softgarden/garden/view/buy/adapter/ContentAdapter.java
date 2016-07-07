package com.softgarden.garden.view.buy.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.softgarden.garden.base.BaseActivity;
import com.softgarden.garden.dialog.InputCountDialog;
import com.softgarden.garden.entity.IndexEntity;
import com.softgarden.garden.helper.HttpHelper;
import com.softgarden.garden.helper.ImageLoaderHelper;
import com.softgarden.garden.interfaces.CheckInterface;
import com.softgarden.garden.interfaces.ModifyCountInterface;
import com.softgarden.garden.jiadun_android.R;

import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by qiang-pc on 2016/6/14.
 */
public class ContentAdapter extends BGAAdapterViewAdapter<IndexEntity.DataBean.SanjiBean> {

    private Context context;
    public ContentAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
        this.context = context;
    }

    @Override
    protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, final int position,
                            IndexEntity.DataBean.SanjiBean bean) {
        TextView tv_price = bgaViewHolderHelper.getView(R.id.tv_price);
        tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        bgaViewHolderHelper
                .setText(R.id.tv_name,bean.getName())
                .setText(R.id.tv_number, bean.getNumber())
                .setText(R.id.tv_weight,bean.getGuige()+"")
                .setText(R.id.tv_price,bean.getPrice()+"")
                .setText(R.id.tv_special,bean.getSpecial());
        // 设置图片
        NetworkImageView iv_product = bgaViewHolderHelper.getView(R.id.iv_product);
        iv_product.setImageUrl(HttpHelper.HOST+bean.getPicture(), ImageLoaderHelper.getInstance());

        final TextView tv_total = bgaViewHolderHelper.getView(R.id.tv_total);
        tv_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputCountDialog.show((BaseActivity)context);
            }
        });
        final TextView tv_group = bgaViewHolderHelper.getView(R.id.tv_group);
        tv_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputCountDialog.show((BaseActivity)context);
            }
        });

        bgaViewHolderHelper.getView(R.id.tv_minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                int count = Integer.parseInt(et_total.getText().toString().trim());
//                modifyCountInterface.doDecrease(et_total,position,count);

                String trim = tv_total.getText().toString().trim();
                if(TextUtils.isEmpty(trim)){
                    Toast.makeText(context,"宝贝不能再少了哦！",Toast.LENGTH_SHORT).show();
                }else{
                    int total = Integer.parseInt(trim);
                    if(total>1){
                        total--;
                        tv_total.setText(total+"");
                    }
                }
            }
        });
        bgaViewHolderHelper.getView(R.id.tv_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                int count = Integer.parseInt(et_total.getText().toString().trim());
//                modifyCountInterface.doIncrease(et_total,position,count);
                String trim = tv_total.getText().toString().trim();
                if(TextUtils.isEmpty(trim)){
                    tv_total.setText("1");
                }else{
                    int total = Integer.parseInt(trim);
                    tv_total.setText((++total)+"");
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
