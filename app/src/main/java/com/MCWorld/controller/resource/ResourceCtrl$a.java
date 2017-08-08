package com.MCWorld.controller.resource;

import com.MCWorld.controller.resource.bean.ResTaskInfo;
import com.MCWorld.controller.resource.bean.ResTaskInfo.State;
import com.MCWorld.controller.resource.bean.a;
import com.MCWorld.controller.resource.zip.b;
import com.MCWorld.controller.resource.zip.c;
import com.MCWorld.controller.resource.zip.d;
import com.MCWorld.framework.AppConfig;
import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord;
import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord$State;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.UtilsApkPackage;
import java.io.File;

public class ResourceCtrl$a {
    public static ResTaskInfo a(DownloadRecord record, int resourceType) {
        ResTaskInfo info = a.dM();
        info.url = record.url;
        info.dir = record.dir;
        info.filename = record.name;
        info.mM = resourceType;
        info.mN = record;
        if (new File(record.dir, record.name).exists()) {
            if (record.error > 0) {
                info.state = State.DOWNLOAD_ERROR.ordinal();
                if (record.error == 35) {
                    info.state = State.XOR_ERROR.ordinal();
                }
            } else if (record.pause) {
                info.state = State.DOWNLOAD_PAUSE.ordinal();
            } else if (record.state == DownloadRecord$State.INIT.state) {
                info.state = State.DOWNLOAD_START.ordinal();
            } else if (record.state == DownloadRecord$State.DOWNLOADING.state) {
                info.state = State.DOWNLOAD_PROGRESS.ordinal();
            } else if (record.state == DownloadRecord$State.COMPLETION.state) {
                info.state = State.DOWNLOAD_COMPLETE.ordinal();
            }
        } else if (record.error == 34) {
            info.state = State.DOWNLOAD_ERROR.ordinal();
        } else {
            info.state = State.FILE_DELETE.ordinal();
        }
        if (info.state == State.DOWNLOAD_COMPLETE.ordinal()) {
            if (resourceType == 5) {
                if (new File(record.dir, record.name).exists()) {
                    HLog.debug("ResourceCtrl", "hpk download complete, but not unzip...", new Object[0]);
                    b hpkFileList = c.ea().aq(record.url);
                    File apkFile = new File(d.au(record.url));
                    if (!apkFile.exists()) {
                        info.state = State.UNZIP_NOT_START.ordinal();
                    } else if (UtilsApkPackage.isApkInstalled(AppConfig.getInstance().getAppContext(), apkFile)) {
                        info.state = State.SUCC.ordinal();
                    } else if (hpkFileList == null || !hpkFileList.fileExists) {
                        info.state = State.UNZIP_NOT_START.ordinal();
                    } else {
                        info.state = State.SUCC.ordinal();
                        info.mR = apkFile.getAbsolutePath();
                    }
                } else {
                    HLog.debug("ResourceCtrl", "hpk download complete, but delete", new Object[0]);
                }
            } else if (new File(info.dir, info.filename).exists()) {
                info.state = State.SUCC.ordinal();
            }
        }
        return info;
    }
}
