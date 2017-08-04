package com.huluxia.image.pipeline.producers;

import android.graphics.Bitmap;
import com.huluxia.framework.base.utils.ImmutableMap;
import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.image.base.imagepipeline.image.g;
import com.huluxia.image.core.common.util.f;
import com.huluxia.image.pipeline.decoder.d;
import com.huluxia.image.pipeline.decoder.e;
import com.huluxia.image.pipeline.request.ImageRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: DecodeProducer */
public class l implements am<com.huluxia.image.core.common.references.a<com.huluxia.image.base.imagepipeline.image.b>> {
    public static final String Ji = "DecodeProducer";
    public static final String Jp = "bitmapSize";
    public static final String Jq = "hasGoodQuality";
    public static final String Jr = "isFinal";
    public static final String Jt = "imageFormat";
    public static final String Ju = "encodedImageSize";
    public static final String Jv = "requestedImageSize";
    public static final String Jw = "sampleSize";
    private final com.huluxia.image.pipeline.decoder.b FA;
    private final d FG;
    private final boolean FY;
    private final boolean Fw;
    private final am<com.huluxia.image.base.imagepipeline.image.d> IY;
    private final boolean Jx;
    private final Executor mExecutor;
    private final com.huluxia.image.base.imagepipeline.memory.a xl;

    /* compiled from: DecodeProducer */
    private abstract class c extends m<com.huluxia.image.base.imagepipeline.image.d, com.huluxia.image.core.common.references.a<com.huluxia.image.base.imagepipeline.image.b>> {
        private final com.huluxia.image.base.imagepipeline.common.a Ex;
        @GuardedBy("this")
        private boolean IZ = false;
        private final JobScheduler JB;
        private final aq Jb;
        private final ao Jn;
        final /* synthetic */ l Jy;

        protected abstract int g(com.huluxia.image.base.imagepipeline.image.d dVar);

        protected abstract g hN();

        public /* synthetic */ void d(Object obj, boolean z) {
            a((com.huluxia.image.base.imagepipeline.image.d) obj, z);
        }

        public c(final l lVar, j<com.huluxia.image.core.common.references.a<com.huluxia.image.base.imagepipeline.image.b>> consumer, final ao producerContext, final boolean decodeCancellationEnabled) {
            this.Jy = lVar;
            super(consumer);
            this.Jn = producerContext;
            this.Jb = producerContext.oB();
            this.Ex = producerContext.oA().pC();
            this.JB = new JobScheduler(lVar.mExecutor, new com.huluxia.image.pipeline.producers.JobScheduler.a(this) {
                final /* synthetic */ c JE;

                public void d(com.huluxia.image.base.imagepipeline.image.d encodedImage, boolean isLast) {
                    if (encodedImage != null) {
                        if (this.JE.Jy.Fw) {
                            ImageRequest request = producerContext.oA();
                            if (this.JE.Jy.Jx || !f.isNetworkUri(request.pv())) {
                                encodedImage.bp(p.a(request, encodedImage));
                            }
                        }
                        this.JE.c(encodedImage, isLast);
                    }
                }
            }, this.Ex.wh);
            this.Jn.a(new e(this) {
                final /* synthetic */ c JE;

                public void oI() {
                    if (this.JE.Jn.oF()) {
                        this.JE.JB.oQ();
                    }
                }

                public void cj() {
                    if (decodeCancellationEnabled) {
                        this.JE.oL();
                    }
                }
            });
        }

        public void a(com.huluxia.image.base.imagepipeline.image.d newResult, boolean isLast) {
            if (isLast && !com.huluxia.image.base.imagepipeline.image.d.e(newResult)) {
                handleError(new NullPointerException("Encoded image is not valid."));
            } else if (!b(newResult, isLast)) {
            } else {
                if (isLast || this.Jn.oF()) {
                    this.JB.oQ();
                }
            }
        }

        protected void n(float progress) {
            super.n(0.99f * progress);
        }

        public void h(Throwable t) {
            handleError(t);
        }

        public void ns() {
            oL();
        }

        protected boolean b(com.huluxia.image.base.imagepipeline.image.d ref, boolean isLast) {
            return this.JB.e(ref, isLast);
        }

