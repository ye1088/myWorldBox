package com.huawei.android.pushagent.plugin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.huawei.android.pushagent.PushReceiver.BOUND_KEY;
import com.huawei.android.pushagent.c.a.b;
import com.huawei.android.pushagent.c.a.h;
import com.huawei.android.pushagent.plugin.a.e;
import com.huawei.android.pushagent.plugin.a.f;
import com.huawei.android.pushagent.plugin.a.g;
import com.huawei.android.pushagent.plugin.a.i;
import com.huawei.android.pushagent.plugin.tools.BLocation;
import com.huawei.android.pushagent.plugin.tools.b.a;
import com.tencent.open.utils.HttpUtils.NetworkUnavailableException;
import java.util.Date;
import org.apache.tools.ant.types.selectors.DepthSelector;
import org.json.JSONArray;
import org.json.JSONObject;

public class c {
    private Context a;
    private int b = 0;
    private e c;

    public c(Context context) {
        this.a = context;
        this.c = new e(context);
    }

    private String a(Context context) {
        com.huawei.android.pushagent.c.a.e.b(BLocation.TAG, "begin to fetch salt");
        String a = a.a(context, com.huawei.android.pushagent.plugin.tools.c.b(context));
        if (a == null) {
            return null;
        }
        i iVar = new i();
        iVar.a(a);
        com.huawei.android.pushagent.c.a.e.a(BLocation.TAG, " saltValue reponse");
        if (iVar.c() > 0) {
            this.c.a("minUp", Long.valueOf(iVar.c()));
        }
        if (iVar.d() > 0) {
            this.c.a("maxUp", Long.valueOf(iVar.d()));
        }
        if (iVar.b() > -1) {
            this.c.a("belongId", Integer.valueOf(iVar.b()));
        }
        if (TextUtils.isEmpty(iVar.a())) {
            com.huawei.android.pushagent.c.a.e.b(BLocation.TAG, "fetch salt fail");
            return null;
        }
        this.c.a(iVar.a());
        return String.valueOf(iVar.a().hashCode());
    }

    private void a(Context context, int i, boolean z) {
        context.sendBroadcast(new Intent("com.huawei.android.push.plugin.RESPONSE").putExtra(BOUND_KEY.PLUGINREPORTTYPE, i).putExtra(BOUND_KEY.PLUGINREPORTRESULT, z).putExtra("reportExtra", new Bundle()).setPackage(this.a.getPackageName()));
    }

    private void a(String str) {
        com.huawei.android.pushagent.c.a.e.a(BLocation.TAG, "add tags failed, need remove local tags");
        a(str, 1);
    }

    private void a(String str, int i) {
        try {
            JSONArray b = b.b(str);
            if (b != null) {
                h hVar = new h(this.a, "tags_info");
                int length = b.length();
                for (int i2 = 0; i2 < length; i2++) {
                    JSONObject optJSONObject = b.optJSONObject(i2);
                    if (optJSONObject != null) {
                        String optString = optJSONObject.optString("tagKey");
                        if (i == optJSONObject.optInt("opType")) {
                            hVar.f(optString);
                            com.huawei.android.pushagent.c.a.e.a(BLocation.TAG, "delete local tags:" + optJSONObject.toString());
                        }
                    }
                }
            }
        } catch (Throwable e) {
            com.huawei.android.pushagent.c.a.e.c(BLocation.TAG, "delete tags error:" + e.getMessage(), e);
        }
    }

    private boolean a() {
        return !TextUtils.isEmpty(this.c.a());
    }

    private void b(String str) {
        com.huawei.android.pushagent.c.a.e.a(BLocation.TAG, "delect tags success, need remove local tags");
        a(str, 2);
    }

    public void a(String str, int i, int i2) {
        try {
            if (-1 == b.a(this.a)) {
                com.huawei.android.pushagent.c.a.e.b(BLocation.TAG, NetworkUnavailableException.ERROR_INFO);
                a(this.a, i, false);
                if (f.TAG.b() == i) {
                    a(str);
                    return;
                }
                return;
            }
            long c = this.c.c("delayTime", 0);
            if (0 == c || c <= System.currentTimeMillis()) {
                this.c.e("delayTime");
                if (TextUtils.isEmpty(str)) {
                    a(this.a, i, false);
                    return;
                }
                Object b = com.huawei.android.pushagent.plugin.tools.c.b(this.a);
                if (TextUtils.isEmpty(b)) {
                    com.huawei.android.pushagent.c.a.e.b(BLocation.TAG, "token is null, need to register and get deviceToken");
                    com.huawei.android.pushagent.plugin.tools.c.a(this.a, new d(this, str, i, i2));
                    return;
                }
                Object valueOf;
                if (a()) {
                    valueOf = String.valueOf(this.c.a().hashCode());
                } else {
                    com.huawei.android.pushagent.c.a.e.b(BLocation.TAG, "salt is null, need to get salt");
                    valueOf = a(this.a);
                }
                if (TextUtils.isEmpty(valueOf)) {
                    a(this.a, i, false);
                    if (f.TAG.b() == i) {
                        a(str);
                        return;
                    }
                    return;
                }
                int i3;
                String a = a.a(this.a, new g(com.huawei.android.pushagent.c.a.a.h.a(b), i2, valueOf, str, this.a));
                com.huawei.android.pushagent.plugin.a.h hVar = new com.huawei.android.pushagent.plugin.a.h();
                if (a == null) {
                    i3 = -1;
                } else {
                    hVar.a(a);
                    i3 = hVar.a();
                    com.huawei.android.pushagent.c.a.e.a(BLocation.TAG, "ReportRsp is " + hVar.toString());
                }
                if (1 != i3 || this.b >= 3) {
                    this.b = 0;
                    if (i3 == 0) {
                        a(this.a, i, true);
                        if (f.LBS.b() == i) {
                            this.c.b(System.currentTimeMillis());
                            return;
                        } else if (f.TAG.b() == i) {
                            b(str);
                            return;
                        } else {
                            return;
                        }
                    } else if (3 == i3) {
                        a(this.a, i, true);
                        i3 = Integer.parseInt(hVar.b());
                        this.c.a("delayTime", Long.valueOf((((long) i3) * 60000) + System.currentTimeMillis()));
                        com.huawei.android.pushagent.c.a.e.a(BLocation.TAG, "please report after " + i3 + DepthSelector.MIN_KEY);
                        return;
                    } else {
                        a(this.a, i, false);
                        if (f.TAG.b() == i) {
                            a(str);
                            return;
                        }
                        return;
                    }
                }
                com.huawei.android.pushagent.c.a.e.b(BLocation.TAG, "salt has expired, need re-fetch");
                this.b++;
                this.c.b();
                a(str, i, i2);
                return;
            }
            com.huawei.android.pushagent.c.a.e.b(BLocation.TAG, "you can not repotr before " + new Date(c));
            a(this.a, i, false);
            if (f.TAG.b() == i) {
                a(str);
            }
        } catch (Throwable e) {
            a(this.a, i, false);
            if (f.TAG.b() == i) {
                a(str);
            }
            com.huawei.android.pushagent.c.a.e.c(BLocation.TAG, e.getMessage(), e);
        }
    }
}
