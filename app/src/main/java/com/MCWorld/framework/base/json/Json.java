package com.MCWorld.framework.base.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Json {
    private static Gson gson = new GsonBuilder().create();

    public static <T> T parseJsonObject(String json, Class<T> clz) {
        return gson.fromJson(json, (Class) clz);
    }

    public static <T> T[] parseJsonArray(String json, Class<T> cls) {
        return (Object[]) gson.fromJson(json, new 1().getType());
    }

    public static <K, V> Map<K, V> parseJsonMap(String json, Class<K> cls, Class<V> cls2) {
        return (Map) gson.fromJson(json, new 2().getType());
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> List<T> parseJsonList(String json, Class<T> clz) throws Exception {
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        List<T> data = new ArrayList();
        Iterator it = array.iterator();
        while (it.hasNext()) {
            data.add(gson.fromJson((JsonElement) it.next(), (Class) clz));
        }
        return data;
    }

    public static <T> List<T> parseJsonList(String json, Type type) throws Exception {
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        List<T> data = new ArrayList();
        Iterator it = array.iterator();
        while (it.hasNext()) {
            data.add(gson.fromJson((JsonElement) it.next(), type));
        }
        return data;
    }

    public static <T> T parseJsonObject(String json, Type type) {
        return gson.fromJson(json, type);
    }

    public static Gson getGson() {
        return gson;
    }
}
