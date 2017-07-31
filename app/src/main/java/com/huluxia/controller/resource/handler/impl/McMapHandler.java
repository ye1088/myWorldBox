package com.huluxia.controller.resource.handler.impl;

import android.support.annotation.y;
import com.huluxia.controller.c;
import com.huluxia.controller.resource.action.UnzipMapAction;
import com.huluxia.controller.resource.action.d;
import com.huluxia.controller.resource.bean.ResTaskInfo.State;
import com.huluxia.framework.DownloadMemCache;
import com.huluxia.framework.base.http.module.ProgressInfo;
import com.huluxia.framework.base.http.toolbox.download.DownloadRecord;
import com.huluxia.framework.base.http.toolbox.download.DownloadRecord$State;
import com.huluxia.framework.base.http.toolbox.error.ErrorCode;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.utils.UtilsFile;
import com.huluxia.utils.bc.a;
import java.io.File;

public class McMapHandler extends d<q> implements a {
    private static final String MAP_SUFFIX = ".zip";
    private static final String TAG = "McMapHandler";
    private Object LOCK = new Object();
    private d action;
    private long lastUnzipTime = 0;
    private String renameFileName;
    private String unzipDirName;
    public UnzipMapAction unzipMapAction;

    public McMapHandler(q info) {
        super(info);
        this.renameFileName = info.filename;
        this.unzipDirName = info.mR;
    }

    protected void doUnzip(String zipPath) {
        String dstPath = "";
        File f = new File(zipPath);
        if (zipPath.endsWith(".zip")) {
            dstPath = f.getParent();
            this.unzipMapAction = new UnzipMapAction(f, this.unzipDirName, this);
            EventNotifyCenter.notifyEventUiThread(c.class, 260, new Object[]{((q) getInfo()).url});
            if (this.unzipMapAction.run()) {
                ((q) getInfo()).state = State.SUCC.ordinal();
            } else {
                ((q) getInfo()).state = State.ERROR.ordinal();
            }
            EventNotifyCenter.notifyEvent(c.class, 262, new Object[]{((q) getInfo()).url});
        }
    }

    public boolean prepare() throws Exception {
        super.prepare();
        DownloadRecord record = DownloadMemCache.getInstance().getRecord(((q) getInfo()).url);
        if (record == null) {
            HLog.info(this, "hpk handler prepare record null, info %s", new Object[]{getInfo()});
            return false;
        } else if (!new File(new File(record.dir, record.name).getAbsolutePath()).exists()) {
            HLog.error(this, "hpk download prepare but file delete before", new Object[0]);
            this.mReporter.deleteRecord(((q) getInfo()).url);
            return false;
        } else if (record.state == DownloadRecord$State.COMPLETION.state) {
            HLog.info(this, "hpk download complete", new Object[]{getInfo()});
            onResponse(null);
            return true;
        } else {
            if (record.error != -1 && ErrorCode.isRestart(record.error)) {
                HLog.error(this, "download prepare, download error before, need to restart", new Object[0]);
                this.mReporter.deleteRecord(((q) getInfo()).url);
            }
            return false;
        }
    }

    public void onDownloadComplete(Object response, @y DownloadRecord record) {
        String newPath = record.dir + File.separator + this.renameFileName + ".zip";
        String path = new File(record.dir, record.name).getAbsolutePath();
        if (UtilsFile.isExist(newPath)) {
            HLog.info(TAG, "map file exist not update name twice", new Object[0]);
            path = newPath;
        } else {
            this.action = new d(path, newPath);
            this.action.run();
            File file = new File(newPath);
            path = file.getAbsolutePath();
            ((q) getInfo()).filename = file.getName();
            this.mReporter.updateName(((q) getInfo()).url, ((q) getInfo()).filename);
        }
        doUnzip(path);
    }

    public void OnProgessCallback(long progress, long length) {
        ((q) getInfo()).mP = new ProgressInfo(length, progress, 0.0f);
        ((q) getInfo()).state = State.UNZIP_PROGRESSING.ordinal();
        if (System.currentTimeMillis() - this.lastUnzipTime > 500) {
            this.lastUnzipTime = System.currentTimeMillis();
            EventNotifyCenter.notifyEventUiThread(c.class, 261, new Object[]{((q) getInfo()).url});
        }
    }
}
