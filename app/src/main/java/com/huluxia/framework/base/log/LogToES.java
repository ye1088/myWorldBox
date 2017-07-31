package com.huluxia.framework.base.log;

import android.os.SystemClock;
import android.util.Log;
import io.netty.handler.codec.http.HttpConstants;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class LogToES {
    private static final String BAK_EXT = ".bak";
    private static int BUFF_SIZE = 32768;
    private static final long DAY_DELAY = 864000000;
    public static final int DEFAULT_BAK_FILE_NUM_LIMIT = 2;
    public static final int DEFAULT_BUFF_SIZE = 32768;
    private static final long FLUSH_INTERVAL = 5000;
    private static final FastDateFormat LOG_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd kk:mm:ss.SSS");
    public static final int MAX_FILE_SIZE = 5;
    private static int mBackFileNumLimit = 2;
    private static long mLastMillis = 0;
    private static Object mLock = new Object();
    private static volatile String mLogPath;
    private static String mPath;
    private static BufferedWriter mWriter;
    private static FastDateFormat simpleDateFormat = FastDateFormat.getInstance("-MM-dd-kk-mm-ss.SSS");

    public static void setBackupLogLimitInMB(int logCapacityInMB) {
        mBackFileNumLimit = ((logCapacityInMB + 5) - 1) / 5;
    }

    public static boolean setLogPath(String logDir) {
        if (logDir == null || logDir.length() == 0) {
            return false;
        }
        mLogPath = logDir;
        new File(logDir).mkdirs();
        return new File(logDir).isDirectory();
    }

    public static String getLogPath() {
        return mLogPath;
    }

    public static void setBuffSize(int bytes) {
        BUFF_SIZE = bytes;
    }

    public static void writeLogToFile(String dir, String fileName, String msg, boolean immediateClose, long timeMillis) throws IOException {
        writeLog(dir, fileName, msg, immediateClose, timeMillis);
    }

    public static void writeLog(String path, String fileName, String msg, boolean immediateClose, long timeMillis) throws IOException {
        if (path != null && path.length() != 0 && fileName != null && fileName.length() != 0) {
            File dirFile = new File(path);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            boolean needCreate = false;
            File logFile = createFile(path, fileName);
            if (!logFile.exists()) {
                try {
                    logFile.createNewFile();
                } catch (IOException e) {
                    Log.e("Log", "can not creare file " + logFile.getAbsolutePath());
                    e.printStackTrace();
                    return;
                }
            } else if ((logFile.length() >>> 20) > 5) {
                deleteOldLogs();
                String fileExt = simpleDateFormat.format(timeMillis);
                Appendable stringBuilder = new StringBuilder(path);
                stringBuilder.append(File.separator).append(fileName).append(fileExt).append(BAK_EXT);
                close();
                logFile.renameTo(new File(stringBuilder.toString()));
                logFile = createFile(path, fileName);
                needCreate = true;
                limitVolume();
            }
            StringBuffer sb = new StringBuffer(LOG_FORMAT.format(timeMillis));
            sb.append(HttpConstants.SP_CHAR);
            sb.append(msg);
            sb.append('\n');
            String strLog = sb.toString();
            synchronized (mLock) {
                if (mPath == null) {
                    mPath = logFile.getAbsolutePath();
                    needCreate = true;
                } else {
                    if (!equal(mPath, logFile.getAbsolutePath())) {
                        BufferedWriter writer = mWriter;
                        if (writer != null) {
                            writer.close();
                        }
                        mWriter = null;
                        mPath = null;
                        needCreate = true;
                    }
                }
                BufferedWriter bufWriter = mWriter;
                if (needCreate || bufWriter == null) {
                    mPath = logFile.getAbsolutePath();
                    bufWriter = new BufferedWriter(new FileWriter(logFile, true), BUFF_SIZE);
                    mWriter = bufWriter;
                }
                bufWriter.write(strLog);
                long curMillis = SystemClock.elapsedRealtime();
                if (curMillis - mLastMillis >= FLUSH_INTERVAL) {
                    bufWriter.flush();
                    mLastMillis = curMillis;
                }
                if (immediateClose) {
                    bufWriter.close();
                    mPath = null;
                    mWriter = null;
                }
            }
        }
    }

    private static File createFile(String path, String fileName) {
        return new File(path.endsWith(File.separator) ? path + fileName : path + File.separator + fileName);
    }

    private static boolean equal(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return s1 == null && s2 == null;
        } else {
            return s1.equals(s2);
        }
    }

    private static void deleteOldLogs() {
        File dirFile = new File(getLogPath());
        if (dirFile.exists()) {
            long now = System.currentTimeMillis();
            File[] files = dirFile.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (isBakFile(file.getName()) && now - file.lastModified() > DAY_DELAY) {
                        file.delete();
                    }
                }
            }
        }
    }

    public static boolean getLogOutputPaths(HLog$LogOutputPaths out, String currentName) {
        int i = 0;
        String dir = getLogPath();
        if (dir == null || currentName == null) {
            return false;
        }
        String current;
        out.dir = dir;
        synchronized (mLock) {
            current = mPath;
        }
        if (current == null) {
            current = createFile(dir, currentName).getAbsolutePath();
        }
        out.currentLogFile = current;
        File[] files = new File(dir).listFiles();
        if (files != null) {
            long maxModifiedTime = 0;
            String dest = null;
            int length = files.length;
            while (i < length) {
                File e = files[i];
                if (isBakFile(e.getAbsolutePath()) && e.lastModified() > maxModifiedTime) {
                    maxModifiedTime = e.lastModified();
                    dest = e.getAbsolutePath();
                }
                i++;
            }
            out.latestBackupFile = dest;
        }
        return true;
    }

    private static boolean isBakFile(String file) {
        return file.endsWith(BAK_EXT);
    }

    private static void limitVolume() {
        int i = 0;
        File dirFile = new File(getLogPath());
        if (dirFile.exists()) {
            File[] files = dirFile.listFiles();
            if (files != null && files.length > Math.max(0, mBackFileNumLimit)) {
                int i2;
                int numOfDeletable = 0;
                for (File file : files) {
                    if (isBakFile(file.getName())) {
                        numOfDeletable++;
                    }
                }
                if (numOfDeletable > 0) {
                    File[] deletables = new File[numOfDeletable];
                    int length = files.length;
                    int i3 = 0;
                    while (i < length) {
                        File e = files[i];
                        if (i3 >= numOfDeletable) {
                            break;
                        }
                        if (isBakFile(e.getName())) {
                            i2 = i3 + 1;
                            deletables[i3] = e;
                        } else {
                            i2 = i3;
                        }
                        i++;
                        i3 = i2;
                    }
                    deleteIfOutOfBound(deletables);
                }
            }
        }
    }

    private static void deleteIfOutOfBound(File[] files) {
        if (files.length > mBackFileNumLimit) {
            Arrays.sort(files, new Comparator<File>() {
                public int compare(File lhs, File rhs) {
                    return rhs.getName().compareTo(lhs.getName());
                }
            });
            int filesNum = files.length;
            for (int i = mBackFileNumLimit; i < filesNum; i++) {
                File file = files[i];
                if (!file.delete()) {
                    Log.e("LogToES", "LogToES failed to delete file " + file);
                }
            }
        }
    }

    public static void flush() {
        synchronized (mLock) {
            BufferedWriter writer = mWriter;
            if (writer != null) {
                try {
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void close() {
        synchronized (mLock) {
            BufferedWriter writer = mWriter;
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            mPath = null;
        }
    }

    public static boolean isOpen() {
        boolean z;
        synchronized (mLock) {
            z = mWriter != null;
        }
        return z;
    }
}
