package com.softgarden.garden.view.login.entity;

/**
 * Created by qiang-pc on 2016/7/1.
 */
public class LoginBean {

    /**
     * address : 广州市天河区棠下村
     * id : 1
     * last_login_time : 0
     * nickname : 广州真好面包店
     * password : 1b1dc7ce30120c3cf8734e26100bf145
     * phone : 18873563438
     * state : 0
     * status : 1
     * telephone : 18873563438
     * username : GZ_0001
     */

    private DataBean data;
    /**
     * data : {"address":"广州市天河区棠下村","id":"1","last_login_time":"0","nickname":"广州真好面包店",
     * "password":"1b1dc7ce30120c3cf8734e26100bf145","phone":"18873563438","state":"0",
     * "status":"1","telephone":"18873563438","username":"GZ_0001"}
     * errorMsg : 强制改密
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
        private String address;
        private String id;
        private String last_login_time;
        private String nickname;
        private String password;
        private String phone;
        private String state;
        private String status;
        private String telephone;
        private String username;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLast_login_time() {
            return last_login_time;
        }

        public void setLast_login_time(String last_login_time) {
            this.last_login_time = last_login_time;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
