package com.MCWorld.mcsdk;

public class DTPokerFace {
    public static final int PROT_EXEC = 4;
    public static final int PROT_NONE = 0;
    public static final int PROT_READ = 1;
    public static final int PROT_WRITE = 2;
    public static final int _SC_PAGESIZE = 39;

    public static native int mprotect(long j, long j2, int i);

    public static native long sysconf(int i);

    public static void init() {
        c.init();
    }
}
