package com.softgarden.garden.other;


import com.softgarden.garden.base.BaseApplication;
import com.softgarden.garden.entity.IndexEntity;
import com.softgarden.garden.entity.TempDataBean;
import com.softgarden.garden.utils.LogUtils;
import com.softgarden.garden.utils.Utils;

import java.util.List;
import java.util.Observable;

/**
 * Created by qiang-pc on 2016/7/11.
 * 购物车的全局类
 */
public class ShoppingCart extends Observable {

    private static ShoppingCart instance;
    private float total = 0;
    private int totalNum=0;

    private boolean hasClear;

    public void setHasClear(boolean hasClear) {
        this.hasClear = hasClear;
    }

    public boolean isHasClear() {
        return hasClear;
    }

    /**
     * 购物车的总价格
     * @return
     */
    public float getTotal() {
        return total;
    }

    /**
     * 购物车的总数量
     * @return
     */
    public int getTotalNum() {
        return totalNum;
    }

    private ShoppingCart() {}

    public static ShoppingCart getInstance() {
        if (instance == null) {
            instance = new ShoppingCart();
        }
        return instance;
    }

    public void clearCart(){
        total = 0;
        totalNum = 0;
        setChanged();
        notifyObservers(this);
    }

    /**
     * 初始化购物车
     */
    public void initCartList(){
        totalNum = 0;
        total = 0;
        if(BaseApplication.indexEntity!=null && BaseApplication.tempDataBeans!=null){
            List<IndexEntity.DataBean.ShopBean> shop = BaseApplication.indexEntity.getData()
                    .getShop();
            // 遍历所有数据计算购物车的总数量和总价格
            int classSize = shop.size();
            for(int i = 0;i<classSize;i++){
                int groupSize = shop.get(i).getChild().size();
                for(int j = 0;j<groupSize;j++){
                    int goodsSize = shop.get(i).getChild().get(j).getGoods()
                            .size();
                    for(int k = 0;k<goodsSize;k++){
                        IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean goodsBean = shop.get(i)
                                .getChild().get(j).getGoods().get(k);
                        boolean isInTempData = false;
                        for (TempDataBean item: BaseApplication.tempDataBeans){
                            if(item.getIetmNo().equals(goodsBean.getItemNo())){
                                int count = item.getShuliang() + item.getTuangou();
                                totalNum += count;
                                float price;
                                if (goodsBean.getBzj()!=null){
                                    price = goodsBean.getIsSpecial() == 0?Float.parseFloat(goodsBean
                                            .getBzj()): Float.parseFloat(goodsBean.getPrice());
                                }else{
                                    price = Float.parseFloat(goodsBean.getPrice());
                                }
                                total += Utils.formatFloat(price * count);
                                isInTempData = true;
                                break;
                            }
                        }
                        if(!isInTempData){
                            if (!hasClear){// 清空了购物车就不再把预估数加入购物车
                                int count = Integer.parseInt(goodsBean.getProQty());
                                totalNum += count;
                                float price;
                                if (goodsBean.getBzj()!=null){
                                    price = goodsBean.getIsSpecial() == 0?Float.parseFloat(goodsBean
                                            .getBzj()): Float.parseFloat(goodsBean.getPrice());
                                }else{
                                    price = Float.parseFloat(goodsBean.getPrice());
                                }
                                total += Utils.formatFloat(price * count);
                            }
                        }
                    }
                }
            }
        }
        LogUtils.e("total:"+total+" totalnum:"+totalNum);
    }

    /**
     * 应该需要有个全局的集合用来保存团购数和预估数，修改数据就是往这个集合中添加数据
     * 然后重新计算总数和总价，再notify
     * @param item
     */
    public void changeItem(TempDataBean item){
        boolean hasFind = false;
        for(int i = 0;i<BaseApplication.tempDataBeans.size();i++){
            if(BaseApplication.tempDataBeans.get(i).getIetmNo().equals(item.getIetmNo())){
                BaseApplication.tempDataBeans.get(i).setShuliang(item.getShuliang());
                BaseApplication.tempDataBeans.get(i).setTuangou(item.getTuangou());
                hasFind = true;
                break;
            }
        }
        if(!hasFind){
          BaseApplication.tempDataBeans.add(item);
        }
        // TODO: 2016/7/12 重新计算总价和总数量
        initCartList();
        setChanged();
        notifyObservers(this);
    }

}