package com.MCWorld.image.base.cache.disk;

import com.MCWorld.image.base.cache.disk.c.c;

/* compiled from: DefaultEntryEvictionComparatorSupplier */
public class a implements g {
    public f gz() {
        return new f(this) {
            final /* synthetic */ a tX;

            {
                this.tX = this$0;
            }

            public /* synthetic */ int compare(Object obj, Object obj2) {
                return a((c) obj, (c) obj2);
            }

            public int a(c e1, c e2) {
                long time1 = e1.getTimestamp();
                long time2 = e2.getTimestamp();
                if (time1 < time2) {
                    return -1;
                }
                return time2 == time1 ? 0 : 1;
            }
        };
    }
}
