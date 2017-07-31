package com.huluxia.framework.base.log;

import android.util.Log;

class HLog$15 implements Runnable {
    final /* synthetic */ String val$filename;
    final /* synthetic */ int val$line;
    final /* synthetic */ String val$methodname;
    final /* synthetic */ Object val$obj;
    final /* synthetic */ Throwable val$t;

    HLog$15(Object obj, String str, String str2, int i, Throwable th) {
        this.val$obj = obj;
        this.val$methodname = str;
        this.val$filename = str2;
        this.val$line = i;
        this.val$t = th;
    }

    public void run() {
        try {
            String logText = HLog.access$800(this.val$obj, this.val$methodname, this.val$filename, this.val$line);
            Log.e(HLog.access$200(this.val$obj), logText, this.val$t);
            HLog.access$600(logText, this.val$t);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
