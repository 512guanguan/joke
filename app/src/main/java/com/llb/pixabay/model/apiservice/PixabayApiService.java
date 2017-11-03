package com.llb.pixabay.model.apiservice;

import com.llb.pixabay.model.bean.SearchImagesResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Derrick on 2017/7/12.
 */

public interface PixabayApiService {
    @GET("api/")
    Observable<SearchImagesResponse> searchPictures(@QueryMap Map<String, String> requestParams);
}
