package com.dream.llb.subway.model.bean;

import android.text.TextUtils;

import com.dream.llb.subway.model.api.SubwayURL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 论坛首页模块信息解析结果
 * http://www.ditiezu.com/forum.php?mod=forum
 * Created by llb on 2017-09-11.
 */

public class HomePageResponse {
    public AccountInfoUrl accountInfoUrls;
    public ArrayList<ForumInformation> forumInformations;

    public HomePageResponse() {
        accountInfoUrls = new AccountInfoUrl();
        forumInformations = new ArrayList<>();
    }

    public class ForumInformation {
        /**
         * 论坛分区，如管理分区、地铁文化、生活休闲
         **/
        public String forum;
        public String forumUrl;
        public String forumID;//forum.php?gid=2&amp;mod=forum&mobile=yes提取的gid
        public String subjectUrl;
        /**
         * 某个主题模块名，如广州区、地铁心情
         **/
        public String subjectName;
        public String subjectID;//forum.php?mod=forumdisplay&fid=8&mobile=yes提取的fid
        public String subjectNewPost;//近日新帖数
        public String iconUrl;
        public String description;

        public ForumInformation() {
        }

        public ForumInformation(String forum, String forumUrl, String forumID,
                                String subjectUrl, String subjectName, String subjectID,
                                String subjectNewPost, String iconUrl, String description) {
            this.forum = forum;
            this.forumUrl = forumUrl;
            this.forumID = forumID;
            this.subjectUrl = subjectUrl;
            this.subjectName = subjectName;
            this.subjectID = subjectID;
            this.subjectNewPost = subjectNewPost;
            this.iconUrl = iconUrl;
            this.description = description;
        }
    }

    /**
     * 首页能够获取的登录态信息
     */
    public static class AccountInfoUrl {
        /**
         * 消息链接
         **/
        public String messageUrl;
        /**
         * 提醒链接
         **/
        public String noticeUrl;
        /**
         * 提醒条数
         **/
        public String noticeNumber;
        /**
         * 好友链接
         **/
        public String friendsUrl;
        /**
         * 我的帖子链接
         **/
        public String myPostUrl;
        /**
         * 我的收藏链接
         **/
        public String myCollectionUrl;
        /**
         * 个人资料链接
         **/
        public String personInfoUrl;
        /**
         * 退出链接
         **/
        public String logOutUrl;

        /**
         * <li class="notb"><a href="home.php?mod=space&amp;do=pm&mobile=yes">消息</a></li>
         * <li><a href="home.php?mod=space&amp;do=notice&mobile=yes" class="xi1">提醒(2)</a></li>
         * <li><a href="home.php?mod=space&amp;do=friend&mobile=yes">好友</a></li>
         * <li><a href="home.php?mod=space&amp;uid=616077&amp;do=thread&amp;view=me&mobile=yes">我的帖子</a></li>
         * <li><a href="home.php?mod=space&amp;uid=616077&amp;do=favorite&amp;view=me&amp;type=forum&mobile=yes">我的收藏</a></li>
         * <li><a href="home.php?mod=space&amp;uid=616077&amp;do=profile&mobile=yes">个人资料</a></li>
         * <li style=" border-bottom:none;"><a href="member.php?mod=logging&amp;action=logout&amp;formhash=048d4c2d&mobile=yes">退出</a></li>
         */
        public AccountInfoUrl() {
        }

        public AccountInfoUrl(String messageUrl, String noticeUrl, String noticeNumber,
                              String friendsUrl, String myPostUrl, String myCollectionUrl,
                              String personInfoUrl, String logOutUrl) {
            this.messageUrl = messageUrl;
            this.noticeUrl = noticeUrl;
            this.noticeNumber = noticeNumber;
            this.friendsUrl = friendsUrl;
            this.myPostUrl = myPostUrl;
            this.myCollectionUrl = myCollectionUrl;
            this.personInfoUrl = personInfoUrl;
            this.logOutUrl = logOutUrl;
        }
    }

