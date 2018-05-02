package com.dream.llb.subway.view.notice_msg;

import android.util.Log;

import com.dream.llb.subway.model.SubwayLoader;
import com.dream.llb.subway.model.bean.NoticeMsgResponse;

import io.reactivex.Observable;

/**
 * Created by llb on 2017-09-12.
 */

public class NoticeMsgPresenter implements NoticeMsgContract.Presenter {
    private NoticeMsgContract.View noticeView;

    public NoticeMsgPresenter(NoticeMsgContract.View noticeView) {
        this.noticeView = noticeView;
    }

    @Override
    public void getNoticeData(String readUrl, String unreadUrl) {
        NoticeMsgResponse unreadResponse = new NoticeMsgResponse();
        SubwayLoader.getInstance().getNoticeListData(unreadUrl)
                .flatMap(unreadRes -> {
                    // 这里拿到的是未读新提醒，再次请求时消息就会出现在下一个已读结果里
                    return SubwayLoader.getInstance().getNoticeListData(readUrl);
                })
                .subscribe(response -> {
                    noticeView.setNoticeData(response);
                }, (Throwable e) -> {
                    e.getMessage();
                }, () -> {
                });
    }
}
