package com.MCWorld.image.base.imagepipeline.cache;

import android.graphics.Bitmap;
import android.os.SystemClock;
import com.android.internal.util.Predicate;
import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.framework.base.utils.Supplier;
import com.MCWorld.framework.base.utils.VisibleForTesting;
import com.MCWorld.image.core.common.memory.MemoryTrimType;
import com.MCWorld.image.core.common.memory.a;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
/* compiled from: CountingMemoryCache */
public class d<K, V> implements e<K, V>, a {
    @VisibleForTesting
    static final long vN = TimeUnit.MINUTES.toMillis(5);
    private final g<V> vK;
    @VisibleForTesting
    @GuardedBy("this")
    final c<K, b<K, V>> vO;
    @VisibleForTesting
    @GuardedBy("this")
    final c<K, b<K, V>> vP;
    @VisibleForTesting
    @GuardedBy("this")
    final Map<Bitmap, Object> vQ = new WeakHashMap();
    private final a vR;
    private final Supplier<f> vS;
    @GuardedBy("this")
    protected f vT;
    @GuardedBy("this")
    private long vU;

    @VisibleForTesting
    /* compiled from: CountingMemoryCache */
    static class b<K, V> {
        public int clientCount = 0;
        public final K key;
        public final com.MCWorld.image.core.common.references.a<V> vY;
        public boolean vZ = false;
        @Nullable
        public final c<K> wa;

        private b(K key, com.MCWorld.image.core.common.references.a<V> valueRef, @Nullable c<K> observer) {
            this.key = Preconditions.checkNotNull(key);
            this.vY = (com.MCWorld.image.core.common.references.a) Preconditions.checkNotNull(com.MCWorld.image.core.common.references.a.b((com.MCWorld.image.core.common.references.a) valueRef));
            this.wa = observer;
        }

        @VisibleForTesting
        static <K, V> b<K, V> b(K key, com.MCWorld.image.core.common.references.a<V> valueRef, @Nullable c<K> observer) {
            return new b(key, valueRef, observer);
        }
    }

    public d(g<V> valueDescriptor, a cacheTrimStrategy, Supplier<f> memoryCacheParamsSupplier, com.MCWorld.image.base.imagepipeline.bitmaps.a platformBitmapFactory, boolean isExternalCreatedBitmapLogEnabled) {
        this.vK = valueDescriptor;
        this.vO = new c(a((g) valueDescriptor));
        this.vP = new c(a((g) valueDescriptor));
        this.vR = cacheTrimStrategy;
        this.vS = memoryCacheParamsSupplier;
        this.vT = (f) this.vS.get();
        this.vU = SystemClock.uptimeMillis();
        if (isExternalCreatedBitmapLogEnabled) {
            platformBitmapFactory.a(new 1(this));
        }
    }

    private g<b<K, V>> a(g<V> evictableValueDescriptor) {
        return new 2(this, evictableValueDescriptor);
    }

    public com.MCWorld.image.core.common.references.a<V> a(K key, com.MCWorld.image.core.common.references.a<V> valueRef) {
        return a(key, valueRef, null);
    }

    public com.MCWorld.image.core.common.references.a<V> a(K key, com.MCWorld.image.core.common.references.a<V> valueRef, c<K> observer) {
        b oldExclusive;
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(valueRef);
        hl();
        com.MCWorld.image.core.common.references.a<V> oldRefToClose = null;
        com.MCWorld.image.core.common.references.a<V> clientRef = null;
        synchronized (this) {
            oldExclusive = (b) this.vO.remove(key);
            b oldEntry = (b) this.vP.remove(key);
            if (oldEntry != null) {
                f(oldEntry);
                oldRefToClose = i(oldEntry);
            }
            if (l(valueRef.get())) {
                b newEntry = b.b(key, valueRef, observer);
                this.vP.put(key, newEntry);
                clientRef = a(newEntry);
            }
        }
        com.MCWorld.image.core.common.references.a.c(oldRefToClose);
        d(oldExclusive);
        hm();
        return clientRef;
    }

    private synchronized boolean l(V value) {
        boolean z;
        int newValueSize = this.vK.j(value);
        z = newValueSize <= this.vT.wf && hn() <= this.vT.wc - 1 && ho() <= this.vT.wb - newValueSize;
        return z;
    }

    @Nullable
    public com.MCWorld.image.core.common.references.a<V> m(K key) {
        b oldExclusive;
        Preconditions.checkNotNull(key);
        com.MCWorld.image.core.common.references.a<V> clientRef = null;
        synchronized (this) {
            oldExclusive = (b) this.vO.remove(key);
            b entry = (b) this.vP.get(key);
            if (entry != null) {
                clientRef = a(entry);
            }
        }
        d(oldExclusive);
        hl();
        hm();
        return clientRef;
    }

    private synchronized com.MCWorld.image.core.common.references.a<V> a(b<K, V> entry) {
        g(entry);
        return com.MCWorld.image.core.common.references.a.a(entry.vY.get(), new 3(this, entry));
    }

    private void b(b<K, V> entry) {
        com.MCWorld.image.core.common.references.a<V> oldRefToClose;
        b entry2;
        Preconditions.checkNotNull(entry);
        synchronized (this) {
            h(entry);
            boolean isExclusiveAdded = c((b) entry);
            oldRefToClose = i(entry);
        }
        com.MCWorld.image.core.common.references.a.c(oldRefToClose);
        if (!isExclusiveAdded) {
            entry2 = null;
        }
        e(entry2);
        hl();
        hm();
    }

    private synchronized boolean c(b<K, V> entry) {
        boolean z;
        if (entry.vZ || entry.clientCount != 0) {
            z = false;
        } else {
            this.vO.put(entry.key, entry);
            z = true;
        }
        return z;
    }

