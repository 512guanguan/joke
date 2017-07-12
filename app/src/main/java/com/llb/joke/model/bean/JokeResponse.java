package com.llb.joke.model.bean;

/**
 * Created by Derrick on 2017/6/19.
 * <p>
 * {
 * "error_code": 0,
 * "reason": "Success",
 * "result": {
 * "data": [
 * {
 * "content": "女生分手的原因有两个，\r\n一个是：闺蜜看不上。另一个是：闺蜜看上了。",
 * "hashId": "607ce18b4bed0d7b0012b66ed201fb08",
 * "unixtime": 1418815439,
 * "updatetime": "2014-12-17 19:23:59"
 * },
 * {
 * "content": "老师讲完课后，问道\r\n“同学们，你们还有什么问题要问吗？”\r\n这时，班上一男同学举手，\r\n“老师，这节什么课？”",
 * "hashId": "20670bc096a2448b5d78c66746c930b6",
 * "unixtime": 1418814837,
 * "updatetime": "2014-12-17 19:13:57"
 * }]
 */
public class JokeResponse {
    public int error_code;
    public String reason;
    public JokeResult result;

    public class JokeResult{
        public JokeData[] data;
    }
    public class JokeData {
        public String content;
        public String hashId;
        public String unixtime;
        public String updatetime;
    }
}