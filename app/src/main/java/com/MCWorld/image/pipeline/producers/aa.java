package com.MCWorld.image.pipeline.producers;

import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import com.MCWorld.image.base.imagepipeline.memory.d;
import com.MCWorld.image.pipeline.request.ImageRequest;
import java.io.IOException;
import java.util.concurrent.Executor;

/* compiled from: LocalResourceFetchProducer */
public class aa extends y {
    public static final String Ji = "LocalResourceFetchProducer";
    private final Resources mResources;

    public aa(Executor executor, d pooledByteBufferFactory, Resources resources, boolean decodeFileDescriptorEnabled) {
        super(executor, pooledByteBufferFactory, decodeFileDescriptorEnabled);
        this.mResources = resources;
    }

    protected com.MCWorld.image.base.imagepipeline.image.d l(ImageRequest imageRequest) throws IOException {
        return g(this.mResources.openRawResource(o(imageRequest)), m(imageRequest));
    }

    private int m(ImageRequest imageRequest) {
        int length;
        AssetFileDescriptor fd = null;
        try {
            fd = this.mResources.openRawResourceFd(o(imageRequest));
            length = (int) fd.getLength();
            if (fd != null) {
                try {
                    fd.close();
                } catch (IOException e) {
                }
            }
        } catch (NotFoundException e2) {
            length = -1;
            if (fd != null) {
                try {
                    fd.close();
                } catch (IOException e3) {
                }
            }
        } catch (Throwable th) {
            if (fd != null) {
                try {
                    fd.close();
                } catch (IOException e4) {
                }
            }
        }
        return length;
    }

    protected String oK() {
        return Ji;
    }

    private static int o(ImageRequest imageRequest) {
        return Integer.parseInt(imageRequest.pv().getPath().substring(1));
    }
}
