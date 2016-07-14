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
        private String Amount;
        private String OrderDate;
        private String OrderNo;
        private String Qty;
        private String tgs;

        public String getAmount() {
            return Amount;
        }

        public void setAmount(String Amount) {
            this.Amount = Amount;
        }

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

        public String getQty() {
            return Qty;
        }

        public void setQty(String Qty) {
            this.Qty = Qty;
        }

        public String getTgs() {
            return tgs;
        }

        public void setTgs(String tgs) {
            this.tgs = tgs;
        }
    }

}
