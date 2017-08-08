package com.MCWorld.image.core.common.memory;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

/* compiled from: MemoryUiTrimmableRegistry */
public class d {
    private static final Set<c> ye = Collections.newSetFromMap(new WeakHashMap());

    public static void a(c uiTrimmable) {
        ye.add(uiTrimmable);
    }

    public static Iterable<c> iterable() {
        return ye;
    }
}
