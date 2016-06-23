package com.softgarden.garden.view.buy.entity;

import java.util.List;

/**
 * Created by qiang-pc on 2016/6/14.
 */
public class TestDataBean {

    /**
     * arr : [{"back":"10.10%","name":"a2","number":"123","prediction":10,"price":20,"weight":80}]
     * num : 8
     * type : a
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int num;
        private String type;
        /**
         * back : 10.10%
         * name : a2
         * number : 123
         * prediction : 10
         * price : 20
         * weight : 80
         */

        private List<ArrBean> arr;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<ArrBean> getArr() {
            return arr;
        }

        public void setArr(List<ArrBean> arr) {
            this.arr = arr;
        }

        public static class ArrBean {
            private String back;
            private String name;
            private String number;
            private int prediction;
            private int price;
            private int weight;

            public String getBack() {
                return back;
            }

            public void setBack(String back) {
                this.back = back;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public int getPrediction() {
                return prediction;
            }

            public void setPrediction(int prediction) {
                this.prediction = prediction;
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
}
