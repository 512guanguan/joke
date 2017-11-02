package com.llb.subway.view;

import android.util.Log;

import com.llb.subway.model.SubwayLoader;
import com.llb.subway.model.bean.CityListItem;

/**
 * Created by llb on 2017-09-12.
 */

public class SubwayFirstPresenter implements SubwayFirstContract.Presenter {
    @Override
    public void getPostListData() {
        SubwayLoader.getInstance().getPostListData().subscribe((String response) -> {
            Log.i("llb", "response = " + response);
            CityListItem.Builder.parse(response);
        }, (Throwable e) -> {
            Log.d("llb", "error " + e.getMessage());
        }, () -> {
            Log.d("llb", "completed");
        });
    }
}
