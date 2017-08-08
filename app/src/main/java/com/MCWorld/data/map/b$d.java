package com.MCWorld.data.map;

import java.util.Comparator;

/* compiled from: FileItem */
public class b$d implements Comparator<b> {
    public /* synthetic */ int compare(Object obj, Object obj2) {
        return a((b) obj, (b) obj2);
    }

    public int a(b arg0, b arg1) {
        long ret = arg1.size - arg0.size;
        if (ret > 0) {
            return 1;
        }
        if (ret == 0) {
            return 0;
        }
        return -1;
    }
}
