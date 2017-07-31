package com.huluxia.version;

import com.huluxia.db.DbConstants.GameInfoDbTable;
import com.huluxia.db.a;
import com.huluxia.framework.BaseDbManager;
import com.huluxia.framework.base.db.AbstractBaseDb;
import com.huluxia.framework.base.db.DbCommand;
import com.huluxia.framework.base.db.DbContext;
import com.huluxia.framework.base.db.DbError;
import com.huluxia.framework.base.db.DbHelper;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.table.DatabaseTableConfig;
import java.sql.SQLException;
import java.util.List;

/* compiled from: VersionDb */
public class b extends AbstractBaseDb {
    private static final String TAG = "VersionDb";
    private static b bnQ;

    public static synchronized b MR() {
        b bVar;
        synchronized (b.class) {
            if (bnQ == null) {
                bnQ = new b();
                bnQ.setDbContext(BaseDbManager.getDataContext());
            }
            bVar = bnQ;
        }
        return bVar;
    }

    public void setDbContext(DbContext ctx) {
        super.setDbContext(ctx);
    }

    public void a(final d info, final Object context) {
        sendCommand(new DbCommand(this) {
            final /* synthetic */ b bnS;

            public void execute() throws Exception {
                this.bnS.getDaoNoCacheWithConfig(b.getTableConfig()).createOrUpdate(info);
                HLog.verbose(b.TAG, "saveDownloadRingInfo  info = " + info.url, new Object[0]);
            }

            public void onSucceed(Object obj) {
                EventNotifyCenter.notifyEvent(a.class, 15, new Object[]{Boolean.valueOf(true), info, context});
            }

            public void onFail(DbError error) {
                EventNotifyCenter.notifyEvent(a.class, 15, new Object[]{Boolean.valueOf(false), info, context});
            }
        });
    }

    public void av(final Object context) {
        sendCommand(new DbCommand(this) {
            final /* synthetic */ b bnS;

            public void execute() throws Exception {
                this.result.resultObject = this.bnS.getDaoNoCacheWithConfig(b.getTableConfig()).queryForAll();
            }

            public void onSucceed(Object obj) {
                g.MS().resetRecord((List) obj);
                EventNotifyCenter.notifyEvent(a.class, 16, new Object[]{Boolean.valueOf(true), data, context});
            }

            public void onFail(DbError error) {
                EventNotifyCenter.notifyEvent(a.class, 16, new Object[]{Boolean.valueOf(false), null, context});
            }
        });
    }

    public List<d> aw(Object context) throws Exception {
        DatabaseTableConfig<d> config = getTableConfig();
        HLog.verbose(TAG, "syncQueryDownloadInfos create table with name = " + config.getTableName(), new Object[0]);
        DbHelper.createDbTableWithConfig(config, this.dbContext);
        return getDaoNoCacheWithConfig(config).queryForAll();
    }

    public void l(final String url, final Object context) {
        sendCommand(new DbCommand(this) {
            final /* synthetic */ b bnS;

            public void execute() throws Exception {
                DatabaseTableConfig<d> config = b.getTableConfig();
                HLog.verbose(b.TAG, "deleteDownloadInfo create table with name = " + config.getTableName() + ",url = " + url, new Object[0]);
                DbHelper.createDbTableWithConfig(config, this.bnS.dbContext);
                DeleteBuilder<d, Integer> deleteBuilder = this.bnS.getDaoNoCacheWithConfig(config).deleteBuilder();
                deleteBuilder.where().eq("url", url);
                deleteBuilder.delete();
            }

            public void onSucceed(Object obj) {
                EventNotifyCenter.notifyEvent(a.class, 17, new Object[]{Boolean.valueOf(true), url, context});
            }

            public void onFail(DbError error) {
                EventNotifyCenter.notifyEvent(a.class, 17, new Object[]{Boolean.valueOf(false), url, context});
            }
        });
    }

    public void a(d info) throws SQLException {
        getDao(d.class).update((Object) info);
    }

    public void syncDelRecord(String url) throws SQLException {
        getDao(d.class).deleteById(url);
    }

    public static DatabaseTableConfig<d> getTableConfig() {
        DatabaseTableConfig<d> config = new DatabaseTableConfig();
        config.setTableName(d.TABLE);
        config.setDataClass(d.class);
        return config;
    }

