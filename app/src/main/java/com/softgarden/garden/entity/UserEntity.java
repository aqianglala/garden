package com.softgarden.garden.entity;

import com.softgarden.garden.base.BaseDao;

/**
 * Created by qiang-pc on 2016/7/4.
 */
public class UserEntity extends BaseDao {

    /**
     * status : 1
     * errorMsg :
     * data : {"id":"1","username":"GZ_0001","nickname":"广州真好面包店","phone":"18873563438",
     * "last_login_time":"0","state":"1","address":"广州市天河区棠下村","telephone":"18873563438",
     * "status":"1","token":"cc076b76bc001ff54d6090142d473e34"}
     */

    private String status;
    private String errorMsg;
    /**
     * id : 1
     * username : GZ_0001
     * nickname : 广州真好面包店
     * phone : 18873563438
     * last_login_time : 0
     * state : 1
     * address : 广州市天河区棠下村
     * telephone : 18873563438
     * status : 1
     * token : cc076b76bc001ff54d6090142d473e34
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
        private String id;
        private String username;
        private String nickname;
        private String phone;
        private String last_login_time;
        private String state;
        private String address;
        private String telephone;
        private String status;
        private String token;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getLast_login_time() {
            return last_login_time;
        }

        public void setLast_login_time(String last_login_time) {
            this.last_login_time = last_login_time;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
