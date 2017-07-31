package com.tencent.smtt.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.tencent.tbs.video.interfaces.IUserStateChangedListener;
import com.tencent.tbs.video.interfaces.a;

class ac {
    private static ac e = null;
    ae a = null;
    Context b;
    a c;
    IUserStateChangedListener d;

    private ac(Context context) {
        this.b = context.getApplicationContext();
        this.a = new ae(this.b);
    }

    public static synchronized ac a(Context context) {
        ac acVar;
        synchronized (ac.class) {
            if (e == null) {
                e = new ac(context);
            }
            acVar = e;
        }
        return acVar;
    }

    public void a(int i, int i2, Intent intent) {
        if (this.c != null) {
            this.c.a(i, i2, intent);
        }
    }

    void a(Activity activity, int i) {
        this.a.a(activity, i);
    }

    public boolean a() {
        this.a.a();
        return this.a.b();
    }

    public boolean a(String str, Bundle bundle, a aVar) {
        Object obj;
        if (bundle == null) {
            bundle = new Bundle();
        }
        if (!TextUtils.isEmpty(str)) {
            bundle.putString("videoUrl", str);
        }
        if (aVar != null) {
            this.a.a();
            if (!this.a.b()) {
                return false;
            }
            this.c = aVar;
            this.d = new ad(this);
            this.c.a(this.d);
            bundle.putInt("callMode", 3);
        } else {
            bundle.putInt("callMode", 1);
        }
        ae aeVar = this.a;
        if (aVar == null) {
            obj = null;
        }
        aeVar.a(bundle, obj);
        return true;
    }
}