    public void a(final String url, final int downloadStatus, final Object context) {
        sendCommand(new DbCommand(this) {
            final /* synthetic */ b bnS;

            public void execute() throws Exception {
                DatabaseTableConfig<d> config = b.getTableConfig();
                HLog.verbose(b.TAG, "updateDownloadStatus create table with name = " + config.getTableName() + ", url = " + url + ", status = " + downloadStatus, new Object[0]);
                DbHelper.createDbTableWithConfig(config, this.bnS.dbContext);
                UpdateBuilder<d, Integer> updateBuilder = this.bnS.getDaoNoCacheWithConfig(config).updateBuilder();
                updateBuilder.updateColumnValue("downloadstatus", Integer.valueOf(downloadStatus)).where().eq("url", url);
                updateBuilder.update();
            }

            public void onSucceed(Object obj) {
                EventNotifyCenter.notifyEvent(a.class, 18, new Object[]{Boolean.valueOf(true), url, Integer.valueOf(downloadStatus), context});
            }

            public void onFail(DbError error) {
                EventNotifyCenter.notifyEvent(a.class, 18, new Object[]{Boolean.valueOf(false), url, Integer.valueOf(downloadStatus), context});
            }
        });
    }

    public void b(final int url, final String filePath, final Object context) {
        sendCommand(new DbCommand(this) {
            final /* synthetic */ b bnS;

            public void execute() throws Exception {
                DatabaseTableConfig<d> config = b.getTableConfig();
                HLog.verbose(b.TAG, "updateDownloadFilePath create table with name = " + config.getTableName() + " ,file name = " + filePath, new Object[0]);
                DbHelper.createDbTableWithConfig(config, this.bnS.dbContext);
                UpdateBuilder<d, Integer> updateBuilder = this.bnS.getDaoNoCacheWithConfig(config).updateBuilder();
                updateBuilder.updateColumnValue("filepath", filePath).where().eq("url", Integer.valueOf(url));
                updateBuilder.update();
            }

            public void onSucceed(Object obj) {
                EventNotifyCenter.notifyEvent(a.class, 19, new Object[]{Boolean.valueOf(true), Integer.valueOf(url), filePath, context});
            }

            public void onFail(DbError error) {
                EventNotifyCenter.notifyEvent(a.class, 19, new Object[]{Boolean.valueOf(false), Integer.valueOf(url), filePath, context});
            }
        });
    }

    public void a(String url, long length, Object context) {
        final String str = url;
        final long j = length;
        final Object obj = context;
        sendCommand(new DbCommand(this) {
            final /* synthetic */ b bnS;

            public void execute() throws Exception {
                DatabaseTableConfig<d> config = b.getTableConfig();
                HLog.verbose(b.TAG, "updateFileSize create table with name = " + config.getTableName() + " ,url = " + str, new Object[0]);
                DbHelper.createDbTableWithConfig(config, this.bnS.dbContext);
                UpdateBuilder<d, Integer> updateBuilder = this.bnS.getDaoNoCacheWithConfig(config).updateBuilder();
                updateBuilder.updateColumnValue(d.FILESIZE, Long.valueOf(j)).where().eq("url", str);
                updateBuilder.update();
            }

            public void onSucceed(Object obj) {
                EventNotifyCenter.notifyEvent(a.class, 20, new Object[]{Boolean.valueOf(true), str, Long.valueOf(j), obj});
            }

            public void onFail(DbError error) {
                EventNotifyCenter.notifyEvent(a.class, 20, new Object[]{Boolean.valueOf(false), str, Long.valueOf(j), obj});
            }
        });
    }

    public void a(String url, long length, String filePath, int downloadStatus, GameInfoDbTable table, Object context) {
        final String str = url;
        final long j = length;
        final String str2 = filePath;
        final int i = downloadStatus;
        final Object obj = context;
        sendCommand(new DbCommand(this) {
            final /* synthetic */ b bnS;

            public void execute() throws Exception {
                DatabaseTableConfig<d> config = b.getTableConfig();
                HLog.verbose(b.TAG, "updateDownloadInfo create table with name = " + config.getTableName() + " ,url = " + str, new Object[0]);
                DbHelper.createDbTableWithConfig(config, this.bnS.dbContext);
                UpdateBuilder<d, Integer> updateBuilder = this.bnS.getDaoNoCacheWithConfig(config).updateBuilder();
                updateBuilder.where().eq("url", str);
                updateBuilder.updateColumnValue(d.FILESIZE, Long.valueOf(j));
                updateBuilder.updateColumnValue("filepath", str2);
                updateBuilder.updateColumnValue("downloadstatus", Integer.valueOf(i));
                updateBuilder.update();
            }

            public void onSucceed(Object obj) {
                EventNotifyCenter.notifyEvent(a.class, 21, new Object[]{Boolean.valueOf(true), str, Long.valueOf(j), obj});
            }

            public void onFail(DbError error) {
                EventNotifyCenter.notifyEvent(a.class, 21, new Object[]{Boolean.valueOf(false), str, Long.valueOf(j), obj});
            }
        });
    }
}
