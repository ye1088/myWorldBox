package com.tencent.smtt.sdk;

import android.content.Intent;
import android.webkit.WebChromeClient.FileChooserParams;

class u extends WebChromeClient$FileChooserParams {
    final /* synthetic */ FileChooserParams a;
    final /* synthetic */ SystemWebChromeClient b;

    u(SystemWebChromeClient systemWebChromeClient, FileChooserParams fileChooserParams) {
        this.b = systemWebChromeClient;
        this.a = fileChooserParams;
    }

    public Intent createIntent() {
        return this.a.createIntent();
    }

    public String[] getAcceptTypes() {
        return this.a.getAcceptTypes();
    }

    public String getFilenameHint() {
        return this.a.getFilenameHint();
    }

    public int getMode() {
        return this.a.getMode();
    }

    public CharSequence getTitle() {
        return this.a.getTitle();
    }

    public boolean isCaptureEnabled() {
        return this.a.isCaptureEnabled();
    }
}
