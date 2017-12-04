package com.dream.llb.pixabay.model;

import com.dream.llb.common.http.RetrofitServiceManager;
import com.dream.llb.config.Config;
import com.dream.llb.pixabay.model.apiservice.PixabayApiService;
import com.dream.llb.pixabay.model.bean.SearchImagesRequest;
import com.dream.llb.common.utils.ApiUtils;
import com.dream.llb.pixabay.model.bean.SearchImagesResponse;

import java.util.Map;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
/**
 * Created by Derrick on 2017/7/12.
 */

public class PixabayLoader {
    PixabayApiService pixabayApiService = null;

    public PixabayLoader(){
        Config.CURRENT_BASE_URL = Config.BASE_PIXABAY_URL;
        pixabayApiService = RetrofitServiceManager.getRetrofitInstance().create(PixabayApiService.class);
    }

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
