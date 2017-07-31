package com.j256.ormlite.logger;

import com.j256.ormlite.logger.Log.Level;
import java.lang.reflect.Array;

public class Logger {
    private static final String ARG_STRING = "{}";
    private static final int DEFAULT_FULL_MESSAGE_LENGTH = 128;
    private static final Object UNKNOWN_ARG = new Object();
    private final Log log;

    public Logger(Log log) {
        this.log = log;
    }

    public boolean isLevelEnabled(Level level) {
        return this.log.isLevelEnabled(level);
    }

    public void trace(String msg) {
        logIfEnabled(Level.TRACE, null, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void trace(String msg, Object arg0) {
        logIfEnabled(Level.TRACE, null, msg, arg0, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void trace(String msg, Object arg0, Object arg1) {
        logIfEnabled(Level.TRACE, null, msg, arg0, arg1, UNKNOWN_ARG, null);
    }

    public void trace(String msg, Object arg0, Object arg1, Object arg2) {
        logIfEnabled(Level.TRACE, null, msg, arg0, arg1, arg2, null);
    }

    public void trace(String msg, Object[] argArray) {
        logIfEnabled(Level.TRACE, null, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, argArray);
    }

    public void trace(Throwable throwable, String msg) {
        logIfEnabled(Level.TRACE, throwable, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void trace(Throwable throwable, String msg, Object arg0) {
        logIfEnabled(Level.TRACE, throwable, msg, arg0, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void trace(Throwable throwable, String msg, Object arg0, Object arg1) {
        logIfEnabled(Level.TRACE, throwable, msg, arg0, arg1, UNKNOWN_ARG, null);
    }

    public void trace(Throwable throwable, String msg, Object arg0, Object arg1, Object arg2) {
        logIfEnabled(Level.TRACE, throwable, msg, arg0, arg1, arg2, null);
    }

    public void trace(Throwable throwable, String msg, Object[] argArray) {
        logIfEnabled(Level.TRACE, throwable, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, argArray);
    }

    public void debug(String msg) {
        logIfEnabled(Level.DEBUG, null, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void debug(String msg, Object arg0) {
        logIfEnabled(Level.DEBUG, null, msg, arg0, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void debug(String msg, Object arg0, Object arg1) {
        logIfEnabled(Level.DEBUG, null, msg, arg0, arg1, UNKNOWN_ARG, null);
    }

    public void debug(String msg, Object arg0, Object arg1, Object arg2) {
        logIfEnabled(Level.DEBUG, null, msg, arg0, arg1, arg2, null);
    }

    public void debug(String msg, Object[] argArray) {
        logIfEnabled(Level.DEBUG, null, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, argArray);
    }

    public void debug(Throwable throwable, String msg) {
        logIfEnabled(Level.DEBUG, throwable, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void debug(Throwable throwable, String msg, Object arg0) {
        logIfEnabled(Level.DEBUG, throwable, msg, arg0, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void debug(Throwable throwable, String msg, Object arg0, Object arg1) {
        logIfEnabled(Level.DEBUG, throwable, msg, arg0, arg1, UNKNOWN_ARG, null);
    }

    public void debug(Throwable throwable, String msg, Object arg0, Object arg1, Object arg2) {
        logIfEnabled(Level.DEBUG, throwable, msg, arg0, arg1, arg2, null);
    }

    public void debug(Throwable throwable, String msg, Object[] argArray) {
        logIfEnabled(Level.DEBUG, throwable, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, argArray);
    }

    public void info(String msg) {
        logIfEnabled(Level.INFO, null, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void info(String msg, Object arg0) {
        logIfEnabled(Level.INFO, null, msg, arg0, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void info(String msg, Object arg0, Object arg1) {
        logIfEnabled(Level.INFO, null, msg, arg0, arg1, UNKNOWN_ARG, null);
    }

    public void info(String msg, Object arg0, Object arg1, Object arg2) {
        logIfEnabled(Level.INFO, null, msg, arg0, arg1, arg2, null);
    }

    public void info(String msg, Object[] argArray) {
        logIfEnabled(Level.INFO, null, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, argArray);
    }

    public void info(Throwable throwable, String msg) {
        logIfEnabled(Level.INFO, throwable, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void info(Throwable throwable, String msg, Object arg0) {
        logIfEnabled(Level.INFO, throwable, msg, arg0, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void info(Throwable throwable, String msg, Object arg0, Object arg1) {
        logIfEnabled(Level.INFO, throwable, msg, arg0, arg1, UNKNOWN_ARG, null);
    }

    public void info(Throwable throwable, String msg, Object arg0, Object arg1, Object arg2) {
        logIfEnabled(Level.INFO, throwable, msg, arg0, arg1, arg2, null);
    }

    public void info(Throwable throwable, String msg, Object[] argArray) {
        logIfEnabled(Level.INFO, throwable, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, argArray);
    }

    public void warn(String msg) {
        logIfEnabled(Level.WARNING, null, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void warn(String msg, Object arg0) {
        logIfEnabled(Level.WARNING, null, msg, arg0, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void warn(String msg, Object arg0, Object arg1) {
        logIfEnabled(Level.WARNING, null, msg, arg0, arg1, UNKNOWN_ARG, null);
    }

    public void warn(String msg, Object arg0, Object arg1, Object arg2) {
        logIfEnabled(Level.WARNING, null, msg, arg0, arg1, arg2, null);
    }

    public void warn(String msg, Object[] argArray) {
        logIfEnabled(Level.WARNING, null, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, argArray);
    }

    public void warn(Throwable throwable, String msg) {
        logIfEnabled(Level.WARNING, throwable, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void warn(Throwable throwable, String msg, Object arg0) {
        logIfEnabled(Level.WARNING, throwable, msg, arg0, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void warn(Throwable throwable, String msg, Object arg0, Object arg1) {
        logIfEnabled(Level.WARNING, throwable, msg, arg0, arg1, UNKNOWN_ARG, null);
    }

    public void warn(Throwable throwable, String msg, Object arg0, Object arg1, Object arg2) {
        logIfEnabled(Level.WARNING, throwable, msg, arg0, arg1, arg2, null);
    }

    public void warn(Throwable throwable, String msg, Object[] argArray) {
        logIfEnabled(Level.WARNING, throwable, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, argArray);
    }

    public void error(String msg) {
        logIfEnabled(Level.ERROR, null, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void error(String msg, Object arg0) {
        logIfEnabled(Level.ERROR, null, msg, arg0, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void error(String msg, Object arg0, Object arg1) {
        logIfEnabled(Level.ERROR, null, msg, arg0, arg1, UNKNOWN_ARG, null);
    }

    public void error(String msg, Object arg0, Object arg1, Object arg2) {
        logIfEnabled(Level.ERROR, null, msg, arg0, arg1, arg2, null);
    }

    public void error(String msg, Object[] argArray) {
        logIfEnabled(Level.ERROR, null, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, argArray);
    }

    public void error(Throwable throwable, String msg) {
        logIfEnabled(Level.ERROR, throwable, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void error(Throwable throwable, String msg, Object arg0) {
        logIfEnabled(Level.ERROR, throwable, msg, arg0, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void error(Throwable throwable, String msg, Object arg0, Object arg1) {
        logIfEnabled(Level.ERROR, throwable, msg, arg0, arg1, UNKNOWN_ARG, null);
    }

    public void error(Throwable throwable, String msg, Object arg0, Object arg1, Object arg2) {
        logIfEnabled(Level.ERROR, throwable, msg, arg0, arg1, arg2, null);
    }

    public void error(Throwable throwable, String msg, Object[] argArray) {
        logIfEnabled(Level.ERROR, throwable, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, argArray);
    }

    public void fatal(String msg) {
        logIfEnabled(Level.FATAL, null, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void fatal(String msg, Object arg0) {
        logIfEnabled(Level.FATAL, null, msg, arg0, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void fatal(String msg, Object arg0, Object arg1) {
        logIfEnabled(Level.FATAL, null, msg, arg0, arg1, UNKNOWN_ARG, null);
    }

    public void fatal(String msg, Object arg0, Object arg1, Object arg2) {
        logIfEnabled(Level.FATAL, null, msg, arg0, arg1, arg2, null);
    }

    public void fatal(String msg, Object[] argArray) {
        logIfEnabled(Level.FATAL, null, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, argArray);
    }

    public void fatal(Throwable throwable, String msg) {
        logIfEnabled(Level.FATAL, throwable, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void fatal(Throwable throwable, String msg, Object arg0) {
        logIfEnabled(Level.FATAL, throwable, msg, arg0, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void fatal(Throwable throwable, String msg, Object arg0, Object arg1) {
        logIfEnabled(Level.FATAL, throwable, msg, arg0, arg1, UNKNOWN_ARG, null);
    }

    public void fatal(Throwable throwable, String msg, Object arg0, Object arg1, Object arg2) {
        logIfEnabled(Level.FATAL, throwable, msg, arg0, arg1, arg2, null);
    }

    public void fatal(Throwable throwable, String msg, Object[] argArray) {
        logIfEnabled(Level.FATAL, throwable, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, argArray);
    }

    public void log(Level level, String msg) {
        logIfEnabled(level, null, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void log(Level level, String msg, Object arg0) {
        logIfEnabled(level, null, msg, arg0, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void log(Level level, String msg, Object arg0, Object arg1) {
        logIfEnabled(level, null, msg, arg0, arg1, UNKNOWN_ARG, null);
    }

    public void log(Level level, String msg, Object arg0, Object arg1, Object arg2) {
        logIfEnabled(level, null, msg, arg0, arg1, arg2, null);
    }

    public void log(Level level, String msg, Object[] argArray) {
        logIfEnabled(level, null, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, argArray);
    }

    public void log(Level level, Throwable throwable, String msg) {
        logIfEnabled(level, throwable, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void log(Level level, Throwable throwable, String msg, Object arg0) {
        logIfEnabled(level, throwable, msg, arg0, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void log(Level level, Throwable throwable, String msg, Object arg0, Object arg1) {
        logIfEnabled(level, throwable, msg, arg0, arg1, UNKNOWN_ARG, null);
    }

    public void log(Level level, Throwable throwable, String msg, Object arg0, Object arg1, Object arg2) {
        logIfEnabled(level, throwable, msg, arg0, arg1, arg2, null);
    }

    public void log(Level level, Throwable throwable, String msg, Object[] argArray) {
        logIfEnabled(level, throwable, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, argArray);
    }

    private void logIfEnabled(Level level, Throwable throwable, String msg, Object arg0, Object arg1, Object arg2, Object[] argArray) {
        if (this.log.isLevelEnabled(level)) {
            String fullMsg = buildFullMessage(msg, arg0, arg1, arg2, argArray);
            if (throwable == null) {
                this.log.log(level, fullMsg);
            } else {
                this.log.log(level, fullMsg, throwable);
            }
        }
    }

    private String buildFullMessage(String msg, Object arg0, Object arg1, Object arg2, Object[] argArray) {
        StringBuilder sb = null;
        int lastIndex = 0;
        int argC = 0;
        while (true) {
            int argIndex = msg.indexOf(ARG_STRING, lastIndex);
            if (argIndex == -1) {
                break;
            }
            if (sb == null) {
                sb = new StringBuilder(128);
            }
            sb.append(msg, lastIndex, argIndex);
            lastIndex = argIndex + ARG_STRING.length();
            if (argArray == null) {
                if (argC == 0) {
                    appendArg(sb, arg0);
                } else if (argC == 1) {
                    appendArg(sb, arg1);
                } else if (argC == 2) {
                    appendArg(sb, arg2);
                }
            } else if (argC < argArray.length) {
                appendArg(sb, argArray[argC]);
            }
            argC++;
        }
        if (sb == null) {
            return msg;
        }
        sb.append(msg, lastIndex, msg.length());
        return sb.toString();
    }

    private void appendArg(StringBuilder sb, Object arg) {
        if (arg != UNKNOWN_ARG) {
            if (arg == null) {
                sb.append("null");
            } else if (arg.getClass().isArray()) {
                sb.append('[');
                int length = Array.getLength(arg);
                for (int i = 0; i < length; i++) {
                    if (i > 0) {
                        sb.append(", ");
                    }
                    sb.append(Array.get(arg, i));
                }
                sb.append(']');
            } else {
                sb.append(arg.toString());
            }
        }
    }
}
