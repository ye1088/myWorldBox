package com.MCWorld.image.pipeline.platform;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.support.v4.util.Pools.SynchronizedPool;
import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.framework.base.utils.VisibleForTesting;
import com.MCWorld.image.core.common.streams.b;
import com.MCWorld.image.pipeline.memory.d;
import java.io.InputStream;
import java.nio.ByteBuffer;
import javax.annotation.concurrent.ThreadSafe;

@TargetApi(21)
@ThreadSafe
/* compiled from: ArtDecoder */
public class a implements e {
    private static final byte[] IS = new byte[]{(byte) -1, (byte) -39};
    private static final int xo = 16384;
    private final d El;
    @VisibleForTesting
    final SynchronizedPool<ByteBuffer> IR;

    public a(d bitmapPool, int maxNumThreads, SynchronizedPool decodeBuffers) {
        this.El = bitmapPool;
        this.IR = decodeBuffers;
        for (int i = 0; i < maxNumThreads; i++) {
            this.IR.release(ByteBuffer.allocate(16384));
        }
    }

    public com.MCWorld.image.core.common.references.a<Bitmap> a(com.MCWorld.image.base.imagepipeline.image.d encodedImage, Config bitmapConfig) {
        Options options = b(encodedImage, bitmapConfig);
        boolean retryOnFail = options.inPreferredConfig != Config.ARGB_8888;
        try {
            return a(encodedImage.getInputStream(), options);
        } catch (RuntimeException re) {
            if (retryOnFail) {
                return a(encodedImage, Config.ARGB_8888);
            }
            throw re;
        }
    }

    public com.MCWorld.image.core.common.references.a<Bitmap> a(com.MCWorld.image.base.imagepipeline.image.d encodedImage, Config bitmapConfig, int length) {
        InputStream jpegDataStream;
        boolean isJpegComplete = encodedImage.br(length);
        Options options = b(encodedImage, bitmapConfig);
        InputStream jpegDataStream2 = encodedImage.getInputStream();
        Preconditions.checkNotNull(jpegDataStream2);
        if (encodedImage.getSize() > length) {
            jpegDataStream = new com.MCWorld.image.core.common.streams.a(jpegDataStream2, length);
        } else {
            jpegDataStream = jpegDataStream2;
        }
        if (isJpegComplete) {
            jpegDataStream2 = jpegDataStream;
        } else {
            jpegDataStream2 = new b(jpegDataStream, IS);
        }
        boolean retryOnFail = options.inPreferredConfig != Config.ARGB_8888;
        try {
            return a(jpegDataStream2, options);
        } catch (RuntimeException re) {
            if (retryOnFail) {
                return a(encodedImage, Config.ARGB_8888);
            }
            throw re;
        }
    }

    protected com.MCWorld.image.core.common.references.a<Bitmap> a(InputStream inputStream, Options options) {
        Preconditions.checkNotNull(inputStream);
        Bitmap bitmapToReuse = (Bitmap) this.El.get(com.MCWorld.image.base.imageutils.a.c(options.outWidth, options.outHeight, options.inPreferredConfig));
        if (bitmapToReuse == null) {
            throw new NullPointerException("BitmapPool.get returned null");
        }
        options.inBitmap = bitmapToReuse;
        ByteBuffer byteBuffer = (ByteBuffer) this.IR.acquire();
        if (byteBuffer == null) {
            byteBuffer = ByteBuffer.allocate(16384);
        }
        try {
            options.inTempStorage = byteBuffer.array();
            Bitmap decodedBitmap = BitmapFactory.decodeStream(inputStream, null, options);
            this.IR.release(byteBuffer);
            if (bitmapToReuse == decodedBitmap) {
                return com.MCWorld.image.core.common.references.a.a(decodedBitmap, this.El);
            }
            this.El.release(bitmapToReuse);
            decodedBitmap.recycle();
            throw new IllegalStateException();
        } catch (RuntimeException re) {
            this.El.release(bitmapToReuse);
            throw re;
        } catch (Throwable th) {
            this.IR.release(byteBuffer);
        }
    }

    private static Options b(com.MCWorld.image.base.imagepipeline.image.d encodedImage, Config bitmapConfig) {
        Options options = new Options();
        options.inSampleSize = encodedImage.hU();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(encodedImage.getInputStream(), null, options);
        if (options.outWidth == -1 || options.outHeight == -1) {
            throw new IllegalArgumentException();
        }
        options.inJustDecodeBounds = false;
        options.inDither = true;
        options.inPreferredConfig = bitmapConfig;
        options.inMutable = true;
        return options;
    }
}
