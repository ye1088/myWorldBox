package com.MCWorld.data.map;

import java.util.Comparator;

/* compiled from: NameComparator */
public class g implements Comparator<b> {
    public /* synthetic */ int compare(Object obj, Object obj2) {
        return a((b) obj, (b) obj2);
    }

    public int a(b arg0, b arg1) {
        return arg0.name.compareTo(arg1.name);
    }
}
