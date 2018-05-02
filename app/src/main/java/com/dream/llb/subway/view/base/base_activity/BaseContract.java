package com.dream.llb.subway.view.base.base_activity;

import com.dream.llb.subway.model.bean.HomePageResponse;

/**
 * Created by llb on 2017-09-12.
 */

public interface BaseContract {
    interface Presenter {
        void logOut(String url, String referer);
    }

    interface View {
        void onLogOutSuccess();
//        void onLoginFailed(LoginResponse response);
    }
}
