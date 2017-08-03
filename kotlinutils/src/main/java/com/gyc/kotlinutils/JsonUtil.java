package com.gyc.kotlinutils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonUtil {

    public static <T> T jsonToBean(Class<T> classOfT, String json) {

        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

            return gson.fromJson(json, classOfT);

        } catch (Exception e) {
            Log.e("JsonUtil", e.toString(), e);
        }

        return null;
    }

    public static String beanToJson(Object obj) {
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

            return gson.toJson(obj);

        } catch (Exception e) {
            Log.e("JsonUtil", e.toString(), e);
        }

        return null;
    }

    /**
     * 将Map转化为Json
     *
     * @param map
     * @return String
     */
    public static String mapToJson(Map<String, Object> map) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(map);
        return jsonStr;
    }

    /**
     * 函数名称: parseData
     * 函数描述: 将json字符串转换为map
     *
     * @param data
     * @return
     */
    public static Map<String, Object> strToMap(String data) {
        GsonBuilder gb = new GsonBuilder();
        Gson g = gb.create();
        Map<String, Object> map = g.fromJson(data, new TypeToken<Map<String, Object>>() {
        }.getType());
        return map;
    }

    /***
     * List<Map>转json
     * @param list
     * @return
     */
    public static String listToJson(List<Map<String, Object>> list) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(list);
        return jsonStr;
    }

    /***
     * json字串转List<Map>
     * @param data
     * @return
     */
    public static List<Map<String, Object>> strToListMap(String data) {
        GsonBuilder gb = new GsonBuilder();
        Gson g = gb.create();
        List<Map<String, Object>> list = g.fromJson(data, new TypeToken<List<Map<String, Object>>>() {
        }.getType());
        return list;
    }

    public static <T> ArrayList<T> fromJsonList(String json, Class<T> cls) {
        GsonBuilder gb = new GsonBuilder();
        Gson g = gb.create();
        ArrayList<T> mList = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            mList.add(g.fromJson(elem, cls));
        }
        return mList;
    }

}
