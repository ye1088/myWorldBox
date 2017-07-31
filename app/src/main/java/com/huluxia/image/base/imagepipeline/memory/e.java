package com.huluxia.image.base.imagepipeline.memory;

import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.framework.base.utils.VisibleForTesting;
import java.io.InputStream;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
/* compiled from: PooledByteBufferInputStream */
public class e extends InputStream {
    @VisibleForTesting
    int mOffset;
    @VisibleForTesting
    final PooledByteBuffer xh;
    @VisibleForTesting
    int xi;

    public e(PooledByteBuffer pooledByteBuffer) {
        boolean z;
        if (pooledByteBuffer.isClosed()) {
            z = false;
        } else {
            z = true;
        }
        Preconditions.checkArgument(z);
        this.xh = (PooledByteBuffer) Preconditions.checkNotNull(pooledByteBuffer);
        this.mOffset = 0;
        this.xi = 0;
    }

    public int available() {
        return this.xh.size() - this.mOffset;
    }

    public void mark(int readlimit) {
        this.xi = this.mOffset;
    }

    public boolean markSupported() {
        return true;
    }

    public int read() {
        if (available() <= 0) {
            return -1;
        }
        PooledByteBuffer pooledByteBuffer = this.xh;
        int i = this.mOffset;
        this.mOffset = i + 1;
        return pooledByteBuffer.bs(i) & 255;
    }

    public int read(byte[] buffer) {
        return read(buffer, 0, buffer.length);
    }

    public int read(byte[] buffer, int offset, int length) {
        if (offset < 0 || length < 0 || offset + length > buffer.length) {
            throw new ArrayIndexOutOfBoundsException("length=" + buffer.length + "; regionStart=" + offset + "; regionLength=" + length);
        }
        int available = available();
        if (available <= 0) {
            return -1;
        }
        if (length <= 0) {
            return 0;
        }
        int numToRead = Math.min(available, length);
        this.xh.a(this.mOffset, buffer, offset, numToRead);
        this.mOffset += numToRead;
        return numToRead;
    }

    public void reset() {
        this.mOffset = this.xi;
    }

    public long skip(long byteCount) {
        Preconditions.checkArgument(byteCount >= 0);
        int skipped = Math.min((int) byteCount, available());
        this.mOffset += skipped;
        return (long) skipped;
    }
}
