package io.netty.handler.codec.serialization;

import java.lang.ref.Reference;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

abstract class ReferenceMap<K, V> implements Map<K, V> {
    private final Map<K, Reference<V>> delegate;

    abstract Reference<V> fold(V v);

    protected ReferenceMap(Map<K, Reference<V>> delegate) {
        this.delegate = delegate;
    }

    private V unfold(Reference<V> ref) {
        if (ref == null) {
            return null;
        }
        return ref.get();
    }

    public int size() {
        return this.delegate.size();
    }

    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }

    public boolean containsKey(Object key) {
        return this.delegate.containsKey(key);
    }

    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    public V get(Object key) {
        return unfold((Reference) this.delegate.get(key));
    }

    public V put(K key, V value) {
        return unfold((Reference) this.delegate.put(key, fold(value)));
    }

    public V remove(Object key) {
        return unfold((Reference) this.delegate.remove(key));
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
            this.delegate.put(entry.getKey(), fold(entry.getValue()));
        }
    }

    public void clear() {
        this.delegate.clear();
    }

    public Set<K> keySet() {
        return this.delegate.keySet();
    }

    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

    public Set<Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }
}
