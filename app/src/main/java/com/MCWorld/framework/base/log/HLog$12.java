package com.MCWorld.framework.base.log;

import android.util.Log;

class HLog$12 implements Runnable {
    final /* synthetic */ Object[] val$args;
    final /* synthetic */ String val$format;
    final /* synthetic */ Object val$obj;

    HLog$12(Object[] objArr, String str, Object obj) {
        this.val$args = objArr;
        this.val$format = str;
        this.val$obj = obj;
    }

    public void run() {
        try {
            String logText = (this.val$args == null || this.val$args.length == 0) ? this.val$format : String.format(this.val$format, this.val$args);
            if (this.val$args == null || this.val$args.length <= 0 || !(this.val$args[this.val$args.length - 1] instanceof Throwable)) {
                Log.e(HLog.access$200(this.val$obj), logText);
                HLog.access$100(logText);
                return;
            }
            Throwable t = this.val$args[this.val$args.length - 1];
            Log.e(HLog.access$200(this.val$obj), logText, t);
            HLog.access$600(logText, t);
        } catch (Throwable th) {
        }
    }
}
