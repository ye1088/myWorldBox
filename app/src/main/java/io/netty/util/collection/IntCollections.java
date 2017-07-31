package io.netty.util.collection;

import io.netty.util.collection.IntObjectMap.PrimitiveEntry;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

public final class IntCollections {
    private static final IntObjectMap<Object> EMPTY_MAP = new EmptyMap();

    private static final class EmptyMap implements IntObjectMap<Object> {
        private EmptyMap() {
        }

        public Object get(int key) {
            return null;
        }

        public Object put(int key, Object value) {
            throw new UnsupportedOperationException("put");
        }

        public Object remove(int key) {
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

        public Set<Integer> keySet() {
            return Collections.emptySet();
        }

        public boolean containsKey(int key) {
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

        public Object put(Integer key, Object value) {
            throw new UnsupportedOperationException();
        }

        public Object remove(Object key) {
            return null;
        }

        public void putAll(Map<? extends Integer, ?> map) {
            throw new UnsupportedOperationException();
        }

        public Collection<Object> values() {
            return Collections.emptyList();
        }

        public Set<Entry<Integer, Object>> entrySet() {
            return Collections.emptySet();
        }
    }

    private static final class UnmodifiableMap<V> implements IntObjectMap<V> {
        private Iterable<PrimitiveEntry<V>> entries;
        private Set<Entry<Integer, V>> entrySet;
        private Set<Integer> keySet;
        private final IntObjectMap<V> map;
        private Collection<V> values;

        private class EntryImpl implements PrimitiveEntry<V> {
            private final PrimitiveEntry<V> entry;

            EntryImpl(PrimitiveEntry<V> entry) {
                this.entry = entry;
            }

            public int key() {
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

        UnmodifiableMap(IntObjectMap<V> map) {
            this.map = map;
        }

        public V get(int key) {
            return this.map.get(key);
        }

        public V put(int key, V v) {
            throw new UnsupportedOperationException("put");
        }

        public V remove(int key) {
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

        public boolean containsKey(int key) {
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

        public V put(Integer key, V v) {
            throw new UnsupportedOperationException("put");
        }

        public V remove(Object key) {
            throw new UnsupportedOperationException("remove");
        }

        public void putAll(Map<? extends Integer, ? extends V> map) {
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

        public Set<Integer> keySet() {
            if (this.keySet == null) {
                this.keySet = Collections.unmodifiableSet(this.map.keySet());
            }
            return this.keySet;
        }

        public Set<Entry<Integer, V>> entrySet() {
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

    private IntCollections() {
    }

    public static <V> IntObjectMap<V> emptyMap() {
        return EMPTY_MAP;
    }

    public static <V> IntObjectMap<V> unmodifiableMap(IntObjectMap<V> map) {
        return new UnmodifiableMap(map);
    }
}
