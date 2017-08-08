package com.MCWorld.framework;

import com.MCWorld.framework.base.db.AbstractBaseDb;
import com.MCWorld.framework.base.db.DbCommand;
import com.MCWorld.framework.base.db.DbCommand.Callback;
import com.MCWorld.framework.base.db.DbContext;
import com.MCWorld.framework.base.db.DbError;
import com.MCWorld.framework.base.db.DbResult;
import com.MCWorld.framework.base.db.DbSyncCommand;
import com.MCWorld.framework.base.db.DbSyncCommand.SyncRunnable;
import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord;
import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord$State;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaseDataDb extends AbstractBaseDb {
    private static final String TAG = "BaseDataDb";
    private static BaseDataDb baseDb;

    public static synchronized BaseDataDb getInstance() {
        BaseDataDb baseDataDb;
        synchronized (BaseDataDb.class) {
            if (baseDb == null) {
                baseDb = new BaseDataDb();
                baseDb.setDbContext(BaseDbManager.getDataContext());
            }
            baseDataDb = baseDb;
        }
        return baseDataDb;
    }

    public void setDbContext(DbContext ctx) {
        super.setDbContext(ctx);
    }

    public DownloadRecord syncFetchRecord(final String url) throws SQLException {
        return (DownloadRecord) syncCommand(new DbSyncCommand(new SyncRunnable() {
            protected void sync() throws Exception {
                getResult().resultObject = (DownloadRecord) BaseDataDb.this.getDao(DownloadRecord.class).queryForId(url);
            }
        })).resultObject;
    }

    public void syncDelRecord(final String url) {
        syncCommand(new DbSyncCommand(new SyncRunnable() {
            protected void sync() throws Exception {
                BaseDataDb.this.getDao(DownloadRecord.class).deleteById(url);
            }
        }));
    }

    public void asyncDelRecord(final String url) {
        sendCommand(new DbCommand() {
            public void execute() throws Exception {
                BaseDataDb.this.syncDelRecord(url);
            }

            public void onSucceed(Object obj) {
                EventNotifyCenter.notifyEventUiThread(BaseEvent.class, 264, new Object[]{Boolean.valueOf(true), url});
            }

            public void onFail(DbError error) {
                EventNotifyCenter.notifyEventUiThread(BaseEvent.class, 264, new Object[]{Boolean.valueOf(false), url});
                HLog.error(BaseDataDb.TAG, "asyncDelRecord error %s", new Object[]{error.toString()});
            }
        });
    }

    public void syncFullyUpdateRecord(final DownloadRecord record) throws SQLException {
        syncCommand(new DbSyncCommand(new SyncRunnable() {
            protected void sync() throws Exception {
                BaseDataDb.this.getDao(DownloadRecord.class).createOrUpdate(record);
            }
        }));
    }

    public void syncUpdateRecordProgress(final DownloadRecord record) throws SQLException, IllegalArgumentException {
        syncCommand(new DbSyncCommand(new SyncRunnable() {
            protected void sync() throws Exception {
                Dao<DownloadRecord, String> dao = BaseDataDb.this.getDao(DownloadRecord.class);
                dao.createIfNotExists(record);
                UpdateBuilder<DownloadRecord, String> updateBuilder = dao.updateBuilder();
                updateBuilder.updateColumnValue("progress", Long.valueOf(record.progress));
                updateBuilder.updateColumnValue(DownloadRecord.COLUMN_TOTAL, Long.valueOf(record.total));
                updateBuilder.updateColumnValue(DownloadRecord.COLUMN_STATE, Integer.valueOf(record.state));
                updateBuilder.updateColumnValue(DownloadRecord.COLUMN_PAUSE, Boolean.valueOf(record.pause));
                updateBuilder.updateColumnValue(DownloadRecord.COLUMN_ERROR, Integer.valueOf(record.error));
                updateBuilder.where().eq("url", record.url);
                updateBuilder.update();
            }
        }));
    }

    public void syncUpdateRecordPause(final DownloadRecord record) throws SQLException, IllegalArgumentException {
        syncCommand(new DbSyncCommand(new SyncRunnable() {
            protected void sync() throws Exception {
                UpdateBuilder<DownloadRecord, String> updateBuilder = BaseDataDb.this.getDao(DownloadRecord.class).updateBuilder();
                updateBuilder.updateColumnValue(DownloadRecord.COLUMN_PAUSE, Boolean.valueOf(record.pause));
                updateBuilder.updateColumnValue(DownloadRecord.COLUMN_ERROR, Integer.valueOf(record.error));
                updateBuilder.where().eq("url", record.url);
                updateBuilder.update();
            }
        }));
    }

    public void asyncUpdateRecordPause(final DownloadRecord record, final Callback callback) throws SQLException, IllegalArgumentException {
        sendCommand(new DbCommand() {
            public void execute() throws Exception {
                UpdateBuilder<DownloadRecord, String> updateBuilder = BaseDataDb.this.getDao(DownloadRecord.class).updateBuilder();
                updateBuilder.updateColumnValue(DownloadRecord.COLUMN_PAUSE, Boolean.valueOf(record.pause));
                updateBuilder.updateColumnValue(DownloadRecord.COLUMN_ERROR, Integer.valueOf(record.error));
                updateBuilder.where().eq("url", record.url);
                updateBuilder.update();
            }

            public void onSucceed(Object obj) {
                if (callback != null) {
                    callback.onSucceed(obj);
                }
            }

            public void onFail(DbError error) {
                if (callback != null) {
                    callback.onFail(error);
                }
            }
        });
    }

    public void syncUpdateRecordHttpStatusCode(final DownloadRecord record) throws SQLException, IllegalArgumentException {
        syncCommand(new DbSyncCommand(new SyncRunnable() {
            protected void sync() throws Exception {
                Dao<DownloadRecord, String> dao = BaseDataDb.this.getDao(DownloadRecord.class);
                dao.createIfNotExists(record);
                UpdateBuilder<DownloadRecord, String> updateBuilder = dao.updateBuilder();
                updateBuilder.updateColumnValue(DownloadRecord.COLUMN_HTTP_STATUS_CODE, Integer.valueOf(record.httpstatuscode));
                updateBuilder.where().eq("url", record.url);
                updateBuilder.update();
            }
        }));
    }

    public void syncUpdateRecordError(final DownloadRecord record) throws SQLException, IllegalArgumentException {
        syncCommand(new DbSyncCommand(new SyncRunnable() {
            protected void sync() throws Exception {
                Dao<DownloadRecord, String> dao = BaseDataDb.this.getDao(DownloadRecord.class);
                dao.createIfNotExists(record);
                UpdateBuilder<DownloadRecord, String> updateBuilder = dao.updateBuilder();
                updateBuilder.updateColumnValue(DownloadRecord.COLUMN_ERROR, Integer.valueOf(record.error));
                updateBuilder.updateColumnValue(DownloadRecord.COLUMN_PAUSE, Boolean.valueOf(record.pause));
                updateBuilder.where().eq("url", record.url);
                updateBuilder.update();
            }
        }));
    }

    public void syncUpdateFileName(final String url, final String name) throws SQLException, IllegalArgumentException {
        syncCommand(new DbSyncCommand(new SyncRunnable() {
            protected void sync() throws Exception {
                UpdateBuilder<DownloadRecord, String> updateBuilder = BaseDataDb.this.getDao(DownloadRecord.class).updateBuilder();
                updateBuilder.updateColumnValue("name", name);
                updateBuilder.where().eq("url", url);
                updateBuilder.update();
            }
        }));
    }

    public void asyncReloadDownloadRecord() {
        sendCommand(new DbCommand() {
            public void execute() throws Exception {
                List<DownloadRecord> data = BaseDataDb.this.getDao(DownloadRecord.class).queryForAll();
                List<DownloadRecord> remove = new ArrayList();
                for (DownloadRecord record : data) {
                    if (!new File(record.dir, record.name).exists()) {
                        HLog.error(BaseDataDb.TAG, "asyncReloadDownloadRecord file not exist so delete", new Object[0]);
                    } else if (!(record.pause || AppConfig.getInstance().getResCtrlHttpMgr() == null || AppConfig.getInstance().getResCtrlHttpMgr().findDownloadReq(record.url) != null)) {
                        HLog.error(BaseDataDb.TAG, "task is not pause state but no request running currently , so must be something wrong happens before", new Object[0]);
                        record.pause = true;
                        record.resetError();
                        BaseDataDb.this.syncUpdateRecordPause(record);
                    }
                }
                data.removeAll(remove);
                this.result.resultObject = data;
            }

            public void onSucceed(Object obj) {
                DownloadMemCache.getInstance().resetRecord((List) obj);
            }

            public void onFail(DbError error) {
            }
        });
    }

    public List<DownloadRecord> syncReloadDownloadRecord() throws Exception {
        DbSyncCommand command = new DbSyncCommand(new SyncRunnable() {
            protected void sync() throws Exception {
                List<DownloadRecord> data = BaseDataDb.this.getDao(DownloadRecord.class).queryForAll();
                List<DownloadRecord> remove = new ArrayList();
                for (DownloadRecord record : data) {
                    if (!new File(record.dir, record.name).exists()) {
                        HLog.error(BaseDataDb.TAG, "syncReloadDownloadRecord file not exist so delete", new Object[0]);
                    } else if (!record.pause && record.state == DownloadRecord$State.DOWNLOADING.state) {
                        HLog.error(BaseDataDb.TAG, "syncReloadDownloadRecord task is not pause state but no request running currently , so must be something wrong happens before", new Object[0]);
                        if (AppConfig.getInstance().getResCtrlHttpMgr() == null) {
                            record.pause = true;
                            record.resetError();
                            BaseDataDb.this.syncUpdateRecordPause(record);
                        } else if (AppConfig.getInstance().getResCtrlHttpMgr().findDownloadReq(record.url) == null) {
                            record.pause = true;
                            record.resetError();
                            BaseDataDb.this.syncUpdateRecordPause(record);
                        }
                        record.needRestart = true;
                    }
                }
                data.removeAll(remove);
                getResult().resultObject = data;
            }
        });
        List<DownloadRecord> result = new ArrayList();
        DbResult dbResult = syncCommand(command);
        if (dbResult.resultObject != null) {
            result.addAll((List) dbResult.resultObject);
        }
        return result;
    }

    public void syncUpdateRecordState(final DownloadRecord record, final boolean createIfNotExists) throws SQLException, IllegalArgumentException {
        syncCommand(new DbSyncCommand(new SyncRunnable() {
            protected void sync() throws Exception {
                Dao<DownloadRecord, String> dao = BaseDataDb.this.getDao(DownloadRecord.class);
                if (createIfNotExists) {
                    dao.createIfNotExists(record);
                }
                UpdateBuilder<DownloadRecord, String> updateBuilder = dao.updateBuilder();
                updateBuilder.updateColumnValue(DownloadRecord.COLUMN_STATE, Integer.valueOf(record.state));
                updateBuilder.where().eq("url", record.url);
                updateBuilder.update();
            }
        }));
    }

    public void asyncSwitchKey(final DownloadRecord record, final String newUrl) throws SQLException, IllegalArgumentException {
        sendCommand(new DbCommand() {
            public void execute() throws Exception {
                BaseDataDb.this.getDao(DownloadRecord.class).updateId(record, newUrl);
            }

            public void onSucceed(Object obj) {
                EventNotifyCenter.notifyEvent(BaseEvent.class, 262, new Object[]{Boolean.valueOf(true), newUrl, record.url});
            }

            public void onFail(DbError error) {
                EventNotifyCenter.notifyEvent(BaseEvent.class, 262, new Object[]{Boolean.valueOf(false), newUrl, record.url});
            }
        });
    }

    public void syncSwitchKey(final DownloadRecord record, final String newUrl) throws SQLException, IllegalArgumentException {
        syncCommand(new DbSyncCommand(new SyncRunnable() {
            protected void sync() throws Exception {
                Dao<DownloadRecord, String> dao = BaseDataDb.this.getDao(DownloadRecord.class);
                dao.deleteById(record.url);
                record.url = newUrl;
                HLog.verbose(BaseDataDb.TAG, "DownloadRequest swithkey %s , url %s", new Object[]{record, newUrl});
                dao.createIfNotExists(record);
            }
        }));
    }

    public void syncUpdateRecordUrlAndName(final DownloadRecord oldRecord, final String newUrl, final String name) throws SQLException, IllegalArgumentException {
        syncCommand(new DbSyncCommand(new SyncRunnable() {
            protected void sync() throws Exception {
                BaseDataDb.this.syncSwitchKey(oldRecord, newUrl);
                UpdateBuilder<DownloadRecord, String> updateBuilder = BaseDataDb.this.getDao(DownloadRecord.class).updateBuilder();
                updateBuilder.updateColumnValue("name", name);
                updateBuilder.where().eq("url", newUrl);
                updateBuilder.update();
            }
        }));
    }

    public void syncUpdateRecordETag(final DownloadRecord record) throws SQLException, IllegalArgumentException {
        syncCommand(new DbSyncCommand(new SyncRunnable() {
            protected void sync() throws Exception {
                UpdateBuilder<DownloadRecord, String> updateBuilder = BaseDataDb.this.getDao(DownloadRecord.class).updateBuilder();
                updateBuilder.updateColumnValue(DownloadRecord.NO_INTEGRITY, Boolean.valueOf(record.noIntegrity));
                updateBuilder.where().eq("url", record.url);
                updateBuilder.update();
            }
        }));
    }
}
