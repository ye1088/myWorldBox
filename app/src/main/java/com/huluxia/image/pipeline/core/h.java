package com.huluxia.image.pipeline.core;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.v4.util.Pools.SynchronizedPool;
import com.huluxia.framework.base.utils.AndroidPredicates;
import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.framework.base.utils.Suppliers;
import com.huluxia.image.base.imagepipeline.bitmaps.a;
import com.huluxia.image.base.imagepipeline.cache.d;
import com.huluxia.image.base.imagepipeline.cache.e;
import com.huluxia.image.base.imagepipeline.memory.PooledByteBuffer;
import com.huluxia.image.pipeline.cache.c;
import com.huluxia.image.pipeline.cache.i;
import com.huluxia.image.pipeline.cache.j;
import com.huluxia.image.pipeline.decoder.b;
import com.huluxia.image.pipeline.memory.s;
import com.huluxia.image.pipeline.producers.ad;
import com.huluxia.image.pipeline.producers.ae;
import com.huluxia.image.pipeline.producers.ai;
import com.huluxia.image.pipeline.producers.aw;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
/* compiled from: ImagePipelineFactory */
public class h {
    private static h Ge = null;
    private c EX;
    private e Eh;
    private b FA;
    private a FE;
    private j Fg;
    private e<com.huluxia.image.base.cache.common.b, com.huluxia.image.base.imagepipeline.image.b> Fj;
    private e<com.huluxia.image.base.cache.common.b, PooledByteBuffer> Fk;
    private c Fl;
    private final aw Fm;
    private final f Gf;
    private d<com.huluxia.image.base.cache.common.b, com.huluxia.image.base.imagepipeline.image.b> Gg;
    private d<com.huluxia.image.base.cache.common.b, PooledByteBuffer> Gh;
    private com.huluxia.image.base.cache.disk.h Gi;
    private i Gj;
    private com.huluxia.image.base.cache.disk.h Gk;
    private ad Gl;
    private com.huluxia.image.pipeline.platform.e Gm;
    private com.huluxia.image.base.imagepipeline.animated.factory.b Gn;

    public static h mz() {
        return (h) Preconditions.checkNotNull(Ge, "ImagePipelineFactory was not initialized!");
    }

    public static void aH(Context context) {
        a(f.aJ(context).mp());
    }

    public static void a(f imagePipelineConfig) {
        Ge = new h(imagePipelineConfig);
    }

    public static void shutDown() {
        if (Ge != null) {
            Ge.lM().c(AndroidPredicates.True());
            Ge.mD().c(AndroidPredicates.True());
            Ge = null;
        }
    }

    public h(f config) {
        this.Gf = (f) Preconditions.checkNotNull(config);
        this.Fm = new aw(config.lW().hL());
    }

    public com.huluxia.image.base.imagepipeline.animated.factory.b mA() {
        if (this.Gn == null) {
            this.Gn = com.huluxia.image.base.imagepipeline.animated.factory.c.a(me(), this.Gf.lW());
        }
        return this.Gn;
    }

    public d<com.huluxia.image.base.cache.common.b, com.huluxia.image.base.imagepipeline.image.b> mB() {
        if (this.Gg == null) {
            this.Gg = com.huluxia.image.base.imagepipeline.cache.a.a(this.Gf.lP(), this.Gf.mc(), me(), this.Gf.ml().mr());
        }
        return this.Gg;
    }

    public e<com.huluxia.image.base.cache.common.b, com.huluxia.image.base.imagepipeline.image.b> lM() {
        if (this.Fj == null) {
            this.Fj = com.huluxia.image.pipeline.cache.a.a(mB(), this.Gf.lY());
        }
        return this.Fj;
    }

    @Deprecated
    public static com.huluxia.image.base.cache.disk.d a(com.huluxia.image.base.cache.disk.b diskCacheConfig, com.huluxia.image.base.cache.disk.c diskStorage) {
        return a.a(diskCacheConfig, diskStorage);
    }

    public d<com.huluxia.image.base.cache.common.b, PooledByteBuffer> mC() {
        if (this.Gh == null) {
            this.Gh = i.a(this.Gf.lV(), this.Gf.mc(), me());
        }
        return this.Gh;
    }

    public e<com.huluxia.image.base.cache.common.b, PooledByteBuffer> mD() {
        if (this.Fk == null) {
            this.Fk = j.a(mC(), this.Gf.lY());
        }
        return this.Fk;
    }

