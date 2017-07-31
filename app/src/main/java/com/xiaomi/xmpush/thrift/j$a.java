package com.xiaomi.xmpush.thrift;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public enum j$a {
    CHANNEL_ID((short) 1, "channelId"),
    USER_ID((short) 2, "userId"),
    SERVER((short) 3, "server"),
    RESOURCE((short) 4, "resource"),
    IS_PREVIEW((short) 5, "isPreview");
    
    private static final Map<String, j$a> f = null;
    private final short g;
    private final String h;

    static {
        f = new HashMap();
        Iterator it = EnumSet.allOf(j$a.class).iterator();
        while (it.hasNext()) {
            j$a com_xiaomi_xmpush_thrift_j_a = (j$a) it.next();
            f.put(com_xiaomi_xmpush_thrift_j_a.a(), com_xiaomi_xmpush_thrift_j_a);
        }
    }

    private j$a(short s, String str) {
        this.g = s;
        this.h = str;
    }

    public String a() {
        return this.h;
    }
}
