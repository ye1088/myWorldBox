package com.MCWorld.framework.base.log;

import android.util.Log;

class HLog$7 implements Runnable {
    final /* synthetic */ Object[] val$args;
    final /* synthetic */ String val$filename;
    final /* synthetic */ String val$format;
    final /* synthetic */ int val$line;
    final /* synthetic */ Object val$obj;
    final /* synthetic */ String val$thread;

    HLog$7(Object[] objArr, String str, Object obj, String str2, int i, String str3) {
        this.val$args = objArr;
        this.val$format = str;
        this.val$obj = obj;
        this.val$filename = str2;
        this.val$line = i;
        this.val$thread = str3;
    }

    public void run() {
        try {
            String msg = (this.val$args == null || this.val$args.length == 0) ? this.val$format : String.format(this.val$format, this.val$args);
            String logText = HLog.access$300(2, this.val$obj, this.val$filename, this.val$line, this.val$thread, msg);
            if (HLog.access$400()) {
                Log.d(HLog.access$200(this.val$obj), logText.substring(4));
            }
            if (HLog.access$500()) {
                HLog.access$100(logText);
            }
        } catch (Throwable th) {
        }
    }
}
