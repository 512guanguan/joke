package com.llb.joke.model.bean.joke;

/**
 * 根据时间戳返回该时间点前或后的笑话列表
 * Created by Derrick on 2017/6/26.
 */

public class GetJokeByUpdateTimeRequest {
    public String sort; //string	是	类型，desc:指定时间之前发布的，asc:指定时间之后发布的
    public int page; //int	否	当前页数,默认1
    public int pagesize; //int	否	每次返回条数,默认1,最大20
    public String time; //string	是	时间戳（10位），如：1418816972
    public String key; //string	是	您申请的key
}
