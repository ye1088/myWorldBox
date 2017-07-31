package io.netty.handler.codec;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class EmptyHeaders<K, V, T extends Headers<K, V, T>> implements Headers<K, V, T> {
    public V get(K k) {
        return null;
    }

    public V get(K k, V v) {
        return null;
    }

    public V getAndRemove(K k) {
        return null;
    }

    public V getAndRemove(K k, V v) {
        return null;
    }

    public List<V> getAll(K k) {
        return Collections.emptyList();
    }

    public List<V> getAllAndRemove(K k) {
        return Collections.emptyList();
    }

    public Boolean getBoolean(K k) {
        return null;
    }

    public boolean getBoolean(K k, boolean defaultValue) {
        return defaultValue;
    }

    public Byte getByte(K k) {
        return null;
    }

    public byte getByte(K k, byte defaultValue) {
        return defaultValue;
    }

    public Character getChar(K k) {
        return null;
    }

    public char getChar(K k, char defaultValue) {
        return defaultValue;
    }

    public Short getShort(K k) {
        return null;
    }

    public short getShort(K k, short defaultValue) {
        return defaultValue;
    }

    public Integer getInt(K k) {
        return null;
    }

    public int getInt(K k, int defaultValue) {
        return defaultValue;
    }

    public Long getLong(K k) {
        return null;
    }

    public long getLong(K k, long defaultValue) {
        return defaultValue;
    }

    public Float getFloat(K k) {
        return null;
    }

    public float getFloat(K k, float defaultValue) {
        return defaultValue;
    }

    public Double getDouble(K k) {
        return null;
    }

    public double getDouble(K k, double defaultValue) {
        return defaultValue;
    }

    public Long getTimeMillis(K k) {
        return null;
    }

    public long getTimeMillis(K k, long defaultValue) {
        return defaultValue;
    }

    public Boolean getBooleanAndRemove(K k) {
        return null;
    }

    public boolean getBooleanAndRemove(K k, boolean defaultValue) {
        return defaultValue;
    }

    public Byte getByteAndRemove(K k) {
        return null;
    }

    public byte getByteAndRemove(K k, byte defaultValue) {
        return defaultValue;
    }

    public Character getCharAndRemove(K k) {
        return null;
    }

    public char getCharAndRemove(K k, char defaultValue) {
        return defaultValue;
    }

    public Short getShortAndRemove(K k) {
        return null;
    }

    public short getShortAndRemove(K k, short defaultValue) {
        return defaultValue;
    }

    public Integer getIntAndRemove(K k) {
        return null;
    }

    public int getIntAndRemove(K k, int defaultValue) {
        return defaultValue;
    }

    public Long getLongAndRemove(K k) {
        return null;
    }

    public long getLongAndRemove(K k, long defaultValue) {
        return defaultValue;
    }

    public Float getFloatAndRemove(K k) {
        return null;
    }

    public float getFloatAndRemove(K k, float defaultValue) {
        return defaultValue;
    }

    public Double getDoubleAndRemove(K k) {
        return null;
    }

    public double getDoubleAndRemove(K k, double defaultValue) {
        return defaultValue;
    }

    public Long getTimeMillisAndRemove(K k) {
        return null;
    }

    public long getTimeMillisAndRemove(K k, long defaultValue) {
        return defaultValue;
    }

    public boolean contains(K k) {
        return false;
    }

    public boolean contains(K k, V v) {
        return false;
    }

    public boolean containsObject(K k, Object value) {
        return false;
    }

    public boolean containsBoolean(K k, boolean value) {
        return false;
    }

    public boolean containsByte(K k, byte value) {
        return false;
    }

    public boolean containsChar(K k, char value) {
        return false;
    }

    public boolean containsShort(K k, short value) {
        return false;
    }

    public boolean containsInt(K k, int value) {
        return false;
    }

    public boolean containsLong(K k, long value) {
        return false;
    }

    public boolean containsFloat(K k, float value) {
        return false;
    }

    public boolean containsDouble(K k, double value) {
        return false;
    }

    public boolean containsTimeMillis(K k, long value) {
        return false;
    }

    public int size() {
        return 0;
    }

    public boolean isEmpty() {
        return true;
    }

    public Set<K> names() {
        return Collections.emptySet();
    }

    public T add(K k, V v) {
        throw new UnsupportedOperationException("read only");
    }

    public T add(K k, Iterable<? extends V> iterable) {
        throw new UnsupportedOperationException("read only");
    }

    public T add(K k, V... vArr) {
        throw new UnsupportedOperationException("read only");
    }

    public T addObject(K k, Object value) {
        throw new UnsupportedOperationException("read only");
    }

    public T addObject(K k, Iterable<?> iterable) {
        throw new UnsupportedOperationException("read only");
    }

    public T addObject(K k, Object... values) {
        throw new UnsupportedOperationException("read only");
    }

    public T addBoolean(K k, boolean value) {
        throw new UnsupportedOperationException("read only");
    }

    public T addByte(K k, byte value) {
        throw new UnsupportedOperationException("read only");
    }

    public T addChar(K k, char value) {
        throw new UnsupportedOperationException("read only");
    }

    public T addShort(K k, short value) {
        throw new UnsupportedOperationException("read only");
    }

    public T addInt(K k, int value) {
        throw new UnsupportedOperationException("read only");
    }

    public T addLong(K k, long value) {
        throw new UnsupportedOperationException("read only");
    }

    public T addFloat(K k, float value) {
        throw new UnsupportedOperationException("read only");
    }

    public T addDouble(K k, double value) {
        throw new UnsupportedOperationException("read only");
    }

    public T addTimeMillis(K k, long value) {
        throw new UnsupportedOperationException("read only");
    }

    public T add(Headers<? extends K, ? extends V, ?> headers) {
        throw new UnsupportedOperationException("read only");
    }

    public T set(K k, V v) {
        throw new UnsupportedOperationException("read only");
    }

    public T set(K k, Iterable<? extends V> iterable) {
        throw new UnsupportedOperationException("read only");
    }

    public T set(K k, V... vArr) {
        throw new UnsupportedOperationException("read only");
    }

    public T setObject(K k, Object value) {
        throw new UnsupportedOperationException("read only");
    }

    public T setObject(K k, Iterable<?> iterable) {
        throw new UnsupportedOperationException("read only");
    }

    public T setObject(K k, Object... values) {
        throw new UnsupportedOperationException("read only");
    }

    public T setBoolean(K k, boolean value) {
        throw new UnsupportedOperationException("read only");
    }

    public T setByte(K k, byte value) {
        throw new UnsupportedOperationException("read only");
    }

    public T setChar(K k, char value) {
        throw new UnsupportedOperationException("read only");
    }

    public T setShort(K k, short value) {
        throw new UnsupportedOperationException("read only");
    }

    public T setInt(K k, int value) {
        throw new UnsupportedOperationException("read only");
    }

    public T setLong(K k, long value) {
        throw new UnsupportedOperationException("read only");
    }

    public T setFloat(K k, float value) {
        throw new UnsupportedOperationException("read only");
    }

    public T setDouble(K k, double value) {
        throw new UnsupportedOperationException("read only");
    }

    public T setTimeMillis(K k, long value) {
        throw new UnsupportedOperationException("read only");
    }

    public T set(Headers<? extends K, ? extends V, ?> headers) {
        throw new UnsupportedOperationException("read only");
    }

    public T setAll(Headers<? extends K, ? extends V, ?> headers) {
        throw new UnsupportedOperationException("read only");
    }

    public boolean remove(K k) {
        return false;
    }

    public T clear() {
        return thisT();
    }

    public Iterator<Entry<K, V>> iterator() {
        return Collections.emptyList().iterator();
    }

    public boolean equals(Object o) {
        if (!(o instanceof Headers)) {
            return false;
        }
        Headers<?, ?, ?> rhs = (Headers) o;
        if (isEmpty() && rhs.isEmpty()) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return -1028477387;
    }

    public String toString() {
        return new StringBuilder(getClass().getSimpleName()).append('[').append(']').toString();
    }

    private T thisT() {
        return this;
    }
}
