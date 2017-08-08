package com.MCWorld;

/* compiled from: BBSCallbackMgr */
public class b {
    private static b eE = null;
    private h eC;
    private i eD;

    public static synchronized b bq() {
        b bVar;
        synchronized (b.class) {
            if (eE == null) {
                eE = new b();
            }
            bVar = eE;
        }
        return bVar;
    }

    public void a(h callback) {
        this.eC = callback;
    }

    public h br() {
        return this.eC;
    }

    public void a(i callback) {
        this.eD = callback;
    }

    public i bs() {
        return this.eD;
    }
}
