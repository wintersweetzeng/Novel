package com.example.winter.myapplication.utils;

/**
 * Created by liudashuang on 2017/12/2.
 */

public interface HttpCallbackListener {

    void onFinish(String response);

    void onError(Exception e);

}
