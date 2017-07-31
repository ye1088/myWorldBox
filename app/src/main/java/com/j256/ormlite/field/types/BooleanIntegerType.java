package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;

public class BooleanIntegerType extends BooleanType {
    private static final Integer FALSE_VALUE = Integer.valueOf(0);
    private static final Integer TRUE_VALUE = Integer.valueOf(1);
    private static final BooleanIntegerType singleTon = new BooleanIntegerType();

    public static BooleanIntegerType getSingleton() {
        return singleTon;
    }

    public BooleanIntegerType() {
        super(SqlType.INTEGER);
    }

    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        return javaToSqlArg(fieldType, Boolean.valueOf(Boolean.parseBoolean(defaultStr)));
    }

    public Object javaToSqlArg(FieldType fieldType, Object obj) {
        return ((Boolean) obj).booleanValue() ? TRUE_VALUE : FALSE_VALUE;
    }

    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) throws SQLException {
        return Integer.valueOf(results.getInt(columnPos));
    }

    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        return ((Integer) sqlArg).intValue() == 0 ? Boolean.FALSE : Boolean.TRUE;
    }

    public Object resultStringToJava(FieldType fieldType, String stringValue, int columnPos) {
        if (stringValue.length() == 0) {
            return Boolean.FALSE;
        }
        return sqlArgToJava(fieldType, Integer.valueOf(Integer.parseInt(stringValue)), columnPos);
    }
}
