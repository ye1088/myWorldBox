package com.MCWorld.image.pipeline.producers;

import com.MCWorld.image.base.imagepipeline.image.b;
import com.MCWorld.image.pipeline.producers.al.a;

/* compiled from: PostprocessorProducer */
class al$a$2 implements Runnable {
    final /* synthetic */ a Lm;

    al$a$2(a this$1) {
        this.Lm = this$1;
    }

    public void run() {
        synchronized (this.Lm) {
            com.MCWorld.image.core.common.references.a<b> closeableImageRef = a.b(this.Lm);
            boolean isLast = a.c(this.Lm);
            a.a(this.Lm, null);
            a.a(this.Lm, false);
        }
        if (com.MCWorld.image.core.common.references.a.a(closeableImageRef)) {
            try {
                a.a(this.Lm, closeableImageRef, isLast);
            } finally {
                com.MCWorld.image.core.common.references.a.c(closeableImageRef);
            }
        }
        a.d(this.Lm);
    }
}
