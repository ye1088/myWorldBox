package com.MCWorld.image.base.imagepipeline.memory;

import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.Preconditions;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
/* compiled from: PooledByteArrayBufferedInputStream */
public class c extends InputStream {
    private static final String TAG = "PooledByteInputStream";
    private boolean mClosed = false;
    private final InputStream mInputStream;
    private final byte[] xd;
    private final com.MCWorld.image.core.common.references.c<byte[]> xe;
    private int xf = 0;
    private int xg = 0;

    public c(InputStream inputStream, byte[] byteArray, com.MCWorld.image.core.common.references.c<byte[]> resourceReleaser) {
        this.mInputStream = (InputStream) Preconditions.checkNotNull(inputStream);
        this.xd = (byte[]) Preconditions.checkNotNull(byteArray);
        this.xe = (com.MCWorld.image.core.common.references.c) Preconditions.checkNotNull(resourceReleaser);
    }

    public int read() throws IOException {
        Preconditions.checkState(this.xg <= this.xf);
        ie();
        if (!ic()) {
            return -1;
        }
        byte[] bArr = this.xd;
        int i = this.xg;
        this.xg = i + 1;
        return bArr[i] & 255;
    }

    public int read(byte[] buffer, int offset, int length) throws IOException {
        Preconditions.checkState(this.xg <= this.xf);
        ie();
        if (!ic()) {
            return -1;
        }
        int bytesToRead = Math.min(this.xf - this.xg, length);
        System.arraycopy(this.xd, this.xg, buffer, offset, bytesToRead);
        this.xg += bytesToRead;
        return bytesToRead;
    }

    public int available() throws IOException {
        Preconditions.checkState(this.xg <= this.xf);
        ie();
        return (this.xf - this.xg) + this.mInputStream.available();
    }

    public void close() throws IOException {
        if (!this.mClosed) {
            this.mClosed = true;
            this.xe.release(this.xd);
            super.close();
        }
    }

    public long skip(long byteCount) throws IOException {
        Preconditions.checkState(this.xg <= this.xf);
        ie();
        int bytesLeftInBuffer = this.xf - this.xg;
        if (((long) bytesLeftInBuffer) >= byteCount) {
            this.xg = (int) (((long) this.xg) + byteCount);
            return byteCount;
        }
        this.xg = this.xf;
        return ((long) bytesLeftInBuffer) + this.mInputStream.skip(byteCount - ((long) bytesLeftInBuffer));
    }

    private boolean ic() throws IOException {
        if (this.xg < this.xf) {
            return true;
        }
        int readData = this.mInputStream.read(this.xd);
        if (readData <= 0) {
            return false;
        }
        this.xf = readData;
        this.xg = 0;
        return true;
    }

    private void ie() throws IOException {
        if (this.mClosed) {
            throw new IOException("stream already closed");
        }
    }

    protected void finalize() throws Throwable {
        if (!this.mClosed) {
            HLog.error(TAG, "Finalized without closing", new Object[0]);
            close();
        }
        super.finalize();
    }
}
