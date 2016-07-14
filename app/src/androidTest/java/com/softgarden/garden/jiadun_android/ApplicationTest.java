package com.softgarden.garden.jiadun_android;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.google.gson.Gson;
import com.softgarden.garden.dao.ProductDao;
import com.softgarden.garden.entity.HistoryOrderEntity;
import com.softgarden.garden.entity.ProductItem;
import com.softgarden.garden.utils.LogUtils;

import java.util.List;
import java.util.Map;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testInsert(){
        ProductDao dao = ProductDao.getDao();
        for (int i=0;i<10;i++){
            ProductItem productItem = new ProductItem(i+"","面包"+i,i+"","生命包",i+"","sss","30/kg",
                    "包",i+"","",i+"",i+"",i+"",i+"",i+"",i+"");
            dao.insert(productItem);
        }
    }

    public void testUpdate(){
        ProductItem productItem = new ProductItem("ss","ss","ss","ss","0","ss","ss","ss","ss",
                "ss","ss","ss","ss","ss","ss","ss");
        ProductDao dao = ProductDao.getDao();
        dao.updateByProductId(productItem);
    }

    public void testQuery(){
        ProductDao dao = ProductDao.getDao();
        List<ProductItem> all = dao.findAll();
        Log.e("way",all.size()+"");
    }
    String json = "{\n" +
            "    \"status\": \"1\",\n" +
            "    \"errorMsg\": \"\",\n" +
            "    \"data\": {\n" +
            "        \"2016-07-13\": [\n" +
            "            {\n" +
            "                \"OrderDate\": \"2016-07-13\",\n" +
            "                \"OrderNo\": \"1\",\n" +
            "                \"Qty\": \"10\",\n" +
            "                \"Amount\": \"10.00\",\n" +
            "                \"tgs\": \"0\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"OrderDate\": \"2016-07-13\",\n" +
            "                \"OrderNo\": \"3\",\n" +
            "                \"Qty\": \"10\",\n" +
            "                \"Amount\": \"10.00\",\n" +
            "                \"tgs\": \"0\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"OrderDate\": \"2016-07-13\",\n" +
            "                \"OrderNo\": \"1\",\n" +
            "                \"Qty\": \"10\",\n" +
            "                \"Amount\": \"10.00\",\n" +
            "                \"tgs\": \"0\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"2016-07-12\": [\n" +
            "            {\n" +
            "                \"OrderDate\": \"2016-07-12\",\n" +
            "                \"OrderNo\": \"2\",\n" +
            "                \"Qty\": \"10\",\n" +
            "                \"Amount\": \"10.00\",\n" +
            "                \"tgs\": \"0\"\n" +
            "            }\n" +
            "        ]\n" +
            "    }\n" +
            "}";
    public void testToMap(){
        HistoryOrderEntity historyOrderEntity = new Gson().fromJson(json, HistoryOrderEntity.class);
        for(Map.Entry<String,List<HistoryOrderEntity.DataBean>> entry:historyOrderEntity.getData().entrySet()){
            LogUtils.e(entry.getKey());
        }
    }
}