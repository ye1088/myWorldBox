package com.xiaomi.xmpush.thrift;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public enum s$a {
    DEBUG((short) 1, "debug"),
    TARGET((short) 2, "target"),
    ID((short) 3, "id"),
    APP_ID((short) 4, "appId"),
    APP_VERSION((short) 5, "appVersion"),
    PACKAGE_NAME((short) 6, "packageName"),
    TOKEN((short) 7, "token"),
    DEVICE_ID((short) 8, "deviceId"),
    ALIAS_NAME((short) 9, "aliasName"),
    SDK_VERSION((short) 10, "sdkVersion"),
    REG_ID((short) 11, "regId"),
    PUSH_SDK_VERSION_NAME((short) 12, "pushSdkVersionName"),
    PUSH_SDK_VERSION_CODE((short) 13, "pushSdkVersionCode"),
    APP_VERSION_CODE((short) 14, "appVersionCode"),
    ANDROID_ID((short) 15, "androidId"),
    IMEI((short) 16, "imei"),
    SERIAL((short) 17, "serial"),
    IMEI_MD5((short) 18, "imeiMd5"),
    SPACE_ID((short) 19, "spaceId"),
    CONNECTION_ATTRS((short) 100, "connectionAttrs"),
    CLEAN_OLD_REG_INFO((short) 101, "cleanOldRegInfo"),
    OLD_REG_ID((short) 102, "oldRegId");
    
    private static final Map<String, s$a> w = null;
    private final short x;
    private final String y;

    static {
        w = new HashMap();
        Iterator it = EnumSet.allOf(s$a.class).iterator();
        while (it.hasNext()) {
            s$a com_xiaomi_xmpush_thrift_s_a = (s$a) it.next();
            w.put(com_xiaomi_xmpush_thrift_s_a.a(), com_xiaomi_xmpush_thrift_s_a);
        }
    }

    private s$a(short s, String str) {
        this.x = s;
        this.y = str;
    }

    public String a() {
        return this.y;
    }
}
