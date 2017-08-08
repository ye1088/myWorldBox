package com.MCWorld.image.pipeline.core;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.v4.util.Pools.SynchronizedPool;
import com.MCWorld.framework.base.utils.AndroidPredicates;
import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.framework.base.utils.Suppliers;
import com.MCWorld.image.base.imagepipeline.bitmaps.a;
import com.MCWorld.image.base.imagepipeline.cache.d;
import com.MCWorld.image.base.imagepipeline.cache.e;
import com.MCWorld.image.base.imagepipeline.memory.PooledByteBuffer;
import com.MCWorld.image.pipeline.cache.c;
import com.MCWorld.image.pipeline.cache.i;
import com.MCWorld.image.pipeline.cache.j;
import com.MCWorld.image.pipeline.decoder.b;
import com.MCWorld.image.pipeline.memory.s;
import com.MCWorld.image.pipeline.producers.ad;
import com.MCWorld.image.pipeline.producers.ae;
import com.MCWorld.image.pipeline.producers.ai;
import com.MCWorld.image.pipeline.producers.aw;
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
    private e<com.MCWorld.image.base.cache.common.b, com.MCWorld.image.base.imagepipeline.image.b> Fj;
    private e<com.MCWorld.image.base.cache.common.b, PooledByteBuffer> Fk;
    private c Fl;
    private final aw Fm;
    private final f Gf;
    private d<com.MCWorld.image.base.cache.common.b, com.MCWorld.image.base.imagepipeline.image.b> Gg;
    private d<com.MCWorld.image.base.cache.common.b, PooledByteBuffer> Gh;
    private com.MCWorld.image.base.cache.disk.h Gi;
    private i Gj;
    private com.MCWorld.image.base.cache.disk.h Gk;
    private ad Gl;
    private com.MCWorld.image.pipeline.platform.e Gm;
    private com.MCWorld.image.base.imagepipeline.animated.factory.b Gn;

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

    public com.MCWorld.image.base.imagepipeline.animated.factory.b mA() {
        if (this.Gn == null) {
            this.Gn = com.MCWorld.image.base.imagepipeline.animated.factory.c.a(me(), this.Gf.lW());
        }
        return this.Gn;
    }

    public d<com.MCWorld.image.base.cache.common.b, com.MCWorld.image.base.imagepipeline.image.b> mB() {
        if (this.Gg == null) {
            this.Gg = com.MCWorld.image.base.imagepipeline.cache.a.a(this.Gf.lP(), this.Gf.mc(), me(), this.Gf.ml().mr());
        }
        return this.Gg;
    }

    public e<com.MCWorld.image.base.cache.common.b, com.MCWorld.image.base.imagepipeline.image.b> lM() {
        if (this.Fj == null) {
            this.Fj = com.MCWorld.image.pipeline.cache.a.a(mB(), this.Gf.lY());
        }
        return this.Fj;
    }

    @Deprecated
    public static com.MCWorld.image.base.cache.disk.d a(com.MCWorld.image.base.cache.disk.b diskCacheConfig, com.MCWorld.image.base.cache.disk.c diskStorage) {
        return a.a(diskCacheConfig, diskStorage);
    }

    public d<com.MCWorld.image.base.cache.common.b, PooledByteBuffer> mC() {
        if (this.Gh == null) {
            this.Gh = i.a(this.Gf.lV(), this.Gf.mc(), me());
        }
        return this.Gh;
    }

    public e<com.MCWorld.image.base.cache.common.b, PooledByteBuffer> mD() {
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
                com.MCWorld.image.base.imagepipeline.animated.factory.d animatedImageFactory;
                if (mA() != null) {
                    animatedImageFactory = mA().he();
                } else {
                    animatedImageFactory = null;
                }
                if (this.Gf.mk() == null) {
                    this.FA = new com.MCWorld.image.pipeline.decoder.a(animatedImageFactory, mH(), this.Gf.hy());
                } else {
                    this.FA = new com.MCWorld.image.pipeline.decoder.a(animatedImageFactory, mH(), this.Gf.hy(), this.Gf.mk().nB());
                    com.MCWorld.image.base.imageformat.e.hd().j(this.Gf.mk().nC());
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
    public com.MCWorld.image.base.cache.disk.h mF() {
        return mG();
    }

    public com.MCWorld.image.base.cache.disk.h mG() {
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

    public static a a(s poolFactory, com.MCWorld.image.pipeline.platform.e platformDecoder) {
        if (VERSION.SDK_INT >= 21) {
            return new com.MCWorld.image.pipeline.bitmaps.a(poolFactory.op());
        }
        if (VERSION.SDK_INT >= 11) {
            return new com.MCWorld.image.pipeline.bitmaps.e(new com.MCWorld.image.pipeline.bitmaps.b(poolFactory.ou()), platformDecoder);
        }
        return new com.MCWorld.image.pipeline.bitmaps.c();
    }

    public a me() {
        if (this.FE == null) {
            this.FE = a(this.Gf.mf(), mH());
        }
        return this.FE;
    }

    public static com.MCWorld.image.pipeline.platform.e a(s poolFactory, boolean directWebpDirectDecodingEnabled, com.MCWorld.image.pipeline.memory.c bitmapCounterTracker) {
        if (VERSION.SDK_INT >= 21) {
            int maxNumThreads = poolFactory.os();
            return new com.MCWorld.image.pipeline.platform.a(poolFactory.op(), maxNumThreads, new SynchronizedPool(maxNumThreads));
        } else if (!directWebpDirectDecodingEnabled || VERSION.SDK_INT >= 19) {
            return new com.MCWorld.image.pipeline.platform.d(poolFactory.oq(), bitmapCounterTracker);
        } else {
            return new com.MCWorld.image.pipeline.platform.c(bitmapCounterTracker);
        }
    }

    public com.MCWorld.image.pipeline.platform.e mH() {
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
    public com.MCWorld.image.base.cache.disk.h mK() {
        return mL();
    }

    public com.MCWorld.image.base.cache.disk.h mL() {
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
