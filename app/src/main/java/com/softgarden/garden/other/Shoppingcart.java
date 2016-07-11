package com.softgarden.garden.other;

import com.softgarden.garden.dao.ProductBean;

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
     * 应该需要有个全局的集合用来保存团购数和预估数，添加数据就是往这个集合中添加数据
     * 然后重新计算总数和总价，再notify
     * @param item
     */
    public void addItem(ProductBean item){

    }

    /**
     * 应该需要有个全局的集合用来保存团购数和预估数，删除数据就是往这个集合中删除数据
     * 然后重新计算总数和总价，再notify
     * @param item
     */
    public void deleteItem(ProductBean item){

    }
}
