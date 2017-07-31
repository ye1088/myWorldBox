package com.huluxia.db;

import com.huluxia.framework.AppConfig;
import com.huluxia.framework.BaseDbManager;
import com.huluxia.framework.base.db.AbstractBaseDb;

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
