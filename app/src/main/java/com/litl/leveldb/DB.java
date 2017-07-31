package com.litl.leveldb;

import java.io.File;
import java.nio.ByteBuffer;

public class DB extends NativeObject {
    private boolean mDestroyOnClose = false;
    private final File mPath;

    public static abstract class Snapshot extends NativeObject {
        public /* bridge */ /* synthetic */ void close() {
            super.close();
        }

        Snapshot(long ptr) {
            super(ptr);
        }
    }

    private static native void nativeClose(long j);

    private static native void nativeDelete(long j, byte[] bArr);

    private static native void nativeDestroy(String str);

    private static native byte[] nativeGet(long j, long j2, ByteBuffer byteBuffer);

    private static native byte[] nativeGet(long j, long j2, byte[] bArr);

    private static native long nativeGetSnapshot(long j);

    private static native long nativeIterator(long j, long j2);

    private static native long nativeOpen(String str);

    private static native void nativePut(long j, byte[] bArr, byte[] bArr2);

    private static native void nativeReleaseSnapshot(long j, long j2);

    private static native void nativeWrite(long j, long j2);

    public static native String stringFromJNI();

    public /* bridge */ /* synthetic */ void close() {
        super.close();
    }

    public DB(File path) {
        System.loadLibrary("leveldbjni");
        if (path == null) {
            throw new NullPointerException();
        }
        this.mPath = path;
    }

    public void open() {
        this.mPtr = nativeOpen(this.mPath.getAbsolutePath());
    }

    protected void closeNativeObject(long ptr) {
        nativeClose(ptr);
        if (this.mDestroyOnClose) {
            destroy(this.mPath);
        }
    }

    public void put(byte[] key, byte[] value) {
        if (!assertOpen("Database is closed")) {
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

    public byte[] get(byte[] key) {
        return get(null, key);
    }

    public byte[] get(Snapshot snapshot, byte[] key) {
        if (!assertOpen("Database is closed, get byte key")) {
            return new byte[0];
        }
        if (key == null) {
            return new byte[0];
        }
        return nativeGet(this.mPtr, snapshot != null ? snapshot.getPtr() : 0, key);
    }

    public byte[] get(ByteBuffer key) {
        return get(null, key);
    }

    public byte[] get(Snapshot snapshot, ByteBuffer key) {
        if (!assertOpen("Database is closed, get bytebuffer")) {
            return new byte[0];
        }
        if (key == null) {
            return new byte[0];
        }
        return nativeGet(this.mPtr, snapshot != null ? snapshot.getPtr() : 0, key);
    }

    public void delete(byte[] key) {
        if (assertOpen("Database is closed, delte byte") && key != null) {
            nativeDelete(this.mPtr, key);
        }
    }

    public void write(WriteBatch batch) {
        if (assertOpen("Database is closed, write batch") && batch != null) {
            nativeWrite(this.mPtr, batch.getPtr());
        }
    }

    public Iterator iterator() {
        return iterator(null);
    }

    public Iterator iterator(final Snapshot snapshot) {
        if (!assertOpen("Database is closed, iterator")) {
            return null;
        }
        ref();
        if (snapshot != null) {
            snapshot.ref();
        }
        return new Iterator(nativeIterator(this.mPtr, snapshot != null ? snapshot.getPtr() : 0)) {
            protected void closeNativeObject(long ptr) {
                super.closeNativeObject(ptr);
                if (snapshot != null) {
                    snapshot.unref();
                }
                DB.this.unref();
            }
        };
    }

    public Snapshot getSnapshot() {
        if (!assertOpen("Database is closed, getsnapshot")) {
            return null;
        }
        ref();
        return new Snapshot(nativeGetSnapshot(this.mPtr)) {
            protected void closeNativeObject(long ptr) {
                DB.nativeReleaseSnapshot(DB.this.getPtr(), getPtr());
                DB.this.unref();
            }
        };
    }

    public void destroy() {
        this.mDestroyOnClose = true;
        if (getPtr() == 0) {
            destroy(this.mPath);
        }
    }

    public static void destroy(File path) {
        nativeDestroy(path.getAbsolutePath());
    }
}
