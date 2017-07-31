package com.huluxia.image.pipeline.memory;

import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.framework.base.utils.VisibleForTesting;
import com.huluxia.image.base.imagepipeline.memory.PooledByteBuffer;
import com.huluxia.image.base.imagepipeline.memory.PooledByteBuffer.ClosedException;
import com.huluxia.image.core.common.references.a;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
/* compiled from: NativePooledByteBuffer */
public class m implements PooledByteBuffer {
    @VisibleForTesting
    @GuardedBy("this")
    a<NativeMemoryChunk> HY;
    private final int mSize;

    public m(a<NativeMemoryChunk> bufRef, int size) {
        Preconditions.checkNotNull(bufRef);
        boolean z = size >= 0 && size <= ((NativeMemoryChunk) bufRef.get()).getSize();
        Preconditions.checkArgument(z);
        this.HY = bufRef.iu();
        this.mSize = size;
    }

    public synchronized int size() {
        iC();
        return this.mSize;
    }

    public synchronized byte bs(int offset) {
        byte bs;
        boolean z = true;
        synchronized (this) {
            boolean z2;
            iC();
            if (offset >= 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            Preconditions.checkArgument(z2);
            if (offset >= this.mSize) {
                z = false;
            }
            Preconditions.checkArgument(z);
            bs = ((NativeMemoryChunk) this.HY.get()).bs(offset);
        }
        return bs;
    }

    public synchronized void a(int offset, byte[] buffer, int bufferOffset, int length) {
        iC();
        Preconditions.checkArgument(offset + length <= this.mSize);
        ((NativeMemoryChunk) this.HY.get()).c(offset, buffer, bufferOffset, length);
    }

    public synchronized long if() {
        iC();
        return ((NativeMemoryChunk) this.HY.get()).if();
    }

    public synchronized boolean isClosed() {
        return !a.a(this.HY);
    }

    public synchronized void close() {
        a.c(this.HY);
        this.HY = null;
    }

    synchronized void iC() {
        if (isClosed()) {
            throw new ClosedException();
        }
    }
}
