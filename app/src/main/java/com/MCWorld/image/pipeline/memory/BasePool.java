package com.MCWorld.image.pipeline.memory;

import android.annotation.SuppressLint;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.framework.base.utils.Sets;
import com.MCWorld.framework.base.utils.Throwables;
import com.MCWorld.framework.base.utils.VisibleForTesting;
import com.MCWorld.image.base.imagepipeline.memory.b;
import com.MCWorld.image.core.common.memory.MemoryTrimType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.NotThreadSafe;

public abstract class BasePool<V> implements b<V> {
    final com.MCWorld.image.core.common.memory.b FC;
    final t Hm;
    @VisibleForTesting
    final SparseArray<e<V>> Hn;
    @VisibleForTesting
    final Set<V> Ho;
    private boolean Hp;
    @VisibleForTesting
    @GuardedBy("this")
    final a Hq;
    @VisibleForTesting
    @GuardedBy("this")
    final a Hr;
    private final u Hs;
    private final Class<?> tF = getClass();

    public static class InvalidSizeException extends RuntimeException {
        public InvalidSizeException(Object size) {
            super("Invalid size: " + size.toString());
        }
    }

    public static class InvalidValueException extends RuntimeException {
        public InvalidValueException(Object value) {
            super("Invalid value: " + value.toString());
        }
    }

    public static class PoolSizeViolationException extends RuntimeException {
        public PoolSizeViolationException(int hardCap, int usedBytes, int freeBytes, int allocSize) {
            super("Pool hard cap violation? Hard cap = " + hardCap + " Used size = " + usedBytes + " Free size = " + freeBytes + " Request size = " + allocSize);
        }
    }

    public static class SizeTooLargeException extends InvalidSizeException {
        public SizeTooLargeException(Object size) {
            super(size);
        }
    }

    @VisibleForTesting
    @NotThreadSafe
    static class a {
        private static final String TAG = "com.huluxia.image.pipeline.memory.BasePool.Counter";
        int Ht;
        int Hu;

        a() {
        }

        public void cx(int numBytes) {
            this.Ht++;
            this.Hu += numBytes;
        }

        public void cy(int numBytes) {
            if (this.Hu < numBytes || this.Ht <= 0) {
                HLog.warn(TAG, String.format("Unexpected decrement of %d. Current numBytes = %d, count = %d", new Object[]{Integer.valueOf(numBytes), Integer.valueOf(this.Hu), Integer.valueOf(this.Ht)}), new Object[0]);
                return;
            }
            this.Ht--;
            this.Hu -= numBytes;
        }

        public void reset() {
            this.Ht = 0;
            this.Hu = 0;
        }
    }

    @VisibleForTesting
    protected abstract void I(V v);

    protected abstract int J(V v);

    protected abstract V cr(int i);

    protected abstract int cs(int i);

    protected abstract int ct(int i);

    public BasePool(com.MCWorld.image.core.common.memory.b memoryTrimmableRegistry, t poolParams, u poolStatsTracker) {
        this.FC = (com.MCWorld.image.core.common.memory.b) Preconditions.checkNotNull(memoryTrimmableRegistry);
        this.Hm = (t) Preconditions.checkNotNull(poolParams);
        this.Hs = (u) Preconditions.checkNotNull(poolStatsTracker);
        this.Hn = new SparseArray();
        a(new SparseIntArray(0));
        this.Ho = Sets.newIdentityHashSet();
        this.Hr = new a();
        this.Hq = new a();
    }

    protected void initialize() {
        this.FC.a(this);
        this.Hs.a(this);
    }

