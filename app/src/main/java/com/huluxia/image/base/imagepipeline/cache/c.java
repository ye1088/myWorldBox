package com.huluxia.image.base.imagepipeline.cache;

import com.android.internal.util.Predicate;
import com.huluxia.framework.base.utils.VisibleForTesting;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
/* compiled from: CountingLruMap */
public class c<K, V> {
    private final g<V> vK;
    @GuardedBy("this")
    private final LinkedHashMap<K, V> vL = new LinkedHashMap();
    @GuardedBy("this")
    private int vM = 0;

    public c(g<V> valueDescriptor) {
        this.vK = valueDescriptor;
    }

    @VisibleForTesting
    synchronized ArrayList<K> hg() {
        return new ArrayList(this.vL.keySet());
    }

    @VisibleForTesting
    synchronized ArrayList<V> hh() {
        return new ArrayList(this.vL.values());
    }

    public synchronized int getCount() {
        return this.vL.size();
    }

    public synchronized int hi() {
        return this.vM;
    }

    @Nullable
    public synchronized K hj() {
        return this.vL.isEmpty() ? null : this.vL.keySet().iterator().next();
    }

    public synchronized ArrayList<Entry<K, V>> a(@Nullable Predicate<K> predicate) {
        ArrayList<Entry<K, V>> matchingEntries;
        matchingEntries = new ArrayList(this.vL.entrySet().size());
        for (Entry<K, V> entry : this.vL.entrySet()) {
            if (predicate == null || predicate.apply(entry.getKey())) {
                matchingEntries.add(entry);
            }
        }
        return matchingEntries;
    }

    public synchronized boolean contains(K key) {
        return this.vL.containsKey(key);
    }

    @Nullable
    public synchronized V get(K key) {
        return this.vL.get(key);
    }

    @Nullable
    public synchronized V put(K key, V value) {
        V oldValue;
        oldValue = this.vL.remove(key);
        this.vM -= k(oldValue);
        this.vL.put(key, value);
        this.vM += k(value);
        return oldValue;
    }

    @Nullable
    public synchronized V remove(K key) {
        V oldValue;
        oldValue = this.vL.remove(key);
        this.vM -= k(oldValue);
        return oldValue;
    }

    public synchronized ArrayList<V> b(@Nullable Predicate<K> predicate) {
        ArrayList<V> oldValues;
        oldValues = new ArrayList();
        Iterator<Entry<K, V>> iterator = this.vL.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<K, V> entry = (Entry) iterator.next();
            if (predicate == null || predicate.apply(entry.getKey())) {
                oldValues.add(entry.getValue());
                this.vM -= k(entry.getValue());
                iterator.remove();
            }
        }
        return oldValues;
    }

    public synchronized ArrayList<V> hk() {
        ArrayList<V> oldValues;
        oldValues = new ArrayList(this.vL.values());
        this.vL.clear();
        this.vM = 0;
        return oldValues;
    }

    private int k(V value) {
        return value == null ? 0 : this.vK.j(value);
    }
}
