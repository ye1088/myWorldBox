package com.huluxia.framework.base.log;

import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import com.huluxia.framework.AppConstant;
import com.huluxia.framework.FrameworkPref;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.tencent.mm.sdk.platformtools.SpecilApiUtil;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.IllegalFormatException;
import java.util.Map;

public class HLog {
    public static String MAIN_THEAD_NAME = "MainThread";
    private static final Handler sHandler = new Handler(sHandlerThread.getLooper());
    private static final HandlerThread sHandlerThread = new HandlerThread("LogThread", 10);
    private static volatile LogOptions sOptions = new LogOptions();

    public static class LogOptions {
        public static final int LEVEL_DEBUG = 2;
        public static final int LEVEL_ERROR = 5;
        public static final int LEVEL_INFO = 3;
        public static final int LEVEL_VERBOSE = 1;
        public static final int LEVEL_WARN = 4;
        public int backUpLogLimitInMB = 10;
        public int buffSizeInBytes = 32768;
        public boolean honorVerbose = false;
        public String logFileName = AppConstant.LOG_FILE;
        public int logLevel = 1;
        public String stackTraceFilterKeyword;
        public String uniformTag;
    }

    static {
        sHandlerThread.start();
    }

    public static boolean initialize(String directory) {
        return LogToES.setLogPath(directory);
    }

    public static LogOutputPaths getLogOutputPaths() {
        LogOutputPaths ret = new LogOutputPaths();
        if (!getLogOutputPaths(ret)) {
            error("HLog", "failed to get log output paths.", new Object[0]);
        }
        return ret;
    }

    public static boolean getLogOutputPaths(LogOutputPaths out) {
        return LogToES.getLogOutputPaths(out, sOptions.logFileName);
    }

    public static boolean initialize(String directory, LogOptions options) {
        setOptions(options);
        return LogToES.setLogPath(directory);
    }

    public static void setUniformTag(String tag) {
        if (tag != null && tag.length() != 0) {
            sOptions.uniformTag = tag;
        }
    }

    public static String getLogPath() {
        return LogToES.getLogPath();
    }

    public static LogOptions getOptions() {
        return sOptions;
    }

    private static boolean setOptions(LogOptions options) {
        LogOptions tmpOp;
        if (options == null) {
            tmpOp = new LogOptions();
        } else {
            tmpOp = options;
        }
        sOptions = tmpOp;
        LogToES.setBackupLogLimitInMB(tmpOp.backUpLogLimitInMB);
        LogToES.setBuffSize(tmpOp.buffSizeInBytes);
        return tmpOp.buffSizeInBytes > 0 && !isNullOrEmpty(tmpOp.logFileName);
    }

