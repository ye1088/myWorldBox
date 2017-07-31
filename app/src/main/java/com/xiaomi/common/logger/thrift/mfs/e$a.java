package com.xiaomi.common.logger.thrift.mfs;

import com.tencent.mm.sdk.plugin.BaseProfile;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public enum e$a {
    CONTRY((short) 1, "contry"),
    PROVINCE((short) 2, BaseProfile.COL_PROVINCE),
    CITY((short) 3, BaseProfile.COL_CITY),
    ISP((short) 4, "isp");
    
    private static final Map<String, e$a> e = null;
    private final short f;
    private final String g;

    static {
        e = new HashMap();
        Iterator it = EnumSet.allOf(e$a.class).iterator();
        while (it.hasNext()) {
            e$a com_xiaomi_common_logger_thrift_mfs_e_a = (e$a) it.next();
            e.put(com_xiaomi_common_logger_thrift_mfs_e_a.a(), com_xiaomi_common_logger_thrift_mfs_e_a);
        }
    }

    private e$a(short s, String str) {
        this.f = s;
        this.g = str;
    }

    public String a() {
        return this.g;
    }
}
