package com.MCWorld.image.pipeline.datasource;

import com.MCWorld.image.core.common.references.a;
import com.MCWorld.image.core.datasource.c;
import com.MCWorld.image.pipeline.producers.am;
import com.MCWorld.image.pipeline.producers.at;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
/* compiled from: CloseableProducerToDataSourceAdapter */
public class d<T> extends a<a<T>> {
    protected /* synthetic */ void d(Object obj, boolean z) {
        a((a) obj, z);
    }

    @Nullable
    public /* synthetic */ Object getResult() {
        return nt();
    }

    protected /* synthetic */ void s(Object obj) {
        h((a) obj);
    }

    public static <T> c<a<T>> a(am<a<T>> producer, at settableProducerContext, com.MCWorld.image.pipeline.listener.c listener) {
        return new d(producer, settableProducerContext, listener);
    }

    private d(am<a<T>> producer, at settableProducerContext, com.MCWorld.image.pipeline.listener.c listener) {
        super(producer, settableProducerContext, listener);
    }

    @Nullable
    public a<T> nt() {
        return a.b((a) super.getResult());
    }

    protected void h(a<T> result) {
        a.c(result);
    }

    protected void a(a<T> result, boolean isLast) {
        super.d(a.b((a) result), isLast);
    }
}
