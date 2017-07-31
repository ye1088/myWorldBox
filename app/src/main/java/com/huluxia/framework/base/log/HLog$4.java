package com.huluxia.framework.base.log;

import android.util.Log;

class HLog$4 implements Runnable {
    final /* synthetic */ Object[] val$args;
    final /* synthetic */ String val$format;
    final /* synthetic */ Object val$obj;
    final /* synthetic */ boolean val$outToDDMS;
    final /* synthetic */ boolean val$outToFile;

    HLog$4(Object[] objArr, String str, boolean z, Object obj, boolean z2) {
        this.val$args = objArr;
        this.val$format = str;
        this.val$outToDDMS = z;
        this.val$obj = obj;
        this.val$outToFile = z2;
    }

    public void run() {
        try {
            String logText = (this.val$args == null || this.val$args.length == 0) ? this.val$format : String.format(this.val$format, this.val$args);
            if (this.val$outToDDMS) {
                Log.v(HLog.access$200(this.val$obj), logText);
            }
            if (this.val$outToFile) {
                HLog.access$100(logText);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
