package com.MCWorld.image.pipeline.producers;

import com.MCWorld.image.base.imagepipeline.common.Priority;
import com.MCWorld.image.pipeline.request.ImageRequest;
import com.MCWorld.image.pipeline.request.ImageRequest$RequestLevel;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
/* compiled from: SettableProducerContext */
public class at extends d {
    public at(ImageRequest imageRequest, String id, aq producerListener, Object callerContext, ImageRequest$RequestLevel lowestPermittedRequestLevel, boolean isPrefetch, boolean isIntermediateResultExpected, Priority priority) {
        super(imageRequest, id, producerListener, callerContext, lowestPermittedRequestLevel, isPrefetch, isIntermediateResultExpected, priority);
    }

    public void ar(boolean isPrefetch) {
        d.r(ao(isPrefetch));
    }

    public void as(boolean isIntermediateResultExpected) {
        d.s(ap(isIntermediateResultExpected));
    }

    public void b(Priority priority) {
        d.t(a(priority));
    }
}
