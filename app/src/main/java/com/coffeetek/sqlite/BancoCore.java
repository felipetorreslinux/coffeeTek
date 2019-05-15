package com.coffeetek.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoCore extends SQLiteOpenHelper {

    public String table_cart = "cart";

    public BancoCore(Context context){
        super(context, "banco.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        TableCart(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table "+table_cart);
        onCreate(db);
    }

    public void TableCart(SQLiteDatabase database){
        database.execSQL("create table "+table_cart+" (" +
                "_id integer primary key," +
                "title text," +
                "image text," +
                "size integer," +
                "sugar integer," +
                "additional text," +
                "qtd integer)");
    }
}
