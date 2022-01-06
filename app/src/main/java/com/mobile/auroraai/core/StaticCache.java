package com.mobile.auroraai.core;

import java.util.HashMap;
import java.util.Map;

/**
 * DO NOT ABUSE THIS
 */
public class StaticCache {

    private static final Map<String, String> inMemoryCache = new HashMap<>();

    private static void put(final String key, final String value) {
        inMemoryCache.put(key, value);
    }

    private static String get(final String key) {
        return inMemoryCache.get(key);
    }

    private static void del(final String key) {
        inMemoryCache.remove(key);
    }
}
