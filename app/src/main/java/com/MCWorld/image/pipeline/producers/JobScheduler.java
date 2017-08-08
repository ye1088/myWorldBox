package com.MCWorld.image.pipeline.producers;

import android.os.SystemClock;
import com.MCWorld.framework.base.utils.VisibleForTesting;
import com.MCWorld.image.base.imagepipeline.image.d;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.concurrent.GuardedBy;

public class JobScheduler {
    static final String JR = "queueTime";
    private final a JT;
    private final Runnable JU = new Runnable(this) {
        final /* synthetic */ JobScheduler Kc;

        {
            this.Kc = this$0;
        }

        public void run() {
            this.Kc.oS();
        }
    };
    private final Runnable JV = new Runnable(this) {
        final /* synthetic */ JobScheduler Kc;

        {
            this.Kc = this$0;
        }

        public void run() {
            this.Kc.oR();
        }
    };
    private final int JW;
    @VisibleForTesting
    @GuardedBy("this")
    d JX = null;
    @VisibleForTesting
    @GuardedBy("this")
    boolean JY = false;
    @VisibleForTesting
    @GuardedBy("this")
    JobState JZ = JobState.IDLE;
    @VisibleForTesting
    @GuardedBy("this")
    long Ka = 0;
    @VisibleForTesting
    @GuardedBy("this")
    long Kb = 0;
    private final Executor mExecutor;

    @VisibleForTesting
    enum JobState {
        IDLE,
        QUEUED,
        RUNNING,
        RUNNING_AND_PENDING
    }

    public interface a {
        void d(d dVar, boolean z);
    }

    @VisibleForTesting
    static class b {
        private static ScheduledExecutorService Ke;

        b() {
        }

        static ScheduledExecutorService oV() {
            if (Ke == null) {
                Ke = Executors.newSingleThreadScheduledExecutor();
            }
            return Ke;
        }
    }

    public JobScheduler(Executor executor, a jobRunnable, int minimumJobIntervalMs) {
        this.mExecutor = executor;
        this.JT = jobRunnable;
        this.JW = minimumJobIntervalMs;
    }

    public void oP() {
        d oldEncodedImage;
        synchronized (this) {
            oldEncodedImage = this.JX;
            this.JX = null;
            this.JY = false;
        }
        d.d(oldEncodedImage);
    }

    public boolean e(d encodedImage, boolean isLast) {
        if (!f(encodedImage, isLast)) {
            return false;
        }
        d oldEncodedImage;
        synchronized (this) {
            oldEncodedImage = this.JX;
            this.JX = d.a(encodedImage);
            this.JY = isLast;
        }
        d.d(oldEncodedImage);
        return true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean oQ() {
        /*
        r10 = this;
        r0 = android.os.SystemClock.uptimeMillis();
        r4 = 0;
        r2 = 0;
        monitor-enter(r10);
        r3 = r10.JX;	 Catch:{ all -> 0x003e }
        r6 = r10.JY;	 Catch:{ all -> 0x003e }
        r3 = f(r3, r6);	 Catch:{ all -> 0x003e }
        if (r3 != 0) goto L_0x0015;
    L_0x0012:
        r3 = 0;
        monitor-exit(r10);	 Catch:{ all -> 0x003e }
    L_0x0014:
        return r3;
    L_0x0015:
        r3 = com.huluxia.image.pipeline.producers.JobScheduler.AnonymousClass3.Kd;	 Catch:{ all -> 0x003e }
        r6 = r10.JZ;	 Catch:{ all -> 0x003e }
        r6 = r6.ordinal();	 Catch:{ all -> 0x003e }
        r3 = r3[r6];	 Catch:{ all -> 0x003e }
        switch(r3) {
            case 1: goto L_0x002c;
            case 2: goto L_0x0022;
            case 3: goto L_0x0041;
            default: goto L_0x0022;
        };	 Catch:{ all -> 0x003e }
    L_0x0022:
        monitor-exit(r10);	 Catch:{ all -> 0x003e }
        if (r2 == 0) goto L_0x002a;
    L_0x0025:
        r6 = r4 - r0;
        r10.S(r6);
    L_0x002a:
        r3 = 1;
        goto L_0x0014;
    L_0x002c:
        r6 = r10.Kb;	 Catch:{ all -> 0x003e }
        r3 = r10.JW;	 Catch:{ all -> 0x003e }
        r8 = (long) r3;	 Catch:{ all -> 0x003e }
        r6 = r6 + r8;
        r4 = java.lang.Math.max(r6, r0);	 Catch:{ all -> 0x003e }
        r2 = 1;
        r10.Ka = r0;	 Catch:{ all -> 0x003e }
        r3 = com.huluxia.image.pipeline.producers.JobScheduler.JobState.QUEUED;	 Catch:{ all -> 0x003e }
        r10.JZ = r3;	 Catch:{ all -> 0x003e }
        goto L_0x0022;
    L_0x003e:
        r3 = move-exception;
        monitor-exit(r10);	 Catch:{ all -> 0x003e }
        throw r3;
    L_0x0041:
        r3 = com.huluxia.image.pipeline.producers.JobScheduler.JobState.RUNNING_AND_PENDING;	 Catch:{ all -> 0x003e }
        r10.JZ = r3;	 Catch:{ all -> 0x003e }
        goto L_0x0022;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huluxia.image.pipeline.producers.JobScheduler.oQ():boolean");
    }

    private void S(long delay) {
        if (delay > 0) {
            b.oV().schedule(this.JV, delay, TimeUnit.MILLISECONDS);
        } else {
            this.JV.run();
        }
    }

    private void oR() {
        this.mExecutor.execute(this.JU);
    }

    private void oS() {
        long now = SystemClock.uptimeMillis();
        synchronized (this) {
            d input = this.JX;
            boolean isLast = this.JY;
            this.JX = null;
            this.JY = false;
            this.JZ = JobState.RUNNING;
            this.Kb = now;
        }
        try {
            if (f(input, isLast)) {
                this.JT.d(input, isLast);
            }
            d.d(input);
            oT();
        } catch (Throwable th) {
            d.d(input);
            oT();
        }
    }

    private void oT() {
        long now = SystemClock.uptimeMillis();
        long when = 0;
        boolean shouldEnqueue = false;
        synchronized (this) {
            if (this.JZ == JobState.RUNNING_AND_PENDING) {
                when = Math.max(this.Kb + ((long) this.JW), now);
                shouldEnqueue = true;
                this.Ka = now;
                this.JZ = JobState.QUEUED;
            } else {
                this.JZ = JobState.IDLE;
            }
        }
        if (shouldEnqueue) {
            S(when - now);
        }
    }

    private static boolean f(d encodedImage, boolean isLast) {
        return isLast || d.e(encodedImage);
    }

    public synchronized long oU() {
        return this.Kb - this.Ka;
    }
}