    public V get(int size) {
        V value;
        nL();
        int bucketedSize = cs(size);
        synchronized (this) {
            int sizeInBytes;
            e<V> bucket = cu(bucketedSize);
            if (bucket != null) {
                value = bucket.get();
                if (value != null) {
                    Preconditions.checkState(this.Ho.add(value));
                    sizeInBytes = ct(J(value));
                    this.Hq.cx(sizeInBytes);
                    this.Hr.cy(sizeInBytes);
                    this.Hs.cG(sizeInBytes);
                    lI();
                    if (HLog.isImageLoggable(1)) {
                        HLog.verbose(this.tF, "get (reuse) (object, size) = (%x, %s)", new Object[]{Integer.valueOf(System.identityHashCode(value)), Integer.valueOf(bucketedSize)});
                    }
                }
            }
            sizeInBytes = ct(bucketedSize);
            if (cw(sizeInBytes)) {
                this.Hq.cx(sizeInBytes);
                if (bucket != null) {
                    bucket.nU();
                }
                value = null;
                try {
                    value = cr(bucketedSize);
                } catch (Throwable e) {
                    synchronized (this) {
                        this.Hq.cy(sizeInBytes);
                        bucket = cu(bucketedSize);
                        if (bucket != null) {
                            bucket.nV();
                        }
                        Throwables.propagateIfPossible(e);
                    }
                    return value;
                }
                synchronized (this) {
                    Preconditions.checkState(this.Ho.add(value));
                    nM();
                    this.Hs.cH(sizeInBytes);
                    lI();
                    if (HLog.isImageLoggable(1)) {
                        HLog.verbose(this.tF, "get (alloc) (object, size) = (%x, %s)", new Object[]{Integer.valueOf(System.identityHashCode(value)), Integer.valueOf(bucketedSize)});
                    }
                }
            } else {
                throw new PoolSizeViolationException(this.Hm.Ip, this.Hq.Hu, this.Hr.Hu, sizeInBytes);
            }
        }
        return value;
    }

    public void release(V value) {
        Preconditions.checkNotNull(value);
        int bucketedSize = J(value);
        int sizeInBytes = ct(bucketedSize);
        synchronized (this) {
            e<V> bucket = cu(bucketedSize);
            if (!this.Ho.remove(value)) {
                HLog.error(this.tF, "release (free, value unrecognized) (object, size) = (%x, %s)", new Object[]{Integer.valueOf(System.identityHashCode(value)), Integer.valueOf(bucketedSize)});
                I(value);
                this.Hs.cI(sizeInBytes);
            } else if (bucket == null || bucket.nS() || nN() || !K(value)) {
                if (bucket != null) {
                    bucket.nV();
                }
                if (HLog.isImageLoggable(1)) {
                    HLog.verbose(this.tF, "release (free) (object, size) = (%x, %s)", new Object[]{Integer.valueOf(System.identityHashCode(value)), Integer.valueOf(bucketedSize)});
                }
                I(value);
                this.Hq.cy(sizeInBytes);
                this.Hs.cI(sizeInBytes);
            } else {
                bucket.release(value);
                this.Hr.cx(sizeInBytes);
                this.Hq.cy(sizeInBytes);
                this.Hs.cJ(sizeInBytes);
                if (HLog.isImageLoggable(1)) {
                    HLog.verbose(this.tF, "release (reuse) (object, size) = (%x, %s)", new Object[]{Integer.valueOf(System.identityHashCode(value)), Integer.valueOf(bucketedSize)});
                }
            }
            lI();
        }
    }

    public void b(MemoryTrimType memoryTrimType) {
        gR();
    }

    protected void nK() {
    }

    protected boolean K(V value) {
        Preconditions.checkNotNull(value);
        return true;
    }

    private synchronized void nL() {
        boolean z = !nN() || this.Hr.Hu == 0;
        Preconditions.checkState(z);
    }

    private synchronized void a(SparseIntArray inUseCounts) {
        Preconditions.checkNotNull(inUseCounts);
        this.Hn.clear();
        SparseIntArray bucketSizes = this.Hm.Ir;
        if (bucketSizes != null) {
            for (int i = 0; i < bucketSizes.size(); i++) {
                int bucketSize = bucketSizes.keyAt(i);
                this.Hn.put(bucketSize, new e(ct(bucketSize), bucketSizes.valueAt(i), inUseCounts.get(bucketSize, 0)));
            }
            this.Hp = false;
        } else {
            this.Hp = true;
        }
    }

    @VisibleForTesting
    void gR() {
        List<e<V>> bucketsToTrim = new ArrayList(this.Hn.size());
        SparseIntArray inUseCounts = new SparseIntArray();
        synchronized (this) {
            int i;
            for (i = 0; i < this.Hn.size(); i++) {
                e<V> bucket = (e) this.Hn.valueAt(i);
                if (bucket.nT() > 0) {
                    bucketsToTrim.add(bucket);
                }
                inUseCounts.put(this.Hn.keyAt(i), bucket.hn());
            }
            a(inUseCounts);
            this.Hr.reset();
            lI();
        }
        nK();
        for (i = 0; i < bucketsToTrim.size(); i++) {
            bucket = (e) bucketsToTrim.get(i);
            while (true) {
                V item = bucket.pop();
                if (item == null) {
                    break;
                }
                I(item);
            }
        }
    }

    @VisibleForTesting
    synchronized void nM() {
        if (nN()) {
            trimToSize(this.Hm.Iq);
        }
    }

