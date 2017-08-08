package com.MCWorld.image.drawee.components;

import android.os.Handler;
import android.os.Looper;
import com.MCWorld.framework.base.utils.Preconditions;
import java.util.HashSet;
import java.util.Set;

/* compiled from: DeferredReleaser */
public class a {
    private static a zG = null;
    private final Set<a> zH = new HashSet();
    private final Handler zI = new Handler(Looper.getMainLooper());
    private final Runnable zJ = new Runnable(this) {
        final /* synthetic */ a zK;

        {
            this.zK = this$0;
        }

        public void run() {
            a.iY();
            for (a releasable : this.zK.zH) {
                releasable.release();
            }
            this.zK.zH.clear();
        }
    };

    public static synchronized a iX() {
        a aVar;
        synchronized (a.class) {
            if (zG == null) {
                zG = new a();
            }
            aVar = zG;
        }
        return aVar;
    }

    public void a(a releasable) {
        iY();
        if (this.zH.add(releasable) && this.zH.size() == 1) {
            this.zI.post(this.zJ);
        }
    }

    public void b(a releasable) {
        iY();
        this.zH.remove(releasable);
    }

    private static void iY() {
        Preconditions.checkState(Looper.getMainLooper().getThread() == Thread.currentThread());
    }
}
