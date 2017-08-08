package com.MCWorld.image.pipeline.producers;

import android.os.SystemClock;
import com.MCWorld.framework.base.utils.VisibleForTesting;
import com.MCWorld.image.base.imagepipeline.image.d;
import com.MCWorld.image.base.imagepipeline.memory.a;
import com.MCWorld.image.base.imagepipeline.memory.f;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.annotation.Nullable;

/* compiled from: NetworkFetchProducer */
public class ag implements am<d> {
    public static final String Ji = "NetworkFetchProducer";
    public static final String KV = "intermediate_result";
    private static final int KW = 16384;
    @VisibleForTesting
    static final long KY = 100;
    private final com.MCWorld.image.base.imagepipeline.memory.d Eo;
    private final ah FD;
    private final a xl;

    public ag(com.MCWorld.image.base.imagepipeline.memory.d pooledByteBufferFactory, a byteArrayPool, ah networkFetcher) {
        this.Eo = pooledByteBufferFactory;
        this.xl = byteArrayPool;
        this.FD = networkFetcher;
    }

    public void b(j<d> consumer, ao context) {
        context.oB().n(context.getId(), Ji);
        s fetchState = this.FD.a((j) consumer, context);
        this.FD.a(fetchState, new 1(this, fetchState));
    }

    private void a(s fetchState, InputStream responseData, int responseContentLength) throws IOException {
        f pooledOutputStream;
        if (responseContentLength > 0) {
            pooledOutputStream = this.Eo.bu(responseContentLength);
        } else {
            pooledOutputStream = this.Eo.ig();
        }
        byte[] ioArray = (byte[]) this.xl.get(16384);
        while (true) {
            try {
                int length = responseData.read(ioArray);
                if (length < 0) {
                    break;
                } else if (length > 0) {
                    pooledOutputStream.write(ioArray, 0, length);
                    a(pooledOutputStream, fetchState);
                    fetchState.oM().onProgressUpdate(t(pooledOutputStream.size(), responseContentLength));
                }
            } finally {
                this.xl.release(ioArray);
                pooledOutputStream.close();
            }
        }
        this.FD.b(fetchState, pooledOutputStream.size());
        b(pooledOutputStream, fetchState);
    }

    private static float t(int downloaded, int total) {
        return total > 0 ? ((float) downloaded) / ((float) total) : 1.0f - ((float) Math.exp(((double) (-downloaded)) / 50000.0d));
    }

    private void a(f pooledOutputStream, s fetchState) {
        long nowMs = SystemClock.uptimeMillis();
        if (c(fetchState) && nowMs - fetchState.oO() >= 100) {
            fetchState.R(nowMs);
            fetchState.oB().c(fetchState.getId(), Ji, KV);
            a(pooledOutputStream, false, fetchState.oM());
        }
    }

    private void b(f pooledOutputStream, s fetchState) {
        fetchState.oB().c(fetchState.getId(), Ji, a(fetchState, pooledOutputStream.size()));
        a(pooledOutputStream, true, fetchState.oM());
    }

    private void a(f pooledOutputStream, boolean isFinal, j<d> consumer) {
        Throwable th;
        com.MCWorld.image.core.common.references.a result = com.MCWorld.image.core.common.references.a.b(pooledOutputStream.ih());
        d encodedImage = null;
        try {
            d encodedImage2 = new d(result);
            try {
                encodedImage2.hW();
                consumer.e(encodedImage2, isFinal);
                d.d(encodedImage2);
                com.MCWorld.image.core.common.references.a.c(result);
            } catch (Throwable th2) {
                th = th2;
                encodedImage = encodedImage2;
                d.d(encodedImage);
                com.MCWorld.image.core.common.references.a.c(result);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            d.d(encodedImage);
            com.MCWorld.image.core.common.references.a.c(result);
            throw th;
        }
    }

    private void a(s fetchState, Throwable e) {
        fetchState.oB().a(fetchState.getId(), Ji, e, null);
        fetchState.oM().j(e);
    }

    private void b(s fetchState) {
        fetchState.oB().d(fetchState.getId(), Ji, null);
        fetchState.oM().iq();
    }

    private boolean c(s fetchState) {
        if (fetchState.oN().oA().pD()) {
            return this.FD.a(fetchState);
        }
        return false;
    }

    @Nullable
    private Map<String, String> a(s fetchState, int byteSize) {
        if (fetchState.oB().bE(fetchState.getId())) {
            return this.FD.a(fetchState, byteSize);
        }
        return null;
    }
}
