package com.litl.leveldb;

import android.text.TextUtils;
import android.util.Log;
import com.huluxia.framework.base.log.HLog;
import java.io.Closeable;

abstract class NativeObject implements Closeable {
    private static final String TAG = NativeObject.class.getSimpleName();
    protected long mPtr;
    private int mRefCount;

    protected abstract void closeNativeObject(long j);

    protected NativeObject() {
        this.mRefCount = 0;
        ref();
    }

    protected NativeObject(long ptr) {
        this();
        if (ptr == 0) {
            throw new OutOfMemoryError("Failed to allocate native object");
        }
        this.mPtr = ptr;
    }

    protected synchronized long getPtr() {
        return this.mPtr;
    }

    protected boolean assertOpen(String message) {
        if (getPtr() != 0) {
            return true;
        }
        HLog.error(TAG, "assertOpen invalid, message = " + message, new Object[0]);
        return false;
    }

    synchronized void ref() {
        this.mRefCount++;
    }

    synchronized void unref() {
        if (this.mRefCount <= 0) {
            throw new IllegalStateException("Reference count is already 0");
        }
        this.mRefCount--;
        if (this.mRefCount == 0) {
            closeNativeObject(this.mPtr);
            this.mPtr = 0;
        }
    }

    public synchronized void close() {
        if (this.mPtr != 0) {
            unref();
        }
    }

    protected void finalize() throws Throwable {
        if (this.mPtr != 0) {
            Class<?> clazz = getClass();
            String name = clazz.getSimpleName();
            while (TextUtils.isEmpty(name)) {
                clazz = clazz.getSuperclass();
                name = clazz.getSimpleName();
            }
            Log.w(TAG, "NativeObject " + name + " refcount: " + this.mRefCount + " id: " + System.identityHashCode(this) + " was finalized before native resource was closed, did you forget to call close()?");
        }
        super.finalize();
    }
}
