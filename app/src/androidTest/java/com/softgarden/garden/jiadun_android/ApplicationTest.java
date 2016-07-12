package com.softgarden.garden.jiadun_android;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.softgarden.garden.dao.ProductDao;
import com.softgarden.garden.entity.ProductItem;

import java.util.List;

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
            ProductItem productItem = new ProductItem(i+"","蛋糕"+i,i+"",null,"22",i+"",null,"1",
                    "22","22","22/克","998");
            dao.insert(productItem);
        }
    }

    public void testUpdate(){
        ProductItem productItem = new ProductItem("8","蛋糕88","",null,"88","",null,"1",
                "22","22","22/克","998");
        ProductDao dao = ProductDao.getDao();
        dao.updateByProductId(productItem);
    }

    public void testQuery(){
        ProductDao dao = ProductDao.getDao();
        List<ProductItem> all = dao.findAll();
        Log.e("way",all.size()+"");
    }
}