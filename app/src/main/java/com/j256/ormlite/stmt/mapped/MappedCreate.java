package com.j256.ormlite.stmt.mapped;

import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.logger.Log.Level;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.GeneratedKeyHolder;
import com.j256.ormlite.table.TableInfo;
import io.netty.util.internal.StringUtil;
import java.sql.SQLException;

public class MappedCreate<T, ID> extends BaseMappedStatement<T, ID> {
    private String dataClassName;
    private final String queryNextSequenceStmt;
    private int versionFieldTypeIndex;

    private static class KeyHolder implements GeneratedKeyHolder {
        Number key;

        private KeyHolder() {
        }

        public Number getKey() {
            return this.key;
        }

        public void addKey(Number key) throws SQLException {
            if (this.key == null) {
                this.key = key;
                return;
            }
            throw new SQLException("generated key has already been set to " + this.key + ", now set to " + key);
        }
    }

    private MappedCreate(TableInfo<T, ID> tableInfo, String statement, FieldType[] argFieldTypes, String queryNextSequenceStmt, int versionFieldTypeIndex) {
        super(tableInfo, statement, argFieldTypes);
        this.dataClassName = tableInfo.getDataClass().getSimpleName();
        this.queryNextSequenceStmt = queryNextSequenceStmt;
        this.versionFieldTypeIndex = versionFieldTypeIndex;
    }

    public int insert(DatabaseType databaseType, DatabaseConnection databaseConnection, T data, ObjectCache objectCache) throws SQLException {
        KeyHolder keyHolder = null;
        if (this.idField != null) {
            boolean assignId;
            if (!this.idField.isAllowGeneratedIdInsert() || this.idField.isObjectsFieldValueDefault(data)) {
                assignId = true;
            } else {
                assignId = false;
            }
            if (this.idField.isSelfGeneratedId() && this.idField.isGeneratedId()) {
                if (assignId) {
                    this.idField.assignField(data, this.idField.generateId(), false, objectCache);
                }
            } else if (this.idField.isGeneratedIdSequence() && databaseType.isSelectSequenceBeforeInsert()) {
                if (assignId) {
                    assignSequenceId(databaseConnection, data, objectCache);
                }
            } else if (this.idField.isGeneratedId() && assignId) {
                keyHolder = new KeyHolder();
            }
        }
        Object args;
        try {
            if (this.tableInfo.isForeignAutoCreate()) {
                for (FieldType fieldType : this.tableInfo.getFieldTypes()) {
                    if (fieldType.isForeignAutoCreate()) {
                        Object foreignObj = fieldType.extractRawJavaFieldValue(data);
                        if (foreignObj != null && fieldType.getForeignIdField().isObjectsFieldValueDefault(foreignObj)) {
                            fieldType.createWithForeignDao(foreignObj);
                        }
                    }
                }
            }
            args = getFieldObjects(data);
            Object versionDefaultValue = null;
            if (this.versionFieldTypeIndex >= 0 && args[this.versionFieldTypeIndex] == null) {
                FieldType versionFieldType = this.argFieldTypes[this.versionFieldTypeIndex];
                versionDefaultValue = versionFieldType.moveToNextValue(null);
                args[this.versionFieldTypeIndex] = versionFieldType.convertJavaFieldToSqlArgValue(versionDefaultValue);
            }
            int rowC = databaseConnection.insert(this.statement, args, this.argFieldTypes, keyHolder);
            logger.debug("insert data with statement '{}' and {} args, changed {} rows", (Object) this.statement, Integer.valueOf(args.length), Integer.valueOf(rowC));
            if (args.length > 0) {
                logger.trace("insert arguments: {}", args);
            }
            if (rowC > 0) {
                if (versionDefaultValue != null) {
                    this.argFieldTypes[this.versionFieldTypeIndex].assignField(data, versionDefaultValue, false, null);
                }
                if (keyHolder != null) {
                    Number key = keyHolder.getKey();
                    if (key == null) {
                        throw new SQLException("generated-id key was not set by the update call, maybe a_isRightVersion schema mismatch between entity and database table?");
                    } else if (key.longValue() == 0) {
                        throw new SQLException("generated-id key must not be 0 value, maybe a_isRightVersion schema mismatch between entity and database table?");
                    } else {
                        assignIdValue(data, key, "keyholder", objectCache);
                    }
                }
                if (objectCache != null && foreignCollectionsAreAssigned(this.tableInfo.getForeignCollections(), data)) {
                    objectCache.put(this.clazz, this.idField.extractJavaFieldValue(data), data);
                }
            }
            return rowC;
        } catch (Object e) {
            logger.debug("insert data with statement '{}' and {} args, threw exception: {}", (Object) this.statement, (Object) Integer.valueOf(args.length), e);
            if (args.length > 0) {
                logger.trace("insert arguments: {}", args);
            }
            throw e;
        } catch (SQLException e2) {
            throw SqlExceptionUtil.create("Unable to run insert stmt on object " + data + ": " + this.statement, e2);
        }
    }

