package com.dream.llb.subway.view.post_detail;

import android.text.TextUtils;
import android.util.Log;

import com.dream.llb.subway.model.AccountLoader;
import com.dream.llb.subway.model.SubwayLoader;
import com.dream.llb.subway.model.bean.EditCommentPageResponse;
import com.dream.llb.subway.model.bean.PostDetailResponse;

import java.util.HashMap;

import io.reactivex.Observable;

/**
 * Created by llb on 2017-09-12.
 */

public class PostDetailPresenter implements PostDetailContract.Presenter {
    private PostDetailContract.View postDetailView;

    public PostDetailPresenter(PostDetailContract.View postDetailView) {
        this.postDetailView = postDetailView;
    }

    @Override
    public void refreshPage(String url) {
        postDetailView.showProgressDialog();
        SubwayLoader.getInstance().getPostDetailData(url)
                .subscribe((PostDetailResponse response) -> {
//                    Log.i("llb", "response = " + response);
                    postDetailView.hideProgressDialog();
                    postDetailView.onFinishRefresh(response);
                }, (Throwable e) -> {
                    postDetailView.hideProgressDialog();
//                    Log.d("llb", "error " + e.getMessage());
                }, () -> {
                    postDetailView.hideProgressDialog();
//                    Log.d("llb", "completed");
                });
    }

    @Override
    public void loadMoreData(String url,int currentPage) {
        currentPage++;//X-Requested-With:XMLHttpRequest
        //forum.php?mod=viewthread&tid=539523&extra=&ordertype=1&threads=thread&mobile=yes&page=2
        url += "&extra=&ordertype=1&threads=thread";
        SubwayLoader.getInstance().getMoreCommentData(url + "&page="+currentPage, url)
                .subscribe((PostDetailResponse response) -> {
//                    Log.i("llb", "response = " + response);
                    postDetailView.onFinishLoadMore(response);
                }, (Throwable e) -> {
                    postDetailView.onFinishLoadMore(null);
//                    Log.d("llb", "error " + e.getMessage());
                }, () -> {
//                    Log.d("llb", "completed");
                });
    }

    @Override
    public void postComment(PostDetailResponse postDetailResponse, String commentMsg, String captcha) {
        HashMap<String,String> data = new HashMap<>();
        /**
         * 回复主贴：
         * formhash	04fee59b
         message	厉害了我的哥，可以从这个角度拍
         replysubmit	回复
         */
        data.put("formhash",postDetailResponse.replyFormHash);
        data.put("message",commentMsg);
        data.put("replysubmit",postDetailResponse.replysubmit);
        if(!TextUtils.isEmpty(captcha)) {
            data.put("sechash",postDetailResponse.sechash_CAPTCHA);
            data.put("seccodeverify",captcha);
        }
        AccountLoader.getInstance().postComment(postDetailResponse.replyCommentUrl,data,postDetailResponse.currentPageUrl)
                .subscribe((response) -> {
//                    Log.i("llb", "评论完了？？");
                    postDetailView.onPostCommentFinished(response);
                }, (Throwable e) -> {
//                    Log.d("llb", "error " + e.getMessage());
                }, () -> {
//                    Log.d("llb", "completed");
                });
    }

    @Override
    public void postReplyComment(EditCommentPageResponse editCommentPageResponse, String commentMsg, String captcha,String referer) {
        HashMap<String,String> data = new HashMap<>();
        /**
         * 回复主贴：
         * formhash	04fee59b
         * posttime 1412122
         * wysiwyg 0
         * noticeauthor
         * noticetrimstr
         * noticeauthormsg
         * reppid
         * reppost
         * subject
         * checkbox
         * message	厉害了我的哥，可以从这个角度拍
         * save
         * usesig 1
         *
         *
         *
         * 回复评论：
         * formhash	9685fbc5
         posttime	1516108130
         wysiwyg	0
         noticeauthor	6e3372BGE1WVarguZSkznHtlfsoSDzCXPLmYsiJ1J27G8DAO
         noticetrimstr
         noticeauthormsg	1、目前丰台站已经开工，普高结合没有疑义了，北京西站的普速将迁往丰台站。
         reppid	8475242
         reppost	8475242
         subject
         message	没那么快吧，没听到多少靠谱的
         sechash	SYfO7
         seccodeverify	4pwj
         replysubmit	true
         usesig	1
         */
        data.put("formhash",editCommentPageResponse.formhash);
        data.put("posttime",editCommentPageResponse.posttime);
        data.put("wysiwyg",editCommentPageResponse.wysiwyg);
        data.put("noticeauthor",editCommentPageResponse.noticeauthor);
        data.put("noticetrimstr",editCommentPageResponse.noticetrimstr);
        data.put("noticeauthormsg",editCommentPageResponse.noticeauthormsg);
        data.put("reppid",editCommentPageResponse.reppid);
        data.put("reppost",editCommentPageResponse.reppost);
        data.put("subject",editCommentPageResponse.subject);
        data.put("message",commentMsg);
        data.put("usesig",editCommentPageResponse.usesig);
        data.put("replysubmit",editCommentPageResponse.replysubmit);
        if(!TextUtils.isEmpty(captcha)) {
            data.put("sechash",editCommentPageResponse.sechash);
            data.put("seccodeverify",captcha);
        }
        AccountLoader.getInstance().postComment(editCommentPageResponse.replyUrl,data,referer)
                .subscribe((response) -> {
//                    Log.i("llb", "评论完了？？");
                    postDetailView.onPostCommentFinished(response);
                }, (Throwable e) -> {
//                    Log.d("llb", "error " + e.getMessage());
                }, () -> {
//                    Log.d("llb", "completed");
                });
    }

    @Override
    public void getCaptchaImage(String url,String referer) {
        SubwayLoader.getInstance().getCaptchaImage(url,referer)
                .subscribe((response) -> {
//                    Log.i("llb", "存储路径path = " + response);
                    postDetailView.setCaptchaImage(response);
                }, (Throwable e) -> {
//                    Log.d("llb", "error " + e.getMessage());
                }, () -> {
//                    Log.d("llb", "completed");
                });
    }

    @Override
    public void getReplyPageData(String url,String referer) {
        SubwayLoader.getInstance().getEditCommentPage(url,referer)
                .subscribe((EditCommentPageResponse response) -> {
//                    Log.i("llb", "response = " + response);
                    postDetailView.setReplyPageData(response);
                }, (Throwable e) -> {
//                    Log.d("llb", "error " + e.getMessage());
                }, () -> {
//                    Log.d("llb", "completed");
                });
    }
}
