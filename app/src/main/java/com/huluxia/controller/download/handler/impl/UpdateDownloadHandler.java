package com.huluxia.controller.download.handler.impl;

import android.support.annotation.y;
import com.huluxia.controller.resource.bean.ResTaskInfo;
import com.huluxia.controller.resource.handler.impl.d;
import com.huluxia.framework.AppConfig;
import com.huluxia.framework.base.http.toolbox.download.DownloadRecord;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.UtilsApkPackage;
import java.io.File;

public class UpdateDownloadHandler extends d<ResTaskInfo> {
    private static final String TAG = "DownloadHandler";
    private com.huluxia.controller.resource.action.d action;
    private String mRenameFileName;

    public UpdateDownloadHandler(ResTaskInfo info) {
        super(info);
        this.mRenameFileName = info.filename;
    }

    public void onDownloadComplete(Object response, @y DownloadRecord record) {
        String newPath = record.dir + File.separator + this.mRenameFileName;
        String path = new File(record.dir, record.name).getAbsolutePath();
        if (path.endsWith(".apk")) {
            HLog.info(TAG, "file exist not update name twice", new Object[0]);
        } else {
            this.action = new com.huluxia.controller.resource.action.d(path, newPath);
            this.action.run();
            ResTaskInfo resTaskInfo = (ResTaskInfo) getInfo();
            resTaskInfo.filename = new File(newPath).getName();
            this.mReporter.updateName(((ResTaskInfo) getInfo()).url, ((ResTaskInfo) getInfo()).filename);
            path = newPath;
        }
        UtilsApkPackage.runInstallApp(AppConfig.getInstance().getAppContext(), path);
    }
}
