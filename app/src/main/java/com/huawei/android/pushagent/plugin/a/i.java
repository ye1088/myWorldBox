package com.huawei.android.pushagent.plugin.a;

import com.huawei.android.pushagent.c.a.b;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushagent.plugin.tools.BLocation;
import org.json.JSONObject;

public class i {
    private String a;
    private String b;
    private int c = -1;
    private long d = 0;
    private long e = 0;

    public String a() {
        return this.b;
    }

    public void a(String str) {
        JSONObject a = b.a(str);
        if (a == null) {
            e.a(BLocation.TAG, "enter SaltRsp.loadFromString, json is null");
            return;
        }
        this.a = a.optString("resultcode");
        this.b = a.optString("salt");
        this.c = a.optInt("belongId", -1);
        this.d = a.optLong("minUp", 0);
        this.e = a.optLong("maxUp", 0);
    }

    public int b() {
        return this.c;
    }

    public long c() {
        return this.d * 1000;
    }

    public long d() {
        return this.e * 1000;
    }

    public String toString() {
        return "resultCode:" + this.a + ";salt:" + this.b + ";belongId:" + this.c + ";minUp:" + this.d + ";maxUp:" + this.e;
    }
}
