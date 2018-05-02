package com.dream.llb.subway.model.bean;

import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * 提醒信息页面
 * http://www.ditiezu.com/member.php?mod=logging&action=login&mobile=yes
 * Created by llb on 2017-09-11.
 */

public class NoticeMsgResponse {
    public ArrayList<String> postIDList;//涉及到的帖子id
    public ArrayList<String> noticeList;//消息列表

    public NoticeMsgResponse() {
        noticeList = new ArrayList<>();
        postIDList = new ArrayList<>();
    }

    public class Builder {
        /**
         * <div class="bm">
         * <div class="bm_c">
         * <p class="mbm">
         * <a href="home.php?mod=spa319&mobile=yes" class="y xi2" >(屏蔽)</a>
         * <span class="xg1"><span title="2018-4-16 15:30:33">5&nbsp;小时前</span></span>
         * </p>
         * <p class="xg3">
         * <a href="home.php?mod=space&uid=583319&mobile=yes">132111012</a>
         * 回复了您的帖子
         * <a href="forum.php?m41&mobile=yes" target="_blank">番禺三号线通勤</a> &nbsp;
         * <a href="forum.php?mod641&ptid=558358&mobile=yes" target="_blank" class="lit">查看</a></p>
         * <p class="mtn ptn bt xg3">还有 12 个相同通知被忽略</p>
         * </div>
         * <div class="bm_c">
         * <p class="mbm">
         * <a href="home.php?mod=spacecp&amp;ac=common&amp;op=ignore&amp;authorid=439358&amp;type=post&amp;handlekey=addfriendhk_439358&mobile=yes" class="y xi2" >(屏蔽)</a>
         * <span class="xg1"><span title="2018-4-15 20:33:19">昨天&nbsp;20:33</span></span>
         * </p>
         * <p class="xg3"><a href="home.php?mod=space&uid=439358&mobile=yes">JHFGHR</a> 回复了您的帖子 <a href="forum.php?mod=redirect&goto=findpost&ptid=557012&pid=8686044&mobile=yes" target="_blank">向论坛提出建议改进一下用字审核问题</a> &nbsp; <a href="forum.php?mod=redirect&goto=findpost&pid=8686044&ptid=557012&mobile=yes" target="_blank" class="lit">查看</a></p>
         * </div>
         * <div class="bm_c">
         * <p class="mbm">
         * <a href="home.php?mod=spacecp&amp;ac=common&amp;op=ignore&amp;authorid=470144&amp;type=post&amp;handlekey=addfriendhk_470144&mobile=yes" class="y xi2" >(屏蔽)</a>
         * <span class="xg1"><span title="2018-4-15 13:43:19">昨天&nbsp;13:43</span></span>
         * </p>
         * <p class="xg3"><a href="home.php?mod=space&uid=470144&mobile=yes">广州地铁4号线</a> 回复了您的帖子 <a href="forum.php?mod=redirect&goto=findpost&ptid=558354&pid=8685546&mobile=yes" target="_blank">男人有几条腿</a> &nbsp; <a href="forum.php?mod=redirect&goto=findpost&pid=8685546&ptid=558354&mobile=yes" target="_blank" class="lit">查看</a></p>
         * </div>
         * <div class="bm_c">
         * <p class="mbm">
         * <a href="home.php?mod=spacecp&amp;ac=common&amp;op=ignore&amp;authorid=476326&amp;type=post&amp;handlekey=addfriendhk_476326&mobile=yes" class="y xi2" >(屏蔽)</a>
         * <span class="xg1"><span title="2018-4-14 17:36:03">前天&nbsp;17:36</span></span>
         * </p>
         * <p class="xg3"><a href="home.php?mod=space&uid=476326&mobile=yes">vbj</a> 回复了您的帖子 <a href="forum.php?mod=redirect&goto=findpost&ptid=557487&pid=8684407&mobile=yes" target="_blank">好挤呀</a> &nbsp; <a href="forum.php?mod=redirect&goto=findpost&pid=8684407&ptid=557487&mobile=yes" target="_blank" class="lit">查看</a></p>
         * </div>
         * </div>
         * </div></div>
         */
        public NoticeMsgResponse parsePage(String html) {
            if (TextUtils.isEmpty(html))
                return null;
            NoticeMsgResponse noticeMsgResponse = new NoticeMsgResponse();
            Document document = Jsoup.parse(html);
//            //解析页面form元素
            try {
                Elements elements = document.select("div.bm div.bm_c");//帖子
                for (int i = 0; i < elements.size(); i++) {
                    String item = elements.get(i).toString();
                    item = item.replace("(屏蔽)", "");
                    noticeMsgResponse.noticeList.add(item);
//                    Pattern pattern = Pattern.compile("^ptid=[0-9]*&$");
//                    Matcher matcher = pattern.matcher(item);
//                    if (matcher.find()) {
//                        item = item.substring(matcher.start(), matcher.end());
//                        noticeMsgResponse.postIDList.add(item.substring(6, item.length() - 1));
//                    }
                    if (item.indexOf("ptid=") != -1) {
                        item = item.substring(item.indexOf("ptid="));
//                        item = item.substring(5, item.indexOf("&"));
                        noticeMsgResponse.postIDList.add(item.substring(5, item.indexOf("&")));
                    }else {
                        noticeMsgResponse.postIDList.add("");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return noticeMsgResponse;
        }
    }
}
