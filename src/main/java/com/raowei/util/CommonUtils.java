package com.raowei.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
/**
 * @author terryrao
 * @version 2015-09-15
 * @since 1.0
 */
public class CommonUtils {

private List<List<Object>> parseJson(String value) {
        Type arrayType = new TypeToken<ArrayList<JsonArray>>() {
        }.getType();
        Type type = new TypeToken<ArrayList<JsonPrimitive>>() {
        }.getType();
        List<List<Object>> result = new ArrayList<>();
        ArrayList<JsonArray> jsonObjects = new Gson().fromJson(value, arrayType);
        for (JsonArray array : jsonObjects) {
            List<Object> rows = new ArrayList<>();
            ArrayList<JsonPrimitive> objects = new Gson().fromJson(array, type);
            for (JsonPrimitive object : objects) {
                rows.add(new Gson().fromJson(object, Object.class));
            }
            result.add(rows);
        }

        return result;
    }

}
