package com.huluxia.utils;

import android.util.Pair;
import android.util.SparseArray;
import android.util.SparseIntArray;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

/* compiled from: UtilsFunction */
public class ad {
    public static <A, B, C> e<A, B, C> c(A a, B b, C c) {
        return new e(a, b, c);
    }

    public static <E> d<E> a(d<E> p) {
        return new 1(p);
    }

    public static int limit(int x, int low, int high) {
        return Math.min(Math.max(low, x), high);
    }

    public static int maximum(int... xs) {
        int m = Integer.MIN_VALUE;
        for (int x : xs) {
            m = Math.max(m, x);
        }
        return m;
    }

    public static <E> E a(d<E> p, List<E> xs) {
        if (!empty((Collection) xs)) {
            for (E x : xs) {
                if (p.pred(x)) {
                    return x;
                }
            }
        }
        return null;
    }

    public static <E> E find(E x, List<E> xs) {
        return a(new 2(x), (List) xs);
    }

    public static <E> int b(d<E> p, List<E> xs) {
        int n = length((Collection) xs);
        int i = 0;
        while (i < n && !p.pred(xs.get(i))) {
            i++;
        }
        return i == n ? -1 : i;
    }

    public static <K, V> V lookup(K k, List<Pair<K, V>> xs) {
        if (!empty((Collection) xs)) {
            for (Pair<K, V> x : xs) {
                if (k == x.first) {
                    return x.second;
                }
            }
        }
        return null;
    }

    public static <E> E lookup(int k, SparseArray<E> xs) {
        return empty((SparseArray) xs) ? null : xs.get(k);
    }

    public static <E> List<E> a(b<E> cmp, List<E> xs) {
        List ys = new ArrayList();
        if (!empty((Collection) xs)) {
            for (E x : xs) {
                if (a(new 3(cmp, x), ys) == null) {
                    ys.add(x);
                }
            }
        }
        return ys;
    }

    public static <E> List<E> nub(List<E> xs) {
        return a(new 4(), (List) xs);
    }

    public static boolean empty(Collection<?> xs) {
        return xs == null || xs.isEmpty();
    }

    public static <T> boolean empty(T[] xs) {
        return xs == null || xs.length == 0;
    }

    public static boolean empty(SparseArray<?> xs) {
        return xs == null || xs.size() == 0;
    }

    public static boolean empty(SparseIntArray xs) {
        return xs == null || xs.size() == 0;
    }

    public static boolean empty(int[] xs) {
        return xs == null || xs.length == 0;
    }

    public static boolean empty(long[] xs) {
        return xs == null || xs.length == 0;
    }

    public static boolean empty(CharSequence s) {
        return s == null || s.length() == 0;
    }

    public static boolean empty(Map<?, ?> m) {
        return m == null || m.isEmpty();
    }

    public static int size(Collection<?> xs) {
        return xs == null ? 0 : xs.size();
    }

    public static int size(CharSequence s) {
        return s == null ? 0 : s.length();
    }

    public static <T> int size(T[] xs) {
        return xs == null ? 0 : xs.length;
    }

    public static int size(int[] xs) {
        return xs == null ? 0 : xs.length;
    }

    public static int size(long[] xs) {
        return xs == null ? 0 : xs.length;
    }

    public static int size(Map<?, ?> m) {
        return m == null ? 0 : m.size();
    }

    public static int size(SparseArray<?> xs) {
        return xs == null ? 0 : xs.size();
    }

    public static int size(SparseIntArray xs) {
        return xs == null ? 0 : xs.size();
    }

    public static int length(Collection<?> xs) {
        return size((Collection) xs);
    }

    public static int length(CharSequence s) {
        return size(s);
    }

    public static <T> int length(T[] xs) {
        return size((Object[]) xs);
    }

    public static int length(int[] xs) {
        return size(xs);
    }

    public static int length(Map<?, ?> m) {
        return size((Map) m);
    }

    public static int length(SparseArray<?> xs) {
        return size((SparseArray) xs);
    }

    public static int length(SparseIntArray xs) {
        return size(xs);
    }

    public static <T> boolean elem(T x, T[] xs) {
        return !empty((Object[]) xs) && Arrays.asList(xs).contains(x);
    }

    public static <T> boolean elem(T x, Collection<T> xs) {
        return !empty((Collection) xs) && xs.contains(x);
    }

