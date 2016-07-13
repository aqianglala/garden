package com.softgarden.garden.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * @author Administrator
 *
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

	public static final String DB_NAME = "garden.db";
	private static final String tag ="MySQLiteOpenHelper";


	public MySQLiteOpenHelper(Context context) {
		super(context, DB_NAME, null, 1);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {

		String sql="create table product( _id integer primary key autoincrement, " +
				"itemclassCode varchar(20), " +
				"itemclassName varchar(20)," +
				"Itemgroupcdoe varchar(20)," +
				"ItemGroupName varchar(20)," +
				"IetmNo varchar(20)," +
				"ItemName varchar(20)," +
				"spec varchar(20)," +
				"Unit varchar(20)," +
				"bzj varchar(20)," +
				"picture varchar(200)," +
				"proQty varchar(20)," +
				"Price varchar(20)," +
				"IsSpecial varchar(20)," +
				"tuangou varchar(20)," +
				"shuliang varchar(20)," +
				"returnrate varchar(20));";
		db.execSQL(sql);
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int ordVersion, int newVersion) {

		switch (ordVersion) {
//		case 2:
//			String sql="create table book( _id integer primary key autoincrement,bname varchar(20), bversion integer);";
//			db.execSQL(sql);
//		case 3:
////			String sql="create table person( _id integer primary key autoincrement, name varchar(20),age integer);";
////			db.execSQL(sql);
//			break;

		default:
			break;
		}

	}

}
