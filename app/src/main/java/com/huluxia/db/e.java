package com.huluxia.db;

import com.huluxia.db.DbConstants.GameInfoDbTable;
import com.huluxia.framework.base.db.AbstractBaseDb;
import com.huluxia.framework.base.db.DbCommand;
import com.huluxia.framework.base.db.DbContext;
import com.huluxia.framework.base.db.DbError;
import com.huluxia.framework.base.db.DbHelper;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.s;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.table.DatabaseTableConfig;
import java.sql.SQLException;
import java.util.List;

/* compiled from: ResDb */
public class e extends AbstractBaseDb {
    private static final String TAG = "ResDb";

    public static e eM() {
        return (e) b.eI();
    }

    public void setDbContext(DbContext ctx) {
        super.setDbContext(ctx);
    }

    public void a(final s info, final Object context) {
        sendCommand(new DbCommand(this) {
            final /* synthetic */ e rH;

            public void execute() throws Exception {
                this.rH.getDaoNoCacheWithConfig(e.eN()).createOrUpdate(info);
                HLog.verbose(e.TAG, "saveDownloadGameInfo  info = " + info.appid, new Object[0]);
            }

            public void onSucceed(Object obj) {
                EventNotifyCenter.notifyEvent(a.class, 1, new Object[]{Boolean.valueOf(true), info, context});
            }

            public void onFail(DbError error) {
                EventNotifyCenter.notifyEvent(a.class, 1, new Object[]{Boolean.valueOf(false), info, context});
            }
        });
    }

    public void f(final Object context) {
        sendCommand(new DbCommand(this) {
            final /* synthetic */ e rH;

            public void execute() throws Exception {
                DatabaseTableConfig<s> config = e.eN();
                HLog.verbose(e.TAG, "queryDownloadGameInfos create table with name = " + config.getTableName(), new Object[0]);
                DbHelper.createDbTableWithConfig(config, this.rH.dbContext);
                this.result.resultObject = this.rH.getDaoNoCacheWithConfig(config).queryForAll();
            }

            public void onSucceed(Object obj) {
                List<s> data = (List) obj;
                EventNotifyCenter.notifyEvent(a.class, 2, new Object[]{Boolean.valueOf(true), data, context});
            }

            public void onFail(DbError error) {
                EventNotifyCenter.notifyEvent(a.class, 2, new Object[]{Boolean.valueOf(false), null, context});
            }
        });
    }

    public List<s> g(Object context) throws Exception {
        DatabaseTableConfig<s> config = eN();
        HLog.verbose(TAG, "queryDownloadGameInfos create table with name = " + config.getTableName(), new Object[0]);
        DbHelper.createDbTableWithConfig(config, this.dbContext);
        return getDaoNoCacheWithConfig(config).queryForAll();
    }

    public void a(final long appId, final Object context) {
        sendCommand(new DbCommand(this) {
            final /* synthetic */ e rH;

            public void execute() throws Exception {
                DatabaseTableConfig<s> config = e.eN();
                HLog.verbose(e.TAG, "deleteDownloadGameInfo create table with name = " + config.getTableName() + ",appid = " + appId, new Object[0]);
                DbHelper.createDbTableWithConfig(config, this.rH.dbContext);
                this.rH.getDaoNoCacheWithConfig(config).deleteById(Long.valueOf(appId));
            }

            public void onSucceed(Object obj) {
                EventNotifyCenter.notifyEvent(a.class, 3, new Object[]{Boolean.valueOf(true), Long.valueOf(appId), context});
            }

            public void onFail(DbError error) {
                EventNotifyCenter.notifyEvent(a.class, 3, new Object[]{Boolean.valueOf(false), Long.valueOf(appId), context});
            }
        });
    }

    public void a(s info) throws SQLException {
        getDao(s.class).update((Object) info);
    }

    public void r(long appId) throws SQLException {
        getDao(s.class).deleteById(Long.valueOf(appId));
    }

    public static DatabaseTableConfig<s> eN() {
        DatabaseTableConfig<s> config = new DatabaseTableConfig();
        config.setTableName(s.TABLE);
        config.setDataClass(s.class);
        return config;
    }

