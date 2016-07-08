package com.softgarden.garden.jiadun_android;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.softgarden.garden.dao.ProductBean;
import com.softgarden.garden.dao.TableConfig;
import com.softgarden.garden.dao.TableOperate;

import java.util.ArrayList;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testInsert(){
        TableOperate tableOperate = new TableOperate();
        for(int i=0;i<10;i++){
            ProductBean productBean = new ProductBean();
            productBean.setCar_id("id_"+i);
            productBean.setProduct_name("name_"+i);
            productBean.setProduct_number("number_"+i);
            productBean.setProduct_price("price_"+i);
            productBean.setProduct_special("special_"+i);
            productBean.setProduct_category("category_"+i);
            productBean.setProduct_guige("guige_"+i);
            tableOperate.insert(TableConfig.TABLE_NAME,productBean);
        }
    }

    public void testDelete(){
        TableOperate tableOperate = new TableOperate();
        tableOperate.delete(TableConfig.TABLE_NAME,TableConfig.Product.PRODUCT_ID,"id_9");
    }

    public void testUpdate(){
        ProductBean productBean = new ProductBean();
        productBean.setProduct_name("修改了");
        TableOperate tableOperate = new TableOperate();
        tableOperate.uptate(TableConfig.TABLE_NAME,TableConfig.Product.PRODUCT_ID,"id_8",
                productBean);
    }

    public void testQuery(){
        TableOperate tableOperate = new TableOperate();
        ArrayList<ProductBean> query = tableOperate.query(TableConfig.TABLE_NAME, ProductBean
                .class, TableConfig.Product.PRODUCT_NAME, "修改了");
        for(int i = 0;i<query.size();i++){
            ProductBean productBean = query.get(i);
            Log.e("query",productBean.getProduct_name());
        }
    }

    public void testQueryAll(){
        TableOperate tableOperate = new TableOperate();
        ArrayList<ProductBean> query = tableOperate.queryAll(TableConfig.TABLE_NAME, ProductBean
                .class);
        for(int i = 0;i<query.size();i++){
            ProductBean productBean = query.get(i);
            Log.e("query",productBean.getProduct_name());
        }
    }
}