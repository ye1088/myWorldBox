package com.huluxia.image.pipeline.core;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap.Config;
import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.framework.base.utils.Supplier;
import com.huluxia.framework.base.utils.VisibleForTesting;
import com.huluxia.image.pipeline.cache.d;
import com.huluxia.image.pipeline.cache.e;
import com.huluxia.image.pipeline.cache.g;
import com.huluxia.image.pipeline.cache.k;
import com.huluxia.image.pipeline.cache.o;
import com.huluxia.image.pipeline.decoder.b;
import com.huluxia.image.pipeline.listener.c;
import com.huluxia.image.pipeline.memory.r;
import com.huluxia.image.pipeline.memory.s;
import com.huluxia.image.pipeline.producers.ah;
import com.huluxia.image.pipeline.producers.t;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nullable;

/* compiled from: ImagePipelineConfig */
public class f {
    private static b FN = new b(null);
    private final k EH;
    private final d EY;
    @Nullable
    private final b FA;
    private final com.huluxia.image.base.cache.disk.b FB;
    private final com.huluxia.image.core.common.memory.b FC;
    private final ah FD;
    @Nullable
    private final com.huluxia.image.base.imagepipeline.bitmaps.a FE;
    private final s FF;
    private final com.huluxia.image.pipeline.decoder.d FG;
    private final Set<c> FH;
    private final boolean FI;
    private final com.huluxia.image.base.cache.disk.b FJ;
    @Nullable
    private final com.huluxia.image.pipeline.decoder.c FK;
    private final g FL;
    private final Supplier<com.huluxia.image.pipeline.memory.c> FM;
    private final Supplier<Boolean> Fi;
    @Nullable
    private final com.huluxia.image.base.imagepipeline.animated.factory.d Fu;
    private final Supplier<com.huluxia.image.base.imagepipeline.cache.f> Fv;
    private final boolean Fw;
    private final d Fx;
    private final Supplier<com.huluxia.image.base.imagepipeline.cache.f> Fy;
    private final com.huluxia.image.base.imagepipeline.core.b Fz;
    private final Context mContext;
    private final Config ws;

    /* compiled from: ImagePipelineConfig */
    public static class a {
        private k EH;
        private d EY;
        private b FA;
        private com.huluxia.image.base.cache.disk.b FB;
        private com.huluxia.image.core.common.memory.b FC;
        private ah FD;
        private com.huluxia.image.base.imagepipeline.bitmaps.a FE;
        private s FF;
        private com.huluxia.image.pipeline.decoder.d FG;
        private Set<c> FH;
        private boolean FI;
        private com.huluxia.image.base.cache.disk.b FJ;
        private com.huluxia.image.pipeline.decoder.c FK;
        public Supplier<com.huluxia.image.pipeline.memory.c> FM;
        private final com.huluxia.image.pipeline.core.g.a FP;
        private Supplier<Boolean> Fi;
        private com.huluxia.image.base.imagepipeline.animated.factory.d Fu;
        private Supplier<com.huluxia.image.base.imagepipeline.cache.f> Fv;
        private boolean Fw;
        private d Fx;
        private Supplier<com.huluxia.image.base.imagepipeline.cache.f> Fy;
        private com.huluxia.image.base.imagepipeline.core.b Fz;
        private final Context mContext;
        private Config ws;

        private a(Context context) {
            this.Fw = false;
            this.FI = true;
            this.FP = new com.huluxia.image.pipeline.core.g.a(this);
            this.mContext = (Context) Preconditions.checkNotNull(context);
        }

        public a a(com.huluxia.image.base.imagepipeline.animated.factory.d animatedImageFactory) {
            this.Fu = animatedImageFactory;
            return this;
        }

        public a c(Config config) {
            this.ws = config;
            return this;
        }

        public a d(Supplier<com.huluxia.image.base.imagepipeline.cache.f> bitmapMemoryCacheParamsSupplier) {
            this.Fv = (Supplier) Preconditions.checkNotNull(bitmapMemoryCacheParamsSupplier);
            return this;
        }

        public a a(d cacheKeyFactory) {
            this.EY = cacheKeyFactory;
            return this;
        }

        public a a(d fileCacheFactory) {
            this.Fx = fileCacheFactory;
            return this;
        }

        @Deprecated
        public a a(b diskStorageFactory) {
            a(new a(diskStorageFactory));
            return this;
        }

        public boolean lT() {
            return this.Fw;
        }

        public a ag(boolean downsampleEnabled) {
            this.Fw = downsampleEnabled;
            return this;
        }

