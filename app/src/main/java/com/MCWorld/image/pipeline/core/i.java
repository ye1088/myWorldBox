package com.MCWorld.image.pipeline.core;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import com.MCWorld.image.base.imagepipeline.bitmaps.a;
import com.MCWorld.image.base.imagepipeline.cache.e;
import com.MCWorld.image.base.imagepipeline.memory.PooledByteBuffer;
import com.MCWorld.image.pipeline.cache.c;
import com.MCWorld.image.pipeline.cache.d;
import com.MCWorld.image.pipeline.cache.h;
import com.MCWorld.image.pipeline.cache.p;
import com.MCWorld.image.pipeline.cache.q;
import com.MCWorld.image.pipeline.decoder.b;
import com.MCWorld.image.pipeline.producers.aa;
import com.MCWorld.image.pipeline.producers.ab;
import com.MCWorld.image.pipeline.producers.ac;
import com.MCWorld.image.pipeline.producers.ad;
import com.MCWorld.image.pipeline.producers.ag;
import com.MCWorld.image.pipeline.producers.ah;
import com.MCWorld.image.pipeline.producers.aj;
import com.MCWorld.image.pipeline.producers.ak;
import com.MCWorld.image.pipeline.producers.al;
import com.MCWorld.image.pipeline.producers.am;
import com.MCWorld.image.pipeline.producers.as;
import com.MCWorld.image.pipeline.producers.au;
import com.MCWorld.image.pipeline.producers.av;
import com.MCWorld.image.pipeline.producers.aw;
import com.MCWorld.image.pipeline.producers.ax;
import com.MCWorld.image.pipeline.producers.ay;
import com.MCWorld.image.pipeline.producers.az;
import com.MCWorld.image.pipeline.producers.bb;
import com.MCWorld.image.pipeline.producers.f;
import com.MCWorld.image.pipeline.producers.g;
import com.MCWorld.image.pipeline.producers.k;
import com.MCWorld.image.pipeline.producers.l;
import com.MCWorld.image.pipeline.producers.n;
import com.MCWorld.image.pipeline.producers.o;
import com.MCWorld.image.pipeline.producers.r;
import com.MCWorld.image.pipeline.producers.u;
import com.MCWorld.image.pipeline.producers.v;
import com.MCWorld.image.pipeline.producers.w;
import com.MCWorld.image.pipeline.producers.x;
import com.MCWorld.image.pipeline.producers.z;
import java.util.concurrent.Executor;

/* compiled from: ProducerFactory */
public class i {
    private final c EW;
    private final c EX;
    private final d EY;
    private final int EZ;
    private final com.MCWorld.image.base.imagepipeline.memory.d Eo;
    private final b FA;
    private final a FE;
    private final com.MCWorld.image.pipeline.decoder.d FG;
    private final boolean FI;
    private final int FS;
    private final boolean FT;
    private final boolean FY;
    private final e<com.MCWorld.image.base.cache.common.b, com.MCWorld.image.base.imagepipeline.image.b> Fj;
    private final e<com.MCWorld.image.base.cache.common.b, PooledByteBuffer> Fk;
    private final boolean Fw;
    private final com.MCWorld.image.base.imagepipeline.core.b Fz;
    private ad Gl;
    private AssetManager Go;
    private final h Gp;
    private ContentResolver mContentResolver;
    private final Context mContext;
    private Resources mResources;
    private final com.MCWorld.image.base.imagepipeline.memory.a xl;

    public i(Context context, com.MCWorld.image.base.imagepipeline.memory.a byteArrayPool, b imageDecoder, com.MCWorld.image.pipeline.decoder.d progressiveJpegConfig, boolean downsampleEnabled, int enhancedWebpTranscodingType, boolean resizeAndRotateEnabledForNetwork, boolean decodeCancellationEnabled, com.MCWorld.image.base.imagepipeline.core.b executorSupplier, com.MCWorld.image.base.imagepipeline.memory.d pooledByteBufferFactory, e<com.MCWorld.image.base.cache.common.b, com.MCWorld.image.base.imagepipeline.image.b> bitmapMemoryCache, e<com.MCWorld.image.base.cache.common.b, PooledByteBuffer> encodedMemoryCache, c defaultBufferedDiskCache, c smallImageBufferedDiskCache, ad mediaVariationsIndex, d cacheKeyFactory, a platformBitmapFactory, boolean decodeFileDescriptorEnabled, int forceSmallCacheThresholdBytes) {
        this.mContext = context;
        this.EZ = forceSmallCacheThresholdBytes;
        this.mContentResolver = context.getApplicationContext().getContentResolver();
        this.mResources = context.getApplicationContext().getResources();
        this.Go = context.getApplicationContext().getAssets();
        this.xl = byteArrayPool;
        this.FA = imageDecoder;
        this.FG = progressiveJpegConfig;
        this.Fw = downsampleEnabled;
        this.FS = enhancedWebpTranscodingType;
        this.FI = resizeAndRotateEnabledForNetwork;
        this.FY = decodeCancellationEnabled;
        this.Fz = executorSupplier;
        this.Eo = pooledByteBufferFactory;
        this.Fj = bitmapMemoryCache;
        this.Fk = encodedMemoryCache;
        this.EW = defaultBufferedDiskCache;
        this.EX = smallImageBufferedDiskCache;
        this.Gl = mediaVariationsIndex;
        this.EY = cacheKeyFactory;
        this.FE = platformBitmapFactory;
        this.FT = decodeFileDescriptorEnabled;
        if (forceSmallCacheThresholdBytes > 0) {
            this.Gp = new q(defaultBufferedDiskCache, smallImageBufferedDiskCache, cacheKeyFactory, forceSmallCacheThresholdBytes);
        } else {
            this.Gp = new p(defaultBufferedDiskCache, smallImageBufferedDiskCache, cacheKeyFactory);
        }
    }

