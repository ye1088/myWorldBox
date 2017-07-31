package com.huawei.android.pushagent.a.b;

import com.huawei.android.pushagent.c.a;
import java.io.Serializable;

public class g implements Serializable {
    private long a;
    private byte b;
    private byte c;

    public long a() {
        return this.a;
    }

    public void a(byte b) {
        this.b = b;
    }

    public void a(long j) {
        this.a = j;
    }

    public byte b() {
        return this.b;
    }

    public void b(byte b) {
        this.c = b;
    }

    public byte c() {
        return this.c;
    }

    public String toString() {
        return new StringBuffer(getClass().getSimpleName()).append(" netEventTime:").append(a.a(this.a, "yyyy-MM-dd HH:mm:ss SSS")).append(" netType:").append(this.b).append(" netEvent:").append(this.c).toString();
    }
}
