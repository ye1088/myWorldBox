package io.netty.util.collection;

import java.util.Map;

public interface LongObjectMap<V> extends Map<Long, V> {

    public interface PrimitiveEntry<V> {
        long key();

        void setValue(V v);

        V value();
    }

    boolean containsKey(long j);

    Iterable<PrimitiveEntry<V>> entries();

    V get(long j);

    V put(long j, V v);

    V remove(long j);
}
