package com.MCWorld.mcsdk.dtlib;

import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Process;
import org.bytedeco.javacpp.avformat;

/* compiled from: DTSystemOper */
public class h {
    private static final String TAG = "DTSystemOper";
    private static final boolean alz = false;
    private static h aoG = null;
    public static final boolean aoH = false;
    public static final int aoL = 0;
    public static final int aoM = 1;
    private int aoI = 0;
    private int aoJ = 0;
    private int aoK = 0;
    private int aoN = 0;
    private boolean aoO = false;

    public static synchronized h CW() {
        h hVar;
        synchronized (h.class) {
            if (aoG == null) {
                aoG = new h();
            }
            hVar = aoG;
        }
        return hVar;
    }

    public int CX() {
        return this.aoI;
    }

    public void iF(int inputDtLoadResMode) {
        this.aoI = inputDtLoadResMode;
    }

    public int CY() {
        return this.aoJ;
    }

    public void iG(int inputDtLoadResSunMode) {
        this.aoJ = inputDtLoadResSunMode;
    }

    public int CZ() {
        return this.aoK;
    }

    public void iH(int dtJSMode) {
        this.aoK = dtJSMode;
    }

    public int Da() {
        return this.aoN;
    }

    private void iI(int inputDtSDKMode) {
        this.aoN = inputDtSDKMode;
    }

    public boolean Db() {
        return this.aoO;
    }

    public void cj(boolean inputBInitDTSDKModeFlag) {
        this.aoO = inputBInitDTSDKModeFlag;
    }

    public void Dc() {
        if (!this.aoO) {
            cj(true);
            try {
                if (this.aoI == 2) {
                    iI(0);
                } else if (this.aoI == 3) {
                    if (VERSION.SDK_INT < 11) {
                        iI(1);
                    } else {
                        iI(0);
                    }
                } else if (this.aoI == 5) {
                    iI(0);
                } else {
                    iI(0);
                }
            } catch (Exception e) {
            }
        }
    }

    public static void iJ(int nMSeconds) {
        try {
            Thread.currentThread();
            Thread.sleep((long) nMSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void Dd() {
        Process.killProcess(Process.myPid());
    }

    public static void aY(Context in_context) {
        Intent i = in_context.getPackageManager().getLaunchIntentForPackage(in_context.getPackageName());
        if (i != null) {
            i.addFlags(avformat.AVFMT_SEEK_TO_PTS);
            i.putExtra("restart", 4);
            in_context.startActivity(i);
        }
        Process.killProcess(Process.myPid());
    }
}
