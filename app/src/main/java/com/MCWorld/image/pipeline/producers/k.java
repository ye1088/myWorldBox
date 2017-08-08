package com.MCWorld.image.pipeline.producers;

import android.net.Uri;
import android.util.Base64;
import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.framework.base.utils.VisibleForTesting;
import com.MCWorld.image.base.imagepipeline.memory.d;
import com.MCWorld.image.core.common.executors.a;
import com.MCWorld.image.pipeline.request.ImageRequest;
import io.netty.handler.codec.http.HttpHeaders.Values;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/* compiled from: DataFetchProducer */
public class k extends y {
    public static final String Ji = "DataFetchProducer";

    public k(d pooledByteBufferFactory, boolean fileDescriptorEnabled) {
        super(a.ik(), pooledByteBufferFactory, fileDescriptorEnabled);
    }

    protected com.MCWorld.image.base.imagepipeline.image.d l(ImageRequest imageRequest) throws IOException {
        byte[] data = bF(imageRequest.pv().toString());
        return f(new ByteArrayInputStream(data), data.length);
    }

    protected String oK() {
        return Ji;
    }

    @VisibleForTesting
    static byte[] bF(String uri) {
        Preconditions.checkArgument(uri.substring(0, 5).equals("data:"));
        int commaPos = uri.indexOf(44);
        String dataStr = uri.substring(commaPos + 1, uri.length());
        if (isBase64(uri.substring(0, commaPos))) {
            return Base64.decode(dataStr, 0);
        }
        return Uri.decode(dataStr).getBytes();
    }

    @VisibleForTesting
    static boolean isBase64(String prefix) {
        if (!prefix.contains(";")) {
            return false;
        }
        String[] parameters = prefix.split(";");
        return parameters[parameters.length - 1].equals(Values.BASE64);
    }
}
