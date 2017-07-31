package com.huluxia.image.base.imagepipeline.cache;

import com.huluxia.image.base.imagepipeline.cache.d.b;

/* compiled from: CountingMemoryCache */
class d$2 implements g<b<K, V>> {
    final /* synthetic */ d vV;
    final /* synthetic */ g vW;

    d$2(d this$0, g gVar) {
        this.vV = this$0;
        this.vW = gVar;
    }

    public int j(b<K, V> entry) {
        return this.vW.j(entry.vY.get());
    }
}
