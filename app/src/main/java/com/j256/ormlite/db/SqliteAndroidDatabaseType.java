package com.j256.ormlite.db;

import com.j256.ormlite.android.DatabaseTableConfigUtil;
import com.j256.ormlite.field.DataPersister;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.DateStringType;
import com.j256.ormlite.field.types.SqlDateStringType;
import com.j256.ormlite.field.types.SqlDateType;
import com.j256.ormlite.field.types.TimeStampStringType;
import com.j256.ormlite.field.types.TimeStampType;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import java.sql.SQLException;

public class SqliteAndroidDatabaseType extends BaseSqliteDatabaseType {

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$j256$ormlite$field$SqlType = new int[SqlType.values().length];

        static {
            try {
                $SwitchMap$com$j256$ormlite$field$SqlType[SqlType.DATE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    public void loadDriver() {
    }

    public boolean isDatabaseUrlThisType(String url, String dbTypePart) {
        return true;
    }

    protected String getDriverClassName() {
        return null;
    }

    public String getDatabaseName() {
        return "Android SQLite";
    }

    protected void appendDateType(StringBuilder sb, FieldType fieldType, int fieldWidth) {
        appendStringType(sb, fieldType, fieldWidth);
    }

    public void appendEscapedEntityName(StringBuilder sb, String name) {
        sb.append('`').append(name).append('`');
    }

    protected void appendBooleanType(StringBuilder sb, FieldType fieldType, int fieldWidth) {
        appendShortType(sb, fieldType, fieldWidth);
    }

    public DataPersister getDataPersister(DataPersister defaultPersister, FieldType fieldType) {
        if (defaultPersister == null) {
            return super.getDataPersister(defaultPersister, fieldType);
        }
        switch (AnonymousClass1.$SwitchMap$com$j256$ormlite$field$SqlType[defaultPersister.getSqlType().ordinal()]) {
            case 1:
                if (defaultPersister instanceof TimeStampType) {
                    return TimeStampStringType.getSingleton();
                }
                if (defaultPersister instanceof SqlDateType) {
                    return SqlDateStringType.getSingleton();
                }
                return DateStringType.getSingleton();
            default:
                return super.getDataPersister(defaultPersister, fieldType);
        }
    }

    public boolean isNestedSavePointsSupported() {
        return false;
    }

    public boolean isBatchUseTransaction() {
        return true;
    }

    public <T> DatabaseTableConfig<T> extractDatabaseTableConfig(ConnectionSource connectionSource, Class<T> clazz) throws SQLException {
        return DatabaseTableConfigUtil.fromClass(connectionSource, clazz);
    }
}
