package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;
import java.util.Arrays;

public class ByteArrayType extends BaseDataType {
    private static final ByteArrayType singleTon = new ByteArrayType();

    public static ByteArrayType getSingleton() {
        return singleTon;
    }

    private ByteArrayType() {
        super(SqlType.BYTE_ARRAY);
    }

    protected ByteArrayType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        if (defaultStr == null) {
            return null;
        }
        return defaultStr.getBytes();
    }

    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) throws SQLException {
        return results.getBytes(columnPos);
    }

    public boolean isArgumentHolderRequired() {
        return true;
    }

    public boolean dataIsEqual(Object fieldObj1, Object fieldObj2) {
        if (fieldObj1 == null) {
            if (fieldObj2 == null) {
                return true;
            }
            return false;
        } else if (fieldObj2 != null) {
            return Arrays.equals((byte[]) fieldObj1, (byte[]) fieldObj2);
        } else {
            return false;
        }
    }

    public Object resultStringToJava(FieldType fieldType, String stringValue, int columnPos) {
        return stringValue.getBytes();
    }

    public Class<?> getPrimaryClass() {
        return byte[].class;
    }
}
