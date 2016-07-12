package com.softgarden.garden.other;


import com.softgarden.garden.base.BaseApplication;
import com.softgarden.garden.entity.IndexEntity;
import com.softgarden.garden.entity.TempDataBean;

import java.util.List;
import java.util.Observable;

/**
 * Created by qiang-pc on 2016/7/11.
 * 购物车的全局类
 */
public class ShoppingCart extends Observable {

    private static ShoppingCart instance;
    private double total = 0;
    private int totalNum=0;

    /**
     * 购物车的总价格
     * @return
     */
    public double getTotal() {
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

    /**
     * 初始化购物车
     */
    public void initCartList(){
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
                            if(goodsBean.getIetmNo().equals(item.getIetmNo())){
                                int count = item.getShuliang() + item.getTuangou();
                                totalNum += count;
                                int price = goodsBean.getIsSpecial() == 0?Integer.parseInt(goodsBean
                                        .getBzj()): goodsBean.getPrice();
                                total += (price * count);
                                isInTempData = true;
                                break;
                            }
                        }
                        if(!isInTempData){
                            int count = goodsBean.getProQty();
                            totalNum += count;
                            int price = goodsBean.getIsSpecial() == 0?Integer.parseInt(goodsBean
                                    .getBzj()): goodsBean.getPrice();
                            total += (price * count);
                        }
                    }
                }
            }
        }
    }

    /**
     * 应该需要有个全局的集合用来保存团购数和预估数，添加数据就是往这个集合中添加数据
     * 然后重新计算总数和总价，再notify
     * @param item
     */
    public void changeItem(TempDataBean item){
        for(int i = 0;i<BaseApplication.tempDataBeans.size();i++){
            if(BaseApplication.tempDataBeans.get(i).getIetmNo().equals(item.getIetmNo())){
                BaseApplication.tempDataBeans.get(i).setShuliang(item.getShuliang());
                BaseApplication.tempDataBeans.get(i).setTuangou(item.getTuangou());
                break;
            }else{
                BaseApplication.tempDataBeans.add(item);
                break;
            }
        }
        // TODO: 2016/7/12 重新计算总价和总数量
        initCartList();
        setChanged();
        notifyObservers();
    }

}
