package com.evan.holder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static DBHelper dbHelper;

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static SQLiteDatabase getInstance(Context context) {
        if (dbHelper == null) {
            // 指定数据库名为student，需修改时在此修改；此处使用默认工厂；指定版本为1
            dbHelper = new DBHelper(context, "test_db", null, 1);
        }
        return dbHelper.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists tableSave (_id integer primary key autoincrement, Name text, ServoPos1 integer, ServoPos2 integer)";
        String sql2 = "create table if not exists tableTrans (_id integer primary key autoincrement, Name text, ServoPos1 integer, ServoPos2 integer)";
        db.execSQL(sql);
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public void addData(SQLiteDatabase db,int x, int y) {

    }
}
