package io.netty.util.collection;

import java.util.Map;

public interface ShortObjectMap<V> extends Map<Short, V> {

    public interface PrimitiveEntry<V> {
        short key();

        void setValue(V v);

        V value();
    }

    boolean containsKey(short s);

    Iterable<PrimitiveEntry<V>> entries();

    V get(short s);

    V put(short s, V v);

    V remove(short s);
}
