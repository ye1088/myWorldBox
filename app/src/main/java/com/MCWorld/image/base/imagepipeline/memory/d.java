package com.MCWorld.image.base.imagepipeline.memory;

import java.io.IOException;
import java.io.InputStream;

/* compiled from: PooledByteBufferFactory */
public interface d {
    PooledByteBuffer a(InputStream inputStream, int i) throws IOException;

    PooledByteBuffer bt(int i);

    f bu(int i);

    PooledByteBuffer i(InputStream inputStream) throws IOException;

    f ig();

    PooledByteBuffer j(byte[] bArr);
}
