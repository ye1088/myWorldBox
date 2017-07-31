package com.huluxia.mcjavascript;

import com.huluxia.framework.base.log.HLog;
import com.huluxia.mcjsmanager.c;
import com.mojang.minecraftpe.MainActivity;
import java.io.IOException;
import org.mozilla.javascript.Context;

/* compiled from: DTLoadHLXJS */
public class h {
    public static MainActivity dTMainActivity = null;

    /* compiled from: DTLoadHLXJS */
    private static class a implements Runnable {
        public Throwable ajJ = null;
        private String ajK;
        private String sourceName;

        public a(String inFileData, String paramString) {
            this.ajK = inFileData;
            this.sourceName = paramString;
        }

        public void run() {
            try {
                Context localContext = Context.enter();
                f.setupContext(localContext);
                f.initJustLoadedScript(localContext, localContext.HLXcompileReader(this.ajK, this.sourceName, 0, null), this.sourceName);
                Context.exit();
            } catch (Throwable throwable) {
                HLog.error("HLXParseThread", "hlx js parse thread run throw " + throwable, new Object[0]);
                this.ajJ = throwable;
            }
        }
    }

    public static void init() {
        if (MainActivity.currentMainActivity != null) {
            MainActivity main = (MainActivity) MainActivity.currentMainActivity.get();
            if (main != null) {
                dTMainActivity = main;
            }
        }
    }

    public static void C(String DirName, int in_JsFileIndex) throws IOException {
        try {
            if (dTMainActivity != null) {
                String tmpFileData = com.mojang.minecraftpe.a.bHZ.J(DirName, in_JsFileIndex);
                String tmpFileName = c.hF(in_JsFileIndex);
                if (tmpFileData != null && tmpFileName != null) {
                    C(tmpFileData, tmpFileName);
                }
            }
        } catch (Exception e) {
        }
    }

    public static void C(String inFileData, String sourceName) throws IOException {
        if (!f.Al()) {
            return;
        }
        if (f.Am()) {
            a parseRunner = new a(inFileData, sourceName);
            Thread t = new Thread(Thread.currentThread().getThreadGroup(), parseRunner, "mctools parse thread", 262144);
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
            }
            if (parseRunner.ajJ != null) {
                IOException back;
                if (parseRunner.ajJ instanceof IOException) {
                    back = parseRunner.ajJ;
                } else {
                    back = new IOException(parseRunner.ajJ);
                }
                throw back;
            }
            return;
        }
        throw new RuntimeException("Not available in multiplayer");
    }
}
