package com.huluxia.data.map;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/* compiled from: FileItem */
public class b$c implements Comparator<b> {
    public /* synthetic */ int compare(Object obj, Object obj2) {
        return a((b) obj, (b) obj2);
    }

    public int a(b arg0, b arg1) {
        return Collator.getInstance(Locale.CHINESE).compare(arg0.name, arg1.name);
    }
}
