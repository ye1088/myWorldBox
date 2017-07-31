package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.channel.commonutils.misc.d.a;
import com.xiaomi.push.service.v;
import com.xiaomi.push.service.w;
import com.xiaomi.xmpush.thrift.ad;
import com.xiaomi.xmpush.thrift.c;
import com.xiaomi.xmpush.thrift.f;
import com.xiaomi.xmpush.thrift.l;
import com.xiaomi.xmpush.thrift.r;
import org.apache.thrift.b;

public class g extends a {
    private Context a;

    public g(Context context) {
        this.a = context;
    }

    public int a() {
        return 2;
    }

    public void run() {
        v a = v.a(this.a);
        b lVar = new l();
        lVar.a(w.a(a, c.MISC_CONFIG));
        lVar.b(w.a(a, c.PLUGIN_CONFIG));
        b rVar = new r("-1", false);
        rVar.c(f.DailyCheckClientConfig.p);
        rVar.a(ad.a(lVar));
        j.a(this.a).a(rVar, com.xiaomi.xmpush.thrift.a.i, null);
    }
}
