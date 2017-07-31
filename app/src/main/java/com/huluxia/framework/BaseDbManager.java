package com.huluxia.framework;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.huluxia.framework.base.db.AbstractBaseDb;
import com.huluxia.framework.base.db.DbContext;
import com.huluxia.framework.base.log.HLog;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;

public class BaseDbManager {
    private static final String TAG = "BaseDbManager";
    private static DbContext dataContext;
    private static Map<Class<? extends AbstractBaseDb>, AbstractBaseDb> mDbs = new Hashtable();

    public interface DataConnectionHelper {
        void onDbCreate(SQLiteDatabase sQLiteDatabase, ConnectionSource connectionSource) throws SQLException;

        void onDbUpgrade(SQLiteDatabase sQLiteDatabase, ConnectionSource connectionSource, int i, int i2) throws SQLException;

        void onOpen(SQLiteDatabase sQLiteDatabase);
    }

    public static void init(String name, Context ctx, DataConnectionHelper helper, int dbVersion) {
        if (helper == null) {
            throw new IllegalArgumentException("helper must not be null");
        }
        HLog.info(TAG, "init db with name %s", name);
        if (dataContext == null) {
            dataContext = new 1("DataDbThread", name, ctx, dbVersion, helper);
            dataContext.open();
        }
    }

    public static void uninit() {
        if (dataContext != null) {
            dataContext.closeDbHelper();
        }
    }

    public static DbContext getDataContext() {
        return dataContext;
    }

    public static synchronized AbstractBaseDb getFrameworkDataDb() {
        AbstractBaseDb db;
        synchronized (BaseDbManager.class) {
            db = getDb(BaseDataDb.class);
        }
        return db;
    }

    public static <T extends AbstractBaseDb> AbstractBaseDb getDb(Class<T> clz) {
        AbstractBaseDb db = (AbstractBaseDb) mDbs.get(clz);
        if (db != null) {
            return db;
        }
        try {
            db = (AbstractBaseDb) clz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            db.setDbContext(getDataContext());
            mDbs.put(clz, db);
            return db;
        } catch (Exception e) {
            HLog.error(TAG, "db init failed, error %s", e, new Object[0]);
            return db;
        }
    }
}
