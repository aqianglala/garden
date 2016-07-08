package com.softgarden.garden.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by qiang-pc on 2016/7/8.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    private static  MySQLiteOpenHelper helper;

    //构造器,传入四个参数Context对象，数据库名字name，操作数据库的Cursor对象，版本号version。
    private MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //为了简化构造器的使用，我们自定义一个构造器
    private MySQLiteOpenHelper(Context context, String name) {
        this(context, name, null, 1);//传入Context和数据库的名称，调用上面那个构造器
    }
    //将自定义的数据库创建类单例。
    public static  synchronized  MySQLiteOpenHelper getInstance(Context context) {
        if(helper==null){
            helper = new MySQLiteOpenHelper(context, TableConfig.DB_NAME);//数据库名称为create_db。
        }
        return  helper;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建数据库表
        sqLiteDatabase.execSQL("create table if not exists "+TableConfig.TABLE_NAME+"( " +
                " _id integer primary key autoincrement, "
                +TableConfig.Product.PRODUCT_ID+" verchar(20),"
                +TableConfig.Product.PRODUCT_NAME+ " verchar(20),"
                +TableConfig.Product.PRODUCT_NUMBER+ " verchar(20),"
                +TableConfig.Product.PRODUCT_PICTURE+ " verchar(200) ,"
                +TableConfig.Product.PRODUCT_PRICE+ " verchar(20),"
                +TableConfig.Product.PRODUCT_CATEGORY+ " verchar(20), "
                +TableConfig.Product.PRODUCT_GUIGE+ " verchar(20),"
                +TableConfig.Product.PRODUCT_SPECIAL+ " verchar(20),"
                +TableConfig.Product.PRODUCT_SORT+ " verchar(20),"
                +TableConfig.Product.PRODUCT_WHETHER+ " verchar(20),"
                +TableConfig.Product.PRODUCT_TUANGOU+ " verchar(20),"
                +TableConfig.Product.PRODUCT_YUGU+ " verchar(20))");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //用于升级数据库，只需要在创建本类对象时传入一个比之前创建传入的version大的数即可。
    }
}
