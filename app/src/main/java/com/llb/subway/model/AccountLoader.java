package com.llb.subway.model;


import android.util.Log;

import com.llb.subway.model.bean.ForumListItem;
import com.llb.subway.model.bean.LoginPageResponse;
import com.llb.subway.model.bean.PostDetailResponse;
import com.llb.subway.model.bean.PostListItem;
import com.llb.subway.model.http.DefaultObservableTransformer;
import com.llb.subway.view.base.BaseActivity;

import io.reactivex.Observable;

/**
 * 目前打算把所有登录态的请求放这里
 * Created by llb on 2017-09-06.
 */

public class AccountLoader extends BaseLoader {
    private static AccountLoader instance;

    private AccountLoader() {
        super();
    }

    public static AccountLoader getInstance() {
        return instance == null ? new AccountLoader() : instance;
    }

}


