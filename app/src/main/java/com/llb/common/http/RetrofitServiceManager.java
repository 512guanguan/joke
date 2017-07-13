package com.llb.common.http;

import android.util.Log;

import com.llb.config.Config;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Derrick on 2017/6/19.
 */

public class RetrofitServiceManager {
    private static RetrofitServiceManager INSTANCE = null;
    private Retrofit mRetrofit = null;

    private OkHttpClient getOkHttpClient() {
        //日志显示级别BASIC、NONE、HEADERS、BODY
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BASIC;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("zcb", "OkHttp====Message:" + message);
            }
        });
        loggingInterceptor.setLevel(level);

        //定制OkHttp
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //OkHttp进行添加拦截器loggingInterceptor
        builder.addInterceptor(loggingInterceptor);
        builder.connectTimeout(Config.DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
        builder.writeTimeout(Config.DEFAULT_TIME_OUT, TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(Config.DEFAULT_TIME_OUT, TimeUnit.SECONDS);//读操作超时时间
        return builder.build();
    }

    private RetrofitServiceManager() {
//        // 添加公共参数拦截器
//        HttpCommonInterceptor commonInterceptor = new HttpCommonInterceptor.Builder()
//                .addHeaderParams("paltform","android")
//                .addHeaderParams("userToken","1234343434dfdfd3434")
//                .addHeaderParams("userId","123445")
//                .build();
//        builder.addInterceptor(commonInterceptor);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Config.CURRENT_BASE_URL)
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitServiceManager getRetrofitInstance() {
        if (INSTANCE == null) {
            synchronized (RetrofitServiceManager.class) {
                if (INSTANCE == null) {
                    RetrofitServiceManager.INSTANCE = new RetrofitServiceManager();
                }
            }
        }
        return INSTANCE;
    }

    public static void clearInstance() {
        INSTANCE = null;
    }

    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }
}
