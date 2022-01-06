package com.mobile.auroraai;

import com.mobile.auroraai.core.StaticLog;

public class GlobalHolder {

    private static boolean isInit = false;

    private static StaticLog staticLog;

    public static void init() {
        if (!isInit) {
            staticLog = new StaticLog();
        }
        isInit = true;
    }

    public static StaticLog getStaticLog() {
        return staticLog;
    }
}
