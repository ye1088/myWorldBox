package com.huluxia.image.pipeline.memory;

import android.util.Log;
import com.huluxia.framework.base.utils.DoNotStrip;
import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.framework.base.utils.VisibleForTesting;
import com.huluxia.image.pipeline.nativecode.a;
import java.io.Closeable;

@DoNotStrip
public class NativeMemoryChunk implements Closeable {
    private static final String TAG = "NativeMemoryChunk";
    private final long HX;
    private boolean mClosed;
    private final int mSize;

    @DoNotStrip
    private static native long nativeAllocate(int i);

    @DoNotStrip
    private static native void nativeCopyFromByteArray(long j, byte[] bArr, int i, int i2);

    @DoNotStrip
    private static native void nativeCopyToByteArray(long j, byte[] bArr, int i, int i2);

    @DoNotStrip
    private static native void nativeFree(long j);

    @DoNotStrip
    private static native void nativeMemcpy(long j, long j2, int i);

    @DoNotStrip
    private static native byte nativeReadByte(long j);

    static {
        a.load();
    }

    public NativeMemoryChunk(int size) {
        boolean z;
        if (size > 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z);
        this.mSize = size;
        this.HX = nativeAllocate(this.mSize);
        this.mClosed = false;
    }

    @VisibleForTesting
    public NativeMemoryChunk() {
        this.mSize = 0;
        this.HX = 0;
        this.mClosed = true;
    }

    public synchronized void close() {
        if (!this.mClosed) {
            this.mClosed = true;
            nativeFree(this.HX);
        }
    }

    public synchronized boolean isClosed() {
        return this.mClosed;
    }

    public int getSize() {
        return this.mSize;
    }

    public synchronized int b(int nativeMemoryOffset, byte[] byteArray, int byteArrayOffset, int count) {
        int actualCount;
        Preconditions.checkNotNull(byteArray);
        Preconditions.checkState(!isClosed());
        actualCount = s(nativeMemoryOffset, count);
        c(nativeMemoryOffset, byteArray.length, byteArrayOffset, actualCount);
        nativeCopyFromByteArray(this.HX + ((long) nativeMemoryOffset), byteArray, byteArrayOffset, actualCount);
        return actualCount;
    }

    public synchronized int c(int nativeMemoryOffset, byte[] byteArray, int byteArrayOffset, int count) {
        int actualCount;
        Preconditions.checkNotNull(byteArray);
        Preconditions.checkState(!isClosed());
        actualCount = s(nativeMemoryOffset, count);
        c(nativeMemoryOffset, byteArray.length, byteArrayOffset, actualCount);
        nativeCopyToByteArray(this.HX + ((long) nativeMemoryOffset), byteArray, byteArrayOffset, actualCount);
        return actualCount;
    }

    public synchronized byte bs(int offset) {
        byte nativeReadByte;
        boolean z = true;
        synchronized (this) {
            boolean z2;
            if (isClosed()) {
                z2 = false;
            } else {
                z2 = true;
            }
            Preconditions.checkState(z2);
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
            nativeReadByte = nativeReadByte(this.HX + ((long) offset));
        }
        return nativeReadByte;
    }

    public void a(int offset, NativeMemoryChunk other, int otherOffset, int count) {
        Preconditions.checkNotNull(other);
        if (other.HX == this.HX) {
            Log.w(TAG, "Copying from NativeMemoryChunk " + Integer.toHexString(System.identityHashCode(this)) + " to NativeMemoryChunk " + Integer.toHexString(System.identityHashCode(other)) + " which share the same address " + Long.toHexString(this.HX));
            Preconditions.checkArgument(false);
        }
        if (other.HX < this.HX) {
            synchronized (other) {
                synchronized (this) {
                    b(offset, other, otherOffset, count);
                }
            }
            return;
        }
        synchronized (this) {
            synchronized (other) {
                b(offset, other, otherOffset, count);
            }
        }
    }

    public long if() {
        return this.HX;
    }

    private void b(int offset, NativeMemoryChunk other, int otherOffset, int count) {
        boolean z;
        boolean z2 = true;
        if (isClosed()) {
            z = false;
        } else {
            z = true;
        }
        Preconditions.checkState(z);
        if (other.isClosed()) {
            z2 = false;
        }
        Preconditions.checkState(z2);
        c(offset, other.mSize, otherOffset, count);
        nativeMemcpy(other.HX + ((long) otherOffset), this.HX + ((long) offset), count);
    }

    protected void finalize() throws Throwable {
        if (!isClosed()) {
            Log.w(TAG, "finalize: Chunk " + Integer.toHexString(System.identityHashCode(this)) + " still active. Underlying address = " + Long.toHexString(this.HX));
            try {
                close();
            } finally {
                super.finalize();
            }
        }
    }

    private int s(int offset, int count) {
        return Math.min(Math.max(0, this.mSize - offset), count);
    }

    private void c(int myOffset, int otherLength, int otherOffset, int count) {
        boolean z;
        boolean z2 = true;
        Preconditions.checkArgument(count >= 0);
        if (myOffset >= 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z);
        if (otherOffset >= 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z);
        if (myOffset + count <= this.mSize) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z);
        if (otherOffset + count > otherLength) {
            z2 = false;
        }
        Preconditions.checkArgument(z2);
    }
}
