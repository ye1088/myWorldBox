package com.j256.ormlite.db;

import com.j256.ormlite.field.DataPersister;
import com.j256.ormlite.field.FieldConverter;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BigDecimalStringType;
import java.util.List;

public abstract class BaseSqliteDatabaseType extends BaseDatabaseType {
    private static final FieldConverter booleanConverter = new BooleanNumberFieldConverter();

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$j256$ormlite$field$SqlType = new int[SqlType.values().length];

        static {
            try {
                $SwitchMap$com$j256$ormlite$field$SqlType[SqlType.BOOLEAN.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$j256$ormlite$field$SqlType[SqlType.BIG_DECIMAL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    protected void appendLongType(StringBuilder sb, FieldType fieldType, int fieldWidth) {
        if (fieldType.getSqlType() == SqlType.LONG && fieldType.isGeneratedId()) {
            sb.append("INTEGER");
        } else {
            sb.append("BIGINT");
        }
    }

    protected void configureGeneratedId(String tableName, StringBuilder sb, FieldType fieldType, List<String> list, List<String> list2, List<String> list3, List<String> list4) {
        if (fieldType.getSqlType() == SqlType.INTEGER || fieldType.getSqlType() == SqlType.LONG) {
            sb.append("PRIMARY KEY AUTOINCREMENT ");
            return;
        }
        throw new IllegalArgumentException("Sqlite requires that auto-increment generated-id be integer or long type");
    }

    protected boolean generatedIdSqlAtEnd() {
        return false;
    }

    public boolean isVarcharFieldWidthSupported() {
        return false;
    }

    public boolean isCreateTableReturnsZero() {
        return false;
    }

    public boolean isCreateIfNotExistsSupported() {
        return true;
    }

    public FieldConverter getFieldConverter(DataPersister dataPersister, FieldType fieldType) {
        switch (AnonymousClass1.$SwitchMap$com$j256$ormlite$field$SqlType[dataPersister.getSqlType().ordinal()]) {
            case 1:
                return booleanConverter;
            case 2:
                return BigDecimalStringType.getSingleton();
            default:
                return super.getFieldConverter(dataPersister, fieldType);
        }
    }

    public void appendInsertNoColumns(StringBuilder sb) {
        sb.append("DEFAULT VALUES");
    }
}
