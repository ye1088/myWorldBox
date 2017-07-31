package com.huluxia.framework.base.utils;

import android.util.Log;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class UtilsJson {
    public static String toJsonString(Object obj) {
        return new Gson().toJson(obj);
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        return new Gson().fromJson(json, (Class) clazz);
    }

    public static Map<String, Object> toMap(String json) {
        return (Map) new Gson().fromJson(json, Map.class);
    }

    public static <T> T toObjectNoExp(String json, Class<T> clazz) {
        try {
            return toObject(json, clazz);
        } catch (Exception e) {
            Log.w("[Deserialize Error]", e);
            return null;
        }
    }

    public static <T> ArrayList<T> toArrayList(String json, Type type) {
        return (ArrayList) new Gson().fromJson(json, type);
    }
}
