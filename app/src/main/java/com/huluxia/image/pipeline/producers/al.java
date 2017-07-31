package com.huluxia.image.pipeline.producers;

import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.framework.base.utils.VisibleForTesting;
import com.huluxia.image.base.imagepipeline.image.b;
import com.huluxia.image.core.common.references.a;
import com.huluxia.image.pipeline.request.d;
import com.huluxia.image.pipeline.request.e;
import java.util.concurrent.Executor;

/* compiled from: PostprocessorProducer */
public class al implements am<a<b>> {
    @VisibleForTesting
    static final String Lc = "Postprocessor";
    public static final String NAME = "PostprocessorProducer";
    private final am<a<b>> IY;
    private final com.huluxia.image.base.imagepipeline.bitmaps.a Ld;
    private final Executor mExecutor;

    /* compiled from: PostprocessorProducer */
    class c extends m<a<b>, a<b>> {
        final /* synthetic */ al Lk;

        protected /* synthetic */ void d(Object obj, boolean z) {
            a((a) obj, z);
        }

        private c(al this$0, a postprocessorConsumer) {
            this.Lk = this$0;
            super(postprocessorConsumer);
        }

        protected void a(a<b> newResult, boolean isLast) {
            if (isLast) {
                oM().e(newResult, isLast);
            }
        }
    }

    public al(am<a<b>> inputProducer, com.huluxia.image.base.imagepipeline.bitmaps.a platformBitmapFactory, Executor executor) {
        this.IY = (am) Preconditions.checkNotNull(inputProducer);
        this.Ld = platformBitmapFactory;
        this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
    }

    public void b(j<a<b>> consumer, ao context) {
        j<a<b>> postprocessorConsumer;
        aq listener = context.oB();
        d postprocessor = context.oA().pH();
        a basePostprocessorConsumer = new a(this, consumer, listener, context.getId(), postprocessor, context);
        if (postprocessor instanceof e) {
            postprocessorConsumer = new b(this, basePostprocessorConsumer, (e) postprocessor, context, null);
        } else {
            postprocessorConsumer = new c(basePostprocessorConsumer);
        }
        this.IY.b(postprocessorConsumer, context);
    }
}
