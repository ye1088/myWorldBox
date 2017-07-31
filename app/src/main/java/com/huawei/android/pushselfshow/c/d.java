package com.huawei.android.pushselfshow.c;

import android.content.Context;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushselfshow.b.a;
import com.huawei.android.pushselfshow.richpush.tools.b;
import com.tencent.connect.common.Constants;

public class d extends Thread {
    private Context a;
    private a b;

    public d(Context context, a aVar) {
        this.a = context;
        this.b = aVar;
    }

    public boolean a(Context context) {
        return "cosa".equals(this.b.o) ? b(context) : "email".equals(this.b.o) ? c(context) : "rp".equals(this.b.o) ? d(context) : true;
    }

    public boolean b(Context context) {
        if (com.huawei.android.pushselfshow.utils.a.b(context, this.b.z)) {
            return true;
        }
        com.huawei.android.pushselfshow.utils.a.a(context, "4", this.b);
        return false;
    }

    public boolean c(Context context) {
        if (com.huawei.android.pushselfshow.utils.a.d(context)) {
            return true;
        }
        com.huawei.android.pushselfshow.utils.a.a(context, Constants.VIA_REPORT_TYPE_WPA_STATE, this.b);
        return false;
    }

    public boolean d(Context context) {
        if (this.b.C == null || this.b.C.length() == 0) {
            com.huawei.android.pushselfshow.utils.a.a(context, Constants.VIA_SHARE_TYPE_INFO, this.b);
            e.a("PushSelfShowLog", "ilegle richpush param ,rpl is null");
            return false;
        }
        e.a("PushSelfShowLog", "rpl is " + this.b.C);
        if ("application/zip".equals(this.b.E) || this.b.C.endsWith(".zip")) {
            this.b.E = "application/zip";
            if (this.b.i == 1) {
                String a = new com.huawei.android.pushselfshow.richpush.tools.d().a(context, this.b.C, this.b.j, b.a("application/zip"));
                if (a != null && a.length() > 0) {
                    this.b.C = a;
                    this.b.E = "application/zip_local";
                }
                e.a("PushSelfShowLog", "Download first ,the localfile" + a);
            }
            return true;
        } else if ("text/html".equals(this.b.E) || this.b.C.endsWith(".html")) {
            this.b.E = "text/html";
            return true;
        } else {
            e.a("PushSelfShowLog", "unknow rpl type");
            com.huawei.android.pushselfshow.utils.a.a(context, Constants.VIA_SHARE_TYPE_INFO, this.b);
            return false;
        }
    }

    public void run() {
        e.a("PushSelfShowLog", "enter run()");
        try {
            if (a(this.a)) {
                b.a(this.a, this.b);
            }
        } catch (Exception e) {
        }
        super.run();
    }
}
