package com.huluxia.framework.base.utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ImmutableSet<E> extends HashSet<E> {
    private ImmutableSet(Set<E> set) {
        super(set);
    }

    public static <E> ImmutableSet<E> copyOf(Set<E> set) {
        return new ImmutableSet(set);
    }

    public static <E> ImmutableSet<E> of(E... elements) {
        HashSet<E> set = new HashSet();
        Collections.addAll(set, elements);
        return new ImmutableSet(set);
    }
}
