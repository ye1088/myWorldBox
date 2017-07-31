package com.huluxia.image.pipeline.producers;

import com.huluxia.image.base.imagepipeline.image.d;
import com.huluxia.image.pipeline.request.ImageRequest;

/* compiled from: BranchOnSeparateImagesProducer */
public class i implements am<d> {
    private final am<d> Jl;
    private final am<d> Jm;

    /* compiled from: BranchOnSeparateImagesProducer */
    private class a extends m<d, d> {
        private ao Jn;
        final /* synthetic */ i Jo;

        protected /* synthetic */ void d(Object obj, boolean z) {
            a((d) obj, z);
        }

        private a(i iVar, j<d> consumer, ao producerContext) {
            this.Jo = iVar;
            super(consumer);
            this.Jn = producerContext;
        }

        protected void a(d newResult, boolean isLast) {
            ImageRequest request = this.Jn.oA();
            boolean isGoodEnough = ba.a(newResult, request.pz());
            if (newResult != null && (isGoodEnough || request.pE())) {
                j oM = oM();
                boolean z = isLast && isGoodEnough;
                oM.e(newResult, z);
            }
            if (isLast && !isGoodEnough) {
                d.d(newResult);
                this.Jo.Jm.b(oM(), this.Jn);
            }
        }

        protected void h(Throwable t) {
            this.Jo.Jm.b(oM(), this.Jn);
        }
    }

    public i(am<d> inputProducer1, am<d> inputProducer2) {
        this.Jl = inputProducer1;
        this.Jm = inputProducer2;
    }

    public void b(j<d> consumer, ao context) {
        this.Jl.b(new a(consumer, context), context);
    }
}
