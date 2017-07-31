package bolts;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* compiled from: CancellationTokenSource */
public class e implements Closeable {
    private boolean closed;
    private final List<d> da = new ArrayList();
    private ScheduledFuture<?> dc;
    private boolean dd;
    private final ScheduledExecutorService executor = b.aO();
    private final Object lock = new Object();

    public boolean aS() {
        boolean z;
        synchronized (this.lock) {
            aV();
            z = this.dd;
        }
        return z;
    }

    public c aW() {
        c cVar;
        synchronized (this.lock) {
            aV();
            cVar = new c(this);
        }
        return cVar;
    }

    public void cancel() {
        synchronized (this.lock) {
            aV();
            if (this.dd) {
                return;
            }
            aX();
            this.dd = true;
            List registrations = new ArrayList(this.da);
            e(registrations);
        }
    }

    public void e(long delay) {
        a(delay, TimeUnit.MILLISECONDS);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(long r6, java.util.concurrent.TimeUnit r8) {
        /*
        r5 = this;
        r2 = -1;
        r0 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1));
        if (r0 >= 0) goto L_0x000f;
    L_0x0006:
        r0 = new java.lang.IllegalArgumentException;
        r1 = "Delay must be >= -1";
        r0.<init>(r1);
        throw r0;
    L_0x000f:
        r0 = 0;
        r0 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1));
        if (r0 != 0) goto L_0x0019;
    L_0x0015:
        r5.cancel();
    L_0x0018:
        return;
    L_0x0019:
        r1 = r5.lock;
        monitor-enter(r1);
        r0 = r5.dd;	 Catch:{ all -> 0x0022 }
        if (r0 == 0) goto L_0x0025;
    L_0x0020:
        monitor-exit(r1);	 Catch:{ all -> 0x0022 }
        goto L_0x0018;
    L_0x0022:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0022 }
        throw r0;
    L_0x0025:
        r5.aX();	 Catch:{ all -> 0x0022 }
        r0 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1));
        if (r0 == 0) goto L_0x0039;
    L_0x002c:
        r0 = r5.executor;	 Catch:{ all -> 0x0022 }
        r2 = new bolts.e$1;	 Catch:{ all -> 0x0022 }
        r2.<init>(r5);	 Catch:{ all -> 0x0022 }
        r0 = r0.schedule(r2, r6, r8);	 Catch:{ all -> 0x0022 }
        r5.dc = r0;	 Catch:{ all -> 0x0022 }
    L_0x0039:
        monitor-exit(r1);	 Catch:{ all -> 0x0022 }
        goto L_0x0018;
        */
        throw new UnsupportedOperationException("Method not decompiled: bolts.e.a(long, java.util.concurrent.TimeUnit):void");
    }

    public void close() {
        synchronized (this.lock) {
            if (this.closed) {
                return;
            }
            aX();
            for (d registration : this.da) {
                registration.close();
            }
            this.da.clear();
            this.closed = true;
        }
    }

    d c(Runnable action) {
        d ctr;
        synchronized (this.lock) {
            aV();
            ctr = new d(this, action);
            if (this.dd) {
                ctr.aU();
            } else {
                this.da.add(ctr);
            }
        }
        return ctr;
    }

    void aT() throws CancellationException {
        synchronized (this.lock) {
            aV();
            if (this.dd) {
                throw new CancellationException();
            }
        }
    }

    void a(d registration) {
        synchronized (this.lock) {
            aV();
            this.da.remove(registration);
        }
    }

    private void e(List<d> registrations) {
        for (d registration : registrations) {
            registration.aU();
        }
    }

    public String toString() {
        return String.format(Locale.US, "%s@%s[cancellationRequested=%s]", new Object[]{getClass().getName(), Integer.toHexString(hashCode()), Boolean.toString(aS())});
    }

    private void aV() {
        if (this.closed) {
            throw new IllegalStateException("Object already closed");
        }
    }

    private void aX() {
        if (this.dc != null) {
            this.dc.cancel(true);
            this.dc = null;
        }
    }
}
