package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.SQLException;

public class SqlDateStringType extends DateStringType {
    private static final SqlDateStringType singleTon = new SqlDateStringType();

    public static SqlDateStringType getSingleton() {
        return singleTon;
    }

    private SqlDateStringType() {
        super(SqlType.STRING);
    }

    protected SqlDateStringType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) throws SQLException {
        return new Date(((java.util.Date) super.sqlArgToJava(fieldType, sqlArg, columnPos)).getTime());
    }

    public Object javaToSqlArg(FieldType fieldType, Object javaObject) {
        return super.javaToSqlArg(fieldType, new java.util.Date(((Date) javaObject).getTime()));
    }

    public boolean isValidForField(Field field) {
        return field.getType() == Date.class;
    }
}
