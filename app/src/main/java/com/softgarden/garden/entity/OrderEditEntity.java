package com.softgarden.garden.entity;

import java.util.List;

/**
 * Created by qiang-pc on 2016/7/14.
 */
public class OrderEditEntity {
    private String OrderDate;
    private String OrderNo;
    private String remarks;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    private List<HistoryDetailsEntity.DataBean.ShopBean> zstail;

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public List<HistoryDetailsEntity.DataBean.ShopBean> getZstail() {
        return zstail;
    }

    public void setZstail(List<HistoryDetailsEntity.DataBean.ShopBean> zstail) {
        this.zstail = zstail;
    }
}
