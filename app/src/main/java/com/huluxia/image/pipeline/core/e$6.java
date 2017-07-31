package com.huluxia.image.pipeline.core;

import android.net.Uri;
import com.android.internal.util.Predicate;
import com.huluxia.image.base.cache.common.b;

/* compiled from: ImagePipeline */
class e$6 implements Predicate<b> {
    final /* synthetic */ e Fq;
    final /* synthetic */ Uri Fs;

    e$6(e this$0, Uri uri) {
        this.Fq = this$0;
        this.Fs = uri;
    }

    public /* synthetic */ boolean apply(Object obj) {
        return y((b) obj);
    }

    public boolean y(b key) {
        return key.c(this.Fs);
    }
}
