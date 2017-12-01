package com.example.winter.myapplication;

import android.app.Application;
import android.content.Context;

/**
 * Created by liudashuang on 2017/11/30.
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
