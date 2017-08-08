package com.MCWorld.image.base.imagepipeline.memory;

import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.framework.base.utils.VisibleForTesting;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* compiled from: PooledByteStreams */
public class g {
    private static final int xj = 16384;
    private final int xk;
    private final a xl;

    public g(a byteArrayPool) {
        this(byteArrayPool, 16384);
    }

    @VisibleForTesting
    g(a byteArrayPool, int tempBufSize) {
        Preconditions.checkArgument(tempBufSize > 0);
        this.xk = tempBufSize;
        this.xl = byteArrayPool;
    }

    public long copy(InputStream from, OutputStream to) throws IOException {
        long count = 0;
        byte[] tmp = (byte[]) this.xl.get(this.xk);
        while (true) {
            int read = from.read(tmp, 0, this.xk);
            if (read == -1) {
                break;
            }
            try {
                to.write(tmp, 0, read);
                count += (long) read;
            } finally {
                this.xl.release(tmp);
            }
        }
        return count;
    }

    public long a(InputStream from, OutputStream to, long bytesToCopy) throws IOException {
        boolean z = false;
        if (bytesToCopy > 0) {
            z = true;
        }
        Preconditions.checkState(z);
        long copied = 0;
        byte[] tmp = (byte[]) this.xl.get(this.xk);
        while (copied < bytesToCopy) {
            int read = from.read(tmp, 0, (int) Math.min((long) this.xk, bytesToCopy - copied));
            if (read == -1) {
                break;
            }
            try {
                to.write(tmp, 0, read);
                copied += (long) read;
            } finally {
                this.xl.release(tmp);
            }
        }
        this.xl.release(tmp);
        return copied;
    }
}
