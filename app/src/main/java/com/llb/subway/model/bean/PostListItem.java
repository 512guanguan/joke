package com.llb.subway.model.bean;

import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 分论坛列表页解析结果，如广州区
 * http://www.ditiezu.com/forum.php?mod=forumdisplay&fid=7&mobile=yes
 * Created by llb on 2017-09-11.
 */

public class PostListItem {
    public ArrayList<PostItemInformation> postList;
    public ArrayList<LineInformation> lineList;

    public PostListItem() {
        this.postList = new ArrayList<>();
        this.lineList = new ArrayList<>();
    }
    public PostListItem(ArrayList<PostItemInformation> postList, ArrayList<LineInformation> lineList) {
        this.postList = postList;
        this.lineList = lineList;
    }

    public class PostItemInformation {
        public String postUrl;
        public String iconUrl;//http://www.ditiezu.com/mplus/img/p_1.png
        public String title;
        public String author;
        public String time;
        public String commentNum;
    }

    public class LineInformation{
        public String name;
        public String url;
    }

    public class Builder{
        /**
         * <div id="thtys_menu" class="thtyss" style="display:none">
             <a href="forum.php?mod=forumdisplay&amp;fid=8&mobile=yes" class="a">全部</a>
             <a href="forum.php?mod=forumdisplay&amp;fid=8&amp;filter=typeid&amp;typeid=48&mobile=yes" >1号线</a>
             <a href="forum.php?mod=forumdisplay&amp;fid=8&amp;filter=typeid&amp;typeid=49&mobile=yes" >2号线</a>
         </div>

         <ul id="alist" class="thlist">
             <li>
                 <a href="forum.php?mod=viewthread&amp;tid=383197&mobile=yes">
                     <h1><img src="./mplus/img/l1.png" height="15" />地铁族北京区管理规定</h1>
                     <p>hat600<span class="pipe">-</span>2014-9-23 <span class="replies">3</span></p>
                 </a>
             </li>
             <li>
                 <a href="forum.php?mod=viewthread&amp;tid=537380&mobile=yes">
                     <h1>关于北京北站（西直门站）的建议，北京北改为西直门，清河改为北京北。</h1>
                     <p>zzyljl<span class="pipe">-</span>2017-11-7 <span class="replies">40</span></p>
                 </a>
             </li>
         </ul>
         */
        public PostListItem parse(String html){
            if(TextUtils.isEmpty(html))
                return null;
            PostListItem postListItem = new PostListItem();
            Document document= Jsoup.parse(html);
            //解析所有线路信息
            Elements elements = document.select("div #thtys_menu a");
            for(int j = 0;j<elements.size();j++){
                LineInformation line = new LineInformation();
                line.name = elements.get(j).text();
                line.url = elements.get(j).attr("href");
                postListItem.lineList.add(line);
            }
            //TODO 解析帖子列表信息
            Elements postElements = document.select("ul.thlist li");
            for(int j = 0;j<postElements.size();j++){
                PostItemInformation postItemInformation = new PostItemInformation();
                postItemInformation.postUrl = postElements.get(j).select("li a").attr("href");
                postItemInformation.title = postElements.get(j).select("li a h1").text();
                Elements e = postElements.get(j).select("li a h1 img");
                postItemInformation.iconUrl = e.size() > 0 ? e.first().attr("src").substring(2) : "";
                postItemInformation.commentNum = postElements.get(j).getElementsByClass("replies").text();
                List<TextNode> textNodes = postElements.get(j).getElementsByTag("p").first().textNodes();
                postItemInformation.author =textNodes.get(0).text();
                postItemInformation.time = textNodes.get(1).text();
                postListItem.postList.add(postItemInformation);
            }
            return postListItem;
        }
    }
}
