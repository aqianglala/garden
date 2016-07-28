package com.softgarden.garden.entity;

import com.softgarden.garden.base.BaseDao;

/**
 * Created by qiang-pc on 2016/7/28.
 */
public class GetFindCodeEntity extends BaseDao {

    /**
     * status : 1
     * errorMsg :
     * data : {"randNum":9843,"phone":"18873563438"}
     */

    private String status;
    private String errorMsg;
    /**
     * randNum : 9843
     * phone : 18873563438
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
        private int randNum;
        private String phone;

        public int getRandNum() {
            return randNum;
        }

        public void setRandNum(int randNum) {
            this.randNum = randNum;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
