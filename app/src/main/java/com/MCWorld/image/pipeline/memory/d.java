package com.MCWorld.image.pipeline.memory;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.image.core.common.memory.b;
import javax.annotation.concurrent.ThreadSafe;

@TargetApi(21)
@ThreadSafe
/* compiled from: BitmapPool */
public class d extends BasePool<Bitmap> {
    protected /* synthetic */ void I(Object obj) {
        h((Bitmap) obj);
    }

    protected /* synthetic */ int J(Object obj) {
        return i((Bitmap) obj);
    }

    protected /* synthetic */ boolean K(Object obj) {
        return j((Bitmap) obj);
    }

    protected /* synthetic */ Object cr(int i) {
        return cz(i);
    }

    public d(b memoryTrimmableRegistry, t poolParams, u poolStatsTracker) {
        super(memoryTrimmableRegistry, poolParams, poolStatsTracker);
        initialize();
    }

    protected Bitmap cz(int size) {
        return Bitmap.createBitmap(1, (int) Math.ceil(((double) size) / 2.0d), Config.RGB_565);
    }

    protected void h(Bitmap value) {
        Preconditions.checkNotNull(value);
        value.recycle();
    }

    protected int cs(int requestSize) {
        return requestSize;
    }

    protected int i(Bitmap value) {
        Preconditions.checkNotNull(value);
        return value.getAllocationByteCount();
    }

    protected int ct(int bucketedSize) {
        return bucketedSize;
    }

    protected boolean j(Bitmap value) {
        Preconditions.checkNotNull(value);
        return !value.isRecycled() && value.isMutable();
    }
}
