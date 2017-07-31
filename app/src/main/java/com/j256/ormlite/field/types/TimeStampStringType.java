package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class TimeStampStringType extends DateStringType {
    private static final TimeStampStringType singleTon = new TimeStampStringType();

    public static TimeStampStringType getSingleton() {
        return singleTon;
    }

    private TimeStampStringType() {
        super(SqlType.STRING);
    }

    protected TimeStampStringType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) throws SQLException {
        return new Timestamp(((Date) super.sqlArgToJava(fieldType, sqlArg, columnPos)).getTime());
    }

    public Object javaToSqlArg(FieldType fieldType, Object javaObject) {
        return super.javaToSqlArg(fieldType, new Date(((Timestamp) javaObject).getTime()));
    }

    public boolean isValidForField(Field field) {
        return field.getType() == Timestamp.class;
    }

    public Object moveToNextValue(Object currentValue) {
        long newVal = System.currentTimeMillis();
        if (currentValue == null) {
            return new Timestamp(newVal);
        }
        if (newVal == ((Timestamp) currentValue).getTime()) {
            return new Timestamp(1 + newVal);
        }
        return new Timestamp(newVal);
    }
}
