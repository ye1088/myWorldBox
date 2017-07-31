package com.huawei.android.pushagent.plugin.a;

import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushagent.plugin.tools.BLocation;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class d {
    private String a;
    private int b;
    private List c = new ArrayList();
    private double d = -1.0d;
    private double e = -1.0d;
    private boolean f = false;

    public JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("mccmnc", this.a);
            jSONObject.put("phoneType", this.b);
            if (this.c == null || this.c.size() == 0) {
                e.d(BLocation.TAG, "locations is null");
                return null;
            }
            JSONArray jSONArray = new JSONArray();
            for (b a : this.c) {
                JSONObject a2 = a.a();
                if (a2 != null) {
                    jSONArray.put(a2);
                }
            }
            jSONObject.put("location", jSONArray);
            if (!(this.d == -1.0d || this.e == -1.0d)) {
                jSONObject.put("lng", this.d);
                jSONObject.put("lat", this.e);
            }
            return jSONObject;
        } catch (Throwable e) {
            e.c(BLocation.TAG, "parse GSMInfo to json error", e);
            return null;
        } catch (Throwable e2) {
            e.c(BLocation.TAG, "parse GSMInfo to json error", e2);
            return null;
        }
    }

    public void a(double d, double d2) {
        if (d != -1.0d && d2 != -1.0d) {
            this.d = d;
            this.e = d2;
            this.f = true;
        }
    }

    public void a(int i) {
        this.b = i;
    }

    public void a(String str) {
        this.a = str;
    }

    public void a(List list) {
        this.c = list;
    }

    public boolean b() {
        e.a(BLocation.TAG, "hasLonLat:" + this.f);
        return this.f;
    }
}
