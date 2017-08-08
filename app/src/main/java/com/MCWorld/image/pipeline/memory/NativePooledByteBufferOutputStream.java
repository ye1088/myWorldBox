package com.MCWorld.image.pipeline.memory;

import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.framework.base.utils.VisibleForTesting;
import com.MCWorld.image.base.imagepipeline.memory.PooledByteBuffer;
import com.MCWorld.image.base.imagepipeline.memory.f;
import com.MCWorld.image.core.common.references.a;
import java.io.IOException;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class NativePooledByteBufferOutputStream extends f {
    private a<NativeMemoryChunk> HY;
    private final l HZ;
    private int Ht;

    public static class InvalidStreamException extends RuntimeException {
        public InvalidStreamException() {
            super("OutputStream no longer valid");
        }
    }

    public /* synthetic */ PooledByteBuffer ih() {
        return oa();
    }

    public NativePooledByteBufferOutputStream(l pool) {
        this(pool, pool.nY());
    }

    public NativePooledByteBufferOutputStream(l pool, int initialCapacity) {
        boolean z;
        if (initialCapacity > 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z);
        this.HZ = (l) Preconditions.checkNotNull(pool);
        this.Ht = 0;
        this.HY = a.a(this.HZ.get(initialCapacity), this.HZ);
    }

    public m oa() {
        iC();
        return new m(this.HY, this.Ht);
    }

    public int size() {
        return this.Ht;
    }

    public void write(int oneByte) throws IOException {
        write(new byte[]{(byte) oneByte});
    }

    public void write(byte[] buffer, int offset, int count) throws IOException {
        if (offset < 0 || count < 0 || offset + count > buffer.length) {
            throw new ArrayIndexOutOfBoundsException("length=" + buffer.length + "; regionStart=" + offset + "; regionLength=" + count);
        }
        iC();
        cF(this.Ht + count);
        ((NativeMemoryChunk) this.HY.get()).b(this.Ht, buffer, offset, count);
        this.Ht += count;
    }

    public void close() {
        a.c(this.HY);
        this.HY = null;
        this.Ht = -1;
        super.close();
    }

    @VisibleForTesting
    void cF(int newLength) {
        iC();
        if (newLength > ((NativeMemoryChunk) this.HY.get()).getSize()) {
            NativeMemoryChunk newbuf = (NativeMemoryChunk) this.HZ.get(newLength);
            ((NativeMemoryChunk) this.HY.get()).a(0, newbuf, 0, this.Ht);
            this.HY.close();
            this.HY = a.a(newbuf, this.HZ);
        }
    }

    private void iC() {
        if (!a.a(this.HY)) {
            throw new InvalidStreamException();
        }
    }
}
