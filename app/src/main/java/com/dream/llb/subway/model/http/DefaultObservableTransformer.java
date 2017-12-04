package com.dream.llb.subway.model.http;/**
 * Created by Derrick on 2017/7/18.
 */

import android.util.Log;
import android.widget.Toast;

import com.dream.llb.subway.view.base.BaseApplication;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * 利用RxJava的compose操作符对所有接口请求进行统一的Transformer设置
 * Created by Derrick on 2017/7/18
 **/
public class DefaultObservableTransformer {
    public static <T> ObservableTransformer<T, T> defaultTransformer() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                // 统一判断错误码
                .doOnError((error) -> { // 网络出错时
                    Log.i("llb", "doOnError!!");
                    Toast.makeText(BaseApplication.mContext,"心碎了，网络失败了！",Toast.LENGTH_SHORT).show();;
//                    ErrorHandler.handlerNetworkError(error); TODO
                });
    }
}