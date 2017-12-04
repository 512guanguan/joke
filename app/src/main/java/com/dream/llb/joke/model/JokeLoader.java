package com.dream.llb.joke.model;

import com.dream.llb.common.http.RetrofitServiceManager;
import com.dream.llb.config.Config;
import com.dream.llb.joke.model.bean.GetLatestJokeRequest;
import com.dream.llb.common.utils.ApiUtils;
import com.dream.llb.joke.model.apiservice.JokeApiService;
import com.dream.llb.joke.model.bean.JokeResponse;

import java.util.Map;

import retrofit2.http.QueryMap;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
/**
 * Created by Derrick on 2017/6/19.
 */

public class JokeLoader {
    JokeApiService jokeApiService = null;

    public JokeLoader() {
        Config.CURRENT_BASE_URL = Config.BASE_JOKE_URL;
        jokeApiService = RetrofitServiceManager.getRetrofitInstance().create(JokeApiService.class);
    }

    public Observable<JokeResponse> getLatestJoke(GetLatestJokeRequest getLatestJokeRequest) {
        Map<String, String> requestParams = ApiUtils.parseRequestParams(getLatestJokeRequest);
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
