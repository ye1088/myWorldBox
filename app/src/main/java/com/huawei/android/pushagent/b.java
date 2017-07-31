package com.huawei.android.pushagent;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.MessageQueue;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import com.huawei.android.pushagent.b.a;
import com.huawei.android.pushagent.c.a.e;

public class b extends Thread {
    public Handler a;
    private MessageQueue b;
    private WakeLock c = ((PowerManager) this.d.getSystemService("power")).newWakeLock(1, "eventLooper");
    private Context d;

    public b(Context context) {
        super("ReceiverDispatcher");
        this.d = context;
    }

    public void a(a aVar, Intent intent) {
        if (this.a == null) {
            e.d("PushLogAC2705", "ReceiverDispatcher: the handler is null");
            PushService.a().stopService();
            return;
        }
        try {
            if (!this.c.isHeld()) {
                this.c.acquire();
            }
            if (!this.a.postDelayed(new a(this, aVar, intent), 1)) {
                e.c("PushLogAC2705", "postDelayed runnable error");
                throw new Exception("postDelayed runnable error");
            }
        } catch (Throwable e) {
            try {
                e.c("PushLogAC2705", "dispatchIntent error", e);
                if (this.c.isHeld()) {
                    e.b("PushLogAC2705", "release wakelock after dispatchIntent error");
                    this.c.release();
                }
            } catch (Throwable e2) {
                e.c("PushLogAC2705", "release eventLooper wakelock error", e2);
            }
        }
    }

    public void run() {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(Unknown Source)
	at java.util.HashMap$KeyIterator.next(Unknown Source)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:80)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r2 = this;
        android.os.Looper.prepare();	 Catch:{ Throwable -> 0x002f, all -> 0x004c }
        r0 = new android.os.Handler;	 Catch:{ Throwable -> 0x002f, all -> 0x004c }
        r0.<init>();	 Catch:{ Throwable -> 0x002f, all -> 0x004c }
        r2.a = r0;	 Catch:{ Throwable -> 0x002f, all -> 0x004c }
        r0 = android.os.Looper.myQueue();	 Catch:{ Throwable -> 0x002f, all -> 0x004c }
        r2.b = r0;	 Catch:{ Throwable -> 0x002f, all -> 0x004c }
        r0 = r2.b;	 Catch:{ Throwable -> 0x002f, all -> 0x004c }
        r1 = new com.huawei.android.pushagent.c;	 Catch:{ Throwable -> 0x002f, all -> 0x004c }
        r1.<init>(r2);	 Catch:{ Throwable -> 0x002f, all -> 0x004c }
        r0.addIdleHandler(r1);	 Catch:{ Throwable -> 0x002f, all -> 0x004c }
        android.os.Looper.loop();	 Catch:{ Throwable -> 0x002f, all -> 0x004c }
        r0 = r2.c;
        if (r0 == 0) goto L_0x002e;
    L_0x0021:
        r0 = r2.c;
        r0 = r0.isHeld();
        if (r0 == 0) goto L_0x002e;
    L_0x0029:
        r0 = r2.c;
        r0.release();
    L_0x002e:
        return;
    L_0x002f:
        r0 = move-exception;
        r1 = "PushLogAC2705";	 Catch:{ Throwable -> 0x002f, all -> 0x004c }
        r0 = com.huawei.android.pushagent.c.a.e.a(r0);	 Catch:{ Throwable -> 0x002f, all -> 0x004c }
        com.huawei.android.pushagent.c.a.e.d(r1, r0);	 Catch:{ Throwable -> 0x002f, all -> 0x004c }
        r0 = r2.c;
        if (r0 == 0) goto L_0x002e;
    L_0x003e:
        r0 = r2.c;
        r0 = r0.isHeld();
        if (r0 == 0) goto L_0x002e;
    L_0x0046:
        r0 = r2.c;
        r0.release();
        goto L_0x002e;
    L_0x004c:
        r0 = move-exception;
        r1 = r2.c;
        if (r1 == 0) goto L_0x005e;
    L_0x0051:
        r1 = r2.c;
        r1 = r1.isHeld();
        if (r1 == 0) goto L_0x005e;
    L_0x0059:
        r1 = r2.c;
        r1.release();
    L_0x005e:
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushagent.b.run():void");
    }
}
