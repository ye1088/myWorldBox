package com.MCWorld.utils;

import android.util.Pair;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/* compiled from: UtilsFunction */
public class ad$c {
    public static <K, V> List<Pair<K, V>> toList(Map<K, V> m) {
        List<Pair<K, V>> xs = new ArrayList();
        if (!ad.empty(m)) {
            for (Entry<K, V> e : m.entrySet()) {
                xs.add(Pair.create(e.getKey(), e.getValue()));
            }
        }
        return xs;
    }

    public static <K extends Comparable<K>, V> Map<K, V> fromList(List<Pair<K, V>> xs) {
        Map<K, V> m = new TreeMap();
        if (!ad.empty(xs)) {
            for (Pair<K, V> p : xs) {
                m.put(p.first, p.second);
            }
        }
        return m;
    }

    public static <V> Map<Integer, V> fromList(SparseArray<V> xs) {
        Map<Integer, V> m = new TreeMap();
        if (!ad.empty(xs)) {
            for (int i = 0; i < xs.size(); i++) {
                m.put(Integer.valueOf(xs.keyAt(i)), xs.valueAt(i));
            }
        }
        return m;
    }

    public static <V> List<V> values(SparseArray<V> m) {
        List<V> xs = new ArrayList();
        int n = ad.size(m);
        for (int i = 0; i < n; i++) {
            xs.add(m.valueAt(i));
        }
        return xs;
    }
}
