package com.coffeetek.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.coffeetek.R;

public class ShareUtils  {

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public ShareUtils(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(context.getString(R.string.share_cart), Context.MODE_PRIVATE);
        this.editor = context.getSharedPreferences(context.getString(R.string.share_cart), Context.MODE_PRIVATE).edit();
    }

    public boolean isViewCart() {
        return sharedPreferences.getBoolean("viewCart", false);
    }

    public void setViewCart(boolean viewCart) {
        editor.putBoolean("viewCart", viewCart);
        editor.commit();
    }
}
