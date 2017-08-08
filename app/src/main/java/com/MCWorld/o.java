package com.MCWorld;

import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.image.core.common.memory.MemoryTrimType;
import com.MCWorld.image.core.common.memory.a;
import com.MCWorld.image.core.common.memory.b;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Set;

/* compiled from: PainterMemoryTrimmableRegistry */
public class o implements ComponentCallbacks, b {
    private static final String TAG = "PainterMemoryTrimmableRegistry";
    private static o gi = null;
    private final Set<a> gg = Collections.newSetFromMap(new Hashtable());

    public static synchronized o ci() {
        o oVar;
        synchronized (o.class) {
            if (gi == null) {
                gi = new o();
            }
            oVar = gi;
        }
        return oVar;
    }

    public void a(a trimmable) {
        HLog.info(TAG, "register memory trimmable " + trimmable, new Object[0]);
        this.gg.add(trimmable);
    }

    public void b(a trimmable) {
        HLog.info(TAG, "unregister memory trimmable " + trimmable, new Object[0]);
        this.gg.remove(trimmable);
    }

    public void onConfigurationChanged(Configuration newConfig) {
    }

    public void onLowMemory() {
        HLog.warn(TAG, "on low memory so trim...", new Object[0]);
        for (a trimmable : this.gg) {
            trimmable.b(MemoryTrimType.OnSystemLowMemoryWhileAppInForeground);
        }
    }
}
