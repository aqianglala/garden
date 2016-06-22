package com.softgarden.garden.view.shopcar;

/**
 * Created by qiang-pc on 2016/6/22.
 */
public class ProductInfo {

    private String Id;
    private String name;
    private float price;
    private int count;
    private boolean isChoosed;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public ProductInfo(String id, String name, float price, int count) {
        Id = id;
        this.name = name;
        this.price = price;
        this.count = count;
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
