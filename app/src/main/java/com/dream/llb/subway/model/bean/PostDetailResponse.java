package com.dream.llb.subway.model.bean;

import android.text.TextUtils;
import android.util.Log;

import com.dream.llb.subway.model.api.SubwayURL;
import com.dream.llb.subway.view.base.BaseApplication;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * 分论坛列表页解析结果，如广州区
 * http://www.ditiezu.com/forum.php?mod=forumdisplay&fid=7&mobile=yes
 * Created by llb on 2017-09-11.
 */

public class PostDetailResponse extends BaseResponse {
    public String subjectID;//所属的论坛forum id
    //    public String newPostURL;
    public String pageWarning;//例如权限不够之类的报错信息，弹框提醒后退出
    public String currentPageUrl;//当前帖子链接，存着备用
    public String postTitle;//帖子名称
    public String author;//楼主
    public String authorInfoUrl;//楼主信息页
    public String postTime;//发帖时间
    public String postContent;//帖子内容
    public String collectUrl;//收藏链接
    public String commentNum;//总评论数
    public String changeOrderUrl;//评论顺序or逆序显示切换
    public ArrayList<CommentInformation> commentList;

    //回复模块
    public String replyCommentUrl;//回复主贴的链接
    public String replysubmit;//貌似都是传“回复”
    public String CAPTCHA_URL;//可能有，回复主贴需要的验证码图片链接
    public String replyFormHash;//回复必须的
    public String sechash_CAPTCHA;//可能有，回复主贴需要的验证码sechash

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
        public String replyUrl;//点击回复
    }

    public class Builder {
        /**
         * <div class="vt">
         * <div class="pt">
         * <a href="forum.php?mobile=yes">首页</a>
         * <em> &gt; </em> <a href="forum.php?mod=forumdisplay&amp;fid=23&mobile=yes">广 州 区</a>
         * <a href="forum.php?mod=post&action=newthread&fid=23&mobile=yes" class="y v_pst" >发帖</a>
         * </div>
         * <p>
         * <p>
         * 有些时候权限不够之类的会报错
         * <div class="showmg">
         * <div class="warning" id="messagetext">
         * <p class="mbn">抱歉，本帖要求阅读权限高于 20 才能浏览</p>
         * <p><a href="http://www.ditiezu.com/./?mobile=yes">点击此链接进行跳转</a></p>
         * </div>
         * </div>
         * </div></div>
         * <p>
         * <div class="ct">
         * <div id="post_8132669" class="vb notb">
         * <h1 class="vt_th">
         * <a href="forum.php?mod=viewthread&amp;tid=530533&amp;extra=&mobile=yes">上海磁浮线430秒的体验</a>
         * </h1>
         * <div class="user_first">
         * <a href="home.php?mod=spacecp&amp;ac=favorite&amp;type=thread&amp;id=530533&mobile=yes" class="fav">收藏</a>
         * <a href="home.php?mod=space&amp;uid=1254&amp;do=profile&mobile=yes">ppy</a>
         * 发表于
         * <span class="p_dl"> <em id="authorposton8132669">2017-08-27 08:55</em> </span>
         * </div>
         * <p>
         * <div class="vbc">
         * <div class="pbody">
         * <div class="mes">
         * <div id="postmessage_8132669" class="postmessage"><br />
         * 有关上海磁浮线，本人在“轨交篇（十）：上海磁浮线”中已经有详细的叙述，这里不再介绍。<br />
         * 这是链接。<br />
         * <a href="http://www.ditiezu.com/thread-211638-1-1.html" target="_blank">http://www.ditiezu.com/thread-211638-1-1.html</a><br />
         * <br />
         * 上海磁浮线是一条商业试验线，全长29．863公里，从龙阳路~浦东国际机场，一站直达。<br />
         * <br />
         * 2017年上海地图上的磁浮线，<br />
         * </div>
         * <div class="warning"> 附件: <em>您需要<a href="member.php?mod=logging&amp;action=login&mobile=yes">登录</a>才可以下载或查看附件。没有帐号？<a href="member.php?mod=joinditiezu.php&mobile=yes" title="注册帐号">注册地铁族</a></em> </div>
         * </div>
         * </div>
         * <!--// postslist end-->
         * </div>
         * </div>
         * </div>
         * <p>
         * <div id="post_8280490" class="vb vc">
         * <a href="home.php?mod=space&amp;uid=15329&amp;do=profile&mobile=yes" class="avatar"><img src="http://www.ditiezu.com/uc_server/data/avatar/000/01/53/29_avatar_small.jpg" onerror="this.onerror=null;this.src='http://www.ditiezu.com/uc_server/images/noavatar_small.gif'" /></a>
         * <div class="user">
         * <a href="home.php?mod=space&amp;uid=15329&amp;do=profile&mobile=yes">jason130</a>
         * <span class="p_dl">
         * <em id="authorposton8280490">11-13 13:14</em>
         * </span>
         * <span class="p_nm y">楼主</span>
         * </div>
         * <div class="vbc notb">
         * <div class="pbody mbn">
         * <div class="mes">
         * <div id="postmessage_8280490" class="postmessage">
         * <div class="quote">
         * <blockquote><font size="2"><font color="#999999">comet 发表于 2017-11-13 07:51</font> <a href="http://www.ditiezu.com/forum.php?mod=redirect&amp;goto=findpost&amp;pid=8279577&amp;ptid=476334" target="_blank"><a href="static/image/common/back.gif" target="_blank">[查看图片]</a></a></font><br />
         * 感觉这两年全国有轨电车建设的很迅速，像南京的麒麟有轨电车，北京的西郊有轨电车等等，都是今年通车，可否 ...</blockquote>
         * </div><br />
         * 前年已经做过有轨电车专辑台历了了<img src="static/image/smiley/default/48.gif" smilieid="80" border="0" alt="" /> </div>
         * </div>
         * </div>
         * <div class="vtrim">
         * <a href="forum.php?mod=post&amp;action=reply&amp;fid=21&amp;tid=476334&amp;repquote=8280490&amp;page=1&mobile=yes">回复</a>
         * <span>
         * </div>
         * <!--// postslist end-->
         * </div>
         * </div>
         * <p>
         * <p>
         * //需要验证码的主贴回复模块
         * <div class="ct ctpd">
         * <div class="ipc">
         * <form method="post" name="inputform" autocomplete="off" id="fastpostform" action="forum.php?mod=post&amp;action=reply&amp;fid=23&amp;tid=546332&amp;extra=&amp;replysubmit=yes">
         * <input type="hidden" name="formhash" value="55423c47" />
         * <div class="ipcp">
         * <div class="ipcc">
         * <textarea name="message" id="fastpostmessage" cols="25" rows="7"></textarea>
         * </div>
         * </div>
         * <input name="sechash" type="hidden" value="Svn20" />
         * <div class="ips">
         * <table cellspacing="0" cellpadding="0">
         * <tr>
         * <th>
         * <p><input name="seccodeverify" id="seccodeverify_Svn20" type="text" autocomplete="off" /></p>
         * </th>
         * <td>
         * <img src="misc.php?mod=seccode&amp;update=47349&amp;idhash=Svn20" class="scod" />
         * </td>
         * </tr>
         * </table>
         * </div>
         * <div class="inbox"><input type="submit" name="replysubmit" id="fastpostsubmit" value="回复" class="ibt ibtp" /></div>
         * </form>
         * </div>
         * <p>
         * //不需要验证码
         * <div class="ct ctpd">
         * <div class="ipc">
         * <form method="post" name="inputform" autocomplete="off" id="fastpostform" action="forum.php?mod=post&amp;action=reply&amp;fid=7&amp;tid=547858&amp;extra=&amp;replysubmit=yes">
         * <input type="hidden" name="formhash" value="5e4293fb" />
         * <div class="ipcp">
         * <div class="ipcc">
         * <textarea name="message" id="fastpostmessage" cols="25" rows="7"></textarea>
         * </div>
         * </div>
         * <div class="inbox"><input type="submit" name="replysubmit" id="fastpostsubmit" value="回复" class="ibt ibtp" /></div>
         * </form>
         * </div>
         */
        public PostDetailResponse parsePage(String html) {
            if (TextUtils.isEmpty(html))
                return null;
            PostDetailResponse postDetailResponse = new PostDetailResponse();
            Document document = Jsoup.parse(html);
            //解析看有没有报错信息出现
            try {
                //抱歉，本帖要求阅读权限高于 20 才能浏览
                postDetailResponse.pageWarning = document.select("div.showmg div.warning p.mbn").get(0).text();
                return postDetailResponse;
            } catch (Exception e) {
                e.printStackTrace();
//                Log.i("llb", "没有页面报错信息出现");
            }
//          //解析帖子信息
            Elements elements = document.select("div.vt div.pt a");//拿fid
            if (elements.size() > 0) {
//<a href="forum.php?mod=forumdisplay&amp;fid=23&mobile=yes">广 州 区</a>
                String str = elements.get(1).attr("href");
                str = str.substring(str.indexOf("fid="));
                postDetailResponse.subjectID = str.substring(4, str.indexOf("&"));
//                Log.i("llb", "匹配出来的fid = " + postDetailResponse.subjectID);
            }

            elements.clear();
            elements = document.select("div.ct div[class=vb notb]");//帖子
            Document doc = Jsoup.parse(elements.toString());
            elements.clear();
            elements = doc.select("h1.vt_th a");
            if (elements.size() > 0) {
                postDetailResponse.postTitle = elements.first().text();
                postDetailResponse.currentPageUrl = SubwayURL.SUBWAY_BASE + elements.first().attr("href");
            }
            elements.clear();
            elements = doc.select("div.user_first");
            if (elements.size() > 0) {
                Elements es = elements.select("a");
                if (es.size() > 1) {
                    postDetailResponse.collectUrl = es.first().attr("href");
                    postDetailResponse.author = es.get(1).text();
                    postDetailResponse.authorInfoUrl = es.get(1).attr("href");
                }
                es.clear();
                es = elements.select("span.p_dl");
                postDetailResponse.postTime = es.size() > 0 ? es.first().text() : "";
            }

            //检查帖子作者是否有问题
            /**
             * <div class="vbc">
                 <div class="pbody">
                     <div class="mes">
                        <div class="xi1">提示: <em>作者被禁止或删除 内容自动屏蔽</em></div>
                     </div>
                 </div>
             <div class="vtrim">
                <a href="forum.php?mod=post&amp;action=reply&aamp;page=1&mobile=yes">回复</a>
                <span>
             </div>
             */
            elements.clear();
            elements = doc.select("div.vbc div.pbody div.mes ");//div.postmessage
            if (elements.size() > 0) {
                postDetailResponse.postContent = elements.toString();//elements.first().text();
            }else {
                elements.clear();
                elements = doc.select("div.vbc div.pbody div.mes div.xi1");
                if(elements.size()>0){
                    postDetailResponse.postContent = elements.toString();//elements.first().text();
                }else {
                    postDetailResponse.postContent = "<p>帖子内容是空的哟！！！</p>";
                }
            }

            postDetailResponse.changeOrderUrl = document.select("div.titls a.xi2").attr("href");
            String str = document.select("div.titls span.y").text();
            postDetailResponse.commentNum = str.replace("共有", "").replace("条回复", "");

            //TODO 解析帖子评论列表信息
            elements.clear();
            elements = document.select("div[class=vb vc]");
            for (int j = 0; j < elements.size(); j++) {
                CommentInformation comment = new CommentInformation();
                comment.headShotUrl = elements.get(j).select("a.avatar img").attr("src");
                comment.author = elements.get(j).select("div.user a").text();
                comment.authorInfoUrl = elements.get(j).select("div.user a").attr("href");
                comment.floor = elements.get(j).select("div.user span[class=p_nm y]").text();
                comment.commentTime = elements.get(j).select("div.user span.p_dl").text();
                comment.commentContent = elements.get(j).select("div.pbody div.mes ").toString().replace("[查看图片]", "");//div.postmessage
                //解析引用内容
                if (elements.get(j).select("div.quote").size() > 0) {
                    comment.quoteContent = elements.get(j).select("div.quote").text();//TODO 不对，做了登陆态再来
                    comment.quoteOrigin = elements.get(j).select("div.quote font font").text();
                }
                //解析回复按钮链接
                try {
                    comment.replyUrl = elements.get(j).select("div.vtrim a").get(0).attr("href");
                    comment.replyUrl = SubwayURL.SUBWAY_BASE + comment.replyUrl;
                    BaseApplication.isLogin = true;
                } catch (Exception e) {
                    e.printStackTrace();
//                    BaseApplication.isLogin = false;
//                    Log.i("llb", "找不到回复按钮,未登录or帖子已关闭");
                }
                postDetailResponse.commentList.add(comment);
            }

            //解析回复主贴的模块
            /**
             * <div class="ct ctpd">
             <div class="ipc">
             <form method="post" name="inputform" autocomplete="off" id="fastpostform" action="forum.php?mod=post&amp;action=reply&amp;fid=23&amp;tid=546332&amp;extra=&amp;replysubmit=yes">
             <input type="hidden" name="formhash" value="55423c47" />
             <div class="ipcp">
             <div class="ipcc">
             <textarea name="message" id="fastpostmessage" cols="25" rows="7"></textarea>
             </div>
             </div>
             <input name="sechash" type="hidden" value="Svn20" />
             <div class="ips">
             <table cellspacing="0" cellpadding="0">
             <tr>
             <th>
             <p><input name="seccodeverify" id="seccodeverify_Svn20" type="text" autocomplete="off" /></p>
             </th>
             <td>
             <img src="misc.php?mod=seccode&amp;update=47349&amp;idhash=Svn20" class="scod" />
             </td>
             </tr>
             </table>
             </div>
             <div class="inbox"><input type="submit" name="replysubmit" id="fastpostsubmit" value="回复" class="ibt ibtp" /></div>
             </form>
             </div>
             */
            elements.clear();
            elements = document.select("div[class=ct ctpd] div.ipc form");//回复form表单
            try {
                if (elements.size() > 0) {
                    doc = Jsoup.parse(elements.toString());
                    postDetailResponse.replyCommentUrl = SubwayURL.SUBWAY_BASE + doc.select("form").attr("action");
                    postDetailResponse.replyFormHash = doc.select("input[name=formhash]").get(0).attr("value");
                    postDetailResponse.replysubmit = doc.select("div.inbox input[name=replysubmit]").get(0).attr("value");
                    if (doc.select("input[name=sechash]").size() > 0) {
                        postDetailResponse.sechash_CAPTCHA = doc.select("input[name=sechash]").get(0).attr("value");
                        postDetailResponse.CAPTCHA_URL = SubwayURL.SUBWAY_BASE + doc.select("img.scod").first().attr("src");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return postDetailResponse;
        }
    }
}
