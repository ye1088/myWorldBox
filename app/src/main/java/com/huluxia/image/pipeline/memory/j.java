package com.huluxia.image.pipeline.memory;

import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.framework.base.utils.VisibleForTesting;
import com.huluxia.image.core.common.memory.b;
import com.huluxia.image.core.common.references.c;
import java.util.Map;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
/* compiled from: FlexByteArrayPool */
public class j {
    @VisibleForTesting
    final a HU;
    private final c<byte[]> xe;

    @VisibleForTesting
    /* compiled from: FlexByteArrayPool */
    static class a extends k {
        public a(b memoryTrimmableRegistry, t poolParams, u poolStatsTracker) {
            super(memoryTrimmableRegistry, poolParams, poolStatsTracker);
        }

        e<byte[]> cv(int bucketedSize) {
            return new q(ct(bucketedSize), this.Hm.Iu, 0);
        }
    }

    public j(b memoryTrimmableRegistry, t params) {
        Preconditions.checkArgument(params.Iu > 0);
        this.HU = new a(memoryTrimmableRegistry, params, p.oc());
        this.xe = new c<byte[]>(this) {
            final /* synthetic */ j HV;

            {
                this.HV = this$0;
            }

            public /* synthetic */ void release(Object obj) {
                s((byte[]) obj);
            }

            public void s(byte[] unused) {
                this.HV.s(unused);
            }
        };
    }

    public com.huluxia.image.core.common.references.a<byte[]> cA(int size) {
        return com.huluxia.image.core.common.references.a.a(this.HU.get(size), this.xe);
    }

    public void s(byte[] value) {
        this.HU.release(value);
    }

    public Map<String, Integer> nO() {
        return this.HU.nO();
    }

    public int nY() {
        return this.HU.nY();
    }
}
