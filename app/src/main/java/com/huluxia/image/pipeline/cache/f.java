package com.huluxia.image.pipeline.cache;

import android.net.Uri;
import com.huluxia.image.base.cache.common.b;
import com.huluxia.image.base.cache.common.h;
import com.huluxia.image.pipeline.request.ImageRequest;
import com.huluxia.image.pipeline.request.d;
import javax.annotation.Nullable;

/* compiled from: DefaultCacheKeyFactory */
public class f implements d {
    private static f ES = null;

    protected f() {
    }

    public static synchronized f lx() {
        f fVar;
        synchronized (f.class) {
            if (ES == null) {
                ES = new f();
            }
            fVar = ES;
        }
        return fVar;
    }

    public b a(ImageRequest request, Object callerContext) {
        return new b(k(request.pv()).toString(), request.pz(), request.pA(), request.pC(), null, null, callerContext);
    }

    public b b(ImageRequest request, Object callerContext) {
        b postprocessorCacheKey;
        String postprocessorName;
        d postprocessor = request.pH();
        if (postprocessor != null) {
            postprocessorCacheKey = postprocessor.oz();
            postprocessorName = postprocessor.getClass().getName();
        } else {
            postprocessorCacheKey = null;
            postprocessorName = null;
        }
        return new b(k(request.pv()).toString(), request.pz(), request.pA(), request.pC(), postprocessorCacheKey, postprocessorName, callerContext);
    }

    public b c(ImageRequest request, @Nullable Object callerContext) {
        return a(request, request.pv(), callerContext);
    }

    public b a(ImageRequest request, Uri sourceUri, @Nullable Object callerContext) {
        return new h(k(sourceUri).toString());
    }

    protected Uri k(Uri sourceUri) {
        return sourceUri;
    }
}
