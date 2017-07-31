package io.netty.util.collection;

import java.util.Map;

public interface ByteObjectMap<V> extends Map<Byte, V> {

    public interface PrimitiveEntry<V> {
        byte key();

        void setValue(V v);

        V value();
    }

    boolean containsKey(byte b);

    Iterable<PrimitiveEntry<V>> entries();

    V get(byte b);

    V put(byte b, V v);

    V remove(byte b);
}
