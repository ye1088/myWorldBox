package com.MCWorld.framework;

import com.MCWorld.framework.base.http.io.Response.ProgressListener;
import com.MCWorld.framework.base.log.HLog;

class BaseHttpMgr$4 implements ProgressListener {
    final /* synthetic */ BaseHttpMgr this$0;
    final /* synthetic */ ProgressListener val$progressListener;

    BaseHttpMgr$4(BaseHttpMgr this$0, ProgressListener progressListener) {
        this.this$0 = this$0;
        this.val$progressListener = progressListener;
    }

    public void onProgress(String url, long length, long progress, float speed) {
        HLog.verbose(this, "upload progress url %s , length %d , progress %d, speed %f", new Object[]{url, Long.valueOf(length), Long.valueOf(progress), Float.valueOf(speed)});
        final String str = url;
        final long j = length;
        final long j2 = progress;
        final float f = speed;
        BaseHttpMgr.access$000(this.this$0).post(new Runnable() {
            public void run() {
                if (BaseHttpMgr$4.this.val$progressListener != null) {
                    BaseHttpMgr$4.this.val$progressListener.onProgress(str, j, j2, f);
                }
            }
        });
    }
}
