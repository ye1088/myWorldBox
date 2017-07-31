package com.huluxia.image.pipeline.cache;

import com.android.internal.util.Predicate;
import com.huluxia.image.base.imagepipeline.cache.e;
import com.huluxia.image.core.common.references.a;

/* compiled from: InstrumentedMemoryCache */
public class l<K, V> implements e<K, V> {
    private final e<K, V> ET;
    private final m EU;

    public l(e<K, V> delegate, m tracker) {
        this.ET = delegate;
        this.EU = tracker;
    }

    public a<V> m(K key) {
        a<V> result = this.ET.m(key);
        if (result == null) {
            this.EU.lp();
        } else {
            this.EU.H(key);
        }
        return result;
    }

    public a<V> a(K key, a<V> value) {
        this.EU.lq();
        return this.ET.a(key, value);
    }

    public int c(Predicate<K> predicate) {
        return this.ET.c(predicate);
    }

    public boolean d(Predicate<K> predicate) {
        return this.ET.d(predicate);
    }
}
