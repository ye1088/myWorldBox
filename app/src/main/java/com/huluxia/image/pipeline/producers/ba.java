package com.huluxia.image.pipeline.producers;

import com.huluxia.image.base.imagepipeline.common.c;
import com.huluxia.image.base.imagepipeline.image.d;
import com.huluxia.image.base.imageutils.a;

/* compiled from: ThumbnailSizeChecker */
public final class ba {
    public static final float LZ = 1.3333334f;
    private static final int Ma = 90;
    private static final int Mb = 270;

    public static boolean a(int width, int height, c resizeOptions) {
        if (resizeOptions == null) {
            if (((float) cN(width)) < a.xu || cN(height) < 2048) {
                return false;
            }
            return true;
        } else if (cN(width) < resizeOptions.width || cN(height) < resizeOptions.height) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean a(d encodedImage, c resizeOptions) {
        if (encodedImage == null) {
            return false;
        }
        switch (encodedImage.hQ()) {
            case 90:
            case 270:
                return a(encodedImage.getHeight(), encodedImage.getWidth(), resizeOptions);
            default:
                return a(encodedImage.getWidth(), encodedImage.getHeight(), resizeOptions);
        }
    }

    public static int cN(int size) {
        return (int) (((float) size) * LZ);
    }
}