        private void c(com.huluxia.image.base.imagepipeline.image.d encodedImage, boolean isLast) {
            if (!isFinished() && com.huluxia.image.base.imagepipeline.image.d.e(encodedImage)) {
                String imageFormatStr;
                String encodedImageSize;
                String sampleSize;
                String requestedSizeStr;
                com.huluxia.image.base.imageformat.d imageFormat = encodedImage.hT();
                if (imageFormat != null) {
                    imageFormatStr = imageFormat.getName();
                } else {
                    imageFormatStr = "unknown";
                }
                if (encodedImage != null) {
                    encodedImageSize = encodedImage.getWidth() + "x" + encodedImage.getHeight();
                    sampleSize = String.valueOf(encodedImage.hU());
                } else {
                    encodedImageSize = "unknown";
                    sampleSize = "unknown";
                }
                com.huluxia.image.base.imagepipeline.common.c resizeOptions = this.Jn.oA().pz();
                if (resizeOptions != null) {
                    requestedSizeStr = resizeOptions.width + "x" + resizeOptions.height;
                } else {
                    requestedSizeStr = "unknown";
                }
                long queueTime;
                g quality;
                com.huluxia.image.base.imagepipeline.image.b image;
                try {
                    queueTime = this.JB.oU();
                    int length = isLast ? encodedImage.getSize() : g(encodedImage);
                    quality = isLast ? com.huluxia.image.base.imagepipeline.image.f.wZ : hN();
                    this.Jb.n(this.Jn.getId(), l.Ji);
                    image = null;
                    image = this.Jy.FA.a(encodedImage, length, quality, this.Ex);
                    this.Jb.c(this.Jn.getId(), l.Ji, a(image, queueTime, quality, isLast, imageFormatStr, encodedImageSize, requestedSizeStr, sampleSize));
                    a(image, isLast);
                } catch (Exception e) {
                    this.Jb.a(this.Jn.getId(), l.Ji, e, a(image, queueTime, quality, isLast, imageFormatStr, encodedImageSize, requestedSizeStr, sampleSize));
                    handleError(e);
                } finally {
                    com.huluxia.image.base.imagepipeline.image.d.d(encodedImage);
                }
            }
        }

        private Map<String, String> a(@Nullable com.huluxia.image.base.imagepipeline.image.b image, long queueTime, g quality, boolean isFinal, String imageFormatName, String encodedImageSize, String requestImageSize, String sampleSize) {
            if (!this.Jb.bE(this.Jn.getId())) {
                return null;
            }
            String queueStr = String.valueOf(queueTime);
            String qualityStr = String.valueOf(quality.ia());
            String finalStr = String.valueOf(isFinal);
            if (image instanceof com.huluxia.image.base.imagepipeline.image.c) {
                Bitmap bitmap = ((com.huluxia.image.base.imagepipeline.image.c) image).hM();
                String sizeStr = bitmap.getWidth() + "x" + bitmap.getHeight();
                Map<String, String> tmpMap = new HashMap(8);
                tmpMap.put(l.Jp, sizeStr);
                tmpMap.put("queueTime", queueStr);
                tmpMap.put(l.Jq, qualityStr);
                tmpMap.put(l.Jr, finalStr);
                tmpMap.put(l.Ju, encodedImageSize);
                tmpMap.put(l.Jt, imageFormatName);
                tmpMap.put(l.Jv, requestImageSize);
                tmpMap.put(l.Jw, sampleSize);
                return ImmutableMap.copyOf(tmpMap);
            }
            tmpMap = new HashMap(7);
            tmpMap.put("queueTime", queueStr);
            tmpMap.put(l.Jq, qualityStr);
            tmpMap.put(l.Jr, finalStr);
            tmpMap.put(l.Ju, encodedImageSize);
            tmpMap.put(l.Jt, imageFormatName);
            tmpMap.put(l.Jv, requestImageSize);
            tmpMap.put(l.Jw, sampleSize);
            return ImmutableMap.copyOf(tmpMap);
        }

