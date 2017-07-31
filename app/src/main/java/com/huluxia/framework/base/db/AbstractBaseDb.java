package com.huluxia.framework.base.db;

import android.database.sqlite.SQLiteDatabase;
import com.huluxia.framework.base.log.HLog;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.DatabaseTableConfig;
import java.sql.SQLException;

public abstract class AbstractBaseDb {
    protected DbContext dbContext;

    protected AbstractBaseDb() {
    }

    public void setDbContext(DbContext ctx) {
        this.dbContext = ctx;
    }

    protected <D extends Dao<T, ?>, T> D getDao(Class<T> cls) {
        Dao<?, ?> dao = null;
        if (this.dbContext != null) {
            DbHelper helper = this.dbContext.getDbHelper();
            if (cls == null || helper == null) {
                HLog.error(this, "db helper is NULL, clz %s", new Object[]{cls.getName()});
            } else {
                try {
                    dao = helper.getDao(cls);
                } catch (SQLException e) {
                    HLog.error(this, "cannot getDao for class " + cls.getName(), e, new Object[0]);
                }
            }
        } else {
            HLog.error(this, "db context is NULL, clz %s", new Object[]{cls.getName()});
        }
        return dao;
    }

    protected <D extends Dao<T, ?>, T> D getDaoWithConfig(DatabaseTableConfig<T> config) {
        D dao = null;
        if (this.dbContext != null) {
            DbHelper helper = this.dbContext.getDbHelper();
            if (!(config == null || helper == null)) {
                try {
                    dao = DaoManager.createDao(helper.getConnectionSource(), (DatabaseTableConfig) config);
                } catch (SQLException e) {
                    HLog.error(this, "cannot getDao for class with config" + config, new Object[0]);
                }
            }
        }
        return dao;
    }

    protected SQLiteDatabase getSqliteDb() {
        return this.dbContext.getDbHelper().getWritableDatabase();
    }

    protected void sendCommand(DbCommand cmd) {
        if (this.dbContext != null) {
            this.dbContext.sendCommand(cmd);
        }
    }

    protected DbResult syncCommand(DbSyncCommand cmd) {
        if (this.dbContext != null) {
            return this.dbContext.syncCommnad(cmd);
        }
        return null;
    }

    protected <D extends Dao<T, ?>, T> D getDaoNoCacheWithConfig(DatabaseTableConfig<T> config) {
        D dao = getDaoWithConfig(config);
        if (!(this.dbContext == null || this.dbContext.getDbHelper() == null)) {
            DaoManager.unregisterDao(this.dbContext.getDbHelper().getConnectionSource(), dao);
        }
        return dao;
    }
}
