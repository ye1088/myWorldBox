package com.huluxia.framework;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.log.HLog;

class BaseHttpMgr$3 implements Listener<String> {
    final /* synthetic */ BaseHttpMgr this$0;
    final /* synthetic */ Listener val$succListener;

    BaseHttpMgr$3(BaseHttpMgr this$0, Listener listener) {
        this.this$0 = this$0;
        this.val$succListener = listener;
    }

    public void onResponse(final String response) {
        HLog.verbose(this, "upload file succ, response = " + response, new Object[0]);
        BaseHttpMgr.access$000(this.this$0).post(new Runnable() {
            public void run() {
                if (BaseHttpMgr$3.this.val$succListener != null) {
                    BaseHttpMgr$3.this.val$succListener.onResponse(response);
                }
            }
        });
    }
}
