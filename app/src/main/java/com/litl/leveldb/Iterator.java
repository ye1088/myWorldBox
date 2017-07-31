package com.litl.leveldb;

public class Iterator extends NativeObject {
    private static native void nativeDestroy(long j);

    private static native byte[] nativeKey(long j);

    private static native void nativeNext(long j);

    private static native void nativePrev(long j);

    private static native void nativeSeek(long j, byte[] bArr);

    private static native void nativeSeekToFirst(long j);

    private static native void nativeSeekToLast(long j);

    private static native boolean nativeValid(long j);

    private static native byte[] nativeValue(long j);

    public /* bridge */ /* synthetic */ void close() {
        super.close();
    }

    Iterator(long iterPtr) {
        super(iterPtr);
    }

    protected void closeNativeObject(long ptr) {
        nativeDestroy(ptr);
    }

    public void seekToFirst() {
        if (assertOpen("Iterator is closed, seektofirst")) {
            nativeSeekToFirst(this.mPtr);
        }
    }

    public void seekToLast() {
        if (assertOpen("Iterator is closed, seektolast")) {
            nativeSeekToLast(this.mPtr);
        }
    }

    public void seek(byte[] target) {
        if (!assertOpen("Iterator is closed, seek")) {
            return;
        }
        if (target == null) {
            throw new IllegalArgumentException();
        }
        nativeSeek(this.mPtr, target);
    }

    public boolean isValid() {
        if (assertOpen("Iterator is closed, isValid")) {
            return nativeValid(this.mPtr);
        }
        return false;
    }

    public void next() {
        if (assertOpen("Iterator is closed, next")) {
            nativeNext(this.mPtr);
        }
    }

    public void prev() {
        if (assertOpen("Iterator is closed, prev")) {
            nativePrev(this.mPtr);
        }
    }

    public byte[] getKey() {
        if (assertOpen("Iterator is closed, getkey")) {
            return nativeKey(this.mPtr);
        }
        return new byte[0];
    }

    public byte[] getValue() {
        if (assertOpen("Iterator is closed, getvalue")) {
            return nativeValue(this.mPtr);
        }
        return new byte[0];
    }
}
