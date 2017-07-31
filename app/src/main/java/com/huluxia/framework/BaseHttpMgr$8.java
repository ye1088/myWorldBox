package com.huluxia.framework;

import com.huluxia.framework.base.http.dispatcher.RequestQueue.RequestFilter;
import com.huluxia.framework.base.http.io.Request;
import com.huluxia.framework.base.http.io.impl.request.SegmentRequest;
import com.huluxia.framework.base.log.HLog;

class BaseHttpMgr$8 implements RequestFilter {
    final /* synthetic */ BaseHttpMgr this$0;
    final /* synthetic */ String val$url;

    BaseHttpMgr$8(BaseHttpMgr this$0, String str) {
        this.this$0 = this$0;
        this.val$url = str;
    }

    public boolean apply(Request<?> request) {
        if (!(request instanceof SegmentRequest)) {
            return false;
        }
        boolean apply = request.getUrl().equals(this.val$url);
        HLog.verbose("HttpMgr", "pause segmentRequest apply = " + apply + ", url " + request.getUrl() + ", target " + this.val$url, new Object[0]);
        return apply;
    }
}
