package com.MCWorld.mcsdk.log;

import android.util.Log;

/* compiled from: DTDebug */
class a$3 implements Runnable {
    final /* synthetic */ long aoS;
    final /* synthetic */ String val$logText;

    a$3(String str, long j) {
        this.val$logText = str;
        this.aoS = j;
    }

    public void run() {
        if (a.access$000()) {
            try {
                c.writeLogToFile(c.getLogPath(), a.Df().logFileName, this.val$logText, false, this.aoS);
            } catch (Throwable e) {
                Log.e("HLogs", "writeToLog fail, " + e);
            }
        }
    }
}
