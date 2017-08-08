package com.MCWorld.image.pipeline.datasource;

import android.graphics.Bitmap;
import com.MCWorld.image.core.common.references.a;
import com.MCWorld.image.core.datasource.c;
import javax.annotation.Nullable;

/* compiled from: BaseBitmapDataSubscriber */
public abstract class b extends com.MCWorld.image.core.datasource.b<a<com.MCWorld.image.base.imagepipeline.image.b>> {
    protected abstract void e(@Nullable Bitmap bitmap);

    public void onNewResultImpl(c<a<com.MCWorld.image.base.imagepipeline.image.b>> dataSource) {
        if (dataSource.isFinished()) {
            a<com.MCWorld.image.base.imagepipeline.image.b> closeableImageRef = (a) dataSource.getResult();
            Bitmap bitmap = null;
            if (closeableImageRef != null && (closeableImageRef.get() instanceof com.MCWorld.image.base.imagepipeline.image.a)) {
                bitmap = ((com.MCWorld.image.base.imagepipeline.image.a) closeableImageRef.get()).hM();
            }
            try {
                e(bitmap);
            } finally {
                a.c(closeableImageRef);
            }
        }
    }
}
