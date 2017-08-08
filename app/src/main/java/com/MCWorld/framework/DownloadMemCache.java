package com.MCWorld.framework;

import com.MCWorld.framework.base.async.AsyncTaskCenter;
import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.framework.base.utils.UtilsFunction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DownloadMemCache {
    private static final String TAG = "DownloadMemCache";
    private static DownloadMemCache instance;
    private volatile boolean loadReocrd = false;
    private CallbackHandler mCallback = new CallbackHandler() {
        @MessageHandler(message = 262)
        public void onRecvSwithKey(boolean succ, String newUrl, String oldUrl) {
            if (!succ) {
                DownloadMemCache.this.switchMemRecordKey(oldUrl, newUrl);
            }
            EventNotifyCenter.notifyEventUiThread(BaseEvent.class, 263, Boolean.valueOf(succ), newUrl, oldUrl);
        }

        @MessageHandler(message = 265)
        public void onDbOpen() {
            HLog.info(DownloadMemCache.TAG, "db open recv", new Object[0]);
            DownloadMemCache.this.ensureLoadRecord();
        }
    };
    private List<DownloadRecord> memcache = new ArrayList();

    private DownloadMemCache() {
        EventNotifyCenter.add(BaseEvent.class, this.mCallback);
    }

    public void uninit() {
        this.loadReocrd = false;
        EventNotifyCenter.remove(this.mCallback);
        instance = null;
    }

    public static synchronized DownloadMemCache getInstance() {
        DownloadMemCache downloadMemCache;
        synchronized (DownloadMemCache.class) {
            if (instance == null) {
                instance = new DownloadMemCache();
            }
            downloadMemCache = instance;
        }
        return downloadMemCache;
    }

    private void ensureLoadRecord() {
        if (!this.loadReocrd) {
            HLog.info(TAG, "not ever load record before,try....", new Object[0]);
            AsyncTaskCenter.getInstance().executeSingleThread(new 2(this), 0);
        }
    }

    public synchronized void resetRecord(List<DownloadRecord> records) {
        int i = 0;
        synchronized (this) {
            String str = TAG;
            String str2 = "reset record size %d";
            Object[] objArr = new Object[1];
            if (!UtilsFunction.empty((Collection) records)) {
                i = records.size();
            }
            objArr[0] = Integer.valueOf(i);
            HLog.info(str, str2, objArr);
            if (!UtilsFunction.empty((Collection) records)) {
                this.memcache = records;
                EventNotifyCenter.notifyEventUiThread(BaseEvent.class, 261, new Object[0]);
            }
        }
    }

    public DownloadRecord getRecord(String url) {
        DownloadRecord downloadRecord = null;
        if (!UtilsFunction.empty((CharSequence) url)) {
            synchronized (this.memcache) {
                for (DownloadRecord record : this.memcache) {
                    if (url.equals(record.url)) {
                        downloadRecord = record.clone();
                        break;
                    }
                }
            }
        }
        return downloadRecord;
    }

    public void deleteRecord(String url) {
        if (!UtilsFunction.empty((CharSequence) url)) {
            synchronized (this.memcache) {
                DownloadRecord remove = new DownloadRecord();
                remove.url = url;
                HLog.verbose(TAG, "delete record url " + url + ", delete " + this.memcache.remove(remove), new Object[0]);
            }
            BaseDataDb.getInstance().asyncDelRecord(url);
        }
    }

    public boolean syncSwitchRecordKey(String newUrl, String oldUrl) {
        if (UtilsFunction.empty((CharSequence) newUrl) || UtilsFunction.empty((CharSequence) oldUrl) || switchMemRecordKey(newUrl, oldUrl) == null) {
            return false;
        }
        return true;
    }

    private DownloadRecord switchMemRecordKey(String newUrl, String oldUrl) {
        DownloadRecord ret;
        DownloadRecord exist = null;
        synchronized (this.memcache) {
            for (DownloadRecord record : this.memcache) {
                if (oldUrl.equals(record.url)) {
                    exist = record;
                    break;
                }
            }
            if (exist != null) {
                ret = exist.clone();
                exist.url = newUrl;
                ret.url = oldUrl;
            } else {
                ret = null;
            }
        }
        return ret;
    }

    public void updateRecordProgress(DownloadRecord record) {
        synchronized (this.memcache) {
            int index = this.memcache.indexOf(record);
            if (index < 0) {
                this.memcache.add(record.clone());
            } else {
                DownloadRecord memRecord = (DownloadRecord) this.memcache.get(index);
                memRecord.progress = record.progress;
                memRecord.total = record.total;
                memRecord.state = record.state;
                memRecord.pause = record.pause;
                memRecord.error = record.error;
            }
        }
    }

    public void updateRecordPause(DownloadRecord record) {
        synchronized (this.memcache) {
            int index = this.memcache.indexOf(record);
            if (index >= 0) {
                DownloadRecord memRecord = (DownloadRecord) this.memcache.get(index);
                memRecord.error = record.error;
                memRecord.pause = record.pause;
            } else {
                HLog.info(TAG, "update record pause no memory cache miss, " + record, new Object[0]);
            }
        }
    }

    public void updateRecordError(DownloadRecord record) {
        synchronized (this.memcache) {
            int index = this.memcache.indexOf(record);
            if (index < 0) {
                this.memcache.add(record.clone());
            } else {
                DownloadRecord memRecord = (DownloadRecord) this.memcache.get(index);
                memRecord.error = record.error;
                memRecord.pause = record.pause;
            }
        }
    }

    public void updateRecordHttpStatusCode(DownloadRecord record) {
        synchronized (this.memcache) {
            int index = this.memcache.indexOf(record);
            if (index < 0) {
                this.memcache.add(record.clone());
            } else {
                ((DownloadRecord) this.memcache.get(index)).httpstatuscode = record.httpstatuscode;
            }
        }
    }

    public void updateRecordState(DownloadRecord record, boolean createIfNotExists) {
        synchronized (this.memcache) {
            int index = this.memcache.indexOf(record);
            if (index >= 0) {
                ((DownloadRecord) this.memcache.get(index)).state = record.state;
            } else if (createIfNotExists) {
                this.memcache.add(record.clone());
            }
        }
    }

    public void updateRecordName(String url, String name) {
        HLog.verbose(TAG, "update recrod name %s", name);
        synchronized (this.memcache) {
            DownloadRecord record = new DownloadRecord();
            record.url = url;
            int index = this.memcache.indexOf(record);
            if (index >= 0) {
                HLog.verbose(TAG, "update recrod name %s, url %s", name, url);
                ((DownloadRecord) this.memcache.get(index)).name = name;
            }
        }
    }

    public void updateRecordRecordUrlAndName(DownloadRecord oldRecord, String newUrl, String name) {
        HLog.verbose(TAG, "update updateRecordRecordUrlAndName oldRecord %s, url %s, name %s", oldRecord, newUrl, name);
        synchronized (this.memcache) {
            int index = this.memcache.indexOf(oldRecord);
            if (index >= 0) {
                HLog.verbose(TAG, "updateRecordRecordUrlAndName oldRecord %s, url %s, name %s", oldRecord, newUrl, name);
                DownloadRecord memRecord = (DownloadRecord) this.memcache.get(index);
                memRecord.name = name;
                memRecord.url = newUrl;
            }
        }
    }

    public void updateRecordEtag(DownloadRecord record) {
        synchronized (this.memcache) {
            int index = this.memcache.indexOf(record);
            if (index >= 0) {
                HLog.verbose(TAG, "updateRecordEtag record %s", record);
                ((DownloadRecord) this.memcache.get(index)).noIntegrity = record.noIntegrity;
            }
        }
    }

    public List<DownloadRecord> getMemcache() {
        List<DownloadRecord> downloadRecords = new ArrayList();
        synchronized (this.memcache) {
            for (DownloadRecord record : this.memcache) {
                downloadRecords.add(record.clone());
            }
        }
        return downloadRecords;
    }

    public void updateRecordNeedRestart(DownloadRecord record, boolean needed) {
        HLog.verbose(TAG, "update need restart needed " + needed, new Object[0]);
        synchronized (this.memcache) {
            int index = this.memcache.indexOf(record);
            if (index >= 0) {
                HLog.verbose(TAG, "updateRecordNeedRestart record %s", record);
                ((DownloadRecord) this.memcache.get(index)).needRestart = needed;
            }
        }
    }
}
