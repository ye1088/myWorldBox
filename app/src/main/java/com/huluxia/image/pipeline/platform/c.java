package com.huluxia.image.pipeline.platform;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.os.MemoryFile;
import com.huluxia.framework.base.utils.ByteStreams;
import com.huluxia.framework.base.utils.Closeables;
import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.framework.base.utils.Throwables;
import com.huluxia.image.base.imagepipeline.memory.PooledByteBuffer;
import com.huluxia.image.base.imagepipeline.memory.e;
import com.huluxia.image.core.common.references.a;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import javax.annotation.Nullable;

/* compiled from: GingerbreadPurgeableDecoder */
public class c extends b {
    private static Method IU;

    public /* bridge */ /* synthetic */ a m(Bitmap bitmap) {
        return super.m(bitmap);
    }

    public c(com.huluxia.image.pipeline.memory.c tracker) {
        super(tracker);
    }

    protected Bitmap a(a<PooledByteBuffer> bytesRef, Options options) {
        return a(bytesRef, ((PooledByteBuffer) bytesRef.get()).size(), null, options);
    }

    protected Bitmap a(a<PooledByteBuffer> bytesRef, int length, Options options) {
        return a(bytesRef, length, b.a((a) bytesRef, length) ? null : Ep, options);
    }

    private static MemoryFile a(a<PooledByteBuffer> bytesRef, int inputLength, @Nullable byte[] suffix) throws IOException {
        Throwable th;
        MemoryFile memoryFile = new MemoryFile(null, inputLength + (suffix == null ? 0 : suffix.length));
        memoryFile.allowPurging(false);
        InputStream pbbIs = null;
        InputStream is = null;
        try {
            InputStream pbbIs2 = new e((PooledByteBuffer) bytesRef.get());
            try {
                InputStream is2 = new com.huluxia.image.core.common.streams.a(pbbIs2, inputLength);
                try {
                    OutputStream os = memoryFile.getOutputStream();
                    ByteStreams.copy(is2, os);
                    if (suffix != null) {
                        memoryFile.writeBytes(suffix, 0, inputLength, suffix.length);
                    }
                    a.c(bytesRef);
                    Closeables.closeQuietly(pbbIs2);
                    Closeables.closeQuietly(is2);
                    Closeables.close(os, true);
                    return memoryFile;
                } catch (Throwable th2) {
                    th = th2;
                    is = is2;
                    pbbIs = pbbIs2;
                    a.c(bytesRef);
                    Closeables.closeQuietly(pbbIs);
                    Closeables.closeQuietly(is);
                    Closeables.close(null, true);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                pbbIs = pbbIs2;
                a.c(bytesRef);
                Closeables.closeQuietly(pbbIs);
                Closeables.closeQuietly(is);
                Closeables.close(null, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            a.c(bytesRef);
            Closeables.closeQuietly(pbbIs);
            Closeables.closeQuietly(is);
            Closeables.close(null, true);
            throw th;
        }
    }

    private synchronized Method oy() {
        if (IU == null) {
            try {
                IU = MemoryFile.class.getDeclaredMethod("getFileDescriptor", new Class[0]);
            } catch (Exception e) {
                throw Throwables.propagate(e);
            }
        }
        return IU;
    }

    private FileDescriptor a(MemoryFile memoryFile) {
        try {
            return (FileDescriptor) oy().invoke(memoryFile, new Object[0]);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    protected Bitmap a(a<PooledByteBuffer> bytesRef, int inputLength, byte[] suffix, Options options) {
        MemoryFile memoryFile = null;
        try {
            memoryFile = a((a) bytesRef, inputLength, suffix);
            Bitmap bitmap = (Bitmap) Preconditions.checkNotNull(com.huluxia.image.core.common.webp.c.zd.decodeFileDescriptor(a(memoryFile), null, options), "BitmapFactory returned null");
            if (memoryFile != null) {
                memoryFile.close();
            }
            return bitmap;
        } catch (IOException e) {
            throw Throwables.propagate(e);
        } catch (Throwable th) {
            if (memoryFile != null) {
                memoryFile.close();
            }
        }
    }
}
