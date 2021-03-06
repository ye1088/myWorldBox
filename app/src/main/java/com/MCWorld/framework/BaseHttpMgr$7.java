package com.MCWorld.framework;

import com.MCWorld.framework.base.http.dispatcher.RequestQueue.RequestFilter;
import com.MCWorld.framework.base.http.io.Request;
import com.MCWorld.framework.base.http.io.impl.request.DownloadRequest;
import com.MCWorld.framework.base.log.HLog;

class BaseHttpMgr$7 implements RequestFilter {
    final /* synthetic */ BaseHttpMgr this$0;
    final /* synthetic */ String val$url;

    BaseHttpMgr$7(BaseHttpMgr this$0, String str) {
        this.this$0 = this$0;
        this.val$url = str;
    }

    public boolean apply(Request<?> request) {
        if (!(request instanceof DownloadRequest)) {
            return false;
        }
        boolean apply = request.getUrl().equals(this.val$url);
        HLog.verbose("HttpMgr", "cancelDownloadRequest apply = " + apply, new Object[0]);
        return apply;
    }
}
