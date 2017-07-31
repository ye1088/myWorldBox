package com.huluxia.image.pipeline.producers;

import bolts.g;
import bolts.h;
import com.huluxia.framework.base.utils.ImmutableMap;
import com.huluxia.framework.base.utils.VisibleForTesting;
import com.huluxia.image.base.cache.common.b;
import com.huluxia.image.base.imagepipeline.image.d;
import com.huluxia.image.pipeline.cache.c;
import com.huluxia.image.pipeline.request.ImageRequest;
import com.huluxia.image.pipeline.request.ImageRequest$CacheChoice;
import com.huluxia.image.pipeline.request.c$b;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: MediaVariationsFallbackProducer */
public class ac implements am<d> {
    @VisibleForTesting
    static final String Ji = "MediaVariationsFallbackProducer";
    public static final String Jj = "cached_value_found";
    public static final String Ks = "cached_value_used_as_last";
    private final c EW;
    private final c EX;
    private final com.huluxia.image.pipeline.cache.d EY;
    private final ad Gl;
    private final am<d> IY;

    @VisibleForTesting
    /* compiled from: MediaVariationsFallbackProducer */
    class a extends m<d, d> {
        private final ao Jn;
        final /* synthetic */ ac Kv;

        protected /* synthetic */ void d(Object obj, boolean z) {
            a((d) obj, z);
        }

        public a(ac this$0, j<d> consumer, ao producerContext) {
            this.Kv = this$0;
            super(consumer);
            this.Jn = producerContext;
        }

        protected void a(d newResult, boolean isLast) {
            if (isLast && newResult != null) {
                j(newResult);
            }
            oM().e(newResult, isLast);
        }

        private void j(d newResult) {
            ImageRequest imageRequest = this.Jn.oA();
            com.huluxia.image.pipeline.request.c mediaVariations = imageRequest.pw();
            if (imageRequest.pF() && mediaVariations != null) {
                this.Kv.Gl.a(mediaVariations.getMediaId(), this.Kv.EY.c(imageRequest, this.Jn.gk()), newResult);
            }
        }
    }

    public ac(c defaultBufferedDiskCache, c smallImageBufferedDiskCache, com.huluxia.image.pipeline.cache.d cacheKeyFactory, ad mediaVariationsIndex, am<d> inputProducer) {
        this.EW = defaultBufferedDiskCache;
        this.EX = smallImageBufferedDiskCache;
        this.EY = cacheKeyFactory;
        this.Gl = mediaVariationsIndex;
        this.IY = inputProducer;
    }

    public void b(j<d> consumer, ao producerContext) {
        ImageRequest imageRequest = producerContext.oA();
        com.huluxia.image.base.imagepipeline.common.c resizeOptions = imageRequest.pz();
        com.huluxia.image.pipeline.request.c mediaVariations = imageRequest.pw();
        if (!imageRequest.pF() || resizeOptions == null || resizeOptions.height <= 0 || resizeOptions.width <= 0 || mediaVariations == null) {
            e(consumer, producerContext);
            return;
        }
        producerContext.oB().n(producerContext.getId(), Ji);
        AtomicBoolean isCancelled = new AtomicBoolean(false);
        if (mediaVariations.pN() != null) {
            a(consumer, producerContext, mediaVariations, mediaVariations.pN(), imageRequest, resizeOptions, isCancelled);
        } else {
            final j<d> jVar = consumer;
            final ao aoVar = producerContext;
            final com.huluxia.image.pipeline.request.c cVar = mediaVariations;
            final ImageRequest imageRequest2 = imageRequest;
            final com.huluxia.image.base.imagepipeline.common.c cVar2 = resizeOptions;
            final AtomicBoolean atomicBoolean = isCancelled;
            this.Gl.bJ(mediaVariations.getMediaId()).a(new g<List<c$b>, Object>(this) {
                final /* synthetic */ ac Kv;

                public Object a(h<List<c$b>> task) throws Exception {
                    if (task.isCancelled() || task.ba()) {
                        return task;
                    }
                    try {
                        if (task.getResult() != null && !((List) task.getResult()).isEmpty()) {
                            return this.Kv.a(jVar, aoVar, cVar, (List) task.getResult(), imageRequest2, cVar2, atomicBoolean);
                        }
                        this.Kv.e(jVar, aoVar);
                        return null;
                    } catch (Exception e) {
                        return null;
                    }
                }
            });
        }
        a(isCancelled, producerContext);
    }

