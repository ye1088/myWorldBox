package com.huluxia.image.core.common.references;

import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.framework.base.utils.VisibleForTesting;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: CloseableReference */
public final class a<T> implements Closeable, Cloneable {
    private static Class<a> tF = a.class;
    private static final c<Closeable> yg = new 1();
    private static final AtomicInteger yh = new AtomicInteger(0);
    private static final AtomicInteger yi = new AtomicInteger(0);
    private static volatile boolean yj;
    @Nullable
    private final Throwable yk;
    @Nullable
    private Throwable yl;
    @GuardedBy("this")
    private boolean ym = false;
    private final SharedReference<T> yn;

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return iu();
    }

    private a(SharedReference<T> sharedReference) {
        this.yn = (SharedReference) Preconditions.checkNotNull(sharedReference);
        sharedReference.iz();
        this.yk = iy();
    }

    private a(T t, c<T> resourceReleaser) {
        this.yn = new SharedReference(t, resourceReleaser);
        this.yk = iy();
    }

    @Nullable
    public static <T extends Closeable> a<T> b(@Nullable T t) {
        if (t == null) {
            return null;
        }
        return new a(t, yg);
    }

    @Nullable
    public static <T> a<T> a(@Nullable T t, c<T> resourceReleaser) {
        if (t == null) {
            return null;
        }
        return new a(t, resourceReleaser);
    }

    public void close() {
        synchronized (this) {
            if (this.ym) {
                return;
            }
            this.ym = true;
            this.yn.iA();
        }
    }

    public synchronized T get() {
        Preconditions.checkState(!this.ym);
        return this.yn.get();
    }

    public synchronized a<T> iu() {
        this.yl = iy();
        Preconditions.checkState(isValid());
        return new a(this.yn);
    }

    public synchronized a<T> iv() {
        this.yl = iy();
        return isValid() ? new a(this.yn) : null;
    }

    public synchronized boolean isValid() {
        return !this.ym;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void finalize() throws java.lang.Throwable {
        /*
        r6 = this;
        r2 = yh;	 Catch:{ all -> 0x0067 }
        r2.incrementAndGet();	 Catch:{ all -> 0x0067 }
        monitor-enter(r6);	 Catch:{ all -> 0x0067 }
        r2 = r6.ym;	 Catch:{ all -> 0x0064 }
        if (r2 == 0) goto L_0x000f;
    L_0x000a:
        monitor-exit(r6);	 Catch:{ all -> 0x0064 }
        super.finalize();
    L_0x000e:
        return;
    L_0x000f:
        monitor-exit(r6);	 Catch:{ all -> 0x0064 }
        r2 = yi;	 Catch:{ all -> 0x0067 }
        r2.incrementAndGet();	 Catch:{ all -> 0x0067 }
        r2 = "Finalized without closing: %x %x (type = %s)";
        r3 = 3;
        r3 = new java.lang.Object[r3];	 Catch:{ all -> 0x0067 }
        r4 = 0;
        r5 = java.lang.System.identityHashCode(r6);	 Catch:{ all -> 0x0067 }
        r5 = java.lang.Integer.valueOf(r5);	 Catch:{ all -> 0x0067 }
        r3[r4] = r5;	 Catch:{ all -> 0x0067 }
        r4 = 1;
        r5 = r6.yn;	 Catch:{ all -> 0x0067 }
        r5 = java.lang.System.identityHashCode(r5);	 Catch:{ all -> 0x0067 }
        r5 = java.lang.Integer.valueOf(r5);	 Catch:{ all -> 0x0067 }
        r3[r4] = r5;	 Catch:{ all -> 0x0067 }
        r4 = 2;
        r5 = r6.yn;	 Catch:{ all -> 0x0067 }
        r5 = r5.get();	 Catch:{ all -> 0x0067 }
        r5 = r5.getClass();	 Catch:{ all -> 0x0067 }
        r5 = r5.getSimpleName();	 Catch:{ all -> 0x0067 }
        r3[r4] = r5;	 Catch:{ all -> 0x0067 }
        r1 = java.lang.String.format(r2, r3);	 Catch:{ all -> 0x0067 }
        r2 = yj;	 Catch:{ all -> 0x0067 }
        if (r2 == 0) goto L_0x006f;
    L_0x004c:
        r2 = r6.yl;	 Catch:{ all -> 0x0067 }
        if (r2 == 0) goto L_0x006c;
    L_0x0050:
        r0 = r6.yl;	 Catch:{ all -> 0x0067 }
    L_0x0052:
        r2 = tF;	 Catch:{ all -> 0x0067 }
        r3 = 1;
        r3 = new java.lang.Object[r3];	 Catch:{ all -> 0x0067 }
        r4 = 0;
        r3[r4] = r0;	 Catch:{ all -> 0x0067 }
        com.huluxia.framework.base.log.HLog.warn(r2, r1, r3);	 Catch:{ all -> 0x0067 }
    L_0x005d:
        r6.close();	 Catch:{ all -> 0x0067 }
        super.finalize();
        goto L_0x000e;
    L_0x0064:
        r2 = move-exception;
        monitor-exit(r6);	 Catch:{ all -> 0x0064 }
        throw r2;	 Catch:{ all -> 0x0067 }
    L_0x0067:
        r2 = move-exception;
        super.finalize();
        throw r2;
    L_0x006c:
        r0 = r6.yk;	 Catch:{ all -> 0x0067 }
        goto L_0x0052;
    L_0x006f:
        r2 = tF;	 Catch:{ all -> 0x0067 }
        r3 = 0;
        r3 = new java.lang.Object[r3];	 Catch:{ all -> 0x0067 }
        com.huluxia.framework.base.log.HLog.warn(r2, r1, r3);	 Catch:{ all -> 0x0067 }
        goto L_0x005d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huluxia.image.core.common.references.a.finalize():void");
    }

    @VisibleForTesting
    public synchronized SharedReference<T> hZ() {
        return this.yn;
    }

    public synchronized int iw() {
        return isValid() ? System.identityHashCode(this.yn.get()) : 0;
    }

    public static boolean a(@Nullable a<?> ref) {
        return ref != null && ref.isValid();
    }

    @Nullable
    public static <T> a<T> b(@Nullable a<T> ref) {
        return ref != null ? ref.iv() : null;
    }

    public static <T> List<a<T>> g(Collection<a<T>> refs) {
        if (refs == null) {
            return null;
        }
        List<a<T>> ret = new ArrayList(refs.size());
        for (a ref : refs) {
            ret.add(b(ref));
        }
        return ret;
    }

    public static void c(@Nullable a<?> ref) {
        if (ref != null) {
            ref.close();
        }
    }

    public static void a(@Nullable Iterable<? extends a<?>> references) {
        if (references != null) {
            for (a<?> ref : references) {
                c(ref);
            }
        }
    }

    public static a ix() {
        return new a(yh.get(), yi.get(), null);
    }

    public static void Q(boolean enabled) {
        yj = enabled;
    }

    @Nullable
    private static Throwable iy() {
        if (yj) {
            return new Throwable();
        }
        return null;
    }
}
