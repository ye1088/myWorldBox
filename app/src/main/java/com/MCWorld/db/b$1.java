package com.MCWorld.db;

import android.database.sqlite.SQLiteDatabase;
import com.MCWorld.framework.BaseDbManager.DataConnectionHelper;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.module.p;
import com.MCWorld.module.s;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;

/* compiled from: DbManager */
class b$1 implements DataConnectionHelper {
    b$1() {
    }

    public void onDbCreate(SQLiteDatabase database, ConnectionSource connectionSource) throws SQLException {
        HLog.info("DbManager", "DbManager create table " + s.class.getName(), new Object[0]);
        try {
            TableUtils.createTableIfNotExists(connectionSource, s.class);
            TableUtils.createTableIfNotExists(connectionSource, p.class);
        } catch (Exception e) {
            HLog.error("DbManager", "DbManager create table " + s.class.getName() + " error = " + e.getMessage(), new Object[0]);
        }
    }

    public void onDbUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) throws SQLException {
        if (oldVersion == 1 || oldVersion == 2) {
            HLog.info("DbManager", "DbManager update version = 1 or version = 2", new Object[0]);
        } else if (oldVersion < 5) {
            HLog.info("DbManager", "DbManager update version <= 5", new Object[0]);
        } else if (oldVersion < 6) {
            HLog.info("DbManager", "DbManager update version <= 6", new Object[0]);
            try {
                database.execSQL("ALTER TABLE hlx_res_info ADD COLUMN datadownurl TEXT;");
                TableUtils.createTableIfNotExists(connectionSource, c.class);
            } catch (Exception e) {
                HLog.warn("DbManager", "table may not exist ever e %s " + e.getMessage(), new Object[0]);
            }
        }
    }

    public void onOpen(SQLiteDatabase db) {
    }
}
