package com.softgarden.garden.entity;

/**
 * Created by qiang-pc on 2016/7/12.
 */
public class TempDataBean {

    // 团购数目
    private int tuangou;
    // 团购旁边的加减中的数量
    private int shuliang;
    // 产品编号
    private String IetmNo;
    // 是否已从购物车删除
    private boolean isDeleted;

    public TempDataBean(int tuangou, int shuliang, String ietmNo) {
        this.tuangou = tuangou;
        this.shuliang = shuliang;
        IetmNo = ietmNo;
    }

    public TempDataBean(int tuangou, int shuliang, String ietmNo, boolean isDeleted) {
        this.tuangou = tuangou;
        this.shuliang = shuliang;
        IetmNo = ietmNo;
        this.isDeleted = isDeleted;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

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

    public String getIetmNo() {
        return IetmNo;
    }

    public void setIetmNo(String ietmNo) {
        IetmNo = ietmNo;
    }
}
