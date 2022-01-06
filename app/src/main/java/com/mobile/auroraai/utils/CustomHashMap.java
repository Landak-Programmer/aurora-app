package com.mobile.auroraai.utils;

import com.mobile.auroraai.exception.ValueNotExist;

import java.util.HashMap;
import java.util.Map;

public class CustomHashMap<K, V> extends HashMap<K, V> {

    public K getKeyUsingValue(final V value) {
        if (super.containsValue(value)) {
            for (final Map.Entry<K, V> entry : this.entrySet()) {
                if (entry.getValue().equals(value)) {
                    return entry.getKey();
                }
            }
        }
        // todo: too unforgiving?
        throw new ValueNotExist(value.toString());
    }
}
