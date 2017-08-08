package com.MCWorld.image.pipeline.core;

import android.net.Uri;
import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.framework.base.utils.VisibleForTesting;
import com.MCWorld.image.base.imagepipeline.image.b;
import com.MCWorld.image.base.imagepipeline.image.d;
import com.MCWorld.image.base.imagepipeline.memory.PooledByteBuffer;
import com.MCWorld.image.core.common.references.a;
import com.MCWorld.image.core.common.util.f;
import com.MCWorld.image.core.common.webp.c;
import com.MCWorld.image.pipeline.producers.ah;
import com.MCWorld.image.pipeline.producers.am;
import com.MCWorld.image.pipeline.producers.ar;
import com.MCWorld.image.pipeline.producers.aw;
import com.MCWorld.image.pipeline.producers.az;
import com.MCWorld.image.pipeline.request.ImageRequest;
import com.MCWorld.image.pipeline.request.ImageRequest$RequestLevel;
import java.util.HashMap;
import java.util.Map;

/* compiled from: ProducerSequenceFactory */
public class j {
    private final ah FD;
    private final boolean FI;
    private final boolean FR;
    private final int FU;
    private final aw Fm;
    @VisibleForTesting
    am<a<b>> GA;
    @VisibleForTesting
    am<a<b>> GB;
    @VisibleForTesting
    am<a<b>> GC;
    @VisibleForTesting
    am<a<b>> GD;
    @VisibleForTesting
    Map<am<a<b>>, am<a<b>>> GF = new HashMap();
    @VisibleForTesting
    Map<am<a<b>>, am<Void>> GG = new HashMap();
    @VisibleForTesting
    am<a<b>> GH;
    @VisibleForTesting
    am<a<b>> GI;
    @VisibleForTesting
    am<a<b>> GJ;
    private final i Gj;
    @VisibleForTesting
    am<a<b>> Gq;
    @VisibleForTesting
    am<d> Gr;
    @VisibleForTesting
    am<d> Gs;
    @VisibleForTesting
    am<a<PooledByteBuffer>> Gt;
    @VisibleForTesting
    am<a<PooledByteBuffer>> Gu;
    @VisibleForTesting
    am<Void> Gv;
    @VisibleForTesting
    am<Void> Gw;
    private am<d> Gx;
    @VisibleForTesting
    am<a<b>> Gy;
    @VisibleForTesting
    am<a<b>> Gz;

    public j(i producerFactory, ah networkFetcher, boolean resizeAndRotateEnabledForNetwork, boolean webpSupportEnabled, aw threadHandoffProducerQueue, int throttlingMaxSimultaneousRequests) {
        this.Gj = producerFactory;
        this.FD = networkFetcher;
        this.FI = resizeAndRotateEnabledForNetwork;
        this.FR = webpSupportEnabled;
        this.Fm = threadHandoffProducerQueue;
        this.FU = throttlingMaxSimultaneousRequests;
    }

    public am<a<PooledByteBuffer>> f(ImageRequest imageRequest) {
        h(imageRequest);
        Uri uri = imageRequest.pv();
        if (f.isNetworkUri(uri)) {
            return na();
        }
        if (f.isLocalFileUri(uri)) {
            return nb();
        }
        throw new IllegalArgumentException("Unsupported uri scheme for encoded image fetch! Uri is: " + s(uri));
    }

    public am<a<PooledByteBuffer>> na() {
        synchronized (this) {
            if (this.Gu == null) {
                this.Gu = new ar(nd());
            }
        }
        return this.Gu;
    }

    public am<a<PooledByteBuffer>> nb() {
        synchronized (this) {
            if (this.Gt == null) {
                this.Gt = new ar(nh());
            }
        }
        return this.Gt;
    }

    public am<Void> g(ImageRequest imageRequest) {
        h(imageRequest);
        Uri uri = imageRequest.pv();
        if (f.isNetworkUri(uri)) {
            return ne();
        }
        if (f.isLocalFileUri(uri)) {
            return ng();
        }
        throw new IllegalArgumentException("Unsupported uri scheme for encoded image fetch! Uri is: " + s(uri));
    }

    private static void h(ImageRequest imageRequest) {
        Preconditions.checkNotNull(imageRequest);
        Preconditions.checkArgument(imageRequest.oC().getValue() <= ImageRequest$RequestLevel.ENCODED_MEMORY_CACHE.getValue());
    }

    public am<a<b>> i(ImageRequest imageRequest) {
        am<a<b>> pipelineSequence = k(imageRequest);
        if (imageRequest.pH() != null) {
            return t(pipelineSequence);
        }
        return pipelineSequence;
    }

    public am<Void> j(ImageRequest imageRequest) {
        return u(k(imageRequest));
    }

    private am<a<b>> k(ImageRequest imageRequest) {
        Preconditions.checkNotNull(imageRequest);
        Uri uri = imageRequest.pv();
        Preconditions.checkNotNull(uri, "Uri is null.");
        if (f.isNetworkUri(uri)) {
            return nc();
        }
        if (f.isLocalFileUri(uri)) {
            if (com.MCWorld.image.core.common.media.a.isVideo(com.MCWorld.image.core.common.media.a.bm(uri.getPath()))) {
                return nj();
            }
            return ni();
        } else if (f.isLocalContentUri(uri)) {
            return nk();
        } else {
            if (f.isLocalAssetUri(uri)) {
                return nm();
            }
            if (f.isLocalResourceUri(uri)) {
                return nl();
            }
            if (f.isDataUri(uri)) {
                return nq();
            }
            if (f.f(uri)) {
                return nn();
            }
            if (!f.g(uri)) {
                throw new IllegalArgumentException("Unsupported uri scheme! Uri is: " + s(uri));
            } else if (f.h(uri)) {
                return np();
            } else {
                return no();
            }
        }
    }

