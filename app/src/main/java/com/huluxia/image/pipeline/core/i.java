package com.huluxia.image.pipeline.core;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import com.huluxia.image.base.imagepipeline.bitmaps.a;
import com.huluxia.image.base.imagepipeline.cache.e;
import com.huluxia.image.base.imagepipeline.memory.PooledByteBuffer;
import com.huluxia.image.pipeline.cache.c;
import com.huluxia.image.pipeline.cache.d;
import com.huluxia.image.pipeline.cache.h;
import com.huluxia.image.pipeline.cache.p;
import com.huluxia.image.pipeline.cache.q;
import com.huluxia.image.pipeline.decoder.b;
import com.huluxia.image.pipeline.producers.aa;
import com.huluxia.image.pipeline.producers.ab;
import com.huluxia.image.pipeline.producers.ac;
import com.huluxia.image.pipeline.producers.ad;
import com.huluxia.image.pipeline.producers.ag;
import com.huluxia.image.pipeline.producers.ah;
import com.huluxia.image.pipeline.producers.aj;
import com.huluxia.image.pipeline.producers.ak;
import com.huluxia.image.pipeline.producers.al;
import com.huluxia.image.pipeline.producers.am;
import com.huluxia.image.pipeline.producers.as;
import com.huluxia.image.pipeline.producers.au;
import com.huluxia.image.pipeline.producers.av;
import com.huluxia.image.pipeline.producers.aw;
import com.huluxia.image.pipeline.producers.ax;
import com.huluxia.image.pipeline.producers.ay;
import com.huluxia.image.pipeline.producers.az;
import com.huluxia.image.pipeline.producers.bb;
import com.huluxia.image.pipeline.producers.f;
import com.huluxia.image.pipeline.producers.g;
import com.huluxia.image.pipeline.producers.k;
import com.huluxia.image.pipeline.producers.l;
import com.huluxia.image.pipeline.producers.n;
import com.huluxia.image.pipeline.producers.o;
import com.huluxia.image.pipeline.producers.r;
import com.huluxia.image.pipeline.producers.u;
import com.huluxia.image.pipeline.producers.v;
import com.huluxia.image.pipeline.producers.w;
import com.huluxia.image.pipeline.producers.x;
import com.huluxia.image.pipeline.producers.z;
import java.util.concurrent.Executor;

/* compiled from: ProducerFactory */
public class i {
    private final c EW;
    private final c EX;
    private final d EY;
    private final int EZ;
    private final com.huluxia.image.base.imagepipeline.memory.d Eo;
    private final b FA;
    private final a FE;
    private final com.huluxia.image.pipeline.decoder.d FG;
    private final boolean FI;
    private final int FS;
    private final boolean FT;
    private final boolean FY;
    private final e<com.huluxia.image.base.cache.common.b, com.huluxia.image.base.imagepipeline.image.b> Fj;
    private final e<com.huluxia.image.base.cache.common.b, PooledByteBuffer> Fk;
    private final boolean Fw;
    private final com.huluxia.image.base.imagepipeline.core.b Fz;
    private ad Gl;
    private AssetManager Go;
    private final h Gp;
    private ContentResolver mContentResolver;
    private final Context mContext;
    private Resources mResources;
    private final com.huluxia.image.base.imagepipeline.memory.a xl;

