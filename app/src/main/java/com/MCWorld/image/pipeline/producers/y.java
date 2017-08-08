package com.MCWorld.image.pipeline.producers;

import android.os.Build.VERSION;
import com.MCWorld.framework.base.utils.Closeables;
import com.MCWorld.framework.base.utils.Supplier;
import com.MCWorld.image.base.imagepipeline.image.d;
import com.MCWorld.image.base.imagepipeline.memory.PooledByteBuffer;
import com.MCWorld.image.core.common.references.a;
import com.MCWorld.image.pipeline.request.ImageRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;

/* compiled from: LocalFetchProducer */
public abstract class y implements am<d> {
    private final com.MCWorld.image.base.imagepipeline.memory.d Eo;
    private final boolean Ko;
    private final Executor mExecutor;

    protected abstract d l(ImageRequest imageRequest) throws IOException;

    protected abstract String oK();

    protected y(Executor executor, com.MCWorld.image.base.imagepipeline.memory.d pooledByteBufferFactory, boolean fileDescriptorEnabled) {
        this.mExecutor = executor;
        this.Eo = pooledByteBufferFactory;
        boolean z = fileDescriptorEnabled && VERSION.SDK_INT == 19;
        this.Ko = z;
    }

    public void b(j<d> consumer, ao producerContext) {
        aq listener = producerContext.oB();
        String requestId = producerContext.getId();
        final ImageRequest imageRequest = producerContext.oA();
        final StatefulProducerRunnable cancellableProducerRunnable = new StatefulProducerRunnable<d>(this, consumer, listener, oK(), requestId) {
            final /* synthetic */ y Kp;

            protected /* synthetic */ Object getResult() throws Exception {
                return oW();
            }

            protected /* synthetic */ void p(Object obj) {
                h((d) obj);
            }

            protected d oW() throws Exception {
                d encodedImage = this.Kp.l(imageRequest);
                if (encodedImage == null) {
                    return null;
                }
                encodedImage.hW();
                return encodedImage;
            }

            protected void h(d result) {
                d.d(result);
            }
        };
        producerContext.a(new e(this) {
            final /* synthetic */ y Kp;

            public void cj() {
                cancellableProducerRunnable.cancel();
            }
        });
        this.mExecutor.execute(cancellableProducerRunnable);
    }

    protected d f(InputStream inputStream, int length) throws IOException {
        a<PooledByteBuffer> ref = null;
        if (length <= 0) {
            try {
                ref = a.b(this.Eo.i(inputStream));
            } catch (Throwable th) {
                Closeables.closeQuietly(inputStream);
                a.c(ref);
            }
        } else {
            ref = a.b(this.Eo.a(inputStream, length));
        }
        d dVar = new d(ref);
        Closeables.closeQuietly(inputStream);
        a.c(ref);
        return dVar;
    }

    protected d g(InputStream inputStream, int length) throws IOException {
        Runtime runTime = Runtime.getRuntime();
        long javaMax = runTime.maxMemory();
        long javaFree = Math.min(javaMax - (runTime.totalMemory() - runTime.freeMemory()), 8388608);
        if (this.Ko && (inputStream instanceof FileInputStream) && javaMax >= 64 * javaFree) {
            return a(new File(inputStream.toString()), length);
        }
        return f(inputStream, length);
    }

    protected d a(final File file, int length) throws IOException {
        return new d(new Supplier<FileInputStream>(this) {
            final /* synthetic */ y Kp;

            public /* synthetic */ Object get() {
                return oX();
            }

            public FileInputStream oX() {
                try {
                    return new FileInputStream(file);
                } catch (IOException ioe) {
                    throw new RuntimeException(ioe);
                }
            }
        }, length);
    }
}
