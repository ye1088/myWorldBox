package com.huluxia.framework.base.http.toolbox.statis;

import com.huluxia.framework.base.http.io.impl.request.DownloadRequest;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;

public interface IDownloadTracker {
    void trackError(DownloadRequest downloadRequest, VolleyError volleyError);

    void trackSectionSpeed(DownloadRequest downloadRequest, long j, boolean z);
}
