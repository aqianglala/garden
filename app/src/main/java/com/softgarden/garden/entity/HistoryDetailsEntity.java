package com.softgarden.garden.entity;

import com.softgarden.garden.base.BaseDao;

import java.util.List;

/**
 * 服务器端返回的历史订单详情数据的封装类
 * Created by qiang-pc on 2016/7/15.
 */
public class HistoryDetailsEntity extends BaseDao {

    /**
     * status : 1
     * errorMsg :
     * data : {"head":{"zffs":"3","remarks":""},"shop":[{"ItemNo":"3001","proQty":"10",
     * "Qty":"10","returnrate":"0.0000","Price":"1.0000","IsSpecial":"1","Amount":"10.00",
     * "tgs":"0","ItemName":"嘉顿生命面包(蜡纸)","spec":"450g/袋","picture":"","bzj":"2.0000"},
     * {"ItemNo":"3107","proQty":"9","Qty":"9","returnrate":"0.0000","Price":"1.0000",
     * "IsSpecial":"0","Amount":"9.00","tgs":"0","ItemName":"蜜糖甜生命面包","spec":"450g/袋",
     * "picture":"","bzj":"2.0000"},{"ItemNo":"3064","proQty":"0","Qty":"2","returnrate":"0
     * .0000","Price":"0.0000","IsSpecial":"0","Amount":"0.00","tgs":"0","ItemName":"菠萝椰丝忌廉包",
     * "spec":"80g/袋","picture":"","bzj":"3.0000"}]}
     */

    private String status;
    private String errorMsg;
    /**
     * head : {"zffs":"3","remarks":""}
     * shop : [{"ItemNo":"3001","proQty":"10","Qty":"10","returnrate":"0.0000","Price":"1.0000",
     * "IsSpecial":"1","Amount":"10.00","tgs":"0","ItemName":"嘉顿生命面包(蜡纸)","spec":"450g/袋",
     * "picture":"","bzj":"2.0000"},{"ItemNo":"3107","proQty":"9","Qty":"9","returnrate":"0
     * .0000","Price":"1.0000","IsSpecial":"0","Amount":"9.00","tgs":"0","ItemName":"蜜糖甜生命面包",
     * "spec":"450g/袋","picture":"","bzj":"2.0000"},{"ItemNo":"3064","proQty":"0","Qty":"2",
     * "returnrate":"0.0000","Price":"0.0000","IsSpecial":"0","Amount":"0.00","tgs":"0","ItemName":"菠萝椰丝忌廉包","spec":"80g/袋","picture":"","bzj":"3.0000"}]
     */

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
         * zffs : 3
         * remarks :
         */

        private HeadBean head;
        /**
         * ItemNo : 3001
         * proQty : 10
         * Qty : 10
         * returnrate : 0.0000
         * Price : 1.0000
         * IsSpecial : 1
         * Amount : 10.00
         * tgs : 0
         * ItemName : 嘉顿生命面包(蜡纸)
         * spec : 450g/袋
         * picture :
         * bzj : 2.0000
         */

        private List<ShopBean> shop;

        public HeadBean getHead() {
            return head;
        }

        public void setHead(HeadBean head) {
            this.head = head;
        }

        public List<ShopBean> getShop() {
            return shop;
        }

        public void setShop(List<ShopBean> shop) {
            this.shop = shop;
        }

        public static class HeadBean {
            private String zffs;
            private String remarks;
            private String type;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getZffs() {
                return zffs;
            }

            public void setZffs(String zffs) {
                this.zffs = zffs;
            }

            public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }
        }

        public static class ShopBean {
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
