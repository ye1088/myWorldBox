package com.MCWorld.db;

import com.MCWorld.controller.c;
import com.MCWorld.controller.resource.ResourceCtrl;
import com.MCWorld.controller.resource.bean.ResTaskInfo;
import com.MCWorld.controller.resource.bean.a;
import com.MCWorld.framework.BaseEvent;
import com.MCWorld.framework.DownloadMemCache;
import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.module.s;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* compiled from: ResInfoMemCache */
public class f {
    private static final String TAG = "ResInfoMemCache";
    private boolean loadReocrd;
    private CallbackHandler mCallback;
    private CallbackHandler mTaskCallback;
    private List<s> memcache;

    private f() {
        this.memcache = new ArrayList();
        this.loadReocrd = false;
        this.mTaskCallback = new CallbackHandler(this) {
            final /* synthetic */ f rL;

            {
                this.rL = this$0;
            }

            @MessageHandler(message = 266)
            public void onServiceRestart() {
                HLog.info(f.TAG, "service restart recv..........", new Object[0]);
                this.rL.eP();
            }
        };
        this.mCallback = new CallbackHandler(this) {
            final /* synthetic */ f rL;

            {
                this.rL = this$0;
            }

            @MessageHandler(message = 265)
            public void onDbOpen() {
                HLog.info(f.TAG, "db open recv", new Object[0]);
                this.rL.ensureLoadRecord();
            }
        };
        EventNotifyCenter.add(c.class, this.mTaskCallback);
        EventNotifyCenter.add(BaseEvent.class, this.mCallback);
    }

    public void uninit() {
    }

    public static f eO() {
        return a.eQ();
    }

    public synchronized void resetRecord(List<s> records) {
        if (!UtilsFunction.empty((Collection) records)) {
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
                    List<s> data = e.eM().g(new Object());
                    this.loadReocrd = true;
                    resetRecord(data);
                    return;
                } catch (Exception e) {
                    HLog.error(TAG, "sync load res info time %d, e %s", Integer.valueOf(i), e);
                    i++;
                }
            }
        }
    }

    public s s(long id) {
        synchronized (this.memcache) {
            for (s record : this.memcache) {
                if (id == record.appid) {
                    return record;
                }
            }
            return null;
        }
    }

    public s aD(String url) {
        if (UtilsFunction.empty((CharSequence) url)) {
            return null;
        }
        synchronized (this.memcache) {
            for (s record : this.memcache) {
                if (url.equals(record.downloadingUrl)) {
                    return record;
                }
            }
            return null;
        }
    }

    public void b(s info) {
        int index = this.memcache.indexOf(info);
        if (index < 0) {
            this.memcache.add(info);
        } else {
            ((s) this.memcache.get(index)).downloadingUrl = info.downloadingUrl;
        }
        e.eM().a(info, null);
    }

    public void t(long id) {
        synchronized (this.memcache) {
            s remove = new s();
            remove.appid = id;
            this.memcache.remove(remove);
        }
        e.eM().a(id, null);
    }

    public boolean c(s info) {
        int index = this.memcache.indexOf(info);
        if (index < 0) {
            this.memcache.add(info);
        } else {
            ((s) this.memcache.get(index)).downloadingUrl = info.downloadingUrl;
        }
        try {
            e.eM().a(info);
            return true;
        } catch (SQLException e) {
            HLog.error(TAG, "syncUpdateRecord id %d", Long.valueOf(info.appid));
            return false;
        }
    }

    public boolean u(long id) {
        HLog.verbose(TAG, "delete record", new Object[0]);
        synchronized (this.memcache) {
            s remove = new s();
            remove.appid = id;
            this.memcache.remove(remove);
        }
        try {
            e.eM().r(id);
            return true;
        } catch (SQLException e) {
            HLog.error(TAG, "syncDeleteRecord id %d", Long.valueOf(id));
            return false;
        }
    }

    private void eP() {
        Collection<DownloadRecord> downloadRecords = DownloadMemCache.getInstance().getMemcache();
        if (!UtilsFunction.empty((Collection) downloadRecords)) {
            for (DownloadRecord record : downloadRecords) {
                if (record.needRestart) {
                    s resDbInfo = aD(record.url);
                    if (resDbInfo == null) {
                        HLog.error(TAG, "download record not in res db", new Object[0]);
                    } else {
                        HLog.info(TAG, "task restart %s, db info %s", record, resDbInfo);
                        ResTaskInfo taskInfo = a.dM();
                        taskInfo.dir = record.dir;
                        taskInfo.url = resDbInfo.downloadingUrl;
                        taskInfo.mM = resDbInfo.downFileType;
                        taskInfo.mS_appTitle = resDbInfo.apptitle;
                        taskInfo.filename = record.name;
                        taskInfo.mU = false;
                        taskInfo.mV_fileName = resDbInfo.getFinalFileName();
                        taskInfo.na = true;
                        taskInfo.nb = false;
                        ResourceCtrl.getInstance().addTask(taskInfo);
                        record.needRestart = false;
                    }
                }
            }
        }
    }
}
