package com.softgarden.garden.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by qiang-pc on 2016/7/7.
 */
public class MySqliteOpenHelper extends SQLiteOpenHelper {
//    "id": "5",
//            "number": "3155",
//            "name": "小平方包",
//            "picture": "/Uploads/Picture/2016-07-06/577c6c4fc4d29.jpg",
//            "category": "4",
//            "guige": "1篮X9包",
//            "price": "0.03",
//            "whether": "0",
//            "special": "0.01",
//            "sort": "0"
    public static String TABLE_NAME = "cart";
    public MySqliteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                              int version) {
        super(context, "garden.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table cart( _id integer primary key autoincrement, " +
                "car_id varchar(20), " +
                "cart_name varchar(20)," +
                "cart_number varchar(20)," +
                "cart_picture varchar(200)," +
                "cart_price varchar(20)," +
                "cart_category varchar(20)," +
                "cart_guige varchar(20)," +
                "cart_special varchar(20)," +
                "cart_sort varchar(20)," +
                "cart_whether varchar(20));";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
