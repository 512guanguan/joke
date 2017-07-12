package com.llb.pixabay.model;

import com.llb.common.utils.ApiUtils;
import com.llb.pixabay.model.apiservice.PixabayApiService;
import com.llb.pixabay.model.bean.SearchImagesRequest;
import com.llb.pixabay.model.bean.SearchImagesResponse;

import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Derrick on 2017/7/12.
 */

public class PixabayLoader {
    PixabayApiService pixabayApiService = null;

    public Observable<SearchImagesResponse> searchImage(SearchImagesRequest request) {
        Map<String, String> requestParams = ApiUtils.parseRequestParams(request);
        return pixabayApiService.searchPictures(requestParams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .map((SearchImagesResponse searchImageResponse) -> {
                    System.out.print(searchImageResponse);
                    return searchImageResponse;
                });
    }
}
