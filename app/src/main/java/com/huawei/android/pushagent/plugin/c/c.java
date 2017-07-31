package com.huawei.android.pushagent.plugin.c;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.android.pushagent.c.a.b;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushagent.plugin.a.f;
import com.huawei.android.pushagent.plugin.tools.BLocation;
import org.json.JSONArray;

public class c extends b {
    private String a = "";

    public c(String str) {
        this.a = str;
    }

    public int a() {
        return f.TAG.b();
    }

    public String a(Context context) {
        if (TextUtils.isEmpty(this.a)) {
            e.b(BLocation.TAG, "tag is null");
            return null;
        }
        JSONArray b = b.b(this.a);
        return b != null ? b.toString() : null;
    }

    public String b() {
        return f.TAG.a();
    }

    public int c() {
        return a();
    }
}
