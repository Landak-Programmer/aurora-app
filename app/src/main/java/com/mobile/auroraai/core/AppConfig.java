package com.mobile.auroraai.core;

import com.mobile.auroraai.core.PropertiesHelper;

public class AppConfig {

    public static String getMode() {
        return PropertiesHelper.getString("app.mode");
    }

    public static String getURL() {
        return PropertiesHelper.getString(String.format("%s.%s", getMode(), "base.backend.url"));
    }

    public static String getToken() {
        return PropertiesHelper.getString(String.format("%s.%s", getMode(), "backend.token"));
    }

    public static boolean getGlobalServiceEnabled() {
        return Boolean.parseBoolean(PropertiesHelper.getString(String.format("%s.%s", getMode(), "app.global.background.service.enabled")));
    }

    public static boolean getSmsListenerServiceEnabled() {
        return Boolean.parseBoolean(PropertiesHelper.getString(String.format("%s.%s", getMode(), "app.background.service.sms.listener")));
    }

    public static boolean getAccessibilityListenerServiceEnabled() {
        return Boolean.parseBoolean(PropertiesHelper.getString(String.format("%s.%s", getMode(), "app.background.service.accessibility.listener")));
    }
}
