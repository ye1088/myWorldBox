package com.huluxia.image.pipeline.producers;

import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.framework.base.utils.VisibleForTesting;
import com.huluxia.image.base.imageformat.b;
import com.huluxia.image.base.imagepipeline.common.c;
import com.huluxia.image.base.imagepipeline.image.d;
import com.huluxia.image.pipeline.request.ImageRequest;

/* compiled from: DownsampleUtil */
public class p {
    private static final float JM = 0.33333334f;
    private static final int wS = 1;
    private static final float xu = 2048.0f;

    private p() {
    }

    public static int a(ImageRequest imageRequest, d encodedImage) {
        if (!d.c(encodedImage)) {
            return 1;
        }
        int sampleSize;
        float ratio = b(imageRequest, encodedImage);
        if (encodedImage.hT() == b.vl) {
            sampleSize = p(ratio);
        } else {
            sampleSize = o(ratio);
        }
        int maxDimension = Math.max(encodedImage.getHeight(), encodedImage.getWidth());
        while (((float) (maxDimension / sampleSize)) > 2048.0f) {
            if (encodedImage.hT() == b.vl) {
                sampleSize *= 2;
            } else {
                sampleSize++;
            }
        }
        return sampleSize;
    }

    @VisibleForTesting
    static float b(ImageRequest imageRequest, d encodedImage) {
        Preconditions.checkArgument(d.c(encodedImage));
        c resizeOptions = imageRequest.pz();
        if (resizeOptions == null || resizeOptions.height <= 0 || resizeOptions.width <= 0 || encodedImage.getWidth() == 0 || encodedImage.getHeight() == 0) {
            return 1.0f;
        }
        boolean swapDimensions;
        int rotationAngle = c(imageRequest, encodedImage);
        if (rotationAngle == 90 || rotationAngle == 270) {
            swapDimensions = true;
        } else {
            swapDimensions = false;
        }
        HLog.verbose("DownsampleUtil", String.format("Downsample - Specified size: %getMCVersion%d, image size: %getMCVersion%d ratio: %.1f x %.1f, ratio: %.3f for %s", new Object[]{Integer.valueOf(resizeOptions.width), Integer.valueOf(resizeOptions.height), Integer.valueOf(swapDimensions ? encodedImage.getHeight() : encodedImage.getWidth()), Integer.valueOf(swapDimensions ? encodedImage.getWidth() : encodedImage.getHeight()), Float.valueOf(((float) resizeOptions.width) / ((float) (swapDimensions ? encodedImage.getHeight() : encodedImage.getWidth()))), Float.valueOf(((float) resizeOptions.height) / ((float) (swapDimensions ? encodedImage.getWidth() : encodedImage.getHeight()))), Float.valueOf(Math.max(((float) resizeOptions.width) / ((float) (swapDimensions ? encodedImage.getHeight() : encodedImage.getWidth())), ((float) resizeOptions.height) / ((float) (swapDimensions ? encodedImage.getWidth() : encodedImage.getHeight())))), imageRequest.pv().toString()}), new Object[0]);
        return Math.max(((float) resizeOptions.width) / ((float) (swapDimensions ? encodedImage.getHeight() : encodedImage.getWidth())), ((float) resizeOptions.height) / ((float) (swapDimensions ? encodedImage.getWidth() : encodedImage.getHeight())));
    }

    @VisibleForTesting
    static int o(float ratio) {
        if (ratio > c.wt) {
            return 1;
        }
        int sampleSize = 2;
        while (true) {
            if ((1.0d / ((double) sampleSize)) + (0.3333333432674408d * (1.0d / (Math.pow((double) sampleSize, 2.0d) - ((double) sampleSize)))) <= ((double) ratio)) {
                return sampleSize - 1;
            }
            sampleSize++;
        }
    }

    @VisibleForTesting
    static int p(float ratio) {
        if (ratio > c.wt) {
            return 1;
        }
        int sampleSize = 2;
        while (true) {
            if ((1.0d / ((double) (sampleSize * 2))) + (0.3333333432674408d * (1.0d / ((double) (sampleSize * 2)))) <= ((double) ratio)) {
                return sampleSize;
            }
            sampleSize *= 2;
        }
    }

    private static int c(ImageRequest imageRequest, d encodedImage) {
        boolean z = false;
        if (!imageRequest.pA().hD()) {
            return 0;
        }
        int rotationAngle = encodedImage.hQ();
        if (rotationAngle == 0 || rotationAngle == 90 || rotationAngle == 180 || rotationAngle == 270) {
            z = true;
        }
        Preconditions.checkArgument(z);
        return rotationAngle;
    }

    @VisibleForTesting
    static int roundToPowerOfTwo(int sampleSize) {
        int compare = 1;
        while (compare < sampleSize) {
            compare *= 2;
        }
        return compare;
    }
}
