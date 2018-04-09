package com.dream.llb.subway.view.edit_post;

import android.text.TextUtils;
import android.util.Log;

import com.dream.llb.subway.model.AccountLoader;
import com.dream.llb.subway.model.SubwayLoader;
import com.dream.llb.subway.model.bean.EditPostPageResponse;
import com.dream.llb.subway.model.bean.PostDetailResponse;

import java.util.HashMap;

/**
 * Created by llb on 2017-09-12.
 */

public class EditPostPresenter implements EditPostContract.Presenter {
    private EditPostContract.View editPostView;

    public EditPostPresenter(EditPostContract.View editPostView) {
        this.editPostView = editPostView;
    }

    @Override
    public void getPageData(String url, String referer) {
        SubwayLoader.getInstance().getEditPostPage(url, referer)
                .subscribe((EditPostPageResponse response) -> {
                    Log.i("llb", "response = " + response);
                    editPostView.setPageData(response);
                }, (Throwable e) -> {
                    Log.d("llb", "error " + e.getMessage());
                }, () -> {
                    Log.d("llb", "completed");
                });
    }

    @Override
    public void getCaptchaImage(String url, String referer) {
        //"http://www.ditiezu.com/forum.php?mod=viewthread&tid=546302&extra=&ordertype=1&threads=thread"
        SubwayLoader.getInstance().getCaptchaImage(url, referer)
                .subscribe((response) -> {
                    Log.i("llb", "存储路径path = " + response);
                    editPostView.setCaptchaImage(response);
                }, (Throwable e) -> {
                    Log.d("llb", "error " + e.getMessage());
                }, () -> {
                    Log.d("llb", "completed");
                });
    }

    @Override
    public void submitPostData(String url, String referer, EditPostPageResponse response, String content,
                               String title, String typeID, String captcha) {
        HashMap<String, String> data = new HashMap<>();
        /**
         * 发贴：
         * formhash:048d4c2d
         posttime:1523027265
         wysiwyg:0
         typeid:22
         subject:我胡汉三又回来了
         message:大学毕业离开帝都就一直没回来过，这次哥们结婚再回来坐上熟悉的5号线，感觉恍如一场梦呀
         usesig:1
         allownoticeauthor:1
         */
        data.put("formhash", response.formhash);
        data.put("posttime", response.posttime);
        data.put("wysiwyg", response.wysiwyg);
        data.put("typeid", typeID);
        data.put("subject", title);
        data.put("message", content);
        data.put("usesig", response.usesig);
        data.put("allownoticeauthor", response.allownoticeauthor);
        //新用户需要二维码发帖
        if (!TextUtils.isEmpty(captcha)) {
            data.put("sechash", response.sechash);
            data.put("seccodeverify", captcha);
        }
        AccountLoader.getInstance().submitPost(url, data, referer)
                .subscribe((res) -> {
                    Log.i("llb", "发帖成功了！！");
                    editPostView.onSubmitPostSuccess((PostDetailResponse) res);
                }, (Throwable e) -> {
                    Log.d("llb", "error " + e.getMessage());
                }, () -> {
                    Log.d("llb", "completed");
                });
    }

}
