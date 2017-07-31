package com.huluxia.image.pipeline.bitmaps;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import com.huluxia.image.base.imageformat.b;
import com.huluxia.image.base.imagepipeline.bitmaps.a;
import com.huluxia.image.base.imagepipeline.image.d;
import com.huluxia.image.base.imagepipeline.memory.PooledByteBuffer;
import javax.annotation.concurrent.ThreadSafe;

@TargetApi(11)
@ThreadSafe
/* compiled from: HoneycombBitmapFactory */
public class e extends a {
    private final b Eq;
    private final com.huluxia.image.pipeline.platform.e Es;

    public e(b jpegGenerator, com.huluxia.image.pipeline.platform.e purgeableDecoder) {
        this.Eq = jpegGenerator;
        this.Es = purgeableDecoder;
    }

    public com.huluxia.image.core.common.references.a<Bitmap> b(int width, int height, Config bitmapConfig) {
        com.huluxia.image.core.common.references.a<PooledByteBuffer> jpgRef = this.Eq.b((short) width, (short) height);
        d encodedImage;
        try {
            encodedImage = new d(jpgRef);
            encodedImage.d(b.vl);
            com.huluxia.image.core.common.references.a<Bitmap> bitmapRef = this.Es.a(encodedImage, bitmapConfig, ((PooledByteBuffer) jpgRef.get()).size());
            ((Bitmap) bitmapRef.get()).eraseColor(0);
            d.d(encodedImage);
            jpgRef.close();
            return bitmapRef;
        } catch (Throwable th) {
            jpgRef.close();
        }
    }
}
