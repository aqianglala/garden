package com.softgarden.garden.entity;

import com.softgarden.garden.base.BaseDao;

import java.util.List;

/**
 * Created by qiang-pc on 2016/7/6.
 */
public class IndexEntity extends BaseDao{

    /**
     * status : 1
     * errorMsg :
     * data : {"yiji":[{"id":"1","number":"3","title":"面包","pid":"0","sort":"0"},{"id":"2",
     * "number":"4","title":"蛋糕","pid":"0","sort":"0"}],"erji":[{"id":"3","number":"B01",
     * "title":"生命包","pid":"1","sort":"0"},{"id":"4","number":"B02","title":"三文治","pid":"1",
     * "sort":"0"}],"sanji":[{"id":"1","number":"3001","name":"嘉顿生命面包(蜡纸)",
     * "picture":"/Uploads/Picture/2016-07-01/57761aa26ebfa.jpg","category":"3","guige":"450克/袋",
     * "price":"0.02","whether":"1","special":"0.02","sort":"0"},{"id":"2","number":"3107",
     * "name":"蜜糖甜生命面包","picture":"/Uploads/Picture/2016-07-01/57761aa26ebfa.jpg","category":"3",
     * "guige":"450克/袋","price":"0.01","whether":"0","special":"0.02","sort":"0"},{"id":"3",
     * "number":"3117","name":"高纤维生命麦包","picture":"/Uploads/Picture/2016-07-06/577c6bdc37eb5
     * .jpg","category":"3","guige":"450克/袋","price":"0.02","whether":"0","special":"0.01",
     * "sort":"0"},{"id":"4","number":"3288","name":"高蛋白生命面包",
     * "picture":"/Uploads/Picture/2016-07-06/577c6c03c0dfb.jpg","category":"3","guige":"450克/袋",
     * "price":"0.03","whether":"0","special":"0.01","sort":"0"}],
     * "banner":["/Uploads/Picture/2016-07-06/577ccc4feb6f5.jpg",
     * "/Uploads/Picture/2016-07-06/577ccc5dbc654.jpg","/Uploads/Picture/2016-07-06/577ccc68bbca6
     * .jpg"],"thh":{"thh_tui":"1","thh_huan":"0"}}
     */

    private String status;
    private String errorMsg;
    /**
     * yiji : [{"id":"1","number":"3","title":"面包","pid":"0","sort":"0"},{"id":"2","number":"4",
     * "title":"蛋糕","pid":"0","sort":"0"}]
     * erji : [{"id":"3","number":"B01","title":"生命包","pid":"1","sort":"0"},{"id":"4",
     * "number":"B02","title":"三文治","pid":"1","sort":"0"}]
     * sanji : [{"id":"1","number":"3001","name":"嘉顿生命面包(蜡纸)",
     * "picture":"/Uploads/Picture/2016-07-01/57761aa26ebfa.jpg","category":"3","guige":"450克/袋",
     * "price":"0.02","whether":"1","special":"0.02","sort":"0"},{"id":"2","number":"3107",
     * "name":"蜜糖甜生命面包","picture":"/Uploads/Picture/2016-07-01/57761aa26ebfa.jpg","category":"3",
     * "guige":"450克/袋","price":"0.01","whether":"0","special":"0.02","sort":"0"},{"id":"3",
     * "number":"3117","name":"高纤维生命麦包","picture":"/Uploads/Picture/2016-07-06/577c6bdc37eb5
     * .jpg","category":"3","guige":"450克/袋","price":"0.02","whether":"0","special":"0.01",
     * "sort":"0"},{"id":"4","number":"3288","name":"高蛋白生命面包",
     * "picture":"/Uploads/Picture/2016-07-06/577c6c03c0dfb.jpg","category":"3","guige":"450克/袋",
     * "price":"0.03","whether":"0","special":"0.01","sort":"0"}]
     * banner : ["/Uploads/Picture/2016-07-06/577ccc4feb6f5.jpg","/Uploads/Picture/2016-07-06
     * /577ccc5dbc654.jpg","/Uploads/Picture/2016-07-06/577ccc68bbca6.jpg"]
     * thh : {"thh_tui":"1","thh_huan":"0"}
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
        /**
         * thh_tui : 1
         * thh_huan : 0
         */

        private ThhBean thh;
        /**
         * id : 1
         * number : 3
         * title : 面包
         * pid : 0
         * sort : 0
         */

        private List<YijiBean> yiji;
        /**
         * id : 3
         * number : B01
         * title : 生命包
         * pid : 1
         * sort : 0
         */

        private List<ErjiBean> erji;
        /**
         * id : 1
         * number : 3001
         * name : 嘉顿生命面包(蜡纸)
         * picture : /Uploads/Picture/2016-07-01/57761aa26ebfa.jpg
         * category : 3
         * guige : 450克/袋
         * price : 0.02
         * whether : 1
         * special : 0.02
         * sort : 0
         */

        private List<SanjiBean> sanji;
        private List<String> banner;

        public ThhBean getThh() {
            return thh;
        }

        public void setThh(ThhBean thh) {
            this.thh = thh;
        }

        public List<YijiBean> getYiji() {
            return yiji;
        }

        public void setYiji(List<YijiBean> yiji) {
            this.yiji = yiji;
        }

        public List<ErjiBean> getErji() {
            return erji;
        }

        public void setErji(List<ErjiBean> erji) {
            this.erji = erji;
        }

        public List<SanjiBean> getSanji() {
            return sanji;
        }

        public void setSanji(List<SanjiBean> sanji) {
            this.sanji = sanji;
        }

        public List<String> getBanner() {
            return banner;
        }

        public void setBanner(List<String> banner) {
            this.banner = banner;
        }

        public static class ThhBean {
            private String thh_tui;
            private String thh_huan;

            public String getThh_tui() {
                return thh_tui;
            }

            public void setThh_tui(String thh_tui) {
                this.thh_tui = thh_tui;
            }

            public String getThh_huan() {
                return thh_huan;
            }

            public void setThh_huan(String thh_huan) {
                this.thh_huan = thh_huan;
            }
        }

        public static class YijiBean {
            private String id;
            private String number;
            private String title;
            private String pid;
            private String sort;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }
        }

        public static class ErjiBean {
            private String id;
            private String number;
            private String title;
            private String pid;
            private String sort;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }
        }

        public static class SanjiBean {
            private String id;
            private String number;
            private String name;
            private String picture;
            private String category;
            private String guige;
            private String price;
            private String whether;
            private String special;
            private String sort;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getGuige() {
                return guige;
            }

            public void setGuige(String guige) {
                this.guige = guige;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getWhether() {
                return whether;
            }

            public void setWhether(String whether) {
                this.whether = whether;
            }

            public String getSpecial() {
                return special;
            }

            public void setSpecial(String special) {
                this.special = special;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }
        }
    }
}
