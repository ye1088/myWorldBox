package com.huluxia;

import com.huluxia.framework.base.http.toolbox.download.DownloadRecord;
import java.io.File;

class DownloadDialog$1 implements Runnable {
    final /* synthetic */ DownloadDialog eZ;
    final /* synthetic */ DownloadRecord val$record;

    DownloadDialog$1(DownloadDialog this$0, DownloadRecord downloadRecord) {
        this.eZ = this$0;
        this.val$record = downloadRecord;
    }

    public void run() {
        if (this.val$record != null) {
            File file = new File(this.val$record.dir, this.val$record.name);
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
