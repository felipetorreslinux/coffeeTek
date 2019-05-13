package com.coffeetek;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

public class Central extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(this);
    }
}
