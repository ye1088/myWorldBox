package com.MCWorld.framework.base.crash;

public class CrashMgr {
    private static CrashMgr instance;
    private CrashHandler mCrashHandler;

    private CrashMgr() {
    }

    public static synchronized CrashMgr getInstance() {
        CrashMgr crashMgr;
        synchronized (CrashMgr.class) {
            if (instance == null) {
                instance = new CrashMgr();
            }
            crashMgr = instance;
        }
        return crashMgr;
    }

    public CrashMgr init(String fileName) {
        this.mCrashHandler = new CrashHandler(fileName);
        Thread.setDefaultUncaughtExceptionHandler(this.mCrashHandler);
        return this;
    }

    public void setListener(CrashListener listener) {
        this.mCrashHandler.registerListener(listener);
    }
}
