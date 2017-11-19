package com.llb.subway.common.htmlspanner.handlers;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.provider.Browser;
import android.text.ParcelableSpan;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.llb.subway.view.base.BaseActivity;

/**
 * Created by llb on 2017-11-18.
 */

public class MyURLSpan extends ClickableSpan implements ParcelableSpan {
    private final String mURL;

    public MyURLSpan(String url) {
        mURL = url;
    }

    public MyURLSpan(Parcel src) {
        mURL = src.readString();
    }

    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    /** @hide */
    public int getSpanTypeIdInternal() {
        return 0;//TextUtils.URL_SPAN;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        writeToParcelInternal(dest, flags);
    }

    /** @hide */
    public void writeToParcelInternal(Parcel dest, int flags) {
        dest.writeString(mURL);
    }

    public String getURL() {
        return mURL;
    }

    @Override
    public void onClick(View widget) {
//        Uri uri = Uri.parse(getURL());
//        Context context = widget.getContext();
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        intent.putExtra(Browser.EXTRA_APPLICATION_ID, context.getPackageName());
//        try {
//            context.startActivity(intent);
//        } catch (ActivityNotFoundException e) {
//            Log.w("URLSpan", "Actvity was not found for intent, " + intent.toString());
//        }
        //TODO 跳转到webView页面
        Toast.makeText(widget.getContext(), "准备跳去"+mURL, Toast.LENGTH_SHORT).show();
    }
}