    @Nullable
    public com.MCWorld.image.core.common.references.a<V> n(K key) {
        Preconditions.checkNotNull(key);
        com.MCWorld.image.core.common.references.a<V> clientRef = null;
        boolean removed = false;
        synchronized (this) {
            b oldExclusive = (b) this.vO.remove(key);
            if (oldExclusive != null) {
                b<K, V> entry = (b) this.vP.remove(key);
                Preconditions.checkNotNull(entry);
                Preconditions.checkState(entry.clientCount == 0);
                clientRef = entry.vY;
                removed = true;
            }
        }
        if (removed) {
            d(oldExclusive);
        }
        return clientRef;
    }

    public int c(Predicate<K> predicate) {
        ArrayList oldExclusives;
        ArrayList oldEntries;
        synchronized (this) {
            oldExclusives = this.vO.b(predicate);
            oldEntries = this.vP.b(predicate);
            f(oldEntries);
        }
        d(oldEntries);
        e(oldExclusives);
        hl();
        hm();
        return oldEntries.size();
    }

    public void clear() {
        ArrayList oldExclusives;
        ArrayList oldEntries;
        synchronized (this) {
            oldExclusives = this.vO.hk();
            oldEntries = this.vP.hk();
            f(oldEntries);
        }
        d(oldEntries);
        e(oldExclusives);
        hl();
    }

    public synchronized boolean d(Predicate<K> predicate) {
        return !this.vP.a(predicate).isEmpty();
    }

    public void b(MemoryTrimType trimType) {
        ArrayList oldEntries;
        double trimRatio = this.vR.a(trimType);
        synchronized (this) {
            oldEntries = m(Integer.MAX_VALUE, Math.max(0, ((int) (((double) this.vP.hi()) * (1.0d - trimRatio))) - ho()));
            f(oldEntries);
        }
        d(oldEntries);
        e(oldEntries);
        hl();
        hm();
    }

    private synchronized void hl() {
        if (this.vU + vN <= SystemClock.uptimeMillis()) {
            this.vU = SystemClock.uptimeMillis();
            this.vT = (f) this.vS.get();
        }
    }

    private void hm() {
        ArrayList oldEntries;
        synchronized (this) {
            oldEntries = m(Math.min(this.vT.we, this.vT.wc - hn()), Math.min(this.vT.wd, this.vT.wb - ho()));
            f(oldEntries);
        }
        d(oldEntries);
        e(oldEntries);
    }

    @Nullable
    private synchronized ArrayList<b<K, V>> m(int count, int size) {
        ArrayList<b<K, V>> oldEntries;
        count = Math.max(count, 0);
        size = Math.max(size, 0);
        if (this.vO.getCount() > count || this.vO.hi() > size) {
            oldEntries = new ArrayList();
            while (true) {
                if (this.vO.getCount() <= count && this.vO.hi() <= size) {
                    break;
                }
                K key = this.vO.hj();
                this.vO.remove(key);
                oldEntries.add(this.vP.remove(key));
            }
        } else {
            oldEntries = null;
        }
        return oldEntries;
    }

    private void d(@Nullable ArrayList<b<K, V>> oldEntries) {
        if (oldEntries != null) {
            Iterator it = oldEntries.iterator();
            while (it.hasNext()) {
                com.MCWorld.image.core.common.references.a.c(i((b) it.next()));
            }
        }
    }

    private void e(@Nullable ArrayList<b<K, V>> entries) {
        if (entries != null) {
            Iterator it = entries.iterator();
            while (it.hasNext()) {
                d((b) it.next());
            }
        }
    }

    private static <K, V> void d(@Nullable b<K, V> entry) {
        if (entry != null && entry.wa != null) {
            entry.wa.a(entry.key, false);
        }
    }

    private static <K, V> void e(@Nullable b<K, V> entry) {
        if (entry != null && entry.wa != null) {
            entry.wa.a(entry.key, true);
        }
    }

    private synchronized void f(@Nullable ArrayList<b<K, V>> oldEntries) {
        if (oldEntries != null) {
            Iterator it = oldEntries.iterator();
            while (it.hasNext()) {
                f((b) it.next());
            }
        }
    }

    private synchronized void f(b<K, V> entry) {
        boolean z = true;
        synchronized (this) {
            Preconditions.checkNotNull(entry);
            if (entry.vZ) {
                z = false;
            }
            Preconditions.checkState(z);
            entry.vZ = true;
        }
    }

    private synchronized void g(b<K, V> entry) {
        Preconditions.checkNotNull(entry);
        Preconditions.checkState(!entry.vZ);
        entry.clientCount++;
    }

    private synchronized void h(b<K, V> entry) {
        Preconditions.checkNotNull(entry);
        Preconditions.checkState(entry.clientCount > 0);
        entry.clientCount--;
    }

    @Nullable
    private synchronized com.MCWorld.image.core.common.references.a<V> i(b<K, V> entry) {
        com.MCWorld.image.core.common.references.a<V> aVar;
        Preconditions.checkNotNull(entry);
        aVar = (entry.vZ && entry.clientCount == 0) ? entry.vY : null;
        return aVar;
    }

    public synchronized int getCount() {
        return this.vP.getCount();
    }

    public synchronized int hi() {
        return this.vP.hi();
    }

    public synchronized int hn() {
        return this.vP.getCount() - this.vO.getCount();
    }

    public synchronized int ho() {
        return this.vP.hi() - this.vO.hi();
    }

    public synchronized int hp() {
        return this.vO.getCount();
    }

    public synchronized int hq() {
        return this.vO.hi();
    }
}
