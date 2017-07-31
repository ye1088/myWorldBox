package com.huluxia.image.pipeline.memory;

/* compiled from: NoOpPoolStatsTracker */
public class p implements u {
    private static p Ib = null;

    private p() {
    }

    public static synchronized p oc() {
        p pVar;
        synchronized (p.class) {
            if (Ib == null) {
                Ib = new p();
            }
            pVar = Ib;
        }
        return pVar;
    }

    public void a(BasePool basePool) {
    }

    public void cG(int bucketedSize) {
    }

    public void od() {
    }

    public void oe() {
    }

    public void cH(int size) {
    }

    public void cI(int sizeInBytes) {
    }

    public void cJ(int sizeInBytes) {
    }
}
