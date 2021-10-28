package com.kiba.framework.comm.utility;

import com.kiba.framework.comm.callback.ICallback_Comm;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/*
 HttpHelper.get(GetHttpUrl_Comm("LoginCommand","{}"),con->{ });
 */
public class HttpHelper {
    public static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时时间
            .readTimeout(20, TimeUnit.SECONDS)//设置读取超时时间
            .build();


    public static void get(String url, ICallback_Comm callback) throws IOException {
        new Thread() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    String result = response.body().string();
                    callback.Call(result);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    try {
                        callback.Call(e.getMessage());
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                }
            }
        }.start();

    }

    //public static final MediaType JSON  = MediaType.get("application/json; charset=utf-8");
    public static final MediaType mediaType = MediaType.get("application/octet-stream; charset=utf-8");

    public static void post(String url, String param, ICallback_Comm callback) throws IOException {
        new Thread() {
            @Override
            public void run() {
                RequestBody body = RequestBody.create(mediaType, param);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    String result = response.body().string();
                    callback.Call(result);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    try {
                        callback.Call(e.getMessage());
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }

                }
            }
        }.start();

    }
}
