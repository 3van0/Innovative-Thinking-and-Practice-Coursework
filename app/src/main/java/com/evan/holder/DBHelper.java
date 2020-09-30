package com.evan.holder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
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
