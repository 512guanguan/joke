package com.llb.joke.model.joke.service;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Derrick on 2017/6/19.
 */

public class RetrofitServiceManager {
    private static RetrofitServiceManager INSTANCE = null;
    private static String BASE_JOKE_URL = "";
    private static long DEFAULT_TIME_OUT = 10000;
    private Retrofit mRetrofit = null;

    private RetrofitServiceManager() {
        // 创建 OKHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
        builder.writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//读操作超时时间

//        // 添加公共参数拦截器
//        HttpCommonInterceptor commonInterceptor = new HttpCommonInterceptor.Builder()
//                .addHeaderParams("paltform","android")
//                .addHeaderParams("userToken","1234343434dfdfd3434")
//                .addHeaderParams("userId","123445")
//                .build();
//        builder.addInterceptor(commonInterceptor);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_JOKE_URL)
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public RetrofitServiceManager getRetrofitInstance() {
        if (INSTANCE == null) {
            synchronized (RetrofitServiceManager.class) {
                if (INSTANCE == null) {
                    RetrofitServiceManager.INSTANCE = new RetrofitServiceManager();
                }
            }
        }
        return INSTANCE;
    }

    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }
}