    @VisibleForTesting
    synchronized void trimToSize(int targetSize) {
        int bytesToFree = Math.min((this.Hq.Hu + this.Hr.Hu) - targetSize, this.Hr.Hu);
        if (bytesToFree > 0) {
            if (HLog.isImageLoggable(1)) {
                HLog.verbose(this.tF, String.format("trimToSize: TargetSize = %d; Initial Size = %d; Bytes to free = %d", new Object[]{Integer.valueOf(targetSize), Integer.valueOf(this.Hq.Hu + this.Hr.Hu), Integer.valueOf(bytesToFree)}), new Object[0]);
            }
            lI();
            for (int i = 0; i < this.Hn.size() && bytesToFree > 0; i++) {
                e<V> bucket = (e) this.Hn.valueAt(i);
                while (bytesToFree > 0) {
                    V value = bucket.pop();
                    if (value == null) {
                        break;
                    }
                    I(value);
                    bytesToFree -= bucket.HF;
                    this.Hr.cy(bucket.HF);
                }
            }
            lI();
            if (HLog.isImageLoggable(1)) {
                HLog.verbose(this.tF, String.format("trimToSize: TargetSize = %d; Final Size = %d", new Object[]{Integer.valueOf(targetSize), Integer.valueOf(this.Hq.Hu + this.Hr.Hu)}), new Object[0]);
            }
        }
    }

    @VisibleForTesting
    synchronized e<V> cu(int bucketedSize) {
        e<V> newBucket;
        e<V> bucket = (e) this.Hn.get(bucketedSize);
        if (bucket == null && this.Hp) {
            if (HLog.isImageLoggable(1)) {
                HLog.verbose(this.tF, String.format("creating new bucket %s", new Object[]{Integer.valueOf(bucketedSize)}), new Object[0]);
            }
            newBucket = cv(bucketedSize);
            this.Hn.put(bucketedSize, newBucket);
        } else {
            newBucket = bucket;
        }
        return newBucket;
    }

    e<V> cv(int bucketedSize) {
        return new e(ct(bucketedSize), Integer.MAX_VALUE, 0);
    }

    @VisibleForTesting
    synchronized boolean nN() {
        boolean isMaxSizeSoftCapExceeded;
        isMaxSizeSoftCapExceeded = this.Hq.Hu + this.Hr.Hu > this.Hm.Iq;
        if (isMaxSizeSoftCapExceeded) {
            this.Hs.od();
        }
        return isMaxSizeSoftCapExceeded;
    }

    @VisibleForTesting
    synchronized boolean cw(int sizeInBytes) {
        boolean z = false;
        synchronized (this) {
            int hardCap = this.Hm.Ip;
            if (sizeInBytes > hardCap - this.Hq.Hu) {
                this.Hs.oe();
            } else {
                int softCap = this.Hm.Iq;
                if (sizeInBytes > softCap - (this.Hq.Hu + this.Hr.Hu)) {
                    trimToSize(softCap - sizeInBytes);
                }
                if (sizeInBytes > hardCap - (this.Hq.Hu + this.Hr.Hu)) {
                    this.Hs.oe();
                } else {
                    z = true;
                }
            }
        }
        return z;
    }

    @SuppressLint({"InvalidAccessToGuardedField"})
    private void lI() {
        if (HLog.isImageLoggable(1)) {
            HLog.verbose(this.tF, String.format("Used = (%d, %d); Free = (%d, %d)", new Object[]{Integer.valueOf(this.Hq.Ht), Integer.valueOf(this.Hq.Hu), Integer.valueOf(this.Hr.Ht), Integer.valueOf(this.Hr.Hu)}), new Object[0]);
        }
    }

    public synchronized Map<String, Integer> nO() {
        Map<String, Integer> stats;
        stats = new HashMap();
        for (int i = 0; i < this.Hn.size(); i++) {
            stats.put(u.Iv + ct(this.Hn.keyAt(i)), Integer.valueOf(((e) this.Hn.valueAt(i)).hn()));
        }
        stats.put(u.IA, Integer.valueOf(this.Hm.Iq));
        stats.put(u.IB, Integer.valueOf(this.Hm.Ip));
        stats.put(u.Iw, Integer.valueOf(this.Hq.Ht));
        stats.put(u.Ix, Integer.valueOf(this.Hq.Hu));
        stats.put(u.Iy, Integer.valueOf(this.Hr.Ht));
        stats.put(u.Iz, Integer.valueOf(this.Hr.Hu));
        return stats;
    }
}
