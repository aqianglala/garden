package com.softgarden.garden.view.shopcar.entity;

/**
 * Created by qiang-pc on 2016/6/22.
 */
public class GroupInfo {

    private long id;
    private String groupId;
    private String groupName;
    private boolean isChoosed;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public GroupInfo(long id, String groupId, String groupName) {
        this.id = id;
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }
}
