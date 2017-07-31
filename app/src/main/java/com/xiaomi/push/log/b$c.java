package com.xiaomi.push.log;

import android.content.SharedPreferences;
import com.huluxia.module.p;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.channel.commonutils.network.d;
import com.xiaomi.push.service.ag;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

class b$c extends b$b {
    String a;
    String b;
    File c;
    int d;
    boolean e;
    boolean f;
    final /* synthetic */ b g;

    b$c(b bVar, String str, String str2, File file, boolean z) {
        this.g = bVar;
        super(bVar);
        this.a = str;
        this.b = str2;
        this.c = file;
        this.f = z;
    }

    private boolean f() {
        int i;
        SharedPreferences sharedPreferences = b.a(this.g).getSharedPreferences("log.timestamp", 0);
        String string = sharedPreferences.getString("log.requst", "");
        long currentTimeMillis = System.currentTimeMillis();
        try {
            JSONObject jSONObject = new JSONObject(string);
            currentTimeMillis = jSONObject.getLong("time");
            i = jSONObject.getInt("times");
        } catch (JSONException e) {
            i = 0;
        }
        if (System.currentTimeMillis() - currentTimeMillis >= 86400000) {
            currentTimeMillis = System.currentTimeMillis();
            i = 0;
        } else if (i > 10) {
            return false;
        }
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("time", currentTimeMillis);
            jSONObject2.put("times", i + 1);
            sharedPreferences.edit().putString("log.requst", jSONObject2.toString()).commit();
        } catch (JSONException e2) {
            b.c("JSONException on put " + e2.getMessage());
        }
        return true;
    }

    public void b() {
        try {
            if (f()) {
                Map hashMap = new HashMap();
                hashMap.put(p.UID, ag.e());
                hashMap.put("token", this.b);
                hashMap.put("net", d.f(b.a(this.g)));
                d.a(this.a, hashMap, this.c, "file");
            }
            this.e = true;
        } catch (IOException e) {
        }
    }

    public void c() {
        if (!this.e) {
            this.d++;
            if (this.d < 3) {
                b.b(this.g).add(this);
            }
        }
        if (this.e || this.d >= 3) {
            this.c.delete();
        }
        b.a(this.g, (long) ((1 << this.d) * 1000));
    }

    public boolean d() {
        return d.e(b.a(this.g)) || (this.f && d.d(b.a(this.g)));
    }
}
