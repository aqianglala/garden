package com.softgarden.garden.entity;

import com.softgarden.garden.base.BaseDao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qiang-pc on 2016/7/6.
 */
public class IndexEntity extends BaseDao{

    /**
     * status : 1
     * errorMsg :
     * data : {"shop":[{"itemclassName":"面包","itemclassCode":"3","child":[{"itemclassCode":"3",
     * "ItemGroupName":"生命包","Itemgroupcdoe":"B01","goods":[{"itemclassCode":"3",
     * "itemclassName":"面包","Itemgroupcdoe":"B01","ItemGroupName":"生命包","IetmNo":"3001",
     * "ItemName":"嘉顿生命面包(蜡纸)","spec":"450g/袋","Unit":"包","bzj":"1.0000","picture":"","proQty":0,
     * "Price":0,"IsSpecial":0,"returnrate":0},{"itemclassCode":"3","itemclassName":"面包",
     * "Itemgroupcdoe":"B01","ItemGroupName":"生命包","IetmNo":"3107","ItemName":"蜜糖甜生命面包",
     * "spec":"450g/袋","Unit":"包","bzj":"1.0000","picture":"","proQty":0,"Price":0,"IsSpecial":0,
     * "returnrate":0}]},{"itemclassCode":"3","ItemGroupName":"三文治","Itemgroupcdoe":"B02",
     * "goods":[{"itemclassCode":"3","itemclassName":"面包","Itemgroupcdoe":"B02",
     * "ItemGroupName":"三文治","IetmNo":"3155","ItemName":"小平方包","spec":"350g/袋","Unit":"包",
     * "bzj":"1.0000","picture":"","proQty":0,"Price":0,"IsSpecial":0,"returnrate":0}]},
     * {"itemclassCode":"3","ItemGroupName":"忌廉槟","Itemgroupcdoe":"B03",
     * "goods":[{"itemclassCode":"3","itemclassName":"面包","Itemgroupcdoe":"B03",
     * "ItemGroupName":"忌廉槟","IetmNo":"3064","ItemName":"菠萝椰丝忌廉包","spec":"80g/袋","Unit":"袋",
     * "bzj":"1.0000","picture":"","proQty":0,"Price":0,"IsSpecial":0,"returnrate":0}]}]},
     * {"itemclassName":"蛋糕","itemclassCode":"4","child":[{"itemclassCode":"4",
     * "ItemGroupName":"牛油蛋糕","Itemgroupcdoe":"K01","goods":[{"itemclassCode":"4",
     * "itemclassName":"蛋糕","Itemgroupcdoe":"K01","ItemGroupName":"牛油蛋糕","IetmNo":"4001",
     * "ItemName":"宝宝蛋糕","spec":"1篮X42包","Unit":"包","bzj":"1.0000","picture":"","proQty":0,
     * "Price":0,"IsSpecial":0,"returnrate":0}]}]}],
     * "banner":["/Uploads/Picture/2016-07-12/578491c2d2704.png",
     * "/Uploads/Picture/2016-07-12/578491b8b9ce3.png","/Uploads/Picture/2016-07-12/578491af2651e
     * .png"]}
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

    public static class DataBean implements Serializable{
        /**
         * itemclassName : 面包
         * itemclassCode : 3
         * child : [{"itemclassCode":"3","ItemGroupName":"生命包","Itemgroupcdoe":"B01",
         * "goods":[{"itemclassCode":"3","itemclassName":"面包","Itemgroupcdoe":"B01",
         * "ItemGroupName":"生命包","IetmNo":"3001","ItemName":"嘉顿生命面包(蜡纸)","spec":"450g/袋",
         * "Unit":"包","bzj":"1.0000","picture":"","proQty":0,"Price":0,"IsSpecial":0,
         * "returnrate":0},{"itemclassCode":"3","itemclassName":"面包","Itemgroupcdoe":"B01",
         * "ItemGroupName":"生命包","IetmNo":"3107","ItemName":"蜜糖甜生命面包","spec":"450g/袋","Unit":"包",
         * "bzj":"1.0000","picture":"","proQty":0,"Price":0,"IsSpecial":0,"returnrate":0}]},
         * {"itemclassCode":"3","ItemGroupName":"三文治","Itemgroupcdoe":"B02",
         * "goods":[{"itemclassCode":"3","itemclassName":"面包","Itemgroupcdoe":"B02",
         * "ItemGroupName":"三文治","IetmNo":"3155","ItemName":"小平方包","spec":"350g/袋","Unit":"包",
         * "bzj":"1.0000","picture":"","proQty":0,"Price":0,"IsSpecial":0,"returnrate":0}]},
         * {"itemclassCode":"3","ItemGroupName":"忌廉槟","Itemgroupcdoe":"B03",
         * "goods":[{"itemclassCode":"3","itemclassName":"面包","Itemgroupcdoe":"B03",
         * "ItemGroupName":"忌廉槟","IetmNo":"3064","ItemName":"菠萝椰丝忌廉包","spec":"80g/袋","Unit":"袋",
         * "bzj":"1.0000","picture":"","proQty":0,"Price":0,"IsSpecial":0,"returnrate":0}]}]
         */

