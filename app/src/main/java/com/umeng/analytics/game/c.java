package com.umeng.analytics.game;

import android.content.Context;
import com.tencent.open.SocialConstants;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.Gender;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.a;
import com.umeng.analytics.d;
import com.umeng.analytics.e;
import com.umeng.analytics.f;
import java.util.HashMap;
import u.aly.bj;

/* compiled from: InternalGameAgent */
class c implements com.umeng.analytics.c {
    private d a = MobclickAgent.getAgent();
    private b b = null;
    private final int c = 100;
    private final int d = 1;
    private final int e;
    private final int f;
    private final int g;
    private final String h;
    private final String i;
    private final String j;
    private final String k;
    private final String l;
    private final String m;
    private final String n;
    private final String o;
    private final String p;
    private final String q;
    private final String r;
    private final String s;
    private final String t;
    private final String u;
    private final String v;
    private final String w;
    private Context x;

    public c() {
        this.a.a(1);
        this.e = 0;
        this.f = -1;
        this.g = 1;
        this.h = "level";
        this.i = "pay";
        this.j = "buy";
        this.k = "use";
        this.l = "bonus";
        this.m = "item";
        this.n = "cash";
        this.o = "coin";
        this.p = SocialConstants.PARAM_SOURCE;
        this.q = "amount";
        this.r = "user_level";
        this.s = "bonus_source";
        this.t = "level";
        this.u = "status";
        this.v = "duration";
        this.w = "UMGameAgent.init(Context) should be called before any game api";
        a.a = true;
    }

    void a(Context context) {
        if (context == null) {
            bj.b(a.e, "Context is null, can't init GameAgent");
            return;
        }
        this.x = context.getApplicationContext();
        this.a.a((com.umeng.analytics.c) this);
        this.b = new b(this.x);
    }

    void a(boolean z) {
        bj.c(a.e, String.format("Trace sleep time : %b", new Object[]{Boolean.valueOf(z)}));
        a.a = z;
    }

    void a(String str, int i, Gender gender, String str2) {
        AnalyticsConfig.sId = str;
        AnalyticsConfig.sAge = i;
        AnalyticsConfig.sGender = gender;
        AnalyticsConfig.sSource = str2;
    }

    void a(String str) {
        this.b.b = str;
    }

    void b(final String str) {
        if (this.x == null) {
            bj.b(a.e, "UMGameAgent.init(Context) should be called before any game api");
            return;
        }
        this.b.a = str;
        e.a(new f(this) {
            final /* synthetic */ c a;

            public void a() {
                this.a.b.a(str);
                HashMap hashMap = new HashMap();
                hashMap.put("level", str);
                hashMap.put("status", Integer.valueOf(0));
                if (this.a.b.b != null) {
                    hashMap.put("user_level", this.a.b.b);
                }
                this.a.a.a(this.a.x, "level", hashMap);
            }
        });
    }

    private void a(final String str, final int i) {
        if (this.x == null) {
            bj.b(a.e, "UMGameAgent.init(Context) should be called before any game api");
        } else {
            e.a(new f(this) {
                final /* synthetic */ c a;

                public void a() {
                    a b = this.a.b.b(str);
                    if (b != null) {
                        long e = b.e();
                        if (e <= 0) {
                            bj.c(a.e, "level duration is 0");
                            return;
                        }
                        HashMap hashMap = new HashMap();
                        hashMap.put("level", str);
                        hashMap.put("status", Integer.valueOf(i));
                        hashMap.put("duration", Long.valueOf(e));
                        if (this.a.b.b != null) {
                            hashMap.put("user_level", this.a.b.b);
                        }
                        this.a.a.a(this.a.x, "level", hashMap);
                        return;
                    }
                    bj.e(a.e, String.format("finishLevel(or failLevel) called before startLevel", new Object[0]));
                }
            });
        }
    }

    void c(String str) {
        if (this.x == null) {
            bj.b(a.e, "UMGameAgent.init(Context) should be called before any game api");
        } else {
            a(str, 1);
        }
    }

    void d(String str) {
        if (this.x == null) {
            bj.b(a.e, "UMGameAgent.init(Context) should be called before any game api");
        } else {
            a(str, -1);
        }
    }

    void a(double d, double d2, int i) {
        if (this.x == null) {
            bj.b(a.e, "UMGameAgent.init(Context) should be called before any game api");
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("cash", Long.valueOf((long) (d * 100.0d)));
        hashMap.put("coin", Long.valueOf((long) (d2 * 100.0d)));
        hashMap.put(SocialConstants.PARAM_SOURCE, Integer.valueOf(i));
        if (this.b.b != null) {
            hashMap.put("user_level", this.b.b);
        }
        if (this.b.a != null) {
            hashMap.put("level", this.b.a);
        }
        this.a.a(this.x, "pay", hashMap);
    }

    void a(double d, String str, int i, double d2, int i2) {
        a(d, d2 * ((double) i), i2);
        a(str, i, d2);
    }

    void a(String str, int i, double d) {
        if (this.x == null) {
            bj.b(a.e, "UMGameAgent.init(Context) should be called before any game api");
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("item", str);
        hashMap.put("amount", Integer.valueOf(i));
        hashMap.put("coin", Long.valueOf((long) ((((double) i) * d) * 100.0d)));
        if (this.b.b != null) {
            hashMap.put("user_level", this.b.b);
        }
        if (this.b.a != null) {
            hashMap.put("level", this.b.a);
        }
        this.a.a(this.x, "buy", hashMap);
    }

    void b(String str, int i, double d) {
        if (this.x == null) {
            bj.b(a.e, "UMGameAgent.init(Context) should be called before any game api");
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("item", str);
        hashMap.put("amount", Integer.valueOf(i));
        hashMap.put("coin", Long.valueOf((long) ((((double) i) * d) * 100.0d)));
        if (this.b.b != null) {
            hashMap.put("user_level", this.b.b);
        }
        if (this.b.a != null) {
            hashMap.put("level", this.b.a);
        }
        this.a.a(this.x, "use", hashMap);
    }

    void a(double d, int i) {
        if (this.x == null) {
            bj.b(a.e, "UMGameAgent.init(Context) should be called before any game api");
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("coin", Long.valueOf((long) (100.0d * d)));
        hashMap.put("bonus_source", Integer.valueOf(i));
        if (this.b.b != null) {
            hashMap.put("user_level", this.b.b);
        }
        if (this.b.a != null) {
            hashMap.put("level", this.b.a);
        }
        this.a.a(this.x, "bonus", hashMap);
    }

    void a(String str, int i, double d, int i2) {
        a(((double) i) * d, i2);
        a(str, i, d);
    }

    public void a() {
        bj.c(a.e, "App resume from background");
        if (this.x == null) {
            bj.b(a.e, "UMGameAgent.init(Context) should be called before any game api");
        } else if (a.a) {
            this.b.b();
        }
    }

    public void b() {
        if (this.x == null) {
            bj.b(a.e, "UMGameAgent.init(Context) should be called before any game api");
        } else if (a.a) {
            this.b.a();
        }
    }
}
