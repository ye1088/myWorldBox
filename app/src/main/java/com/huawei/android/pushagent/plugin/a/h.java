package com.huawei.android.pushagent.plugin.a;

import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushagent.plugin.tools.BLocation;
import com.MCWorld.ui.profile.ProfileScoreActivity;
import org.json.JSONObject;

public class h {
    private int a = -1;
    private String b;

    public int a() {
        return this.a;
    }

    public void a(String str) {
        JSONObject a = b.a(str);
        if (a == null) {
            e.a(BLocation.TAG, "enter ReportRsp.loadFromString, json is null");
            return;
        }
        this.a = a.optInt("resultcode", -1);
        this.b = a.optString(ProfileScoreActivity.bhf);
    }

    public String b() {
        return this.b;
    }

    public String toString() {
        return "resultCode:" + this.a + ";errorInfo:" + this.b;
    }
}
