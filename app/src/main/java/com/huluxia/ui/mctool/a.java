package com.huluxia.ui.mctool;

import com.huluxia.framework.base.http.io.Response.CancelListener;
import com.huluxia.framework.base.http.io.impl.request.DownloadRequest;

/* compiled from: CancelReqListener */
public abstract class a implements CancelListener {
    private DownloadRequest baG;

    public abstract void onCancel();

    public a(DownloadRequest request) {
        this.baG = request;
    }

    public void setRequest(DownloadRequest request) {
        this.baG = request;
    }

    public DownloadRequest Is() {
        return this.baG;
    }
}
