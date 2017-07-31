package com.huluxia.framework.base.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public final class Sets {
    private Sets() {
    }

    public static <E> HashSet<E> newHashSet() {
        return new HashSet();
    }

    public static <E> HashSet<E> newHashSet(E... elements) {
        HashSet<E> set = newHashSetWithCapacity(elements.length);
        Collections.addAll(set, elements);
        return set;
    }

    public static <E> HashSet<E> newHashSetWithCapacity(int capacity) {
        return new HashSet(capacity);
    }

    public static <E> HashSet<E> newHashSet(Iterable<? extends E> elements) {
        if (elements instanceof Collection) {
            return new HashSet((Collection) elements);
        }
        return newHashSet(elements.iterator());
    }

    public static <E> HashSet<E> newHashSet(Iterator<? extends E> elements) {
        HashSet<E> set = newHashSet();
        while (elements.hasNext()) {
            set.add(elements.next());
        }
        return set;
    }

    public static <E> Set<E> newIdentityHashSet() {
        return newSetFromMap(new IdentityHashMap());
    }

    public static <E> Set<E> newSetFromMap(Map<E, Boolean> map) {
        return Collections.newSetFromMap(map);
    }

    public static <E> CopyOnWriteArraySet<E> newCopyOnWriteArraySet() {
        return new CopyOnWriteArraySet();
    }

    public static <E> LinkedHashSet<E> newLinkedHashSet() {
        return new LinkedHashSet();
    }
}
