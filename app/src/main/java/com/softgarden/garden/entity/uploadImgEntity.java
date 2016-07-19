package com.softgarden.garden.entity;

import com.softgarden.garden.base.BaseDao;

import java.util.List;

/**
 * Created by qiang-pc on 2016/7/19.
 */
public class UploadImgEntity extends BaseDao{

    private DataBean data;
    /**
     * data : {"pic_path":["/Upload/image/20160719/14689169265921.jpg"]}
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
        private List<String> pic_path;

        public List<String> getPic_path() {
            return pic_path;
        }

        public void setPic_path(List<String> pic_path) {
            this.pic_path = pic_path;
        }
    }
}
