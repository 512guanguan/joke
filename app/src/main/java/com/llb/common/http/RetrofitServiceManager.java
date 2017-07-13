package com.llb.common.http;

import com.llb.config.Config;

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
    private Retrofit mRetrofit = null;

    private RetrofitServiceManager() {
        // 创建 OKHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(Config.DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
        builder.writeTimeout(Config.DEFAULT_TIME_OUT, TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(Config.DEFAULT_TIME_OUT, TimeUnit.SECONDS);//读操作超时时间

//        // 添加公共参数拦截器
//        HttpCommonInterceptor commonInterceptor = new HttpCommonInterceptor.Builder()
//                .addHeaderParams("paltform","android")
//                .addHeaderParams("userToken","1234343434dfdfd3434")
//                .addHeaderParams("userId","123445")
//                .build();
//        builder.addInterceptor(commonInterceptor);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Config.CURRENT_BASE_URL)
                .client(builder.build())
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

    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }
}
