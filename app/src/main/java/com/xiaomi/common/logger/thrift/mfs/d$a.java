package com.xiaomi.common.logger.thrift.mfs;

import com.MCWorld.version.d;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public enum d$a {
    IP((short) 1, "ip"),
    FAILED_COUNT((short) 2, "failed_count"),
    SUCCESS_COUNT((short) 3, "success_count"),
    DURATION((short) 4, "duration"),
    SIZE((short) 5, d.SIZE),
    EXP_INFO((short) 6, "exp_info"),
    HTTP_INFO((short) 7, "http_info");
    
    private static final Map<String, d$a> h = null;
    private final short i;
    private final String j;

    static {
        h = new HashMap();
        Iterator it = EnumSet.allOf(d$a.class).iterator();
        while (it.hasNext()) {
            d$a com_xiaomi_common_logger_thrift_mfs_d_a = (d$a) it.next();
            h.put(com_xiaomi_common_logger_thrift_mfs_d_a.a(), com_xiaomi_common_logger_thrift_mfs_d_a);
        }
    }

    private d$a(short s, String str) {
        this.i = s;
        this.j = str;
    }

    public String a() {
        return this.j;
    }
}
