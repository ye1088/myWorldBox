package com.MCWorld.image.pipeline.producers;

import android.content.ContentResolver;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Pair;
import com.MCWorld.framework.base.utils.ImmutableMap;
import com.MCWorld.framework.base.utils.VisibleForTesting;
import com.MCWorld.image.base.imageformat.b;
import com.MCWorld.image.base.imagepipeline.common.c;
import com.MCWorld.image.base.imagepipeline.image.d;
import com.MCWorld.image.base.imagepipeline.memory.PooledByteBuffer;
import com.MCWorld.image.base.imagepipeline.memory.e;
import com.MCWorld.image.base.imageutils.a;
import com.MCWorld.image.core.common.util.f;
import com.MCWorld.image.pipeline.request.ImageRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executor;

/* compiled from: LocalExifThumbnailProducer */
public class x implements az<d> {
    public static final String Ji = "LocalExifThumbnailProducer";
    private static final int Kk = 512;
    @VisibleForTesting
    static final String Kl = "createdThumbnail";
    private final com.MCWorld.image.base.imagepipeline.memory.d Eo;
    private final ContentResolver mContentResolver;
    private final Executor mExecutor;

    public x(Executor executor, com.MCWorld.image.base.imagepipeline.memory.d pooledByteBufferFactory, ContentResolver contentResolver) {
        this.mExecutor = executor;
        this.Eo = pooledByteBufferFactory;
        this.mContentResolver = contentResolver;
    }

    public boolean a(c resizeOptions) {
        return ba.a(512, 512, resizeOptions);
    }

    public void b(j<d> consumer, ao producerContext) {
        aq listener = producerContext.oB();
        String requestId = producerContext.getId();
        final ImageRequest imageRequest = producerContext.oA();
        final StatefulProducerRunnable cancellableProducerRunnable = new StatefulProducerRunnable<d>(this, consumer, listener, Ji, requestId) {
            final /* synthetic */ x Km;

            protected /* synthetic */ Map M(Object obj) {
                return i((d) obj);
            }

            protected /* synthetic */ Object getResult() throws Exception {
                return oW();
            }

            protected /* synthetic */ void p(Object obj) {
                h((d) obj);
            }

            protected d oW() throws Exception {
                ExifInterface exifInterface = this.Km.u(imageRequest.pv());
                if (exifInterface == null || !exifInterface.hasThumbnail()) {
                    return null;
                }
                return this.Km.a(this.Km.Eo.j(exifInterface.getThumbnail()), exifInterface);
            }

            protected void h(d result) {
                d.d(result);
            }

            protected Map<String, String> i(d result) {
                return ImmutableMap.of(x.Kl, Boolean.toString(result != null));
            }
        };
        producerContext.a(new e(this) {
            final /* synthetic */ x Km;

            public void cj() {
                cancellableProducerRunnable.cancel();
            }
        });
        this.mExecutor.execute(cancellableProducerRunnable);
    }

    @VisibleForTesting
    ExifInterface u(Uri uri) throws IOException {
        String realPath = f.a(this.mContentResolver, uri);
        if (bI(realPath)) {
            return new ExifInterface(realPath);
        }
        return null;
    }

    private d a(PooledByteBuffer imageBytes, ExifInterface exifInterface) {
        int width;
        int height = -1;
        Pair<Integer, Integer> dimensions = a.j(new e(imageBytes));
        int rotationAngle = a(exifInterface);
        if (dimensions != null) {
            width = ((Integer) dimensions.first).intValue();
        } else {
            width = -1;
        }
        if (dimensions != null) {
            height = ((Integer) dimensions.second).intValue();
        }
        com.MCWorld.image.core.common.references.a<PooledByteBuffer> closeableByteBuffer = com.MCWorld.image.core.common.references.a.b(imageBytes);
        try {
            d encodedImage = new d(closeableByteBuffer);
            encodedImage.d(b.vl);
            encodedImage.bo(rotationAngle);
            encodedImage.setWidth(width);
            encodedImage.setHeight(height);
            return encodedImage;
        } finally {
            com.MCWorld.image.core.common.references.a.c(closeableByteBuffer);
        }
    }

    private int a(ExifInterface exifInterface) {
        return com.MCWorld.image.base.imageutils.b.bv(Integer.parseInt(exifInterface.getAttribute("Orientation")));
    }

    @VisibleForTesting
    boolean bI(String realPath) throws IOException {
        if (realPath == null) {
            return false;
        }
        File file = new File(realPath);
        if (file.exists() && file.canRead()) {
            return true;
        }
        return false;
    }
}
