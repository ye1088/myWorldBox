package com.MCWorld.data.map;

import java.util.Comparator;

/* compiled from: SizeComparator */
public class l implements Comparator<b> {
    public /* synthetic */ int compare(Object obj, Object obj2) {
        return a((b) obj, (b) obj2);
    }

    public int a(b arg0, b arg1) {
        long ret = arg0.size - arg1.size;
        if (ret > 0) {
            return 1;
        }
        if (ret == 0) {
            return 0;
        }
        return -1;
    }
}
