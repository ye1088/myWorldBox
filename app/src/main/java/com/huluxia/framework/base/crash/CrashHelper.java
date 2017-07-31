package com.huluxia.framework.base.crash;

import com.huluxia.framework.base.log.LogToES;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class CrashHelper {
    public static String getThreadStack(Throwable ex) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        Throwable cause = ex;
        if (cause != null) {
            cause.printStackTrace(printWriter);
        }
        String stackTrace = result.toString();
        printWriter.close();
        return stackTrace.trim();
    }

    public static void writeLogToFile(String fileName, String crash) throws Exception {
        LogToES.writeLogToFile(LogToES.getLogPath(), fileName, crash, true, System.currentTimeMillis());
    }
}
