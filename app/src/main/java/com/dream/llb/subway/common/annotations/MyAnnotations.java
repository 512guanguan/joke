package com.dream.llb.subway.common.annotations;

/**
 * Created by llb on 2018-09-09.
 */
//先定义 常量

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * java的枚举在android中使用会带来dex文件变大、内存占用大等弊端，
 * 用注解来避免
 */
public class MyAnnotations {
    public static final int ASC = 2; //升序
    public static final int DESC = 1; //降序

    //用@IntDef "包住" 常量；
    //@Retention 定义策略
    //声明构造器
    @IntDef({ASC, DESC})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CommentOrders {
    }
}
