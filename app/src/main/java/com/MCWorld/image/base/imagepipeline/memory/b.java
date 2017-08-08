package com.MCWorld.image.base.imagepipeline.memory;

import com.MCWorld.image.core.common.memory.a;
import com.MCWorld.image.core.common.references.c;

/* compiled from: Pool */
public interface b<V> extends a, c<V> {
    V get(int i);

    void release(V v);
}
