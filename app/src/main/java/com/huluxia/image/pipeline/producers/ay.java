package com.huluxia.image.pipeline.producers;

import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.image.base.imagepipeline.common.c;
import com.huluxia.image.base.imagepipeline.image.d;

/* compiled from: ThumbnailBranchProducer */
public class ay implements am<d> {
    private final az<d>[] LW;

    /* compiled from: ThumbnailBranchProducer */
    private class a extends m<d, d> {
        private final c Ev = this.Jn.oA().pz();
        private final ao Jn;
        private final int LX;
        final /* synthetic */ ay LY;

        protected /* synthetic */ void d(Object obj, boolean z) {
            a((d) obj, z);
        }

        public a(ay ayVar, j<d> consumer, ao producerContext, int producerIndex) {
            this.LY = ayVar;
            super(consumer);
            this.Jn = producerContext;
            this.LX = producerIndex;
        }

        protected void a(d newResult, boolean isLast) {
            if (newResult != null && (!isLast || ba.a(newResult, this.Ev))) {
                oM().e(newResult, isLast);
            } else if (isLast) {
                d.d(newResult);
                if (!this.LY.a(this.LX + 1, oM(), this.Jn)) {
                    oM().e(null, true);
                }
            }
        }

        protected void h(Throwable t) {
            if (!this.LY.a(this.LX + 1, oM(), this.Jn)) {
                oM().j(t);
            }
        }
    }

    public ay(az<d>... thumbnailProducers) {
        this.LW = (az[]) Preconditions.checkNotNull(thumbnailProducers);
        Preconditions.checkElementIndex(0, this.LW.length);
    }

    public void b(j<d> consumer, ao context) {
        if (context.oA().pz() == null) {
            consumer.e(null, true);
        } else if (!a(0, consumer, context)) {
            consumer.e(null, true);
        }
    }

    private boolean a(int startIndex, j<d> consumer, ao context) {
        int producerIndex = a(startIndex, context.oA().pz());
        if (producerIndex == -1) {
            return false;
        }
        this.LW[producerIndex].b(new a(this, consumer, context, producerIndex), context);
        return true;
    }

    private int a(int startIndex, c resizeOptions) {
        for (int i = startIndex; i < this.LW.length; i++) {
            if (this.LW[i].a(resizeOptions)) {
                return i;
            }
        }
        return -1;
    }
}
