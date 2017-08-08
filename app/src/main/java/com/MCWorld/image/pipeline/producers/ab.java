package com.MCWorld.image.pipeline.producers;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import com.MCWorld.framework.base.utils.ImmutableMap;
import com.MCWorld.framework.base.utils.VisibleForTesting;
import com.MCWorld.image.base.imagepipeline.image.b;
import com.MCWorld.image.base.imagepipeline.image.c;
import com.MCWorld.image.base.imagepipeline.image.f;
import com.MCWorld.image.core.common.references.a;
import com.MCWorld.image.pipeline.request.ImageRequest;
import java.util.Map;
import java.util.concurrent.Executor;

/* compiled from: LocalVideoThumbnailProducer */
public class ab implements am<a<b>> {
    public static final String Ji = "VideoThumbnailProducer";
    @VisibleForTesting
    static final String Kl = "createdThumbnail";
    private final Executor mExecutor;

    public ab(Executor executor) {
        this.mExecutor = executor;
    }

    public void b(j<a<b>> consumer, ao producerContext) {
        aq listener = producerContext.oB();
        String requestId = producerContext.getId();
        final ImageRequest imageRequest = producerContext.oA();
        final StatefulProducerRunnable cancellableProducerRunnable = new StatefulProducerRunnable<a<b>>(this, consumer, listener, Ji, requestId) {
            final /* synthetic */ ab Kr;

            protected /* synthetic */ Map M(Object obj) {
                return j((a) obj);
            }

            protected /* synthetic */ Object getResult() throws Exception {
                return nt();
            }

            protected /* synthetic */ void p(Object obj) {
                k((a) obj);
            }

            protected a<b> nt() throws Exception {
                Bitmap thumbnailBitmap = ThumbnailUtils.createVideoThumbnail(imageRequest.pG().getPath(), ab.p(imageRequest));
                if (thumbnailBitmap == null) {
                    return null;
                }
                return a.b(new c(thumbnailBitmap, com.MCWorld.image.base.imagepipeline.bitmaps.b.hf(), f.wZ, 0));
            }

            protected Map<String, String> j(a<b> result) {
                return ImmutableMap.of(ab.Kl, String.valueOf(result != null));
            }

            protected void k(a<b> result) {
                a.c(result);
            }
        };
        producerContext.a(new e(this) {
            final /* synthetic */ ab Kr;

            public void cj() {
                cancellableProducerRunnable.cancel();
            }
        });
        this.mExecutor.execute(cancellableProducerRunnable);
    }

    private static int p(ImageRequest imageRequest) {
        if (imageRequest.px() > 96 || imageRequest.py() > 96) {
            return 1;
        }
        return 3;
    }
}
