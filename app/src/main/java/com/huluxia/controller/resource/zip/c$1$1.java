package com.huluxia.controller.resource.zip;

import com.huluxia.controller.resource.zip.c.1;
import com.huluxia.framework.DownloadMemCache;
import com.huluxia.framework.base.http.toolbox.download.DownloadRecord;
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
