package com.dream.llb.subway.model.bean;

import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * 登录页面
 * http://www.ditiezu.com/member.php?mod=logging&action=login&mobile=yes
 * Created by llb on 2017-09-11.
 */

public class LoginPageResponse {
    public String CAPTCHA_URL;//验证码图片链接
    public String loginURL;//登录post链接
    public String formHash;
    public String secHash;
//    public String loginHash;
    public String referer;//
    public String cookieTime;
    public String submit;//登录

    public LoginPageResponse() {
    }

    public class Builder{
        /**
         <form method="post" action="member.php?mod=logging&amp;action=login&amp;loginsubmit=yes&amp;loginhash=LaI2f" onSubmit="pwmd5('password3_LaI2f');" >
         <input type="hidden" name="formhash" id="formhash" value='fa5cb944' />
         <input type="hidden" name="referer" id="referer" value="http://www.ditiezu.com/forum.php?mod=forumdisplay&fid=23&mobile=yes" />
         <p>
         <span>账号</span><input type="text" name="username" id="username_LaI2f" />
         </p>
         <p>
         <span>密码</span><input type="password" name="password" id="password3_LaI2f" value='' />
         </p>
         <input name="sechash" type="hidden" value="S3Ffi" />
         <div class="ips">
         <table cellspacing="0" cellpadding="0">
         <tr>
         <th>
         <p><input name="seccodeverify" id="seccodeverify_S3Ffi" type="text" autocomplete="off" /></p>
         </th>
         <td>
         <img src="misc.php?mod=seccode&amp;update=92052&amp;idhash=S3Ffi" class="scod" />
         </td>
         </tr>
         </table>
         </div>
         <div id="ser_menu" class="ser_menu" style="display:none">
         <div class="select">
         <select name="questionid" id="questionid_LaI2f" >
         <option value="0">安全提问(未设置请忽略)</option>
         <option value="1">母亲的名字</option>
         <option value="2">爷爷的名字</option>
         <option value="3">父亲出生的城市</option>
         <option value="4">你其中一位老师的名字</option>
         <option value="5">你个人计算机的型号</option>
         <option value="6">你最喜欢的餐馆名称</option>
         <option value="7">驾驶执照最后四位数字</option>
         </select>
         </div>
         <p>
         <input type="text" name="answer" id="answer_LaI2f" style="width:100%;" />
         </p>
         </div>
         <label style="display:none;"><input type="checkbox" name="cookietime" id="cookietime_LaI2f" value="2592000" checked="checked" />自动登录</label>
         <div class="inbox mtn lgs_inp" >
         <input type="submit" name="submit" id="submit" value="登录" class="ibt" />
         <input type="reset" value="重填" class="ibt y" />
         </div>
         </form>
         */
        public LoginPageResponse parsePage(String html){
            if(TextUtils.isEmpty(html))
                return null;
            LoginPageResponse loginPageResponse = new LoginPageResponse();
            Document document= Jsoup.parse(html);
//            //解析页面form元素
            try {
                Elements elements = document.select("form");//帖子
                Document doc= Jsoup.parse(elements.toString());
                loginPageResponse.loginURL = doc.select("form").attr("action");
                loginPageResponse.cookieTime = doc.getElementsByAttributeValue("name","cookietime").attr("value");
                loginPageResponse.formHash = doc.getElementsByAttributeValue("name","formhash").attr("value");
                loginPageResponse.referer = doc.getElementsByAttributeValue("name","referer").attr("value");
                loginPageResponse.secHash = doc.getElementsByAttributeValue("name","sechash").attr("value");
                loginPageResponse.submit = doc.getElementsByAttributeValue("name","submit").attr("value");
                loginPageResponse.CAPTCHA_URL = doc.select("img.scod").first().attr("src");
            }catch (Exception e){
                e.printStackTrace();
            }
            return loginPageResponse;
        }
    }
}
