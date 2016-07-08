package com.softgarden.garden.dao;

import android.database.sqlite.SQLiteDatabase;

import com.softgarden.garden.base.BaseApplication;

/**
 * Created by qiang-pc on 2016/7/8.
 */
public class DbManager {
    private static DbManager manager;
    private MySQLiteOpenHelper mySQLiteOpenHelper;
    private SQLiteDatabase db;

    /**
     * 私有化构造器
     */
    private DbManager() {
        //创建数据库
        mySQLiteOpenHelper = MySQLiteOpenHelper.getInstance(BaseApplication.getContext());
        if (db == null) {
            db = mySQLiteOpenHelper.getWritableDatabase();
        }
    }

    /**
     * 单例DbManager类
     *
     * @return 返回DbManager对象
     */
    public static DbManager newInstances() {
        if (manager == null) {
            manager = new DbManager();
        }
        return manager;
    }

    /**
     * 获取数据库的对象
     *
     * @return 返回SQLiteDatabase数据库的对象
     */
    public SQLiteDatabase getDataBase() {
        return db;
    }
}