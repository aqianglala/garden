package com.softgarden.garden.view.back.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.softgarden.garden.base.BaseActivity;
import com.softgarden.garden.dialog.InputDialog;
import com.softgarden.garden.entity.IndexEntity;
import com.softgarden.garden.helper.HttpHelper;
import com.softgarden.garden.helper.ImageLoaderHelper;
import com.softgarden.garden.interfaces.CheckInterface;
import com.softgarden.garden.interfaces.DialogInputListener;
import com.softgarden.garden.interfaces.ModifyCountInterface;
import com.softgarden.garden.jiadun_android.R;

import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by qiang-pc on 2016/6/22.
 */
public class CheckProductAdapter extends BGAAdapterViewAdapter<IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean> {
    private Context context;
    public CheckProductAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
        this.context = context;
    }

    @Override
    protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, final int position, IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean item) {
        bgaViewHolderHelper.setText(R.id.tv_name,item.getItemName())
                .setText(R.id.tv_number,item.getItemNo())
                .setText(R.id.tv_prediction,item.getProQty())
                .setText(R.id.tv_weight,item.getSpec())
                .setText(R.id.tv_back,item.getReturnrate());
        bgaViewHolderHelper.setText(R.id.tv_total,item.getQty()+"");
        // 判断单价
        float price;
        if (item.getBzj()!=null){
            price = item.getIsSpecial() == 0?Float.parseFloat
                    (item.getBzj()): Float.parseFloat( item.getPrice());
        }else{
            price = Float.parseFloat( item.getPrice());
        }
        bgaViewHolderHelper.setText(R.id.tv_price,price+"");

        bgaViewHolderHelper.setChecked(R.id.checkbox,item.isChoosed());

        NetworkImageView iv_product = bgaViewHolderHelper.getView(R.id.iv_product);
        iv_product.setImageUrl(HttpHelper.HOST+item.getPicture(), ImageLoaderHelper.getInstance());

        final TextView tv_total = bgaViewHolderHelper.getView(R.id.tv_total);
        final CheckBox checkbox = bgaViewHolderHelper.getView(R.id.checkbox);

        int total = Integer.parseInt(tv_total.getText().toString().trim());
        if (total>0){
            checkbox.setChecked(true);
        }else if (total == 0){
            checkbox.setChecked(false);
        }
        checkInterface.checkChild(position,checkbox.isChecked());
        tv_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int shuliang = Integer.parseInt(tv_total.getText().toString().trim());
                InputDialog dialog = InputDialog.show((BaseActivity) context,0,
                        shuliang,0,false);
                dialog.setDialogInputListener(new DialogInputListener() {
                    @Override
                    public void inputNum(String num) {
                        if (listener!=null){
                            listener.inputNum(position,num);
                        }
                    }
                });
            }
        });
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInterface.checkChild(position,((CheckBox)v).isChecked());
            }
        });
        bgaViewHolderHelper.getView(R.id.tv_minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCountInterface.doDecrease(tv_total,position,tv_total.getText().toString().trim());
            }
        });
        bgaViewHolderHelper.getView(R.id.tv_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCountInterface.doIncrease(tv_total,position,tv_total.getText().toString().trim());
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

    public interface InputNumListener{
        void inputNum(int position,String num);
    }
    private InputNumListener listener;
    public void setInputNumListener(InputNumListener listener){
        this.listener = listener;
    }
}
