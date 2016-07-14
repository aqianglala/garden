package com.softgarden.garden.entity;

import com.softgarden.garden.base.BaseDao;

/**
 * Created by qiang-pc on 2016/7/4.
 */
public class UserEntity extends BaseDao {

    /**
     * status : 1
     * errorMsg : 强制改密
     * data : {"CustomerNo":"GZ_0001","CustomerName":"东莞呵呵蛋糕店","Shipadd":"东莞西城区","Contact":"李先生",
     * "Tel":"18873563438","bresaleman":"你好","ReturnType":"3","kxd":"1","phone":"18873563438",
     * "ReturnType_name":"可退可换"}
     */

    private String status;
    private String errorMsg;
    /**
     * CustomerNo : GZ_0001
     * CustomerName : 东莞呵呵蛋糕店
     * Shipadd : 东莞西城区
     * Contact : 李先生
     * Tel : 18873563438
     * bresaleman : 你好
     * ReturnType : 3
     * kxd : 1
     * phone : 18873563438
     * ReturnType_name : 可退可换
     */

    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String CustomerNo;
        private String CustomerName;
        private String Shipadd;
        private String Contact;
        private String Tel;
        private String bresaleman;
        private String ReturnType;
        private String kxd;
        private String phone;
        private String ReturnType_name;

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

        public String getContact() {
            return Contact;
        }

        public void setContact(String Contact) {
            this.Contact = Contact;
        }

        public String getTel() {
            return Tel;
        }

        public void setTel(String Tel) {
            this.Tel = Tel;
        }

        public String getBresaleman() {
            return bresaleman;
        }

        public void setBresaleman(String bresaleman) {
            this.bresaleman = bresaleman;
        }

        public String getReturnType() {
            return ReturnType;
        }

        public void setReturnType(String ReturnType) {
            this.ReturnType = ReturnType;
        }

        public String getKxd() {
            return kxd;
        }

        public void setKxd(String kxd) {
            this.kxd = kxd;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getReturnType_name() {
            return ReturnType_name;
        }

        public void setReturnType_name(String ReturnType_name) {
            this.ReturnType_name = ReturnType_name;
        }
    }
}
