package com.huawei.android.pushagent.a;

import com.huawei.android.pushagent.b.a.b.b.a;

public class d {
    public String a;
    public int b;
    public a c;
    public boolean d;

    public d(String str, int i, boolean z, a aVar) {
        this.a = str;
        this.b = i;
        this.d = z;
        this.c = aVar;
    }

    public String toString() {
        return new StringBuffer().append("ip:").append(this.a).append(" port:").append(this.b).append(" useProxy:").append(this.d).append(" conType").append(this.c).toString();
    }
}
