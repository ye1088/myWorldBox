package com.huluxia.controller.resource.action;

import com.huluxia.framework.base.log.HLog;
import java.io.File;

/* compiled from: RenameAction */
public class d implements b {
    private String mG;
    private String mH;

    public d(String oldPath, String newPath) {
        this.mG = oldPath;
        this.mH = newPath;
    }

    public boolean run() {
        File oldFile = new File(this.mG);
        if (oldFile.exists()) {
            boolean result = oldFile.renameTo(new File(this.mH));
            HLog.info(this, "rename old path %s to new path %s result %b", new Object[]{oldFile, this.mH, Boolean.valueOf(result)});
        } else {
            HLog.error(this, "old file not exist, oldpath %s", new Object[]{this.mG});
        }
        return false;
    }
}