    private h a(j<d> consumer, ao producerContext, com.huluxia.image.pipeline.request.c mediaVariations, List<c$b> variants, ImageRequest imageRequest, com.huluxia.image.base.imagepipeline.common.c resizeOptions, AtomicBoolean isCancelled) {
        h<d> diskLookupTask;
        boolean useAsLastResult;
        c preferredCache = imageRequest.pu() == ImageRequest$CacheChoice.SMALL ? this.EX : this.EW;
        Object callerContext = producerContext.gk();
        c$b preferredVariant = null;
        b preferredCacheKey = null;
        for (int i = 0; i < variants.size(); i++) {
            c$b variant = (c$b) variants.get(i);
            b cacheKey = this.EY.a(imageRequest, variant.getUri(), callerContext);
            if (preferredCache.l(cacheKey) && a(variant, preferredVariant, resizeOptions)) {
                preferredVariant = variant;
                preferredCacheKey = cacheKey;
            }
        }
        if (preferredCacheKey == null) {
            diskLookupTask = h.b(null);
            useAsLastResult = false;
        } else {
            diskLookupTask = preferredCache.a(preferredCacheKey, isCancelled);
            useAsLastResult = !mediaVariations.pO() && a(preferredVariant, resizeOptions);
        }
        return diskLookupTask.a(a((j) consumer, producerContext, useAsLastResult));
    }

    private static boolean a(c$b variant, com.huluxia.image.base.imagepipeline.common.c resizeOptions) {
        return variant.getWidth() >= resizeOptions.width && variant.getHeight() >= resizeOptions.height;
    }

    private static boolean a(c$b variant, c$b comparison, com.huluxia.image.base.imagepipeline.common.c resizeOptions) {
        if (comparison == null) {
            return true;
        }
        if (a(comparison, resizeOptions)) {
            if (variant.getWidth() >= comparison.getWidth() || !a(variant, resizeOptions)) {
                return false;
            }
            return true;
        } else if (variant.getWidth() <= comparison.getWidth()) {
            return false;
        } else {
            return true;
        }
    }

    private g<d, Void> a(j<d> consumer, ao producerContext, boolean useAsLastResult) {
        final String requestId = producerContext.getId();
        final aq listener = producerContext.oB();
        final j<d> jVar = consumer;
        final ao aoVar = producerContext;
        final boolean z = useAsLastResult;
        return new g<d, Void>(this) {
            final /* synthetic */ ac Kv;

            public /* synthetic */ Object a(h hVar) throws Exception {
                return b(hVar);
            }

            public Void b(h<d> task) throws Exception {
                boolean triggerNextProducer = true;
                if (ac.e(task)) {
                    listener.d(requestId, ac.Ji, null);
                    jVar.iq();
                    triggerNextProducer = false;
                } else if (task.ba()) {
                    listener.a(requestId, ac.Ji, task.bb(), null);
                    this.Kv.e(jVar, aoVar);
                    triggerNextProducer = true;
                } else {
                    d cachedReference = (d) task.getResult();
                    if (cachedReference != null) {
                        listener.c(requestId, ac.Ji, ac.a(listener, requestId, true, z));
                        if (z) {
                            jVar.onProgressUpdate(1.0f);
                        }
                        jVar.e(cachedReference, z);
                        cachedReference.close();
                        if (z) {
                            triggerNextProducer = false;
                        }
                    } else {
                        listener.c(requestId, ac.Ji, ac.a(listener, requestId, false, false));
                        triggerNextProducer = true;
                    }
                }
                if (triggerNextProducer) {
                    this.Kv.e(jVar, aoVar);
                }
                return null;
            }
        };
    }

    private void e(j<d> consumer, ao producerContext) {
        this.IY.b(new a(this, consumer, producerContext), producerContext);
    }

    private static boolean e(h<?> task) {
        return task.isCancelled() || (task.ba() && (task.bb() instanceof CancellationException));
    }

    @VisibleForTesting
    static Map<String, String> a(aq listener, String requestId, boolean valueFound, boolean useAsLastResult) {
        if (!listener.bE(requestId)) {
            return null;
        }
        if (valueFound) {
            return ImmutableMap.of("cached_value_found", String.valueOf(true), Ks, String.valueOf(useAsLastResult));
        }
        return ImmutableMap.of("cached_value_found", String.valueOf(false));
    }

    private void a(final AtomicBoolean isCancelled, ao producerContext) {
        producerContext.a(new e(this) {
            final /* synthetic */ ac Kv;

            public void cj() {
                isCancelled.set(true);
            }
        });
    }
}
