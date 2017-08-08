package com.MCWorld.image.base.cache.common;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/* compiled from: CacheKeyUtil */
public final class c {
    public static List<String> a(b key) {
        try {
            List<String> ids;
            if (key instanceof e) {
                List<b> keys = ((e) key).gl();
                ids = new ArrayList(keys.size());
                for (int i = 0; i < keys.size(); i++) {
                    ids.add(c((b) keys.get(i)));
                }
                return ids;
            }
            ids = new ArrayList(1);
            ids.add(c(key));
            return ids;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String b(b key) {
        try {
            if (key instanceof e) {
                return c((b) ((e) key).gl().get(0));
            }
            return c(key);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String c(b key) throws UnsupportedEncodingException {
        return com.MCWorld.image.core.common.util.c.p(key.getUriString().getBytes("UTF-8"));
    }
}