        public a e(Supplier<com.huluxia.image.base.imagepipeline.cache.f> encodedMemoryCacheParamsSupplier) {
            this.Fy = (Supplier) Preconditions.checkNotNull(encodedMemoryCacheParamsSupplier);
            return this;
        }

        public a a(com.huluxia.image.base.imagepipeline.core.b executorSupplier) {
            this.Fz = executorSupplier;
            return this;
        }

        public a a(k imageCacheStatsTracker) {
            this.EH = imageCacheStatsTracker;
            return this;
        }

        public a a(b imageDecoder) {
            this.FA = imageDecoder;
            return this;
        }

        public a f(Supplier<Boolean> isPrefetchEnabledSupplier) {
            this.Fi = isPrefetchEnabledSupplier;
            return this;
        }

        public a c(com.huluxia.image.base.cache.disk.b mainDiskCacheConfig) {
            this.FB = mainDiskCacheConfig;
            return this;
        }

        public a a(com.huluxia.image.core.common.memory.b memoryTrimmableRegistry) {
            this.FC = memoryTrimmableRegistry;
            return this;
        }

        public a a(ah networkFetcher) {
            this.FD = networkFetcher;
            return this;
        }

        public a a(com.huluxia.image.base.imagepipeline.bitmaps.a platformBitmapFactory) {
            this.FE = platformBitmapFactory;
            return this;
        }

        public a a(s poolFactory) {
            this.FF = poolFactory;
            return this;
        }

        public a a(com.huluxia.image.pipeline.decoder.d progressiveJpegConfig) {
            this.FG = progressiveJpegConfig;
            return this;
        }

        public a c(Set<c> requestListeners) {
            this.FH = requestListeners;
            return this;
        }

        public a ah(boolean resizeAndRotateEnabledForNetwork) {
            this.FI = resizeAndRotateEnabledForNetwork;
            return this;
        }

        public a d(com.huluxia.image.base.cache.disk.b smallImageDiskCacheConfig) {
            this.FJ = smallImageDiskCacheConfig;
            return this;
        }

        public a a(com.huluxia.image.pipeline.decoder.c imageDecoderConfig) {
            this.FK = imageDecoderConfig;
            return this;
        }

        public a g(Supplier<com.huluxia.image.pipeline.memory.c> bitmapCounterTracker) {
            this.FM = bitmapCounterTracker;
            return this;
        }

        public com.huluxia.image.pipeline.core.g.a mo() {
            return this.FP;
        }

        public f mp() {
            return new f();
        }
    }

    private f(a builder) {
        Supplier eVar;
        Config config;
        d lx;
        d aVar;
        k lG;
        com.huluxia.image.base.cache.disk.b aI;
        com.huluxia.image.core.common.memory.b it;
        ah tVar;
        s sVar;
        com.huluxia.image.pipeline.decoder.d fVar;
        Set hashSet;
        com.huluxia.image.base.imagepipeline.core.b aVar2;
        this.FL = builder.FP.my();
        this.Fu = builder.Fu;
        if (builder.Fv == null) {
            eVar = new e((ActivityManager) builder.mContext.getSystemService("activity"));
        } else {
            eVar = builder.Fv;
        }
        this.Fv = eVar;
        if (builder.ws == null) {
            config = Config.ARGB_8888;
        } else {
            config = builder.ws;
        }
        this.ws = config;
        if (builder.EY == null) {
            lx = com.huluxia.image.pipeline.cache.f.lx();
        } else {
            lx = builder.EY;
        }
        this.EY = lx;
        this.mContext = (Context) Preconditions.checkNotNull(builder.mContext);
        if (builder.Fx == null) {
            aVar = new a(new c());
        } else {
            aVar = builder.Fx;
        }
        this.Fx = aVar;
        this.Fw = builder.Fw;
        if (builder.Fy == null) {
            eVar = new g();
        } else {
            eVar = builder.Fy;
        }
        this.Fy = eVar;
        if (builder.EH == null) {
            lG = o.lG();
        } else {
            lG = builder.EH;
        }
        this.EH = lG;
        this.FA = builder.FA;
        if (builder.Fi == null) {
            eVar = new 1(this);
        } else {
            eVar = builder.Fi;
        }
        this.Fi = eVar;
        if (builder.FB == null) {
            aI = aI(builder.mContext);
        } else {
            aI = builder.FB;
        }
        this.FB = aI;
        if (builder.FC == null) {
            it = com.huluxia.image.core.common.memory.e.it();
        } else {
            it = builder.FC;
        }
        this.FC = it;
        if (builder.FD == null) {
            tVar = new t();
        } else {
            tVar = builder.FD;
        }
        this.FD = tVar;
        this.FE = builder.FE;
        if (builder.FF == null) {
            sVar = new s(r.on().oo());
        } else {
            sVar = builder.FF;
        }
        this.FF = sVar;
        if (builder.FG == null) {
            fVar = new com.huluxia.image.pipeline.decoder.f();
        } else {
            fVar = builder.FG;
        }
        this.FG = fVar;
        if (builder.FH == null) {
            hashSet = new HashSet();
        } else {
            hashSet = builder.FH;
        }
        this.FH = hashSet;
        this.FI = builder.FI;
        if (builder.FJ == null) {
            aI = this.FB;
        } else {
            aI = builder.FJ;
        }
        this.FJ = aI;
        this.FK = builder.FK;
        int numCpuBoundThreads = this.FF.os();
        if (builder.Fz == null) {
            aVar2 = new com.huluxia.image.base.imagepipeline.core.a(numCpuBoundThreads);
        } else {
            aVar2 = builder.Fz;
        }
        this.Fz = aVar2;
        com.huluxia.image.core.common.webp.b webpBitmapFactory = this.FL.mx();
        if (webpBitmapFactory != null) {
            a(webpBitmapFactory, this.FL, new com.huluxia.image.pipeline.bitmaps.d(mf()));
        } else if (this.FL.lU() && com.huluxia.image.core.common.webp.c.za) {
            webpBitmapFactory = com.huluxia.image.core.common.webp.c.iL();
            if (webpBitmapFactory != null) {
                a(webpBitmapFactory, this.FL, new com.huluxia.image.pipeline.bitmaps.d(mf()));
            }
        }
        this.FM = builder.FM == null ? new 2(this) : builder.FM;
    }

