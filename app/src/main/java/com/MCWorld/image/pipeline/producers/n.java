package com.MCWorld.image.pipeline.producers;

import bolts.g;
import com.MCWorld.framework.base.utils.ImmutableMap;
import com.MCWorld.framework.base.utils.VisibleForTesting;
import com.MCWorld.image.base.imagepipeline.image.d;
import com.MCWorld.image.pipeline.cache.h;
import com.MCWorld.image.pipeline.request.ImageRequest;
import com.MCWorld.image.pipeline.request.ImageRequest$RequestLevel;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: DiskCacheReadProducer */
public class n implements am<d> {
    public static final String Ji = "DiskCacheProducer";
    public static final String Jj = "cached_value_found";
    private final am<d> IY;
    private final h JH;

    public n(am<d> inputProducer, h diskCachePolicy) {
        this.IY = inputProducer;
        this.JH = diskCachePolicy;
    }

    public void b(j<d> consumer, ao producerContext) {
        ImageRequest imageRequest = producerContext.oA();
        if (imageRequest.pF()) {
            producerContext.oB().n(producerContext.getId(), Ji);
            AtomicBoolean isCancelled = new AtomicBoolean(false);
            this.JH.a(imageRequest, producerContext.gk(), isCancelled).a(c(consumer, producerContext));
            a(isCancelled, producerContext);
            return;
        }
        d(consumer, producerContext);
    }

    private g<d, Void> c(j<d> consumer, ao producerContext) {
        final String requestId = producerContext.getId();
        final aq listener = producerContext.oB();
        final j<d> jVar = consumer;
        final ao aoVar = producerContext;
        return new g<d, Void>(this) {
            final /* synthetic */ n JL;

            public /* synthetic */ Object a(bolts.h hVar) throws Exception {
                return b(hVar);
            }

            public Void b(bolts.h<d> task) throws Exception {
                if (n.e(task)) {
                    listener.d(requestId, n.Ji, null);
                    jVar.iq();
                } else if (task.ba()) {
                    listener.a(requestId, n.Ji, task.bb(), null);
                    this.JL.IY.b(jVar, aoVar);
                } else {
                    d cachedReference = (d) task.getResult();
                    if (cachedReference != null) {
                        listener.c(requestId, n.Ji, n.a(listener, requestId, true));
                        jVar.onProgressUpdate(1.0f);
                        jVar.e(cachedReference, true);
                        cachedReference.close();
                    } else {
                        listener.c(requestId, n.Ji, n.a(listener, requestId, false));
                        this.JL.IY.b(jVar, aoVar);
                    }
                }
                return null;
            }
        };
    }

    private static boolean e(bolts.h<?> task) {
        return task.isCancelled() || (task.ba() && (task.bb() instanceof CancellationException));
    }

    private void d(j<d> consumer, ao producerContext) {
        if (producerContext.oC().getValue() >= ImageRequest$RequestLevel.DISK_CACHE.getValue()) {
            consumer.e(null, true);
        } else {
            this.IY.b(consumer, producerContext);
        }
    }

    @VisibleForTesting
    static Map<String, String> a(aq listener, String requestId, boolean valueFound) {
        if (listener.bE(requestId)) {
            return ImmutableMap.of("cached_value_found", String.valueOf(valueFound));
        }
        return null;
    }

    private void a(final AtomicBoolean isCancelled, ao producerContext) {
        producerContext.a(new e(this) {
            final /* synthetic */ n JL;

            public void cj() {
                isCancelled.set(true);
            }
        });
    }
}
