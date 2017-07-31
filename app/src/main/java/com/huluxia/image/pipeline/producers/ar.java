package com.huluxia.image.pipeline.producers;

import com.huluxia.image.base.imagepipeline.image.d;
import com.huluxia.image.base.imagepipeline.memory.PooledByteBuffer;

/* compiled from: RemoveImageTransformMetaDataProducer */
public class ar implements am<com.huluxia.image.core.common.references.a<PooledByteBuffer>> {
    private final am<d> IY;

    /* compiled from: RemoveImageTransformMetaDataProducer */
    private class a extends m<d, com.huluxia.image.core.common.references.a<PooledByteBuffer>> {
        final /* synthetic */ ar Lp;

        protected /* synthetic */ void d(Object obj, boolean z) {
            a((d) obj, z);
        }

        private a(ar arVar, j<com.huluxia.image.core.common.references.a<PooledByteBuffer>> consumer) {
            this.Lp = arVar;
            super(consumer);
        }

        protected void a(d newResult, boolean isLast) {
            com.huluxia.image.core.common.references.a<PooledByteBuffer> ret = null;
            try {
                if (d.e(newResult)) {
                    ret = newResult.hS();
                }
                oM().e(ret, isLast);
            } finally {
                com.huluxia.image.core.common.references.a.c(ret);
            }
        }
    }

    public ar(am<d> inputProducer) {
        this.IY = inputProducer;
    }

    public void b(j<com.huluxia.image.core.common.references.a<PooledByteBuffer>> consumer, ao context) {
        this.IY.b(new a(consumer), context);
    }
}
