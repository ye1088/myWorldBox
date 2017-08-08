package com.MCWorld.image.pipeline.producers;

import com.MCWorld.image.base.imagepipeline.image.d;
import com.MCWorld.image.base.imagepipeline.memory.PooledByteBuffer;

/* compiled from: RemoveImageTransformMetaDataProducer */
public class ar implements am<com.MCWorld.image.core.common.references.a<PooledByteBuffer>> {
    private final am<d> IY;

    /* compiled from: RemoveImageTransformMetaDataProducer */
    private class a extends m<d, com.MCWorld.image.core.common.references.a<PooledByteBuffer>> {
        final /* synthetic */ ar Lp;

        protected /* synthetic */ void d(Object obj, boolean z) {
            a((d) obj, z);
        }

        private a(ar arVar, j<com.MCWorld.image.core.common.references.a<PooledByteBuffer>> consumer) {
            this.Lp = arVar;
            super(consumer);
        }

        protected void a(d newResult, boolean isLast) {
            com.MCWorld.image.core.common.references.a<PooledByteBuffer> ret = null;
            try {
                if (d.e(newResult)) {
                    ret = newResult.hS();
                }
                oM().e(ret, isLast);
            } finally {
                com.MCWorld.image.core.common.references.a.c(ret);
            }
        }
    }

    public ar(am<d> inputProducer) {
        this.IY = inputProducer;
    }

    public void b(j<com.MCWorld.image.core.common.references.a<PooledByteBuffer>> consumer, ao context) {
        this.IY.b(new a(consumer), context);
    }
}