    public static <T, ID> MappedCreate<T, ID> build(DatabaseType databaseType, TableInfo<T, ID> tableInfo) {
        int i$;
        int len$;
        StringBuilder sb = new StringBuilder(128);
        BaseMappedStatement.appendTableName(databaseType, sb, "INSERT INTO ", tableInfo.getTableName());
        int argFieldC = 0;
        int versionFieldTypeIndex = -1;
        for (FieldType fieldType : tableInfo.getFieldTypes()) {
            FieldType fieldType2;
            if (isFieldCreatable(databaseType, fieldType2)) {
                if (fieldType2.isVersion()) {
                    versionFieldTypeIndex = argFieldC;
                }
                argFieldC++;
            }
        }
        FieldType[] argFieldTypes = new FieldType[argFieldC];
        if (argFieldC == 0) {
            databaseType.appendInsertNoColumns(sb);
        } else {
            boolean first = true;
            sb.append('(');
            FieldType[] arr$ = tableInfo.getFieldTypes();
            len$ = arr$.length;
            i$ = 0;
            int argFieldC2 = 0;
            while (i$ < len$) {
                fieldType2 = arr$[i$];
                if (isFieldCreatable(databaseType, fieldType2)) {
                    if (first) {
                        first = false;
                    } else {
                        sb.append(StringUtil.COMMA);
                    }
                    BaseMappedStatement.appendFieldColumnName(databaseType, sb, fieldType2, null);
                    argFieldC = argFieldC2 + 1;
                    argFieldTypes[argFieldC2] = fieldType2;
                } else {
                    argFieldC = argFieldC2;
                }
                i$++;
                argFieldC2 = argFieldC;
            }
            sb.append(") VALUES (");
            first = true;
            for (FieldType fieldType22 : tableInfo.getFieldTypes()) {
                if (isFieldCreatable(databaseType, fieldType22)) {
                    if (first) {
                        first = false;
                    } else {
                        sb.append(StringUtil.COMMA);
                    }
                    sb.append('?');
                }
            }
            sb.append(')');
            argFieldC = argFieldC2;
        }
        return new MappedCreate(tableInfo, sb.toString(), argFieldTypes, buildQueryNextSequence(databaseType, tableInfo.getIdField()), versionFieldTypeIndex);
    }

    private boolean foreignCollectionsAreAssigned(FieldType[] foreignCollections, Object data) throws SQLException {
        for (FieldType fieldType : foreignCollections) {
            if (fieldType.extractJavaFieldValue(data) == null) {
                return false;
            }
        }
        return true;
    }

    private static boolean isFieldCreatable(DatabaseType databaseType, FieldType fieldType) {
        if (fieldType.isForeignCollection() || fieldType.isReadOnly()) {
            return false;
        }
        if (databaseType.isIdSequenceNeeded() && databaseType.isSelectSequenceBeforeInsert()) {
            return true;
        }
        if (!fieldType.isGeneratedId() || fieldType.isSelfGeneratedId() || fieldType.isAllowGeneratedIdInsert()) {
            return true;
        }
        return false;
    }

    private static String buildQueryNextSequence(DatabaseType databaseType, FieldType idField) {
        if (idField == null) {
            return null;
        }
        String seqName = idField.getGeneratedIdSequence();
        if (seqName == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(64);
        databaseType.appendSelectNextValFromSequence(sb, seqName);
        return sb.toString();
    }

    private void assignSequenceId(DatabaseConnection databaseConnection, T data, ObjectCache objectCache) throws SQLException {
        long seqVal = databaseConnection.queryForLong(this.queryNextSequenceStmt);
        logger.debug("queried for sequence {} using stmt: {}", Long.valueOf(seqVal), this.queryNextSequenceStmt);
        if (seqVal == 0) {
            throw new SQLException("Should not have returned 0 for stmt: " + this.queryNextSequenceStmt);
        }
        assignIdValue(data, Long.valueOf(seqVal), "sequence", objectCache);
    }

    private void assignIdValue(T data, Number val, String label, ObjectCache objectCache) throws SQLException {
        this.idField.assignIdValue(data, val, objectCache);
        if (logger.isLevelEnabled(Level.DEBUG)) {
            logger.debug("assigned id '{}' from {} to '{}' in {} object", new Object[]{val, label, this.idField.getFieldName(), this.dataClassName});
        }
    }
}
