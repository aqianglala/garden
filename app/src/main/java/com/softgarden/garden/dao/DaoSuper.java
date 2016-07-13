package com.softgarden.garden.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.softgarden.garden.dao.annotation.ColmanName;
import com.softgarden.garden.dao.annotation.TableName;
import com.softgarden.garden.dao.annotation.TablePrimaryKey;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class 	DaoSuper<T> implements Dao<T> {
	public MySQLiteOpenHelper sqLiteOpenHelper;
	public SQLiteDatabase db;

	public DaoSuper(Context context) {
		sqLiteOpenHelper = new MySQLiteOpenHelper(context);
		db = sqLiteOpenHelper.getWritableDatabase();
	}

	@Override
	public long insert(T bean) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		ContentValues contentValues = getvalues(bean);

		String tablename = getTablegame();
		return db.insert(tablename, null, contentValues);
	}

	@Override
	public long delete(T bean) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		return db.delete(getTablegame(), TABLE_ID + "=?",
				new String[] { getId(bean) });
	}

	@Override
	public int update(T bean) {
		// TODO Auto-generated method stub
		ContentValues values = getvalues(bean);
		return db.update(getTablegame(), values, TABLE_ID + "=?",
				new String[] { getId(bean) });
	}

	public int updateByProductId(T bean) {
		// TODO Auto-generated method stub
		ContentValues values = getvalues(bean);
		return db.update(getTablegame(), values, "IetmNo =?",
				new String[] { getIetmNo(bean) });
	}

	/**
	 * 查找全部数据
	 */
	@Override
	public List<T> findAll() {
		return query(null, null,null,null,null);
	}

	/**
	 * 获取表名
	 * 
	 * @return
	 */
	public String getTablegame() {
		T t = getInstance();

		TableName tableName = t.getClass().getAnnotation(TableName.class);
		if (tableName != null) {
			return tableName.value();
		}
		return "";
	}

	/**
	 * 把bean数据装入contentValues
	 * 
	 * @return
	 */
	public ContentValues getvalues(T t) {
		Field[] fields = t.getClass().getDeclaredFields();
		ContentValues contentValues = new ContentValues();
		for (Field f : fields) {
			f.setAccessible(true);
			ColmanName colmanName = f.getAnnotation(ColmanName.class);
			if (colmanName != null) {
				try {
					// 不操作_id字段
					TablePrimaryKey tablePrimaryKey = f
							.getAnnotation(TablePrimaryKey.class);
					if (tablePrimaryKey != null
							&& tablePrimaryKey.isautocurment()) {
						continue;
					}
					Object value=f.get(t);
					contentValues.put(colmanName.value(), value==null?"":value.toString());
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		return contentValues;
	}

	/**
	 * 拿到对应的类型的对象
	 * 
	 * @return
	 */
	private T getInstance() {
		// TODO Auto-generated method stub
		Class clazz = getClass();
		// 获取到泛型类型，即BaseDcao
		Type superclass = clazz.getGenericSuperclass();

		// 如果支持泛型
		if (superclass != null && superclass instanceof ParameterizedType) {
			Type[] arTypes = ((ParameterizedType) superclass)
					.getActualTypeArguments();
			try {
				return (T) ((Class) arTypes[0]).newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 获取主键id字段
	 * 
	 * @param t
	 * @return
	 */
	public String getId(T t) {
		Field[] fields = t.getClass().getDeclaredFields();
		ContentValues contentValues = new ContentValues();
		for (Field f : fields) {
			f.setAccessible(true);
			TablePrimaryKey PrimaryKey = f.getAnnotation(TablePrimaryKey.class);
			if (PrimaryKey != null) {

				String id = "";
				try {
					id = f.get(t).toString();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return id;
			}
		}

		return "";
	}

	/**
	 * 获取主键IetmNo字段
	 *
	 * @param t
	 * @return
	 */
	public String getIetmNo(T t) {
		Field[] fields = t.getClass().getDeclaredFields();
		for (Field f : fields) {
			f.setAccessible(true);
			ColmanName colmanname = f.getAnnotation(ColmanName.class);
			if (colmanname != null && colmanname.value().equals("IetmNo")) {

				String IetmNo = "";
				try {
					IetmNo = f.get(t).toString();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return IetmNo;
			}
		}

		return "";
	}

	
	public List<T> query(String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
		List<T> list = null;
		Cursor cursor = db.query(getTablegame(),null, selection, selectionArgs, groupBy, having, orderBy);
		//db.query(getTablegame(), null, null, null, null, null,);
		if (cursor.getCount() > 0) {
			list = new ArrayList<T>();
			while (cursor.moveToNext()) {
				T t = getInstance();

				for (Field f : t.getClass().getDeclaredFields()) {
					f.setAccessible(true);
					// 获取属性名，即字段名
					ColmanName colmanName = f.getAnnotation(ColmanName.class);
					if (colmanName != null) {
						Object value = null;
						// 获取字段类型
						Class fclass = f.getType();
						if (fclass == int.class) {
							value = cursor.getInt(cursor
									.getColumnIndex(colmanName.value()));
						} else if (fclass == String.class) {
							value = cursor.getString(cursor
									.getColumnIndex(colmanName.value()));
						}

						try {
							// 设置属性数据
							f.set(t, value);
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}

				list.add(t);
			}
		}

		return list;
	}
	@Override
	public List<T> queryForWhere(String selection,String[] selectionArgs,String order) {
		// TODO Auto-generated method stub
		
		return query(selection, selectionArgs, null,null,order);
	}

	public List<T> queryForWhere(String selection,String[] selectionArgs) {
		return query(selection, selectionArgs, null,null,null);
	}
		// TODO Auto-generated method stub
}
