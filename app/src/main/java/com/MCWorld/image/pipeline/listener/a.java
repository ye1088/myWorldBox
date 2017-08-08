package com.MCWorld.image.pipeline.listener;

import com.MCWorld.image.pipeline.request.ImageRequest;
import java.util.Map;
import javax.annotation.Nullable;

/* compiled from: BaseRequestListener */
public class a implements c {
    public void a(ImageRequest request, Object callerContext, String requestId, boolean isPrefetch) {
    }

    public void a(ImageRequest request, String requestId, boolean isPrefetch) {
    }

    public void a(ImageRequest request, String requestId, Throwable throwable, boolean isPrefetch) {
    }

    public void bD(String requestId) {
    }

    public void n(String requestId, String producerName) {
    }

    public void c(String requestId, String producerName, String eventName) {
    }

    public void c(String requestId, String producerName, @Nullable Map<String, String> map) {
    }

    public void a(String requestId, String producerName, Throwable t, @Nullable Map<String, String> map) {
    }

    public void d(String requestId, String producerName, @Nullable Map<String, String> map) {
    }

    public boolean bE(String requestId) {
        return false;
    }
}