        private List<ShopBean> shop;
        private List<String> banner;

        public List<ShopBean> getShop() {
            return shop;
        }

        public void setShop(List<ShopBean> shop) {
            this.shop = shop;
        }

        public List<String> getBanner() {
            return banner;
        }

        public void setBanner(List<String> banner) {
            this.banner = banner;
        }

        public static class ShopBean implements Serializable{
            private String itemclassName;
            private String itemclassCode;
            /**
             * itemclassCode : 3
             * ItemGroupName : 生命包
             * Itemgroupcdoe : B01
             * goods : [{"itemclassCode":"3","itemclassName":"面包","Itemgroupcdoe":"B01",
             * "ItemGroupName":"生命包","IetmNo":"3001","ItemName":"嘉顿生命面包(蜡纸)","spec":"450g/袋",
             * "Unit":"包","bzj":"1.0000","picture":"","proQty":0,"Price":0,"IsSpecial":0,
             * "returnrate":0},{"itemclassCode":"3","itemclassName":"面包","Itemgroupcdoe":"B01",
             * "ItemGroupName":"生命包","IetmNo":"3107","ItemName":"蜜糖甜生命面包","spec":"450g/袋",
             * "Unit":"包","bzj":"1.0000","picture":"","proQty":0,"Price":0,"IsSpecial":0,
             * "returnrate":0}]
             */

            private List<ChildBean> child;

            public String getItemclassName() {
                return itemclassName;
            }

            public void setItemclassName(String itemclassName) {
                this.itemclassName = itemclassName;
            }

            public String getItemclassCode() {
                return itemclassCode;
            }

            public void setItemclassCode(String itemclassCode) {
                this.itemclassCode = itemclassCode;
            }

            public List<ChildBean> getChild() {
                return child;
            }

            public void setChild(List<ChildBean> child) {
                this.child = child;
            }

            public static class ChildBean implements Serializable{
                private String itemclassCode;
                private String ItemGroupName;
                private String Itemgroupcdoe;
                /**
                 * itemclassCode : 3
                 * itemclassName : 面包
                 * Itemgroupcdoe : B01
                 * ItemGroupName : 生命包
                 * IetmNo : 3001
                 * ItemName : 嘉顿生命面包(蜡纸)
                 * spec : 450g/袋
                 * Unit : 包
                 * bzj : 1.0000
                 * picture :
                 * proQty : 0
                 * Price : 0
                 * IsSpecial : 0
                 * returnrate : 0
                 */

                private List<GoodsBean> goods;

                public String getItemclassCode() {
                    return itemclassCode;
                }

                public void setItemclassCode(String itemclassCode) {
                    this.itemclassCode = itemclassCode;
                }

                public String getItemGroupName() {
                    return ItemGroupName;
                }

                public void setItemGroupName(String ItemGroupName) {
                    this.ItemGroupName = ItemGroupName;
                }

                public String getItemgroupcdoe() {
                    return Itemgroupcdoe;
                }

                public void setItemgroupcdoe(String Itemgroupcdoe) {
                    this.Itemgroupcdoe = Itemgroupcdoe;
                }

                public List<GoodsBean> getGoods() {
                    return goods;
                }

                public void setGoods(List<GoodsBean> goods) {
                    this.goods = goods;
                }

                public static class GoodsBean implements Serializable{
                    private String itemclassCode;
                    private String itemclassName;
                    private String Itemgroupcdoe;
                    private String ItemGroupName;
                    private String IetmNo;
                    private String ItemName;
                    private String spec;
                    private String Unit;
                    private String bzj;
                    private String picture;
                    private int proQty;
                    private int Price;
                    private int IsSpecial;
                    private int returnrate;

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

                    public String getItemgroupcdoe() {
                        return Itemgroupcdoe;
                    }

                    public void setItemgroupcdoe(String Itemgroupcdoe) {
                        this.Itemgroupcdoe = Itemgroupcdoe;
                    }

                    public String getItemGroupName() {
                        return ItemGroupName;
                    }

                    public void setItemGroupName(String ItemGroupName) {
                        this.ItemGroupName = ItemGroupName;
                    }

                    public String getIetmNo() {
                        return IetmNo;
                    }

                    public void setIetmNo(String IetmNo) {
                        this.IetmNo = IetmNo;
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

                    public int getPrice() {
                        return Price;
                    }

                    public void setPrice(int Price) {
                        this.Price = Price;
                    }

                    public int getIsSpecial() {
                        return IsSpecial;
                    }

                    public void setIsSpecial(int IsSpecial) {
                        this.IsSpecial = IsSpecial;
                    }

                    public int getReturnrate() {
                        return returnrate;
                    }

                    public void setReturnrate(int returnrate) {
                        this.returnrate = returnrate;
                    }
                }
            }
        }
    }
}