        private synchronized boolean isFinished() {
            return this.IZ;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void aq(boolean r3) {
            /*
            r2 = this;
            monitor-enter(r2);
            if (r3 == 0) goto L_0x0007;
        L_0x0003:
            r0 = r2.IZ;	 Catch:{ all -> 0x001c }
            if (r0 == 0) goto L_0x0009;
        L_0x0007:
            monitor-exit(r2);	 Catch:{ all -> 0x001c }
        L_0x0008:
            return;
        L_0x0009:
            r0 = r2.oM();	 Catch:{ all -> 0x001c }
            r1 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
            r0.onProgressUpdate(r1);	 Catch:{ all -> 0x001c }
            r0 = 1;
            r2.IZ = r0;	 Catch:{ all -> 0x001c }
            monitor-exit(r2);	 Catch:{ all -> 0x001c }
            r0 = r2.JB;
            r0.oP();
            goto L_0x0008;
        L_0x001c:
            r0 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x001c }
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.huluxia.image.pipeline.producers.show_toast.c.aq(boolean):void");
        }

        private void a(com.huluxia.image.base.imagepipeline.image.b decodedImage, boolean isFinal) {
            com.huluxia.image.core.common.references.a<com.huluxia.image.base.imagepipeline.image.b> decodedImageRef = com.huluxia.image.core.common.references.a.b(decodedImage);
            try {
                aq(isFinal);
                oM().e(decodedImageRef, isFinal);
            } finally {
                com.huluxia.image.core.common.references.a.c(decodedImageRef);
            }
        }

        private void handleError(Throwable t) {
            aq(true);
            oM().j(t);
        }

        private void oL() {
            aq(true);
            oM().iq();
        }
    }

    /* compiled from: DecodeProducer */
    private class a extends c {
        final /* synthetic */ l Jy;

        public a(l lVar, j<com.huluxia.image.core.common.references.a<com.huluxia.image.base.imagepipeline.image.b>> consumer, ao producerContext, boolean decodeCancellationEnabled) {
            this.Jy = lVar;
            super(lVar, consumer, producerContext, decodeCancellationEnabled);
        }

        protected synchronized boolean b(com.huluxia.image.base.imagepipeline.image.d encodedImage, boolean isLast) {
            boolean b;
            if (isLast) {
                b = super.b(encodedImage, isLast);
            } else {
                b = false;
            }
            return b;
        }

        protected int g(com.huluxia.image.base.imagepipeline.image.d encodedImage) {
            return encodedImage.getSize();
        }

        protected g hN() {
            return com.huluxia.image.base.imagepipeline.image.f.a(0, false, false);
        }
    }

    /* compiled from: DecodeProducer */
    private class b extends c {
        private final d FG;
        private int JA = 0;
        final /* synthetic */ l Jy;
        private final e Jz;

        public b(l lVar, j<com.huluxia.image.core.common.references.a<com.huluxia.image.base.imagepipeline.image.b>> consumer, ao producerContext, e progressiveJpegParser, d progressiveJpegConfig, boolean decodeCancellationEnabled) {
            this.Jy = lVar;
            super(lVar, consumer, producerContext, decodeCancellationEnabled);
            this.Jz = (e) Preconditions.checkNotNull(progressiveJpegParser);
            this.FG = (d) Preconditions.checkNotNull(progressiveJpegConfig);
        }

        protected synchronized boolean b(com.huluxia.image.base.imagepipeline.image.d encodedImage, boolean isLast) {
            boolean ret;
            ret = super.b(encodedImage, isLast);
            if (!isLast && com.huluxia.image.base.imagepipeline.image.d.e(encodedImage)) {
                if (this.Jz.f(encodedImage)) {
                    int scanNum = this.Jz.nH();
                    if (scanNum <= this.JA || scanNum < this.FG.cn(this.JA)) {
                        ret = false;
                    } else {
                        this.JA = scanNum;
                    }
                } else {
                    ret = false;
                }
            }
            return ret;
        }

        protected int g(com.huluxia.image.base.imagepipeline.image.d encodedImage) {
            return this.Jz.nG();
        }

        protected g hN() {
            return this.FG.co(this.Jz.nH());
        }
    }

    public l(com.huluxia.image.base.imagepipeline.memory.a byteArrayPool, Executor executor, com.huluxia.image.pipeline.decoder.b imageDecoder, d progressiveJpegConfig, boolean downsampleEnabled, boolean downsampleEnabledForNetwork, boolean decodeCancellationEnabled, am<com.huluxia.image.base.imagepipeline.image.d> inputProducer) {
        this.xl = (com.huluxia.image.base.imagepipeline.memory.a) Preconditions.checkNotNull(byteArrayPool);
        this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
        this.FA = (com.huluxia.image.pipeline.decoder.b) Preconditions.checkNotNull(imageDecoder);
        this.FG = (d) Preconditions.checkNotNull(progressiveJpegConfig);
        this.Fw = downsampleEnabled;
        this.Jx = downsampleEnabledForNetwork;
        this.IY = (am) Preconditions.checkNotNull(inputProducer);
        this.FY = decodeCancellationEnabled;
    }

    public void b(j<com.huluxia.image.core.common.references.a<com.huluxia.image.base.imagepipeline.image.b>> consumer, ao producerContext) {
        c progressiveDecoder;
        if (f.isNetworkUri(producerContext.oA().pv())) {
            progressiveDecoder = new b(this, consumer, producerContext, new e(this.xl), this.FG, this.FY);
        } else {
            progressiveDecoder = new a(this, consumer, producerContext, this.FY);
        }
        this.IY.b(progressiveDecoder, producerContext);
    }
}
