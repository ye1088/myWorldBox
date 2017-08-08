package com.MCWorld.image.pipeline.decoder;

import com.MCWorld.image.base.imagepipeline.common.a;
import com.MCWorld.image.base.imagepipeline.image.b;
import com.MCWorld.image.base.imagepipeline.image.d;
import com.MCWorld.image.base.imagepipeline.image.g;

/* compiled from: DefaultImageDecoder */
class a$1 implements b {
    final /* synthetic */ a GR;

    a$1(a this$0) {
        this.GR = this$0;
    }

    public b a(d encodedImage, int length, g qualityInfo, a options) {
        com.MCWorld.image.base.imageformat.d imageFormat = encodedImage.hT();
        if (imageFormat == com.MCWorld.image.base.imageformat.b.vl) {
            return this.GR.b(encodedImage, length, qualityInfo, options);
        }
        if (imageFormat == com.MCWorld.image.base.imageformat.b.vn) {
            return this.GR.a(encodedImage, options);
        }
        if (imageFormat == com.MCWorld.image.base.imageformat.b.vt) {
            return this.GR.c(encodedImage, options);
        }
        if (imageFormat != com.MCWorld.image.base.imageformat.d.vz) {
            return this.GR.b(encodedImage, options);
        }
        throw new IllegalArgumentException("unknown image format");
    }
}
