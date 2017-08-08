package com.MCWorld.framework;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.MCWorld.framework.BaseDbManager.DataConnectionHelper;
import com.MCWorld.framework.base.db.DbHelper;
import com.MCWorld.framework.base.db.DbThread;
import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;

class BaseDbManager$1 extends DbThread {
    final /* synthetic */ Context val$ctx;
    final /* synthetic */ int val$dbVersion;
    final /* synthetic */ DataConnectionHelper val$helper;

    BaseDbManager$1(String name, String dbName, Context context, int i, DataConnectionHelper dataConnectionHelper) {
        this.val$ctx = context;
        this.val$dbVersion = i;
        this.val$helper = dataConnectionHelper;
        super(name, dbName);
    }

    public void createDbHelper(String dbName) {
        HLog.info("BaseDbManager", "DbManager createDbHelper for " + dbName, new Object[0]);
        this.dbHelper = new DbHelper(this.val$ctx, dbName, this.val$dbVersion) {
            public void onDbUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) throws SQLException {
                BaseDbManager$1.this.val$helper.onDbUpgrade(database, connectionSource, oldVersion, newVersion);
            }

            public void onDbCreate(SQLiteDatabase database, ConnectionSource connectionSource) throws SQLException {
                HLog.info("BaseDbManager", "DbManager create table", new Object[0]);
                try {
                    TableUtils.createTableIfNotExists(connectionSource, DownloadRecord.class);
                } catch (Exception e) {
                    HLog.info("BaseDbManager", "DbManager create table error %s", new Object[]{e.getMessage()});
                }
                BaseDbManager$1.this.val$helper.onDbCreate(database, connectionSource);
            }

            public void onOpen(SQLiteDatabase db) {
                BaseDbManager$1.this.val$helper.onOpen(db);
                EventNotifyCenter.notifyEvent(BaseEvent.class, 265, new Object[0]);
            }
        };
        try {
            this.dbHelper.getWritableDatabase();
        } catch (Exception e) {
            HLog.error("BaseDbManager", "DBManager CoreDbThread can not get writable database", new Object[0]);
        }
    }
}
