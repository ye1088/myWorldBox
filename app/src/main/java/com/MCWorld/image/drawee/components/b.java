package com.MCWorld.image.drawee.components;

/* compiled from: RetryManager */
public class b {
    private static final int zP = 4;
    private boolean zQ;
    private int zR;
    private int zS;

    public b() {
        init();
    }

    public static b jb() {
        return new b();
    }

    public void init() {
        this.zQ = false;
        this.zR = 4;
        reset();
    }

    public void reset() {
        this.zS = 0;
    }

    public boolean jc() {
        return this.zQ;
    }

    public void S(boolean tapToRetryEnabled) {
        this.zQ = tapToRetryEnabled;
    }

    public void bB(int maxTapToRetryAttemps) {
        this.zR = maxTapToRetryAttemps;
    }

    public boolean jd() {
        return this.zQ && this.zS < this.zR;
    }

    public void je() {
        this.zS++;
    }
}
