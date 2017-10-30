package com.llb.subway.model.api;

import okhttp3.OkHttpClient;

public final class HttpUtil {

    public static final String TAG="HttpUtil";
    private OkHttpClient client;
    private static HttpUtil instance;


//    private CookiesManager cookiesManager;


//    public CookiesManager getCookiesManager() {
//        return cookiesManager;
//    }

    private HttpUtil(){

//        cookiesManager=new CookiesManager(AppApplication.context);
//        Log.d("NetActivityXXX","UTIL THRAD"+Thread.currentThread().getName());
        client=new OkHttpClient.Builder()

                .build();
    }

    public static HttpUtil getInstance(){
        if(instance==null){
            instance=new HttpUtil();
        }
        return  instance;
    }

    public  OkHttpClient.Builder newBuilder(){
        return client.newBuilder();
    }

    public GetRequest get(String url){
        return new GetRequest(url,client);
    }


    public PostRequest post(String url){

        //Log.d("NetActivityXXX","POST THREAD"+Thread.currentThread().getName());
        return new PostRequest(url,client);}
}