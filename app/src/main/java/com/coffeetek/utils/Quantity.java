package com.coffeetek.utils;

import android.widget.TextView;

import com.coffeetek.R;

public class Quantity {

    public static String qtdMl(int size){
        if(size >= 1) return String.valueOf(size * 100).concat(" ML");
        else return String.valueOf(100).concat(" ML");
    }
}
