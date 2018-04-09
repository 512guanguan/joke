package com.dream.llb.subway.model.bean;

import android.text.TextUtils;
import android.util.Log;

import com.dream.llb.subway.model.api.SubwayURL;

import net.nightwhistler.htmlspanner.TextUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.LinkedHashMap;
import java.util.TreeMap;

/**
 * 发帖页面解析结果
 * <p>
 * http://www.ditiezu.com/forum.php?mod=post&action=newthread&fid=7&mobile=yes
 */

public class EditPostPageResponse {
    public String formhash;
    public String posttime;
    public String wysiwyg;

    public LinkedHashMap<String, String> subjectMap;//存储主题列表，如<"一号线", "22">
    //验证码模块，不一定有
    public String sechash;
    public String captchaUrl;//二维码图片url

    public String parseurloff;//禁用链接识别,默认1
    public String usesig;//是否使用个人签名，默认1
    public String allownoticeauthor;//接收回复通知，默认1

    public EditPostPageResponse() {
        subjectMap = new LinkedHashMap<>();
        parseurloff = "1";
        usesig = "1";
        allownoticeauthor = "1";
    }

    public class Builder {
        /**
         * <form method="post" autocomplete="off" id="postform"
         * action="forum.php?mod=post&amp;action=newthread&amp;fid=7&amp;extra=&amp;topicsubmit=yes"
         * onsubmit="return validate(this)">
         * <input type="hidden" name="formhash" id="formhash" value="bd719e21" />
         * <input type="hidden" name="posttime" id="posttime" value="1516545385" />
         * <input type="hidden" name="wysiwyg" id="e_mode" value="0" />
         * <div class="box">
         * <div id="postbox">
         * <div class="inbox">
         * <div class="ptypes">
         * <div class="ipcl ptype" id="ptypes_menu" >
         * <select name="typeid" width="80">
         * <option value="0">选择主题分类</option>
         * <option value="19">1号线</option>
         * <option value="20">2号线</option>
         * <option value="703">3号线</option>
         * </select>
         * </div>
         * <span class="xi1"> *必填</span>        </div>
         * <div class="ipcl xg1"><span>标题:</span></div>
         * <p><input type="text" name="subject" value="" size="25" style="width:100%;" /></p>
         * </div>
         * <div class="ipcc">
         * <textarea name="message" cols="24" rows="7" id="e_textarea" ></textarea>
         * </div>
         * <p>
         * <p>
         * <p>
         * <input name="sechash" type="hidden" value="S4S3o" />
         * <p>
         * <div class="ips">
         * <table cellspacing="0" cellpadding="0">
         * <tr>
         * <th>
         * <p><input name="seccodeverify" id="seccodeverify_S4S3o" type="text" autocomplete="off" /></p>
         * </th>
         * <td>
         * <img src="misc.php?mod=seccode&amp;update=34606&amp;idhash=S4S3o" class="scod" />
         * </td>
         * </tr>
         * </table>
         * </div>
         * <p>
         * <div class="inbox">
         * <button type="submit" id="postsubmit" class="ibt ibtp" value="true" name="topicsubmit">
         * 发表帖子</button>
         * </div>
         * </div>
         * </div>
         * <div style="display:none;">
         * <div><input type="checkbox" name="parseurloff" id="parseurloff" class="pc" value="1"  /><label for="parseurloff">禁用链接识别</label></div>
         * <div><input type="checkbox" name="usesig" id="usesig" class="pc" value="1" checked="checked" /><label for="usesig">使用个人签名</label></div>
         * <div><input type="checkbox" name="allownoticeauthor" id="allownoticeauthor" class="pc" value="1" checked="checked" /><label for="allownoticeauthor">接收回复通知</label></div>
         * </div>
         * </form>
         * <div class="post_pics">
         * <a href="javascript:;" id="post_pic" onclick="dbox('post_pic','post_pic');">上传图片</a>
         */
        public EditPostPageResponse parse(String html) {
            if (TextUtils.isEmpty(html))
                return null;
            EditPostPageResponse response = new EditPostPageResponse();
            Document document = Jsoup.parse(html);
            //先匹配出form表单内容
            /**
             *  <form method="post" autocomplete="off" id="postform"
             * action="forum.php?mod=post&amp;action=newthread&amp;fid=7&amp;extra=&amp;topicsubmit=yes"
             * onsubmit="return validate(this)">
             * <input type="hidden" name="formhash" id="formhash" value="bd719e21" />
             * <input type="hidden" name="posttime" id="posttime" value="1516545385" />
             * <input type="hidden" name="wysiwyg" id="e_mode" value="0" />
             */
            Elements elements = document.select("form#postform");
            try {
                document = Jsoup.parse(elements.toString());
                response.formhash = document.select("input[name=formhash]").attr("value").toString();
                response.posttime = document.select("input[name=posttime]").attr("value").toString();
                response.wysiwyg = document.select("input[name=wysiwyg]").attr("value").toString();

                //解析线路主题信息
                elements.clear();
                elements = document.select("div#postbox div.inbox div.ptypes div[class=ipcl ptype] select[name=typeid] option");

                for (int i = 0; i < elements.size(); i++) {
                    response.subjectMap.put(elements.get(i).text(), elements.get(i).attr("value").toString());
                }
                /**
                 * <input name="sechash" type="hidden" value="S4S3o" />
                 *
                 *  <div class="ips">
                 *   <table cellspacing="0" cellpadding="0">
                 *      <tr>
                 *      <th>
                 *          <p><input name="seccodeverify" id="seccodeverify_S4S3o" type="text" autocomplete="off" /></p>
                 *      </th>
                 *      <td>
                 *      <img src="misc.php?mod=seccode&amp;update=34606&amp;idhash=S4S3o" class="scod" />
                 *      </td>
                 *      </tr>
                 *  </table>
                 * </div>
                 */
                response.sechash = document.select("div#postbox input[name=sechash]").attr("value").toString();
                response.captchaUrl = document.select("div#postbox div.ips table img.scod").attr("src");
                if(!TextUtils.isEmpty(response.captchaUrl)){
                    response.captchaUrl = SubwayURL.SUBWAY_BASE + response.captchaUrl;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("llb", "解析发帖页信息出错");
            }
            return response;
        }
    }
}
