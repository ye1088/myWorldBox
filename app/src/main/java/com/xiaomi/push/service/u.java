package com.xiaomi.push.service;

import com.xiaomi.stats.g;
import java.util.List;

final class u implements Runnable {
    final /* synthetic */ List a;
    final /* synthetic */ boolean b;

    u(List list, boolean z) {
        this.a = list;
        this.b = z;
    }

    public void run() {
        boolean a = t.b("www.baidu.com:80");
        boolean z = a;
        for (String a2 : this.a) {
            a = z || t.b(a2);
            if (a && !this.b) {
                break;
            }
            z = a;
        }
        a = z;
        g.a(a ? 1 : 2);
    }
}
