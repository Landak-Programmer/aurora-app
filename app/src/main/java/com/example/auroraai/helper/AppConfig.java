package com.example.auroraai.helper;

import com.example.auroraai.core.PropertiesHelper;

public class AppConfig {

    public static String getMode() {
        return PropertiesHelper.getString("app.mode");
    }

    public static String getURL() {
        return String.format("%s.%s", getMode(), PropertiesHelper.getString("base.url"));
    }

    public static String getToken() {
        return String.format("%s.%s", getMode(), PropertiesHelper.getString("backend.token"));
    }
}
