package com.huawei.android.pushagent.c.a;

import android.content.Context;
import com.huawei.android.pushagent.plugin.tools.BLocation;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONObject;

public class c {
    public String b = "";
    public HashMap c = new HashMap();
    protected Context d = null;

    public c(Context context, String str) {
        this.b = str;
        this.d = context;
    }

    public static HashMap g(String str) {
        HashMap hashMap = new HashMap();
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String valueOf = String.valueOf(keys.next());
                hashMap.put(valueOf, jSONObject.get(valueOf));
            }
        } catch (Throwable e) {
            e.c(BLocation.TAG, e.toString(), e);
        }
        return hashMap;
    }

    public int a(String str, int i) {
        Object b = b(str, Integer.valueOf(i));
        return b instanceof Integer ? ((Integer) b).intValue() : b instanceof Long ? (int) ((Long) b).longValue() : i;
    }

    public String a(String str, String str2) {
        return String.valueOf(b(str, str2));
    }

    public boolean a(String str, Object obj) {
        this.c.put(str, obj);
        new h(this.d, this.b).a(str, obj);
        return true;
    }

    public HashMap af() {
        HashMap hashMap = new HashMap();
        Map b = new h(this.d, this.b).b();
        if (b != null) {
            for (Entry entry : b.entrySet()) {
                if (entry != null) {
                    hashMap.put(entry.getKey(), entry.getValue());
                }
            }
        }
        if (hashMap.size() != 0) {
            this.c = hashMap;
        }
        return hashMap;
    }

    public boolean ag() {
        new h(this.d, this.b).a(this.c);
        return true;
    }

    public Object b(String str, Object obj) {
        Object f = f(str);
        return f == null ? obj : f;
    }

    public long c(String str, long j) {
        Object b = b(str, Long.valueOf(j));
        return b instanceof Integer ? (long) ((Integer) b).intValue() : b instanceof Long ? ((Long) b).longValue() : j;
    }

    public Object f(String str) {
        return this.c.get(str);
    }

    public String toString() {
        String str = " ";
        String str2 = ":";
        StringBuffer stringBuffer = new StringBuffer();
        for (Entry entry : this.c.entrySet()) {
            stringBuffer.append((String) entry.getKey()).append(str2).append(entry.getValue()).append(str);
        }
        return stringBuffer.toString();
    }
}
