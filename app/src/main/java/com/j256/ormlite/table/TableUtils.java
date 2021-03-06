package com.j256.ormlite.table;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.misc.IOUtils;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.stmt.StatementBuilder.StatementType;
import com.j256.ormlite.support.CompiledStatement;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.DatabaseResults;
import io.netty.handler.codec.http.HttpConstants;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TableUtils {
    private static Logger logger = LoggerFactory.getLogger(TableUtils.class);
    private static final FieldType[] noFieldTypes = new FieldType[0];

    private TableUtils() {
    }

    public static <T> int createTable(ConnectionSource connectionSource, Class<T> dataClass) throws SQLException {
        return doCreateTable(DaoManager.createDao(connectionSource, (Class) dataClass), false);
    }

    public static int createTable(Dao<?, ?> dao) throws SQLException {
        return doCreateTable(dao, false);
    }

    public static <T> int createTableIfNotExists(ConnectionSource connectionSource, Class<T> dataClass) throws SQLException {
        return doCreateTable(DaoManager.createDao(connectionSource, (Class) dataClass), true);
    }

    public static <T> int createTable(ConnectionSource connectionSource, DatabaseTableConfig<T> tableConfig) throws SQLException {
        return doCreateTable(DaoManager.createDao(connectionSource, (DatabaseTableConfig) tableConfig), false);
    }

    public static <T> int createTableIfNotExists(ConnectionSource connectionSource, DatabaseTableConfig<T> tableConfig) throws SQLException {
        return doCreateTable(DaoManager.createDao(connectionSource, (DatabaseTableConfig) tableConfig), true);
    }

    public static <T, ID> List<String> getCreateTableStatements(ConnectionSource connectionSource, Class<T> dataClass) throws SQLException {
        Dao<T, ID> dao = DaoManager.createDao(connectionSource, (Class) dataClass);
        if (dao instanceof BaseDaoImpl) {
            return addCreateTableStatements(connectionSource, ((BaseDaoImpl) dao).getTableInfo(), false);
        }
        return addCreateTableStatements(connectionSource, new TableInfo(connectionSource, null, (Class) dataClass), false);
    }

    public static <T, ID> List<String> getCreateTableStatements(ConnectionSource connectionSource, DatabaseTableConfig<T> tableConfig) throws SQLException {
        Dao<T, ID> dao = DaoManager.createDao(connectionSource, (DatabaseTableConfig) tableConfig);
        if (dao instanceof BaseDaoImpl) {
            return addCreateTableStatements(connectionSource, ((BaseDaoImpl) dao).getTableInfo(), false);
        }
        tableConfig.extractFieldTypes(connectionSource);
        return addCreateTableStatements(connectionSource, new TableInfo(connectionSource.getDatabaseType(), null, (DatabaseTableConfig) tableConfig), false);
    }

    public static <T, ID> int dropTable(ConnectionSource connectionSource, Class<T> dataClass, boolean ignoreErrors) throws SQLException {
        return dropTable(DaoManager.createDao(connectionSource, (Class) dataClass), ignoreErrors);
    }

    public static <T, ID> int dropTable(Dao<T, ID> dao, boolean ignoreErrors) throws SQLException {
        ConnectionSource connectionSource = dao.getConnectionSource();
        Class dataClass = dao.getDataClass();
        DatabaseType databaseType = connectionSource.getDatabaseType();
        if (dao instanceof BaseDaoImpl) {
            return doDropTable(databaseType, connectionSource, ((BaseDaoImpl) dao).getTableInfo(), ignoreErrors);
        }
        return doDropTable(databaseType, connectionSource, new TableInfo(connectionSource, null, dataClass), ignoreErrors);
    }

    public static <T, ID> int dropTable(ConnectionSource connectionSource, DatabaseTableConfig<T> tableConfig, boolean ignoreErrors) throws SQLException {
        DatabaseType databaseType = connectionSource.getDatabaseType();
        Dao<T, ID> dao = DaoManager.createDao(connectionSource, (DatabaseTableConfig) tableConfig);
        if (dao instanceof BaseDaoImpl) {
            return doDropTable(databaseType, connectionSource, ((BaseDaoImpl) dao).getTableInfo(), ignoreErrors);
        }
        tableConfig.extractFieldTypes(connectionSource);
        return doDropTable(databaseType, connectionSource, new TableInfo(databaseType, null, (DatabaseTableConfig) tableConfig), ignoreErrors);
    }

    public static <T> int clearTable(ConnectionSource connectionSource, Class<T> dataClass) throws SQLException {
        String tableName = DatabaseTableConfig.extractTableName(dataClass);
        DatabaseType databaseType = connectionSource.getDatabaseType();
        if (databaseType.isEntityNamesMustBeUpCase()) {
            tableName = databaseType.upCaseEntityName(tableName);
        }
        return clearTable(connectionSource, tableName);
    }

    public static <T> int clearTable(ConnectionSource connectionSource, DatabaseTableConfig<T> tableConfig) throws SQLException {
        return clearTable(connectionSource, tableConfig.getTableName());
    }

    private static <T> int clearTable(ConnectionSource connectionSource, String tableName) throws SQLException {
        DatabaseType databaseType = connectionSource.getDatabaseType();
        StringBuilder sb = new StringBuilder(48);
        if (databaseType.isTruncateSupported()) {
            sb.append("TRUNCATE TABLE ");
        } else {
            sb.append("DELETE FROM ");
        }
        databaseType.appendEscapedEntityName(sb, tableName);
        Object statement = sb.toString();
        logger.info("clearing table '{}' with '{}", (Object) tableName, statement);
        CompiledStatement compiledStatement = null;
        DatabaseConnection connection = connectionSource.getReadWriteConnection(tableName);
        try {
            compiledStatement = connection.compileStatement(statement, StatementType.EXECUTE, noFieldTypes, -1, false);
            int runExecute = compiledStatement.runExecute();
            return runExecute;
        } finally {
            IOUtils.closeThrowSqlException(compiledStatement, "compiled statement");
            connectionSource.releaseConnection(connection);
        }
    }

    private static <T, ID> int doDropTable(DatabaseType databaseType, ConnectionSource connectionSource, TableInfo<T, ID> tableInfo, boolean ignoreErrors) throws SQLException {
        logger.info("dropping table '{}'", tableInfo.getTableName());
        List<String> statements = new ArrayList();
        addDropIndexStatements(databaseType, tableInfo, statements);
        addDropTableStatements(databaseType, tableInfo, statements);
        DatabaseConnection connection = connectionSource.getReadWriteConnection(tableInfo.getTableName());
        try {
            int doStatements = doStatements(connection, "drop", statements, ignoreErrors, databaseType.isCreateTableReturnsNegative(), false);
            return doStatements;
        } finally {
            connectionSource.releaseConnection(connection);
        }
    }

    private static <T, ID> void addDropIndexStatements(DatabaseType databaseType, TableInfo<T, ID> tableInfo, List<String> statements) {
        Set<String> indexSet = new HashSet();
        for (FieldType fieldType : tableInfo.getFieldTypes()) {
            String indexName = fieldType.getIndexName();
            if (indexName != null) {
                indexSet.add(indexName);
            }
            String uniqueIndexName = fieldType.getUniqueIndexName();
            if (uniqueIndexName != null) {
                indexSet.add(uniqueIndexName);
            }
        }
        StringBuilder sb = new StringBuilder(48);
        for (Object indexName2 : indexSet) {
            logger.info("dropping index '{}' for table '{}", indexName2, tableInfo.getTableName());
            sb.append("DROP INDEX ");
            databaseType.appendEscapedEntityName(sb, indexName2);
            statements.add(sb.toString());
            sb.setLength(0);
        }
    }

    private static <T, ID> void addCreateIndexStatements(DatabaseType databaseType, TableInfo<T, ID> tableInfo, List<String> statements, boolean ifNotExists, boolean unique) {
        Map<String, List<String>> indexMap = new HashMap();
        for (FieldType fieldType : tableInfo.getFieldTypes()) {
            String indexName;
            if (unique) {
                indexName = fieldType.getUniqueIndexName();
            } else {
                indexName = fieldType.getIndexName();
            }
            if (indexName != null) {
                List<String> columnList = (List) indexMap.get(indexName);
                if (columnList == null) {
                    columnList = new ArrayList();
                    indexMap.put(indexName, columnList);
                }
                columnList.add(fieldType.getColumnName());
            }
        }
        StringBuilder sb = new StringBuilder(128);
        for (Entry<String, List<String>> indexEntry : indexMap.entrySet()) {
            logger.info("creating index '{}' for table '{}", indexEntry.getKey(), tableInfo.getTableName());
            sb.append("CREATE ");
            if (unique) {
                sb.append("UNIQUE ");
            }
            sb.append("INDEX ");
            if (ifNotExists && databaseType.isCreateIndexIfNotExistsSupported()) {
                sb.append("IF NOT EXISTS ");
            }
            databaseType.appendEscapedEntityName(sb, (String) indexEntry.getKey());
            sb.append(" ON ");
            databaseType.appendEscapedEntityName(sb, tableInfo.getTableName());
            sb.append(" ( ");
            boolean first = true;
            for (String columnName : (List) indexEntry.getValue()) {
                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }
                databaseType.appendEscapedEntityName(sb, columnName);
            }
            sb.append(" )");
            statements.add(sb.toString());
            sb.setLength(0);
        }
    }

    private static <T, ID> void addDropTableStatements(DatabaseType databaseType, TableInfo<T, ID> tableInfo, List<String> statements) {
        List<String> statementsBefore = new ArrayList();
        List<String> statementsAfter = new ArrayList();
        for (FieldType fieldType : tableInfo.getFieldTypes()) {
            databaseType.dropColumnArg(fieldType, statementsBefore, statementsAfter);
        }
        StringBuilder sb = new StringBuilder(64);
        sb.append("DROP TABLE ");
        databaseType.appendEscapedEntityName(sb, tableInfo.getTableName());
        sb.append(HttpConstants.SP_CHAR);
        statements.addAll(statementsBefore);
        statements.add(sb.toString());
        statements.addAll(statementsAfter);
    }

    private static <T, ID> int doCreateTable(Dao<T, ID> dao, boolean ifNotExists) throws SQLException {
        if (dao instanceof BaseDaoImpl) {
            return doCreateTable(dao.getConnectionSource(), ((BaseDaoImpl) dao).getTableInfo(), ifNotExists);
        }
        return doCreateTable(dao.getConnectionSource(), new TableInfo(dao.getConnectionSource(), null, dao.getDataClass()), ifNotExists);
    }

    private static <T, ID> int doCreateTable(ConnectionSource connectionSource, TableInfo<T, ID> tableInfo, boolean ifNotExists) throws SQLException {
        DatabaseType databaseType = connectionSource.getDatabaseType();
        logger.info("creating table '{}'", tableInfo.getTableName());
        List<String> statements = new ArrayList();
        List<String> queriesAfter = new ArrayList();
        addCreateTableStatements(databaseType, tableInfo, statements, queriesAfter, ifNotExists);
        DatabaseConnection connection = connectionSource.getReadWriteConnection(tableInfo.getTableName());
        try {
            int doStatements = doStatements(connection, "create", statements, false, databaseType.isCreateTableReturnsNegative(), databaseType.isCreateTableReturnsZero());
            doStatements += doCreateTestQueries(connection, databaseType, queriesAfter);
            return doStatements;
        } finally {
            connectionSource.releaseConnection(connection);
        }
    }

    private static int doStatements(DatabaseConnection connection, String label, Collection<String> statements, boolean ignoreErrors, boolean returnsNegative, boolean expectingZero) throws SQLException {
        int stmtC = 0;
        for (Object statement : statements) {
            int rowC = 0;
            CompiledStatement compiledStmt = null;
            try {
                compiledStmt = connection.compileStatement(statement, StatementType.EXECUTE, noFieldTypes, -1, false);
                rowC = compiledStmt.runExecute();
                logger.info("executed {} table statement changed {} rows: {}", (Object) label, Integer.valueOf(rowC), statement);
            } catch (Object e) {
                if (ignoreErrors) {
                    logger.info("ignoring {} error '{}' for statement: {}", (Object) label, e, statement);
                } else {
                    throw SqlExceptionUtil.create("SQL statement failed: " + statement, e);
                }
            } finally {
                IOUtils.closeThrowSqlException(compiledStmt, "compiled statement");
            }
            if (rowC < 0) {
                if (!returnsNegative) {
                    throw new SQLException("SQL statement " + statement + " updated " + rowC + " rows, we were expecting >= 0");
                }
            } else if (rowC > 0 && expectingZero) {
                throw new SQLException("SQL statement updated " + rowC + " rows, we were expecting == 0: " + statement);
            }
            stmtC++;
        }
        return stmtC;
    }

    private static int doCreateTestQueries(DatabaseConnection connection, DatabaseType databaseType, List<String> queriesAfter) throws SQLException {
        int stmtC = 0;
        for (Object query : queriesAfter) {
            try {
                CompiledStatement compiledStmt = connection.compileStatement(query, StatementType.SELECT, noFieldTypes, -1, false);
                DatabaseResults results = compiledStmt.runQuery(null);
                int rowC = 0;
                for (boolean isThereMore = results.first(); isThereMore; isThereMore = results.next()) {
                    rowC++;
                }
                logger.info("executing create table after-query got {} results: {}", Integer.valueOf(rowC), query);
                IOUtils.closeThrowSqlException(compiledStmt, "compiled statement");
                stmtC++;
            } catch (SQLException e) {
                throw SqlExceptionUtil.create("executing create table after-query failed: " + query, e);
            } catch (Throwable th) {
                IOUtils.closeThrowSqlException(null, "compiled statement");
            }
        }
        return stmtC;
    }

    private static <T, ID> List<String> addCreateTableStatements(ConnectionSource connectionSource, TableInfo<T, ID> tableInfo, boolean ifNotExists) throws SQLException {
        List<String> statements = new ArrayList();
        addCreateTableStatements(connectionSource.getDatabaseType(), tableInfo, statements, new ArrayList(), ifNotExists);
        return statements;
    }

    private static <T, ID> void addCreateTableStatements(DatabaseType databaseType, TableInfo<T, ID> tableInfo, List<String> statements, List<String> queriesAfter, boolean ifNotExists) throws SQLException {
        StringBuilder sb = new StringBuilder(256);
        sb.append("CREATE TABLE ");
        if (ifNotExists && databaseType.isCreateIfNotExistsSupported()) {
            sb.append("IF NOT EXISTS ");
        }
        databaseType.appendEscapedEntityName(sb, tableInfo.getTableName());
        sb.append(" (");
        List<String> additionalArgs = new ArrayList();
        List<String> statementsBefore = new ArrayList();
        List<String> statementsAfter = new ArrayList();
        boolean first = true;
        for (FieldType fieldType : tableInfo.getFieldTypes()) {
            if (!fieldType.isForeignCollection()) {
                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }
                String columnDefinition = fieldType.getColumnDefinition();
                if (columnDefinition == null) {
                    databaseType.appendColumnArg(tableInfo.getTableName(), sb, fieldType, additionalArgs, statementsBefore, statementsAfter, queriesAfter);
                } else {
                    databaseType.appendEscapedEntityName(sb, fieldType.getColumnName());
                    sb.append(HttpConstants.SP_CHAR).append(columnDefinition).append(HttpConstants.SP_CHAR);
                }
            }
        }
        databaseType.addPrimaryKeySql(tableInfo.getFieldTypes(), additionalArgs, statementsBefore, statementsAfter, queriesAfter);
        databaseType.addUniqueComboSql(tableInfo.getFieldTypes(), additionalArgs, statementsBefore, statementsAfter, queriesAfter);
        for (String arg : additionalArgs) {
            sb.append(", ").append(arg);
        }
        sb.append(") ");
        databaseType.appendCreateTableSuffix(sb);
        statements.addAll(statementsBefore);
        statements.add(sb.toString());
        statements.addAll(statementsAfter);
        addCreateIndexStatements(databaseType, tableInfo, statements, ifNotExists, false);
        addCreateIndexStatements(databaseType, tableInfo, statements, ifNotExists, true);
    }
}
