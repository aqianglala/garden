package com.softgarden.garden.entity;

import com.softgarden.garden.base.BaseDao;

/**
 * Created by qiang-pc on 2016/7/25.
 */
public class CommitOrderResultEntity extends BaseDao{

    /**
     * status : 1
     * errorMsg :
     * data : {"OrderNo":"201607211831002256"}
     */

    private String status;
    private String errorMsg;
    /**
     * OrderNo : 201607211831002256
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
        private String OrderNo;

        public String getOrderNo() {
            return OrderNo;
        }

        public void setOrderNo(String OrderNo) {
            this.OrderNo = OrderNo;
        }
    }
}
