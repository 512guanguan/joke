package com.dream.llb.subway.model.bean;

import android.text.TextUtils;

import com.dream.llb.subway.model.api.SubwayURL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 登录结果页面
 * http://www.ditiezu.com/member.php?mod=logging&action=login&mobile=yes
 * Created by llb on 2017-09-11.
 */

public class LoginResponse {
    public String warning;//登录失败的错误提示信息
    public String messageURL;//消息
    public String noticeURL;//提醒
    public String friendURL;//好友
    public String myPostsURL;//我的帖子
    public String myCollectionURL;//我的收藏
    public String personalInfoURL;//个人资料

    public LoginResponse() {
    }

    public class Builder{
        /**
         * <header>  //登录成功时才有这个
         <a href="forum.php?mobile=yes"><img src="./mplus/img/logo.png" height="45" class="logo_img" /></a>
         <div class="umus">
         <a href="javascript:;" onClick="tbox('wrap');"><img src="./mplus/img/h_t.png" height="20" /></a>
         <a href="search.php?mod=forum&mobile=yes" class="a"><img src="./mplus/img/h_s.png" height="20" /></a>
         <a href="javascript:;" id="h_umu" class="a" onClick="dbox('h_umu','a');"><img src="./mplus/img/h_u.png" height="20" /></a>
         </div>
         <div class="a_no"><a href="javascript:;" onClick="tbox('wrap');"></a></div>
         <ul class="p_umu" id="h_umu_menu" style="display:none;"><em></em>
             <li class="notb"><a href="home.php?mod=space&amp;do=pm&mobile=yes">消息</a></li>
             <li><a href="home.php?mod=space&amp;do=notice&mobile=yes">提醒</a></li>
             <li><a href="home.php?mod=space&amp;do=friend&mobile=yes">好友</a></li>
             <li><a href="home.php?mod=space&amp;uid=598175&amp;do=thread&amp;view=me&mobile=yes">我的帖子</a></li>
             <li><a href="home.php?mod=space&amp;uid=598175&amp;do=favorite&amp;view=me&amp;type=forum&mobile=yes">我的收藏</a></li>
             <li><a href="home.php?mod=space&amp;uid=598175&amp;do=profile&mobile=yes">个人资料</a></li>
             <li style=" border-bottom:none;"><a href="member.php?mod=logging&amp;action=logout&amp;formhash=673e7f41&mobile=yes">退出</a></li>
         </ul>
         </header>

         <div class="ct">
             <div class="pt bb">
                <a href="forum.php?mobile=yes">首页</a> <em> &gt; </em> <a href="javascript:history.back();" onclick="javascript:history.back();" >返回</a>
             </div>
             <div class="showmg">
                 <div class="warning" id="messagetext">
                     <p class="mbn">抱歉，验证码填写错误</p>
                     <p><a href="http://www.ditiezu.com/forum.php?mod=forum&mobile=yes">点击此链接进行跳转</a></p>
                 </div>
             </div>
         </div></div>

         <div class="ct">
         <div class="pt bb">
         <a href="forum.php?mobile=yes">首页</a> <em> &gt; </em> <a href="javascript:history.back();" onclick="javascript:history.back();" >返回</a>
         </div>
         <div class="showmg">
         <div class="warning" id="messagetext">
         <p class="mbn">抱歉，验证码填写错误</p>
         <p><a href="http://www.ditiezu.com/forum.php?mod=forum&mobile=yes">点击此链接进行跳转</a></p>
         </div>
         </div>
         </div></div>
         */
        public LoginResponse parsePage(String html){
            if(TextUtils.isEmpty(html))
                return null;
            LoginResponse loginResponse = new LoginResponse();
            Document document= Jsoup.parse(html);
//            //解析页面form元素
            try {
                Elements elements = document.select("div.ct div.showmg div.warning");//帖子
                if(elements.size()>0){
                    Elements e= elements.get(0).select("p.mbn");
                    if(e.size()>0){
                        loginResponse.warning = e.first().text();
                    }
                }

                //如果登录成功了
                elements.clear();
                elements = document.select("ul.p_umu li");
                if(elements.size()>0){
                    for(Element item : elements){
                        switch (item.text()){
                            case "消息":
                                loginResponse.messageURL = SubwayURL.SUBWAY_BASE+item.select("a").first().attr("href");
                                break;
                            case "提醒":
                                loginResponse.noticeURL = SubwayURL.SUBWAY_BASE+item.select("a").first().attr("href");
                                break;
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return loginResponse;
        }
    }
}
