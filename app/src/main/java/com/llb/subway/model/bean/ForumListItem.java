package com.llb.subway.model.bean;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 论坛首页模块信息解析结果
 * http://www.ditiezu.com/forum.php?mod=forum
 * Created by llb on 2017-09-11.
 */

public class ForumListItem {
    public ArrayList<ForumInformation> forumInformations;
//    /**论坛分区，如管理分区、地铁文化、生活休闲**/
//    public String forum;
//    public String forumUrl;
//    public String forumID;//forum.php?gid=2&amp;mod=forum&mobile=yes提取的gid
//    public String subjectUrl;
//    /**某个主题模块名，如广州区、地铁心情**/
//    public String subjectName;
//    public String subjectID;//forum.php?mod=forumdisplay&fid=8&mobile=yes提取的fid
//    public String subjectNewPost;//近日新帖数
//    public String iconUrl;
//    public String description;

    public ForumListItem(){
        forumInformations = new ArrayList<>();
    }

//    public ForumListItem(String forum, String forumUrl, String forumID, String subjectUrl, String subjectName, String subjectID, String subjectNewPost, String iconUrl, String description) {
//        this.forum = forum;
//        this.forumUrl = forumUrl;
//        this.forumID = forumID;
//        this.subjectUrl = subjectUrl;
//        this.subjectName = subjectName;
//        this.subjectID = subjectID;
//        this.subjectNewPost = subjectNewPost;
//        this.iconUrl = iconUrl;
//        this.description = description;
//    }

    public class ForumInformation{
        /**论坛分区，如管理分区、地铁文化、生活休闲**/
        public String forum;
        public String forumUrl;
        public String forumID;//forum.php?gid=2&amp;mod=forum&mobile=yes提取的gid
        public String subjectUrl;
        /**某个主题模块名，如广州区、地铁心情**/
        public String subjectName;
        public String subjectID;//forum.php?mod=forumdisplay&fid=8&mobile=yes提取的fid
        public String subjectNewPost;//近日新帖数
        public String iconUrl;
        public String description;
    }

    public class Builder{
        /**
         * <div class="catlist">
             <h1><a href="forum.php?gid=2&amp;mod=forum&mobile=yes">都市地铁</a><span id="subfrm_2" onclick="dbox('subfrm_2','o');" class="o"></span></h1>
             <ul id="subfrm_2_menu" style="display: block">
             <li>
                <a href="forum.php?mod=forumdisplay&fid=7&mobile=yes"><img src="data/attachment/common/forum/bj.gif" align="left" alt="" /></a>
                <a href="forum.php?mod=forumdisplay&amp;fid=7&mobile=yes" class="a">
                <p class="f_nm">北 京 区</p>
                <p class="xg1 f_dp">地下通衢肇始燕，铁流暗涌古城垣 ...</p>                    <span>365</span>                    </a>
             </li>

             <li>
                 <a href="forum.php?mod=forumdisplay&fid=6&mobile=yes"><img src="data/attachment/common/forum/tj.gif" align="left" alt="" /></a>
                 <a href="forum.php?mod=forumdisplay&amp;fid=6&mobile=yes" class="a">
                 <p class="f_nm">天 津 区</p>
                 <p class="xg1 f_dp">跨越海河津门，连接新区古镇，通 ...</p>                    <span>5</span>                    </a>
             </li>
         </ul>
         */
        public List<ForumListItem> parse(String html){
            if(html==null)
                return null;
            ForumListItem forumListItem = new ForumListItem();
//            List<ForumListItem> CityList =new ArrayList<>();
            String gidReg = "gid=";
            String fidReg = "fid=";
            Document document= Jsoup.parse(html);
            Elements elements = document.select("div.catlist");
            for(int j = 0;j<elements.size();j++){
                ForumInformation forumInformation = new ForumInformation();
                Element forumSection = elements.get(j);

                Elements element = forumSection.getElementsByTag("h1");
                Document doc = Jsoup.parse(element.toString());
                element = doc.select("a");
                forumInformation.forum = element.size() > 0 ? element.get(0).text() : "";
                forumInformation.forumUrl = element.size()> 0 ? element.get(0).attr("href"): "";
                String[] arr = forumInformation.forumUrl.split(gidReg);
                forumInformation.forumID = arr.length>1? arr[1].substring(0,arr[1].indexOf("&")): "";

                element =forumSection.select("li");
                for(int i=0;i<element.size();i++){
                    Elements item=element.get(i).getElementsByTag("a");
                    doc = Jsoup.parse(item.toString());

                    forumInformation.subjectUrl = item.first().attr("href");
                    Matcher mat = Pattern.compile(fidReg).matcher(forumInformation.subjectUrl);
                    if(mat.find()){
                        String str = forumInformation.subjectUrl.substring(mat.end());
                        forumInformation.subjectID = str.substring(0, str.indexOf("&"));
                    }

                    forumInformation.subjectNewPost = doc.select("span").size()>0 ? doc.select("span").get(0).text() : "0";;

                    String iconUrl = doc.select("img").size()>0 ? doc.select("img").get(0).attr("src") : "";
                    String subjectName = doc.select("p.f_nm").size()>0 ? doc.select("p.f_nm").get(0).text() : "";
                    String description = doc.select("p.xg1").size()>0 ? doc.select("p.xg1").get(0).text() : "";
                    CityList.add(new ForumListItem(forum, forumUrl, forumID,subjectUrl,subjectName, subjectID,subjectNewPost,iconUrl,description));
                }
            }
            return CityList;
        }
    }
}
