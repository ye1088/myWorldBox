package com.huluxia.image.pipeline.memory;

import android.util.SparseIntArray;
import com.huluxia.framework.base.utils.Preconditions;
import javax.annotation.Nullable;

/* compiled from: PoolParams */
public class t {
    public static final int Io = -1;
    public final int Ip;
    public final int Iq;
    public final SparseIntArray Ir;
    public final int Is;
    public final int It;
    public final int Iu;

    public t(int maxSize, @Nullable SparseIntArray bucketSizes) {
        this(maxSize, maxSize, bucketSizes, 0, Integer.MAX_VALUE, -1);
    }

    public t(int maxSizeSoftCap, int maxSizeHardCap, @Nullable SparseIntArray bucketSizes) {
        this(maxSizeSoftCap, maxSizeHardCap, bucketSizes, 0, Integer.MAX_VALUE, -1);
    }

    public t(int maxSizeSoftCap, int maxSizeHardCap, @Nullable SparseIntArray bucketSizes, int minBucketSize, int maxBucketSize, int maxNumThreads) {
        boolean z = maxSizeSoftCap >= 0 && maxSizeHardCap >= maxSizeSoftCap;
        Preconditions.checkState(z);
        this.Iq = maxSizeSoftCap;
        this.Ip = maxSizeHardCap;
        this.Ir = bucketSizes;
        this.Is = minBucketSize;
        this.It = maxBucketSize;
        this.Iu = maxNumThreads;
    }
}
