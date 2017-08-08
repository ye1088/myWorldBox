package com.MCWorld.data.map;

import java.util.Comparator;

/* compiled from: FileItem */
public class b$b implements Comparator<b> {
    public /* synthetic */ int compare(Object obj, Object obj2) {
        return a((b) obj, (b) obj2);
    }

    public int a(b arg0, b arg1) {
        return String.valueOf(b.e(arg1)).compareTo(String.valueOf(b.e(arg0)));
    }
}