    public void a(long appid, int downloadStatus, Object context) {
        final long j = appid;
        final int i = downloadStatus;
        final Object obj = context;
        sendCommand(new DbCommand(this) {
            final /* synthetic */ e rH;

            public void execute() throws Exception {
                DatabaseTableConfig<s> config = e.eN();
                HLog.verbose(e.TAG, "updateDownloadStatus create table with name = " + config.getTableName() + ", appid = " + j + ", status = " + i, new Object[0]);
                DbHelper.createDbTableWithConfig(config, this.rH.dbContext);
                UpdateBuilder<s, Long> updateBuilder = this.rH.getDaoNoCacheWithConfig(config).updateBuilder();
                updateBuilder.updateColumnValue("downloadstatus", Integer.valueOf(i)).where().eq("appid", Long.valueOf(j));
                updateBuilder.update();
            }

            public void onSucceed(Object obj) {
                EventNotifyCenter.notifyEvent(a.class, 4, new Object[]{Boolean.valueOf(true), Long.valueOf(j), Integer.valueOf(i), obj});
            }

            public void onFail(DbError error) {
                EventNotifyCenter.notifyEvent(a.class, 4, new Object[]{Boolean.valueOf(false), Long.valueOf(j), Integer.valueOf(i), obj});
            }
        });
    }

    public void b(long appid, String filePath, Object context) {
        final String str = filePath;
        final long j = appid;
        final Object obj = context;
        sendCommand(new DbCommand(this) {
            final /* synthetic */ e rH;

            public void execute() throws Exception {
                DatabaseTableConfig<s> config = e.eN();
                HLog.verbose(e.TAG, "updateDownloadFilePath create table with name = " + config.getTableName() + " ,file name = " + str, new Object[0]);
                DbHelper.createDbTableWithConfig(config, this.rH.dbContext);
                UpdateBuilder<s, Long> updateBuilder = this.rH.getDaoNoCacheWithConfig(config).updateBuilder();
                updateBuilder.updateColumnValue("filepath", str).where().eq("appid", Long.valueOf(j));
                updateBuilder.update();
            }

            public void onSucceed(Object obj) {
                EventNotifyCenter.notifyEvent(a.class, 5, new Object[]{Boolean.valueOf(true), Long.valueOf(j), str, obj});
            }

            public void onFail(DbError error) {
                EventNotifyCenter.notifyEvent(a.class, 5, new Object[]{Boolean.valueOf(false), Long.valueOf(j), str, obj});
            }
        });
    }

    public void a(long appid, long length, Object context) {
        final long j = appid;
        final long j2 = length;
        final Object obj = context;
        sendCommand(new DbCommand(this) {
            final /* synthetic */ e rH;

            public void execute() throws Exception {
                DatabaseTableConfig<s> config = e.eN();
                HLog.verbose(e.TAG, "updateFileSize create table with name = " + config.getTableName() + " ,appid = " + j, new Object[0]);
                DbHelper.createDbTableWithConfig(config, this.rH.dbContext);
                UpdateBuilder<s, Long> updateBuilder = this.rH.getDaoNoCacheWithConfig(config).updateBuilder();
                updateBuilder.updateColumnValue(s.PAGESIZE, Long.valueOf(j2)).where().eq("appid", Long.valueOf(j));
                updateBuilder.update();
            }

            public void onSucceed(Object obj) {
                EventNotifyCenter.notifyEvent(a.class, 6, new Object[]{Boolean.valueOf(true), Long.valueOf(j), Long.valueOf(j2), obj});
            }

            public void onFail(DbError error) {
                EventNotifyCenter.notifyEvent(a.class, 6, new Object[]{Boolean.valueOf(false), Long.valueOf(j), Long.valueOf(j2), obj});
            }
        });
    }

    public void a(long appid, long length, String filePath, int downloadStatus, GameInfoDbTable table, Object context) {
        final long j = appid;
        final long j2 = length;
        final String str = filePath;
        final int i = downloadStatus;
        final Object obj = context;
        sendCommand(new DbCommand(this) {
            final /* synthetic */ e rH;

            public void execute() throws Exception {
                DatabaseTableConfig<s> config = e.eN();
                HLog.verbose(e.TAG, "updateFileSize create table with name = " + config.getTableName() + " ,appid = " + j, new Object[0]);
                DbHelper.createDbTableWithConfig(config, this.rH.dbContext);
                UpdateBuilder<s, Long> updateBuilder = this.rH.getDaoNoCacheWithConfig(config).updateBuilder();
                updateBuilder.where().eq("appid", Long.valueOf(j));
                updateBuilder.updateColumnValue(s.PAGESIZE, Long.valueOf(j2));
                updateBuilder.updateColumnValue("filepath", str);
                updateBuilder.updateColumnValue("downloadstatus", Integer.valueOf(i));
                updateBuilder.update();
            }

            public void onSucceed(Object obj) {
                EventNotifyCenter.notifyEvent(a.class, 7, new Object[]{Boolean.valueOf(true), Long.valueOf(j), Long.valueOf(j2), obj});
            }

            public void onFail(DbError error) {
                EventNotifyCenter.notifyEvent(a.class, 7, new Object[]{Boolean.valueOf(false), Long.valueOf(j), Long.valueOf(j2), obj});
            }
        });
    }
}
