package com.llb.joke.model.bean.joke;

/**
 * 获取最新的笑话
 * Created by Derrick on 2017/6/19.
 *接口地址：http://japi.juhe.cn/joke/content/text.from
 * 请求方式：http get
 *
 */
public class GetLatestJokeRequest {
    public int page; //	int	否	当前页数,默认1
    public int pagesize; // int	否	每次返回条数,默认1,最大20
    public String key; // string	是	您申请的key
}