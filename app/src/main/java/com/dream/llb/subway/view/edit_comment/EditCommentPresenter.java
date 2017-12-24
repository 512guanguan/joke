package com.dream.llb.subway.view.edit_comment;

import android.util.Log;

import com.dream.llb.subway.model.AccountLoader;
import com.dream.llb.subway.model.SubwayLoader;
import com.dream.llb.subway.model.bean.EditCommentPageResponse;
import com.dream.llb.subway.model.bean.LoginPageResponse;
import com.dream.llb.subway.view.login.LoginContract;

import java.util.HashMap;

/**
 * Created by llb on 2017-09-12.
 */

public class EditCommentPresenter implements EditCommentContract.Presenter {
    private EditCommentContract.View editCommentView;

    public EditCommentPresenter(EditCommentContract.View editCommentView) {
        this.editCommentView = editCommentView;
    }

    @Override
    public void getPageData(String url,String referer) {
        SubwayLoader.getInstance().getEditCommentPage(url,referer)
                .subscribe((EditCommentPageResponse response) -> {
            Log.i("llb", "response = " + response);
                    editCommentView.setPageData(response);
        }, (Throwable e) -> {
            Log.d("llb", "error " + e.getMessage());
        }, () -> {
            Log.d("llb", "completed");
        });
    }

    @Override
    public void getCaptchaImage(String url, String referer) {
        //"http://www.ditiezu.com/forum.php?mod=viewthread&tid=546302&extra=&ordertype=1&threads=thread"
        SubwayLoader.getInstance().getCaptchaImage(url,referer)
                .subscribe((response) -> {
                    Log.i("llb", "存储路径path = " + response);
                    editCommentView.setCaptchaImage(response);
                }, (Throwable e) -> {
                    Log.d("llb", "error " + e.getMessage());
                }, () -> {
                    Log.d("llb", "completed");
                });
    }

}
