package com.MCWorld;

import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.image.core.common.disk.a;
import com.MCWorld.image.core.common.disk.b;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Set;

/* compiled from: PainterDiskTrimmableRegistry */
public class n implements b {
    private static final String TAG = "PainterDiskTrimmableRegistry";
    private static n gh = null;
    private final Set<a> gg = Collections.newSetFromMap(new Hashtable());

    public static synchronized n cg() {
        n nVar;
        synchronized (n.class) {
            if (gh == null) {
                gh = new n();
            }
            nVar = gh;
        }
        return nVar;
    }

    public void a(a trimmable) {
        HLog.info(TAG, "register disk trimmable " + trimmable, new Object[0]);
        this.gg.add(trimmable);
    }

    public void b(a trimmable) {
        HLog.info(TAG, "unregister disk trimmable " + trimmable, new Object[0]);
        this.gg.remove(trimmable);
    }
}
