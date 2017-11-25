package com.llb.subway.model.http.picasso;

import com.llb.subway.model.api.SubwayURL;

import java.io.IOException;
import java.io.SequenceInputStream;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by llb on 2017-11-20.
 */

public class CommonHeaderInterceptor implements Interceptor{
    public CommonHeaderInterceptor(){
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder  request = chain.request().newBuilder();
        request.header("Referer", SubwayURL.SUBWAY_LOGIN_PAGE)
                .header("Accept-Encoding","gzip,deflate")
                .header("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8")
                .header("Accept","image/webp,image/apng,image/*,*/*;q=0.8")
                .header("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1");
        return chain.proceed(request.build());
    }
}
