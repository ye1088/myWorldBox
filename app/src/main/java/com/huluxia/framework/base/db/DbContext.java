package com.huluxia.framework.base.db;

public interface DbContext {
    void closeDbHelper();

    void createDbHelper(String str);

    DbHelper getDbHelper();

    void open();

    void sendCommand(DbCommand dbCommand);

    DbResult syncCommnad(DbSyncCommand dbSyncCommand);
}
