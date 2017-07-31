package com.huluxia.framework.base.crash;

import com.huluxia.framework.base.log.HLog;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;

public class CrashHandler implements UncaughtExceptionHandler {
    private final String mFileName;
    private List<CrashListener> mListeners = new ArrayList();

    public CrashHandler(String fileName) {
        this.mFileName = fileName;
    }

    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            String crash = CrashHelper.getThreadStack(ex);
            HLog.error(this, "uncaughtException happens crash : " + crash, new Object[0]);
            CrashHelper.writeLogToFile(this.mFileName, crash);
            for (CrashListener listener : this.mListeners) {
                listener.onCrash(crash);
            }
        } catch (Exception e) {
            HLog.error(this, "uncaughtException ex happens", new Object[0]);
        }
    }

    public void registerListener(CrashListener reporter) {
        if (reporter != null) {
            this.mListeners.add(reporter);
        }
    }
}
