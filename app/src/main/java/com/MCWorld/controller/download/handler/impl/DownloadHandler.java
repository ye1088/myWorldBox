package com.MCWorld.controller.download.handler.impl;

import android.support.annotation.y;
import com.MCWorld.controller.resource.bean.ResTaskInfo;
import com.MCWorld.controller.resource.bean.ResTaskInfo.State;
import com.MCWorld.controller.resource.handler.impl.d;
import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.UtilsFile;
import java.io.File;

public class DownloadHandler extends d<ResTaskInfo> {
    private static final String TAG = "DownloadHandler";
    private com.MCWorld.controller.resource.action.d action;
    private String mRenameFileName;

    public DownloadHandler(ResTaskInfo info) {
        super(info);
        this.mRenameFileName = info.filename;
    }

    public void onDownloadComplete(Object response, @y DownloadRecord record) {
        String path = new File(record.dir, record.name).getAbsolutePath();
        String newPath = record.dir + File.separator + this.mRenameFileName;
        if (UtilsFile.isExist(newPath)) {
            HLog.info(this, "download file exist, don't update name twice", new Object[0]);
        } else {
            this.action = new com.MCWorld.controller.resource.action.d(path, newPath);
            this.action.run();
            ResTaskInfo resTaskInfo = (ResTaskInfo) getInfo();
            resTaskInfo.filename = new File(newPath).getName();
            this.mReporter.updateName(((ResTaskInfo) getInfo()).url, ((ResTaskInfo) getInfo()).filename);
            path = newPath;
        }
        ((ResTaskInfo) getInfo()).state = State.SUCC.ordinal();
    }
}
