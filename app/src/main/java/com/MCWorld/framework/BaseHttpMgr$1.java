package com.MCWorld.framework;

import com.MCWorld.framework.base.http.dispatcher.RequestQueue.RequestFilter;
import com.MCWorld.framework.base.http.io.Request;

class BaseHttpMgr$1 implements RequestFilter {
    final /* synthetic */ BaseHttpMgr this$0;

    BaseHttpMgr$1(BaseHttpMgr this$0) {
        this.this$0 = this$0;
    }

    public boolean apply(Request<?> request) {
        return true;
    }
}
