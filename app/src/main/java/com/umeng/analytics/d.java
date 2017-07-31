package com.umeng.analytics;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.analytics.onlineconfig.UmengOnlineConfigureListener;
import com.umeng.analytics.onlineconfig.a;
import java.util.HashMap;
import java.util.Map;
import u.aly.ac;
import u.aly.bj;
import u.aly.k;
import u.aly.m;
import u.aly.n;
import u.aly.t;
import u.aly.u;
import u.aly.v;
import u.aly.z;

/* compiled from: InternalAgent */
public class d implements t {
    private final a a = new a();
    private Context b = null;
    private c c;
    private m d = new m();
    private z e = new z();
    private v f = new v();
    private n g;
    private k h;
    private boolean i = false;

    d() {
        this.d.a(this);
    }

    private void f(Context context) {
        if (!this.i) {
            this.b = context.getApplicationContext();
            this.g = new n(this.b);
            this.h = k.a(this.b);
            this.i = true;
        }
    }

    void a(String str) {
        if (!AnalyticsConfig.ACTIVITY_DURATION_OPEN) {
            try {
                this.e.a(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void b(String str) {
        if (!AnalyticsConfig.ACTIVITY_DURATION_OPEN) {
            try {
                this.e.b(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void a(c cVar) {
        this.c = cVar;
    }

    public void a(int i) {
        AnalyticsConfig.mVerticalType = i;
    }

    public void a(String str, String str2) {
        AnalyticsConfig.mWrapperType = str;
        AnalyticsConfig.mWrapperVersion = str2;
    }

    void a(Context context) {
        if (context == null) {
            bj.b(a.e, "unexpected null context in onResume");
            return;
        }
        this.a.a(context);
        try {
            k.a(context).a(this.a);
        } catch (Exception e) {
        }
    }

    void a(UmengOnlineConfigureListener umengOnlineConfigureListener) {
        this.a.a(umengOnlineConfigureListener);
    }

    void b(final Context context) {
        if (context == null) {
            bj.b(a.e, "unexpected null context in onResume");
            return;
        }
        if (AnalyticsConfig.ACTIVITY_DURATION_OPEN) {
            this.e.a(context.getClass().getName());
        }
        try {
            if (!this.i) {
                f(context);
            }
            e.a(new f(this) {
                final /* synthetic */ d a;

                public void a() {
                    this.a.g(context.getApplicationContext());
                }
            });
        } catch (Exception e) {
            bj.b(a.e, "Exception occurred in Mobclick.onResume(). ", e);
        }
    }

    void c(final Context context) {
        if (context == null) {
            bj.b(a.e, "unexpected null context in onPause");
            return;
        }
        if (AnalyticsConfig.ACTIVITY_DURATION_OPEN) {
            this.e.b(context.getClass().getName());
        }
        try {
            if (!this.i) {
                f(context);
            }
            e.a(new f(this) {
                final /* synthetic */ d a;

                public void a() {
                    this.a.h(context.getApplicationContext());
                }
            });
        } catch (Exception e) {
            bj.b(a.e, "Exception occurred in Mobclick.onRause(). ", e);
        }
    }

    public void a(Context context, String str, HashMap<String, Object> hashMap) {
        try {
            if (!this.i) {
                f(context);
            }
            this.g.a(str, hashMap);
        } catch (Exception e) {
            bj.b(a.e, "", e);
        }
    }

    void a(Context context, String str) {
        if (!TextUtils.isEmpty(str)) {
            if (context == null) {
                bj.b(a.e, "unexpected null context in reportError");
                return;
            }
            try {
                if (!this.i) {
                    f(context);
                }
                this.h.a(new ac(str).a(false));
            } catch (Exception e) {
                bj.b(a.e, "", e);
            }
        }
    }

    void a(Context context, Throwable th) {
        if (context != null && th != null) {
            try {
                if (!this.i) {
                    f(context);
                }
                this.h.a(new ac(th).a(false));
            } catch (Exception e) {
                bj.b(a.e, "", e);
            }
        }
    }

    private void g(Context context) {
        this.f.c(context);
        if (this.c != null) {
            this.c.a();
        }
    }

    private void h(Context context) {
        this.f.d(context);
        this.e.a(context);
        if (this.c != null) {
            this.c.b();
        }
        this.h.b();
    }

    void d(Context context) {
        try {
            if (!this.i) {
                f(context);
            }
            this.h.a();
        } catch (Exception e) {
            bj.b(a.e, "", e);
        }
    }

    public void a(Context context, String str, String str2, long j, int i) {
        try {
            if (!this.i) {
                f(context);
            }
            this.g.a(str, str2, j, i);
        } catch (Exception e) {
            bj.b(a.e, "", e);
        }
    }

    void a(Context context, String str, Map<String, Object> map, long j) {
        try {
            if (!this.i) {
                f(context);
            }
            this.g.a(str, map, j);
        } catch (Exception e) {
            bj.b(a.e, "", e);
        }
    }

    void a(Context context, final String str, final String str2) {
        try {
            if (!this.i) {
                f(context);
            }
            e.a(new f(this) {
                final /* synthetic */ d a;

                public void a() {
                    this.a.g.a(str, str2);
                }
            });
        } catch (Exception e) {
            bj.b(a.e, "", e);
        }
    }

    void b(Context context, final String str, final String str2) {
        try {
            e.a(new f(this) {
                final /* synthetic */ d a;

                public void a() {
                    this.a.g.b(str, str2);
                }
            });
        } catch (Exception e) {
            bj.b(a.e, "", e);
        }
    }

    void a(Context context, final String str, final HashMap<String, Object> hashMap, final String str2) {
        try {
            if (!this.i) {
                f(context);
            }
            e.a(new f(this) {
                final /* synthetic */ d a;

                public void a() {
                    this.a.g.a(str, hashMap, str2);
                }
            });
        } catch (Exception e) {
            bj.b(a.e, "", e);
        }
    }

    void c(Context context, final String str, final String str2) {
        try {
            e.a(new f(this) {
                final /* synthetic */ d a;

                public void a() {
                    this.a.g.c(str, str2);
                }
            });
        } catch (Exception e) {
            bj.b(a.e, "", e);
        }
    }

    void e(Context context) {
        try {
            this.e.a();
            h(context);
            u.a(context).edit().commit();
            e.a();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void a(Throwable th) {
        try {
            this.e.a();
            if (this.b != null) {
                if (!(th == null || this.h == null)) {
                    this.h.b(new ac(th));
                }
                h(this.b);
                u.a(this.b).edit().commit();
            }
            e.a();
        } catch (Exception e) {
            bj.a(a.e, "Exception in onAppCrash", e);
        }
    }
}
