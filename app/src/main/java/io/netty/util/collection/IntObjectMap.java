package io.netty.util.collection;

import java.util.Map;

public interface IntObjectMap<V> extends Map<Integer, V> {

    public interface PrimitiveEntry<V> {
        int key();

        void setValue(V v);

        V value();
    }

    boolean containsKey(int i);

    Iterable<PrimitiveEntry<V>> entries();

    V get(int i);

    V put(int i, V v);

    V remove(int i);
}
