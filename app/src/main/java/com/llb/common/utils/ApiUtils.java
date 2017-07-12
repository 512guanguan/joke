package com.llb.common.utils;

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
                requestParams.put(field.getName(), field.get(request).toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                requestParams.put(field.getName(), "");
            }
        }
        return requestParams;
    }
}
