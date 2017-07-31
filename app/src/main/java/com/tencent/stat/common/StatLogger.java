package com.tencent.stat.common;

import android.util.Log;
import com.tencent.stat.StatConfig;

public final class StatLogger {
    private boolean debugEnable = true;
    private int logLevel = 2;
    private String tag = "default";

    public StatLogger(String str) {
        this.tag = str;
    }

    private String getLoggerClassInfo() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace == null) {
            return null;
        }
        for (StackTraceElement stackTraceElement : stackTrace) {
            if (!stackTraceElement.isNativeMethod() && !stackTraceElement.getClassName().equals(Thread.class.getName()) && !stackTraceElement.getClassName().equals(getClass().getName())) {
                return "[" + Thread.currentThread().getName() + "(" + Thread.currentThread().getId() + "): " + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + "]";
            }
        }
        return null;
    }

    public void d(Object obj) {
        if (isDebugEnable()) {
            debug(obj);
        }
    }

    public void debug(Object obj) {
        if (this.logLevel <= 3) {
            String loggerClassInfo = getLoggerClassInfo();
            Log.d(this.tag, loggerClassInfo == null ? obj.toString() : loggerClassInfo + " - " + obj);
        }
    }

    public void e(Exception exception) {
        if (StatConfig.isDebugEnable()) {
            error(exception);
        }
    }

    public void e(Object obj) {
        if (isDebugEnable()) {
            error(obj);
        }
    }

    public void error(Exception exception) {
        if (this.logLevel <= 6) {
            StringBuffer stringBuffer = new StringBuffer();
            String loggerClassInfo = getLoggerClassInfo();
            StackTraceElement[] stackTrace = exception.getStackTrace();
            if (loggerClassInfo != null) {
                stringBuffer.append(loggerClassInfo + " - " + exception + "\r\n");
            } else {
                stringBuffer.append(exception + "\r\n");
            }
            if (stackTrace != null && stackTrace.length > 0) {
                for (StackTraceElement stackTraceElement : stackTrace) {
                    if (stackTraceElement != null) {
                        stringBuffer.append("[ " + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + " ]\r\n");
                    }
                }
            }
            Log.e(this.tag, stringBuffer.toString());
        }
    }

    public void error(Object obj) {
        if (this.logLevel <= 6) {
            String loggerClassInfo = getLoggerClassInfo();
            Log.e(this.tag, loggerClassInfo == null ? obj.toString() : loggerClassInfo + " - " + obj);
        }
    }

    public int getLogLevel() {
        return this.logLevel;
    }

    public void i(Object obj) {
        if (isDebugEnable()) {
            info(obj);
        }
    }

    public void info(Object obj) {
        if (this.logLevel <= 4) {
            String loggerClassInfo = getLoggerClassInfo();
            Log.i(this.tag, loggerClassInfo == null ? obj.toString() : loggerClassInfo + " - " + obj);
        }
    }

    public boolean isDebugEnable() {
        return this.debugEnable;
    }

    public void setDebugEnable(boolean z) {
        this.debugEnable = z;
    }

    public void setLogLevel(int i) {
        this.logLevel = i;
    }

    public void setTag(String str) {
        this.tag = str;
    }

    public void v(Object obj) {
        if (isDebugEnable()) {
            verbose(obj);
        }
    }

    public void verbose(Object obj) {
        if (this.logLevel <= 2) {
            String loggerClassInfo = getLoggerClassInfo();
            Log.v(this.tag, loggerClassInfo == null ? obj.toString() : loggerClassInfo + " - " + obj);
        }
    }

    public void w(Object obj) {
        if (isDebugEnable()) {
            warn(obj);
        }
    }

    public void warn(Object obj) {
        if (this.logLevel <= 5) {
            String loggerClassInfo = getLoggerClassInfo();
            Log.w(this.tag, loggerClassInfo == null ? obj.toString() : loggerClassInfo + " - " + obj);
        }
    }
}
