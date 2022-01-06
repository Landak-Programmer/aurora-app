package com.mobile.auroraai.helper;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mobile.auroraai.BuildConfig;

import java.util.ArrayList;
import java.util.List;

public class JsonHelper {

    public static List<String> convertToList(final JsonArray jsonArray) {
        return convertToList(jsonArray, String.class);
    }

    // todo: simplify
    public static <T> List<T> convertToList(final JsonArray jsonArray, final Class<T> tClass) {
        final List<T> result = new ArrayList<>();
        if (!jsonArray.isEmpty()) {
            final JsonElement jsonElement = jsonArray.get(0);

            if (jsonElement.isJsonObject()) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    final JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                    result.add(convertToObject(jsonObject.toString(), tClass));
                }
                return result;
            } else if (jsonElement.isJsonPrimitive()) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    // fixme?
                    result.add((T) jsonArray.get(i).getAsString());
                }
                return result;
            } else if (jsonElement.isJsonArray()) {
                final JsonArray subArray = jsonElement.getAsJsonArray();
                return convertToList(subArray, tClass);
            }
        }
        return new ArrayList<>();
    }

    public static String[] convertToPrimitiveArray(final JsonArray jsonArray) {
        final String[] primitiveArray = new String[jsonArray.size()];
        return convertToList(jsonArray).toArray(primitiveArray);
    }

    private static <T> T convertToObject(final String body, final Class<T> tClass) {
        return new Gson().fromJson(body, tClass);
    }
}
