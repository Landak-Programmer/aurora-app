package com.example.auroraai.core;

import android.content.Context;
import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Properties;

public class PropertiesHelper {

    private static Properties properties = null;
    private static boolean pessimisticLock = false;

    public synchronized static void init(final Context context) {

        if (properties != null || pessimisticLock) {
            return;
        }

        lock();
        properties = new Properties();
        try {
            load(context);
        } catch (IOException e) {
            Log.e("TAG", "Unable to get resource from application.properties");
            // will be handle by the client
            throw new RuntimeException(String.format("Failed to load application.properties due to %s", e.getLocalizedMessage()));
        }
        unlock();

    }

    private static void load(final Context context) throws IOException {
        properties.load(context.getAssets().open("application.properties"));
    }


    private static void lock() {
        pessimisticLock = true;
    }

    private static void unlock() {
        pessimisticLock = false;
    }

    public static String getString(final String key) {
        final String v = properties.getProperty(key);
        if (StringUtils.isBlank(v)) {
            throw new RuntimeException("No such key " + key);
        }
        return v;
    }

    public static Integer getInteger(final String key) {
        // checking
        return Integer.parseInt(properties.getProperty(key));
    }
}
