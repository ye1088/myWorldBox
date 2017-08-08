package com.MCWorld.image.base.imagepipeline.memory;

import java.io.Closeable;

public interface PooledByteBuffer extends Closeable {

    public static class ClosedException extends RuntimeException {
        public ClosedException() {
            super("Invalid bytebuf. Already closed");
        }
    }

    void a(int i, byte[] bArr, int i2, int i3);

    byte bs(int i);

    void close();

    long if();

    boolean isClosed();

    int size();
}
