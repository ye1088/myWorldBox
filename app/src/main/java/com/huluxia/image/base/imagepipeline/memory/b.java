package com.huluxia.image.base.imagepipeline.memory;

import com.huluxia.image.core.common.memory.a;
import com.huluxia.image.core.common.references.c;

/* compiled from: Pool */
public interface b<V> extends a, c<V> {
    V get(int i);

    void release(V v);
}
