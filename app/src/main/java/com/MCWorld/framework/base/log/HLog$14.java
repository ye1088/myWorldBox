package com.MCWorld.framework.base.log;

import android.util.Log;

class HLog$14 implements Runnable {
    final /* synthetic */ Object val$obj;
    final /* synthetic */ Throwable val$t;

    HLog$14(Object obj, Throwable th) {
        this.val$obj = obj;
        this.val$t = th;
    }

    public void run() {
        try {
            String logText = HLog.access$700(this.val$obj);
            Log.e(HLog.access$200(this.val$obj), logText, this.val$t);
            HLog.access$600(logText, this.val$t);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
