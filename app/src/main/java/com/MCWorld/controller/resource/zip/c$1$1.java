package com.MCWorld.controller.resource.zip;

.1;
import com.MCWorld.framework.DownloadMemCache;
import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord;
import java.io.File;

/* compiled from: HpkUnzipHistory */
class c$1$1 implements Runnable {
    final /* synthetic */ 1 oJ;

    c$1$1(1 this$1) {
        this.oJ = this$1;
    }

    public void run() {
        for (DownloadRecord record : DownloadMemCache.getInstance().getMemcache()) {
            b fileList = c.ea().aq(record.url);
            if (fileList != null) {
                boolean fileExists = true;
                for (String file : fileList.getFiles()) {
                    fileExists = fileExists && new File(file).exists();
                    fileList.setFileExists(fileExists);
                    if (!fileExists) {
                        break;
                    }
                }
            }
        }
    }
}
