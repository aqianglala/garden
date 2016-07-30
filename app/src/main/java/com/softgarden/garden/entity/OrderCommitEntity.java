package com.softgarden.garden.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 提交订单到服务器的数据的封装类
 * 本来这个List<ZstailBean> zstail，可以复用的，因为在很多地方都要提交订单，
 * 但是后台一开始的数据字段都不太统一，导致我要创建多个，苦逼的地方，后面懒得改了
 * Created by qiang-pc on 2016/7/14.
 */
public class OrderCommitEntity implements Serializable{

    /**
     * CustomerNo : GZ_0001
     * OrderDate : 2016-7-15
     * zstail : [{"Amount":4,"IsSpecial":0,"ItemGroupName":"生命包","ItemName":"蜜糖甜生命面包",
     * "ItemNo":"3107","Itemgroupcdoe":"B01","Price":0,"Qty":2,"Unit":"包","bzj":"2.0000",
     * "isChoosed":false,"itemclassCode":"3","itemclassName":"面包","picture":"","proQty":0,
     * "returnrate":0,"spec":"450g/袋","tgs":0}]
     */

    private String CustomerNo;
    private String OrderDate;
    private String OrderNo;
    private String remarks;

    private int zffs;
    private int leibie;

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public int getZffs() {
        return zffs;
    }

    public void setZffs(int zffs) {
        this.zffs = zffs;
    }

    public int getLeibie() {
        return leibie;
    }

    public void setLeibie(int leibie) {
        this.leibie = leibie;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * Amount : 4.0
     * IsSpecial : 0
     * ItemGroupName : 生命包
     * ItemName : 蜜糖甜生命面包
     * ItemNo : 3107
     * Itemgroupcdoe : B01
     * Price : 0
     * Qty : 2
     * Unit : 包
     * bzj : 2.0000
     * isChoosed : false
     * itemclassCode : 3
     * itemclassName : 面包
     * picture :
     * proQty : 0
     * returnrate : 0.0
     * spec : 450g/袋
     * tgs : 0
     */

    private List<ZstailBean> zstail;

    public String getCustomerNo() {
        return CustomerNo;
    }

    public void setCustomerNo(String CustomerNo) {
        this.CustomerNo = CustomerNo;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String OrderDate) {
        this.OrderDate = OrderDate;
    }

    public List<ZstailBean> getZstail() {
        return zstail;
    }

    public void setZstail(List<ZstailBean> zstail) {
        this.zstail = zstail;
    }

    public static class ZstailBean implements Serializable{
        private float Amount;
        private int IsSpecial;
        private String ItemGroupName;
        private String ItemName;
        private String ItemNo;
        private String Itemgroupcdoe;
        private String Price;
        private int Qty;
        private String Unit;
        private String bzj;
        private boolean isChoosed;
        private String itemclassCode;
        private String itemclassName;
        private String picture;
        private int proQty;
        private double returnrate;
        private String spec;
        private int tgs;
        private int total;
        private long id;

        public boolean isChoosed() {
            return isChoosed;
        }

        public void setChoosed(boolean choosed) {
            isChoosed = choosed;
        }

        public long getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public ZstailBean(long id, float amount, int isSpecial, String itemGroupName, String itemName,
                          String itemNo, String itemgroupcdoe, String price, int qty, String unit,
                          String bzj, boolean isChoosed, String itemclassCode, String
                                  itemclassName, String picture, int proQty, double returnrate,
                          String spec, int tgs) {
            this.id = id;
            Amount = amount;
            IsSpecial = isSpecial;
            ItemGroupName = itemGroupName;
            ItemName = itemName;
            ItemNo = itemNo;
            Itemgroupcdoe = itemgroupcdoe;
            Price = price;
            Qty = qty;
            Unit = unit;
            this.bzj = bzj;
            this.isChoosed = isChoosed;
            this.itemclassCode = itemclassCode;
            this.itemclassName = itemclassName;
            this.picture = picture;
            this.proQty = proQty;
            this.returnrate = returnrate;
            this.spec = spec;
            this.tgs = tgs;
        }

        public ZstailBean(String ItemNo, String proQty,  String Qty, String returnrate,
                          String Price, String IsSpecial, String Amount, String tgs, String
                                  ItemName, String spec,String picture, String bzj, int total) {
            this.ItemNo = ItemNo;
            this.proQty = Integer.parseInt(proQty);
            this.Qty = Integer.parseInt(Qty);
            this.returnrate = Double.parseDouble(returnrate);
            this.Price = Price;
            this.IsSpecial = Integer.parseInt(IsSpecial);
            this.Amount = Float.parseFloat(Amount);
            this.tgs = Integer.parseInt(tgs);
            this.ItemName = ItemName;
            this.spec = spec;
            this.picture = picture;
            this.bzj = bzj;
            this.total = total;
        }

        public double getAmount() {
            return Amount;
        }

        public void setAmount(float Amount) {
            this.Amount = Amount;
        }

        public int getIsSpecial() {
            return IsSpecial;
        }

        public void setIsSpecial(int IsSpecial) {
            this.IsSpecial = IsSpecial;
        }

        public String getItemGroupName() {
            return ItemGroupName;
        }

        public void setItemGroupName(String ItemGroupName) {
            this.ItemGroupName = ItemGroupName;
        }

        public String getItemName() {
            return ItemName;
        }

        public void setItemName(String ItemName) {
            this.ItemName = ItemName;
        }

        public String getItemNo() {
            return ItemNo;
        }

        public void setItemNo(String ItemNo) {
            this.ItemNo = ItemNo;
        }

        public String getItemgroupcdoe() {
            return Itemgroupcdoe;
        }

        public void setItemgroupcdoe(String Itemgroupcdoe) {
            this.Itemgroupcdoe = Itemgroupcdoe;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String Price) {
            this.Price = Price;
        }

        public int getQty() {
            return Qty;
        }

        public void setQty(int Qty) {
            this.Qty = Qty;
        }

        public String getUnit() {
            return Unit;
        }

        public void setUnit(String Unit) {
            this.Unit = Unit;
        }

        public String getBzj() {
            return bzj;
        }

        public void setBzj(String bzj) {
            this.bzj = bzj;
        }

        public boolean isIsChoosed() {
            return isChoosed;
        }

        public void setIsChoosed(boolean isChoosed) {
            this.isChoosed = isChoosed;
        }

        public String getItemclassCode() {
            return itemclassCode;
        }

        public void setItemclassCode(String itemclassCode) {
            this.itemclassCode = itemclassCode;
        }

        public String getItemclassName() {
            return itemclassName;
        }

        public void setItemclassName(String itemclassName) {
            this.itemclassName = itemclassName;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public int getProQty() {
            return proQty;
        }

        public void setProQty(int proQty) {
            this.proQty = proQty;
        }

        public double getReturnrate() {
            return returnrate;
        }

        public void setReturnrate(double returnrate) {
            this.returnrate = returnrate;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public int getTgs() {
            return tgs;
        }

        public void setTgs(int tgs) {
            this.tgs = tgs;
        }
    }
}
