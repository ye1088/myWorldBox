package com.MCWorld.framework.base.http.toolbox.statis;

import com.MCWorld.framework.base.http.io.impl.request.DownloadRequest;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;

public interface IDownloadTracker {
    void trackError(DownloadRequest downloadRequest, VolleyError volleyError);

    void trackSectionSpeed(DownloadRequest downloadRequest, long j, boolean z);
}
