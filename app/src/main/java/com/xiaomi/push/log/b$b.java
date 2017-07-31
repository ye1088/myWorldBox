package com.xiaomi.push.log;

import com.xiaomi.channel.commonutils.misc.f.b;

class b$b extends b {
    long i = System.currentTimeMillis();
    final /* synthetic */ b j;

    b$b(b bVar) {
        this.j = bVar;
    }

    public void b() {
    }

    public boolean d() {
        return true;
    }

    final boolean e() {
        return System.currentTimeMillis() - this.i > 172800000;
    }
}
