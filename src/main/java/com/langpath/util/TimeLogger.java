package com.langpath.util;

/**
 * Created by sjakowski on 2016-03-21.
 */
public interface TimeLogger {

    void logTime(String method, int count, long time);

    default StringBuilder prepareLog(final String method, final int count, final long time) {
        StringBuilder value = new StringBuilder();
        value.append(method);
        value.append(" ");
        value.append(count);
        value.append(" ");
        value.append(time);
        value.append("\n");
        return value;
    }

}
