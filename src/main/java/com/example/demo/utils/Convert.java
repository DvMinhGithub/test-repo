package com.example.demo.utils;

import com.google.gson.Gson;

public class Convert {
    public static String convertToJson(Object object) {
        return new Gson().toJson(object);
    }

    public static <T> T convertJsonToObject(String jsonString, Class<T> tClass) {
        return new Gson().fromJson(jsonString, tClass);
    }
}
