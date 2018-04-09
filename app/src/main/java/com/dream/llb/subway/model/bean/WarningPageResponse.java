package com.dream.llb.subway.model.bean;

import android.text.TextUtils;

import com.dream.llb.subway.model.api.SubwayURL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 结果提示页面，如评论失败提醒页
 * http://www.ditiezu.com/member.php?mod=logging&action=login&mobile=yes
 * Created by llb on 2017-09-11.
 */

public class WarningPageResponse extends BaseResponse{
    public String warning;//登录失败的错误提示信息
    public WarningPageResponse() {
    }

    public class Builder{
        /**
         <div class="showmg">
         <div class="warning" id="messagetext">
         <p class="mbn">抱歉，验证码填写错误</p>
         <p><a href="http://www.ditiezu.com/forum.php?mod=viewthread&tid=546235&extra=&ordertype=1&threads=thread&mobile=yes">点击此链接进行跳转</a></p>
         </div>
         </div>
         */
        public WarningPageResponse parsePage(String html){
            if(TextUtils.isEmpty(html))
                return null;
            WarningPageResponse warningPageResponse = new WarningPageResponse();
            Document document= Jsoup.parse(html);
//            //解析页面form元素
            try {
                Elements elements = document.select("div.showmg div.warning");//帖子
                if(elements.size()>0){
                    Elements e= elements.get(0).select("p.mbn");
                    if(e.size()>0){
                        warningPageResponse.warning = e.first().text();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return warningPageResponse;
        }
    }
}
