package com.huluxia.image.base.imagepipeline.cache;

import com.android.internal.util.Predicate;
import com.huluxia.image.core.common.references.a;
import javax.annotation.Nullable;

/* compiled from: MemoryCache */
public interface e<K, V> {
    @Nullable
    a<V> a(K k, a<V> aVar);

    int c(Predicate<K> predicate);

    boolean d(Predicate<K> predicate);

    @Nullable
    a<V> m(K k);
}
