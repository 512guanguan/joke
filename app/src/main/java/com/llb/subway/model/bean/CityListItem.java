package com.llb.subway.model.bean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by llb on 2017-09-11.
 */

public class CityListItem {
    public String url;
    public String name;
    public String iconUrl;
    public String description;

    public CityListItem(String url, String name, String iconUrl, String description) {
        this.url = url;
        this.name = name;
        this.iconUrl = iconUrl;
        this.description = description;
    }

    public static class Builder{
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
        public static List<CityListItem> parse(String html){
            if(html==null)
                return null;
            List<CityListItem> postList =new ArrayList<>();

            Document document= Jsoup.parse(html);
            Elements elements = document.select("div.catlist");
//            Element list = document.getElementById("alist");
            Elements items =elements.get(0).select("li");
            for(int i=0;i<items.size();i++){
                Elements item=items.get(i).getElementsByTag("a");
                Document doc = Jsoup.parse(item.toString());
                String url = item.first().attr("href");
                String iconUrl = doc.select("img").get(0).attr("src");
                String name = doc.select("p.f_nm").get(0).text();
                String description = doc.select("p.xg1").get(0).text();
                postList.add(new CityListItem(url,name,iconUrl,description));
            }
            return postList;
        }
    }
}
