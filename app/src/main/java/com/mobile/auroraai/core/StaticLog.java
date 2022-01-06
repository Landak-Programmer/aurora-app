package com.mobile.auroraai.core;

public class StaticLog extends LogAble {

    @Override
    public String getClassTag() {
        throw new UnsupportedOperationException("Static log required tag to be initialize explicitly");
    }

    @Override
    protected void logError(final String message, final Throwable throwable) {
        throw new UnsupportedOperationException("Static log required tag to be initialize explicitly");
    }

    @Override
    protected void logInfo(final String message, final Throwable throwable) {
        throw new UnsupportedOperationException("Static log required tag to be initialize explicitly");
    }

    @Override
    protected void logDebug(final String message, final Throwable throwable) {
        throw new UnsupportedOperationException("Static log required tag to be initialize explicitly");
    }
}
