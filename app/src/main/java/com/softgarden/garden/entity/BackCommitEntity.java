package com.softgarden.garden.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by qiang-pc on 2016/7/14.
 */
public class BackCommitEntity implements Serializable{
    private ArrayList<IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean> zstail;
    private String CustomerNo;
    private String orderDate;

    public BackCommitEntity(ArrayList<IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean> zstail,
                            String customerNo, String orderDate) {
        this.zstail = zstail;
        CustomerNo = customerNo;
        this.orderDate = orderDate;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public ArrayList<IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean> getZstail() {
        return zstail;
    }

    public void setZstail(ArrayList<IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean> zstail) {
        this.zstail = zstail;
    }

    public String getCustomerNo() {
        return CustomerNo;
    }

    public void setCustomerNo(String customerNo) {
        CustomerNo = customerNo;
    }
}
