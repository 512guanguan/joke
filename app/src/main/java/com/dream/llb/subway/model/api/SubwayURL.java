package com.dream.llb.subway.model.api;

/**
 * Created by llb on 2017-09-06.
 */

public class SubwayURL {
    public static final String SUBWAY_BASE = "http://www.ditiezu.com/";
    public static final String SUBWAY_HOST = "http://www.ditiezu.com/forum.php";

    /**论坛首页信息列表**/
    public static final String SUBWAY_HOME = SUBWAY_HOST + "?mod=forum";

    /**某城市不分类信息列表**/
    public static final String SUBWAY_CITY_HOME = SUBWAY_HOST + "?mod=forumdisplay&mobile=yes";//&fid=7 北京

    /**某城市按某分类的信息列表**/
    public static final String SUBWAY_CITY_CATEGORY_HOME = SUBWAY_HOST + "?mod=forumdisplay&mobile=yes&filter=typeid";//&fid=77&typeid=621厦门2号线

    /**某帖子的具体信息**/
    public static final String SUBWAY_POST_DETAIL = SUBWAY_HOST + "?mod=viewthread&threads=thread&extra=&ordertype=1";//&tid=3737

    /**登录页**/
    public static final String SUBWAY_LOGIN_PAGE = SUBWAY_BASE + "member.php?mod=logging&action=login&mobile=yes";
}
