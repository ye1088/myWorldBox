package com.xiaomi.xmpush.thrift;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public enum r$a {
    DEBUG((short) 1, "debug"),
    TARGET((short) 2, "target"),
    ID((short) 3, "id"),
    APP_ID((short) 4, "appId"),
    TYPE((short) 5, "type"),
    REQUIRE_ACK((short) 6, "requireAck"),
    PAYLOAD((short) 7, "payload"),
    EXTRA((short) 8, "extra"),
    PACKAGE_NAME((short) 9, "packageName"),
    CATEGORY((short) 10, "category"),
    BINARY_EXTRA((short) 14, "binaryExtra");
    
    private static final Map<String, r$a> l = null;
    private final short m;
    private final String n;

    static {
        l = new HashMap();
        Iterator it = EnumSet.allOf(r$a.class).iterator();
        while (it.hasNext()) {
            r$a com_xiaomi_xmpush_thrift_r_a = (r$a) it.next();
            l.put(com_xiaomi_xmpush_thrift_r_a.a(), com_xiaomi_xmpush_thrift_r_a);
        }
    }

    private r$a(short s, String str) {
        this.m = s;
        this.n = str;
    }

    public String a() {
        return this.n;
    }
}
