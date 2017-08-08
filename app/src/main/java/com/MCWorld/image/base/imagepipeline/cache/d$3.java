package com.MCWorld.image.base.imagepipeline.cache;

import com.MCWorld.image.base.imagepipeline.cache.d.b;
import com.MCWorld.image.core.common.references.c;

/* compiled from: CountingMemoryCache */
class d$3 implements c<V> {
    final /* synthetic */ d vV;
    final /* synthetic */ b vX;

    d$3(d this$0, b bVar) {
        this.vV = this$0;
        this.vX = bVar;
    }

    public void release(V v) {
        d.a(this.vV, this.vX);
    }
}
