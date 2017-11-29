package com.llb.subway.model.bean;

import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * 登录结果页面
 * http://www.ditiezu.com/member.php?mod=logging&action=login&mobile=yes
 * Created by llb on 2017-09-11.
 */

public class LoginResponse {
    public String warning;//登录失败的错误提示信息

    public LoginResponse() {
    }

    public class Builder{
        /**
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
            }catch (Exception e){
                e.printStackTrace();
            }
            return loginResponse;
        }
    }
}
