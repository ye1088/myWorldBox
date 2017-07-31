package com.j256.ormlite.logger;

import com.j256.ormlite.logger.Log.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jLoggingLog implements Log {
    private final Logger logger;

    public Slf4jLoggingLog(String className) {
        this.logger = LoggerFactory.getLogger(className);
    }

    public boolean isLevelEnabled(Level level) {
        switch (level) {
            case TRACE:
                return this.logger.isTraceEnabled();
            case DEBUG:
                return this.logger.isDebugEnabled();
            case INFO:
                return this.logger.isInfoEnabled();
            case WARNING:
                return this.logger.isWarnEnabled();
            case ERROR:
                return this.logger.isErrorEnabled();
            case FATAL:
                return this.logger.isErrorEnabled();
            default:
                return this.logger.isInfoEnabled();
        }
    }

    public void log(Level level, String msg) {
        switch (level) {
            case TRACE:
                this.logger.trace(msg);
                return;
            case DEBUG:
                this.logger.debug(msg);
                return;
            case INFO:
                this.logger.info(msg);
                return;
            case WARNING:
                this.logger.warn(msg);
                return;
            case ERROR:
                this.logger.error(msg);
                return;
            case FATAL:
                this.logger.error(msg);
                return;
            default:
                this.logger.info(msg);
                return;
        }
    }

    public void log(Level level, String msg, Throwable t) {
        switch (level) {
            case TRACE:
                this.logger.trace(msg, t);
                return;
            case DEBUG:
                this.logger.debug(msg, t);
                return;
            case INFO:
                this.logger.info(msg, t);
                return;
            case WARNING:
                this.logger.warn(msg, t);
                return;
            case ERROR:
                this.logger.error(msg, t);
                return;
            case FATAL:
                this.logger.error(msg, t);
                return;
            default:
                this.logger.info(msg, t);
                return;
        }
    }
}