    public static void verbose(Object obj, String format, Object... args) {
        boolean shouldOutputVerboseToDDMS = shouldOutputVerboseToDDMS();
        boolean shouldOutputVerboseToFile = shouldOutputVerboseToFile();
        if (shouldOutputVerboseToDDMS || shouldOutputVerboseToFile) {
            try {
                outputVerbose(obj, getCallerLineNumber(), getCallerFilename(), format, getThreadName(), shouldOutputVerboseToDDMS, shouldOutputVerboseToFile, args);
            } catch (IllegalFormatException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getThreadName() {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            return String.valueOf(Thread.currentThread().getId());
        }
        return MAIN_THEAD_NAME;
    }

    public static void verboseWithoutLineNumber(Object obj, String format, Object... args) {
        boolean shouldOutputVerboseToDDMS = shouldOutputVerboseToDDMS();
        boolean shouldOutputVerboseToFile = shouldOutputVerboseToFile();
        if (shouldOutputVerboseToDDMS || shouldOutputVerboseToFile) {
            try {
                outputVerbose(obj, format, shouldOutputVerboseToDDMS, shouldOutputVerboseToFile, args);
            } catch (IllegalFormatException e) {
                e.printStackTrace();
            }
        }
    }

    public static void debug(Object obj, String format, Object... args) {
        Object obj2 = obj;
        String str = format;
        outputDebug(obj2, str, getThreadName(), getCallerLineNumber(), getCallerFilename(), args);
    }

    public static void debugWithoutLineNumber(Object obj, String format, Object... args) {
        outputDebug(obj, format, args);
    }

    public static void info(Object obj, String format, Object... args) {
        if (shouldWriteInfo()) {
            try {
                Object obj2 = obj;
                String str = format;
                outputInfo(obj2, str, getThreadName(), getCallerLineNumber(), getCallerFilename(), args);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public static void infoWithoutLineNumber(Object obj, String format, Object... args) {
        if (shouldWriteInfo()) {
            try {
                outputInfo(obj, format, args);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public static void warn(Object obj, String format, Object... args) {
        if (shouldWriteWarn()) {
            try {
                Object obj2 = obj;
                String str = format;
                outputWarning(obj2, str, getThreadName(), getCallerLineNumber(), getCallerFilename(), args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void warnWithoutLineNumber(Object obj, String format, Object... args) {
        if (shouldWriteWarn()) {
            try {
                outputWarning(obj, format, args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void error(Object obj, String format, Object... args) {
        if (shouldWriteError()) {
            try {
                Object obj2 = obj;
                String str = format;
                outputError(obj2, str, getThreadName(), getCallerLineNumber(), getCallerFilename(), args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void errorWithoutLineNumber(Object obj, String format, Object... args) {
        if (shouldWriteError()) {
            try {
                outputError(obj, format, args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void error(Object obj, String format, Throwable t, Object... args) {
        error(obj, format + '\n' + stackTraceOf(t), args);
    }

    public static void errorWithoutLineNumber(Object obj, String format, Throwable t, Object... args) {
        errorWithoutLineNumber(obj, format + '\n' + stackTraceOf(t), args);
    }

    public static void error(Object obj, Throwable t) {
        if (shouldWriteError()) {
            outputError(obj, t, getCallerLineNumber(), getCallerFilename(), getCallerMethodName());
        }
    }

    public static void errorWithoutLineNumber(Object obj, Throwable t) {
        if (shouldWriteError()) {
            outputError(obj, t, getCallerMethodName());
        }
    }

    public static void flush() {
        executeCommand(new 1());
    }

    public static void close() {
        executeCommand(new 2());
    }

    private static void executeCommand(Runnable command) {
        sHandler.post(command);
    }

    private static String objClassName(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        return obj.getClass().getSimpleName();
    }

    private static void writeToLog(String logText) {
        long timeMillis = System.currentTimeMillis();
        if (externalStorageExist()) {
            try {
                LogToES.writeLogToFile(LogToES.getLogPath(), sOptions.logFileName, logText, false, timeMillis);
            } catch (Throwable e) {
                Log.e("HLog", "writeToLog fail, " + e);
            }
        }
    }

    private static void logToFile(String logText, Throwable t) {
        executeCommand(new 3(logText, t));
    }

    private static String msgForException(Object obj, String methodname, String filename, int line) {
        StringBuilder sb = new StringBuilder();
        if (obj instanceof String) {
            sb.append((String) obj);
        } else {
            sb.append(obj.getClass().getSimpleName());
        }
        sb.append(" Exception occurs at ");
        sb.append("(P:");
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

    private static String msgForTextLog(int logLevel, Object obj, String filename, int line, String thread, String msg) {
        StringBuilder sb = new StringBuilder();
        switch (logLevel) {
            case 1:
                sb.append("V/: ");
                break;
            case 2:
                sb.append("D/: ");
                break;
            case 3:
                sb.append("I/: ");
                break;
            case 4:
                sb.append("W/: ");
                break;
            case 5:
                sb.append("E/: ");
                break;
            default:
                sb.append("I/: ");
                break;
        }
        sb.append("[");
        sb.append(objClassName(obj));
        sb.append("]");
        sb.append(msg);
        sb.append("(P:");
        sb.append(Process.myPid());
        sb.append(")");
        sb.append("(T:");
        if (thread != null) {
            sb.append(thread);
        } else if (Looper.getMainLooper() == Looper.myLooper()) {
            sb.append("Main");
        } else {
            sb.append(Thread.currentThread().getId());
        }
        sb.append(")");
        sb.append("at (");
        sb.append(filename);
        sb.append(":");
        sb.append(line);
        sb.append(")");
        return sb.toString();
    }

    private static int getCallerLineNumber() {
        int i = 0;
        try {
            return Thread.currentThread().getStackTrace()[4].getLineNumber();
        } catch (Throwable throwable) {
            error("HLog", "getCallerLineNumber " + throwable, new Object[i]);
            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
            if (stack == null || stack.length <= 0) {
                return i;
            }
            return stack[stack.length - 1].getLineNumber();
        }
    }

    private static String getCallerFilename() {
        try {
            return Thread.currentThread().getStackTrace()[4].getFileName();
        } catch (Throwable throwable) {
            error("HLog", "getCallerFilename " + throwable, new Object[0]);
            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
            if (stack == null || stack.length <= 0) {
                return "";
            }
            return stack[stack.length - 1].getFileName();
        }
    }

    private static String getCallerMethodName() {
        try {
            return Thread.currentThread().getStackTrace()[4].getMethodName();
        } catch (Throwable throwable) {
            error("HLog", "getCallerMethodName " + throwable, new Object[0]);
            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
            if (stack == null || stack.length <= 0) {
                return "";
            }
            return stack[stack.length - 1].getMethodName();
        }
    }

    private static String getThreadStacksKeyword() {
        return sOptions.stackTraceFilterKeyword;
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
        printStackTraces(traces, tag, getThreadStacksKeyword(), isNullOrEmpty(sOptions.stackTraceFilterKeyword), false);
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
        return TextUtils.join(SpecilApiUtil.LINE_SEP, Thread.currentThread().getStackTrace());
    }

    private static String tag(Object tag) {
        LogOptions options = sOptions;
        if (options.uniformTag != null) {
            return options.uniformTag;
        }
        if (tag instanceof String) {
            return (String) tag;
        }
        return tag.getClass().getSimpleName();
    }

    private static String tagOfStack() {
        return sOptions.uniformTag == null ? "CallStack" : sOptions.uniformTag;
    }

    private static boolean shouldOutputVerboseToDDMS() {
        return sOptions.logLevel <= 2;
    }

    private static boolean shouldOutputVerboseToFile() {
        return sOptions.logLevel <= 2 && sOptions.honorVerbose;
    }

    private static boolean shouldOutputDebugToDDMS() {
        return sOptions.logLevel <= 2;
    }

    private static boolean shouldOutputDebugToFile() {
        return sOptions.logLevel <= 2 && sOptions.honorVerbose;
    }

    private static boolean shouldWriteDebug() {
        return sOptions.logLevel <= 2;
    }

    private static boolean shouldWriteInfo() {
        return sOptions.logLevel <= 3;
    }

    private static boolean shouldWriteWarn() {
        return sOptions.logLevel <= 4;
    }

    private static boolean shouldWriteError() {
        return sOptions.logLevel <= 5;
    }

    private static boolean externalStorageExist() {
        boolean isExternalStorageExist = false;
        try {
            isExternalStorageExist = Environment.getExternalStorageState().equalsIgnoreCase("mounted");
        } catch (Exception e) {
            Log.e("HLog", e.toString());
        }
        return isExternalStorageExist;
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.length() == 0;
    }

    private static void outputVerbose(Object obj, String format, boolean outToDDMS, boolean outToFile, Object... args) {
        executeCommand(new 4(args, format, outToDDMS, obj, outToFile));
    }

    private static void outputVerbose(Object obj, int line, String filename, String format, String thread, boolean outToDDMS, boolean outToFile, Object... args) {
        executeCommand(new 5(outToDDMS, args, format, obj, filename, line, thread, outToFile));
    }

    private static void outputDebug(Object obj, String format, Object... args) {
        executeCommand(new 6(args, format, obj));
    }

    private static void outputDebug(Object obj, String format, String thread, int line, String filename, Object... args) {
        executeCommand(new 7(args, format, obj, filename, line, thread));
    }

    private static void outputInfo(Object obj, String format, Object... args) {
        executeCommand(new 8(args, format, obj));
    }

    private static void outputInfo(Object obj, String format, String thread, int line, String filename, Object... args) {
        executeCommand(new 9(args, format, obj, filename, line, thread));
    }

    private static void outputWarning(Object obj, String format, Object... args) {
        executeCommand(new 10(args, format, obj));
    }

    private static void outputWarning(Object obj, String format, String theadName, int line, String filename, Object... args) {
        executeCommand(new 11(args, format, obj, filename, line, theadName));
    }

    private static void outputError(Object obj, String format, Object... args) {
        executeCommand(new 12(args, format, obj));
    }

    private static void outputError(Object obj, String format, String threadName, int line, String filename, Object... args) {
        executeCommand(new 13(args, format, obj, filename, line, threadName));
    }

    private static void outputError(Object obj, Throwable t, String methodname) {
        executeCommand(new 14(obj, t));
    }

    private static void outputError(Object obj, Throwable t, int line, String filename, String methodname) {
        executeCommand(new 15(obj, methodname, filename, line, t));
    }

    public static <T> int getLogCollectionSize(Collection<T> infos) {
        return UtilsFunction.empty((Collection) infos) ? 0 : infos.size();
    }

    public static <T, V> int getLogMapSize(Map<T, V> infos) {
        return UtilsFunction.empty((Map) infos) ? 0 : infos.size();
    }

    public static void logStack(Object obj, String msg) {
        logStack(obj, msg, 1);
    }

    public static void logStack(Object obj, String msg, int level) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StringBuilder sb = new StringBuilder();
        sb.append(msg + ", caller stack = [ \n");
        for (StackTraceElement e : stackTraceElements) {
            sb.append(e.toString() + SpecilApiUtil.LINE_SEP);
        }
        String logs = sb.substring(0, sb.length() - 1) + " ]\n";
        switch (level) {
            case 2:
                debug(tag(obj), logs, new Object[0]);
                return;
            case 3:
                info(tag(obj), logs, new Object[0]);
                return;
            case 4:
                warn(tag(obj), logs, new Object[0]);
                return;
            case 5:
                error(tag(obj), logs, new Object[0]);
                return;
            default:
                verbose(tag(obj), logs, new Object[0]);
                return;
        }
    }

    public static boolean isImageLoggable(int level) {
        if (FrameworkPref.getInstance().getBoolean("image_lab", false) || level >= 4) {
            return true;
        }
        return false;
    }
}
