package com.MCWorld.framework;

import com.MCWorld.framework.base.http.io.Response.CancelListener;
import com.MCWorld.framework.base.log.HLog;

class BaseHttpMgr$6 implements CancelListener {
    final /* synthetic */ BaseHttpMgr this$0;
    final /* synthetic */ CancelListener val$cancelListener;

    BaseHttpMgr$6(BaseHttpMgr this$0, CancelListener cancelListener) {
        this.this$0 = this$0;
        this.val$cancelListener = cancelListener;
    }

    public void onCancel() {
        HLog.verbose(this, "upload file cancel ", new Object[0]);
        BaseHttpMgr.access$000(this.this$0).post(new Runnable() {
            public void run() {
                if (BaseHttpMgr$6.this.val$cancelListener != null) {
                    BaseHttpMgr$6.this.val$cancelListener.onCancel();
                }
            }
        });
    }
}
