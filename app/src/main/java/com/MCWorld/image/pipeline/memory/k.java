package com.MCWorld.image.pipeline.memory;

import android.util.SparseIntArray;
import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.image.base.imagepipeline.memory.a;
import com.MCWorld.image.core.common.memory.b;

import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
/* compiled from: GenericByteArrayPool */
public class k extends BasePool<byte[]> implements a {
    private final int[] HW;

    protected /* synthetic */ void I(Object obj) {
        t((byte[]) obj);
    }

    protected /* synthetic */ int J(Object obj) {
        return u((byte[]) obj);
    }

    protected /* synthetic */ Object cr(int i) {
        return cB(i);
    }

    public k(b memoryTrimmableRegistry, t poolParams, u poolStatsTracker) {
        super(memoryTrimmableRegistry, poolParams, poolStatsTracker);
        SparseIntArray bucketSizes = poolParams.Ir;
        this.HW = new int[bucketSizes.size()];
        for (int i = 0; i < bucketSizes.size(); i++) {
            this.HW[i] = bucketSizes.keyAt(i);
        }
        initialize();
    }

    public int nY() {
        return this.HW[0];
    }

    protected byte[] cB(int bucketedSize) {
        return new byte[bucketedSize];
    }

    protected void t(byte[] value) {
        Preconditions.checkNotNull(value);
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

    protected int u(byte[] value) {
        Preconditions.checkNotNull(value);
        return value.length;
    }
}
