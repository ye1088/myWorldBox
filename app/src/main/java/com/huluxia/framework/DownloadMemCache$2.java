package com.huluxia.framework;

import com.huluxia.framework.base.http.toolbox.download.DownloadRecord;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.UtilsFunction;
import java.util.ArrayList;
import java.util.List;

class DownloadMemCache$2 implements Runnable {
    final /* synthetic */ DownloadMemCache this$0;

    DownloadMemCache$2(DownloadMemCache this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        List<DownloadRecord> data = new ArrayList();
        int i = 0;
        while (i < 10) {
            try {
                List<DownloadRecord> records = BaseDataDb.getInstance().syncReloadDownloadRecord();
                if (!UtilsFunction.empty(records)) {
                    data.addAll(records);
                }
                HLog.info("DownloadMemCache", "load all download records %d", new Object[]{Integer.valueOf(UtilsFunction.size(data))});
                DownloadMemCache.access$202(this.this$0, true);
                this.this$0.resetRecord(data);
            } catch (Exception e) {
                HLog.error("DownloadMemCache", "sync load record time %d, e %s", new Object[]{Integer.valueOf(i), e});
                i++;
            }
        }
        HLog.info("DownloadMemCache", "load all download records %d", new Object[]{Integer.valueOf(UtilsFunction.size(data))});
        DownloadMemCache.access$202(this.this$0, true);
        this.this$0.resetRecord(data);
    }
}
