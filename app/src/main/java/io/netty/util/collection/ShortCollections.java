package io.netty.util.collection;

import io.netty.util.collection.ShortObjectMap.PrimitiveEntry;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

public final class ShortCollections {
    private static final ShortObjectMap<Object> EMPTY_MAP = new EmptyMap();

    private static final class EmptyMap implements ShortObjectMap<Object> {
        private EmptyMap() {
        }

        public Object get(short key) {
            return null;
        }

        public Object put(short key, Object value) {
            throw new UnsupportedOperationException("put");
        }

        public Object remove(short key) {
            return null;
        }

        public int size() {
            return 0;
        }

        public boolean isEmpty() {
            return true;
        }

        public boolean containsKey(Object key) {
            return false;
        }

        public void clear() {
        }

        public Set<Short> keySet() {
            return Collections.emptySet();
        }

        public boolean containsKey(short key) {
            return false;
        }

        public boolean containsValue(Object value) {
            return false;
        }

        public Iterable<PrimitiveEntry<Object>> entries() {
            return Collections.emptySet();
        }

        public Object get(Object key) {
            return null;
        }

        public Object put(Short key, Object value) {
            throw new UnsupportedOperationException();
        }

        public Object remove(Object key) {
            return null;
        }

        public void putAll(Map<? extends Short, ?> map) {
            throw new UnsupportedOperationException();
        }

        public Collection<Object> values() {
            return Collections.emptyList();
        }

        public Set<Entry<Short, Object>> entrySet() {
            return Collections.emptySet();
        }
    }

    private static final class UnmodifiableMap<V> implements ShortObjectMap<V> {
        private Iterable<PrimitiveEntry<V>> entries;
        private Set<Entry<Short, V>> entrySet;
        private Set<Short> keySet;
        private final ShortObjectMap<V> map;
        private Collection<V> values;

        private class EntryImpl implements PrimitiveEntry<V> {
            private final PrimitiveEntry<V> entry;

            EntryImpl(PrimitiveEntry<V> entry) {
                this.entry = entry;
            }

            public short key() {
                return this.entry.key();
            }

            public V value() {
                return this.entry.value();
            }

            public void setValue(V v) {
                throw new UnsupportedOperationException("setValue");
            }
        }

        private class IteratorImpl implements Iterator<PrimitiveEntry<V>> {
            final Iterator<PrimitiveEntry<V>> iter;

            IteratorImpl(Iterator<PrimitiveEntry<V>> iter) {
                this.iter = iter;
            }

            public boolean hasNext() {
                return this.iter.hasNext();
            }

            public PrimitiveEntry<V> next() {
                if (hasNext()) {
                    return new EntryImpl((PrimitiveEntry) this.iter.next());
                }
                throw new NoSuchElementException();
            }

            public void remove() {
                throw new UnsupportedOperationException("remove");
            }
        }

        UnmodifiableMap(ShortObjectMap<V> map) {
            this.map = map;
        }

        public V get(short key) {
            return this.map.get(key);
        }

        public V put(short key, V v) {
            throw new UnsupportedOperationException("put");
        }

        public V remove(short key) {
            throw new UnsupportedOperationException("remove");
        }

        public int size() {
            return this.map.size();
        }

        public boolean isEmpty() {
            return this.map.isEmpty();
        }

        public void clear() {
            throw new UnsupportedOperationException("clear");
        }

        public boolean containsKey(short key) {
            return this.map.containsKey(key);
        }

        public boolean containsValue(Object value) {
            return this.map.containsValue(value);
        }

        public boolean containsKey(Object key) {
            return this.map.containsKey(key);
        }

        public V get(Object key) {
            return this.map.get(key);
        }

        public V put(Short key, V v) {
            throw new UnsupportedOperationException("put");
        }

        public V remove(Object key) {
            throw new UnsupportedOperationException("remove");
        }

        public void putAll(Map<? extends Short, ? extends V> map) {
            throw new UnsupportedOperationException("putAll");
        }

        public Iterable<PrimitiveEntry<V>> entries() {
            if (this.entries == null) {
                this.entries = new Iterable<PrimitiveEntry<V>>() {
                    public Iterator<PrimitiveEntry<V>> iterator() {
                        return new IteratorImpl(UnmodifiableMap.this.map.entries().iterator());
                    }
                };
            }
            return this.entries;
        }

        public Set<Short> keySet() {
            if (this.keySet == null) {
                this.keySet = Collections.unmodifiableSet(this.map.keySet());
            }
            return this.keySet;
        }

        public Set<Entry<Short, V>> entrySet() {
            if (this.entrySet == null) {
                this.entrySet = Collections.unmodifiableSet(this.map.entrySet());
            }
            return this.entrySet;
        }

        public Collection<V> values() {
            if (this.values == null) {
                this.values = Collections.unmodifiableCollection(this.map.values());
            }
            return this.values;
        }
    }

    private ShortCollections() {
    }

    public static <V> ShortObjectMap<V> emptyMap() {
        return EMPTY_MAP;
    }

    public static <V> ShortObjectMap<V> unmodifiableMap(ShortObjectMap<V> map) {
        return new UnmodifiableMap(map);
    }
}
