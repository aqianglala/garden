package com.softgarden.garden.dao;

/**
 * Created by qiang-pc on 2016/7/8.
 */
public class TableConfig {
    public static final String DB_NAME = "garden.db";
    public static final String TABLE_NAME = "product";
    /**
     * 数据表的字段
     */
    public static class Product{

        public static final String PRODUCT_ID = "car_id";
        public static final String PRODUCT_NAME = "product_name";
        public static final String PRODUCT_NUMBER = "product_number";
        public static final String PRODUCT_PICTURE = "product_picture";
        public static final String PRODUCT_PRICE = "product_price";
        public static final String PRODUCT_CATEGORY = "product_category";
        public static final String PRODUCT_GUIGE = "product_guige";
        public static final String PRODUCT_SPECIAL = "product_special";
        public static final String PRODUCT_SORT = "product_sort";
        public static final String PRODUCT_WHETHER = "product_whether";
        public static final String PRODUCT_TUANGOU = "product_tuangou";
        public static final String PRODUCT_YUGU = "product_yugu";
    }
}