    public static com.MCWorld.image.pipeline.producers.a a(am<com.MCWorld.image.base.imagepipeline.image.d> inputProducer) {
        return new com.MCWorld.image.pipeline.producers.a(inputProducer);
    }

    public f b(am<com.MCWorld.image.core.common.references.a<com.MCWorld.image.base.imagepipeline.image.b>> inputProducer) {
        return new f(this.Fj, this.EY, inputProducer);
    }

    public g c(am<com.MCWorld.image.core.common.references.a<com.MCWorld.image.base.imagepipeline.image.b>> inputProducer) {
        return new g(this.EY, inputProducer);
    }

    public com.MCWorld.image.pipeline.producers.h d(am<com.MCWorld.image.core.common.references.a<com.MCWorld.image.base.imagepipeline.image.b>> inputProducer) {
        return new com.MCWorld.image.pipeline.producers.h(this.Fj, this.EY, inputProducer);
    }

    public static com.MCWorld.image.pipeline.producers.i a(am<com.MCWorld.image.base.imagepipeline.image.d> inputProducer1, am<com.MCWorld.image.base.imagepipeline.image.d> inputProducer2) {
        return new com.MCWorld.image.pipeline.producers.i(inputProducer1, inputProducer2);
    }

    public k mO() {
        return new k(this.Eo, this.FT);
    }

    public l e(am<com.MCWorld.image.base.imagepipeline.image.d> inputProducer) {
        return new l(this.xl, this.Fz.hJ(), this.FA, this.FG, this.Fw, this.FI, this.FY, inputProducer);
    }

    public n f(am<com.MCWorld.image.base.imagepipeline.image.d> inputProducer) {
        return new n(inputProducer, this.Gp);
    }

    public o g(am<com.MCWorld.image.base.imagepipeline.image.d> inputProducer) {
        return new o(inputProducer, this.Gp);
    }

    public ac h(am<com.MCWorld.image.base.imagepipeline.image.d> inputProducer) {
        return new ac(this.EW, this.EX, this.EY, this.Gl, inputProducer);
    }

    public com.MCWorld.image.pipeline.producers.q i(am<com.MCWorld.image.base.imagepipeline.image.d> inputProducer) {
        return new com.MCWorld.image.pipeline.producers.q(this.EY, inputProducer);
    }

    public r j(am<com.MCWorld.image.base.imagepipeline.image.d> inputProducer) {
        return new r(this.Fk, this.EY, inputProducer);
    }

    public u mP() {
        return new u(this.Fz.hH(), this.Eo, this.Go, this.FT);
    }

    public v mQ() {
        return new v(this.Fz.hH(), this.Eo, this.mContentResolver, this.FT);
    }

    public w mR() {
        return new w(this.Fz.hH(), this.Eo, this.mContentResolver, this.FT);
    }

    public x mS() {
        return new x(this.Fz.hH(), this.Eo, this.mContentResolver);
    }

    public ay a(az<com.MCWorld.image.base.imagepipeline.image.d>[] thumbnailProducers) {
        return new ay(thumbnailProducers);
    }

    public z mT() {
        return new z(this.Fz.hH(), this.Eo, this.FT);
    }

    public aa mU() {
        return new aa(this.Fz.hH(), this.Eo, this.mResources, this.FT);
    }

    public ab mV() {
        return new ab(this.Fz.hH());
    }

    public com.MCWorld.image.pipeline.producers.ishare.a mW() {
        return new com.MCWorld.image.pipeline.producers.ishare.a(this.Fz.hH());
    }

    public com.MCWorld.image.pipeline.producers.ishare.b mX() {
        return new com.MCWorld.image.pipeline.producers.ishare.b(this.Fz.hH(), this.Eo, this.mContentResolver, this.FT);
    }

    public com.MCWorld.image.pipeline.producers.ishare.c mY() {
        return new com.MCWorld.image.pipeline.producers.ishare.c(this.Fz.hH(), this.mContentResolver);
    }

    public ag b(ah networkFetcher) {
        return new ag(this.Eo, this.xl, networkFetcher);
    }

    public static <T> aj<T> mZ() {
        return new aj();
    }

    public ak k(am<com.MCWorld.image.core.common.references.a<com.MCWorld.image.base.imagepipeline.image.b>> inputProducer) {
        return new ak(this.Fj, this.EY, inputProducer);
    }

    public al l(am<com.MCWorld.image.core.common.references.a<com.MCWorld.image.base.imagepipeline.image.b>> inputProducer) {
        return new al(inputProducer, this.FE, this.Fz.hK());
    }

    public as a(am<com.MCWorld.image.base.imagepipeline.image.d> inputProducer, boolean resizingEnabledIfNotDownsampling) {
        Executor hK = this.Fz.hK();
        com.MCWorld.image.base.imagepipeline.memory.d dVar = this.Eo;
        boolean z = resizingEnabledIfNotDownsampling && !this.Fw;
        return new as(hK, dVar, z, inputProducer);
    }

    public static <T> au<T> m(am<T> inputProducer) {
        return new au(inputProducer);
    }

    public <T> av<T> a(am<T> inputProducer, aw inputThreadHandoffProducerQueue) {
        return new av(inputProducer, inputThreadHandoffProducerQueue);
    }

    public <T> ax<T> a(int maxSimultaneousRequests, am<T> inputProducer) {
        return new ax(maxSimultaneousRequests, this.Fz.hL(), inputProducer);
    }

    public bb n(am<com.MCWorld.image.base.imagepipeline.image.d> inputProducer) {
        return new bb(this.Fz.hK(), this.Eo, inputProducer, this.FS);
    }
}
