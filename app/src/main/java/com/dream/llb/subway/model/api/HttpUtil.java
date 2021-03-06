package com.dream.llb.subway.model.api;

import android.text.TextUtils;

import com.dream.llb.config.Config;
import com.dream.llb.subway.BuildConfig;
import com.dream.llb.subway.model.http.MyHttpLoggingInterceptor;
import com.dream.llb.subway.model.http.MyHttpLoggingInterceptor.Level;
import com.dream.llb.subway.model.http.cookie.CookiesManager;
import com.dream.llb.subway.view.base.BaseApplication;

import net.nightwhistler.htmlspanner.TextUtil;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public final class HttpUtil {

    public static final String TAG="HttpUtil";
    private OkHttpClient client;
    private static HttpUtil instance;


    private CookiesManager cookiesManager;
//
//
    public CookiesManager getCookiesManager() {
        return cookiesManager;
    }

    private HttpUtil(){
        MyHttpLoggingInterceptor logInterceptor = new MyHttpLoggingInterceptor();
        logInterceptor.setLevel(Level.BODY);
        cookiesManager=new CookiesManager(BaseApplication.mContext);
        // 创建 OKHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(Config.DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
        builder.writeTimeout(Config.DEFAULT_TIME_OUT, TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(Config.DEFAULT_TIME_OUT, TimeUnit.SECONDS);//读操作超时时间
        if (BuildConfig.DEBUG) {//如果是测试状态，才打印信息
            builder.addInterceptor(logInterceptor);
        }
        builder.cookieJar(cookiesManager);
        client = builder.build();
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

    /**
     * 添加地铁族必须的Get Header参数
     * @param request
     * @return
     */
    public static IRequest addCommonHeaders(IRequest request){
          return request.addHeader("Accept-Encoding","gzip,deflate")
                .addHeader("Accept-Language","zh-CN,zh;q=0.8,en;q=0.6")
                .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addHeader("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1");
//                    .addParameter("username", userName)
    }


    /**
     * 封装post表单数据
     * @param url
     * @param data  HashMap<String, String>格式的数据
     * @return
     */
    public PostRequest post(String url, HashMap<String, String> data, String referer){
        PostRequest request = new PostRequest(url, client);
        for(String item : data.keySet()){
            request.addParameter(item,data.get(item));
        }
        if(!TextUtils.isEmpty(referer)){
            request.addHeader("referer",referer);
        }
        return request;
    }
}