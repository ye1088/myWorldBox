package com.huluxia.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.image.base.cache.common.i;
import com.huluxia.image.base.imagepipeline.image.b;
import com.huluxia.image.base.imagepipeline.image.c;
import com.huluxia.image.core.common.references.a;
import com.huluxia.image.pipeline.core.h;
import com.huluxia.image.pipeline.request.ImageRequest;
import java.io.IOException;
import java.io.OutputStream;

/* compiled from: ImageTempCache */
public class f {
    private static final String TAG = "VideoImageCache";

    public static void b(String filePath, Bitmap bitmap) {
        final a<b> closeableReference = a.b(new c(bitmap, com.huluxia.image.base.imagepipeline.bitmaps.b.hf(), com.huluxia.image.base.imagepipeline.image.f.wZ, 0));
        com.huluxia.image.base.cache.common.b cacheKey = h.mz().lj().lN().a(ImageRequest.bL(filePath), null);
        h.mz().lM().a(cacheKey, closeableReference);
        try {
            h.mz().mG().a(cacheKey, new i() {
                public void write(OutputStream os) throws IOException {
                    c staticBitmap = null;
                    try {
                        staticBitmap = (c) closeableReference.get();
                        staticBitmap.hM().compress(CompressFormat.PNG, 90, os);
                    } finally {
                        if (staticBitmap != null) {
                            staticBitmap.close();
                        }
                    }
                }
            });
        } catch (IOException e) {
            HLog.error(TAG, "insert bitmap to main file cache error", new Object[0]);
        }
    }

    public static Bitmap getBitmap(String filepath) {
        com.huluxia.image.base.cache.common.b cacheKey = h.mz().lj().lN().a(ImageRequest.bL(filepath), null);
        a<b> closeableReference = h.mz().lM().m(cacheKey);
        if (closeableReference != null && closeableReference.get() != null && ((com.huluxia.image.base.imagepipeline.image.a) closeableReference.get()).hM() != null) {
            return ((com.huluxia.image.base.imagepipeline.image.a) closeableReference.get()).hM();
        }
        com.huluxia.image.base.binaryresource.a resource = h.mz().mG().d(cacheKey);
        if (resource == null || !(resource instanceof com.huluxia.image.base.binaryresource.c)) {
            return null;
        }
        return BitmapFactory.decodeFile(((com.huluxia.image.base.binaryresource.c) resource).getFile().getAbsolutePath(), new Options());
    }
}
