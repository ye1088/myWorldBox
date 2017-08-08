package com.MCWorld.image.pipeline.memory;

import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.framework.base.utils.Throwables;
import com.MCWorld.framework.base.utils.VisibleForTesting;
import com.MCWorld.image.base.imagepipeline.memory.PooledByteBuffer;
import com.MCWorld.image.base.imagepipeline.memory.d;
import com.MCWorld.image.base.imagepipeline.memory.f;
import com.MCWorld.image.base.imagepipeline.memory.g;
import com.MCWorld.image.core.common.references.a;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
/* compiled from: NativePooledByteBufferFactory */
public class n implements d {
    private final g ED;
    private final l HZ;

    public /* synthetic */ PooledByteBuffer a(InputStream inputStream, int i) throws IOException {
        return e(inputStream, i);
    }

    public /* synthetic */ PooledByteBuffer bt(int i) {
        return cD(i);
    }

    public /* synthetic */ f bu(int i) {
        return cE(i);
    }

    public /* synthetic */ PooledByteBuffer i(InputStream inputStream) throws IOException {
        return x(inputStream);
    }

    public /* synthetic */ f ig() {
        return nZ();
    }

    public /* synthetic */ PooledByteBuffer j(byte[] bArr) {
        return v(bArr);
    }

    public n(l pool, g pooledByteStreams) {
        this.HZ = pool;
        this.ED = pooledByteStreams;
    }

    public m cD(int size) {
        Preconditions.checkArgument(size > 0);
        a<NativeMemoryChunk> chunkRef = a.a(this.HZ.get(size), this.HZ);
        try {
            m mVar = new m(chunkRef, size);
            return mVar;
        } finally {
            chunkRef.close();
        }
    }

    public m x(InputStream inputStream) throws IOException {
        NativePooledByteBufferOutputStream outputStream = new NativePooledByteBufferOutputStream(this.HZ);
        try {
            m a = a(inputStream, outputStream);
            return a;
        } finally {
            outputStream.close();
        }
    }

    public m v(byte[] bytes) {
        NativePooledByteBufferOutputStream outputStream = new NativePooledByteBufferOutputStream(this.HZ, bytes.length);
        try {
            outputStream.write(bytes, 0, bytes.length);
            m oa = outputStream.oa();
            outputStream.close();
            return oa;
        } catch (IOException ioe) {
            throw Throwables.propagate(ioe);
        } catch (Throwable th) {
            outputStream.close();
        }
    }

    public m e(InputStream inputStream, int initialCapacity) throws IOException {
        NativePooledByteBufferOutputStream outputStream = new NativePooledByteBufferOutputStream(this.HZ, initialCapacity);
        try {
            m a = a(inputStream, outputStream);
            return a;
        } finally {
            outputStream.close();
        }
    }

    @VisibleForTesting
    m a(InputStream inputStream, NativePooledByteBufferOutputStream outputStream) throws IOException {
        this.ED.copy(inputStream, outputStream);
        return outputStream.oa();
    }

    public NativePooledByteBufferOutputStream nZ() {
        return new NativePooledByteBufferOutputStream(this.HZ);
    }

    public NativePooledByteBufferOutputStream cE(int initialCapacity) {
        return new NativePooledByteBufferOutputStream(this.HZ, initialCapacity);
    }
}