    private synchronized am<a<b>> nc() {
        if (this.Gq == null) {
            this.Gq = p(nf());
        }
        return this.Gq;
    }

    private synchronized am<d> nd() {
        if (this.Gs == null) {
            this.Gs = this.Gj.a(nf(), this.Fm);
        }
        return this.Gs;
    }

    private synchronized am<Void> ne() {
        if (this.Gw == null) {
            this.Gw = i.m(nd());
        }
        return this.Gw;
    }

    private synchronized am<d> nf() {
        if (this.Gx == null) {
            this.Gx = i.a(q(this.Gj.b(this.FD)));
            this.Gx = this.Gj.a(this.Gx, this.FI);
        }
        return this.Gx;
    }

    private synchronized am<Void> ng() {
        if (this.Gv == null) {
            this.Gv = i.m(nd());
        }
        return this.Gv;
    }

    private synchronized am<d> nh() {
        if (this.Gr == null) {
            this.Gr = this.Gj.a(q(this.Gj.mT()), this.Fm);
        }
        return this.Gr;
    }

    private synchronized am<a<b>> ni() {
        if (this.Gy == null) {
            this.Gy = o(this.Gj.mT());
        }
        return this.Gy;
    }

    private synchronized am<a<b>> nj() {
        if (this.Gz == null) {
            this.Gz = s(this.Gj.mV());
        }
        return this.Gz;
    }

    private synchronized am<a<b>> nk() {
        if (this.GA == null) {
            this.GA = a(this.Gj.mQ(), new az[]{this.Gj.mR(), this.Gj.mS()});
        }
        return this.GA;
    }

    private synchronized am<a<b>> nl() {
        if (this.GB == null) {
            this.GB = o(this.Gj.mU());
        }
        return this.GB;
    }

    private synchronized am<a<b>> nm() {
        if (this.GC == null) {
            this.GC = o(this.Gj.mP());
        }
        return this.GC;
    }

    private synchronized am<a<b>> nn() {
        if (this.GH == null) {
            this.GH = s(this.Gj.mW());
        }
        return this.GH;
    }

    private synchronized am<a<b>> no() {
        if (this.GI == null) {
            am inputProducer = this.Gj.mX();
            i iVar = this.Gj;
            this.GI = p(this.Gj.a(i.a(inputProducer), true));
        }
        return this.GI;
    }

    private synchronized am<a<b>> np() {
        if (this.GJ == null) {
            this.GJ = s(this.Gj.mY());
        }
        return this.GJ;
    }

    private synchronized am<a<b>> nq() {
        if (this.GD == null) {
            am inputProducer = this.Gj.mO();
            if (c.za && (!this.FR || c.zd == null)) {
                inputProducer = this.Gj.n(inputProducer);
            }
            i iVar = this.Gj;
            this.GD = p(this.Gj.a(i.a(inputProducer), true));
        }
        return this.GD;
    }

    private am<a<b>> o(am<d> inputProducer) {
        return a(inputProducer, new az[]{this.Gj.mS()});
    }

    private am<a<b>> a(am<d> inputProducer, az<d>[] thumbnailProducers) {
        return p(b(q(inputProducer), thumbnailProducers));
    }

    private am<a<b>> p(am<d> inputProducer) {
        return s(this.Gj.e(inputProducer));
    }

    private am<d> q(am<d> inputProducer) {
        if (c.za && (!this.FR || c.zd == null)) {
            inputProducer = this.Gj.n(inputProducer);
        }
        return this.Gj.i(this.Gj.j(r(inputProducer)));
    }

    private am<d> r(am<d> inputProducer) {
        return this.Gj.f(this.Gj.h(this.Gj.g(inputProducer)));
    }

    private am<a<b>> s(am<a<b>> inputProducer) {
        return this.Gj.b(this.Gj.a(this.Gj.c(this.Gj.d(inputProducer)), this.Fm));
    }

    private am<d> b(am<d> inputProducer, az<d>[] thumbnailProducers) {
        am localImageThrottlingProducer = this.Gj.a(this.FU, this.Gj.a(i.a((am) inputProducer), true));
        i iVar = this.Gj;
        return i.a(b(thumbnailProducers), localImageThrottlingProducer);
    }

    private am<d> b(az<d>[] thumbnailProducers) {
        return this.Gj.a(this.Gj.a((az[]) thumbnailProducers), true);
    }

    private synchronized am<a<b>> t(am<a<b>> inputProducer) {
        if (!this.GF.containsKey(inputProducer)) {
            this.GF.put(inputProducer, this.Gj.k(this.Gj.l(inputProducer)));
        }
        return (am) this.GF.get(inputProducer);
    }

    private synchronized am<Void> u(am<a<b>> inputProducer) {
        if (!this.GG.containsKey(inputProducer)) {
            i iVar = this.Gj;
            this.GG.put(inputProducer, i.m(inputProducer));
        }
        return (am) this.GG.get(inputProducer);
    }

    private static String s(Uri uri) {
        String uriString = String.valueOf(uri);
        return uriString.length() > 30 ? uriString.substring(0, 30) + "..." : uriString;
    }
}
