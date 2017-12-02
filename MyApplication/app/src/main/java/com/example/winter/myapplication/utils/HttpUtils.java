package com.example.winter.myapplication.utils;

import android.util.Log;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by liudashuang on 2017/12/2.
 */

public class HttpUtils {

    private static final String TAG = "HttpUtils";
    public static final MediaType MEDIA_TYPE_JSON
            = MediaType.parse("application/json; charset=utf-8");
    private static final String CommonUrl = "http://127.0.0.1:5000/LoveNovel/";

    /**
     * 不阻塞主线程okhttp实现
     * @param address
     * @param callback
     */
    public static void sendOkHttpPost(String address,final RequestBody requestBody, okhttp3.Callback callback) {
        Log.e(TAG, address);
        OkHttpClient client = new OkHttpClient();
        Request requst = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(requst).enqueue(callback);
    }

    /**
     * 不阻塞
     * @param address
     * @param listener
     */
    public static void sendHttpRequest (final String address, final HttpCallbackListener listener) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try{
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/json");
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuffer response = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    if (listener != null){
                        listener.onFinish(response.toString());
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                    e.printStackTrace();
                } finally {
                    if (connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();

    }

    public static void sendHttpPost (final String address, final String obj, final HttpCallbackListener listener) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try{
                    Log.e(TAG, address + obj);
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/json");
                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                    Log.e(TAG, obj);
                    out.writeBytes(obj);
                    out.flush();
                    out.close();
                    if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        throw new IOException(connection.getResponseMessage() +
                                ": with " +
                                address);
                    }

                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuffer response = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    if (listener != null){
                        listener.onFinish(response.toString());
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                    e.printStackTrace();
                } finally {
                    if (connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();

    }

    public String getUrlBytes(String urlSpec, String obj) throws Exception {

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }).start();
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(false);
        connection.setDoInput(true);
        //设置连接超时时间和读取超时时间
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.connect();
        try {
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
//            String json = java.net.URLEncoder.encode(array.toString(), "utf-8");
            Log.e(TAG, obj);
            out.writeBytes(obj);
            out.flush();
            out.close();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = URLDecoder.decode(lines, "utf-8");
                sb.append(lines);
            }
            System.out.println(sb);
            reader.close();
            return sb.toString();
        } finally {
            connection.disconnect();
        }
    }


    /**
     * 不阻塞主线程okhttp实现
     * @param address
     * @param callback
     */
    public static void sendOkHttpRequst(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request requst = new Request.Builder()
                .url(address)
                .build();
        client.newCall(requst).enqueue(callback);

    }


    /**
     * 阻塞主线程
     * @param url
     */
    public void sendRequestWithOkHttp(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(url).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                }   catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendPostWithOkHttp(final String url, final RequestBody requestBody) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                }   catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
