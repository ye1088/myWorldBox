package com.huluxia.framework;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.log.HLog;

class BaseHttpMgr$5 implements ErrorListener {
    final /* synthetic */ BaseHttpMgr this$0;
    final /* synthetic */ ErrorListener val$errorlistener;

    BaseHttpMgr$5(BaseHttpMgr this$0, ErrorListener errorListener) {
        this.this$0 = this$0;
        this.val$errorlistener = errorListener;
    }

    public void onErrorResponse(final VolleyError error) {
        HLog.verbose(this, "upload file error, response = " + error, new Object[0]);
        BaseHttpMgr.access$000(this.this$0).post(new Runnable() {
            public void run() {
                if (BaseHttpMgr$5.this.val$errorlistener != null) {
                    BaseHttpMgr$5.this.val$errorlistener.onErrorResponse(error);
                }
            }
        });
    }
}