    private b lZ() {
        if (this.FA == null) {
            if (this.Gf.lZ() != null) {
                this.FA = this.Gf.lZ();
            } else {
                com.huluxia.image.base.imagepipeline.animated.factory.d animatedImageFactory;
                if (mA() != null) {
                    animatedImageFactory = mA().he();
                } else {
                    animatedImageFactory = null;
                }
                if (this.Gf.mk() == null) {
                    this.FA = new com.huluxia.image.pipeline.decoder.a(animatedImageFactory, mH(), this.Gf.hy());
                } else {
                    this.FA = new com.huluxia.image.pipeline.decoder.a(animatedImageFactory, mH(), this.Gf.hy(), this.Gf.mk().nB());
                    com.huluxia.image.base.imageformat.e.hd().j(this.Gf.mk().nC());
                }
            }
        }
        return this.FA;
    }

    private c mE() {
        if (this.Fl == null) {
            this.Fl = new c(mG(), this.Gf.mf().ou(), this.Gf.mf().ov(), this.Gf.lW().hH(), this.Gf.lW().hI(), this.Gf.lY());
        }
        return this.Fl;
    }

    @Deprecated
    public com.huluxia.image.base.cache.disk.h mF() {
        return mG();
    }

    public com.huluxia.image.base.cache.disk.h mG() {
        if (this.Gi == null) {
            this.Gi = this.Gf.lS().a(this.Gf.mb());
        }
        return this.Gi;
    }

    public e lj() {
        if (this.Eh == null) {
            this.Eh = new e(mJ(), this.Gf.mh(), this.Gf.ma(), lM(), mD(), mE(), mM(), this.Gf.lN(), this.Fm, Suppliers.of(Boolean.valueOf(false)));
        }
        return this.Eh;
    }

    public static a a(s poolFactory, com.huluxia.image.pipeline.platform.e platformDecoder) {
        if (VERSION.SDK_INT >= 21) {
            return new com.huluxia.image.pipeline.bitmaps.a(poolFactory.op());
        }
        if (VERSION.SDK_INT >= 11) {
            return new com.huluxia.image.pipeline.bitmaps.e(new com.huluxia.image.pipeline.bitmaps.b(poolFactory.ou()), platformDecoder);
        }
        return new com.huluxia.image.pipeline.bitmaps.c();
    }

    public a me() {
        if (this.FE == null) {
            this.FE = a(this.Gf.mf(), mH());
        }
        return this.FE;
    }

    public static com.huluxia.image.pipeline.platform.e a(s poolFactory, boolean directWebpDirectDecodingEnabled, com.huluxia.image.pipeline.memory.c bitmapCounterTracker) {
        if (VERSION.SDK_INT >= 21) {
            int maxNumThreads = poolFactory.os();
            return new com.huluxia.image.pipeline.platform.a(poolFactory.op(), maxNumThreads, new SynchronizedPool(maxNumThreads));
        } else if (!directWebpDirectDecodingEnabled || VERSION.SDK_INT >= 19) {
            return new com.huluxia.image.pipeline.platform.d(poolFactory.oq(), bitmapCounterTracker);
        } else {
            return new com.huluxia.image.pipeline.platform.c(bitmapCounterTracker);
        }
    }

    public com.huluxia.image.pipeline.platform.e mH() {
        if (this.Gm == null) {
            this.Gm = a(this.Gf.mf(), this.Gf.ml().lU(), this.Gf.mm());
        }
        return this.Gm;
    }

    private i mI() {
        if (this.Gj == null) {
            this.Gj = new i(this.Gf.getContext(), this.Gf.mf().ox(), lZ(), this.Gf.mg(), this.Gf.lT(), this.Gf.ml().mu(), this.Gf.mi(), this.Gf.ml().mt(), this.Gf.lW(), this.Gf.mf().ou(), lM(), mD(), mE(), mM(), mN(), this.Gf.lN(), me(), this.Gf.ml().lR(), this.Gf.ml().lX());
        }
        return this.Gj;
    }

    private j mJ() {
        if (this.Fg == null) {
            this.Fg = new j(mI(), this.Gf.md(), this.Gf.mi(), this.Gf.ml().lU(), this.Fm, this.Gf.ml().mv());
        }
        return this.Fg;
    }

    @Deprecated
    public com.huluxia.image.base.cache.disk.h mK() {
        return mL();
    }

    public com.huluxia.image.base.cache.disk.h mL() {
        if (this.Gk == null) {
            this.Gk = this.Gf.lS().a(this.Gf.mj());
        }
        return this.Gk;
    }

    private c mM() {
        if (this.EX == null) {
            this.EX = new c(mL(), this.Gf.mf().ou(), this.Gf.mf().ov(), this.Gf.lW().hH(), this.Gf.lW().hI(), this.Gf.lY());
        }
        return this.EX;
    }

    public ad mN() {
        if (this.Gl == null) {
            this.Gl = this.Gf.ml().ms() ? new ae(this.Gf.getContext(), this.Gf.lW().hH(), this.Gf.lW().hI()) : new ai();
        }
        return this.Gl;
    }
}
