package com.huluxia.utils;

import android.util.Log;

/* compiled from: LogCallerUtils */
public class g {
    public static final int DEBUG = 1;
    public static final int ERROR = 4;
    public static final int INFO = 2;
    private static final String TAG = "LogCallerUtils";
    public static final int VERBOSE = 0;
    public static final int WARN = 3;

    public static void eL(String msg) {
        N(msg, 0);
    }

    public static void N(String msg, int level) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StringBuilder sb = new StringBuilder();
        sb.append(msg + ", caller stack = [ ");
        for (StackTraceElement e : stackTraceElements) {
            sb.append(e.toString() + ", ");
        }
        String logs = sb.substring(0, sb.length() - 2) + " ]";
        switch (level) {
            case 1:
                Log.d(TAG, logs);
                return;
            case 2:
                Log.i(TAG, logs);
                return;
            case 3:
                Log.w(TAG, logs);
                return;
            case 4:
                Log.e(TAG, logs);
                return;
            default:
                Log.v(TAG, logs);
                return;
        }
    }
}