    public i(Context context, com.huluxia.image.base.imagepipeline.memory.a byteArrayPool, b imageDecoder, com.huluxia.image.pipeline.decoder.d progressiveJpegConfig, boolean downsampleEnabled, int enhancedWebpTranscodingType, boolean resizeAndRotateEnabledForNetwork, boolean decodeCancellationEnabled, com.huluxia.image.base.imagepipeline.core.b executorSupplier, com.huluxia.image.base.imagepipeline.memory.d pooledByteBufferFactory, e<com.huluxia.image.base.cache.common.b, com.huluxia.image.base.imagepipeline.image.b> bitmapMemoryCache, e<com.huluxia.image.base.cache.common.b, PooledByteBuffer> encodedMemoryCache, c defaultBufferedDiskCache, c smallImageBufferedDiskCache, ad mediaVariationsIndex, d cacheKeyFactory, a platformBitmapFactory, boolean decodeFileDescriptorEnabled, int forceSmallCacheThresholdBytes) {
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

    public static com.huluxia.image.pipeline.producers.a a(am<com.huluxia.image.base.imagepipeline.image.d> inputProducer) {
        return new com.huluxia.image.pipeline.producers.a(inputProducer);
    }

    public f b(am<com.huluxia.image.core.common.references.a<com.huluxia.image.base.imagepipeline.image.b>> inputProducer) {
        return new f(this.Fj, this.EY, inputProducer);
    }

    public g c(am<com.huluxia.image.core.common.references.a<com.huluxia.image.base.imagepipeline.image.b>> inputProducer) {
        return new g(this.EY, inputProducer);
    }

    public com.huluxia.image.pipeline.producers.h d(am<com.huluxia.image.core.common.references.a<com.huluxia.image.base.imagepipeline.image.b>> inputProducer) {
        return new com.huluxia.image.pipeline.producers.h(this.Fj, this.EY, inputProducer);
    }

    public static com.huluxia.image.pipeline.producers.i a(am<com.huluxia.image.base.imagepipeline.image.d> inputProducer1, am<com.huluxia.image.base.imagepipeline.image.d> inputProducer2) {
        return new com.huluxia.image.pipeline.producers.i(inputProducer1, inputProducer2);
    }

    public k mO() {
        return new k(this.Eo, this.FT);
    }

    public l e(am<com.huluxia.image.base.imagepipeline.image.d> inputProducer) {
        return new l(this.xl, this.Fz.hJ(), this.FA, this.FG, this.Fw, this.FI, this.FY, inputProducer);
    }

    public n f(am<com.huluxia.image.base.imagepipeline.image.d> inputProducer) {
        return new n(inputProducer, this.Gp);
    }

    public o g(am<com.huluxia.image.base.imagepipeline.image.d> inputProducer) {
        return new o(inputProducer, this.Gp);
    }

    public ac h(am<com.huluxia.image.base.imagepipeline.image.d> inputProducer) {
        return new ac(this.EW, this.EX, this.EY, this.Gl, inputProducer);
    }

    public com.huluxia.image.pipeline.producers.q i(am<com.huluxia.image.base.imagepipeline.image.d> inputProducer) {
        return new com.huluxia.image.pipeline.producers.q(this.EY, inputProducer);
    }

    public r j(am<com.huluxia.image.base.imagepipeline.image.d> inputProducer) {
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

    public ay a(az<com.huluxia.image.base.imagepipeline.image.d>[] thumbnailProducers) {
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

    public com.huluxia.image.pipeline.producers.ishare.a mW() {
        return new com.huluxia.image.pipeline.producers.ishare.a(this.Fz.hH());
    }

    public com.huluxia.image.pipeline.producers.ishare.b mX() {
        return new com.huluxia.image.pipeline.producers.ishare.b(this.Fz.hH(), this.Eo, this.mContentResolver, this.FT);
    }

    public com.huluxia.image.pipeline.producers.ishare.c mY() {
        return new com.huluxia.image.pipeline.producers.ishare.c(this.Fz.hH(), this.mContentResolver);
    }

    public ag b(ah networkFetcher) {
        return new ag(this.Eo, this.xl, networkFetcher);
    }

    public static <T> aj<T> mZ() {
        return new aj();
    }

    public ak k(am<com.huluxia.image.core.common.references.a<com.huluxia.image.base.imagepipeline.image.b>> inputProducer) {
        return new ak(this.Fj, this.EY, inputProducer);
    }

    public al l(am<com.huluxia.image.core.common.references.a<com.huluxia.image.base.imagepipeline.image.b>> inputProducer) {
        return new al(inputProducer, this.FE, this.Fz.hK());
    }

    public as a(am<com.huluxia.image.base.imagepipeline.image.d> inputProducer, boolean resizingEnabledIfNotDownsampling) {
        Executor hK = this.Fz.hK();
        com.huluxia.image.base.imagepipeline.memory.d dVar = this.Eo;
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

    public bb n(am<com.huluxia.image.base.imagepipeline.image.d> inputProducer) {
        return new bb(this.Fz.hK(), this.Eo, inputProducer, this.FS);
    }
}
