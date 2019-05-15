package com.coffeetek.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.coffeetek.models.ProductsModel;

import java.util.ArrayList;
import java.util.List;

public class SQLiCart {

    Context context;
    BancoCore bancoCore;
    SQLiteDatabase database;

    public SQLiCart(Context context) {
        this.context = context;
        this.bancoCore = new BancoCore(context);
        this.database = bancoCore.getWritableDatabase();
    }

    public int add(ProductsModel productsModel){
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", productsModel.getTitle());
        contentValues.put("image", productsModel.getImage());
        contentValues.put("size", productsModel.getSize());
        contentValues.put("sugar", productsModel.getSugar());
        contentValues.put("additional", productsModel.getAdditional());
        contentValues.put("qtd", productsModel.getQuantity());
        Cursor cursor = database.rawQuery("select * from "+bancoCore.table_cart+" where title = ?", new String[]{productsModel.getTitle()});
        if(cursor != null){
            try{
                if(cursor.getCount() > 0){
                    cursor.moveToFirst();
                    int qtd = (cursor.getInt(cursor.getColumnIndex("qtd")) + productsModel.getQuantity());
                    contentValues.put("qtd", qtd != 0 ? qtd : 1);
                    database.update(bancoCore.table_cart, contentValues, "title = ?", new String[]{productsModel.getTitle()});
                    return 2;
                }else{
                    database.insert(bancoCore.table_cart, null, contentValues);
                    return 1;
                }
            }finally {
                cursor.close();
            }
        }
        return 0;
    }

    public List<ProductsModel> list(){
        List<ProductsModel> list = new ArrayList<>();
        list.clear();
        Cursor cursor = database.rawQuery("select * from "+ bancoCore.table_cart, null);
        if(cursor != null){
            try{
                if(cursor.getCount() > 0){
                    cursor.moveToFirst();
                    do {
                        ProductsModel productsModel = new ProductsModel();
                        productsModel.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                        productsModel.setImage(cursor.getString(cursor.getColumnIndex("image")));
                        productsModel.setSize(cursor.getInt(cursor.getColumnIndex("size")));
                        productsModel.setSugar(cursor.getInt(cursor.getColumnIndex("sugar")));
                        productsModel.setAdditional(cursor.getString(cursor.getColumnIndex("additional")));
                        productsModel.setQuantity(cursor.getInt(cursor.getColumnIndex("qtd")));
                        list.add(productsModel);
                    }while (cursor.moveToNext());
                    return list;
                }
                return list;
            }finally {
                cursor.close();
            }
        }
        return list;
    }

    public boolean remove(ProductsModel productsModel){
        Cursor cursor = database.rawQuery("select * from "+bancoCore.table_cart+" where title = ?", new String[]{productsModel.getTitle()});
        if(cursor != null){
            try{
                if(cursor.getCount() > 0){
                    database.delete(bancoCore.table_cart, "title = ?", new String[]{productsModel.getTitle()});
                    return true;
                }
                return false;
            }finally {
                cursor.close();
            }
        }
        return false;
    }

    public int count(){
        Cursor cursor = database.rawQuery("select * from " + bancoCore.table_cart, null);
        if(cursor != null){
            try{
                if(cursor.getCount() > 0){
                    cursor.moveToFirst();
                    return cursor.getCount();
                }
                return 0;
            }finally {
                cursor.close();
            }
        }
        return 0;
    }
}
