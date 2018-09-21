package com.dream.llb.subway.common.htmlspanner.handlers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;

import com.dream.llb.subway.common.annotations.MyAnnotations;
import com.dream.llb.subway.common.util.SharedPreferencesUtil;
import com.dream.llb.subway.model.api.SubwayURL;
import com.dream.llb.subway.view.post_detail.PostDetailActivity;
import com.dream.llb.subway.view.show_image.ShowImageActivity;
import com.dream.llb.subway.view.webpage.WebPageActivity;

/**
 * Created by llb on 2017-11-18.
 */

@SuppressLint("ParcelCreator")
public class MyURLSpan extends ClickableSpan implements ParcelableSpan {
    private String mURL;

    public MyURLSpan(String url) {
        mURL = url;
    }

    public MyURLSpan(Parcel src) {
        mURL = src.readString();
    }

    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    /**
     * @hide
     */
    public int getSpanTypeIdInternal() {
        return 0;//TextUtils.URL_SPAN;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        writeToParcelInternal(dest, flags);
    }

    /**
     * @hide
     */
    public void writeToParcelInternal(Parcel dest, int flags) {
        dest.writeString(mURL);
    }

    public String getURL() {
        return mURL;
    }

    @Override
    public void onClick(View widget) {
//        Uri uri = Uri.parse(getURL());
        Context context = widget.getContext();
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        intent.putExtra(Browser.EXTRA_APPLICATION_ID, context.getPackageName());
        if (!mURL.startsWith("http")) {//如forum.php/..
            mURL = SubwayURL.SUBWAY_BASE + mURL;
        }
        Intent intent;
        if (mURL.endsWith(".jpg") || mURL.endsWith(".png") || mURL.endsWith(".jpeg") || mURL.endsWith(".bmp") || mURL.endsWith(".gif")) {
            intent = new Intent(context, ShowImageActivity.class);
            intent.putExtra("url", mURL);
        } else if (mURL.endsWith(".pdf") || mURL.endsWith(".doc")) {
            Toast.makeText(context, "暂不支持该格式文件", Toast.LENGTH_SHORT).show();
            return;
        } else if (mURL.indexOf("http://www.ditiezu.com/thread-") == -1 && mURL.indexOf("https://www.ditiezu.com/thread-") == -1) {
            intent = new Intent(context, WebPageActivity.class);
            intent.putExtra("url", mURL);
        } else {
            //论坛其他帖子链接=》转换成手机版链接
            //http://www.ditiezu.com/thread-192706-1-1.html
            //http://www.ditiezu.com/forum.php?mod=viewthread&tid=192706&extra=page=1&ordertype=1&threads=thread
            mURL = mURL.replace("http://www.ditiezu.com/thread-", "http://www.ditiezu.com/forum.php?mod=viewthread&tid=");
            int currentOrder = (int) SharedPreferencesUtil.get(context, SharedPreferencesUtil.SETTING_ORDER, MyAnnotations.DESC);
            mURL = mURL.substring(0, mURL.indexOf("-")) + "&extra=page=1&ordertype=" + currentOrder + "&threads=thread";
            intent = new Intent(context, PostDetailActivity.class);
            intent.putExtra("url", mURL);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }

        context.startActivity(intent);
        //TODO 跳转到webView页面
//        Toast.makeText(widget.getContext(), mURL, Toast.LENGTH_SHORT).show();
    }
}