    private static void a(com.huluxia.image.core.common.webp.b webpBitmapFactory, g imagePipelineExperiments, com.huluxia.image.core.common.webp.a bitmapCreator) {
        com.huluxia.image.core.common.webp.c.zd = webpBitmapFactory;
        com.huluxia.image.core.common.webp.b.a webpErrorLogger = imagePipelineExperiments.mw();
        if (webpErrorLogger != null) {
            webpBitmapFactory.a(webpErrorLogger);
        }
        if (bitmapCreator != null) {
            webpBitmapFactory.a(bitmapCreator);
        }
    }

    private static com.huluxia.image.base.cache.disk.b aI(Context context) {
        return com.huluxia.image.base.cache.disk.b.aD(context).gK();
    }

    @VisibleForTesting
    static void lO() {
        FN = new b(null);
    }

    @Nullable
    public com.huluxia.image.base.imagepipeline.animated.factory.d he() {
        return this.Fu;
    }

    public Config hy() {
        return this.ws;
    }

    public Supplier<com.huluxia.image.base.imagepipeline.cache.f> lP() {
        return this.Fv;
    }

    public d lN() {
        return this.EY;
    }

    public Context getContext() {
        return this.mContext;
    }

    public static b lQ() {
        return FN;
    }

    public boolean lR() {
        return this.FL.lR();
    }

    public d lS() {
        return this.Fx;
    }

    public boolean lT() {
        return this.Fw;
    }

    public boolean lU() {
        return this.FL.lU();
    }

    public Supplier<com.huluxia.image.base.imagepipeline.cache.f> lV() {
        return this.Fy;
    }

    public com.huluxia.image.base.imagepipeline.core.b lW() {
        return this.Fz;
    }

    @Deprecated
    public int lX() {
        return this.FL.lX();
    }

    public k lY() {
        return this.EH;
    }

    @Nullable
    public b lZ() {
        return this.FA;
    }

    public Supplier<Boolean> ma() {
        return this.Fi;
    }

    public com.huluxia.image.base.cache.disk.b mb() {
        return this.FB;
    }

    public com.huluxia.image.core.common.memory.b mc() {
        return this.FC;
    }

    public ah md() {
        return this.FD;
    }

    @Nullable
    public com.huluxia.image.base.imagepipeline.bitmaps.a me() {
        return this.FE;
    }

    public s mf() {
        return this.FF;
    }

    public com.huluxia.image.pipeline.decoder.d mg() {
        return this.FG;
    }

    public Set<c> mh() {
        return Collections.unmodifiableSet(this.FH);
    }

    public boolean mi() {
        return this.FI;
    }

    public com.huluxia.image.base.cache.disk.b mj() {
        return this.FJ;
    }

    @Nullable
    public com.huluxia.image.pipeline.decoder.c mk() {
        return this.FK;
    }

    public g ml() {
        return this.FL;
    }

    public static a aJ(Context context) {
        return new a(context);
    }

    public com.huluxia.image.pipeline.memory.c mm() {
        return (com.huluxia.image.pipeline.memory.c) this.FM.get();
    }
}
