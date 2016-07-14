package com.softgarden.garden.view.buy.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.softgarden.garden.base.BaseActivity;
import com.softgarden.garden.base.BaseApplication;
import com.softgarden.garden.dialog.InputCountDialog;
import com.softgarden.garden.entity.IndexEntity;
import com.softgarden.garden.entity.TempDataBean;
import com.softgarden.garden.helper.HttpHelper;
import com.softgarden.garden.helper.ImageLoaderHelper;
import com.softgarden.garden.interfaces.DialogInputListener;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.other.ShoppingCart;
import com.softgarden.garden.utils.ToastUtil;

import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by qiang-pc on 2016/6/14.
 */
public class ContentAdapter extends BGAAdapterViewAdapter<IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean> {

    private Context context;
    public ContentAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
        this.context = context;
    }

    @Override
    protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, final int position,
                            final IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean bean) {
        // 商品数量上限
        final int maxCount = bean.getProQty() * Integer.parseInt(BaseApplication.userInfo.getData().getKxd
                ());
        bgaViewHolderHelper
                .setText(R.id.tv_name,bean.getItemName())
                .setText(R.id.tv_number, bean.getIetmNo())
                .setText(R.id.tv_weight,bean.getSpec())
                .setText(R.id.tv_back,bean.getReturnrate()+"")
                .setText(R.id.tv_prediction,bean.getProQty()+"")
                .setText(R.id.tv_weight,bean.getSpec());

        TextView tv_price = bgaViewHolderHelper.getView(R.id.tv_price);
        TextView tv_special = bgaViewHolderHelper.getView(R.id.tv_special);
        ImageView iv_tejia = bgaViewHolderHelper.getView(R.id.iv_tejia);

        int price = bean.getPrice();
        if(price == 0 || bean.getIsSpecial() == 0){// 没有特价，使用标准价
            tv_special.setText(bean.getBzj());
            tv_price.setVisibility(View.GONE);
            iv_tejia.setVisibility(View.GONE);
        }else{// 特价
            tv_special.setText(bean.getPrice()+"");
            tv_price.setText(bean.getBzj());
            tv_price.setVisibility(View.VISIBLE);
            tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            iv_tejia.setVisibility(View.VISIBLE);
        }
        // TODO: 2016/7/12
        NetworkImageView iv_product = bgaViewHolderHelper.getView(R.id.iv_product);
        iv_product.setImageUrl(HttpHelper.HOST+bean.getPicture(), ImageLoaderHelper.getInstance());

        final TextView tv_total = bgaViewHolderHelper.getView(R.id.tv_total);
        tv_total.setText(bean.getProQty()+"");
        final TextView tv_group = bgaViewHolderHelper.getView(R.id.tv_group);

        for (TempDataBean item: BaseApplication.tempDataBeans){
            if(bean.getIetmNo().equals(item.getIetmNo())){
                tv_total.setText(item.getShuliang()+"");
                tv_group.setText(item.getTuangou()+"");
                break;
            }
        }

        tv_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tuangou = Integer.parseInt(tv_group.getText().toString().trim());
                int shuliang = Integer.parseInt(tv_total.getText().toString().trim());
                InputCountDialog dialog = InputCountDialog.show((BaseActivity) context,tuangou,
                        shuliang,maxCount,false);
                dialog.setDialogInputListener(new DialogInputListener() {
                    @Override
                    public void inputNum(String num) {
                        tv_total.setText(num);
                        ShoppingCart shoppingCart = ShoppingCart.getInstance();
                        int tuangou = Integer.parseInt(tv_group.getText().toString().trim());
                        shoppingCart.changeItem(new TempDataBean(tuangou,Integer.parseInt(num),
                                bean.getIetmNo()));
                    }
                });
            }
        });
        tv_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tuangou = Integer.parseInt(tv_group.getText().toString().trim());
                int shuliang = Integer.parseInt(tv_total.getText().toString().trim());
                InputCountDialog dialog = InputCountDialog.show((BaseActivity) context,tuangou,
                        shuliang,maxCount,true);
                dialog.setDialogInputListener(new DialogInputListener() {
                    @Override
                    public void inputNum(String num) {
                        tv_group.setText(num);
                        ShoppingCart shoppingCart = ShoppingCart.getInstance();
                        int count = Integer.parseInt(tv_total.getText().toString().trim());
                        shoppingCart.changeItem(new TempDataBean(Integer.parseInt(num),count,bean.getIetmNo()));
                    }
                });
            }
        });

        bgaViewHolderHelper.getView(R.id.tv_minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(tv_total.getText().toString().trim());
                int tuangou = Integer.parseInt(tv_group.getText().toString().trim());
                if(count>0){
                    tv_total.setText(--count+"");
                    TempDataBean item = new TempDataBean(tuangou, count, bean.getIetmNo(),false);
                    ShoppingCart shoppingcart = ShoppingCart.getInstance();
                    shoppingcart.changeItem(item);
                }else{
                    Toast.makeText(context,"宝贝不能再少了哦！",Toast.LENGTH_SHORT).show();
                }
            }
        });
        bgaViewHolderHelper.getView(R.id.tv_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(tv_total.getText().toString().trim());
                int tuangou = Integer.parseInt(tv_group.getText().toString().trim());
                if(maxCount != 0){// 有数量上限
                    if(count<maxCount){
                        tv_total.setText(++count+"");
                        TempDataBean item = new TempDataBean(tuangou, count, bean.getIetmNo(),false);
                        ShoppingCart shoppingcart = ShoppingCart.getInstance();
                        shoppingcart.changeItem(item);
                    }else{
                        ToastUtil.show("数量已达上限！");
                    }
                }else{// 无数量上限
                    tv_total.setText(++count+"");
                    TempDataBean item = new TempDataBean(tuangou, count, bean.getIetmNo(),false);
                    ShoppingCart shoppingcart = ShoppingCart.getInstance();
                    shoppingcart.changeItem(item);
                }
            }
        });
    }
}
