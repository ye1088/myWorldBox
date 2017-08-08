package com.MCWorld.image.pipeline.core;

import com.MCWorld.framework.base.utils.Objects;
import com.MCWorld.framework.base.utils.Supplier;
import com.MCWorld.image.base.imagepipeline.memory.PooledByteBuffer;
import com.MCWorld.image.core.common.references.a;
import com.MCWorld.image.core.datasource.c;
import com.MCWorld.image.pipeline.request.ImageRequest;

/* compiled from: ImagePipeline */
class e$2 implements Supplier<c<a<PooledByteBuffer>>> {
    final /* synthetic */ Object Av;
    final /* synthetic */ e Fq;
    final /* synthetic */ ImageRequest val$imageRequest;

    e$2(e this$0, ImageRequest imageRequest, Object obj) {
        this.Fq = this$0;
        this.val$imageRequest = imageRequest;
        this.Av = obj;
    }

    public c<a<PooledByteBuffer>> get() {
        return this.Fq.g(this.val$imageRequest, this.Av);
    }

    public String toString() {
        return Objects.toStringHelper(this).add("uri", this.val$imageRequest.pv()).toString();
    }
}
