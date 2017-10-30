package com.llb.subway.model.bean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by llb on 2017-09-11.
 */

public class PostListItem {
    public String url;
    public String title;
    public String author;
    public String time;
    public String commentNum;

    public PostListItem(String url, String title, String author, String time, String commentNum) {
        this.url = url;
        this.title = title;
        this.author = author;
        this.time = time;
        this.commentNum = commentNum;
    }

    public static class Builder{
        /**
         * <ul id="alist" class="thlist">
         *  <li>
         *     <a href="forum.php?mod=viewthread&tid=476334&mobile=yes">
         *         <h1> <img src="./mplus/img/p_1.png" height="15" /> 征集2018年地铁族台历图片作品公告 </h1>
         *         <p> author <span class="pipe">-</span>2016-9-7 <span class="replies">101</span> </p>
         *     </a>
         *  </li>
         </ul>
         */
        public static List<PostListItem> parse(String html){
            if(html==null)
                return null;
            List<PostListItem> postList =new ArrayList<>();

            Document document= Jsoup.parse(html);
            Element list = document.getElementById("alist");
            Elements items =list.getElementsByTag("li");
            for(int i=0;i<items.size();i++){
                Element item=items.get(i).getElementById("a");
                Document doc = Jsoup.parse(item.toString());
                String url = doc.attr("href");
                String title = doc.select("h1").get(0).text();
                String author = doc.select("p").get(0).text();
                String time = doc.select("").text();// TODO
                String commentNum=doc.getElementsByClass("replies").text();
                postList.add(new PostListItem(url, title,author,time,commentNum));
            }
            return postList;
        }
    }
}
