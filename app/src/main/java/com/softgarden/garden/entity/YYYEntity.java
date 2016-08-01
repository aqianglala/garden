package com.softgarden.garden.entity;

import com.softgarden.garden.base.BaseDao;

import java.util.List;

/**
 * Created by qiang-pc on 2016/8/1.
 */
public class YYYEntity extends BaseDao{

    /**
     * status : 1
     * errorMsg :
     * data : [{"id":"1","CustomerNo":"GZ_0001","CustomerName":"测试1","Shipadd":"广州天河区",
     * "status":"1"},{"id":"2","CustomerNo":"GZ_0002","CustomerName":"测试2","Shipadd":"广州天河区",
     * "status":"1"},{"id":"3","CustomerNo":"GZ_0003","CustomerName":"测试3","Shipadd":"广州",
     * "status":"1"},{"id":"4","CustomerNo":"GZ_0004","CustomerName":"测试4","Shipadd":"广州",
     * "status":"1"},{"id":"5","CustomerNo":"GZ10016","CustomerName":"广州现金","Shipadd":"广州吉山",
     * "status":"1"}]
     */

    private String status;
    private String errorMsg;
    /**
     * id : 1
     * CustomerNo : GZ_0001
     * CustomerName : 测试1
     * Shipadd : 广州天河区
     * status : 1
     */

    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean extends me.yokeyword.indexablelistview.IndexEntity {
        private String id;
        private String name;
        private String CustomerNo;
        private String CustomerName;
        private String Shipadd;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCustomerNo() {
            return CustomerNo;
        }

        public void setCustomerNo(String CustomerNo) {
            this.CustomerNo = CustomerNo;
        }

        public String getCustomerName() {
            return CustomerName;
        }

        public void setCustomerName(String CustomerName) {
            this.CustomerName = CustomerName;
        }

        public String getShipadd() {
            return Shipadd;
        }

        public void setShipadd(String Shipadd) {
            this.Shipadd = Shipadd;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public void setName(String name) {
            this.name = name;
        }
    }
}
