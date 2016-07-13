package com.softgarden.garden.view.shopcar.entity;

/**
 * Created by qiang-pc on 2016/6/22.
 */
public class ProductInfo {

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
    private boolean isChoosed;
    // 团购数目
    private int tuangou;
    // 团购旁边的加减中的数量
    private int shuliang;

    public int getTuangou() {
        return tuangou;
    }

    public void setTuangou(int tuangou) {
        this.tuangou = tuangou;
    }

    public int getShuliang() {
        return shuliang;
    }

    public void setShuliang(int shuliang) {
        this.shuliang = shuliang;
    }

    public ProductInfo(String itemclassCode, String itemclassName, String itemgroupcdoe, String
            itemGroupName, String ietmNo, String itemName, String spec, String unit, String bzj,
                     String picture, int proQty, int price, int isSpecial, int returnrate,
                     boolean isChoosed, int tuangou, int shuliang) {
        this.itemclassCode = itemclassCode;
        this.itemclassName = itemclassName;
        Itemgroupcdoe = itemgroupcdoe;
        ItemGroupName = itemGroupName;
        IetmNo = ietmNo;
        ItemName = itemName;
        this.spec = spec;
        Unit = unit;
        this.bzj = bzj;
        this.picture = picture;
        this.proQty = proQty;
        Price = price;
        IsSpecial = isSpecial;
        this.returnrate = returnrate;
        this.isChoosed = isChoosed;
        this.tuangou = tuangou;
        this.shuliang = shuliang;
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

    public String getItemgroupcdoe() {
        return Itemgroupcdoe;
    }

    public void setItemgroupcdoe(String itemgroupcdoe) {
        Itemgroupcdoe = itemgroupcdoe;
    }

    public String getItemGroupName() {
        return ItemGroupName;
    }

    public void setItemGroupName(String itemGroupName) {
        ItemGroupName = itemGroupName;
    }

    public String getIetmNo() {
        return IetmNo;
    }

    public void setIetmNo(String ietmNo) {
        IetmNo = ietmNo;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
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

    public void setUnit(String unit) {
        Unit = unit;
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

    public void setPrice(int price) {
        Price = price;
    }

    public int getIsSpecial() {
        return IsSpecial;
    }

    public void setIsSpecial(int isSpecial) {
        IsSpecial = isSpecial;
    }

    public int getReturnrate() {
        return returnrate;
    }

    public void setReturnrate(int returnrate) {
        this.returnrate = returnrate;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }
}