    public class Builder {
        /**
         * 已登录的头部信息，可以解析出登录等信息
         * <header>
         * <a href="forum.php?mobile=yes"><img src="./mplus/img/logo.png" height="45" class="logo_img" /></a>
         * <div class="umus">
         * <a href="javascript:;" onClick="tbox('wrap');"><img src="./mplus/img/h_t.png" height="20" /></a>
         * <a href="search.php?mod=forum&mobile=yes" class="a"><img src="./mplus/img/h_s.png" height="20" /></a>
         * <a href="javascript:;" id="h_umu" class="a" onClick="dbox('h_umu','a');"><img src="./mplus/img/h_u.png" height="20" /></a>
         * <em>N</em>
         * </div>
         * <div class="a_no">
         * <a href="javascript:;" onClick="tbox('wrap');"></a>
         * </div>
         * <ul class="p_umu" id="h_umu_menu" style="display:none;"><em></em>
         * <li class="notb"><a href="home.php?mod=space&amp;do=pm&mobile=yes">消息</a></li>
         * <li><a href="home.php?mod=space&amp;do=notice&mobile=yes" class="xi1">提醒(2)</a></li>
         * <li><a href="home.php?mod=space&amp;do=friend&mobile=yes">好友</a></li>
         * <li><a href="home.php?mod=space&amp;uid=616077&amp;do=thread&amp;view=me&mobile=yes">我的帖子</a></li>
         * <li><a href="home.php?mod=space&amp;uid=616077&amp;do=favorite&amp;view=me&amp;type=forum&mobile=yes">我的收藏</a></li>
         * <li><a href="home.php?mod=space&amp;uid=616077&amp;do=profile&mobile=yes">个人资料</a></li>
         * <li style=" border-bottom:none;"><a href="member.php?mod=logging&amp;action=logout&amp;formhash=048d4c2d&mobile=yes">退出</a></li>
         * </ul>
         * </header>
         * <p>
         * <p>
         * <p>
         * <div class="catlist">
         * <h1><a href="forum.php?gid=2&amp;mod=forum&mobile=yes">都市地铁</a><span id="subfrm_2" onclick="dbox('subfrm_2','o');" class="o"></span></h1>
         * <ul id="subfrm_2_menu" style="display: block">
         * <li>
         * <a href="forum.php?mod=forumdisplay&fid=7&mobile=yes"><img src="data/attachment/common/forum/bj.gif" align="left" alt="" /></a>
         * <a href="forum.php?mod=forumdisplay&amp;fid=7&mobile=yes" class="a">
         * <p class="f_nm">北 京 区</p>
         * <p class="xg1 f_dp">地下通衢肇始燕，铁流暗涌古城垣 ...</p>                    <span>365</span>                    </a>
         * </li>
         * <p>
         * <li>
         * <a href="forum.php?mod=forumdisplay&fid=6&mobile=yes"><img src="data/attachment/common/forum/tj.gif" align="left" alt="" /></a>
         * <a href="forum.php?mod=forumdisplay&amp;fid=6&mobile=yes" class="a">
         * <p class="f_nm">天 津 区</p>
         * <p class="xg1 f_dp">跨越海河津门，连接新区古镇，通 ...</p>                    <span>5</span>                    </a>
         * </li>
         * </ul>
         */
        public HomePageResponse parse(String html) {
            if (TextUtils.isEmpty(html))
                return null;
            HomePageResponse homePageResponse = new HomePageResponse();
            //先解析头部可能存在的登录态信息
            /**
             * 未登录：
             * <header>
             <a href="forum.php?mobile=yes"><img src="./mplus/img/logo.png" height="45" class="logo_img" /></a>
             <div class="umus">
             <a href="javascript:;" onClick="tbox('wrap');"><img src="./mplus/img/h_t.png" height="20" /></a>
             <a href="search.php?mod=forum&mobile=yes" class="a"><img src="./mplus/img/h_s.png" height="20" /></a>
             <a href="member.php?mod=logging&amp;action=login&mobile=yes" class="a"><img src="./mplus/img/h_u.png" height="20" /></a>
             </div>
             <div class="a_no">
             <a href="javascript:;" onClick="tbox('wrap');"></a>
             </div>
             </header>
             * 登录：
             * <header>
             * <a href="forum.php?mobile=yes"><img src="./mplus/img/logo.png" height="45" class="logo_img" /></a>
             * <div class="umus">
             * <a href="javascript:;" onClick="tbox('wrap');"><img src="./mplus/img/h_t.png" height="20" /></a>
             * <a href="search.php?mod=forum&mobile=yes" class="a"><img src="./mplus/img/h_s.png" height="20" /></a>
             * <a href="javascript:;" id="h_umu" class="a" onClick="dbox('h_umu','a');"><img src="./mplus/img/h_u.png" height="20" /></a>
             * <em>N</em>
             * </div>
             * <div class="a_no">
             *      <a href="javascript:;" onClick="tbox('wrap');"></a>
             * </div>
             * <ul class="p_umu" id="h_umu_menu" style="display:none;"><em></em>
             * <li class="notb"><a href="home.php?mod=space&amp;do=pm&mobile=yes">消息</a></li>
             * <li><a href="home.php?mod=space&amp;do=notice&mobile=yes" class="xi1">提醒(2)</a></li>
             * <li><a href="home.php?mod=space&amp;do=friend&mobile=yes">好友</a></li>
             * <li><a href="home.php?mod=space&amp;uid=616077&amp;do=thread&amp;view=me&mobile=yes">我的帖子</a></li>
             * <li><a href="home.php?mod=space&amp;uid=616077&amp;do=favorite&amp;view=me&amp;type=forum&mobile=yes">我的收藏</a></li>
             * <li><a href="home.php?mod=space&amp;uid=616077&amp;do=profile&mobile=yes">个人资料</a></li>
             * <li style=" border-bottom:none;"><a href="member.php?mod=logging&amp;action=logout&amp;formhash=048d4c2d&mobile=yes">退出</a></li>
             * </ul>
             * </header>
             */
            AccountInfoUrl accountInfoUrl = new AccountInfoUrl();
            Document document = Jsoup.parse(html);
            Elements elements = document.select("header ul.p_umu li");
            try {
                if (elements.size() > 0) {
                    accountInfoUrl.messageUrl = elements.get(0).select("a").first().attr("href");
                    accountInfoUrl.noticeUrl = elements.get(1).select("a").first().attr("href");
                    accountInfoUrl.friendsUrl = elements.get(2).select("a").first().attr("href");
                    accountInfoUrl.myPostUrl = elements.get(3).select("a").first().attr("href");
                    accountInfoUrl.myCollectionUrl = elements.get(4).select("a").first().attr("href");
                    accountInfoUrl.personInfoUrl = elements.get(5).select("a").first().attr("href");
                    accountInfoUrl.logOutUrl = elements.get(6).select("a").first().attr("href");
                    String noticeStr = elements.get(1).select("a").first().text();
                    if (noticeStr.indexOf("(") != -1) {
                        accountInfoUrl.noticeNumber = noticeStr.substring(noticeStr.indexOf("("), noticeStr.length() - 1);
                    }
                    homePageResponse.accountInfoUrls = accountInfoUrl;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

//            List<HomePageResponse> CityList =new ArrayList<>();
            String gidReg = "gid=";
            String fidReg = "fid=";
            elements.clear();
            elements = document.select("div.catlist");
            for (int j = 0; j < elements.size(); j++) {
                ForumInformation tempInfo = new ForumInformation();
                Element forumSection = elements.get(j);

                Elements element = forumSection.getElementsByTag("h1");
                Document doc = Jsoup.parse(element.toString());
                element = doc.select("a");
                tempInfo.forum = element.size() > 0 ? element.get(0).text() : "";
                tempInfo.forumUrl = element.size() > 0 ? element.get(0).attr("href") : "";
                String[] arr = tempInfo.forumUrl.split(gidReg);
                tempInfo.forumID = arr.length > 1 ? arr[1].substring(0, arr[1].indexOf("&")) : "";

                element = forumSection.select("li");
                for (int i = 0; i < element.size(); i++) {
                    Elements item = element.get(i).getElementsByTag("a");
                    doc = Jsoup.parse(item.toString());

                    tempInfo.subjectUrl = item.first().attr("href");
                    Matcher mat = Pattern.compile(fidReg).matcher(tempInfo.subjectUrl);
                    if (mat.find()) {
                        String str = tempInfo.subjectUrl.substring(mat.end());
                        tempInfo.subjectID = str.substring(0, str.indexOf("&"));
                    }

                    tempInfo.subjectNewPost = doc.select("span").size() > 0 ? doc.select("span").get(0).text() : "0";
                    ;
                    tempInfo.iconUrl = doc.select("img").size() > 0 ? doc.select("img").get(0).attr("src") : "";
                    tempInfo.subjectName = doc.select("p.f_nm").size() > 0 ? doc.select("p.f_nm").get(0).text() : "";
                    tempInfo.description = doc.select("p.xg1").size() > 0 ? doc.select("p.xg1").get(0).text() : "";
                    homePageResponse.forumInformations.add(new ForumInformation(tempInfo.forum, tempInfo.forumUrl, tempInfo.forumID,
                            tempInfo.subjectUrl, tempInfo.subjectName, tempInfo.subjectID, tempInfo.subjectNewPost, tempInfo.iconUrl,
                            tempInfo.description));
                }
            }
            return homePageResponse;
        }
    }
}
