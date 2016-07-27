package com.softgarden.garden.entity;

import com.softgarden.garden.base.BaseDao;

/**
 * Created by qiang-pc on 2016/7/21.
 */
public class PayEntity extends BaseDao{

    /**
     * param :
     */

    private DataBean data;
    /**
     * data : {"param":""}
     * errorMsg :
     * status : 1
     */

    private String errorMsg;
    private String status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class DataBean {
        private String param;
        private String OrderNo;

        public String getOrderNo() {
            return OrderNo;
        }

        public void setOrderNo(String orderNo) {
            OrderNo = orderNo;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }
    }
}
