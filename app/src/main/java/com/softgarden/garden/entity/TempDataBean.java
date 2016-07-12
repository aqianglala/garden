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
