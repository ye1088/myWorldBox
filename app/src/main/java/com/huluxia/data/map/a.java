package com.huluxia.data.map;

import java.util.Comparator;

/* compiled from: DateComparator */
public class a implements Comparator<b> {
    public /* synthetic */ int compare(Object obj, Object obj2) {
        return a((b) obj, (b) obj2);
    }

    public int a(b arg0, b arg1) {
        return arg0.date.compareTo(arg1.date);
    }
}
