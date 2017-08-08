package com.MCWorld.image.base.cache.disk;

import com.MCWorld.framework.base.utils.VisibleForTesting;
import com.MCWorld.image.base.cache.disk.c.c;

/* compiled from: ScoreBasedEvictionComparatorSupplier */
public class i implements g {
    private final float uK;
    private final float uL;

    public i(float ageWeight, float sizeWeight) {
        this.uK = ageWeight;
        this.uL = sizeWeight;
    }

    public f gz() {
        return new f(this) {
            long uM = System.currentTimeMillis();
            final /* synthetic */ i uN;

            {
                this.uN = this$0;
            }

            public /* synthetic */ int compare(Object obj, Object obj2) {
                return a((c) obj, (c) obj2);
            }

            public int a(c lhs, c rhs) {
                float score1 = this.uN.a(lhs, this.uM);
                float score2 = this.uN.a(rhs, this.uM);
                if (score1 < score2) {
                    return 1;
                }
                return score2 == score1 ? 0 : -1;
            }
        };
    }

    @VisibleForTesting
    float a(c entry, long now) {
        return (this.uK * ((float) (now - entry.getTimestamp()))) + (this.uL * ((float) entry.getSize()));
    }
}
