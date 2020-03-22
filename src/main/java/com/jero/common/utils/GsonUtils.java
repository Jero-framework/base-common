package com.jero.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @Description Gson工具类
 * @Author lixuetao
 * @Date 2020/3/22
 **/
public class GsonUtils {

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private GsonUtils() {
        throw new IllegalStateException("GsonUtils Utility class");
    }

    public static Gson getGson() {
        return (new GsonBuilder()).serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    public static String toJson(Object obj) {
        return getGson().toJson(obj);
    }

    public static String toJson(Object obj, Type type) {
        return getGson().toJson(obj, type);
    }

    public static <T> T fromJson(String jsonStr, Type type) {
        return getGson().fromJson(jsonStr, type);
    }

    public static <T> T fromJson(String jsonStr, Class<T> clazz) {
        return getGson().fromJson(jsonStr, clazz);
    }

}
