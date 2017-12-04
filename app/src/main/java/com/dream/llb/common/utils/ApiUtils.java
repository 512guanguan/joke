package com.dream.llb.common.utils;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Derrick on 2017/7/4.
 */

public class ApiUtils {
    public static long getTime() {
        return new Date().getTime();
    }
    public static Map<String, String> parseRequestParams(Object request) {
        Field[] fields = request.getClass().getDeclaredFields();
        Map<String, String> requestParams = new HashMap<>();
        for(Field field : fields) {
            try {
                if(field.get(request) != null) {
                    requestParams.put(field.getName(),field.get(request).toString());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return requestParams;
    }
}
