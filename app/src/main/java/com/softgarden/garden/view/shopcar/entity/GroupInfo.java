package com.softgarden.garden.view.shopcar.entity;

/**
 * Created by qiang-pc on 2016/6/22.
 */
public class GroupInfo {

    private String Id;
    private String name;
    private boolean isChoosed;

    public GroupInfo(String id, String name) {
        Id = id;
        this.name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }
}
