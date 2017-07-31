package com.huluxia.data.map;

import java.util.Comparator;

/* compiled from: FileItem */
public class b$a implements Comparator<b> {
    public /* synthetic */ int compare(Object obj, Object obj2) {
        return a((b) obj, (b) obj2);
    }

    public int a(b arg0, b arg1) {
        return arg1.date.compareTo(arg0.date);
    }
}
