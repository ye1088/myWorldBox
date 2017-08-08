package com.MCWorld.image.pipeline.producers;

import com.MCWorld.image.pipeline.producers.af.a;
import java.io.Closeable;

/* compiled from: MultiplexProducer */
class af$a$a extends b<T> {
    final /* synthetic */ a KU;

    private af$a$a(a aVar) {
        this.KU = aVar;
    }

    protected /* synthetic */ void d(Object obj, boolean z) {
        a((Closeable) obj, z);
    }

    protected void a(T newResult, boolean isLast) {
        this.KU.a(this, newResult, isLast);
    }

    protected void h(Throwable t) {
        this.KU.a(this, t);
    }

    protected void ns() {
        this.KU.a(this);
    }

    protected void n(float progress) {
        this.KU.a(this, progress);
    }
}
