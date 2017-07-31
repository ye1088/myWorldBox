package com.huluxia.image.pipeline.producers;

import com.huluxia.framework.base.utils.Closeables;
import com.huluxia.framework.base.utils.ImmutableMap;
import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.framework.base.utils.VisibleForTesting;
import com.huluxia.image.base.imageformat.b;
import com.huluxia.image.base.imagepipeline.common.c;
import com.huluxia.image.base.imagepipeline.image.d;
import com.huluxia.image.base.imagepipeline.memory.PooledByteBuffer;
import com.huluxia.image.base.imagepipeline.memory.f;
import com.huluxia.image.core.common.util.TriState;
import com.huluxia.image.pipeline.nativecode.JpegTranscoder;
import com.huluxia.image.pipeline.request.ImageRequest;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

/* compiled from: ResizeAndRotateProducer */
public class as implements am<d> {
    public static final String Ji = "ResizeAndRotateProducer";
    private static final String Lq = "Original size";
    private static final String Lr = "Requested size";
    private static final String Ls = "Fraction";
    private static final int Lt = 360;
    @VisibleForTesting
    static final int Lu = 85;
    @VisibleForTesting
    static final int Lv = 8;
    @VisibleForTesting
    static final int Lw = 100;
    private final com.huluxia.image.base.imagepipeline.memory.d Eo;
    private final am<d> IY;
    private final boolean Lx;
    private final Executor mExecutor;

    /* compiled from: ResizeAndRotateProducer */
    private class a extends m<d, d> {
        private final JobScheduler JB;
        private boolean Jg = false;
        private final ao Jn;
        final /* synthetic */ as Ly;

        protected /* synthetic */ void d(@Nullable Object obj, boolean z) {
            a((d) obj, z);
        }

        public a(final as asVar, final j<d> consumer, ao producerContext) {
            this.Ly = asVar;
            super(consumer);
            this.Jn = producerContext;
            this.JB = new JobScheduler(asVar.mExecutor, new com.huluxia.image.pipeline.producers.JobScheduler.a(this) {
                final /* synthetic */ a LA;

                public void d(d encodedImage, boolean isLast) {
                    this.LA.g(encodedImage, isLast);
                }
            }, 100);
            this.Jn.a(new e(this) {
                final /* synthetic */ a LA;

                public void oI() {
                    if (this.LA.Jn.oF()) {
                        this.LA.JB.oQ();
                    }
                }

                public void cj() {
                    this.LA.JB.oP();
                    this.LA.Jg = true;
                    consumer.iq();
                }
            });
        }

        protected void a(@Nullable d newResult, boolean isLast) {
            if (!this.Jg) {
                if (newResult != null) {
                    TriState shouldTransform = as.a(this.Jn.oA(), newResult, this.Ly.Lx);
                    if (!isLast && shouldTransform == TriState.UNSET) {
                        return;
                    }
                    if (shouldTransform != TriState.YES) {
                        oM().e(newResult, isLast);
                    } else if (!this.JB.e(newResult, isLast)) {
                    } else {
                        if (isLast || this.Jn.oF()) {
                            this.JB.oQ();
                        }
                    }
                } else if (isLast) {
                    oM().e(null, true);
                }
            }
        }

