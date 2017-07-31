package com.huluxia.controller.resource.handler.impl;

import android.support.annotation.y;
import com.huluxia.controller.resource.action.d;
import com.huluxia.controller.resource.bean.ResTaskInfo;
import com.huluxia.controller.resource.bean.ResTaskInfo.State;
import com.huluxia.framework.DownloadMemCache;
import com.huluxia.framework.base.http.toolbox.download.DownloadRecord;
import com.huluxia.framework.base.http.toolbox.download.DownloadRecord$State;
import com.huluxia.framework.base.http.toolbox.error.ErrorCode;
import com.huluxia.framework.base.log.HLog;
import java.io.File;

/* compiled from: RenameHandler */
public abstract class p extends d<ResTaskInfo> implements h {
    private d action;

    public p(ResTaskInfo info) {
        super(info);
    }

    public void onDownloadComplete(Object response, @y DownloadRecord record) {
        String path = new File(record.dir, record.name).getAbsolutePath();
        String suffix = "." + getSuffix();
        HLog.info(this, "begin to rename file %s with suffix %s", new Object[]{path, suffix});
        if (path.endsWith(suffix)) {
            HLog.info(this, "download file exist, don't update name twice", new Object[0]);
        } else {
            String newPath = path + suffix;
            this.action = new d(path, newPath);
            this.action.run();
            ResTaskInfo resTaskInfo = (ResTaskInfo) getInfo();
            resTaskInfo.filename = new File(newPath).getName();
            this.mReporter.updateName(((ResTaskInfo) getInfo()).url, ((ResTaskInfo) getInfo()).filename);
            path = newPath;
        }
        aj(path);
        ((ResTaskInfo) getInfo()).state = State.SUCC.ordinal();
    }

    public void aj(String path) {
    }

    public boolean prepare() throws Exception {
        super.prepare();
        DownloadRecord record = DownloadMemCache.getInstance().getRecord(((ResTaskInfo) getInfo()).url);
        if (record == null) {
            HLog.info(this, "handler prepare record null, info %s", new Object[]{getInfo()});
            return false;
        } else if (!new File(new File(record.dir, record.name).getAbsolutePath()).exists()) {
            HLog.error(this, "download prepare but file delete before", new Object[0]);
            this.mReporter.deleteRecord(((ResTaskInfo) getInfo()).url);
            return false;
        } else if (record.state == DownloadRecord$State.COMPLETION.state) {
            HLog.info(this, "download complete", new Object[]{getInfo()});
            onResponse(null);
            return true;
        } else {
            if (record.error != -1 && ErrorCode.isRestart(record.error)) {
                HLog.error(this, "download prepare, download error before, need to restart", new Object[0]);
                this.mReporter.deleteRecord(((ResTaskInfo) getInfo()).url);
            }
            return false;
        }
    }
}
