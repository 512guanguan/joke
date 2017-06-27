package com.llb.joke.model.api_service;

import com.llb.joke.model.bean.joke.JokeResponse;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

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
