package com.MCWorld.image.pipeline.cache;

import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.image.base.cache.common.b;
import com.MCWorld.image.base.imagepipeline.image.d;
import com.MCWorld.image.base.imagepipeline.memory.PooledByteBuffer;
import com.MCWorld.image.core.common.references.a;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: StagingArea */
public class r {
    private static final Class<?> tF = r.class;
    @GuardedBy("this")
    private Map<b, d> Fd = new HashMap();

    private r() {
    }

    public static r lH() {
        return new r();
    }

    public synchronized void a(b key, d encodedImage) {
        Preconditions.checkNotNull(key);
        Preconditions.checkArgument(d.e(encodedImage));
        d.d((d) this.Fd.put(key, d.a(encodedImage)));
        lI();
    }

    public void clearAll() {
        synchronized (this) {
            List<d> old = new ArrayList(this.Fd.values());
            this.Fd.clear();
        }
        for (int i = 0; i < old.size(); i++) {
            d encodedImage = (d) old.get(i);
            if (encodedImage != null) {
                encodedImage.close();
            }
        }
    }

    public boolean v(b key) {
        Preconditions.checkNotNull(key);
        synchronized (this) {
            d encodedImage = (d) this.Fd.remove(key);
        }
        if (encodedImage == null) {
            return false;
        }
        try {
            boolean isValid = encodedImage.isValid();
            return isValid;
        } finally {
            encodedImage.close();
        }
    }

    public synchronized boolean d(b key, d encodedImage) {
        boolean z = false;
        synchronized (this) {
            Preconditions.checkNotNull(key);
            Preconditions.checkNotNull(encodedImage);
            Preconditions.checkArgument(d.e(encodedImage));
            d oldValue = (d) this.Fd.get(key);
            if (oldValue != null) {
                a<PooledByteBuffer> oldRef = oldValue.hS();
                a<PooledByteBuffer> ref = encodedImage.hS();
                if (!(oldRef == null || ref == null)) {
                    try {
                        if (oldRef.get() == ref.get()) {
                            this.Fd.remove(key);
                            lI();
                            z = true;
                        }
                    } finally {
                        a.c(ref);
                        a.c(oldRef);
                        d.d(oldValue);
                    }
                }
                a.c(ref);
                a.c(oldRef);
                d.d(oldValue);
            }
        }
        return z;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.MCWorld.image.base.imagepipeline.image.d w(com.MCWorld.image.base.cache.common.b r8) {
        /*
        r7 = this;
        monitor-enter(r7);
        com.huluxia.framework.base.utils.Preconditions.checkNotNull(r8);	 Catch:{ all -> 0x0059 }
        r2 = r7.Fd;	 Catch:{ all -> 0x0059 }
        r0 = r2.get(r8);	 Catch:{ all -> 0x0059 }
        r0 = (com.huluxia.image.base.imagepipeline.image.d) r0;	 Catch:{ all -> 0x0059 }
        if (r0 == 0) goto L_0x0053;
    L_0x000e:
        monitor-enter(r0);	 Catch:{ all -> 0x0059 }
        r2 = com.huluxia.image.base.imagepipeline.image.d.e(r0);	 Catch:{ all -> 0x0055 }
        if (r2 != 0) goto L_0x004d;
    L_0x0015:
        r2 = r7.Fd;	 Catch:{ all -> 0x0055 }
        r2.remove(r8);	 Catch:{ all -> 0x0055 }
        r2 = tF;	 Catch:{ all -> 0x0055 }
        r3 = "Found closed reference %d for key %s (%d)";
        r4 = 3;
        r4 = new java.lang.Object[r4];	 Catch:{ all -> 0x0055 }
        r5 = 0;
        r6 = java.lang.System.identityHashCode(r0);	 Catch:{ all -> 0x0055 }
        r6 = java.lang.Integer.valueOf(r6);	 Catch:{ all -> 0x0055 }
        r4[r5] = r6;	 Catch:{ all -> 0x0055 }
        r5 = 1;
        r6 = r8.getUriString();	 Catch:{ all -> 0x0055 }
        r4[r5] = r6;	 Catch:{ all -> 0x0055 }
        r5 = 2;
        r6 = java.lang.System.identityHashCode(r8);	 Catch:{ all -> 0x0055 }
        r6 = java.lang.Integer.valueOf(r6);	 Catch:{ all -> 0x0055 }
        r4[r5] = r6;	 Catch:{ all -> 0x0055 }
        r3 = java.lang.String.format(r3, r4);	 Catch:{ all -> 0x0055 }
        r4 = 0;
        r4 = new java.lang.Object[r4];	 Catch:{ all -> 0x0055 }
        com.huluxia.framework.base.log.HLog.warn(r2, r3, r4);	 Catch:{ all -> 0x0055 }
        r2 = 0;
        monitor-exit(r0);	 Catch:{ all -> 0x0055 }
    L_0x004b:
        monitor-exit(r7);
        return r2;
    L_0x004d:
        r1 = com.huluxia.image.base.imagepipeline.image.d.a_isRightVersion(r0);	 Catch:{ all -> 0x0055 }
        monitor-exit(r0);	 Catch:{ all -> 0x005c }
        r0 = r1;
    L_0x0053:
        r2 = r0;
        goto L_0x004b;
    L_0x0055:
        r2 = move-exception;
        r1 = r0;
    L_0x0057:
        monitor-exit(r0);	 Catch:{ all -> 0x005c }
        throw r2;	 Catch:{ all -> 0x0059 }
    L_0x0059:
        r2 = move-exception;
        monitor-exit(r7);
        throw r2;
    L_0x005c:
        r2 = move-exception;
        goto L_0x0057;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huluxia.image.pipeline.cache.r.w(com.huluxia.image.base.cache.common.b):com.huluxia.image.base.imagepipeline.image.d");
    }

    public synchronized boolean x(b key) {
        boolean z = false;
        synchronized (this) {
            Preconditions.checkNotNull(key);
            if (this.Fd.containsKey(key)) {
                d storedEncodedImage = (d) this.Fd.get(key);
                synchronized (storedEncodedImage) {
                    if (d.e(storedEncodedImage)) {
                        z = true;
                    } else {
                        this.Fd.remove(key);
                        HLog.warn(tF, String.format("Found closed reference %d for key %s (%d)", new Object[]{Integer.valueOf(System.identityHashCode(storedEncodedImage)), key.getUriString(), Integer.valueOf(System.identityHashCode(key))}), new Object[0]);
                    }
                }
            }
        }
        return z;
    }

    private synchronized void lI() {
        HLog.debug(tF, "Count = %d", new Object[]{Integer.valueOf(this.Fd.size())});
    }
}
