package com.xiaomi.common.logger.thrift.mfs;

import com.huluxia.framework.BaseHttpMgr;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public enum b$a {
    CATEGORY((short) 1, "category"),
    UUID((short) 2, "uuid"),
    VERSION((short) 3, "version"),
    NETWORK((short) 4, "network"),
    CLIENT_IP((short) 5, "client_ip"),
    LOCATION((short) 6, "location"),
    HOST_INFO((short) 7, "host_info"),
    VERSION_TYPE((short) 8, "version_type"),
    APP_NAME((short) 9, "app_name"),
    APP_VERSION((short) 10, BaseHttpMgr.PARAM_APP_VERSION);
    
    private static final Map<String, b$a> k = null;
    private final short l;
    private final String m;

    static {
        k = new HashMap();
        Iterator it = EnumSet.allOf(b$a.class).iterator();
        while (it.hasNext()) {
            b$a com_xiaomi_common_logger_thrift_mfs_b_a = (b$a) it.next();
            k.put(com_xiaomi_common_logger_thrift_mfs_b_a.a(), com_xiaomi_common_logger_thrift_mfs_b_a);
        }
    }

    private b$a(short s, String str) {
        this.l = s;
        this.m = str;
    }

    public String a() {
        return this.m;
    }
}
