package com.llb.joke.model.apiservice;

import com.llb.joke.model.bean.JokeResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Derrick on 2017/6/26.
 */

public interface JokeApiService {
    @GET("content/text.from")
//    Call<JokeResponse> getLatestJoke(@QueryMap Map<String, String> requestParams);
    Observable<JokeResponse> getLatestJoke(@QueryMap Map<String, String> requestParams);

    @GET("content/list.from") //按更新时间查询笑话
    Observable<JokeResponse> getJokeByUpdateTime(@QueryMap Map<String, String> requestParams);
}
