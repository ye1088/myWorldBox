package io.netty.util.collection;

import java.util.Map;

public interface CharObjectMap<V> extends Map<Character, V> {

    public interface PrimitiveEntry<V> {
        char key();

        void setValue(V v);

        V value();
    }

    boolean containsKey(char c);

    Iterable<PrimitiveEntry<V>> entries();

    V get(char c);

    V put(char c, V v);

    V remove(char c);
}