        private void g(d encodedImage, boolean isLast) {
            Exception e;
            Throwable th;
            this.Jn.oB().n(this.Jn.getId(), as.Ji);
            ImageRequest imageRequest = this.Jn.oA();
            f outputStream = this.Ly.Eo.ig();
            Map<String, String> extraMap = null;
            InputStream is = null;
            try {
                int numerator = as.b(imageRequest, encodedImage, this.Ly.Lx);
                extraMap = a(encodedImage, imageRequest, numerator);
                is = encodedImage.getInputStream();
                JpegTranscoder.a(is, outputStream, as.a(imageRequest.pA(), encodedImage), numerator, 85);
                com.huluxia.image.core.common.references.a<PooledByteBuffer> ref = com.huluxia.image.core.common.references.a.b(outputStream.ih());
                try {
                    d ret = new d(ref);
                    d dVar;
                    try {
                        ret.d(b.vl);
                        ret.hW();
                        this.Jn.oB().c(this.Jn.getId(), as.Ji, extraMap);
                        oM().e(ret, isLast);
                        d.d(ret);
                        try {
                            com.huluxia.image.core.common.references.a.c(ref);
                            Closeables.closeQuietly(is);
                            outputStream.close();
                            dVar = ret;
                        } catch (Exception e2) {
                            e = e2;
                            dVar = ret;
                            try {
                                this.Jn.oB().a(this.Jn.getId(), as.Ji, e, extraMap);
                                oM().j(e);
                                Closeables.closeQuietly(is);
                                outputStream.close();
                            } catch (Throwable th2) {
                                th = th2;
                                Closeables.closeQuietly(is);
                                outputStream.close();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            dVar = ret;
                            Closeables.closeQuietly(is);
                            outputStream.close();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        dVar = ret;
                        com.huluxia.image.core.common.references.a.c(ref);
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    com.huluxia.image.core.common.references.a.c(ref);
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                this.Jn.oB().a(this.Jn.getId(), as.Ji, e, extraMap);
                oM().j(e);
                Closeables.closeQuietly(is);
                outputStream.close();
            }
        }

        private Map<String, String> a(d encodedImage, ImageRequest imageRequest, int numerator) {
            if (!this.Jn.oB().bE(this.Jn.getId())) {
                return null;
            }
            String requestedSize;
            String originalSize = encodedImage.getWidth() + "x" + encodedImage.getHeight();
            if (imageRequest.pz() != null) {
                requestedSize = imageRequest.pz().width + "x" + imageRequest.pz().height;
            } else {
                requestedSize = "Unspecified";
            }
            return ImmutableMap.of(as.Lq, originalSize, as.Lr, requestedSize, as.Ls, numerator > 0 ? numerator + "/8" : "", "queueTime", String.valueOf(this.JB.oU()));
        }
    }

    public as(Executor executor, com.huluxia.image.base.imagepipeline.memory.d pooledByteBufferFactory, boolean resizingEnabled, am<d> inputProducer) {
        this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
        this.Eo = (com.huluxia.image.base.imagepipeline.memory.d) Preconditions.checkNotNull(pooledByteBufferFactory);
        this.Lx = resizingEnabled;
        this.IY = (am) Preconditions.checkNotNull(inputProducer);
    }

    public void b(j<d> consumer, ao context) {
        this.IY.b(new a(this, consumer, context), context);
    }

    private static TriState a(ImageRequest request, d encodedImage, boolean resizingEnabled) {
        if (encodedImage == null || encodedImage.hT() == com.huluxia.image.base.imageformat.d.vz) {
            return TriState.UNSET;
        }
        if (encodedImage.hT() != b.vl) {
            return TriState.NO;
        }
        boolean z = b(request.pA(), encodedImage) || cM(b(request, encodedImage, resizingEnabled));
        return TriState.valueOf(z);
    }

    @VisibleForTesting
    static float a(c resizeOptions, int width, int height) {
        if (resizeOptions == null) {
            return 1.0f;
        }
        float ratio = Math.max(((float) resizeOptions.width) / ((float) width), ((float) resizeOptions.height) / ((float) height));
        if (((float) width) * ratio > resizeOptions.wu) {
            ratio = resizeOptions.wu / ((float) width);
        }
        if (((float) height) * ratio > resizeOptions.wu) {
            return resizeOptions.wu / ((float) height);
        }
        return ratio;
    }

    @VisibleForTesting
    static int a(float maxRatio, float roundUpFraction) {
        return (int) ((8.0f * maxRatio) + roundUpFraction);
    }

    private static int b(ImageRequest imageRequest, d encodedImage, boolean resizingEnabled) {
        if (!resizingEnabled) {
            return 8;
        }
        c resizeOptions = imageRequest.pz();
        if (resizeOptions == null) {
            return 8;
        }
        int widthAfterRotation;
        int heightAfterRotation;
        int rotationAngle = a(imageRequest.pA(), encodedImage);
        boolean swapDimensions = rotationAngle == 90 || rotationAngle == 270;
        if (swapDimensions) {
            widthAfterRotation = encodedImage.getHeight();
        } else {
            widthAfterRotation = encodedImage.getWidth();
        }
        if (swapDimensions) {
            heightAfterRotation = encodedImage.getWidth();
        } else {
            heightAfterRotation = encodedImage.getHeight();
        }
        int numerator = a(a(resizeOptions, widthAfterRotation, heightAfterRotation), resizeOptions.wv);
        if (numerator > 8) {
            return 8;
        }
        return numerator < 1 ? 1 : numerator;
    }

    private static int a(com.huluxia.image.base.imagepipeline.common.d rotationOptions, d encodedImage) {
        if (!rotationOptions.hE()) {
            return 0;
        }
        int rotationFromMetadata = k(encodedImage);
        return !rotationOptions.hD() ? (rotationOptions.hF() + rotationFromMetadata) % 360 : rotationFromMetadata;
    }

    private static int k(d encodedImage) {
        switch (encodedImage.hQ()) {
            case 90:
            case 180:
            case 270:
                return encodedImage.hQ();
            default:
                return 0;
        }
    }

    private static boolean cM(int numerator) {
        return numerator < 8;
    }

    private static boolean b(com.huluxia.image.base.imagepipeline.common.d rotationOptions, d encodedImage) {
        return (rotationOptions.hG() || a(rotationOptions, encodedImage) == 0) ? false : true;
    }
}
