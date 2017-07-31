package com.huluxia.framework;

import com.huluxia.framework.base.http.dispatcher.RequestQueue.RequestFilter;
import com.huluxia.framework.base.http.io.Request;

class BaseHttpMgr$2 implements RequestFilter {
    final /* synthetic */ BaseHttpMgr this$0;

    BaseHttpMgr$2(BaseHttpMgr this$0) {
        this.this$0 = this$0;
    }

    public boolean apply(Request<?> request) {
        return true;
    }
}
