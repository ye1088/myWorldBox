package com.j256.ormlite.misc;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionManager {
    private static final String SAVE_POINT_PREFIX = "ORMLITE";
    private static final Logger logger = LoggerFactory.getLogger(TransactionManager.class);
    private static AtomicInteger savePointCounter = new AtomicInteger();
    private ConnectionSource connectionSource;

    public TransactionManager(ConnectionSource connectionSource) {
        this.connectionSource = connectionSource;
        initialize();
    }

    public void initialize() {
        if (this.connectionSource == null) {
            throw new IllegalStateException("dataSource was not set on " + getClass().getSimpleName());
        }
    }

    public <T> T callInTransaction(Callable<T> callable) throws SQLException {
        return callInTransaction(this.connectionSource, (Callable) callable);
    }

    public <T> T callInTransaction(String tableName, Callable<T> callable) throws SQLException {
        return callInTransaction(tableName, this.connectionSource, (Callable) callable);
    }

    public static <T> T callInTransaction(ConnectionSource connectionSource, Callable<T> callable) throws SQLException {
        return callInTransaction(null, connectionSource, (Callable) callable);
    }

    public static <T> T callInTransaction(String tableName, ConnectionSource connectionSource, Callable<T> callable) throws SQLException {
        DatabaseConnection connection = connectionSource.getReadWriteConnection(tableName);
        try {
            T callInTransaction = callInTransaction(connection, connectionSource.saveSpecialConnection(connection), connectionSource.getDatabaseType(), callable);
            return callInTransaction;
        } finally {
            connectionSource.clearSpecialConnection(connection);
            connectionSource.releaseConnection(connection);
        }
    }

    public static <T> T callInTransaction(DatabaseConnection connection, DatabaseType databaseType, Callable<T> callable) throws SQLException {
        return callInTransaction(connection, false, databaseType, callable);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <T> T callInTransaction(com.j256.ormlite.support.DatabaseConnection r10, boolean r11, com.j256.ormlite.db.DatabaseType r12, java.util.concurrent.Callable<T> r13) throws java.sql.SQLException {
        /*
        r9 = 1;
        r3 = 0;
        r2 = 0;
        r5 = 0;
        if (r11 != 0) goto L_0x000c;
    L_0x0006:
        r6 = r12.isNestedSavePointsSupported();	 Catch:{ all -> 0x0072 }
        if (r6 == 0) goto L_0x004e;
    L_0x000c:
        r6 = r10.isAutoCommitSupported();	 Catch:{ all -> 0x0072 }
        if (r6 == 0) goto L_0x0025;
    L_0x0012:
        r6 = r10.isAutoCommit();	 Catch:{ all -> 0x0072 }
        if (r6 == 0) goto L_0x0025;
    L_0x0018:
        r6 = 0;
        r10.setAutoCommit(r6);	 Catch:{ all -> 0x0072 }
        r3 = 1;
        r6 = logger;	 Catch:{ all -> 0x0072 }
        r7 = "had to set auto-commit to false";
        r6.debug(r7);	 Catch:{ all -> 0x0072 }
    L_0x0025:
        r6 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0072 }
        r6.<init>();	 Catch:{ all -> 0x0072 }
        r7 = "ORMLITE";
        r6 = r6.append(r7);	 Catch:{ all -> 0x0072 }
        r7 = savePointCounter;	 Catch:{ all -> 0x0072 }
        r7 = r7.incrementAndGet();	 Catch:{ all -> 0x0072 }
        r6 = r6.append(r7);	 Catch:{ all -> 0x0072 }
        r6 = r6.toString();	 Catch:{ all -> 0x0072 }
        r5 = r10.setSavePoint(r6);	 Catch:{ all -> 0x0072 }
        if (r5 != 0) goto L_0x0065;
    L_0x0045:
        r6 = logger;	 Catch:{ all -> 0x0072 }
        r7 = "started savePoint transaction";
        r6.debug(r7);	 Catch:{ all -> 0x0072 }
    L_0x004d:
        r2 = 1;
    L_0x004e:
        r4 = r13.call();	 Catch:{ SQLException -> 0x0081, Exception -> 0x0092 }
        if (r2 == 0) goto L_0x0057;
    L_0x0054:
        commit(r10, r5);	 Catch:{ SQLException -> 0x0081, Exception -> 0x0092 }
    L_0x0057:
        if (r3 == 0) goto L_0x0064;
    L_0x0059:
        r10.setAutoCommit(r9);
        r6 = logger;
        r7 = "restored auto-commit to true";
        r6.debug(r7);
    L_0x0064:
        return r4;
    L_0x0065:
        r6 = logger;	 Catch:{ all -> 0x0072 }
        r7 = "started savePoint transaction {}";
        r8 = r5.getSavepointName();	 Catch:{ all -> 0x0072 }
        r6.debug(r7, r8);	 Catch:{ all -> 0x0072 }
        goto L_0x004d;
    L_0x0072:
        r6 = move-exception;
        if (r3 == 0) goto L_0x0080;
    L_0x0075:
        r10.setAutoCommit(r9);
        r7 = logger;
        r8 = "restored auto-commit to true";
        r7.debug(r8);
    L_0x0080:
        throw r6;
    L_0x0081:
        r0 = move-exception;
        if (r2 == 0) goto L_0x0087;
    L_0x0084:
        rollBack(r10, r5);	 Catch:{ SQLException -> 0x0088 }
    L_0x0087:
        throw r0;	 Catch:{ all -> 0x0072 }
    L_0x0088:
        r1 = move-exception;
        r6 = logger;	 Catch:{ all -> 0x0072 }
        r7 = "after commit exception, rolling back to save-point also threw exception";
        r6.error(r0, r7);	 Catch:{ all -> 0x0072 }
        goto L_0x0087;
    L_0x0092:
        r0 = move-exception;
        if (r2 == 0) goto L_0x0098;
    L_0x0095:
        rollBack(r10, r5);	 Catch:{ SQLException -> 0x00a0 }
    L_0x0098:
        r6 = "Transaction callable threw non-SQL exception";
        r6 = com.j256.ormlite.misc.SqlExceptionUtil.create(r6, r0);	 Catch:{ all -> 0x0072 }
        throw r6;	 Catch:{ all -> 0x0072 }
    L_0x00a0:
        r1 = move-exception;
        r6 = logger;	 Catch:{ all -> 0x0072 }
        r7 = "after commit exception, rolling back to save-point also threw exception";
        r6.error(r0, r7);	 Catch:{ all -> 0x0072 }
        goto L_0x0098;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.j256.ormlite.misc.TransactionManager.callInTransaction(com.j256.ormlite.support.DatabaseConnection, boolean, com.j256.ormlite.db.DatabaseType, java.util.concurrent.Callable):T");
    }

    public void setConnectionSource(ConnectionSource connectionSource) {
        this.connectionSource = connectionSource;
    }

    private static void commit(DatabaseConnection connection, Savepoint savePoint) throws SQLException {
        Object name = savePoint == null ? null : savePoint.getSavepointName();
        connection.commit(savePoint);
        if (name == null) {
            logger.debug("committed savePoint transaction");
        } else {
            logger.debug("committed savePoint transaction {}", name);
        }
    }

    private static void rollBack(DatabaseConnection connection, Savepoint savePoint) throws SQLException {
        Object name = savePoint == null ? null : savePoint.getSavepointName();
        connection.rollback(savePoint);
        if (name == null) {
            logger.debug("rolled back savePoint transaction");
        } else {
            logger.debug("rolled back savePoint transaction {}", name);
        }
    }
}