    public static <T> void swap(List<T> xs, int i, int j) {
        T tmp = xs.get(i);
        xs.set(i, xs.get(j));
        xs.set(j, tmp);
    }

    public static <T> void swap(T[] xs, int i, int j) {
        T tmp = xs[i];
        xs[i] = xs[j];
        xs[j] = tmp;
    }

    public static <T> void shift(List<T> xs, int from, int to) {
        T tmp = xs.get(from);
        int d = from < to ? 1 : -1;
        while (from != to) {
            xs.set(from, xs.get(from + d));
            from += d;
        }
        xs.set(to, tmp);
    }

    public static <T> void shift(T[] xs, int from, int to) {
        T tmp = xs[from];
        int d = from < to ? 1 : -1;
        while (from != to) {
            xs[from] = xs[from + d];
            from += d;
        }
        xs[to] = tmp;
    }

    public static <E> List<E> add(List<E> xs, E x) {
        if (xs == null) {
            xs = new ArrayList();
        }
        xs.add(x);
        return xs;
    }

    public static <E> List<E> a(b<E> cmp, List<E> xs, E x) {
        int n = length((Collection) xs);
        int i = 0;
        while (i < n && !cmp.eq(xs.get(i), x)) {
            i++;
        }
        if (i < n) {
            xs.remove(i);
        }
        return xs;
    }

    public static <E> List<E> del(List<E> xs, E x) {
        return a(new 5(), (List) xs, (Object) x);
    }

    public static <E> Pair<List<E>, List<E>> c(d<E> p, List<E> xs) {
        return Pair.create(d(p, xs), e(p, xs));
    }

    public static <E> List<E> take(int n, List<E> xs) {
        List<E> ys = new ArrayList();
        if (!empty((Collection) xs) && n > 0) {
            ys.addAll(xs.subList(0, Math.min(n, length((Collection) xs))));
        }
        return ys;
    }

    public static String take(int n, String s) {
        return s.substring(0, limit(n, 0, length((CharSequence) s)));
    }

    public static <K, V> Map<K, V> take(int n, Map<K, V> xs) {
        Map<K, V> ys = new HashMap();
        for (Entry<K, V> k : xs.entrySet()) {
            int n2 = n - 1;
            if (n > 0) {
                ys.put(k.getKey(), k.getValue());
            }
            n = n2;
        }
        return ys;
    }

    public static <E> List<E> d(d<E> p, List<E> xs) {
        int n = length((Collection) xs);
        int i = 0;
        while (i < n && p.pred(xs.get(i))) {
            i++;
        }
        return take(i, (List) xs);
    }

    public static <E> List<E> drop(int n, List<E> xs) {
        List<E> ys = new ArrayList();
        if (xs != null && n <= length((Collection) xs)) {
            ys.addAll(xs.subList(Math.max(0, n), length((Collection) xs)));
        }
        return ys;
    }

    public static String drop(int n, String s) {
        if (s == null || n > length((CharSequence) s)) {
            return "";
        }
        return s.substring(Math.max(0, n));
    }

    public static <E> List<E> e(d<E> p, List<E> xs) {
        int n = length((Collection) xs);
        int i = 0;
        while (i < n && p.pred(xs.get(i))) {
            i++;
        }
        return drop(n, (List) xs);
    }

    public static <E> E head(LinkedList<E> xs) {
        return empty((Collection) xs) ? null : xs.element();
    }

    public static <E> LinkedList<E> tail(LinkedList<E> xs) {
        if (empty((Collection) xs)) {
            return xs;
        }
        LinkedList<E> ys = new LinkedList(xs);
        ys.remove();
        return ys;
    }

    public static <E> LinkedList<E> cons(E x, LinkedList<E> xs) {
        if (empty((Collection) xs)) {
            xs = new LinkedList();
        }
        xs.addFirst(x);
        return xs;
    }

    public static <E> E first(List<E> xs) {
        return empty((Collection) xs) ? null : xs.get(0);
    }

    public static <E> E second(List<E> xs) {
        return size((Collection) xs) < 2 ? null : xs.get(1);
    }

    public static <E> E last(List<E> xs) {
        return empty((Collection) xs) ? null : xs.get(lastIndex(xs));
    }

    public static int lastIndex(List<?> xs) {
        return empty((Collection) xs) ? -1 : xs.size() - 1;
    }

    public static <E> E first(Collection<E> xs) {
        if (empty((Collection) xs)) {
            return null;
        }
        return xs.iterator().next();
    }

