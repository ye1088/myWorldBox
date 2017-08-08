package com.MCWorld.framework.base.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.MCWorld.framework.base.log.HLog;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;

public abstract class DbHelper extends OrmLiteSqliteOpenHelper {
    private static final String TAG = "DbHelper";
    private String dbName;
    private String dbOpenAccountAction;
    private String dbOpenAccountDbName;

    public abstract void onDbCreate(SQLiteDatabase sQLiteDatabase, ConnectionSource connectionSource) throws SQLException;

    public abstract void onDbUpgrade(SQLiteDatabase sQLiteDatabase, ConnectionSource connectionSource, int i, int i2) throws SQLException;

    public DbHelper(Context context, String dbName, int dbVersion) {
        super(context, dbName, null, dbVersion);
        this.dbName = dbName;
        HLog.info(TAG, "DbHelper constructor", new Object[0]);
    }

    public String getDbName() {
        return this.dbName;
    }

    public void setDBOpenAccount(String action, String dbName) {
        this.dbOpenAccountAction = action;
        this.dbOpenAccountDbName = dbName;
    }

    public String getDbOpenAccountAction() {
        return this.dbOpenAccountAction;
    }

    public String getDbOpenAccountDbName() {
        return this.dbOpenAccountDbName;
    }

    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        HLog.info(TAG, "DbHelper onCreate, name = " + this.dbName, new Object[0]);
        try {
            onDbCreate(database, connectionSource);
        } catch (SQLException e) {
            HLog.error(TAG, "DbHelper onCreate error", e, new Object[0]);
        }
    }

    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        HLog.info(TAG, "DbHelper onUpdate old " + oldVersion + " new " + newVersion, new Object[0]);
        try {
            onDbUpgrade(database, connectionSource, oldVersion, newVersion);
        } catch (Object e) {
            logger.error("DbHelper onUpgrade error", e);
        }
    }

    public static void createDbTableWithConfig(DatabaseTableConfig<?> config, DbContext dbContext) throws SQLException {
        if (dbContext == null || dbContext.getDbHelper() == null) {
            HLog.warn(TAG, "DbManager", new Object[]{"dbcontext = " + dbContext});
            return;
        }
        TableUtils.createTableIfNotExists(dbContext.getDbHelper().getConnectionSource(), (DatabaseTableConfig) config);
    }
}
