package com.huluxia.image.pipeline.producers;

import com.huluxia.image.base.imagepipeline.image.b;
import com.huluxia.image.core.common.references.a;
import com.huluxia.image.pipeline.request.e;
import com.huluxia.image.pipeline.request.f;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: PostprocessorProducer */
class al$b extends m<a<b>, a<b>> implements f {
    @GuardedBy("RepeatedPostprocessorConsumer.this")
    @Nullable
    private a<b> Lh;
    final /* synthetic */ al Lk;
    @GuardedBy("RepeatedPostprocessorConsumer.this")
    private boolean ym;

    protected /* synthetic */ void d(Object obj, boolean z) {
        a((a) obj, z);
    }

    private al$b(al this$0, al$a postprocessorConsumer, e repeatedPostprocessor, ao context) {
        this.Lk = this$0;
        super(postprocessorConsumer);
        this.ym = false;
        this.Lh = null;
        repeatedPostprocessor.a(this);
        context.a(new 1(this, this$0));
    }

    protected void a(a<b> newResult, boolean isLast) {
        if (isLast) {
            l(newResult);
            pl();
        }
    }

    protected void h(Throwable throwable) {
        if (close()) {
            oM().j(throwable);
        }
    }

    protected void ns() {
        if (close()) {
            oM().iq();
        }
    }

    public synchronized void update() {
        pl();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void pl() {
        /*
        r3 = this;
        monitor-enter(r3);
        r1 = r3.ym;	 Catch:{ all -> 0x001a }
        if (r1 == 0) goto L_0x0007;
    L_0x0005:
        monitor-exit(r3);	 Catch:{ all -> 0x001a }
    L_0x0006:
        return;
    L_0x0007:
        r1 = r3.Lh;	 Catch:{ all -> 0x001a }
        r0 = com.huluxia.image.core.common.references.a.b(r1);	 Catch:{ all -> 0x001a }
        monitor-exit(r3);	 Catch:{ all -> 0x001a }
        r1 = r3.oM();	 Catch:{ all -> 0x001d }
        r2 = 0;
        r1.e(r0, r2);	 Catch:{ all -> 0x001d }
        com.huluxia.image.core.common.references.a.c(r0);
        goto L_0x0006;
    L_0x001a:
        r1 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x001a }
        throw r1;
    L_0x001d:
        r1 = move-exception;
        com.huluxia.image.core.common.references.a.c(r0);
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huluxia.image.pipeline.producers.al$b.pl():void");
    }

    private void l(a<b> sourceImageRef) {
        synchronized (this) {
            if (this.ym) {
                return;
            }
            a<b> oldSourceImageRef = this.Lh;
            this.Lh = a.b((a) sourceImageRef);
            a.c(oldSourceImageRef);
        }
    }

    private boolean close() {
        boolean z = true;
        synchronized (this) {
            if (this.ym) {
                z = false;
            } else {
                a<b> oldSourceImageRef = this.Lh;
                this.Lh = null;
                this.ym = true;
                a.c(oldSourceImageRef);
            }
        }
        return z;
    }
}
