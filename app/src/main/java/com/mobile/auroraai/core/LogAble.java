package com.mobile.auroraai.core;

import android.util.Log;

public abstract class LogAble implements TagAble {

    enum Level {
        DEBUG,
        INFO,
        ERROR
    }

    protected void logDebug(final String message, final Throwable throwable) {
        processLog(Level.DEBUG, getClassTag(), message, throwable);
    }

    protected void logInfo(final String message, final Throwable throwable) {
        processLog(Level.INFO, getClassTag(), message, throwable);
    }

    protected void logError(final String message, final Throwable throwable) {
        processLog(Level.ERROR, getClassTag(), message, throwable);
    }

    public void logDebug(final String tag, final String message, final Throwable throwable) {
        processLog(Level.DEBUG, tag, message, throwable);
    }

    public void logInfo(final String tag, final String message, final Throwable throwable) {
        processLog(Level.INFO, tag, message, throwable);
    }

    public void logError(final String tag, final String message, final Throwable throwable) {
        processLog(Level.ERROR, tag, message, throwable);
    }

    private void processLog(final Level level, final String tag, final String message, final Throwable throwable) {
        switch (level) {
            case DEBUG:
                Log.d(tag, message, throwable);
                break;
            case INFO:
                Log.i(tag, message, throwable);
                break;
            case ERROR:
            default:
                Log.e(tag, message, throwable);
                break;
        }
    }
}
