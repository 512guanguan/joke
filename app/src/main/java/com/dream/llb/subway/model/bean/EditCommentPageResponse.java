package com.dream.llb.subway.model.bean;

import android.text.TextUtils;
import android.util.Log;

import com.dream.llb.subway.model.SubwayLoader;
import com.dream.llb.subway.model.api.SubwayURL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 回复帖子的页面解析结果   回复内容最少要有10个字符
 *
 * 只有经验不够的用户才需要输入验证码，回复主贴可以直接在帖子详情页进行，若要验证码就弹框进行
 * http://www.ditiezu.com/forum.php?mod=post&action=reply&fid=23&tid=546461&repquote=8391450&page=1&mobile=yes
 */

public class EditCommentPageResponse {
    public String quoteText;//引用内容

    public String uploadImageUrl;//上传图片的链接
    public String uploadImageHash;
    public String uploadImageUid;

    public String replyUrl;//回复内容提交url  http://www.ditiezu.com/forum.php?mod=post&action=reply&fid=23&tid=546461&extra=&replysubmit=yes
    public String formhash;
    public String posttime;
    public String wysiwyg;//TODO 这个参数不知道啥意思
    public String noticeauthor;
    public String noticetrimstr;
    public String noticeauthormsg;
    public String reppid;
    public String reppost;
    public String subject;
    public String usesig;//是否使用个人签名 好像默认都是传1
    public String replysubmit;//貌似都是true

    //验证码模块，不一定有
    public String sechash;
    public String captchaUrl;//二维码图片url

