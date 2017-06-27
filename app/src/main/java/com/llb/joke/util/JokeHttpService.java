package com.llb.joke.util;

import com.llb.joke.model.bean.joke.JokeResponse;
import com.llb.joke.model.http.RetrofitServiceManager;
import com.llb.joke.model.api_service.JokeApiService;

import java.util.Map;

import retrofit2.http.QueryMap;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Derrick on 2017/6/19.
 */

public class JokeHttpService {
    JokeApiService jokeApiService = null;

    public JokeHttpService() {
        jokeApiService = RetrofitServiceManager.getRetrofitInstance().create(JokeApiService.class);
    }

    public Observable<JokeResponse> getLatestJoke(@QueryMap Map<String, String> requestParams) {
        return jokeApiService.getLatestJoke(requestParams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .map((JokeResponse jokeResponse) -> {
                    System.out.print(jokeResponse);
                    return jokeResponse;
                });
    }

    public Observable<JokeResponse> getJokeByUpdateTime(@QueryMap Map<String, String> requestParams) {
        return jokeApiService.getJokeByUpdateTime(requestParams)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map((response) -> {
                    System.out.print(response);
                    return response;
                });
    }
}
