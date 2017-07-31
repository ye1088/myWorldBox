package com.xiaomi.smack.util;

import com.xiaomi.channel.commonutils.misc.f.b;

final class j extends b {
    final /* synthetic */ Runnable a;

    j(Runnable runnable) {
        this.a = runnable;
    }

    public void b() {
        this.a.run();
    }
}
