package com.huluxia.image.pipeline.listener;

import com.huluxia.image.pipeline.producers.aq;
import com.huluxia.image.pipeline.request.ImageRequest;

/* compiled from: RequestListener */
public interface c extends aq {
    void a(ImageRequest imageRequest, Object obj, String str, boolean z);

    void a(ImageRequest imageRequest, String str, Throwable th, boolean z);

    void a(ImageRequest imageRequest, String str, boolean z);

    void bD(String str);
}
