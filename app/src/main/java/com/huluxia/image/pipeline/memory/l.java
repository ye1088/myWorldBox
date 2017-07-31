package com.huluxia.image.pipeline.memory;

import android.util.SparseIntArray;
import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.image.core.common.memory.b;
import com.huluxia.image.pipeline.memory.BasePool.InvalidSizeException;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
/* compiled from: NativeMemoryChunkPool */
public class l extends BasePool<NativeMemoryChunk> {
    private final int[] HW;

    protected /* synthetic */ void I(Object obj) {
        a((NativeMemoryChunk) obj);
    }

    protected /* synthetic */ int J(Object obj) {
        return b((NativeMemoryChunk) obj);
    }

    protected /* synthetic */ boolean K(Object obj) {
        return c((NativeMemoryChunk) obj);
    }

    protected /* synthetic */ Object cr(int i) {
        return cC(i);
    }

    public l(b memoryTrimmableRegistry, t poolParams, u nativeMemoryChunkPoolStatsTracker) {
        super(memoryTrimmableRegistry, poolParams, nativeMemoryChunkPoolStatsTracker);
        SparseIntArray bucketSizes = poolParams.Ir;
        this.HW = new int[bucketSizes.size()];
        for (int i = 0; i < this.HW.length; i++) {
            this.HW[i] = bucketSizes.keyAt(i);
        }
        initialize();
    }

    public int nY() {
        return this.HW[0];
    }

    protected NativeMemoryChunk cC(int bucketedSize) {
        return new NativeMemoryChunk(bucketedSize);
    }

    protected void a(NativeMemoryChunk value) {
        Preconditions.checkNotNull(value);
        value.close();
    }

    protected int ct(int bucketedSize) {
        return bucketedSize;
    }

    protected int cs(int requestSize) {
        int intRequestSize = requestSize;
        if (intRequestSize <= 0) {
            throw new InvalidSizeException(Integer.valueOf(requestSize));
        }
        for (int bucketedSize : this.HW) {
            if (bucketedSize >= intRequestSize) {
                return bucketedSize;
            }
        }
        return requestSize;
    }

    protected int b(NativeMemoryChunk value) {
        Preconditions.checkNotNull(value);
        return value.getSize();
    }

    protected boolean c(NativeMemoryChunk value) {
        Preconditions.checkNotNull(value);
        return !value.isClosed();
    }
}
