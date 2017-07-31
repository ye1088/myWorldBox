package io.netty.handler.codec;

import io.netty.util.internal.ObjectUtil;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public final class HeadersUtils {

    private static abstract class DelegatingStringSet<T> implements Set<String> {
        protected final Set<T> allNames;

        public DelegatingStringSet(Set<T> allNames) {
            this.allNames = (Set) ObjectUtil.checkNotNull(allNames, "allNames");
        }

        public int size() {
            return this.allNames.size();
        }

        public boolean isEmpty() {
            return this.allNames.isEmpty();
        }

        public boolean contains(Object o) {
            return this.allNames.contains(o.toString());
        }

        public Iterator<String> iterator() {
            return new StringIterator(this.allNames.iterator());
        }

        public Object[] toArray() {
            Object[] arr = new Object[size()];
            fillArray(arr);
            return arr;
        }

        public <X> X[] toArray(X[] a) {
            if (a == null || a.length < size()) {
                X[] arr = (Object[]) new Object[size()];
                fillArray(arr);
                return arr;
            }
            fillArray(a);
            return a;
        }

        private void fillArray(Object[] arr) {
            Iterator<T> itr = this.allNames.iterator();
            for (int i = 0; i < size(); i++) {
                arr[i] = itr.next();
            }
        }

        public boolean remove(Object o) {
            return this.allNames.remove(o);
        }

        public boolean containsAll(Collection<?> c) {
            for (Object o : c) {
                if (!contains(o)) {
                    return false;
                }
            }
            return true;
        }

        public boolean removeAll(Collection<?> c) {
            boolean modified = false;
            for (Object o : c) {
                if (remove(o)) {
                    modified = true;
                }
            }
            return modified;
        }

        public boolean retainAll(Collection<?> c) {
            boolean modified = false;
            Iterator<String> it = iterator();
            while (it.hasNext()) {
                if (!c.contains(it.next())) {
                    it.remove();
                    modified = true;
                }
            }
            return modified;
        }

        public void clear() {
            this.allNames.clear();
        }
    }

    private static final class CharSequenceDelegatingStringSet extends DelegatingStringSet<CharSequence> {
        public CharSequenceDelegatingStringSet(Set<CharSequence> allNames) {
            super(allNames);
        }

        public boolean add(String e) {
            return this.allNames.add(e);
        }

        public boolean addAll(Collection<? extends String> c) {
            return this.allNames.addAll(c);
        }
    }

    private static final class StringEntry implements Entry<String, String> {
        private final Entry<CharSequence, CharSequence> entry;
        private String name;
        private String value;

        StringEntry(Entry<CharSequence, CharSequence> entry) {
            this.entry = entry;
        }

        public String getKey() {
            if (this.name == null) {
                this.name = ((CharSequence) this.entry.getKey()).toString();
            }
            return this.name;
        }

        public String getValue() {
            if (this.value == null && this.entry.getValue() != null) {
                this.value = ((CharSequence) this.entry.getValue()).toString();
            }
            return this.value;
        }

        public String setValue(String value) {
            String old = getValue();
            this.entry.setValue(value);
            return old;
        }

        public String toString() {
            return this.entry.toString();
        }
    }

    private static final class StringEntryIterator implements Iterator<Entry<String, String>> {
        private final Iterator<Entry<CharSequence, CharSequence>> iter;

        public StringEntryIterator(Iterator<Entry<CharSequence, CharSequence>> iter) {
            this.iter = iter;
        }

        public boolean hasNext() {
            return this.iter.hasNext();
        }

        public Entry<String, String> next() {
            return new StringEntry((Entry) this.iter.next());
        }

        public void remove() {
            this.iter.remove();
        }
    }

    private static final class StringIterator<T> implements Iterator<String> {
        private final Iterator<T> iter;

        public StringIterator(Iterator<T> iter) {
            this.iter = iter;
        }

        public boolean hasNext() {
            return this.iter.hasNext();
        }

        public String next() {
            T next = this.iter.next();
            return next != null ? next.toString() : null;
        }

        public void remove() {
            this.iter.remove();
        }
    }

    private HeadersUtils() {
    }

    public static <K, V> List<String> getAllAsString(Headers<K, V, ?> headers, K name) {
        final List<V> allNames = headers.getAll(name);
        return new AbstractList<String>() {
            public String get(int index) {
                V value = allNames.get(index);
                return value != null ? value.toString() : null;
            }

            public int size() {
                return allNames.size();
            }
        };
    }

    public static <K, V> String getAsString(Headers<K, V, ?> headers, K name) {
        V orig = headers.get(name);
        return orig != null ? orig.toString() : null;
    }

    public static Iterator<Entry<String, String>> iteratorAsString(Iterable<Entry<CharSequence, CharSequence>> headers) {
        return new StringEntryIterator(headers.iterator());
    }

    public static Set<String> namesAsString(Headers<CharSequence, CharSequence, ?> headers) {
        return new CharSequenceDelegatingStringSet(headers.names());
    }
}
