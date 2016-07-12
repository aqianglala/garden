package com.softgarden.garden.dao;

import java.util.List;

public interface Dao<T>{
	String TABLE_ID="_id";
	long insert(T bean);
	long delete(T bean);
	int update(T bean);
	List<T> findAll();
	List<T> queryForWhere(String selection, String[] selectionArgs, String orderBy);
	List<T>  query(String selection, String[] selectionArgs, String groupBy, String having, String
			orderBy);
	List<T> queryForWhere(String selection, String[] selectionArgs);
}
