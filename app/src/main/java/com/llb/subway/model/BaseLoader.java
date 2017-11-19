package com.llb.subway.model;


import android.util.Log;

import com.llb.subway.model.api.GetRequest;
import com.llb.subway.model.api.HttpUtil;
import com.llb.subway.model.bean.ForumListItem;
import com.llb.subway.model.bean.PostDetailResponse;
import com.llb.subway.model.bean.PostListItem;
import com.llb.subway.model.http.DefaultObservableTransformer;
import com.llb.subway.view.base.BaseActivity;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;

/**
 * Created by llb on 2017-09-06.
 */

public class BaseLoader {
    /**
     * 通用的网络请求
     *
     * @param url
     * @return
     */
    protected Observable<String> requestByGet(String url) {
        return Observable.just("")
                .observeOn(Schedulers.io())
                .map((s) -> {
                            try {
                                GetRequest request = HttpUtil.getInstance().get(url);
                                request.addHeader("Referer","http://www.ditiezu.com/forum.php?mod=forum");
                                Response res = HttpUtil.addCommonHeaders(request).execute();
                                InputStream inputStream = new ByteArrayInputStream(res.body().bytes());
                                return zipInputStream(inputStream);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return "";
                        }
                );
//                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 处理gzip,deflate返回流
     *
     * @param is
     * @return
     * @throws IOException
     */
    protected String zipInputStream(InputStream is) throws IOException {
        GZIPInputStream gzip = new GZIPInputStream(is);
        BufferedReader in = new BufferedReader(new InputStreamReader(gzip, "utf-8"));
        StringBuffer buffer = new StringBuffer();
        String line;
        while ((line = in.readLine()) != null)
            buffer.append(line + "\n");
        is.close();
        return buffer.toString();
    }
}