    public static <E> List<E> toList(Collection<? extends E> xs) {
        return empty((Collection) xs) ? new ArrayList() : new ArrayList(xs);
    }

    public static <T> List<T> toList(T x) {
        return Collections.singletonList(x);
    }

    public static <T> List<T> toList(T[] xs) {
        List<T> ys = new ArrayList();
        if (!empty((Object[]) xs)) {
            for (T x : xs) {
                ys.add(x);
            }
        }
        return ys;
    }

    public static List<Integer> toList(int[] xs) {
        List<Integer> ys = new ArrayList();
        if (!empty(xs)) {
            for (int x : xs) {
                ys.add(Integer.valueOf(x));
            }
        }
        return ys;
    }

    public static List<Long> toList(long[] xs) {
        List<Long> ys = new ArrayList();
        if (!empty(xs)) {
            for (long x : xs) {
                ys.add(Long.valueOf(x));
            }
        }
        return ys;
    }

    public static <E> List<Pair<Integer, E>> toList(SparseArray<E> xs) {
        List<Pair<Integer, E>> ys = new ArrayList();
        if (!empty((SparseArray) xs)) {
            for (int i = 0; i < xs.size(); i++) {
                ys.add(Pair.create(Integer.valueOf(xs.keyAt(i)), xs.valueAt(i)));
            }
        }
        return ys;
    }

    public static List<Pair<Integer, Integer>> toList(SparseIntArray xs) {
        List<Pair<Integer, Integer>> ys = new ArrayList();
        if (!empty(xs)) {
            for (int i = 0; i < xs.size(); i++) {
                ys.add(Pair.create(Integer.valueOf(xs.keyAt(i)), Integer.valueOf(xs.valueAt(i))));
            }
        }
        return ys;
    }

    public static int[] toArray(List<Integer> xs) {
        int n = length((Collection) xs);
        int[] ys = new int[n];
        for (int i = 0; i < n; i++) {
            ys[i] = ((Integer) xs.get(i)).intValue();
        }
        return ys;
    }

    public static List<Integer> toIntegerList(List<Long> list) {
        if (list == null) {
            return null;
        }
        List<Integer> intList = new ArrayList();
        for (Long value : list) {
            intList.add(Integer.valueOf(value.intValue()));
        }
        return intList;
    }

    public static List<Long> toLongList(List<Integer> list) {
        if (list == null) {
            return null;
        }
        List<Long> intList = new ArrayList();
        for (Integer value : list) {
            intList.add(Long.valueOf(value.longValue()));
        }
        return intList;
    }

    public static <E> List<E> ref(List<E> list) {
        return list == null ? new ArrayList() : list;
    }

    public static <E> E[] ref(E[] xs) {
        return xs == null ? new Object[0] : xs;
    }

    public static int[] ref(int[] xs) {
        return xs == null ? new int[0] : xs;
    }

    public static String ref(String s) {
        return s == null ? "" : s;
    }

    public static <A, B> List<Pair<A, B>> zip(List<A> as, List<B> bs) {
        List<Pair<A, B>> xs = new ArrayList();
        if (!(empty((Collection) as) || empty((Collection) bs))) {
            Iterator<A> a = as.iterator();
            Iterator<B> b = bs.iterator();
            while (a.hasNext() && b.hasNext()) {
                xs.add(Pair.create(a.next(), b.next()));
            }
        }
        return xs;
    }

