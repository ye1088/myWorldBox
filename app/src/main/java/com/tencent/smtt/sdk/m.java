package com.tencent.smtt.sdk;

import android.net.Uri;
import android.webkit.ValueCallback;

class m implements ValueCallback<Uri> {
    final /* synthetic */ ValueCallback a;
    final /* synthetic */ k b;

    m(k kVar, ValueCallback valueCallback) {
        this.b = kVar;
        this.a = valueCallback;
    }

    public void a(Uri uri) {
        this.a.onReceiveValue(new Uri[]{uri});
    }

    public /* synthetic */ void onReceiveValue(Object obj) {
        a((Uri) obj);
    }
}
