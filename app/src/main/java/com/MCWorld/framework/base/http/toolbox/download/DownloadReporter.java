package com.MCWorld.framework.base.http.toolbox.download;

import android.os.Handler;
import android.os.Looper;
import com.MCWorld.framework.BaseDataDb;
import com.MCWorld.framework.BaseEvent;
import com.MCWorld.framework.DownloadMemCache;
import com.MCWorld.framework.base.http.toolbox.RequestReporter;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import java.sql.SQLException;

public class DownloadReporter implements RequestReporter {
    private static final String TAG = "DownloadReporter";
    private Handler uiHandler = new Handler(Looper.getMainLooper());

    private void postUiEvent() {
        this.uiHandler.post(new Runnable() {
            public void run() {
                EventNotifyCenter.notifyEvent(BaseEvent.class, 261, new Object[0]);
            }
        });
    }

    public void reportProgress(DownloadRecord record) {
        try {
            BaseDataDb.getInstance().syncUpdateRecordProgress(record);
            DownloadMemCache.getInstance().updateRecordProgress(record);
            postUiEvent();
        } catch (SQLException e) {
            HLog.error(this, "report download progress e %s", new Object[]{e.getMessage()});
        } catch (IllegalArgumentException e2) {
            HLog.error(this, "report download progress e %s", new Object[]{e2.getMessage()});
        }
    }

    public void reportPause(DownloadRecord record) {
        try {
            BaseDataDb.getInstance().syncUpdateRecordPause(record);
            DownloadMemCache.getInstance().updateRecordPause(record);
            postUiEvent();
        } catch (SQLException e) {
            HLog.error(this, "report pause e %s", new Object[]{e.getMessage()});
        } catch (IllegalArgumentException e2) {
            HLog.error(this, "report pause e %s", new Object[]{e2.getMessage()});
        }
    }

    public void asyncReportPause(DownloadRecord record) {
        try {
            BaseDataDb.getInstance().asyncUpdateRecordPause(record, null);
            DownloadMemCache.getInstance().updateRecordPause(record);
            postUiEvent();
        } catch (SQLException e) {
            HLog.error(this, "report pause e %s", new Object[]{e.getMessage()});
        } catch (IllegalArgumentException e2) {
            HLog.error(this, "report pause e %s", new Object[]{e2.getMessage()});
        }
    }

    public void reportError(DownloadRecord record) {
        try {
            BaseDataDb.getInstance().syncUpdateRecordError(record);
            DownloadMemCache.getInstance().updateRecordError(record);
            postUiEvent();
        } catch (SQLException e) {
            HLog.error(this, "report error e %s", new Object[]{e.getMessage()});
        } catch (IllegalArgumentException e2) {
            HLog.error(this, "report pause e %s", new Object[]{e2.getMessage()});
        }
    }

    public void reportHttpStatusCode(DownloadRecord record) {
        try {
            BaseDataDb.getInstance().syncUpdateRecordHttpStatusCode(record);
            DownloadMemCache.getInstance().updateRecordHttpStatusCode(record);
            postUiEvent();
        } catch (SQLException e) {
            HLog.error(this, "report http status code e %s", new Object[]{e.getMessage()});
        } catch (IllegalArgumentException e2) {
            HLog.error(this, "report pause e %s", new Object[]{e2.getMessage()});
        }
    }

    public void reportState(DownloadRecord record) {
        try {
            BaseDataDb.getInstance().syncUpdateRecordState(record, false);
            DownloadMemCache.getInstance().updateRecordState(record, false);
            postUiEvent();
        } catch (SQLException e) {
            HLog.error(this, "report state e %s", new Object[]{e.getMessage()});
        } catch (IllegalArgumentException e2) {
            HLog.error(this, "report pause e %s", new Object[]{e2.getMessage()});
        }
    }

    public void reportPrepare(DownloadRecord record) {
        try {
            BaseDataDb.getInstance().syncUpdateRecordState(record, true);
            DownloadMemCache.getInstance().updateRecordState(record, true);
            postUiEvent();
        } catch (SQLException e) {
            HLog.error(this, "report state e %s", new Object[]{e.getMessage()});
        } catch (IllegalArgumentException e2) {
            HLog.error(this, "report pause e %s", new Object[]{e2.getMessage()});
        }
    }

    public void resetUrlAndName(DownloadRecord oldRecord, String newUrl, String name) {
        if (oldRecord != null) {
            try {
                BaseDataDb.getInstance().syncUpdateRecordUrlAndName(oldRecord.clone(), newUrl, name);
                DownloadMemCache.getInstance().updateRecordRecordUrlAndName(oldRecord, newUrl, name);
            } catch (SQLException e) {
                HLog.error(this, "resetUrlAndName state e %s, newUrl %s, name %s", new Object[]{e.getMessage(), newUrl, name});
            } catch (IllegalArgumentException e2) {
                HLog.error(this, "resetUrlAndName pause e %s, newUrl %s, name %s", new Object[]{e2.getMessage(), newUrl, name});
            }
        }
    }

    public void reportETag(DownloadRecord record) {
        try {
            BaseDataDb.getInstance().syncUpdateRecordETag(record);
            DownloadMemCache.getInstance().updateRecordEtag(record);
        } catch (SQLException e) {
            HLog.error(this, "reportETag state e %s, record %s", new Object[]{e.getMessage(), record});
        } catch (IllegalArgumentException e2) {
            HLog.error(this, "reportETag pause e %s, record %s", new Object[]{e2.getMessage(), record});
        }
    }

    public void updateName(String url, String newName) {
        try {
            BaseDataDb.getInstance().syncUpdateFileName(url, newName);
            DownloadMemCache.getInstance().updateRecordName(url, newName);
        } catch (SQLException e) {
            HLog.error(this, "reportETag state e %s, url %s", new Object[]{e.getMessage(), url});
        } catch (IllegalArgumentException e2) {
            HLog.error(this, "reportETag pause e %s, url %s", new Object[]{e2.getMessage(), url});
        }
    }

    public void deleteRecord(String url) {
        try {
            if (Looper.getMainLooper().getThread().getId() == Thread.currentThread().getId()) {
                BaseDataDb.getInstance().asyncDelRecord(url);
            } else {
                BaseDataDb.getInstance().syncDelRecord(url);
            }
            DownloadMemCache.getInstance().deleteRecord(url);
        } catch (Exception e) {
            HLog.error(this, "deleteRecord state e %s, url %s", new Object[]{e.getMessage(), url});
        }
    }
}
