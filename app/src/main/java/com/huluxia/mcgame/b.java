package com.huluxia.mcgame;

/* compiled from: DTArmorSlot */
public class b {
    public static int[] acK = new int[4];
    private static Object acL = new Object();
    private static boolean acM = false;
    private static Object acN = new Object();
    private static boolean acO = false;
    private static int[] acP;

    public static void uX() {
        vf();
    }

    public static int[] uY() {
        return vb();
    }

    public static void uZ() {
        for (int i = 0; i < 4; i++) {
            if (c.fZ(i) > 0) {
                c.G(i, 0);
            }
        }
    }

    public static int[] va() {
        int[] iArr = null;
        try {
            if (g.vW() != 0) {
                for (int i = 0; i < 4; i++) {
                    acK[i] = c.fZ(i);
                }
                iArr = acK;
            }
        } catch (Exception e) {
        }
        return iArr;
    }

    public static int[] vb() {
        synchronized (acL) {
            acM = true;
            try {
                acL.wait(100);
            } catch (InterruptedException e) {
                return null;
            }
        }
        return acK;
    }

    public static void vc() {
        int i = 0;
        while (i < 4) {
            try {
                acK[i] = c.fZ(i);
                i++;
            } catch (Exception e) {
                return;
            }
        }
    }

    public static void vd() {
        if (acM) {
            synchronized (acL) {
                vc();
                acM = false;
                acL.notify();
            }
        }
    }

    public static boolean b(int[] array) {
        boolean z = true;
        synchronized (acN) {
            acP = array;
            acO = true;
            try {
                acN.wait(100);
            } catch (InterruptedException e) {
                z = false;
            }
        }
        return z;
    }

    public static void ve() {
        try {
            if (acP != null && acP.length >= 4) {
                for (int i = 0; i < 4; i++) {
                    if (acP[i] != 0) {
                        c.G(i, acP[i]);
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public static void vf() {
        if (acO) {
            synchronized (acN) {
                ve();
                acO = false;
                acN.notify();
            }
        }
    }

    public static boolean c(int[] array) {
        return b(array);
    }
}
