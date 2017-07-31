package com.huluxia.controller.resource.zip;

import android.annotation.TargetApi;
import android.support.v4.util.LruCache;
import com.huluxia.framework.base.json.Json;

class HpkUnzipHistory$2 extends LruCache<String, b> {
    final /* synthetic */ c oI;

    HpkUnzipHistory$2(c this$0, int x0) {
        this.oI = this$0;
        super(x0);
    }

    protected /* synthetic */ void entryRemoved(boolean z, Object obj, Object obj2, Object obj3) {
        a(z, (String) obj, (b) obj2, (b) obj3);
    }

    @TargetApi(12)
    protected /* synthetic */ int sizeOf(Object obj, Object obj2) {
        return c((String) obj, (b) obj2);
    }

    @TargetApi(12)
    protected int c(String key, b value) {
        return value == null ? 1 : Json.toJson(value).getBytes().length;
    }

    protected void a(boolean evicted, String key, b oldValue, b newValue) {
    }
}
