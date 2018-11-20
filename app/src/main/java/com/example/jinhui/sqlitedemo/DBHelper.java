package com.example.jinhui.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Email: 1004260403@qq.com
 * Created by jinhui on 2018/11/20.
 */
public class DBHelper extends SQLiteOpenHelper {

    // 数据库名称
    private static final String DB_NAME = "contacts.db";
    public static final String TABLE_NAME = "info";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_PHONE = "phone";

    //声明SQLite对象
//    private SQLiteDatabase db;

    private static final String DATABASE_CREATE = "create table " + TABLE_NAME
            + " (id integer primary key autoincrement, " + FIELD_NAME
            + " char(10), " + FIELD_PHONE + " char(11));";

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        this.db = db;
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // 查询
    public Cursor query() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_NAME, null, null,
                null, null, null, null);
        return cursor;

    }

    // 删除
    public void delete(String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DBHelper.TABLE_NAME, "id = ?", new String[]{id});
        Log.d("Test", "成功删除一条记录");
    }

    // 更新
    public void update(ContentValues values, String args) {
        SQLiteDatabase db = getWritableDatabase();
        db.update(DBHelper.TABLE_NAME, values, "id = ?", new String[]{args});
    }

    // 关闭数据库
    public void close() {
//        if (db != null)
//            db.close();
        SQLiteDatabase db = getWritableDatabase();
        db.close();
    }

    // 插入数据
    public void insert(ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        db.insert(DBHelper.TABLE_NAME, null, values);
        db.close();
    }
}
