package com.MCWorld.aa;

import android.app.Application;
import android.content.Context;

/* compiled from: EncryptConfig */
public class d {
    private static d lY;
    private Context mContext;

    public static synchronized d dD() {
        d dVar;
        synchronized (d.class) {
            if (lY == null) {
                lY = new d();
            }
            dVar = lY;
        }
        return dVar;
    }

    private d() {
    }

    public void a(Application context) {
        this.mContext = context;
    }

    public Context getContext() {
        return this.mContext;
    }
}
