package com.huluxia.image.pipeline.bitmaps;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Build.VERSION;
import com.huluxia.image.base.imageformat.b;
import com.huluxia.image.base.imagepipeline.memory.PooledByteBuffer;
import com.huluxia.image.core.common.webp.a;
import com.huluxia.image.pipeline.memory.j;
import com.huluxia.image.pipeline.memory.s;

/* compiled from: HoneycombBitmapCreator */
public class d implements a {
    protected static final byte[] Ep = new byte[]{(byte) -1, (byte) -39};
    private final b Eq;
    private final j Er;

    public d(s poolFactory) {
        this.Er = poolFactory.oq();
        this.Eq = new b(poolFactory.ou());
    }

    public Bitmap d(int width, int height, Config bitmapConfig) {
        Throwable th;
        com.huluxia.image.core.common.references.a jpgRef = this.Eq.b((short) width, (short) height);
        com.huluxia.image.base.imagepipeline.image.d encodedImage = null;
        try {
            com.huluxia.image.base.imagepipeline.image.d encodedImage2 = new com.huluxia.image.base.imagepipeline.image.d(jpgRef);
            try {
                encodedImage2.d(b.vl);
                Options options = a(encodedImage2.hU(), bitmapConfig);
                int length = ((PooledByteBuffer) jpgRef.get()).size();
                byte[] suffix = a(jpgRef, length) ? null : Ep;
                PooledByteBuffer pooledByteBuffer = (PooledByteBuffer) jpgRef.get();
                com.huluxia.image.core.common.references.a<byte[]> encodedBytesArrayRef = this.Er.cA(length + 2);
                byte[] encodedBytesArray = (byte[]) encodedBytesArrayRef.get();
                pooledByteBuffer.a(0, encodedBytesArray, 0, length);
                if (suffix != null) {
                    l(encodedBytesArray, length);
                    length += 2;
                }
                Bitmap bitmap = BitmapFactory.decodeByteArray(encodedBytesArray, 0, length, options);
                bitmap.eraseColor(0);
                com.huluxia.image.core.common.references.a.c(encodedBytesArrayRef);
                com.huluxia.image.base.imagepipeline.image.d.d(encodedImage2);
                com.huluxia.image.core.common.references.a.c(jpgRef);
                return bitmap;
            } catch (Throwable th2) {
                th = th2;
                encodedImage = encodedImage2;
                com.huluxia.image.core.common.references.a.c(null);
                com.huluxia.image.base.imagepipeline.image.d.d(encodedImage);
                com.huluxia.image.core.common.references.a.c(jpgRef);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            com.huluxia.image.core.common.references.a.c(null);
            com.huluxia.image.base.imagepipeline.image.d.d(encodedImage);
            com.huluxia.image.core.common.references.a.c(jpgRef);
            throw th;
        }
    }

    private static Options a(int sampleSize, Config bitmapConfig) {
        Options options = new Options();
        options.inDither = true;
        options.inPreferredConfig = bitmapConfig;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inSampleSize = sampleSize;
        if (VERSION.SDK_INT >= 11) {
            options.inMutable = true;
        }
        return options;
    }

    private static void l(byte[] imageBytes, int offset) {
        imageBytes[offset] = (byte) -1;
        imageBytes[offset + 1] = (byte) -39;
    }

    protected static boolean a(com.huluxia.image.core.common.references.a<PooledByteBuffer> bytesRef, int length) {
        PooledByteBuffer buffer = (PooledByteBuffer) bytesRef.get();
        return length >= 2 && buffer.bs(length - 2) == (byte) -1 && buffer.bs(length - 1) == (byte) -39;
    }
}
