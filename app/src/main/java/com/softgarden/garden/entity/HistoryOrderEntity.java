package com.softgarden.garden.entity;

import com.softgarden.garden.base.BaseDao;

import java.util.HashMap;
import java.util.List;

/**
 * Created by qiang-pc on 2016/7/14.
 */
public class HistoryOrderEntity extends BaseDao {

    /**
     * status : 1
     * errorMsg :
     * data : {"2016-07-13":[{"OrderDate":"2016-07-13","OrderNo":"1","Qty":"10","Amount":"10.00",
     * "tgs":"0"},{"OrderDate":"2016-07-13","OrderNo":"3","Qty":"10","Amount":"10.00","tgs":"0"},
     * {"OrderDate":"2016-07-13","OrderNo":"1","Qty":"10","Amount":"10.00","tgs":"0"}],
     * "2016-07-12":[{"OrderDate":"2016-07-12","OrderNo":"2","Qty":"10","Amount":"10.00",
     * "tgs":"0"}]}
     */

    private String status;
    private String errorMsg;
    private HashMap<String,List<DataBean>> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public HashMap<String, List<DataBean>> getData() {
        return data;
    }

    public void setData(HashMap<String, List<DataBean>> data) {
        this.data = data;
    }

    /**
     * Amount : 10.00
     * OrderDate : 2016-07-13
     * OrderNo : 1
     * Qty : 10
     * tgs : 0
     */

    public static class DataBean {

        /**
         * OrderDate : 2016-07-15
         * OrderNo : 66354
         * CustomerNo : GZ_0001
         * type : 1
         * is_pay : 3
         * Amount : 33
         * Qty : 17
         * tgs : 0
         */

        private String OrderDate;
        private String OrderNo;
        private String CustomerNo;
        private String type;
        private int is_pay;
        private int Amount;
        private int Qty;
        private int tgs;

        public String getOrderDate() {
            return OrderDate;
        }

        public void setOrderDate(String OrderDate) {
            this.OrderDate = OrderDate;
        }

        public String getOrderNo() {
            return OrderNo;
        }

        public void setOrderNo(String OrderNo) {
            this.OrderNo = OrderNo;
        }

        public String getCustomerNo() {
            return CustomerNo;
        }

        public void setCustomerNo(String CustomerNo) {
            this.CustomerNo = CustomerNo;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getIs_pay() {
            return is_pay;
        }

        public void setIs_pay(int is_pay) {
            this.is_pay = is_pay;
        }

        public int getAmount() {
            return Amount;
        }

        public void setAmount(int Amount) {
            this.Amount = Amount;
        }

        public int getQty() {
            return Qty;
        }

        public void setQty(int Qty) {
            this.Qty = Qty;
        }

        public int getTgs() {
            return tgs;
        }

        public void setTgs(int tgs) {
            this.tgs = tgs;
        }
    }

}
