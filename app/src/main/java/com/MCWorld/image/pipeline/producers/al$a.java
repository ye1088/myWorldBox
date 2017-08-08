package com.MCWorld.image.pipeline.producers;

import android.graphics.Bitmap;
import com.MCWorld.framework.base.utils.ImmutableMap;
import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.image.base.imagepipeline.image.b;
import com.MCWorld.image.base.imagepipeline.image.c;
import com.MCWorld.image.core.common.references.a;
import com.MCWorld.image.pipeline.request.d;
import java.util.Map;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: PostprocessorProducer */
class al$a extends m<a<b>, a<b>> {
    @GuardedBy("PostprocessorConsumer.this")
    private boolean JY = false;
    private final aq Le;
    private final String Lf;
    private final d Lg;
    @GuardedBy("PostprocessorConsumer.this")
    @Nullable
    private a<b> Lh = null;
    @GuardedBy("PostprocessorConsumer.this")
    private boolean Li = false;
    @GuardedBy("PostprocessorConsumer.this")
    private boolean Lj = false;
    final /* synthetic */ al Lk;
    @GuardedBy("PostprocessorConsumer.this")
    private boolean ym;

    protected /* synthetic */ void d(Object obj, boolean z) {
        a((a) obj, z);
    }

    public al$a(al alVar, j<a<b>> consumer, aq listener, String requestId, d postprocessor, ao producerContext) {
        this.Lk = alVar;
        super(consumer);
        this.Le = listener;
        this.Lf = requestId;
        this.Lg = postprocessor;
        producerContext.a(new 1(this, alVar));
    }

    protected void a(a<b> newResult, boolean isLast) {
        if (a.a((a) newResult)) {
            b(newResult, isLast);
        } else if (isLast) {
            d(null, true);
        }
    }

    protected void h(Throwable t) {
        k(t);
    }

    protected void ns() {
        pk();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(@javax.annotation.Nullable com.MCWorld.image.core.common.references.a<com.MCWorld.image.base.imagepipeline.image.b> r4, boolean r5) {
        /*
        r3 = this;
        monitor-enter(r3);
        r2 = r3.ym;	 Catch:{ all -> 0x0022 }
        if (r2 == 0) goto L_0x0007;
    L_0x0005:
        monitor-exit(r3);	 Catch:{ all -> 0x0022 }
    L_0x0006:
        return;
    L_0x0007:
        r0 = r3.Lh;	 Catch:{ all -> 0x0022 }
        r2 = com.huluxia.image.core.common.references.a_isRightVersion.b(r4);	 Catch:{ all -> 0x0022 }
        r3.Lh = r2;	 Catch:{ all -> 0x0022 }
        r3.JY = r5;	 Catch:{ all -> 0x0022 }
        r2 = 1;
        r3.Li = r2;	 Catch:{ all -> 0x0022 }
        r1 = r3.pj();	 Catch:{ all -> 0x0022 }
        monitor-exit(r3);	 Catch:{ all -> 0x0022 }
        com.huluxia.image.core.common.references.a_isRightVersion.c(r0);
        if (r1 == 0) goto L_0x0006;
    L_0x001e:
        r3.ph();
        goto L_0x0006;
    L_0x0022:
        r2 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0022 }
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huluxia.image.pipeline.producers.al$a_isRightVersion.b(com.huluxia.image.core.common.references.a_isRightVersion, boolean):void");
    }

    private void ph() {
        al.a(this.Lk).execute(new 2(this));
    }

    private void pi() {
        synchronized (this) {
            this.Lj = false;
            boolean shouldExecuteAgain = pj();
        }
        if (shouldExecuteAgain) {
            ph();
        }
    }

    private synchronized boolean pj() {
        boolean z = true;
        synchronized (this) {
            if (this.ym || !this.Li || this.Lj || !a.a(this.Lh)) {
                z = false;
            } else {
                this.Lj = true;
            }
        }
        return z;
    }

    private void c(a<b> sourceImageRef, boolean isLast) {
        Preconditions.checkArgument(a.a((a) sourceImageRef));
        if (f((b) sourceImageRef.get())) {
            this.Le.n(this.Lf, al.NAME);
            a<b> destImageRef = null;
            try {
                destImageRef = g((b) sourceImageRef.get());
                this.Le.c(this.Lf, al.NAME, a(this.Le, this.Lf, this.Lg));
                d((a) destImageRef, isLast);
            } catch (Exception e) {
                this.Le.a(this.Lf, al.NAME, e, a(this.Le, this.Lf, this.Lg));
                k(e);
            } finally {
                a.c(destImageRef);
            }
        } else {
            d((a) sourceImageRef, isLast);
        }
    }

    private Map<String, String> a(aq listener, String requestId, d postprocessor) {
        if (listener.bE(requestId)) {
            return ImmutableMap.of("Postprocessor", postprocessor.getName());
        }
        return null;
    }

    private boolean f(b sourceImage) {
        return sourceImage instanceof c;
    }

    private a<b> g(b sourceImage) {
        c staticBitmap = (c) sourceImage;
        a<Bitmap> bitmapRef = this.Lg.a(staticBitmap.hM(), al.b(this.Lk));
        try {
            a<b> b = a.b(new c(bitmapRef, sourceImage.hN(), staticBitmap.hQ()));
            return b;
        } finally {
            a.c(bitmapRef);
        }
    }

    private void d(a<b> newRef, boolean isLast) {
        if ((!isLast && !isClosed()) || (isLast && close())) {
            oM().e(newRef, isLast);
        }
    }

    private void k(Throwable throwable) {
        if (close()) {
            oM().j(throwable);
        }
    }

    private void pk() {
        if (close()) {
            oM().iq();
        }
    }

    private synchronized boolean isClosed() {
        return this.ym;
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
