package com.MCWorld.image.pipeline.core;

import com.MCWorld.framework.base.utils.Objects;
import com.MCWorld.framework.base.utils.Supplier;
import com.MCWorld.image.base.imagepipeline.image.b;
import com.MCWorld.image.core.common.references.a;
import com.MCWorld.image.core.datasource.c;
import com.MCWorld.image.pipeline.request.ImageRequest;
import com.MCWorld.image.pipeline.request.ImageRequest$RequestLevel;

/* compiled from: ImagePipeline */
class e$1 implements Supplier<c<a<b>>> {
    final /* synthetic */ Object Av;
    final /* synthetic */ ImageRequest$RequestLevel Fp;
    final /* synthetic */ e Fq;
    final /* synthetic */ ImageRequest val$imageRequest;

    e$1(e this$0, ImageRequest imageRequest, Object obj, ImageRequest$RequestLevel imageRequest$RequestLevel) {
        this.Fq = this$0;
        this.val$imageRequest = imageRequest;
        this.Av = obj;
        this.Fp = imageRequest$RequestLevel;
    }

    public c<a<b>> get() {
        return this.Fq.b(this.val$imageRequest, this.Av, this.Fp);
    }

    public String toString() {
        return Objects.toStringHelper(this).add("uri", this.val$imageRequest.pv()).toString();
    }
}
