package com.mojang.minecraftpe;

import java.util.HashMap;
import java.util.Map;

/* compiled from: RealmsRedirectInfo */
public class b {
    public static Map<String, b> bIC = new HashMap();
    public String bIA;
    public String bIB = "NONE";
    public String bIz = null;

    public b(String peoUrl, String accountUrl, String loginUrl) {
        this.bIB = peoUrl;
        this.bIA = accountUrl;
        this.bIz = loginUrl;
    }

    static {
        a(new b("NONE", null, a.bId));
    }

    private static void a(b info) {
        bIC.put(info.bIB, info);
    }
}
