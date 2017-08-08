package com.MCWorld;

import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.Supplier;
import com.MCWorld.image.pipeline.memory.a;
import com.MCWorld.image.pipeline.memory.c;

/* compiled from: Painter */
class l$2 implements Supplier<c> {
    final /* synthetic */ l gd;

    l$2(l this$0) {
        this.gd = this$0;
    }

    public /* synthetic */ Object get() {
        return cd();
    }

    public c cd() {
        return new c(this) {
            final /* synthetic */ l$2 ge;

            {
                this.ge = this$1;
            }

            public void a(a counter) {
                if (counter.getCount() >= counter.nP() || counter.getSize() > ((long) counter.getMaxSize())) {
                    HLog.warn("Painter", "bitmap counter tracker increase hit to limit", new Object[0]);
                    this.ge.gd.onLowMemory();
                }
            }

            public void b(a counter) {
            }
        };
    }
}
