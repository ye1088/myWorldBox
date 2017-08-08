package com.MCWorld.image.pipeline.memory;

import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.image.core.common.memory.b;
import com.MCWorld.image.core.common.memory.e;
import javax.annotation.concurrent.Immutable;

@Immutable
/* compiled from: PoolConfig */
public class r {
    private final b FC;
    private final t Id;
    private final u Ie;
    private final t If;
    private final t Ig;
    private final u Ih;
    private final t Ii;
    private final u Ij;

    /* compiled from: PoolConfig */
    public static class a {
        private b FC;
        private t Id;
        private u Ie;
        private t If;
        private t Ig;
        private u Ih;
        private t Ii;
        private u Ij;

        private a() {
        }

        public a a(t bitmapPoolParams) {
            this.Id = (t) Preconditions.checkNotNull(bitmapPoolParams);
            return this;
        }

        public a a(u bitmapPoolStatsTracker) {
            this.Ie = (u) Preconditions.checkNotNull(bitmapPoolStatsTracker);
            return this;
        }

        public a b(t flexByteArrayPoolParams) {
            this.If = flexByteArrayPoolParams;
            return this;
        }

        public a b(b memoryTrimmableRegistry) {
            this.FC = memoryTrimmableRegistry;
            return this;
        }

        public a c(t nativeMemoryChunkPoolParams) {
            this.Ig = (t) Preconditions.checkNotNull(nativeMemoryChunkPoolParams);
            return this;
        }

        public a b(u nativeMemoryChunkPoolStatsTracker) {
            this.Ih = (u) Preconditions.checkNotNull(nativeMemoryChunkPoolStatsTracker);
            return this;
        }

        public a d(t commonByteArrayPoolParams) {
            this.Ii = (t) Preconditions.checkNotNull(commonByteArrayPoolParams);
            return this;
        }

        public a c(u smallByteArrayPoolStatsTracker) {
            this.Ij = (u) Preconditions.checkNotNull(smallByteArrayPoolStatsTracker);
            return this;
        }

        public r oo() {
            return new r();
        }
    }

    private r(a builder) {
        t nW;
        u oc;
        b it;
        if (builder.Id == null) {
            nW = f.nW();
        } else {
            nW = builder.Id;
        }
        this.Id = nW;
        if (builder.Ie == null) {
            oc = p.oc();
        } else {
            oc = builder.Ie;
        }
        this.Ie = oc;
        if (builder.If == null) {
            nW = h.nW();
        } else {
            nW = builder.If;
        }
        this.If = nW;
        if (builder.FC == null) {
            it = e.it();
        } else {
            it = builder.FC;
        }
        this.FC = it;
        if (builder.Ig == null) {
            nW = i.nW();
        } else {
            nW = builder.Ig;
        }
        this.Ig = nW;
        if (builder.Ih == null) {
            oc = p.oc();
        } else {
            oc = builder.Ih;
        }
        this.Ih = oc;
        if (builder.Ii == null) {
            nW = g.nW();
        } else {
            nW = builder.Ii;
        }
        this.Ii = nW;
        if (builder.Ij == null) {
            oc = p.oc();
        } else {
            oc = builder.Ij;
        }
        this.Ij = oc;
    }

    public t og() {
        return this.Id;
    }

    public u oh() {
        return this.Ie;
    }

    public b mc() {
        return this.FC;
    }

    public t oi() {
        return this.Ig;
    }

    public u oj() {
        return this.Ih;
    }

    public t ok() {
        return this.If;
    }

    public t ol() {
        return this.Ii;
    }

    public u om() {
        return this.Ij;
    }

    public static a on() {
        return new a();
    }
}
