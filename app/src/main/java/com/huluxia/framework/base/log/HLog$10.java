package com.huluxia.framework.base.log;

import android.util.Log;

class HLog$10 implements Runnable {
    final /* synthetic */ Object[] val$args;
    final /* synthetic */ String val$format;
    final /* synthetic */ Object val$obj;

    HLog$10(Object[] objArr, String str, Object obj) {
        this.val$args = objArr;
        this.val$format = str;
        this.val$obj = obj;
    }

    public void run() {
        try {
            String logText = (this.val$args == null || this.val$args.length == 0) ? this.val$format : String.format(this.val$format, this.val$args);
            Log.w(HLog.access$200(this.val$obj), logText);
            HLog.access$100(logText);
        } catch (Throwable th) {
        }
    }
}
