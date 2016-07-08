package com.softgarden.garden.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.softgarden.garden.utils.UIUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by qiang-pc on 2016/7/8.
 */
public class TableOperate {
    private final MySQLiteOpenHelper dbHelper;

    public TableOperate() {
        dbHelper = MySQLiteOpenHelper.getInstance(UIUtils.getContext());
    }

    /**
     * 查询数据库的名，数据库的添加
     *
     * @param tableName  查询的数据库的名字
     * @param entityType 查询的数据库所对应的module
     * @param fieldName  查询的字段名
     * @param value      查询的字段值
     * @param <T>        泛型代表AttendInformation，Customer，Order，User，WorkDaily类
     * @return 返回查询结果，结果为AttendInformation，Customer，Order，User，WorkDaily对象
     */
    public <T> ArrayList<T> query(String tableName, Class<T> entityType, String fieldName, String value) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<T> list = new ArrayList();
        Cursor cursor = db.query(tableName, null, fieldName + " = ?", new String[]{value}, null,
                null, " _id desc", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            try {
                T t = entityType.newInstance();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    String content = cursor.getString(i);//获得获取的数据记录第i条字段的内容
                    String columnName = cursor.getColumnName(i);// 获取数据记录第i条字段名的
                    Field field = entityType.getDeclaredField(columnName);//获取该字段名的Field对象。
                    field.setAccessible(true);//取消对age属性的修饰符的检查访问，以便为属性赋值
                    field.set(t, content);
                    field.setAccessible(false);//恢复对age属性的修饰符的检查访问
                }
                list.add(t);
                cursor.moveToNext();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        close(db,cursor);
        return list;
    }

    /**
     * 查询数据库的名，数据库的添加
     *
     * @param tableName  查询的数据库的名字
     * @param entityType 查询的数据库所对应的module
     * @param <T>        泛型代表AttendInformation，Customer，Order，User，WorkDaily类
     * @return 返回查询结果，结果为AttendInformation，Customer，Order，User，WorkDaily对象
     */
    public <T> ArrayList<T> queryAll(String tableName, Class<T> entityType) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<T> list = new ArrayList();
        Cursor cursor = db.query(tableName, null, null, null, null,
                null, " _id desc", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            try {
                T t = entityType.newInstance();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    String content = cursor.getString(i);//获得获取的数据记录第i条字段的内容
                    String columnName = cursor.getColumnName(i);// 获取数据记录第i条字段名的
                    Field field = entityType.getDeclaredField(columnName);//获取该字段名的Field对象。
                    field.setAccessible(true);//取消对age属性的修饰符的检查访问，以便为属性赋值
                    field.set(t, content);
                    field.setAccessible(false);//恢复对age属性的修饰符的检查访问
                }
                list.add(t);
                cursor.moveToNext();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        close(db,cursor);
        return list;
    }

    /**
     * 向数据库插入数据
     *
     * @param tableName 数据库插入数据的数据表
     * @param object    数据库插入的对象
     */
    public void insert(String tableName, Object object) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();//获取该类所有的属性
        ContentValues value = new ContentValues();

        for (Field field : fields) {
            try {
                field.setAccessible(true); //取消对age属性的修饰符的检查访问，以便为属性赋值
                String content = (String) field.get(object);//获取该属性的内容
                value.put(field.getName(), content);
                field.setAccessible(false);//恢复对age属性的修饰符的检查访问
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        db.insert(tableName, null, value);
        close(db,null);
    }

    /**
     * 删除数据
     *
     * @param tableName 删除数据库的表名
     * @param fieldName 删除的字段名
     * @param value     删除的字段的值
     */
    public void delete(String tableName, String fieldName, String value) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(tableName, fieldName + "=?", new String[]{value});
        close(db,null);
    }


    /**
     * 更改数据库内容
     *
     * @param tableName   更改数据的数据表
     * @param columnName  更改的数据的字段名
     * @param columnValue 更改的数据的字段值
     * @param object      更改的数据
     */
    public void uptate(String tableName, String columnName, String columnValue, Object object) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            Class clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();//获取该类所有的属性
            ContentValues value = new ContentValues();
            for (Field field : fields) {
                if (!field.getName().equals("_id")) {
                    field.setAccessible(true); //取消对age属性的修饰符的检查访问，以便为属性赋值
                    String content = (String) field.get(object);//获取该属性的内容
                    value.put(field.getName(), content);
                    field.setAccessible(false);//恢复对age属性的修饰符的检查访问
                }
            }
            db.update(tableName, value, columnName+ "=?", new String[]{columnValue});
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }finally {
            close(db,null);
        }
    }

    public void close(SQLiteDatabase db, Cursor cursor){
        if(cursor !=null) cursor.close();
        if (db!=null) db.close();
    }
}
