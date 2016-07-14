package com.softgarden.garden.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiang-pc on 2016/7/14.
 */
public class TempData {
    private List<TempDataBean> tempDataBeans = new ArrayList<>();

    public List<TempDataBean> getTempDataBeans() {
        return tempDataBeans;
    }

    public void setTempDataBeans(List<TempDataBean> tempDataBeans) {
        this.tempDataBeans = tempDataBeans;
    }
}
