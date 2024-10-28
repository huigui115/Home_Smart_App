package com.example.home_smart.util;

import com.example.home_smart.entity.Light;
import com.example.home_smart.entity.Lock;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class JsonUtil {
    public static Lock parseJsonToLock(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, Lock.class);
    }
    public static List<Lock> parseJsonToLockList(String jsonString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Lock>>() {}.getType();
        return gson.fromJson(jsonString, listType);
    }
    public static Light parseJsonToLight(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, Light.class);
    }
    public static List<Light> parseJsonToLightList(String jsonString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Light>>() {}.getType();
        return gson.fromJson(jsonString, listType);
    }
}
