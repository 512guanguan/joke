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
//    public static final String SUBWAY_POST_DETAIL = SUBWAY_HOST + "?mod=viewthread&threads=thread&extra=&ordertype=1";//&tid=3737

    /**登录页**/
    public static final String SUBWAY_LOGIN_PAGE = SUBWAY_BASE + "member.php?mod=logging&action=login&mobile=yes";

    /**
     *
     */
    public static final String SUBWAY_SIGN_UP_PAGE = SUBWAY_BASE + "member.php?mod=zhuceditiezu.php";
    /**发帖页**/
    public static final String SUBWAY_EDIT_PAGE = SUBWAY_BASE + "forum.php?mod=post&action=newthread&fid=FID&mobile=yes";//TODO fid

    /**发帖URL**/
    public static final String SUBWAY_SUBMIT_POST = SUBWAY_BASE + "forum.php?mod=post&action=newthread&fid=FID&extra=&topicsubmit=yes";// TODO　fid

    /**帖子详情页**/
    public static final String SUBWAY_POST_DETAIL = SUBWAY_BASE + "forum.php?mod=viewthread&tid=TID&extra=&ordertype=1&threads=thread";// TODO　TID

    /**
     * 已读通知消息
     */
    public static final String SUBWAY_NOTICE_MSG_READ = SUBWAY_BASE + "home.php?mod=space&do=notice&isread=1&mobile=yes";
    /**
     * 未读通知消息
     */
    public static final String SUBWAY_NOTICE_MSG_UNREAD = SUBWAY_BASE + "home.php?mod=space&do=notice&mobile=yes";

}
