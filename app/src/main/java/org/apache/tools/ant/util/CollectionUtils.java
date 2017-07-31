package org.apache.tools.ant.util;

import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class CollectionUtils {
    @Deprecated
    public static final List EMPTY_LIST = Collections.EMPTY_LIST;

    public static boolean equals(Vector<?> v1, Vector<?> v2) {
        if (v1 == v2) {
            return true;
        }
        if (v1 == null || v2 == null) {
            return false;
        }
        return v1.equals(v2);
    }

    public static boolean equals(Dictionary<?, ?> d1, Dictionary<?, ?> d2) {
        if (d1 == d2) {
            return true;
        }
        if (d1 == null || d2 == null) {
            return false;
        }
        if (d1.size() != d2.size()) {
            return false;
        }
        Enumeration<?> e1 = d1.keys();
        while (e1.hasMoreElements()) {
            Object key = e1.nextElement();
            Object value1 = d1.get(key);
            Object value2 = d2.get(key);
            if (value2 != null) {
                if (!value1.equals(value2)) {
                }
            }
            return false;
        }
        return true;
    }

    public static String flattenToString(Collection<?> c) {
        StringBuilder sb = new StringBuilder();
        for (Object o : c) {
            if (sb.length() != 0) {
                sb.append(MiPushClient.ACCEPT_TIME_SEPARATOR);
            }
            sb.append(o);
        }
        return sb.toString();
    }

    public static <K, V> void putAll(Dictionary<? super K, ? super V> m1, Dictionary<? extends K, ? extends V> m2) {
        Enumeration<? extends K> it = m2.keys();
        while (it.hasMoreElements()) {
            K key = it.nextElement();
            m1.put(key, m2.get(key));
        }
    }

    public static <E> Enumeration<E> append(Enumeration<E> e1, Enumeration<E> e2) {
        return new CompoundEnumeration(e1, e2);
    }

    public static <E> Enumeration<E> asEnumeration(Iterator<E> iter) {
        return new 1(iter);
    }

    public static <E> Iterator<E> asIterator(Enumeration<E> e) {
        return new 2(e);
    }

    public static <T> Collection<T> asCollection(Iterator<? extends T> iter) {
        List<T> l = new ArrayList();
        while (iter.hasNext()) {
            l.add(iter.next());
        }
        return l;
    }

    public static int frequency(Collection<?> c, Object o) {
        int freq = 0;
        if (c != null) {
            for (Object test : c) {
                if (o == null) {
                    if (test != null) {
                    }
                } else if (o.equals(test)) {
                }
                freq++;
            }
        }
        return freq;
    }
}
