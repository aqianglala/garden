package com.softgarden.garden.view.historyOrders;

import java.util.List;

/**
 * Created by qiang-pc on 2016/6/21.
 */
public class DetailListBeanTest {

    /**
     * arr : [{"back":"10.10%","imgurl":"https://ss0.bdstatic
     * .com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1466503087&di
     * =c210616c27b378d6fcd5bd76ebd02d3b&src=http://img.hb.aicdn
     * .com/d2024a8a998c8d3e4ba842e40223c23dfe1026c8bbf3-OudiPA_fw580","name":"a2","num":5,
     * "number":"123","price":20,"weight":80}]
     * ordernumb : 202020202
     * price : 88
     * totalamount : 88
     */

    private String ordernumb;
    private int price;
    private int totalamount;
    /**
     * back : 10.10%
     * imgurl : https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size
     * =b4000_4000&sec=1466503087&di=c210616c27b378d6fcd5bd76ebd02d3b&src=http://img.hb.aicdn
     * .com/d2024a8a998c8d3e4ba842e40223c23dfe1026c8bbf3-OudiPA_fw580
     * name : a2
     * num : 5
     * number : 123
     * price : 20
     * weight : 80
     */

    private List<ArrBean> arr;

    public String getOrdernumb() {
        return ordernumb;
    }

    public void setOrdernumb(String ordernumb) {
        this.ordernumb = ordernumb;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(int totalamount) {
        this.totalamount = totalamount;
    }

    public List<ArrBean> getArr() {
        return arr;
    }

    public void setArr(List<ArrBean> arr) {
        this.arr = arr;
    }

    public static class ArrBean {
        private String back;
        private String imgurl;
        private String name;
        private int num;
        private String number;
        private int price;
        private int weight;

        public String getBack() {
            return back;
        }

        public void setBack(String back) {
            this.back = back;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }
}
