package com.MCWorld.mcsdk.log;

import android.os.Environment;
import android.os.Process;
import android.util.Log;
import com.tencent.mm.sdk.platformtools.SpecilApiUtil;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: DTDebug */
public class a {
    private static final boolean DEBUG = false;
    public static final String aoP = "DTError";
    private static volatile a aoQ = new a();
    private static final ExecutorService aoR = Executors.newSingleThreadExecutor();

    public static void d(Object... msgs) {
    }

    public static void e(Object... msgs) {
    }

    public static void a(Exception... es) {
    }

    public static int K(String tag, String msg) {
        return 0;
    }

    public static int e(String tag, String msg, Throwable tr) {
        return 0;
    }

    public static boolean initialize(String directory) {
        return c.setLogPath(directory);
    }

    public static boolean a(String directory, a options) {
        a(options);
        return c.setLogPath(directory);
    }

    public static void setUniformTag(String tag) {
        if (tag != null && tag.length() != 0) {
            aoQ.uniformTag = tag;
        }
    }

    public static String getLogPath() {
        return c.getLogPath();
    }

    public static a De() {
        return aoQ;
    }

    private static boolean a(a options) {
        a tmpOp;
        if (options == null) {
            tmpOp = new a();
        } else {
            tmpOp = options;
        }
        aoQ = tmpOp;
        c.setBackupLogLimitInMB(tmpOp.backUpLogLimitInMB);
        c.setBuffSize(tmpOp.buffSizeInBytes);
        return tmpOp.buffSizeInBytes > 0 && !isNullOrEmpty(tmpOp.logFileName);
    }

    public static void verbose(Object obj, String format, Object... args) {
    }

    public static void debug(Object obj, String format, Object... args) {
        if (shouldWriteDebug()) {
            a(obj, format, getCallerLineNumber(), getCallerFilename(), args);
        }
    }

