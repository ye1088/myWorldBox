package com.tencent.smtt.sdk;

import android.content.Intent;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient$FileChooserParams;

class o extends WebChromeClient$FileChooserParams {
    final /* synthetic */ IX5WebChromeClient$FileChooserParams a;
    final /* synthetic */ k b;

    o(k kVar, IX5WebChromeClient$FileChooserParams iX5WebChromeClient$FileChooserParams) {
        this.b = kVar;
        this.a = iX5WebChromeClient$FileChooserParams;
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
