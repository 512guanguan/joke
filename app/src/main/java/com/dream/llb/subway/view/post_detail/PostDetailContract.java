package com.dream.llb.subway.view.post_detail;

import com.dream.llb.subway.model.bean.BaseResponse;
import com.dream.llb.subway.model.bean.EditCommentPageResponse;
import com.dream.llb.subway.model.bean.PostDetailResponse;

/**
 * Created by llb on 2017-09-12.
 */

public interface PostDetailContract {
    interface Presenter{
        void postComment(PostDetailResponse response,String commentMsg,String captcha);

        void postReplyComment(EditCommentPageResponse response,String commentMsg,String captcha,String referer);

        void getReplyPageData(String url,String referer);
        void getCaptchaImage(String url,String referer);

        /**
         * 下拉刷新页面
         */
        void refreshPage(String url);
        /**
         * 上拉加载更多
         */
        void loadMoreData(String url,int currentPage);
    }

    interface View{
        void setReplyPageData(EditCommentPageResponse response);

//        void setPostDetailData(PostDetailResponse response);
        void hideProgressDialog();
        void showProgressDialog();
        /**
         * 上拉刷新结束
         */
        void onFinishLoadMore(PostDetailResponse response);
        /**
         * 下拉刷新结束
         */
        void onFinishRefresh(PostDetailResponse response);
        /**
         * 评论提交成功
         */
        void onPostCommentFinished(BaseResponse response);

        void setCaptchaImage(String response);
    }
}
