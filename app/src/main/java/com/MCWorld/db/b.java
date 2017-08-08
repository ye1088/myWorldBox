package com.MCWorld.db;

import com.MCWorld.framework.AppConfig;
import com.MCWorld.framework.BaseDbManager;
import com.MCWorld.framework.base.db.AbstractBaseDb;

/* compiled from: DbManager */
public class b {
    private static final String TAG = "DbManager";
    private static final int ry = 6;

    public static void init(String name) {
        AppConfig.getInstance().initDbManager(name, new 1(), 6);
    }

    public static synchronized AbstractBaseDb eI() {
        AbstractBaseDb db;
        synchronized (b.class) {
            db = BaseDbManager.getDb(e.class);
        }
        return db;
    }
}
