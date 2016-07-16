package com.softgarden.garden.entity;

import com.softgarden.garden.base.BaseDao;

import java.util.List;

/**
 * Created by qiang-pc on 2016/7/15.
 */
public class HistoryDetailsEntity extends BaseDao {

    /**
     * status : 1
     * errorMsg :
     * data : {"shop":[{"OrderDate":"2016-07-15","OrderNo":"95570","CustomerNo":"GZ_0001",
     * "RouteCode":"1","Operator":"app_GZ_0001","remarks":null,"zffs":null,"is_pay":null,
     * "ItemNo":"3001","proQty":"0","Qty":"2","returnrate":"0.0000","Price":"0.0000",
     * "IsSpecial":"0","Amount":"4.00","tgs":"0","ItemName":"嘉顿生命面包(蜡纸)","spec":"450g/袋",
     * "picture":"","bzj":"2.0000"}]}
     */

    private String status;
    private String errorMsg;
    private DataBean data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * OrderDate : 2016-07-15
         * OrderNo : 95570
         * CustomerNo : GZ_0001
         * RouteCode : 1
         * Operator : app_GZ_0001
         * remarks : null
         * zffs : null
         * is_pay : null
         * ItemNo : 3001
         * proQty : 0
         * Qty : 2
         * returnrate : 0.0000
         * Price : 0.0000
         * IsSpecial : 0
         * Amount : 4.00
         * tgs : 0
         * ItemName : 嘉顿生命面包(蜡纸)
         * spec : 450g/袋
         * picture :
         * bzj : 2.0000
         */

        private List<ShopBean> shop;

        public List<ShopBean> getShop() {
            return shop;
        }

        public void setShop(List<ShopBean> shop) {
            this.shop = shop;
        }

        public static class ShopBean {
            private String OrderDate;
            private String OrderNo;
            private String CustomerNo;
            private String RouteCode;
            private String Operator;
            private Object remarks;
            private Object zffs;
            private Object is_pay;
            private String ItemNo;
            private String proQty;
            private String Qty;
            private String returnrate;
            private String Price;
            private String IsSpecial;
            private String Amount;
            private String tgs;
            private String ItemName;
            private String spec;
            private String picture;
            private String bzj;

            private int total;

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public String getOrderDate() {
                return OrderDate;
            }

            public void setOrderDate(String OrderDate) {
                this.OrderDate = OrderDate;
            }

            public String getOrderNo() {
                return OrderNo;
            }

            public void setOrderNo(String OrderNo) {
                this.OrderNo = OrderNo;
            }

            public String getCustomerNo() {
                return CustomerNo;
            }

            public void setCustomerNo(String CustomerNo) {
                this.CustomerNo = CustomerNo;
            }

            public String getRouteCode() {
                return RouteCode;
            }

            public void setRouteCode(String RouteCode) {
                this.RouteCode = RouteCode;
            }

            public String getOperator() {
                return Operator;
            }

            public void setOperator(String Operator) {
                this.Operator = Operator;
            }

            public Object getRemarks() {
                return remarks;
            }

            public void setRemarks(Object remarks) {
                this.remarks = remarks;
            }

            public Object getZffs() {
                return zffs;
            }

            public void setZffs(Object zffs) {
                this.zffs = zffs;
            }

            public Object getIs_pay() {
                return is_pay;
            }

            public void setIs_pay(Object is_pay) {
                this.is_pay = is_pay;
            }

            public String getItemNo() {
                return ItemNo;
            }

            public void setItemNo(String ItemNo) {
                this.ItemNo = ItemNo;
            }

            public String getProQty() {
                return proQty;
            }

            public void setProQty(String proQty) {
                this.proQty = proQty;
            }

            public String getQty() {
                return Qty;
            }

            public void setQty(String Qty) {
                this.Qty = Qty;
            }

            public String getReturnrate() {
                return returnrate;
            }

            public void setReturnrate(String returnrate) {
                this.returnrate = returnrate;
            }

            public String getPrice() {
                return Price;
            }

            public void setPrice(String Price) {
                this.Price = Price;
            }

            public String getIsSpecial() {
                return IsSpecial;
            }

            public void setIsSpecial(String IsSpecial) {
                this.IsSpecial = IsSpecial;
            }

            public String getAmount() {
                return Amount;
            }

            public void setAmount(String Amount) {
                this.Amount = Amount;
            }

            public String getTgs() {
                return tgs;
            }

            public void setTgs(String tgs) {
                this.tgs = tgs;
            }

            public String getItemName() {
                return ItemName;
            }

            public void setItemName(String ItemName) {
                this.ItemName = ItemName;
            }

            public String getSpec() {
                return spec;
            }

            public void setSpec(String spec) {
                this.spec = spec;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public String getBzj() {
                return bzj;
            }

            public void setBzj(String bzj) {
                this.bzj = bzj;
            }
        }
    }
}
