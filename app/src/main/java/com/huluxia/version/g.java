package com.huluxia.version;

import com.huluxia.controller.c;
import com.huluxia.controller.resource.ResourceCtrl;
import com.huluxia.controller.resource.bean.ResTaskInfo;
import com.huluxia.framework.BaseEvent;
import com.huluxia.framework.DownloadMemCache;
import com.huluxia.framework.base.http.toolbox.download.DownloadRecord;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.utils.UtilsFunction;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/* compiled from: VersionMemCache */
public class g {
    private static final String TAG = "VersionMemCache";
    private boolean loadReocrd;
    private CallbackHandler mCallback;
    private CallbackHandler mTaskCallback;
    private List<d> memcache;

    /* compiled from: VersionMemCache */
    private static class a {
        private static final g boz = new g();

        private a() {
        }
    }

    private g() {
        this.memcache = new ArrayList();
        this.loadReocrd = false;
        this.mTaskCallback = new 1(this);
        this.mCallback = new 2(this);
        EventNotifyCenter.add(c.class, this.mTaskCallback);
        EventNotifyCenter.add(BaseEvent.class, this.mCallback);
    }

    public void uninit() {
    }

    public static g MS() {
        return a.boz;
    }

    public synchronized void resetRecord(List<d> records) {
        if (!UtilsFunction.empty(records)) {
            this.memcache = records;
            EventNotifyCenter.notifyEventUiThread(BaseEvent.class, 261, new Object[0]);
        }
    }

    private void ensureLoadRecord() {
        if (!this.loadReocrd) {
            HLog.info(TAG, "not ever load res record before,try....", new Object[0]);
            int i = 0;
            while (i < 10) {
                try {
                    List<d> data = b.MR().aw(new Object());
                    this.loadReocrd = true;
                    resetRecord(data);
                    return;
                } catch (Exception e) {
                    HLog.error(TAG, "sync load res info time %d, e %s", new Object[]{Integer.valueOf(i), e});
                    i++;
                }
            }
        }
    }

    public d gE(String url) {
        if (UtilsFunction.empty(url)) {
            return null;
        }
        synchronized (this.memcache) {
            for (d record : this.memcache) {
                if (url.equals(record.url)) {
                    return record;
                }
            }
            return null;
        }
    }

    public void b(d info) {
        int index = this.memcache.indexOf(info);
        if (index < 0) {
            this.memcache.add(info);
        } else {
            d memRecord = (d) this.memcache.get(index);
            memRecord.url = info.url;
            memRecord.md5 = info.md5;
            memRecord.restype = info.restype;
            memRecord.downloadStatus = info.downloadStatus;
        }
        b.MR().a(info, null);
    }

    public void deleteRecord(String url) {
        synchronized (this.memcache) {
            d remove = new d();
            remove.url = url;
            this.memcache.remove(remove);
        }
        b.MR().l(url, null);
    }

    public boolean c(d info) {
        int index = this.memcache.indexOf(info);
        if (index < 0) {
            this.memcache.add(info);
        } else {
            ((d) this.memcache.get(index)).url = info.url;
        }
        try {
            b.MR().a(info);
            return true;
        } catch (SQLException e) {
            HLog.error(TAG, "syncUpdateRecord id %d", new Object[]{info.url});
            return false;
        }
    }

    public boolean gF(String url) {
        HLog.verbose(TAG, "delete record", new Object[0]);
        synchronized (this.memcache) {
            d remove = new d();
            remove.url = url;
            this.memcache.remove(remove);
        }
        try {
            b.MR().syncDelRecord(url);
            return true;
        } catch (SQLException e) {
            HLog.error(TAG, "syncDeleteRecord id %d", new Object[]{url});
            return false;
        }
    }

    private void eP() {
        List<DownloadRecord> downloadRecords = DownloadMemCache.getInstance().getMemcache();
        if (!UtilsFunction.empty(downloadRecords)) {
            for (DownloadRecord record : downloadRecords) {
                if (record.needRestart) {
                    d updaterDbInfo = gE(record.url);
                    if (updaterDbInfo == null) {
                        HLog.error(TAG, "download record not in res db", new Object[0]);
                    } else {
                        HLog.info(TAG, "task restart %s, db info %s", new Object[]{record, updaterDbInfo});
                        ResTaskInfo taskInfo = com.huluxia.controller.resource.bean.a.dM();
                        taskInfo.dir = record.dir;
                        taskInfo.url = updaterDbInfo.url;
                        taskInfo.mM = 22;
                        taskInfo.mS_appTitle = updaterDbInfo.name;
                        taskInfo.filename = record.name;
                        taskInfo.mU = false;
                        taskInfo.na = true;
                        taskInfo.nb = false;
                        ResourceCtrl.getInstance().addTask(taskInfo);
                        DownloadMemCache.getInstance().updateRecordNeedRestart(record, false);
                    }
                }
            }
        }
    }
}
