package com.tencent.smtt.sdk;

import android.webkit.WebChromeClient.CustomViewCallback;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient$CustomViewCallback;

class SystemWebChromeClient$b implements IX5WebChromeClient$CustomViewCallback {
    CustomViewCallback a;
    final /* synthetic */ SystemWebChromeClient b;

    SystemWebChromeClient$b(SystemWebChromeClient systemWebChromeClient, CustomViewCallback customViewCallback) {
        this.b = systemWebChromeClient;
        this.a = customViewCallback;
    }

    public void onCustomViewHidden() {
        this.a.onCustomViewHidden();
    }
}
