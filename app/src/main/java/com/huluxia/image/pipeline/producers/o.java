package com.huluxia.image.pipeline.producers;

import com.huluxia.image.base.imagepipeline.image.d;
import com.huluxia.image.pipeline.cache.h;
import com.huluxia.image.pipeline.request.ImageRequest$RequestLevel;

/* compiled from: DiskCacheWriteProducer */
public class o implements am<d> {
    private final am<d> IY;
    private final h JH;

    /* compiled from: DiskCacheWriteProducer */
    private static class a extends m<d, d> {
        private final h JH;
        private final ao Jn;

        public /* synthetic */ void d(Object obj, boolean z) {
            a((d) obj, z);
        }

        private a(j<d> consumer, ao producerContext, h diskCachePolicy) {
            super(consumer);
            this.Jn = producerContext;
            this.JH = diskCachePolicy;
        }

        public void a(d newResult, boolean isLast) {
            if (newResult != null && isLast) {
                this.JH.a(newResult, this.Jn.oA(), this.Jn.gk());
            }
            oM().e(newResult, isLast);
        }
    }

    public o(am<d> inputProducer, h diskCachePolicy) {
        this.IY = inputProducer;
        this.JH = diskCachePolicy;
    }

    public void b(j<d> consumer, ao producerContext) {
        d(consumer, producerContext);
    }

    private void d(j<d> consumerOfDiskCacheWriteProducer, ao producerContext) {
        if (producerContext.oC().getValue() >= ImageRequest$RequestLevel.DISK_CACHE.getValue()) {
            consumerOfDiskCacheWriteProducer.e(null, true);
            return;
        }
        j<d> consumer;
        if (producerContext.oA().pF()) {
            consumer = new a(consumerOfDiskCacheWriteProducer, producerContext, this.JH);
        } else {
            consumer = consumerOfDiskCacheWriteProducer;
        }
        this.IY.b(consumer, producerContext);
    }
}
