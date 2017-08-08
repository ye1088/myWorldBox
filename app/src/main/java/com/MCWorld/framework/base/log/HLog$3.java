package com.MCWorld.framework.base.log;

import com.tencent.mm.sdk.platformtools.SpecilApiUtil;
import java.io.PrintWriter;
import java.io.StringWriter;

class HLog$3 implements Runnable {
    final /* synthetic */ String val$logText;
    final /* synthetic */ Throwable val$t;

    HLog$3(String str, Throwable th) {
        this.val$logText = str;
        this.val$t = th;
    }

    public void run() {
        StringWriter sw = new StringWriter();
        sw.write(this.val$logText);
        sw.write(SpecilApiUtil.LINE_SEP);
        this.val$t.printStackTrace(new PrintWriter(sw));
        HLog.access$100(sw.toString());
    }
}
