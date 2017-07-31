package com.j256.ormlite.logger;

import com.j256.ormlite.logger.Log.Level;
import com.j256.ormlite.misc.IOUtils;
import com.j256.ormlite.stmt.query.SimpleComparison;
import io.netty.handler.codec.http.HttpConstants;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class LocalLog implements Log {
    private static final Level DEFAULT_LEVEL = Level.DEBUG;
    public static final String LOCAL_LOG_FILE_PROPERTY = "com.j256.ormlite.logger.file";
    public static final String LOCAL_LOG_LEVEL_PROPERTY = "com.j256.ormlite.logger.level";
    public static final String LOCAL_LOG_PROPERTIES_FILE = "/ormliteLocalLog.properties";
    private static final List<PatternLevel> classLevels = readLevelResourceFile(LocalLog.class.getResourceAsStream(LOCAL_LOG_PROPERTIES_FILE));
    private static final ThreadLocal<DateFormat> dateFormatThreadLocal = new ThreadLocal<DateFormat>() {
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
        }
    };
    private static PrintStream printStream;
    private final String className;
    private final Level level;

    private static class PatternLevel {
        Level level;
        Pattern pattern;

        public PatternLevel(Pattern pattern, Level level) {
            this.pattern = pattern;
            this.level = level;
        }
    }

    static {
        openLogFile(System.getProperty(LOCAL_LOG_FILE_PROPERTY));
    }

    public LocalLog(String className) {
        this.className = LoggerFactory.getSimpleClassName(className);
        Level level = null;
        if (classLevels != null) {
            for (PatternLevel patternLevel : classLevels) {
                if (patternLevel.pattern.matcher(className).matches() && (level == null || patternLevel.level.ordinal() < level.ordinal())) {
                    level = patternLevel.level;
                }
            }
        }
        if (level == null) {
            String levelName = System.getProperty(LOCAL_LOG_LEVEL_PROPERTY);
            if (levelName == null) {
                level = DEFAULT_LEVEL;
            } else {
                Level matchedLevel;
                try {
                    matchedLevel = Level.valueOf(levelName.toUpperCase());
                } catch (IllegalArgumentException e) {
                    try {
                        matchedLevel = Level.valueOf(levelName.toUpperCase(Locale.ENGLISH));
                    } catch (IllegalArgumentException e2) {
                        throw new IllegalArgumentException("Level '" + levelName + "' was not found", e2);
                    }
                }
                level = matchedLevel;
            }
        }
        this.level = level;
    }

    public static void openLogFile(String logPath) {
        if (logPath == null) {
            printStream = System.out;
            return;
        }
        try {
            printStream = new PrintStream(new File(logPath));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Log file " + logPath + " was not found", e);
        }
    }

    public boolean isLevelEnabled(Level level) {
        return this.level.isEnabled(level);
    }

    public void log(Level level, String msg) {
        printMessage(level, msg, null);
    }

    public void log(Level level, String msg, Throwable throwable) {
        printMessage(level, msg, throwable);
    }

    void flush() {
        printStream.flush();
    }

    static List<PatternLevel> readLevelResourceFile(InputStream stream) {
        List<PatternLevel> levels = null;
        if (stream != null) {
            try {
                levels = configureClassLevels(stream);
            } catch (IOException e) {
                System.err.println("IO exception reading the log properties file '/ormliteLocalLog.properties': " + e);
            } finally {
                IOUtils.closeQuietly(stream);
            }
        }
        return levels;
    }

    private static List<PatternLevel> configureClassLevels(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        List<PatternLevel> list = new ArrayList();
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                return list;
            }
            if (!(line.length() == 0 || line.charAt(0) == '#')) {
                String[] parts = line.split(SimpleComparison.EQUAL_TO_OPERATION);
                if (parts.length != 2) {
                    System.err.println("Line is not in the format of 'pattern = level': " + line);
                } else {
                    try {
                        list.add(new PatternLevel(Pattern.compile(parts[0].trim()), Level.valueOf(parts[1].trim())));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Level '" + parts[1] + "' was not found");
                    }
                }
            }
        }
    }

    private void printMessage(Level level, String message, Throwable throwable) {
        if (isLevelEnabled(level)) {
            StringBuilder sb = new StringBuilder(128);
            sb.append(((DateFormat) dateFormatThreadLocal.get()).format(new Date()));
            sb.append(" [").append(level.name()).append("] ");
            sb.append(this.className).append(HttpConstants.SP_CHAR);
            sb.append(message);
            printStream.println(sb.toString());
            if (throwable != null) {
                throwable.printStackTrace(printStream);
            }
        }
    }
}
