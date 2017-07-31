package com.huluxia.image.pipeline.producers;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import com.huluxia.image.base.imagepipeline.memory.d;
import com.huluxia.image.pipeline.request.ImageRequest;
import java.io.IOException;
import java.util.concurrent.Executor;

/* compiled from: LocalAssetFetchProducer */
public class u extends y {
    public static final String Ji = "LocalAssetFetchProducer";
    private final AssetManager Go;

    public u(Executor executor, d pooledByteBufferFactory, AssetManager assetManager, boolean decodeFileDescriptorEnabled) {
        super(executor, pooledByteBufferFactory, decodeFileDescriptorEnabled);
        this.Go = assetManager;
    }

    protected com.huluxia.image.base.imagepipeline.image.d l(ImageRequest imageRequest) throws IOException {
        return g(this.Go.open(n(imageRequest), 2), m(imageRequest));
    }

    private int m(ImageRequest imageRequest) {
        int length;
        AssetFileDescriptor fd = null;
        try {
            fd = this.Go.openFd(n(imageRequest));
            length = (int) fd.getLength();
            if (fd != null) {
                try {
                    fd.close();
                } catch (IOException e) {
                }
            }
        } catch (IOException e2) {
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

    private static String n(ImageRequest imageRequest) {
        return imageRequest.pv().getPath().substring(1);
    }
}
