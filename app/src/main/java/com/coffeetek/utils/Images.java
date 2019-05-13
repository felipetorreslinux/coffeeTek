package com.coffeetek.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Images {

    public static Bitmap imageUrl(String url){
        byte[] decodedString = Base64.decode(url.split(",")[1], Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}
