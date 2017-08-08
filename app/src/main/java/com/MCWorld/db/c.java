package com.MCWorld.db;

import com.MCWorld.framework.BaseDbManager;
import com.MCWorld.framework.base.db.AbstractBaseDb;
import com.MCWorld.framework.base.db.DbCommand;
import com.MCWorld.framework.base.db.DbContext;
import com.MCWorld.framework.base.db.DbError;
import com.MCWorld.framework.base.db.DbHelper;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.p;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.table.DatabaseTableConfig;
import java.sql.SQLException;

/* compiled from: ProfileDb */
public class c extends AbstractBaseDb {
    private static final String TAG = "ProfileInfoDb";
    private static c rz;

    public static synchronized c eJ() {
        c cVar;
        synchronized (c.class) {
            if (rz == null) {
                rz = new c();
                rz.setDbContext(BaseDbManager.getDataContext());
            }
            cVar = rz;
        }
        return cVar;
    }

    public void setDbContext(DbContext ctx) {
        super.setDbContext(ctx);
    }

    public void a(final p info, final Object context) {
        sendCommand(new DbCommand(this) {
            final /* synthetic */ c rC;

            public void execute() throws Exception {
                DatabaseTableConfig config = c.getTableConfig();
                HLog.verbose(c.TAG, "saveInfo ProfileDbInfo name = " + config.getTableName() + ", uid = " + info.uid + ", json = " + info.toString(), new Object[0]);
                this.rC.getDaoNoCacheWithConfig(config).createOrUpdate(info);
            }

            public void onSucceed(Object obj) {
                EventNotifyCenter.notifyEvent(a.class, 25, new Object[]{Boolean.valueOf(true), info, context});
            }

            public void onFail(DbError error) {
                EventNotifyCenter.notifyEvent(a.class, 25, new Object[]{Boolean.valueOf(false), info, context});
            }
        });
    }

    public void a(final Object context, final long uid) {
        sendCommand(new DbCommand(this) {
            final /* synthetic */ c rC;

            public void execute() throws Exception {
                this.result.resultObject = (p) this.rC.getDaoNoCacheWithConfig(c.getTableConfig()).queryBuilder().selectColumns(p.JSON).where().eq(p.UID, Long.valueOf(uid)).queryForFirst();
            }

            public void onSucceed(Object obj) {
                d.eK().a((p) obj);
                EventNotifyCenter.notifyEvent(a.class, 26, new Object[]{Boolean.valueOf(true), data, context});
            }

            public void onFail(DbError error) {
                EventNotifyCenter.notifyEvent(a.class, 26, new Object[]{Boolean.valueOf(false), null, context});
            }
        });
    }

    public p p(long uid) throws SQLException {
        QueryBuilder<p, Long> builder = getDao(p.class).queryBuilder();
        builder.where().eq(p.UID, Long.valueOf(uid));
        return (p) builder.queryForFirst();
    }

    public void a(long uid, String json, Object context) {
        final long j = uid;
        final String str = json;
        final Object obj = context;
        sendCommand(new DbCommand(this) {
            final /* synthetic */ c rC;

            public void execute() throws Exception {
                DatabaseTableConfig<p> config = c.getTableConfig();
                HLog.verbose(c.TAG, "updateInfo create table with name = " + config.getTableName() + ", uid = " + j + ", json = " + str, new Object[0]);
                DbHelper.createDbTableWithConfig(config, this.rC.dbContext);
                UpdateBuilder<p, Long> updateBuilder = this.rC.getDaoNoCacheWithConfig(config).updateBuilder();
                updateBuilder.updateColumnValue(p.JSON, str).where().eq(p.UID, Long.valueOf(j));
                updateBuilder.update();
            }

            public void onSucceed(Object obj) {
                EventNotifyCenter.notifyEvent(a.class, 27, new Object[]{Boolean.valueOf(true), Long.valueOf(j), obj});
            }

            public void onFail(DbError error) {
                EventNotifyCenter.notifyEvent(a.class, 27, new Object[]{Boolean.valueOf(false), Long.valueOf(j), obj});
            }
        });
    }

    public static DatabaseTableConfig<p> getTableConfig() {
        DatabaseTableConfig<p> config = new DatabaseTableConfig();
        config.setTableName(p.TABLE);
        config.setDataClass(p.class);
        return config;
    }
}
