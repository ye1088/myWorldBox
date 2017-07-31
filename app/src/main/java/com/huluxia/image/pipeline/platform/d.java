package com.huluxia.image.pipeline.platform;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.image.base.imagepipeline.memory.PooledByteBuffer;
import com.huluxia.image.core.common.references.a;
import com.huluxia.image.pipeline.memory.c;
import com.huluxia.image.pipeline.memory.j;
import javax.annotation.concurrent.ThreadSafe;

@TargetApi(19)
@ThreadSafe
/* compiled from: KitKatPurgeableDecoder */
public class d extends b {
    private final j Er;

    public /* bridge */ /* synthetic */ a m(Bitmap bitmap) {
        return super.m(bitmap);
    }

    public d(j flexByteArrayPool, c tracker) {
        super(tracker);
        this.Er = flexByteArrayPool;
    }

    protected Bitmap a(a<PooledByteBuffer> bytesRef, Options options) {
        PooledByteBuffer pooledByteBuffer = (PooledByteBuffer) bytesRef.get();
        int length = pooledByteBuffer.size();
        a<byte[]> encodedBytesArrayRef = this.Er.cA(length);
        try {
            byte[] encodedBytesArray = (byte[]) encodedBytesArrayRef.get();
            pooledByteBuffer.a(0, encodedBytesArray, 0, length);
            Bitmap bitmap = (Bitmap) Preconditions.checkNotNull(BitmapFactory.decodeByteArray(encodedBytesArray, 0, length, options), "BitmapFactory returned null");
            return bitmap;
        } finally {
            a.c(encodedBytesArrayRef);
        }
    }

    protected Bitmap a(a<PooledByteBuffer> bytesRef, int length, Options options) {
        boolean z = false;
        byte[] suffix = b.a((a) bytesRef, length) ? null : Ep;
        PooledByteBuffer pooledByteBuffer = (PooledByteBuffer) bytesRef.get();
        if (length <= pooledByteBuffer.size()) {
            z = true;
        }
        Preconditions.checkArgument(z);
        a<byte[]> encodedBytesArrayRef = this.Er.cA(length + 2);
        try {
            byte[] encodedBytesArray = (byte[]) encodedBytesArrayRef.get();
            pooledByteBuffer.a(0, encodedBytesArray, 0, length);
            if (suffix != null) {
                l(encodedBytesArray, length);
                length += 2;
            }
            Bitmap bitmap = (Bitmap) Preconditions.checkNotNull(BitmapFactory.decodeByteArray(encodedBytesArray, 0, length, options), "BitmapFactory returned null");
            return bitmap;
        } finally {
            a.c(encodedBytesArrayRef);
        }
    }

    private static void l(byte[] imageBytes, int offset) {
        imageBytes[offset] = (byte) -1;
        imageBytes[offset + 1] = (byte) -39;
    }
}
