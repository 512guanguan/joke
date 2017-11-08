package com.llb.subway.model.bean;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 分论坛列表页解析结果，如广州区
 * http://www.ditiezu.com/forum.php?mod=forumdisplay&fid=7&mobile=yes
 * Created by llb on 2017-09-11.
 */

public class PostListItem {
    public ArrayList<PostListInformation> postList;
    public ArrayList<LineInformation> lineList;

    public PostListItem() {
        this.postList = new ArrayList<>();
        this.lineList = new ArrayList<>();
    }
    public PostListItem(ArrayList<PostListInformation> postList, ArrayList<LineInformation> lineList) {
        this.postList = postList;
        this.lineList = lineList;
    }

    public class  PostListInformation{
        public String postUrl;
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
         */
        public List<PostListItem> parse(String html){
            if(html==null)
                return null;
            List<LineInformation> lineList =new ArrayList<>();
            Document document= Jsoup.parse(html);
            //解析所有线路信息
            Elements elements = document.select("div #thtys_menu a");
            for(int j = 0;j<elements.size();j++){
                Element Section = elements.get(j);
                LineInformation line = new LineInformation();
                line.name = elements.get(j).text();
                line.url = elements.get(j).attr("href");
                lineList.add(line);
            }
            //TODO 解析帖子列表信息
            
            return null;
        }
    }
}
