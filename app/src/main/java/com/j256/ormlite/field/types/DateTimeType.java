package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.DatabaseResults;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.SQLException;

public class DateTimeType extends BaseDataType {
    private static final String[] associatedClassNames = new String[]{"org.joda.time.DateTime"};
    private static Class<?> dateTimeClass = null;
    private static Method getMillisMethod = null;
    private static Constructor<?> millisConstructor = null;
    private static final DateTimeType singleTon = new DateTimeType();

    private DateTimeType() {
        super(SqlType.LONG);
    }

    protected DateTimeType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public static DateTimeType getSingleton() {
        return singleTon;
    }

    public String[] getAssociatedClassNames() {
        return associatedClassNames;
    }

    public Object javaToSqlArg(FieldType fieldType, Object javaObject) throws SQLException {
        return extractMillis(javaObject);
    }

    public Object parseDefaultString(FieldType fieldType, String defaultStr) throws SQLException {
        try {
            return Long.valueOf(Long.parseLong(defaultStr));
        } catch (NumberFormatException e) {
            throw SqlExceptionUtil.create("Problems with field " + fieldType + " parsing default DateTime value: " + defaultStr, e);
        }
    }

    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) throws SQLException {
        return Long.valueOf(results.getLong(columnPos));
    }

    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) throws SQLException {
        return createInstance((Long) sqlArg);
    }

    public boolean isEscapedValue() {
        return false;
    }

    public boolean isAppropriateId() {
        return false;
    }

    public Class<?> getPrimaryClass() {
        try {
            return getDateTimeClass();
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public boolean isValidForVersion() {
        return true;
    }

    public Object moveToNextValue(Object currentValue) throws SQLException {
        long newVal = System.currentTimeMillis();
        if (currentValue == null) {
            return createInstance(Long.valueOf(newVal));
        }
        if (newVal == extractMillis(currentValue).longValue()) {
            return createInstance(Long.valueOf(1 + newVal));
        }
        return createInstance(Long.valueOf(newVal));
    }

    private Object createInstance(Long sqlArg) throws SQLException {
        try {
            if (millisConstructor == null) {
                millisConstructor = getDateTimeClass().getConstructor(new Class[]{Long.TYPE});
            }
            return millisConstructor.newInstance(new Object[]{sqlArg});
        } catch (Exception e) {
            throw SqlExceptionUtil.create("Could not use reflection to construct a Joda DateTime", e);
        }
    }

    private Long extractMillis(Object javaObject) throws SQLException {
        try {
            if (getMillisMethod == null) {
                getMillisMethod = getDateTimeClass().getMethod("getMillis", new Class[0]);
            }
            if (javaObject == null) {
                return null;
            }
            return (Long) getMillisMethod.invoke(javaObject, new Object[0]);
        } catch (Exception e) {
            throw SqlExceptionUtil.create("Could not use reflection to get millis from Joda DateTime: " + javaObject, e);
        }
    }

    private Class<?> getDateTimeClass() throws ClassNotFoundException {
        if (dateTimeClass == null) {
            dateTimeClass = Class.forName("org.joda.time.DateTime");
        }
        return dateTimeClass;
    }
}
