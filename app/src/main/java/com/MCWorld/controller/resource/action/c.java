package com.MCWorld.controller.resource.action;

import com.MCWorld.controller.resource.http.a;

/* compiled from: PauseDownloadAction */
public class c implements b {
    private String url;

    public c(String url) {
        this.url = url;
    }

    public boolean run() {
        a.dX().pauseDownloadRequest(this.url);
        return true;
    }
}
