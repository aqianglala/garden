package com.softgarden.garden.view.start.entity;

/**
 * Created by qiang-pc on 2016/6/16.
 */
public class MessageBean {

    public String message ;
    public String itemclassName ;
    public MessageBean(String message) {
        this.message = message ;
    }

    public MessageBean(String message,String itemclassName) {
        this.message = message ;
        this.itemclassName = itemclassName ;
    }
}