    public static boolean eq(Object a, Object b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null) {
            return false;
        }
        return a.equals(b);
    }

    public static boolean isPrefixOf(String prefix, String s) {
        if (empty((CharSequence) prefix)) {
            return true;
        }
        if (empty((CharSequence) s)) {
            return false;
        }
        return s.startsWith(prefix);
    }

    public static <E> boolean isPrefixOf(List<E> prefix, List<E> xs) {
        if (empty((Collection) prefix)) {
            return true;
        }
        if (empty((Collection) xs)) {
            return false;
        }
        return eq(prefix, take(length((Collection) prefix), (List) xs));
    }

    public static <T> void convert(T[] dst, Object[] src) {
        for (int i = 0; i < src.length; i++) {
            dst[i] = src;
        }
    }

    public static <T> List<T> concat(List<T> xs, List<T> ys) {
        List<T> zs = ref((List) xs);
        zs.addAll(ref((List) ys));
        return zs;
    }

    public static <T> T[] concat(T[] xs, T[] ys) {
        int i = 0;
        Object[] zs = (Object[]) new Object[(length((Object[]) xs) + length((Object[]) ys))];
        int length = xs.length;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            int i4 = i3 + 1;
            zs[i3] = xs[i2];
            i2++;
            i3 = i4;
        }
        i2 = ys.length;
        while (i < i2) {
            i4 = i3 + 1;
            zs[i3] = ys[i];
            i++;
            i3 = i4;
        }
        return zs;
    }

    public static int[] concat(int[] xs, int[] ys) {
        int i = 0;
        int[] zs = new int[(length(xs) + length(ys))];
        int length = xs.length;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            int i4 = i3 + 1;
            zs[i3] = xs[i2];
            i2++;
            i3 = i4;
        }
        i2 = ys.length;
        while (i < i2) {
            i4 = i3 + 1;
            zs[i3] = ys[i];
            i++;
            i3 = i4;
        }
        return zs;
    }

    public static <T> List<T> a(b<T> cmp, List<T> xs, List<T> ys) {
        ys = ref((List) ys);
        if (empty((Collection) xs)) {
            return ys;
        }
        for (T y : ys) {
            boolean e = false;
            for (T x : xs) {
                if (cmp.eq(x, y)) {
                    e = true;
                    break;
                }
            }
            if (!e) {
                xs.add(y);
            }
        }
        return xs;
    }

    public static <T> List<T> union(List<T> xs, List<T> ys) {
        return a(new 6(), (List) xs, (List) ys);
    }

    public static <T> List<T> b(b<T> eq, List<T> xs, List<T> ys) {
        List zs = toList((Collection) xs);
        for (T y : ys) {
            zs = a((b) eq, zs, (Object) y);
        }
        return zs;
    }

    public static <T> List<T> diff(List<T> xs, List<T> ys) {
        return b(new 7(), xs, ys);
    }

    public static <A, B> List<B> a(f<B, A> f, List<A> xs) {
        List<B> ys = new ArrayList();
        for (A x : ref((List) xs)) {
            ys.add(f.apply(x));
        }
        return ys;
    }

    public static <E> List<E> f(d<E> p, List<E> xs) {
        List<E> ys = new ArrayList();
        for (E x : xs) {
            if (p.pred(x)) {
                ys.add(x);
            }
        }
        return ys;
    }

    public static <S, E> S a(a<S, S, E> f, S s, Collection<E> xs) {
        if (!empty((Collection) xs)) {
            for (E x : xs) {
                s = f.apply(s, x);
            }
        }
        return s;
    }

    public static <E> List<E> insert(Comparator<E> cmp, E x, List<E> xs) {
        int pos = Collections.binarySearch(xs, x, cmp);
        if (pos < 0) {
            pos = (-pos) - 1;
        }
        xs.add((-pos) - 1, x);
        return xs;
    }

    public static <E> List<E> sort(Comparator<E> cmp, List<E> xs) {
        xs = ref((List) xs);
        try {
            Collections.sort(xs, cmp);
        } catch (Exception e) {
        }
        return xs;
    }

    public static int sum(Integer[] xs) {
        int n = 0;
        for (Integer intValue : xs) {
            n += intValue.intValue();
        }
        return n;
    }

    public static long sum(Long[] xs) {
        long n = 0;
        for (Long longValue : xs) {
            n += longValue.longValue();
        }
        return n;
    }

    public static int sum(List<Integer> xs) {
        int n = 0;
        for (Integer intValue : xs) {
            n += intValue.intValue();
        }
        return n;
    }

    public static long sum(List<Long> xs, Long l) {
        long n = 0;
        for (Long longValue : xs) {
            n += longValue.longValue();
        }
        return n;
    }

    public static int ord(boolean x) {
        return x ? 1 : 0;
    }

    public static int ord(Integer x) {
        return x == null ? 0 : x.intValue();
    }

    public static <E> List<E> replicate(int n, E x) {
        List<E> xs = new ArrayList();
        int n2 = n;
        while (true) {
            n = n2 - 1;
            if (n2 <= 0) {
                return xs;
            }
            xs.add(x);
            n2 = n;
        }
    }

    public static <E> List<E> replicate(int n, Callable<E> gen) {
        List<E> xs = new ArrayList();
        int n2 = n;
        while (true) {
            n = n2 - 1;
            if (n2 <= 0) {
                break;
            }
            try {
                xs.add(gen.call());
                n2 = n;
            } catch (Exception e) {
            }
        }
        return xs;
    }
}
