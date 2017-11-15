package com.llb.subway.model.bean;

import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * 分论坛列表页解析结果，如广州区
 * http://www.ditiezu.com/forum.php?mod=forumdisplay&fid=7&mobile=yes
 * Created by llb on 2017-09-11.
 */

public class PostDetailResponse {
    public String postTitle;//帖子名称
    public String author;//楼主
    public String authorInfoUrl;//楼主信息页
    public String postTime;//发帖时间
    public String postContent;//帖子内容
    public String correctUrl;//
    public String commentNum;//总评论数
    public String changeOrderUrl;//评论顺序or逆序显示切换
    public ArrayList<CommentInformation> commentList;

    public PostDetailResponse() {
        this.commentList = new ArrayList<>();
    }
    public PostDetailResponse(ArrayList<CommentInformation> commentList) {
        this.commentList = commentList;
    }

    public class CommentInformation {
        public String floor;//楼层
        public String commentContent;//评论内容
        public String author;//评论者
        public String authorInfoUrl;//评论者信息页
        public String headShotUrl;//头像图片
        public String commentTime;//评论时间
        public String quoteOrigin;//引用处的作者
        public String quoteContent;//引用内容
    }

    public class Builder{
        /**
         * <div class="ct">
         <div id="post_8132669" class="vb notb">
         <h1 class="vt_th">
            <a href="forum.php?mod=viewthread&amp;tid=530533&amp;extra=&mobile=yes">上海磁浮线430秒的体验</a>
         </h1>
         <div class="user_first">
             <a href="home.php?mod=spacecp&amp;ac=favorite&amp;type=thread&amp;id=530533&mobile=yes" class="fav">收藏</a>
             <a href="home.php?mod=space&amp;uid=1254&amp;do=profile&mobile=yes">ppy</a>
             发表于
             <span class="p_dl"> <em id="authorposton8132669">2017-08-27 08:55</em> </span>
         </div>

         <div class="vbc">
         <div class="pbody">
         <div class="mes">
         <div id="postmessage_8132669" class="postmessage"><br />
         有关上海磁浮线，本人在“轨交篇（十）：上海磁浮线”中已经有详细的叙述，这里不再介绍。<br />
         这是链接。<br />
         <a href="http://www.ditiezu.com/thread-211638-1-1.html" target="_blank">http://www.ditiezu.com/thread-211638-1-1.html</a><br />
         <br />
         上海磁浮线是一条商业试验线，全长29．863公里，从龙阳路~浦东国际机场，一站直达。<br />
         <br />
         2017年上海地图上的磁浮线，<br />
         </div>
         <div class="warning"> 附件: <em>您需要<a href="member.php?mod=logging&amp;action=login&mobile=yes">登录</a>才可以下载或查看附件。没有帐号？<a href="member.php?mod=joinditiezu.php&mobile=yes" title="注册帐号">注册地铁族</a></em> </div>
         </div>
         </div>
         <!--// postslist end-->
         </div>
         </div>
         </div>
         */
        public PostDetailResponse parsePage(String html){
            if(TextUtils.isEmpty(html))
                return null;
            PostDetailResponse postDetailResponse = new PostDetailResponse();
            Document document= Jsoup.parse(html);
//            //解析帖子信息
            Elements elements = document.select("div.ct div[class=vb notb]");//帖子
            Document doc= Jsoup.parse(elements.toString());
            elements.clear();
            elements = doc.select("h1.vt_th a");
            if(elements.size()>0){
                postDetailResponse.postTitle = elements.first().text();
            }
            elements.clear();
            elements = doc.select("div.user_first");
            if(elements.size()>0){
                Elements es = elements.select("a");
                if(es.size()>1){
                    postDetailResponse.correctUrl = es.first().attr("href");
                    postDetailResponse.author = es.get(1).text();
                    postDetailResponse.authorInfoUrl = es.get(1).attr("href");
                }
                es.clear();
                es = elements.select("span.p_dl");
                postDetailResponse.postTime = es.size() > 0 ? es.first().text() : "";
            }

            elements.clear();
            elements = doc.select("div.vbc div.pbody div.mes div.postmessage");
            if(elements.size()>0){

                postDetailResponse.postContent = elements.toString();//elements.first().text();
            }

            postDetailResponse.changeOrderUrl = document.select("div.titls a.xi2").attr("href");

            //TODO 解析帖子评论列表信息
            elements.clear();
            elements = document.select("div[class=vb vc]");
            for(int j = 0;j<elements.size();j++){
                CommentInformation comment = new CommentInformation();
                comment.headShotUrl = elements.get(j).select("a.avatar img").attr("src");
                comment.author = elements.get(j).select("div.user a").text();
                comment.authorInfoUrl = elements.get(j).select("div.user a").attr("href");
                comment.floor = elements.get(j).select("div.user span[class=p_nm y]").text();
                comment.commentTime = elements.get(j).select("div.user span.p_dl").text();
                comment.commentContent = elements.get(j).select("div.pbody div.mes div.postmessage").text();
                //解析引用内容
                if(elements.get(j).select("div.quote").size()>0){
                    comment.quoteContent = elements.get(j).select("div.quote").text();//TODO 不对，做了登陆态再来
                    comment.quoteOrigin = elements.get(j).select("div.quote font font").text();
                }
                postDetailResponse.commentList.add(comment);
            }
            return postDetailResponse;
        }
    }
}
