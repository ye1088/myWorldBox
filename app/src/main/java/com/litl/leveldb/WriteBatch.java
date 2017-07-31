package com.litl.leveldb;

import java.nio.ByteBuffer;

public class WriteBatch extends NativeObject {
    private static native void nativeClear(long j);

    private static native long nativeCreate();

    private static native void nativeDelete(long j, ByteBuffer byteBuffer);

    private static native void nativeDestroy(long j);

    private static native void nativePut(long j, ByteBuffer byteBuffer, ByteBuffer byteBuffer2);

    public /* bridge */ /* synthetic */ void close() {
        super.close();
    }

    public WriteBatch() {
        super(nativeCreate());
    }

    protected void closeNativeObject(long ptr) {
        nativeDestroy(ptr);
    }

    public void delete(ByteBuffer key) {
        if (!assertOpen("WriteBatch is closed, delte")) {
            return;
        }
        if (key == null) {
            throw new NullPointerException("key");
        }
        nativeDelete(this.mPtr, key);
    }

    public void put(ByteBuffer key, ByteBuffer value) {
        if (!assertOpen("WriteBatch is closed, put")) {
            return;
        }
        if (key == null) {
            throw new NullPointerException("key");
        } else if (value == null) {
            throw new NullPointerException("value");
        } else {
            nativePut(this.mPtr, key, value);
        }
    }

    public void clear() {
        if (assertOpen("WriteBatch is closed, clear")) {
            nativeClear(this.mPtr);
        }
    }
}
