package com.huluxia.image.pipeline.producers;

import com.huluxia.image.base.imagepipeline.image.d;

/* compiled from: AddImageTransformMetaDataProducer */
public class a implements am<d> {
    private final am<d> IY;

    /* compiled from: AddImageTransformMetaDataProducer */
    private static class a extends m<d, d> {
        protected /* synthetic */ void d(Object obj, boolean z) {
            a((d) obj, z);
        }

        private a(j<d> consumer) {
            super(consumer);
        }

        protected void a(d newResult, boolean isLast) {
            if (newResult == null) {
                oM().e(null, isLast);
                return;
            }
            if (!d.c(newResult)) {
                newResult.hW();
            }
            oM().e(newResult, isLast);
        }
    }

    public a(am<d> inputProducer) {
        this.IY = inputProducer;
    }

    public void b(j<d> consumer, ao context) {
        this.IY.b(new a(consumer), context);
    }
}
