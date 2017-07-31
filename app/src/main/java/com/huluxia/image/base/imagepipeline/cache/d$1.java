package com.huluxia.image.base.imagepipeline.cache;

import android.graphics.Bitmap;
import com.huluxia.image.base.imagepipeline.bitmaps.a.a;

/* compiled from: CountingMemoryCache */
class d$1 implements a {
    final /* synthetic */ d vV;

    d$1(d this$0) {
        this.vV = this$0;
    }

    public void c(Bitmap bitmap, Object callerContext) {
        this.vV.vQ.put(bitmap, callerContext);
    }
}
