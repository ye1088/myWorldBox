package com.huluxia.image.pipeline.core;

import bolts.g;
import bolts.h;
import com.huluxia.image.base.cache.common.b;

/* compiled from: ImagePipeline */
class e$5 implements g<Boolean, h<Boolean>> {
    final /* synthetic */ b Fb;
    final /* synthetic */ e Fq;

    e$5(e this$0, b bVar) {
        this.Fq = this$0;
        this.Fb = bVar;
    }

    public /* synthetic */ Object a(h hVar) throws Exception {
        return c(hVar);
    }

    public h<Boolean> c(h<Boolean> task) throws Exception {
        if (task.isCancelled() || task.ba() || !((Boolean) task.getResult()).booleanValue()) {
            return e.a(this.Fq).m(this.Fb);
        }
        return h.b(Boolean.valueOf(true));
    }
}
