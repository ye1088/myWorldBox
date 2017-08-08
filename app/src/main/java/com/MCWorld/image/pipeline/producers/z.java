package com.MCWorld.image.pipeline.producers;

import com.MCWorld.image.base.imagepipeline.memory.d;
import com.MCWorld.image.pipeline.request.ImageRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.Executor;

/* compiled from: LocalFileFetchProducer */
public class z extends y {
    public static final String Ji = "LocalFileFetchProducer";

    public z(Executor executor, d pooledByteBufferFactory, boolean decodeFileDescriptorEnabled) {
        super(executor, pooledByteBufferFactory, decodeFileDescriptorEnabled);
    }

    protected com.MCWorld.image.base.imagepipeline.image.d l(ImageRequest imageRequest) throws IOException {
        return g(new FileInputStream(imageRequest.pG().toString()), (int) imageRequest.pG().length());
    }

    protected String oK() {
        return Ji;
    }
}