    public static void info(Object obj, String format, Object... args) {
        if (shouldWriteInfo()) {
            try {
                b(obj, format, getCallerLineNumber(), getCallerFilename(), args);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public static void warn(Object obj, String format, Object... args) {
        if (shouldWriteWarn()) {
            try {
                c(obj, format, getCallerLineNumber(), getCallerFilename(), args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void error(Object obj, String format, Object... args) {
        if (shouldWriteError()) {
            try {
                d(obj, format, getCallerLineNumber(), getCallerFilename(), args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void error(Object obj, Throwable t) {
        if (shouldWriteError()) {
            outputError(obj, t, getCallerLineNumber(), getCallerFilename(), getCallerMethodName());
        }
    }

    public static void flush() {
        executeCommand(new 1());
    }

    public static void close() {
        executeCommand(new 2());
    }

    public static boolean isOpen() {
        return (aoR.isShutdown() || aoR.isTerminated() || !c.isOpen()) ? false : true;
    }

    private static void executeCommand(Runnable command) {
        aoR.execute(command);
    }

    private static String objClassName(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        return obj.getClass().getSimpleName();
    }

    private static void writeToLog(String logText) {
        executeCommand(new 3(logText, System.currentTimeMillis()));
    }

    private static void logToFile(String logText, Throwable t) {
        StringWriter sw = new StringWriter();
        sw.write(logText);
        sw.write(SpecilApiUtil.LINE_SEP);
        t.printStackTrace(new PrintWriter(sw));
        writeToLog(sw.toString());
    }

    private static String msgForException(Object obj, String methodname, String filename, int line) {
        StringBuilder sb = new StringBuilder();
        if (obj instanceof String) {
            sb.append((String) obj);
        } else {
            sb.append(obj.getClass().getSimpleName());
        }
        sb.append(" Exception occurs at ");
        sb.append("(get_config_sp_intVal:");
        sb.append(Process.myPid());
        sb.append(")");
        sb.append("(T:");
        sb.append(Thread.currentThread().getId());
        sb.append(") at ");
        sb.append(methodname);
        sb.append(" (");
        sb.append(filename);
        sb.append(":" + line);
        sb.append(")");
        return sb.toString();
    }

    private static String a(Object obj, String filename, int line, String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append(msg);
        sb.append("(get_config_sp_intVal:");
        sb.append(Process.myPid());
        sb.append(")");
        sb.append("(T:");
        sb.append(Thread.currentThread().getId());
        sb.append(")");
        sb.append("(C:");
        sb.append(objClassName(obj));
        sb.append(")");
        sb.append("at (");
        sb.append(filename);
        sb.append(":");
        sb.append(line);
        sb.append(")");
        return sb.toString();
    }

    private static int getCallerLineNumber() {
        return Thread.currentThread().getStackTrace()[4].getLineNumber();
    }

    private static String getCallerFilename() {
        return Thread.currentThread().getStackTrace()[4].getFileName();
    }

    private static String getCallerMethodName() {
        return Thread.currentThread().getStackTrace()[4].getMethodName();
    }

    private static String getThreadStacksKeyword() {
        return aoQ.stackTraceFilterKeyword;
    }

    public static void printThreadStacks() {
        printThreadStacks(tagOfStack(), getThreadStacksKeyword(), false, false);
    }

    public static void printThreadStacks(String tag) {
        printThreadStacks(tag, getThreadStacksKeyword(), isNullOrEmpty(getThreadStacksKeyword()), false);
    }

    public static void printThreadStacks(Throwable e, String tag) {
        printStackTraces(e.getStackTrace(), tag);
    }

    public static void printThreadStacks(String tag, String keyword) {
        printThreadStacks(tag, keyword, false, false);
    }

    public static void printThreadStacks(String tag, String keyword, boolean fullLog, boolean release) {
        printStackTraces(Thread.currentThread().getStackTrace(), tag, keyword, fullLog, release);
    }

    public static void printStackTraces(StackTraceElement[] traces, String tag) {
        printStackTraces(traces, tag, getThreadStacksKeyword(), isNullOrEmpty(aoQ.stackTraceFilterKeyword), false);
    }

    private static void printStackTraces(StackTraceElement[] traces, String tag, String keyword, boolean fullLog, boolean release) {
        printLog(tag, "------------------------------------", release);
        for (StackTraceElement e : traces) {
            String info = e.toString();
            if (fullLog || !(isNullOrEmpty(keyword) || info.indexOf(keyword) == -1)) {
                printLog(tag, info, release);
            }
        }
        printLog(tag, "------------------------------------", release);
    }

    private static void printLog(String tag, String log, boolean release) {
        if (release) {
            info(tag, log, new Object[0]);
        } else {
            debug(tag, log, new Object[0]);
        }
    }

    public static String stackTraceOf(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    public static String stackTrace() {
        try {
            throw new RuntimeException();
        } catch (Exception e) {
            return stackTraceOf(e);
        }
    }

    private static String tag(Object tag) {
        a options = aoQ;
        if (options.uniformTag != null) {
            return options.uniformTag;
        }
        if (tag instanceof String) {
            return (String) tag;
        }
        return tag.getClass().getSimpleName();
    }

    private static String tagOfStack() {
        return aoQ.uniformTag == null ? "CallStack" : aoQ.uniformTag;
    }

    private static boolean shouldOutputVerboseToDDMS() {
        return aoQ.logLevel <= 1;
    }

    private static boolean shouldOutputVerboseToFile() {
        return aoQ.logLevel <= 1 && aoQ.honorVerbose;
    }

    private static boolean shouldWriteDebug() {
        return aoQ.logLevel <= 2;
    }

    private static boolean shouldWriteInfo() {
        return aoQ.logLevel <= 3;
    }

    private static boolean shouldWriteWarn() {
        return aoQ.logLevel <= 4;
    }

    private static boolean shouldWriteError() {
        return aoQ.logLevel <= 5;
    }

    private static boolean externalStorageExist() {
        return Environment.getExternalStorageState().equalsIgnoreCase("mounted");
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.length() == 0;
    }

    private static void a(Object obj, int line, String filename, String format, boolean outToDDMS, boolean outToFile, Object... args) {
        try {
            String logText = a(obj, filename, line, String.format(format, args));
            if (outToDDMS) {
                Log.v(tag(obj), logText);
            }
            if (outToFile) {
                writeToLog(logText);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static void a(Object obj, String format, int line, String filename, Object... args) {
        try {
            String logText = a(obj, filename, line, String.format(format, args));
            Log.d(tag(obj), logText);
            writeToLog(logText);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static void b(Object obj, String format, int line, String filename, Object... args) {
        try {
            String logText = a(obj, filename, line, String.format(format, args));
            Log.i(tag(obj), logText);
            writeToLog(logText);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static void c(Object obj, String format, int line, String filename, Object... args) {
        try {
            String logText = a(obj, filename, line, String.format(format, args));
            Log.w(tag(obj), logText);
            writeToLog(logText);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static void d(Object obj, String format, int line, String filename, Object... args) {
        try {
            String logText = a(obj, filename, line, String.format(format, args));
            if (args.length <= 0 || !(args[args.length - 1] instanceof Throwable)) {
                Log.e(tag(obj), logText);
                writeToLog(logText);
                return;
            }
            Throwable t = args[args.length - 1];
            Log.e(tag(obj), logText, t);
            logToFile(logText, t);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static void outputError(Object obj, Throwable t, int line, String filename, String methodname) {
        try {
            String logText = msgForException(obj, methodname, filename, line);
            Log.e(tag(obj), logText, t);
            logToFile(logText, t);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
