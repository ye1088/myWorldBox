package com.MCWorld.image.pipeline.producers;

import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.image.base.imageformat.e;
import com.MCWorld.image.base.imagepipeline.image.d;
import com.MCWorld.image.base.imagepipeline.memory.f;
import com.MCWorld.image.core.common.util.TriState;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

/* compiled from: WebpTranscodeProducer */
public class bb implements am<d> {
    public static final String Ji = "WebpTranscodeProducer";
    private static final int Lu = 80;
    public static final int Mc = 0;
    public static final int Md = 1;
    private final com.MCWorld.image.base.imagepipeline.memory.d Eo;
    private final am<d> IY;
    private final int Me;
    private final Executor mExecutor;

    @Retention(RetentionPolicy.SOURCE)
    /* compiled from: WebpTranscodeProducer */
    public @interface a {
    }

    /* compiled from: WebpTranscodeProducer */
    private class b extends m<d, d> {
        private final ao JO;
        final /* synthetic */ bb Mg;
        private TriState Mh = TriState.UNSET;

        protected /* synthetic */ void d(@Nullable Object obj, boolean z) {
            a((d) obj, z);
        }

        public b(bb bbVar, j<d> consumer, ao context) {
            this.Mg = bbVar;
            super(consumer);
            this.JO = context;
        }

        protected void a(@Nullable d newResult, boolean isLast) {
            if (this.Mh == TriState.UNSET && newResult != null) {
                this.Mh = bb.l(newResult);
            }
            if (this.Mh == TriState.NO) {
                oM().e(newResult, isLast);
            } else if (!isLast) {
            } else {
                if (this.Mh != TriState.YES || newResult == null) {
                    oM().e(newResult, isLast);
                } else {
                    this.Mg.a(newResult, oM(), this.JO);
                }
            }
        }
    }

    public bb(Executor executor, com.MCWorld.image.base.imagepipeline.memory.d pooledByteBufferFactory, am<d> inputProducer, int enhancedTranscodingType) {
        this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
        this.Eo = (com.MCWorld.image.base.imagepipeline.memory.d) Preconditions.checkNotNull(pooledByteBufferFactory);
        this.IY = (am) Preconditions.checkNotNull(inputProducer);
        this.Me = enhancedTranscodingType;
    }

    public void b(j<d> consumer, ao context) {
        this.IY.b(new b(this, consumer, context), context);
    }

    private void a(d originalResult, j<d> consumer, ao producerContext) {
        Preconditions.checkNotNull(originalResult);
        final d encodedImageCopy = d.a(originalResult);
        this.mExecutor.execute(new StatefulProducerRunnable<d>(this, consumer, producerContext.oB(), Ji, producerContext.getId()) {
            final /* synthetic */ bb Mg;

            protected /* synthetic */ Object getResult() throws Exception {
                return oW();
            }

            protected /* synthetic */ void o(Object obj) {
                n((d) obj);
            }

            protected /* synthetic */ void p(Object obj) {
                h((d) obj);
            }

            protected d oW() throws Exception {
                f outputStream = this.Mg.Eo.ig();
                com.MCWorld.image.core.common.references.a ref;
                try {
                    bb.a(encodedImageCopy, outputStream, this.Mg.Me);
                    ref = com.MCWorld.image.core.common.references.a.b(outputStream.ih());
                    d encodedImage = new d(ref);
                    encodedImage.b(encodedImageCopy);
                    com.MCWorld.image.core.common.references.a.c(ref);
                    outputStream.close();
                    return encodedImage;
                } catch (Throwable th) {
                    outputStream.close();
                }
            }

            protected void h(d result) {
                d.d(result);
            }

            protected void n(d result) {
                d.d(encodedImageCopy);
                super.o(result);
            }

            protected void i(Exception e) {
                d.d(encodedImageCopy);
                super.i(e);
            }

            protected void iq() {
                d.d(encodedImageCopy);
                super.iq();
            }
        });
    }

    private static TriState l(d encodedImage) {
        Preconditions.checkNotNull(encodedImage);
        com.MCWorld.image.base.imageformat.d imageFormat = e.h(encodedImage.getInputStream());
        if (com.MCWorld.image.base.imageformat.b.b(imageFormat)) {
            com.MCWorld.image.base.imagepipeline.nativecode.a webpTranscoder = com.MCWorld.image.base.imagepipeline.nativecode.b.ii();
            if (webpTranscoder == null) {
                return TriState.NO;
            }
            return TriState.valueOf(!webpTranscoder.e(imageFormat));
        } else if (imageFormat == com.MCWorld.image.base.imageformat.d.vz) {
            return TriState.UNSET;
        } else {
            return TriState.NO;
        }
    }

    private static void a(d encodedImage, f outputStream, int enhancedWebpTranscodingType) throws Exception {
        InputStream imageInputStream = encodedImage.getInputStream();
        com.MCWorld.image.base.imageformat.d imageFormat = e.h(imageInputStream);
        if (imageFormat == com.MCWorld.image.base.imageformat.b.vp || imageFormat == com.MCWorld.image.base.imageformat.b.vr) {
            if (1 == enhancedWebpTranscodingType) {
                com.MCWorld.image.base.imagepipeline.nativecode.b.ii().b(imageInputStream, outputStream);
            } else {
                com.MCWorld.image.base.imagepipeline.nativecode.b.ii().a(imageInputStream, outputStream, 80);
            }
        } else if (imageFormat == com.MCWorld.image.base.imageformat.b.vq || imageFormat == com.MCWorld.image.base.imageformat.b.vs) {
            com.MCWorld.image.base.imagepipeline.nativecode.b.ii().b(imageInputStream, outputStream);
        } else {
            throw new IllegalArgumentException("Wrong image format");
        }
    }
}