    public class Builder{
        /**
         * 上传图片模块的
         * post form示例：
         * http://www.ditiezu.com/misc.php?mod=swfupload&operation=upload&simple=1&type=image
         *
         * Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*;q=0.8
        Accept-Encoding:gzip, deflate
        Accept-Language:zh-CN,zh;q=0.9,en;q=0.8
        Cache-Control:max-age=0
        Connection:keep-alive
        Content-Length:13863
        Content-Type:multipart/form-data; boundary=----WebKitFormBoundarygChiLHBa0eQbxuwV
        Cookie:__cfd
        Host:www.ditiezu.com
        Origin:http://www.ditiezu.com
        Referer:http://www.ditiezu.com/forum.php?mod=post&action=reply&fid=23&tid=546461&repquote=8391450&page=1&mobile=yes
        Upgrade-Insecure-Requests:1
        User-Agent:Mozilla/5.0 (iPhone;

         *
         *------WebKitFormBoundarygChiLHBa0eQbxuwV
         Content-Disposition: form-data; name="uid"

         598175
         ------WebKitFormBoundarygChiLHBa0eQbxuwV
         Content-Disposition: form-data; name="hash"

         35d1addbefcee10a8e5b7b8aa64f8f19
         ------WebKitFormBoundarygChiLHBa0eQbxuwV
         Content-Disposition: form-data; name="Filedata"; filename="hh1562535601_676.gif"
         Content-Type: image/gif

         ------WebKitFormBoundarygChiLHBa0eQbxuwV--
         *
         * <div id="imgattachbtnhidden" style="display:none;">
         *     <span>
         *         <form name="imgattachform" id="imgattachform" method="post" autocomplete="off"
         *              action="misc.php?mod=swfupload&amp;operation=upload&amp;simple=1&amp;type=image"
         *              target="attachframe" enctype="multipart/form-data"><input type="hidden" name="uid"
         *              value="598175"><input type="hidden" name="hash" value="35d1addbefcee10a8e5b7b8aa64f8f19">
         *                  <input type="file" name="Filedata" size="0" style="width:120px; height:20px; opacity:0;" />
         *         </form>
         *     </span>
         *</div>
         <div id="imgattachbtn"></div>
         <div class="mtb post_ts">
         <span class="xi1">jpg, jpeg, gif, png</span>
         文件尺寸 <span class="xi1">小于 512KB </span>
         </div></div>
         <div id="imguploadbtn" class="inbox mtb">
         <button class="ibt ibt_ps ibtp" type="button" onclick="uploadAttach(0, 0, 'img')">上传图片</button>
         </div>
         <div id="imguploading" class="mtb" style="display: none;"><img src="comiis_xy/uploading.gif" style="vertical-align: middle;" /></div>
         </div></div>


         回复按钮：
         formhash:5e4293fb
         posttime:1514042699
         wysiwyg:0
         noticeauthor:4cccF7mnbM5SNG8BkyixToQPIH5PsYVZejmGSPlabu0FUK6baA
         noticetrimstr:[quote][color=#999999]谷飘 发表于 2016-10-30 15:25[/color]
         [color=#999999]这都几年前的了。[/color][/quote]
         noticeauthormsg:这都几年前的了。
         reppid:7340444
         reppost:7340444
         special:1
         subject:
         message:挖坟？
         usesig:1

         <form method="post" autocomplete="off" id="postform"
         action="forum.php?mod=post&amp;action=reply&amp;fid=23&amp;tid=546461&amp;extra=&amp;replysubmit=yes"
         onsubmit="to_left validate(this)">
             <input type="hidden" name="formhash" id="formhash" value="5e4293fb" />
             <input type="hidden" name="posttime" id="posttime" value="1514040200" />
             <input type="hidden" name="wysiwyg" id="e_mode" value="0" />
             <input type="hidden" name="noticeauthor" value="91d4AlQw3mKonnHyft282wUghyYexkG+s02OjKZ1yxg/gWocPw" />
             <input type="hidden" name="noticetrimstr" value="[quote][color=#999999]市二宫 发表于 2017-12-23 22:42[/color][color=#999999]。。。看标题我还以为是哪个不知好歹的小子又来地铁族发广告[/color][/quote]" />
             <input type="hidden" name="noticeauthormsg" value="。。。看标题我还以为是哪个不知好歹的小子又来地铁族发广告" />
             <input type="hidden" name="reppid" value="8391450" />
             <input type="hidden" name="reppost" value="8391450" />
             <div class="box">
                <div id="postbox">
                    <div class="inbox">
                        <div class="ipcl xg1">RE: 抓紧买票上船！
                            <span id="subjectbox" style="display:none">
                                <input type="text" name="subject" id="subject" value="" onkeyup="strLenCalc(this, 'checklen', 80);" style="width:100%;" />
                            </span>
                            <div class="quote"><blockquote><font color="#999999">市二宫 发表于 2017-12-23 22:42</font><br /><font color="#999999">。。。看标题我还以为是哪个不知好歹的小子又来地铁族发广告</font></blockquote>
                            </div>
                        </div>
                    </div>
                    <div class="ipcc">
                        <textarea name="message" cols="24" rows="7" id="e_textarea" ></textarea>
                    </div>
                    //这部分是验证码模块的，不是所有的都需要验证码
                    <input name="sechash" type="hidden" value="SwegT" />
                     <div class="ips">
                         <table cellspacing="0" cellpadding="0">
                             <tr>
                             <th>
                             <p><input name="seccodeverify" id="seccodeverify_SwegT" type="text" autocomplete="off" /></p>
                             </th>
                             <td>
                             <img src="misc.php?mod=seccode&amp;update=44068&amp;idhash=SwegT" class="scod" />
                             </td>
                             </tr>
                         </table>
                    </div>
                    <div class="inbox">
                         <button type="submit" id="postsubmit" class="ibt ibtp" value="true" name="replysubmit">
                         回复</button>
                    </div>
                </div>
             </div>
             <div style="display:none;">
                <div><input type="checkbox" name="parseurloff" id="parseurloff" class="pc" value="1"  /><label for="parseurloff">禁用链接识别</label></div>
                <div><input type="checkbox" name="usesig" id="usesig" class="pc" value="1" checked="checked" /><label for="usesig">使用个人签名</label></div>
             </div>
         </form>
         */
        public EditCommentPageResponse parse(String html){
            if(TextUtils.isEmpty(html))
                return null;
            EditCommentPageResponse response = new EditCommentPageResponse();
            Document document= Jsoup.parse(html);
            //解析所有引用内容信息
            response.quoteText = document.select("div.inbox div[class=ipcl xg1]").toString();
            /**
             * <div class="ipcl xg1">RE: 抓紧买票上船！
             <span id="subjectbox" style="display:none">
             <input type="text" name="subject" id="subject" value="" onkeyup="strLenCalc(this, 'checklen', 80);" style="width:100%;" />
             </span>
             <div class="quote"><blockquote><font color="#999999">市二宫 发表于 2017-12-23 22:42</font><br /><font color="#999999">。。。看标题我还以为是哪个不知好歹的小子又来地铁族发广告</font></blockquote>
             </div>
             </div>
             */
            try{
                response.subject = document.select("div.inbox div[class=ipcl xg1] span input[name=subject]").get(0).attr("value");

            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                Elements elements = document.select("form#postform");
                //解析回复操作所需的参数
                response.replyUrl = SubwayURL.SUBWAY_BASE + elements.get(0).attr("action");
                Document doc= Jsoup.parse(elements.toString());
                response.formhash = doc.select("input[name=formhash]").get(0).attr("value");
                response.posttime = doc.getElementsByAttributeValue("name","posttime").attr("value");
                response.wysiwyg = doc.getElementsByAttributeValue("name","wysiwyg").attr("value");
                response.noticeauthor = doc.select("input[name=noticeauthor]").get(0).attr("value");
                response.noticetrimstr = doc.select("input[name=noticetrimstr]").get(0).attr("value");
                response.noticeauthormsg = doc.select("input[name=noticeauthormsg]").get(0).attr("value");
                response.reppid = doc.select("input[name=reppid]").get(0).attr("value");
                response.reppost = doc.select("input[name=reppost]").get(0).attr("value");
                response.usesig = doc.select("input[name=usesig]").get(0).attr("value");
                response.replysubmit = doc.select("div.inbox button[name=replysubmit]").get(0).attr("value");
                //解析可能存在验证码
                try {
                    response.sechash = doc.getElementsByAttributeValue("name","sechash").attr("value");
                    response.captchaUrl = doc.select("div.ips table img.scod").attr("src");
                    if(!TextUtils.isEmpty(response.captchaUrl)){
                        response.captchaUrl = SubwayURL.SUBWAY_BASE + response.captchaUrl;
                    }
                }catch (Exception e){
                    e.printStackTrace();
//                    Log.i("llb","解析验证码所需的参数出错");
                }
            }catch (Exception e){
                e.printStackTrace();
//                Log.i("llb","解析回复操作所需的参数出错");
            }

            try {
                Elements elements = document.select("div#imgattachbtnhidden form#imgattachform");
                //解析回复操作所需的参数
                response.uploadImageUrl = elements.get(0).attr("action");
                Document doc= Jsoup.parse(elements.toString());
                response.uploadImageHash = doc.select("input[name=hash]").get(0).attr("value");
                response.uploadImageUid = doc.select("input[name=uid]").get(0).attr("value");
            }catch (Exception e){
                e.printStackTrace();
//                Log.i("llb","解析上传图片需要的参数出错");
            }
            return response;
        }
    }
}
